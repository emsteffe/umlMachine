
package org.umlMachine.view.figures;

import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.layouter.LocatorLayouter;
import org.jhotdraw.draw.liner.ElbowLiner;
import org.jhotdraw.draw.locator.BezierPointLocator;
import java.awt.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.*;
import org.umlMachine.model.TransitionData;

/**
 * Lowell Johnson
 */
@SuppressWarnings("serial")
public class TransitionFigure extends LabeledLineConnectionFigure  {

	//(String action, StateData start, StateData end, String trigger,String event, String condition)
	private TransitionData data = new TransitionData();

	public TransitionFigure() {

		//Visual Attributes
		set(STROKE_COLOR, new Color(0,0,0));
		set(STROKE_WIDTH, 2d);
		set(END_DECORATION, new ArrowTip());
		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);	
		ElbowLiner elbow = new ElbowLiner();
		setLayouter(new LocatorLayouter());
		setLiner(elbow);


		TextFigure label1 = new TextFigure("Event");
		TextFigure label2 = new TextFigure("Action");
		ListFigure compartment = new ListFigure();

		compartment.add(label1);
		compartment.add(label2);

		add(compartment);


		//TODO Fix minor bug regarding perfectly straight transition lines (low proiority)
		LocatorLayouter.LAYOUT_LOCATOR.set(compartment, new BezierPointLocator(1,0));

	}


	//TODO All the the cases where this method returns false really should report why it's not a valid connection back to the user
	@Override
	public boolean canConnect(Connector start, Connector end) {
		if ((start.getOwner() instanceof StateFigure) && (end.getOwner() instanceof StateFigure)) {

			StateFigure sf = (StateFigure) start.getOwner();
			StateFigure ef = (StateFigure) end.getOwner();

			// Prevent transitions into START state
			if(ef.getType() == -1){
				return false;            	
			}

			//Prevent duplicate transitions
			for(TransitionData t : sf.getData().getTransitionsOut()){
				if(t.getEnd().equals(ef.getData())){
					return false;
				}					
			}	

			//Back and forth overlapping transitions
			for(TransitionData t : ef.getData().getTransitionsOut()){
				if(t.getEnd().equals(sf.getData())){
					//TODO find a way to make the transition lines not right on top of one another (low priority)
					//To just disallow this behavior: return false;
				}				
			}

			//Disallow ambiguous transitions
			for(TransitionData t : ef.getData().getTransitionsOut()){
				//Check transitions out
				if(t.getEvent().equals(this.data.getEvent()) ){
					return false;
				}

				//Check the start state's internal transitions
				for(String trigger : sf.getData().getInternalTriggers()){
					if(trigger.equals(t.getEvent())) return false;					
				}

				//Now we are going to check parent's stuff. 
				//The continue keyword is one of two useful things I learned in the 361 discussions.
				//The other one has to do with Space Jam.
				if(sf.getData().getParent() == null) continue;

				//Check against the start figure's parent's transitions out
				for(TransitionData parentTrans : sf.getData().getParent().getTransitionsOut() ){
					if(parentTrans.getEvent().equals(t.getEvent())) return false;					
				}

				//Check against the start figure's internal triggers
				for(String parentInternal : sf.getData().getParent().getInternalTriggers() ){
					if(parentInternal.equals(t.getEvent())) return false;
				}

			}

			/*
			 * Parent/children checking
			 */

			//Start state is a child
			if(sf.getData().getParent() != null ){ 

				//End state has a parent and it's not the same as state state's parent
				if((ef.getData().getParent() != null) && (ef.getData().getParent() != sf.getData().getParent())) return false;

				//Children cannot connect to parents (no nesting allowed)
				if(ef.getData().getFamilyType() == 1) return false;

				//TODO Confirm this design choice
				//Disallow children from connecting to other states outside of its family
				//If end state does have transitions coming into it, it must only have ONE and it must be the (soon to be) parent state
				if(ef.getData().getTransitionsIn().size() > 1) return false;
				if(ef.getData().getTransitionsIn().size() == 1 &&
						ef.getData().getTransitionsIn().get(0).getStart() != sf.getData().getParent()) return false;
			}

			//End state is a child
			if(ef.getData().getParent() != null){

				//Parents cannot connect to someone else's children. That's just not right.
				if( (sf.getData().getFamilyType() == 1) && (ef.getData().getParent() != sf.getData()) ) return false;

			}

			return true;
		}
		return false;
	}

	@Override
	public boolean canConnect(Connector start) {

		//Prevent transitions out of END state
		if( (start.getOwner() instanceof StateFigure)){
			//nested loop here to avoid casting errors, there is probably a more elegant solution
			if (( (StateFigure)start.getOwner() ).getType() != 1){
				return true;
			}
		}

		return false;
	}

	@Override
	protected void handleDisconnect(Connector start, Connector end) {
		StateFigure sf = (StateFigure) start.getOwner();

		sf.getData().removeTransitionOut(data);

	}

	@Override
	protected void handleConnect(Connector start, Connector end) {
		StateFigure sf = (StateFigure) start.getOwner();
		StateFigure ef = (StateFigure) end.getOwner();

		data.setStart(sf.getData());
		data.setEnd(ef.getData());

		ef.getData().addTransitionIn(data);
		sf.getData().addTransitionOut(data);

		//Convert end state to a sibling of the start state if applicable 
		//The state should be able to find the parent on it's own. *crosses fingers*
		if(  (sf.getData().getParent() != null) && (ef.getData().getParent() != sf.getData().getParent()) ){
			ef.makeChild();
		}	

	}

	public TransitionData getData(){
		return data;
	}

	@Override
	//I hope we aren't using this
	public TransitionFigure clone() {
		return (TransitionFigure) super.clone();
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
