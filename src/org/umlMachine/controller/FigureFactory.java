package org.umlMachine.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.figures.TransitionFigure;
import org.umlMachine.model.TransitionData;

//import org.umlMachine.xml.XMLElement;
import net.n3.nanoxml.XMLElement;

public class FigureFactory {

	private static FigureFactory instance = null;
	private FigureFactory(){}
	private Map<String,StateFigure> figureMap = new HashMap<String,StateFigure>();
	
	public static FigureFactory getInstance(){
		if(instance == null) instance = new FigureFactory();
		return instance;
	}
	
	public StateFigure getState(){
		StateFigure toReturn = new StateFigure();
		figureMap.put(toReturn.getName(), toReturn);
		return toReturn;
	}
	
	public Set<StateFigure> getStates(){
		Set<StateFigure> toReturn = new HashSet<StateFigure>();
		for(StateFigure state : figureMap.values())
			toReturn.add(state);
		return toReturn;
	}
	
	public StateFigure findState(String name){
		return figureMap.get(name);
	}
	
	public void reloadFactory(Set<StateFigure> toLoad){
		figureMap.clear();
		for(StateFigure state : toLoad){
			figureMap.put(state.getName(), state);
		}
	}
	
	public StateFigure getState(boolean isStart){
		StateFigure toReturn = new StateFigure(isStart);
		figureMap.put(toReturn.getName(), toReturn);
		return toReturn;
	}
	
	
	public TransitionFigure getTransition(){
		TransitionFigure toReturn = new TransitionFigure();
		return toReturn;
	}
	
	
	public void addTransitions(Set<TransitionData> transitions) {
		for(TransitionData transition : transitions){
			transition.getStart().addTransitionOut(transition);
		}
	}

	public void updateName(String name, String newName) {
		StateFigure buffer = figureMap.get(name);
		figureMap.remove(name);
		buffer.setName(newName);
		figureMap.put(buffer.getName(), buffer);
	}

}
