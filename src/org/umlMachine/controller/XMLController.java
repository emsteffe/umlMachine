package org.umlMachine.controller;

import java.util.List;

import org.umlMachine.model.*;

public class XMLController {
	
	public XMLController(){}
	
	public String serialize(List<StateData> states){
		String toReturn = "";
		for(StateData state : states){
			toReturn = toReturn + state.toXML() + "\n";
		}
		return toReturn;
	}
	
	

}
