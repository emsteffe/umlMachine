package org.umlMachine.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import org.umlMachine.model.StateData;
import org.umlMachine.model.TransitionData;
import org.umlMachine.controller.XMLController;

public class UMLModelTest {

	
	private StateData defaultState;
	private TransitionData defaultTransition;
	StateData state2;
	private XMLController xml;
	
	@Before
	public void constructModel(){
		defaultState = new StateData();
		defaultTransition = new TransitionData();
		state2 = new StateData();
		xml = new XMLController();
	}
	
	
	@Test 
	public void testBaseStateActions(){
		assertEquals(0,defaultState.getActions().size());
	}
	
	@Test
	public void testBaseStateTransitionsIn(){
		assertEquals(0,defaultState.getTransitionsIn().size());
	}
	
	@Test
	public void testBaseStateTransitionsOut(){
		assertEquals(0,defaultState.getTransitionsIn().size());
	}
	
	@Test
	public void testBaseTransition(){
		assertEquals(null, defaultTransition.getAction());
	}
	
	@Test
	public void testBaseTransitionStart(){
		assertEquals(null, defaultTransition.getStart());
	}
	
	@Test
	public void testBaseTransitionEnd(){
		assertEquals(null, defaultTransition.getEnd());
	}
	
	@Test
	public void testBaseTransitionEvent(){
		assertEquals(null, defaultTransition.getEvent());
	}
	
	@Test
	public void testBaseTransitionTrigger(){
		assertEquals(null, defaultTransition.getTrigger());
	}
	
	@Test
	public void testStateAddAction(){
		String expected = "TestAction1";
		defaultState.addAction("TestAction1");
		
		assertEquals(expected , defaultState.getActions().get(0));
		
		assertEquals(1,defaultState.getActions().size());
	}
	
	@Test	
	public void testStateAddTransition(){
		defaultTransition.setStart(defaultState);
		defaultTransition.setEnd(state2);
		defaultState.addTransitionOut(defaultTransition);
		
		assertEquals(1,defaultState.getTransitionsOut().size());
		assertEquals(1,state2.getTransitionsIn().size());
		assertEquals(state2.getTransitionsIn().get(0),defaultState.getTransitionsOut().get(0));
	}
	
	@Test
	public void testStateTransitionToSelf(){
		TransitionData tran1 = new TransitionData();
		tran1.setStart(state2);
		tran1.setEnd(state2);
		state2.addTransitionOut(tran1);
		
		assertEquals(1,state2.getTransitionsIn().size());
		assertEquals(1,state2.getTransitionsOut().size());
		assertEquals(state2.getTransitionsIn().get(0),state2.getTransitionsOut().get(0));
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
}
