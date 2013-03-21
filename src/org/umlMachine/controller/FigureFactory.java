package org.umlMachine.controller;

import java.util.Set;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.figures.TransitionFigure;

//import org.umlMachine.xml.XMLElement;
import net.n3.nanoxml.XMLElement;

public class FigureFactory {

	private static FigureFactory instance = null;
	private FigureFactory(){}
	public static FigureFactory getInstance(){
		if(instance == null) instance = new FigureFactory();
		return instance;
	}
	
	public StateFigure getState(){
		StateFigure toReturn = new StateFigure();
		return toReturn;
	}
	
	public StateFigure getState(boolean isStart){
		StateFigure toReturn = new StateFigure(isStart);
		return toReturn;
	}
	
	
	public TransitionFigure getTransition(){
		TransitionFigure toReturn = new TransitionFigure();
		return toReturn;
	}
	
	public Set<StateFigure> buildFigures(Set<XMLElement>  elements){
		
		return null;
	}

}
