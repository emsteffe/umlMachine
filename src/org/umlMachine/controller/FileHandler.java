/*file save, load, serialize.

file save 
takes a Set<figures>
returns bool. true on save success

file load
takes nothing
returns a Set<figures>

serialize
takes a Set<figures>
returns bool. true on success storing

*/


package org.umlMachine.controller;

import java.util.Set;

import org.umlMachine.figures.StateFigure;
import org.umlMachine.model.StateData;

public class FileHandler {
	
	public boolean exportDiagram(Set<StateFigure> states){//both transitions and states
		return false;
	}
	
	public Set<StateFigure> importDiagram(){
		return null;
	}
	
	public Set<StateData> loadSerial(){
		return null;
	}
	
	public boolean serialize(Set<StateFigure> states){
		return false;
	}
	
}


