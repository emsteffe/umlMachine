package org.umlMachine.model;

import java.util.ArrayList;
import java.util.List;

public class StateData {
	private boolean isStart = false;
	private boolean isEnd = false;
	private String name;
	private ArrayList<String> actions;
	private ArrayList<TransitionData> transitionsOut;
	private ArrayList<TransitionData> transitionsIn;

	public StateData(){
		
		actions = new ArrayList<String>();
		transitionsOut = new ArrayList<TransitionData>();
		transitionsIn = new ArrayList<TransitionData>();
	}
	
	public StateData(boolean isStart, boolean isEnd, String name){
		this.isStart = isStart;
		this.isEnd = isEnd;
		this.name = name;
		actions = new ArrayList<String>();
		transitionsOut = new ArrayList<TransitionData>();
		transitionsIn = new ArrayList<TransitionData>();
	}
	
	public void setStart(boolean isStart){
		this.isStart = isStart;
	}
	
	public boolean isStart(){
		return isStart;
	}
	
	public void setEnd(boolean isEnd){
		this.isEnd = isEnd;
	}
	
	public boolean isEnd(){
		return isEnd;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addAction(String action){
		actions.add(action);
	}
	
	public void removeAction(String action){
		actions.remove(action);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getActions(){
		return (List<String>) actions.clone();
	}
	
	@SuppressWarnings("unchecked")
	public List<TransitionData> getTransitionsOut(){
		return (List<TransitionData>) transitionsOut.clone();
	}
	
	@SuppressWarnings("unchecked")
	public List<TransitionData> getTransitionsIn(){
		return (List<TransitionData>) transitionsIn.clone();
	}
	
	public void addTransitionOut(TransitionData transition){
		if(!transitionsOut.contains(transition)){
			transitionsOut.add(transition);
			transition.getEnd().addTransitionIn(transition);
		}
	}
	
	public void addTransitionIn(TransitionData transition){
		if(!transitionsIn.contains(transition)){
			transitionsIn.add(transition);
		}
	}
	
	public void removeTransitionOut(TransitionData transition){
		if(transitionsOut.contains(transition)){
			transitionsOut.remove(transition);
			transition.getEnd().removeTransitionIn(transition);
		}
	}
	
	public void removeTransitionIn(TransitionData transition){
		if(transitionsIn.contains(transition)){
			transitionsIn.remove(transition);
		}
	}
	
	public String toXML(){
		String toReturn = "<State name=" + name + " type=";
		if(isStart()) toReturn = toReturn + "start";
		else if(isEnd()) toReturn = toReturn + "end";
		else toReturn = toReturn + "norm";
		toReturn = toReturn + ">\n";
		
		toReturn = toReturn + "\t<Actions>\n";
		for(String action : actions){
			toReturn = toReturn + "\t\t<Action>" + action + "</Action>\n";
		}
		toReturn = toReturn + "\t</Actions>\n";
		
		toReturn = toReturn + "\t<TransitionsIn>\n";
		for(TransitionData transition : transitionsIn){
			toReturn = toReturn + "\t\t" + transition.toXML() + "\n";
		}
		toReturn = toReturn + "\t</TransitionsIn>\n";
		
		toReturn = toReturn + "\t<TransitionsOut>\n";
		for(TransitionData transition : transitionsOut){
			toReturn = toReturn + "\t\t" + transition.toXML() + "\n";
		}
		toReturn = toReturn + "\t</TransitionsOut>\n";
		toReturn = toReturn + "</State>";
		
		return toReturn;
	}
	
}
