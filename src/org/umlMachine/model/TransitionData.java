package org.umlMachine.model;

import java.util.ArrayList;
import java.util.StringTokenizer;
import org.umlMachine.controller.FigureFactory;

public class TransitionData {

	private StateData start;
	private StateData end;
	private ArrayList<String> actions;
	private String event;
	
	public TransitionData(){}

	@SuppressWarnings("unchecked")
	public TransitionData(ArrayList<String> actions, StateData start, StateData end,String event){
		this.start = start;
		this.end = end;
		this.actions = (ArrayList<String>) actions.clone();
		this.event = event;
	}

	// get/set basic stuff
	public void setStart(StateData start){this.start = start;}
	public void setEnd(StateData end){this.end = end;}
	public void setEvent(String event){this.event = event;}
	public StateData getStart(){return start;}
	public StateData getEnd(){return end;}
	public String getEvent(){return event;}
	
	public void addAction(String action){
		actions.add(action);
	}
	
	public void removeAction(String action){
		actions.remove(action);
	}

	public String toXML(){
		String toReturn = "<Transition ";
		toReturn = toReturn + "\t<Actions>\n";
		
		for(String action : actions){
			toReturn = toReturn + "\t\t<Action>" + action + "</Action>\n";
		}
		
		toReturn = toReturn + "\t</Actions>\n";
		toReturn = toReturn + "\" start=\"" + start.getName();
		toReturn = toReturn + "\" end=\"" + end.getName();
		toReturn = toReturn + "\" event=\"" + event;
		toReturn = toReturn + "\"/>";
		
		return toReturn;
	}

	public void setFromXML(String xml){
		StringTokenizer tokens = new StringTokenizer(xml.trim(),"\"");
		tokens.nextToken();//start
		start = FigureFactory.getInstance().findState(tokens.nextToken().trim()).getData();//value for start
		tokens.nextToken();
		end = FigureFactory.getInstance().findState(tokens.nextToken().trim()).getData();
		tokens.nextToken();
		event = tokens.nextToken();


	}
}
