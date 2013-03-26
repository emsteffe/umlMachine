//Used for integration testing 

package org.umlMachine.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.umlMachine.figures.*;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.controller.XMLController;

public class UMLMachineTest extends TestDetails {

	private FigureFactory FF;
	private XMLController xml;
	
	@Before
	public void constructModel(){
		FF = new FigureFactory();
		xml = new XMLController();
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
		assertFalse("Allowed transition into start figure", AllowedTransitionIntoStartState(FF));
	}
	
	/*@Test
	/* test to see if allowed a transition to have no transition out figure 
	
	public void testForMissingTransitionEndState(){
		constructModel();
		asserFalse("Transition is missing an end state figure", AllowedTranstionToHaveNoEndState(FF));
	}
	
	@Test
	/* test to see if allowed an end state figure to have a transition out
	 

	public void testNoTransitionOutFromEndState(){
		
	}
	
	@Test
	public void testStateAddAction(){
	
	}
	
	@Test	
	public void testStateAddTransition(){
	
	}
	
	@Test
	public void testStateTransitionToSelf(){
	
	}
	@Test
	public void testStateDefaultXML(){
		String expected = "<State name=null type=norm>\n"+
							"\t<Actions>\n"+
							"\t</Actions>\n"+
							"\t<TransitionsIn>\n"+
							"\t</TransitionsIn>\n"+
							"\t<TransitionsOut>\n"+
							"\t</TransitionsOut>\n"+
							"</State>";
		
		assertEquals(expected,defaultState.toXML());
	}
	
	@Test
	public void testTwoStateXML(){
		String expected = 	"<State name=state1 type=start>\n"+
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
		
		TransitionData tran1 = new TransitionData("trans1", defaultState, defaultState, "refself", "event1", "[con1]");
		TransitionData tran2 = new TransitionData("trans2", defaultState, state2, "continue", "event2",null);
		
		defaultState.addAction("action1");
		state2.addAction("action2");
		state2.addAction("action3");
		
		defaultState.setName("state1");
		state2.setName("state2");
		
		defaultState.setStart(true);
		state2.setEnd(true);
		
		defaultState.addTransitionOut(tran1);
		defaultState.addTransitionOut(tran2);
		
		List<StateData> testList = new ArrayList<StateData>();
		
		testList.add(defaultState);
		testList.add(state2);
		
		assertEquals(expected, xml.serialize(testList));
	}
	*/
}

