package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;
import java.util.Set;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.model.TransitionData;
import org.umlMachine.view.figures.StateFigure;

@SuppressWarnings("serial")
public class ValidateTool extends AbstractTool{

	public ValidateTool(){}
	public void mouseDragged(MouseEvent e){}

	@Override
	public void activate(DrawingEditor editor) {
		super.editor = editor;
		
		System.out.println("Validate Diragram");
		FigureFactory fact = FigureFactory.getInstance();
		System.out.println("total states " + fact.getNumStates());
		Set<StateFigure> states = fact.getStates();
		for(StateFigure state : states){
			System.out.println(state.getData().getName());
			for(String a : state.getData().getActions()){
				System.out.println("\t"+a);
			}
			for(TransitionData trans : state.getData().getTransitionsOut()){
				System.out.println("\t\t"+trans.getEvent());
				for(String s : trans.getActions()){
					System.out.println("\t\t\t"+s);
				}
				System.out.println("\t\t\t"+trans.getStart().getName()+" -> "+trans.getEnd().getName());
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent evt) {		
		//This is questionable programming -- TODO fix it later
		super.deactivate(editor);
		fireToolDone();
	}
	





}
