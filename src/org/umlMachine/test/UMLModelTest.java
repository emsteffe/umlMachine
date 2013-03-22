package org.umlMachine.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.umlMachine.figures.*;
import org.umlMachine.controller.XMLController;

public class UMLModelTest extends TestDetails {

	private ArrayList<StateFigure> listFigures;
	private XMLController xml;
	
	@Before
	public void constructModel(){
		listFigures = new ArrayList<StateFigure>();
		xml = new XMLController();
	}
	
	@Test
	/* test to see if the number of desired state figures are really created 
	 */
	
	public void testForNumbersOfFiguresCreatedInList(){
		assertEquals(0, (construct(listFigures, 0)));
		constructModel();
		assertEquals(1, (construct(listFigures, 1)));
		constructModel();
		assertEquals(2, (construct(listFigures, 2)));
		constructModel();
		assertEquals(3, (construct(listFigures,3))); 
	}

	@Test
	/* test to see if we successfully created a start state figure
	 */
	
	public void createdStartStateSuccessfully(){
		assertTrue("Start state not created successfully",createStartState());
	}
	
	@Test
	/* test to see if we successfully created an end state figure
	 */
	
	public void createdEndStateSuccessfully(){
		assertTrue("End state not created successfully", createEndState());
	}
	
	@Test
	/* test to see if we created a Transition figure successfully
	 */
	public void createdTransitionFigureSuccessfuly(){
		assertTrue("TransitionFigure not created successfully", createTransitionFigure());
	}
	
	@Test
	/* test to see if we allowed a transition going into start state figure
	 */
	
	public void testForNoTransitionIntoStartState(){
		assertFalse("Allowed transition into start figure", allowedTransitionIntoStartState());
	}
	
	@Test
	/* test to see if we allowed a transition to have no transition out figure
	 */
	
	public void testForMissingTransitionEndStateFigure(){
		constructModel();
		assertFalse("Transition is missing an end state figure", allowedTranstionToHaveNoEndStateFigure(listFigures));
	}
	
	@Test
	/* test to see to see if we allowed an end state figure to have a transition out
	 */

	public void testNoTransitionOutFromEndState(){
		assertFalse("End state has a transition out", allowedEndStateToHaveTransitionsOut(listFigures));
		
	}
	
	/* test transition to self */
	
	@Test
	public void testStateTransitionToSelf(){
		
	}
	
	@Test
	public void testStateDefaultXML(){
		assertEquals(expected1(), createDefaultState());
	}
	
	@Test
	public void testTwoStateXML(){
		assertEquals(expected2(), serializeData(xml));
	}
}
