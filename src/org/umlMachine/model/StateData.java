package org.umlMachine.model;

import java.util.ArrayList;
import java.util.List;

import org.umlMachine.controller.FigureFactory;

public class StateData {
	private boolean isStart = false;
	private boolean isEnd = false;
	private boolean isParent = false;
	private boolean isChild = false;
	private StateData parent = null;
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

	public int getFamilyType(){
		if(isChild) return -1;
		if(isParent) return 1;
		return 0;
	}	

	public StateData getParent(){
		return parent;
	}

	//For a state to become a child it needs to have exactly one parent state available
	//TODO This is really bad at error handling. Fix it later.
	@SuppressWarnings("unchecked")
	public boolean makeChild(){
		
		//Already a child or a parent
		if(isChild || isParent) {
			System.out.println("This state is already has a family.");
			return false;
		}
		
		ArrayList<StateData> candidates = new ArrayList<StateData>();
		ArrayList<TransitionData> transIn = (ArrayList<TransitionData>) transitionsIn.clone();
		
		//Gather a list of all available parents directly connected to this state
		for(TransitionData trans : transIn){
			if(trans.getStart().isParent) candidates.add(trans.getStart());
		}
		
		//Get any parents of children states connected to us
		for(TransitionData trans : transIn){
			if(trans.getStart().parent != null) candidates.add(trans.getStart().parent);
		}
		
		//No available parents
		if(candidates.size() == 0) {
			System.out.println("No parents available");
			return false;
		}
		
		//Ensure that there is only one available parent, even if it appears multiple times in the list
		for(StateData x : candidates){
			for(StateData y : candidates){
				if(x != y) {
					System.out.println("Too many valid parents.");
					return false;
				}
			}	
		}
		
		//This line seems too simple and it makes me uneasy
		parent = candidates.get(0);

		isParent = false;
		isChild = true;

		return true;
	}

	public boolean makeParent(){
		
		//Too many things to check to make this safe, so just disable it for now
		if(isChild || isParent) return error("This state is already a child state");
		
		isChild = false;
		isParent = true;	
		return true;
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
	
	public List<String> getEntryActions(){
		return entryActions;
	}
	
	public List<String> getExitActions(){
		return exitActions;
	}
	
	public List<String> getInternalTriggers(){
		ArrayList<String> triggers = new ArrayList<String>();
		for(String action:internalActions){
			triggers.add(action.substring(0, action.indexOf('/')));
		}	
		return triggers;
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
		if(!transitionsOut.contains(transition) && !isEnd){
			transitionsOut.add(transition);
			//transition.getEnd().addTransitionIn(transition);
		}
	}

	public void addTransitionIn(TransitionData transition){
		//Because of prototyping, every transition is the same object.
		//TODO re-implement
		//if(!transitionsIn.contains(transition)){
		transitionsIn.add(transition);
		//}
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
	
	private boolean error(String x){
		System.out.println(x);
		return false;
	}
	
	public boolean isParent(){
		return isParent;
	}
	
	public boolean isChild(){
		return isChild;
	}
	
	public void setIsChild(boolean set){
		isChild = set;
	}
	
	public void setIsParent(boolean set){
		isParent = set;
	}
	
	public void setParent(StateData parent){
		this.parent = parent;
	}

	public String toXML(){
		String toReturn = "<State name=\"" + name + "\" parent=\"" + parent +  "\" isParent=\"" + isParent +  "\" isChild=\"" + isChild +  "\" type=\"";
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
