/*
 * @(#)DependencyFigure.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.umlMachine.figures;

import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.liner.ElbowLiner;
import java.awt.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.*;
import org.umlMachine.model.RefModel;
import org.umlMachine.model.TransitionData;

/**
 * DependencyFigure.
 *
 * @author Werner Randelshofer.
 * @version $Id: DependencyFigure.java 718 2010-11-21 17:49:53Z rawcoder $
 */
@SuppressWarnings("serial")
public class TransitionFigure extends LineConnectionFigure  {

	//(String action, StateData start, StateData end, String trigger,String event, String condition)
	private TransitionData data = new TransitionData();

	
	/** Creates a new instance. */
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
		setLiner(elbow);
		
	}



	/**
	 * Checks if two figures can be connected. Implement this method
	 * to constrain the allowed connections between figures.
	 */
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
			
			//Prevent back and forth overlapping transitions
			for(TransitionData t : ef.getData().getTransitionsOut()){
				if(t.getEnd().equals(sf.getData())){
					return false;
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

	/**
	 * Handles the disconnection of a connection.
	 * Override this method to handle this event.
	 */
	@Override
	protected void handleDisconnect(Connector start, Connector end) {
		StateFigure sf = (StateFigure) start.getOwner();
		StateFigure ef = (StateFigure) end.getOwner();

		sf.removeDependency(this);
		ef.removeDependency(this);

		sf.getData().removeTransitionOut(data);

	}

	/**
	 * Handles the connection of a connection.
	 * Override this method to handle this event.
	 */
	@Override
	protected void handleConnect(Connector start, Connector end) {
		StateFigure sf = (StateFigure) start.getOwner();
		StateFigure ef = (StateFigure) end.getOwner();


		sf.addDependency(this);
		ef.addDependency(this);

		System.out.println("connecting " + sf.getName()+ " to "+ ef.getName());

		data.setStart(sf.getData());
		data.setEnd(ef.getData());
		sf.getData().addTransitionOut(data);

	}

	public TransitionData getData(){
		return data;
	}

	@Override
	public TransitionFigure clone() {
		TransitionFigure that = new TransitionFigure();

		return that;
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void removeNotify(Drawing d) {
		if (getStartFigure() != null) {
			((StateFigure) getStartFigure()).removeDependency(this);
		}
		if (getEndFigure() != null) {
			((StateFigure) getEndFigure()).removeDependency(this);
		}
		super.removeNotify(d);
	}
}
