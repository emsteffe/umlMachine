package org.umlMachine.controller.tools;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;

import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FigureFactory;

public class AnimateTool extends AbstractTool{

	public AnimateTool(){}
	public void mouseDragged(MouseEvent e){}

	@Override
	public void activate(DrawingEditor editor) {
		super.editor = editor;

		System.out.println("The Factory has [" + FigureFactory.getInstance().getNumStates() + "] states in it.");
		System.out.println(FigureFactory.getInstance().figureMap.toString());

	}


	@Override
	public void mouseEntered(MouseEvent evt) {		
		//This is questionable programming -- TODO fix it later
		super.deactivate(editor);
		fireToolDone();
	}
	





}
