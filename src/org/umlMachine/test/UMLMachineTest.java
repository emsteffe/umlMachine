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
		FF = FigureFactory.getInstance();
		FigureFactory.clear();
		xml = new XMLController();
		transitionData = new TransitionData();
		stateFigure = FF.getState();
		stateData = new StateData();
		transitionFigure = new TransitionFigure();
	}
		
	// Model tests
		//StateData Tests
	@Test
	public void testDefaultStateData(){
		assertEquals(false, stateData.isStart());
		assertEquals(false, stateData.isEnd());
		assertEquals(null, stateData.getName());
		assertEquals(0, stateData.getTransitionsIn().size());
		assertEquals(0, stateData.getTransitionsOut().size());
		assertEquals(0, stateData.getActions().size());
	}
	@Test
	public void testDefaultStateFigureData(){
		assertEquals(false, stateFigure.getData().isStart());
		assertEquals(false, stateFigure.getData().isEnd());
		assertEquals("State 0", stateFigure.getData().getName());
		assertEquals(0, stateFigure.getData().getTransitionsIn().size());
		assertEquals(0, stateFigure.getData().getTransitionsOut().size());
		assertEquals(3, stateFigure.getData().getActions().size());
	}
	@Test
	public void testStateFigureDataSets(){
		stateFigure.getData().forceName("testName");
		assertEquals("testName", stateFigure.getData().getName());
		stateFigure.getData().forceName("name2");
		assertEquals("name2", stateFigure.getData().getName());
		stateFigure.getData().setStart(true);
		assertEquals(true, stateFigure.getData().isStart());
		stateFigure.getData().setEnd(true);
		assertEquals(true, stateFigure.getData().isEnd());
	}

	//TransitionData tests
	@Test
	public void testDefaultTransitionData(){
		assertEquals(0,transitionData.getActions().size());
		assertEquals("", transitionData.getEvent());
		assertEquals(null, transitionData.getEnd());
		assertEquals(null, transitionData.getStart());
	}
	
	@Test
	public void testTransitionDataSets(){
		transitionData.addAction("actionTest");
		assertEquals("actionTest", transitionData.getActions().get(0));
		transitionData.setEvent("eventTest");
		assertEquals("eventTest", transitionData.getEvent());
	}
	
	//figureTests
	@Test
	public void testDefaultStateFigure(){
		assertEquals(3, stateFigure.getActions().size());
	}
	
	@Test
	public void testXMLSerialization(){
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
		
		tData1.addAction("Transition 1");
		tData2.addAction("Transition 2");
		tData3.addAction("Transition 3");
		tData4.addAction("Transition 4");
		
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
				"<Transition action=\"Transition 1\" start=\"State 1\" end=\"State 2\"" +
				"event=\"null\"/>\n" +
				"<Transition action=\"Transition 3\" start=\"State 1\" end=\"State 3\"" +
				"event=\"null\"/>\n<Transition action=\"Transition 2\" start=\"State 2\" " +
				"end=\"State 3\" event=\"null\"/>\n<Transition " +
				"action=\"Transition 4\" start=\"State 2\" end=\"State 2\" event=\"null\" " +
				"/>\n</Transitions>\n</Serial>",xml.serialize(set));
	}
	
	@Test
	// test to see if the number of desired state figures are really created 
	public void testForNumbersOfFiguresCreatedInList(){
		FigureFactory.clear();
		assertEquals(0, (construct(FF, 0)));
		FigureFactory.clear();
		assertEquals(1, (construct(FF, 1)));
		FigureFactory.clear();
		assertEquals(2, (construct(FF, 2)));
		FigureFactory.clear();
		assertEquals(3, (construct(FF,3))); 
	}
	
	@Test
	// test to see if successfully created a start state figure
	public void testCreatedStartStateSuccessfully(){
		FigureFactory.clear();
		assertTrue("Start state not created successfully",createStartState(FF));
	}
	
	@Test
	// test to see if successfully created an end state figure
	public void testCreatedEndStateSuccessfully(){
		FigureFactory.clear();
		assertTrue("End state not created successfully", createEndState(FF));
	}
	
	@Test
	// test to see if successfully created a transition figure
	public void testCreatedTransitionFigureSuccessfully(){
		FigureFactory.clear();
		assertTrue("Transition figure not created successfully", createTransitionFigure(FF));
		
	}
	@Test
	// test to see if allowed a transition going into start state figure 
	public void testForNoTransitionIntoStartState(){
		FigureFactory.clear();
		assertFalse("Allowed transition into start figure", allowedTransitionIntoStartState(FF));
	}
	
	@Test
	// test to see if allowed an end state figure to have a transition out
	 public void testNoTransitionOutFromEndState(){
		FigureFactory.clear();
		assertTrue("Allowed Transition out of end state", allowedEndStateToHaveTransitionsOut());
	}
	
	@Test
	public void testSiblings(){
		FigureFactory.clear();
		assertTrue("These are not siblings", checkSiblings());
	}
}

