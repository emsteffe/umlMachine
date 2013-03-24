package org.umlMachine.controller.tools;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import org.jhotdraw.draw.CompositeFigure;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FigureFactory;

public class AnimateTool extends AbstractTool{

	public AnimateTool(){
		
	}

	@Override
	public void activate(DrawingEditor editor) {
		super.activate(editor);
		
		System.out.println("The Factory has [" + FigureFactory.getInstance().getNumStates() + "] states in it.");
		
		super.deactivate(editor);
		fireToolDone();
	}


	public void mouseDragged(MouseEvent e) {}

}
