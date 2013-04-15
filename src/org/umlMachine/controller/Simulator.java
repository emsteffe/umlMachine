/*
simulation
out of diagram
	takes a Set<figures> (will take care of loading events file)
	returns true on end state. false on cancel or dead.

out of file
	takes nothing (will take care of file loading)
	returns true on end state. false on cancel, dead or early termination.
*/

package org.umlMachine.controller;

import java.util.Set;

import org.umlMachine.model.figures.StateFigure;

public class Simulator {
	
	private static Simulator instance = null;

	public static Simulator getInstance(){
		if(instance == null) instance = new Simulator();
		return instance;
	}
	
	
	public boolean simulate(){
		return false;
	}
	
	public boolean simulate(Set<StateFigure> s){
		return false;
	}


	public void fromFile() {
		// TODO Auto-generated method stub
		FileHandler.getInstance().getFile();
	}


	public void fromDiagram() {
		// TODO Auto-generated method stub
		FileHandler.getInstance().getFile();
	}

	
}
	