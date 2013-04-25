//Used for integration testing 

package org.umlMachine.test;

import static org.junit.Assert.*;

import java.util.LinkedHashSet;

import org.junit.Before;
import org.junit.Test;

import org.umlMachine.controller.FigureFactory;
import org.umlMachine.controller.XMLController;
import org.umlMachine.model.StateData;
import org.umlMachine.model.TransitionData;
import org.umlMachine.view.figures.StateFigure;
import org.umlMachine.view.figures.TransitionFigure;

public class UMLMachineTest extends TestDetails {

	private FigureFactory FF;
	private XMLController xml;
	
	//Models
	private StateData stateData;
	private TransitionData transitionData;
	private StateFigure stateFigure;
	private TransitionFigure transitionFigure;
	
	@Before
	public void constructModel(){
		FF = new FigureFactory();
		xml = new XMLController();
		stateData = new StateData();
		transitionData = new TransitionData();
		stateFigure = new StateFigure();
		transitionFigure = new TransitionFigure();
	}
	
	
	// Model tests
		//StateData Tests
	@Test
	public void testDefaultStateData(){
		constructModel();
		assertEquals(false, stateData.isStart());
		assertEquals(false, stateData.isEnd());
		assertEquals(null, stateData.getName());
		assertEquals(0, stateData.getTransitionsIn().size());
		assertEquals(0, stateData.getTransitionsOut().size());
		assertEquals(0, stateData.getActions().size());
	}
	@Test
	public void testStateDataSets(){
		constructModel();
		stateData.forceName("testName");
		assertEquals("testName", stateData.getName());
		stateData.forceName("name2");
		assertEquals("name2", stateData.getName());
		stateData.setStart(true);
		assertEquals(true, stateData.isStart());
		stateData.setEnd(true);
		assertEquals(true, stateData.isEnd());
	}
	
	//TransitionData tests
	@Test
	public void testDefaultTransitionData(){
		constructModel();
		assertEquals(null,transitionData.getAction());
		assertEquals(null, transitionData.getEvent());
		assertEquals(null, transitionData.getTrigger());
		assertEquals(null, transitionData.getEnd());
		assertEquals(null, transitionData.getStart());
	}
	
	@Test
	public void testTransitionDataSets(){
		constructModel();
		transitionData.setAction("actionTest");
		assertEquals("actionTest", transitionData.getAction());
		transitionData.setCondition("conditionTest");
		assertEquals("conditionTest",transitionData.condition());
		transitionData.setEvent("eventTest");
		assertEquals("eventTest", transitionData.getEvent());
		transitionData.setTrigger("triggerTest");
		assertEquals("triggerTest", transitionData.getTrigger());
	}
	
	//figureTests
	@Test
	public void testStateFigure(){
		constructModel();
		assertEquals(0, stateFigure.getActions().size());
	}
	
	@Test
	public void testXMLSerialization(){
		constructModel();
		StateData sData1 = new StateData();
		StateData sData2 = new StateData();
		StateData sData3 = new StateData();
		TransitionData tData1 = new TransitionData();
		TransitionData tData2 = new TransitionData();
		TransitionData tData3 = new TransitionData();
		TransitionData tData4 = new TransitionData();
		
		sData1.addAction("state1Action1");
		sData1.addAction("state1Action2");
		sData2.addAction("state2Action1");
		
		tData1.setStart(sData1);
		tData1.setEnd(sData2);
		tData2.setStart(sData2);
		tData2.setEnd(sData3);
		tData3.setStart(sData1);
		tData3.setEnd(sData3);
		tData4.setStart(sData2);
		tData4.setEnd(sData2);
		
		sData1.forceName("State 1");
		sData2.forceName("State 2");
		sData3.forceName("State 3");
		
		tData1.setAction("Transition 1");
		tData2.setAction("Transition 2");
		tData3.setAction("Transition 3");
		tData4.setAction("Transition 4");
		
		tData1.setTrigger("Trig1");
		tData2.setTrigger("Trig2");
		tData3.setTrigger("Trig3");
		tData4.setTrigger("Trig4");
		
		tData2.setCondition("[CON1]");
		
		sData1.addTransitionOut(tData1);
		sData1.addTransitionOut(tData3);
		sData2.addTransitionOut(tData2);
		sData2.addTransitionOut(tData4);
		
		LinkedHashSet<StateData> set = new LinkedHashSet<StateData>();
		set.add(sData1);
		set.add(sData2);
		set.add(sData3);
		
		
		assertEquals("<Serial>\n<States>\n<State name=\"State 1\"" +
				" type=\"norm\">\n\t<Actions>\n\t\t<Action>state1Action1" +
				"</Action>\n\t\t<Action>state1Action2</Action>\n\t</Actions>\n</State>\n" +
				"<State name=\"State 2\" type=\"norm\">\n\t<Actions>\n\t\t<Action>state2Action1" +
				"</Action>\n\t</Actions>\n</State>\n<State name=\"State 3\" type=\"norm\">\n\t<Actions>" +
				"\n\t</Actions>\n</State>\n</States>\n<Transitions>\n" +
				"<Transition action=\"Transition 1\" start=\"State 1\" end=\"State 2\" trigger=\"Trig1\" " +
				"event=\"null\" condition=\"null\"/>\n" +
				"<Transition action=\"Transition 3\" start=\"State 1\" end=\"State 3\" trigger=\"Trig3\" " +
				"event=\"null\" condition=\"null\"/>\n<Transition action=\"Transition 2\" start=\"State 2\" " +
				"end=\"State 3\" trigger=\"Trig2\" event=\"null\" condition=\"[CON1]\"/>\n<Transition " +
				"action=\"Transition 4\" start=\"State 2\" end=\"State 2\" trigger=\"Trig4\" event=\"null\" " +
				"condition=\"null\"/>\n</Transitions>\n</Serial>",xml.serialize(set));
	}
	
	@Test
	// test to see if the number of desired state figures are really created 
	public void testForNumbersOfFiguresCreatedInList(){
		constructModel();
		assertEquals(0, (construct(FF, 0)));
		constructModel();
		assertEquals(1, (construct(FF, 1)));
		constructModel();
		assertEquals(2, (construct(FF, 2)));
		constructModel();
		assertEquals(3, (construct(FF,3))); 
	}
	
	@Test
	// test to see if successfully created a start state figure
	public void testCreatedStartStateSuccessfully(){
		constructModel();
		assertTrue("Start state not created successfully",createStartState(FF));
	}
	
	@Test
	// test to see if successfully created an end state figure
	public void testCreatedEndStateSuccessfully(){
		constructModel();
		assertTrue("End state not created successfully", createEndState(FF));
	}
	
	@Test
	// test to see if successfully created a transition figure
	public void testCreatedTransitionFigureSuccessfully(){
		constructModel();
		assertTrue("Transition figure not created successfully", createTransitionFigure(FF));
		
	}
	@Test
	// test to see if allowed a transition going into start state figure 
	public void testForNoTransitionIntoStartState(){
		constructModel();
		assertFalse("Allowed transition into start figure", allowedTransitionIntoStartState(FF));
	}
	
	@Test
	// test to see if allowed an end state figure to have a transition out
	 public void testNoTransitionOutFromEndState(){
		constructModel();
		assertFalse("Allowed Transition out of end state", allowedEndStateToHaveTransitionsOut());
	}
}

