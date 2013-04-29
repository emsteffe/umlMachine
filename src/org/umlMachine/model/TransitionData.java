package org.umlMachine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.view.figures.StateFigure;

public class TransitionData {

	private StateData start;
	private StateData end;
	private ArrayList<String> actions = new ArrayList<String>();
	private String event = "";
	private static int count = 0;
	
	public TransitionData(){count++;}
	
	public static int getCount(){
		return count;
	}

	@SuppressWarnings("unchecked")
	public TransitionData(ArrayList<String> actions, StateData start, StateData end,String event){
		count++;
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
		System.out.println("adding "+ action + " to trans "+event);
		actions.add(action);
	}
	
	public void removeAction(String action){
		actions.remove(action);
	}
	
	public List<String> getActions(){
		return actions;
	}

	public String toXML(){
		String toReturn = "<transition ";
		
		toReturn = toReturn + "start=\"" + start.getName();
		toReturn = toReturn + "\" end=\"" + end.getName();
		toReturn = toReturn + "\" event=\"" + event;
		toReturn = toReturn + "\">\n";
		toReturn = toReturn + "\t<actions>\n";
		
		for(String action : actions){
			toReturn = toReturn + "\t\t<action>" + action + "</action>\n";
		}
		
		toReturn = toReturn + "\t</actions>\n";
		
		toReturn =toReturn + "</transition>";
		
		return toReturn;
	}

	public void setFromXML(String xml){
		//start
		String start = xml.substring(xml.indexOf("start=\"")+7, xml.indexOf("end=\"")-2);
		String end = xml.substring(xml.indexOf("end=\"")+5 , xml.indexOf("event=\"")-2);
		String event = xml.substring(xml.indexOf("event=\"")+7, xml.indexOf("<actions>")-2);
		String actions = xml.substring(xml.indexOf("<actions>")+9, xml.indexOf("</actions>"));
		StringTokenizer tokens = new StringTokenizer(actions,"</>");
		while(tokens.hasMoreTokens()){
			tokens.nextToken();
			addAction(tokens.nextToken());
			tokens.nextToken();
		}
		System.out.println("looking for state "+start);
		this.start = FigureFactory.getInstance().findState(start).getData();
		this.end = FigureFactory.getInstance().findState(end).getData();
		this.event = event;
	}
}
