package org.umlMachine.model;

import java.util.ArrayList;
import java.util.List;

import org.umlMachine.controller.FigureFactory;

public class StateData {
	private boolean isStart = false;
	private boolean isEnd = false;
	private boolean isParent = false;
	private boolean isChild = false;
	private String name;
	private ArrayList<String> entryActions;
	private ArrayList<String> internalActions;
	private ArrayList<String> exitActions;
	private ArrayList<TransitionData> transitionsOut;
	private ArrayList<TransitionData> transitionsIn;

	public StateData(){

		entryActions = new ArrayList<String>();
		internalActions = new ArrayList<String>();
		exitActions = new ArrayList<String>();
		transitionsOut = new ArrayList<TransitionData>();
		transitionsIn = new ArrayList<TransitionData>();
	}

	public StateData(boolean isStart, boolean isEnd, String name){
		this.isStart = isStart;
		this.isEnd = isEnd;
		this.name = name;
		entryActions = new ArrayList<String>();
		internalActions = new ArrayList<String>();
		exitActions = new ArrayList<String>();
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
		FigureFactory.getInstance().updateName(this.name,name);
		this.name = name;
	}

	public void forceName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
	
	//I have no idea if we are going to use this
	public int getFamilyType(){
		if(isChild) return -1;
		if(isParent) return 1;
		return 0;
	}
	
	public void makeParent(){
		isChild = false;
		isParent = true;	
	}
	
	public void makeChild(){
		isParent = false;
		isChild = true;
	}
	
	public void makeNormal(){
		isParent = false;
		isChild = false;
	}
	

	public void removeAction(String a)	{
		if(a.startsWith("Entry/")){
			entryActions.remove(a);
		}else if(a.startsWith("Exit/")){
			exitActions.remove(a);
		}else{
			internalActions.remove(a);	
		}
	}

	public void addAction(String a){
		if(a.startsWith("Entry/")){
			entryActions.add(a);
		}else if(a.startsWith("Exit/")){
			exitActions.add(a);
		}else{
			internalActions.add(a);	
		}
	}

	//Return one big list of actions
	public List<String> getActions(){
		List<String> actions = new ArrayList<String>();
		actions.addAll(entryActions);
		actions.addAll(internalActions);
		actions.addAll(exitActions);
		return actions;
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
		String toReturn = "<State name=\"" + name + "\" type=\"";
		if(isStart()) toReturn = toReturn + "start";
		else if(isEnd()) toReturn = toReturn + "end";
		else toReturn = toReturn + "norm";
		toReturn = toReturn + "\">\n";
		toReturn = toReturn + "\t<Actions>\n";

		for(String action : getActions()){
			toReturn = toReturn + "\t\t<Action>" + action + "</Action>\n";
		}

		toReturn = toReturn + "\t</Actions>\n";
		toReturn = toReturn + "</State>";

		return toReturn;
	}

}
