//Used for integration testing 

package org.umlMachine.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

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
		assertFalse("Allowed transition into start figure", allowedTransitionIntoStartState(FF));
	}
	
	@Test
	// test to see if allowed an end state figure to have a transition out
	 public void testNoTransitionOutFromEndState(){
		constructModel();
		assertFalse("Allowed Transition out of end state", allowedEndStateToHaveTransitionsOut());
	}
}

