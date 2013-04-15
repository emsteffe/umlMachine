/* @(#)testDetails.java	 * 
 * helper methods to help with the UMLModelTest.Java
 **/
package org.umlMachine.test;

import org.umlMachine.controller.FigureFactory;
import org.umlMachine.controller.XMLController;
import org.umlMachine.model.figures.StateFigure;
import org.umlMachine.model.figures.TransitionFigure;

public class TestDetails{

	StateFigure[] Figure = new StateFigure[10];

	/** constructs the desired number of figures
	 * and returns the size
	 * @param a is the list that is working on
	 * @param number is the desired number of state figures to be created
	 */
	public int construct(FigureFactory ff, int number){
		if (number > 0){ 
			String[] name = new String[number];
			for (int i = 0; i < number; i++){
				Figure[i] = new StateFigure();
				name[i] = "name" + i;
				ff.figureMap.put(name[i], Figure[i]);
			}
		}
		return ff.figureMap.size();
	}
	
	// creates and checks if successfully created a start state figure	
	public boolean createStartState(FigureFactory ff){
		StateFigure s = new StateFigure(true);
		String name = "start";
		ff.figureMap.put(name, s);
		return ff.figureMap.get(name).getType() == -1;
	}

	// creates and checks if successfully created an end state figure
	public boolean createEndState(FigureFactory ff){
		StateFigure s = new StateFigure(false);
		String name = "end";
		ff.figureMap.put(name, s);
		return ff.figureMap.get(name).getType() == 1;
	}

	// creates a Transition Figure between two States
	public boolean createTransitionFigure(FigureFactory ff){
		StateFigure s = new StateFigure(true);
		StateFigure e = new StateFigure(false);
		String start = "start";
		String end = "end";
		TransitionFigure t = new TransitionFigure();
		ff.figureMap.put(start, s);
		ff.figureMap.put(end, e);
		try{
		ff.figureMap.get(start).getData().getTransitionsOut().add(t.getData());
		ff.figureMap.get(end).getData().getTransitionsIn().add(t.getData());
		return true;
		}catch(Exception x){
		return false;
		}
	}

	//creates a transition into the start state form the regular state
	public boolean allowedTransitionIntoStartState(FigureFactory ff){
		StateFigure s = new StateFigure(true);
		StateFigure e = new StateFigure();
		String start = "start";
		String normalState = "state1";
		TransitionFigure t = new TransitionFigure();
		ff.figureMap.put(start, s);
		ff.figureMap.put(normalState, e);
		try{
		ff.figureMap.get(normalState).getData().getTransitionsOut().add(t.getData());
		ff.figureMap.get(start).getData().getTransitionsIn().add(t.getData());
		return false;
		}catch(Exception x){
		return true;
		}
	}
	// creates a transition out of end state figure
	public boolean allowedEndStateToHaveTransitionsOut(){
		StateFigure e = new StateFigure(false);
		TransitionFigure t = new TransitionFigure();
		try{
		e.getData().addTransitionOut(t.getData());
		return true;
		}catch (Exception x){
		return false;
		}

	}
	/*
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
	*/
}

