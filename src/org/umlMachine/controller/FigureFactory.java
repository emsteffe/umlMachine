package org.umlMachine.controller;

import java.util.Set;

import org.umlMachine.figures.StateFigure;
import org.umlMachine.figures.TaskFigure;
import org.umlMachine.figures.TransitionFigure;
import org.umlMachine.xml.XMLElement;

public class FigureFactory {

	private static FigureFactory instance = null;
	private FigureFactory(){}
	public static FigureFactory getInstance(){
		if(instance == null) instance = new FigureFactory();
		return instance;
	}
	
	public StateFigure getState(String name){
		StateFigure toReturn = new StateFigure();
		return toReturn;
	}
	
	public StateFigure getState(boolean isStart){
		StateFigure toReturn = new StateFigure(isStart);
		return toReturn;
	}
	
	
	public TransitionFigure getTransition(String action){
		TransitionFigure toReturn = new TransitionFigure();
		return toReturn;
	}
	
	public Set<TaskFigure> buildFigures(Set<XMLElement>  elements){
		
		return null;
	}

}
