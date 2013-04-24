package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;
import java.util.Set;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.model.figures.StateFigure;

@SuppressWarnings("serial")
public class AnimateTool extends AbstractTool{

	public AnimateTool(){}
	public void mouseDragged(MouseEvent e){}

	@Override
	public void activate(DrawingEditor editor) {
		super.editor = editor;

		//System.out.println("The Factory has [" + FigureFactory.getInstance().getNumStates() + "] states in it.");
		
		//System.out.println(FigureFactory.getInstance().figureMap.toString());
		
		//This is really broken
		Set<StateFigure> states = FigureFactory.getInstance().getStates();
		for(StateFigure state : states){
			System.out.println( "\nName:" + state.getName());		
			System.out.println("Actions:" + state.getActions().toString());
		}

	}

	@Override
	public void mouseEntered(MouseEvent evt) {		
		//This is questionable programming -- TODO fix it later
		super.deactivate(editor);
		fireToolDone();
	}
	





}
