package org.umlMachine.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.figures.TransitionFigure;
import org.umlMachine.model.TransitionData;

public class FigureFactory {

	private static FigureFactory instance = null;
	public FigureFactory(){}
	public Map<String,StateFigure> figureMap = new HashMap<String,StateFigure>();
	private int numStates;

	
	public static FigureFactory getInstance(){
		if(instance == null) instance = new FigureFactory();
		return instance;
	}
	
	public StateFigure getState(){
		StateFigure toReturn = new StateFigure();
		figureMap.put(toReturn.getName(), toReturn);
		numStates++;
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
		numStates = 0;
		for(StateFigure state : toLoad){
			figureMap.put(state.getName(), state);
			numStates++;
		}
	}
	
	public StateFigure getState(boolean isStart){
		StateFigure toReturn = new StateFigure(isStart);
		figureMap.put(toReturn.getName(), toReturn);
		numStates++;
		return toReturn;
	}
	
	public int getNumStates(){
		return numStates;
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
		if(buffer!= null){
			buffer.setName(newName);
			figureMap.put(buffer.getName(), buffer);
		}
	}

}
