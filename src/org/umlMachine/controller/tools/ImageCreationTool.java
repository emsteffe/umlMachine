package org.umlMachine.controller.tools;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
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
import org.umlMachine.figures.StateFigure;

import edu.umd.cs.findbugs.annotations.Nullable;

@SuppressWarnings("serial")
public class ImageCreationTool extends CreationTool{

	@SuppressWarnings({ "deprecation", "rawtypes" })
	public ImageCreationTool(StateFigure stateFigure, @Nullable Map<AttributeKey, Object> attributes) {
		super(stateFigure, attributes, null);
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
