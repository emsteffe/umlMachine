package org.umlMachine.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jhotdraw.xml.DefaultDOMFactory;
import org.jhotdraw.xml.NanoXMLDOMInput;
import org.jhotdraw.xml.NanoXMLDOMOutput;
import org.umlMachine.model.*;
import org.umlMachine.model.figures.StateFigure;

public class XMLController {
	
	public XMLController(){}
	
	public String serialize(Set<StateData> toSerialize){
		String toReturn = "<Serial>\n";
		toReturn = toReturn + "<States>\n";
		for(StateData state : toSerialize){
			toReturn = toReturn + state.toXML() + "\n";
		}
		toReturn = toReturn + "</States>\n";
		toReturn = toReturn + "<Transitions>\n";
		for(StateData state : toSerialize){
			for(TransitionData transition : state.getTransitionsOut()){
				toReturn = toReturn + transition.toXML() + "\n";
			}
		}
		toReturn = toReturn + "</Transitions>\n";
		toReturn = toReturn + "</Serial>";
		return toReturn;
	}
	
	public Set<StateData> loadData(File file) throws IOException{
		Set<StateData> toReturn = new HashSet<StateData>();
		HashMap<String,StateData> tempMap = new HashMap<String,StateData>();
		NanoXMLDOMInput in = new NanoXMLDOMInput(new DefaultDOMFactory(), new FileInputStream(file));
		in.openElement("Serial");
		in.openElement("States");
		
		int stateCount = in.getElementCount();
		for(int i= 0; i!=stateCount; i++){
			in.openElement(i);//open state
			StateData data = new StateData();
			data.setName(in.getAttribute("name", ""));
			if(in.getAttribute("type", "").equals("start"))
				data.setStart(true);
			else if(in.getAttribute("type", "").equals("end"))
				data.setEnd(true);
			
			in.openElement("Actions");

			int actionCount = in.getElementCount();
			for(int k=0; k!= actionCount; k++){
				in.openElement("Action");
				data.addAction(in.getText());
				in.closeElement();//close Action
			}
			in.closeElement();//close Actions
			
			in.closeElement();//close State
			tempMap.put(data.getName(), data);
		}
		
		in.closeElement();//close States
		
		in.openElement("Transitions");
		int transitionCount = in.getElementCount();
		for(int i= 0; i!= transitionCount; i++){
			in.openElement(i); //open Transition
			TransitionData transition = new TransitionData();
			transition.addAction(in.getAttribute("action", ""));
			//make it multi action
			transition.setEvent(in.getAttribute("event", ""));
			transition.setStart(tempMap.get(in.getAttribute("start", "")));
			transition.setEnd(tempMap.get(in.getAttribute("end", "")));
			transition.getStart().addTransitionOut(transition);
			
			in.closeElement();//close Transition
		}
		in.closeElement(); //close Transitions
		
		in.closeElement();//close Serial
		return toReturn;
	}
	
	public Set<StateFigure> importStateFigures(File file) throws IOException{
		Set<StateFigure> toReturn = new HashSet<StateFigure>();
		NanoXMLDOMInput in = new NanoXMLDOMInput(new DefaultDOMFactory(), new FileInputStream(file));
		in.openElement("Export");
		in.openElement("StateFigures");

		int stateCount = in.getElementCount();
		for(int i = 0; i!=stateCount ; i++){
			in.openElement(i); // statefigure number
			StateFigure toAdd = new StateFigure();
			toAdd.read(in);
			toReturn.add(toAdd);
			in.closeElement(); // close statefigure
		}
		
		in.closeElement(); // close StateFigures
		in.closeElement(); // close Export
		
		return toReturn;
	}
	
	public Set<TransitionData> importTransitions(File file) throws IOException{
		Set<TransitionData> toReturn = new HashSet<TransitionData>();
		NanoXMLDOMInput in = new NanoXMLDOMInput(new DefaultDOMFactory(), new FileInputStream(file));
		in.openElement("Export");
		in.openElement("StateFigures");
		in.closeElement(); // opening and closing statefigures for safety
		in.openElement("Transitions");

		int transitionCount = in.getElementCount();
		for(int i = 0; i!=transitionCount ; i++){
			in.openElement(i); // open transitiondata
			in.openElement(0); // open transition
			TransitionData toAdd = new TransitionData();
			toAdd.addAction(in.getAttribute("action", ""));
			toAdd.setEvent(in.getAttribute("event", ""));
			toAdd.setStart(FigureFactory.getInstance().findState(in.getAttribute("start", "")).getData());
			toAdd.setEnd(FigureFactory.getInstance().findState(in.getAttribute("end", "")).getData());
			toReturn.add(toAdd);
			in.closeElement(); // close transition
			in.closeElement(); // transitiondata
		}
		
		in.closeElement(); // close StateFigures
		in.closeElement(); // close Export
		
		return toReturn;
	}
	
	
	public void exportFigures(File file) throws IOException{
		NanoXMLDOMOutput out = new NanoXMLDOMOutput(new DefaultDOMFactory());
		out.openElement("Export");
		out.openElement("StateFigures");
		
		Set<StateFigure> stateSet = FigureFactory.getInstance().getStates(); 
		for(StateFigure state : stateSet){
			out.openElement("StateFigure"); // statefigure number
			state.write(out);
			out.closeElement(); // close statefigure
		}
		
		out.closeElement(); // close StateFigures
		
		out.openElement("Transitions");
		
		for(StateFigure state : stateSet){
			out.openElement("StateFigure"); // statefigure number
			for(TransitionData transition : state.getData().getTransitionsOut()){
				out.openElement("TransitionData"); //consecutive add texts will not be merged so create wrapper
				out.addText(transition.toXML());
				out.closeElement();
			}
			out.closeElement(); // close statefigure
		}
		
		out.closeElement(); //close Transitions
		out.closeElement(); // close Export
		
		out.save(new FileOutputStream(file));
	}
	
	

}
