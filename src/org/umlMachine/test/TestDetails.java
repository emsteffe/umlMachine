/** @(#)testDetails.java
 * 
 * helper methods to help with the UMLModelTest.Java
 * 
 * constructs the models being tested  
 */
package org.umlMachine.test;
import java.util.ArrayList;
import java.util.List;

import org.umlMachine.controller.XMLController;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.figures.TransitionFigure;
import org.umlMachine.model.TransitionData;

public class TestDetails{

	StateFigure defaultFigure = new StateFigure();
	
	/** constructs the desired number of figures
	 * and returns the size 
	 * @param a is the list that is working on
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
	
	/* creates and checks if successfully created a start state figure */	
	
	public boolean createStartState(){
		StateFigure s = new StateFigure(true);
		return s.getType() == -1;
	}
	
	/* creates and checks if successfully created an end state figure */
	
	public boolean createEndState(){
		StateFigure s = new StateFigure(false);
		return s.getType() == 1;
	}
	
	/* creates a Transition Figure 
	 */
	
	public boolean createTransitionFigure() {
		StateFigure start = new StateFigure(true);
		StateFigure end = new StateFigure(false);
		
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
	
	
	public boolean allowedTranstionToHaveNoEndStateFigure(ArrayList<StateFigure> l){
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

	public String expected2(){
		String s = "<State name=state1 type=start>\n"+
				"\t<Actions>\n"+
				"\t\t<Action>action1</Action>\n"+
			"\t</Actions>\n"+
			"\t<TransitionsIn>\n" +
				"\t\t<Transition action=trans1 start=state1 end=state1 trigger=refself event=event1 condition=[con1]/>\n" +
			"\t</TransitionsIn>\n" +
			"\t<TransitionsOut>\n" +
				"\t\t<Transition action=trans1 start=state1 end=state1 trigger=refself event=event1 condition=[con1]/>\n" +
				"\t\t<Transition action=trans2 start=state1 end=state2 trigger=continue event=event2 condition=null/>\n" +
			"\t</TransitionsOut>\n" +
		"</State>\n" +
		"<State name=state2 type=end>\n"+
			"\t<Actions>\n"+
				"\t\t<Action>action2</Action>\n"+
				"\t\t<Action>action3</Action>\n" +
			"\t</Actions>\n"+
			"\t<TransitionsIn>\n" +
				"\t\t<Transition action=trans2 start=state1 end=state2 trigger=continue event=event2 condition=null/>\n" +
			"\t</TransitionsIn>\n" +
			"\t<TransitionsOut>\n" +
			"\t</TransitionsOut>\n" +
		"</State>\n";
		return s;
	}
	
	public String serializeData(XMLController x){
		StateFigure one = new StateFigure();
		StateFigure two = new StateFigure();
		
		TransitionData tran1 = new TransitionData("trans1", one, one, "refself", "event1", "[con1]");
		TransitionData tran2 = new TransitionData("trans2", one, two, "continue", "event2",null);
		
		one.addAction("action1");
		two.addAction("action2");
		two.addAction("action3");
		
		one.setName("state1");
		one.setName("state2");
		
		one.setStart(true);
		two.setEnd(true);
		
		one.addTransitionOut(tran1);
		two.addTransitionOut(tran2);
		
		List<StateData> testList = new ArrayList<StateData>();
		
		testList.add(one);
		testList.add(two);
		
		return x.serialize(testList);
		
	}
}

	
	
