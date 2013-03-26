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
import org.jhotdraw.draw.layouter.HorizontalLayouter;
import org.jhotdraw.draw.tool.TextAreaCreationTool;

import java.awt.*;
import java.util.ArrayList;

import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.geom.BezierPath.Node;
import org.umlMachine.model.StateData;
import org.umlMachine.model.TransitionData;

/**
 * DependencyFigure.
 *
 * @author Werner Randelshofer.
 * @version $Id: DependencyFigure.java 718 2010-11-21 17:49:53Z rawcoder $
 */
@SuppressWarnings("serial")
public class TransitionFigure extends LineConnectionFigure {

	
	//(String action, StateData start, StateData end, String trigger,String event, String condition)
	private TransitionData data;
	
	protected GraphicalCompositeFigure textArea = new GraphicalCompositeFigure();
	
	protected ArrayList<Figure> children = new ArrayList<Figure>();
	
	/** Creates a new instance. */
	public TransitionFigure() {

		ListFigure list = new ListFigure();
		
		//TODO Make this real data from TransitionData
		list.add(new TextFigure("Event"));
		list.add(new TextFigure(" / "));
		list.add(new TextFigure("Action"));
		list.add(new TextFigure(" / "));
		list.add(new TextFigure("Condition"));
		
		textArea.add(list);
		
		textArea.setLayouter(new HorizontalLayouter());
		
		TextAreaFigure textbox = new TextAreaFigure("Text");
		
		
		
		//TODO find a way to to add textArea to the screen? 
		

		//TODO check if start is end, change from straight line to curved.
		
		
		//Visual Attributes
		set(STROKE_COLOR, new Color(0,0,0));
		set(STROKE_WIDTH, 2d);
		set(END_DECORATION, new ArrowTip());
		setAttributeEnabled(END_DECORATION, false);
		setAttributeEnabled(START_DECORATION, false);
		setAttributeEnabled(STROKE_DASHES, false);
		setAttributeEnabled(FONT_ITALIC, false);
		setAttributeEnabled(FONT_UNDERLINE, false);
	}
	


	/**
	 * Checks if two figures can be connected. Implement this method
	 * to constrain the allowed connections between figures.
	 */
	@Override
	public boolean canConnect(Connector start, Connector end) {
		if ((start.getOwner() instanceof StateFigure)
				&& (end.getOwner() instanceof StateFigure)) {

			StateFigure sf = (StateFigure) start.getOwner();
			StateFigure ef = (StateFigure) end.getOwner();

			// Prevent transitions into START state
			if(ef.getType() == -1){
				return false;            	
			}

			// Disallow multiple connections to same dependent
			if (ef.getPredecessors().contains(sf)) {
				return false;
			}

			// Disallow cyclic connections
			return !sf.isDependentOf(ef);
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
		
		data.setEnd(null);
		data.setStart(null);
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
		
		data.setEnd(ef.getData());
		data.setStart(sf.getData());
	}

	@Override
	public TransitionFigure clone() {
		TransitionFigure that = (TransitionFigure) super.clone();

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
