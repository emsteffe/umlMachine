package org.umlMachine.controller.tools;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.view.figures.StateFigure;

import com.sun.istack.internal.Nullable;

@SuppressWarnings("serial")
public class ImageCreationTool extends CreationTool{


	protected Figure createdFigure;
	boolean type; //true = start, false = end


	@SuppressWarnings({ "deprecation", "rawtypes" })
	public ImageCreationTool(Boolean b, HashMap<AttributeKey, Object> attributes) {
		super(new StateFigure(), attributes, null);
		type = b;

	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Figure createFigure() {
		
		Figure f;
		//If you are trying to make a start state and one already exists...
		if(type && (FigureFactory.getInstance().findState("StartState[jh72%3tr#FHuu]")!=null) ){
			f = FigureFactory.getInstance().getState(false);
		}else{
			f = FigureFactory.getInstance().getState(type);
		}
				
		getEditor().applyDefaultAttributesTo(f);
		if (prototypeAttributes != null) {
			for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet()) {
				f.set(entry.getKey(), entry.getValue());
			}
		}
		return f;
	}


	@SuppressWarnings({ "deprecation", "rawtypes" })
	public ImageCreationTool(StateFigure stateFigure, @Nullable Map<AttributeKey, Object> attributes) {
		super(stateFigure, attributes, null);

	}

	@Override
	public void mousePressed(MouseEvent evt) {

		getView().clearSelection();
		
		createdFigure = createFigure();
		Point2D.Double p = constrainPoint(viewToDrawing(anchor));
		anchor.x = evt.getX();
		anchor.y = evt.getY();
		createdFigure.setBounds(p, p);
		getDrawing().add(createdFigure);
	}

	@Override
	public void activate(DrawingEditor editor) {
		super.activate(editor);
	}



	// Override this method to resize the image back to correct dimensions if user clicked and dragged while placing state
	@Override
	public void mouseReleased(MouseEvent evt) {

		if (createdFigure != null) {
			Rectangle2D.Double bounds = createdFigure.getBounds();
			if (bounds.width == 0 && bounds.height == 0) {
				getDrawing().remove(createdFigure);
				if (isToolDoneAfterCreation()) {
					fireToolDone();
				}

			} else {

				//if (Math.abs(anchor.x - evt.getX()) < minimalSizeTreshold.width && Math.abs(anchor.y - evt.getY()) < minimalSizeTreshold.height) {
				createdFigure.willChange();
				createdFigure.setBounds(constrainPoint(new Point(anchor.x, anchor.y)),constrainPoint(new Point(anchor.x + (int) minimalSize.width,anchor.y + (int)  minimalSize.height)));
				createdFigure.changed();
				//}


				if (createdFigure instanceof CompositeFigure) {
					((CompositeFigure) createdFigure).layout();
				}
				final Figure addedFigure = createdFigure;

				final Drawing addedDrawing = getDrawing();
				getDrawing().fireUndoableEditHappened(new AbstractUndoableEdit() {

					@Override
					public String getPresentationName() {
						return presentationName;
					}

					@Override
					public void undo() throws CannotUndoException {
						super.undo();
						addedDrawing.remove(addedFigure);
					}

					@Override
					public void redo() throws CannotRedoException {
						super.redo();
						addedDrawing.add(addedFigure);
					}
				});
				Rectangle r = new Rectangle(anchor.x, anchor.y, 0, 0);
				r.add(evt.getX(), evt.getY());
				maybeFireBoundsInvalidated(r);
				creationFinished(createdFigure);
				createdFigure = null;
			}
		} else {
			if (isToolDoneAfterCreation()) {
				fireToolDone();
			}
		}
	}

}
