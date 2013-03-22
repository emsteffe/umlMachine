/* @(#)testDetails.java
 * 
 * helper methods to help with the UMLModelTest.Java
 * 
 * constructs the models being tested  
 */
package org.umlMachine.test;
import java.util.ArrayList;

import org.umlMachine.controller.XMLController;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.figures.TransitionFigure;
import org.umlMachine.model.TransitionData;

public class TestDetails{

	StateFigure defaultFigure = new StateFigure();
	
	/* constructs the desired number of the figures
	 * and returns the size 
	 * @param a is the list that we are working with
	 * @param number is the desired number of state figures to be created
	 */
	public int construct(ArrayList<StateFigure> a, int number){
		if (number == 0) return a.size();
		else
			for (int i = 0; i < number; i++){
				a.add(defaultFigure);
			}
		return a.size();
	}
	
	/* creates and checks if we successfully created a start state figure */	
	
	public boolean createStartState(ArrayList<StateFigure> a){
		StateFigure s = new StateFigure(true);
		a.add(s);
		return a.get(0).getType() == -1;
	}
	
	/* creates an checks if we successfully created an end state figure */
	
	public boolean createEndState(ArrayList<StateFigure> a){
		StateFigure s = new StateFigure(false);
		a.add(s);
		return a.get(0).getType() == 1;
	}
	
	/* creates a Transition Figure *?
	 */
	
	public boolean createTransitionFigure() {
		// TODO 
		return false;
	}

	
	
	/* creates two state figures, one is a start state, the other is a regular state
	 * and will try to create a transition into the start state form the regular state
	 */
	
	public boolean allowedTransitionIntoStartState(){
		StateFigure a = new StateFigure(true);
		StateFigure b = new StateFigure();
		TransitionFigure t = new TransitionFigure();
		a.addDependency(t);
		
		return false;
	}
	
	public boolean allowedTranstionToHaveNoEndState(ArrayList<StateFigure> l){
		StateFigure a = new StateFigure();
		
		return false;
	}
	
	public boolean allowedEndStateToHaveTransitionsOut(
			ArrayList<StateFigure> listFigures2) {
		// TODO 
		return false;
	}

	public String expected1(){
		String s = "<State name=null type=norm>\n"+
				"\t<Actions>\n"+
				"\t</Actions>\n"+
				"\t<TransitionsIn>\n"+
				"\t</TransitionsIn>\n"+
				"\t<TransitionsOut>\n"+
				"\t</TransitionsOut>\n"+
				"</State>";
		return s;
	}
	public String createDefaultState(){
		
		String toReturn = "<State name=" + defaultFigure.getName() + " type=";
		if(defaultFigure.getType() == -1) toReturn = toReturn + "start";
		else if(defaultFigure.getType() == 1) toReturn = toReturn + "end";
		else toReturn = toReturn + "norm";
		toReturn = toReturn + ">\n";
		
		toReturn = toReturn + "\t<Actions>\n";
		for(String action : defaultFigure.actions){
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

	
	
