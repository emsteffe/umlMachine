
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


			return true;
		}
		return false;
	}

	@Override
	public boolean canConnect(Connector start) {
		//Prevent transitions out of END state
		if( (start.getOwner() instanceof StateFigure)){
			//nested loops to avoid casting errors
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

		System.out.println("connecting " + sf.getName()+ " to "+ ef.getName());

		data.setStart(sf.getData());
		data.setEnd(ef.getData());
		sf.getData().addTransitionOut(data);

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
