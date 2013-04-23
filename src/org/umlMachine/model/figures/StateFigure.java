/*
 * @(#)TaskFigure.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.umlMachine.model.figures;

import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.draw.handle.MoveHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.layouter.VerticalLayouter;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.geom.*;
import static org.jhotdraw.draw.AttributeKeys.*;
import java.util.*;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.handle.BoundsOutlineHandle;
import org.jhotdraw.geom.Insets2D;
import org.jhotdraw.xml.*;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.model.StateData;

/**
 * TaskFigure.
 *
 * @author Werner Randelshofer.
 * @version $Id: TaskFigure.java 727 2011-01-09 13:23:59Z rawcoder $
 */
@SuppressWarnings("serial")
public class StateFigure extends GraphicalCompositeFigure {

	private HashSet<TransitionFigure> dependencies;
	private boolean isEnd = false;
	private boolean isStart = false;
	private StateData data = new StateData(isStart,isEnd,"state");

	/**
	 * This adapter is used, to connect a TextFigure with the name of
	 * the TaskFigure model.
	 */

	private static class NameAdapter extends FigureAdapter {

		public NameAdapter(StateFigure target) {
		}

		public void attributeChanged(FigureEvent e) {

		}
	}


	/*
	willChange();
	this.StateFigure.willChange();

	changed();
	this.StateFigue.changed();
	 */

	private ListFigure attributeCompartment = new ListFigure();
	private ListFigure actionCompartment = new ListFigure();

	@Override
	public Collection<Action> getActions(Point2D.Double p){
		LinkedList<Action> actions = new LinkedList<Action>();

		actions.add(new AddActionAction(this));

		return actions;

	}

	class AddActionAction extends AbstractAction{

		StateFigure fig;

		public AddActionAction(StateFigure fig){
			this.fig = fig;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			//fig.getActionCompartment().add(new TextFigure());

		}



	}


	public StateFigure() {

		super(new RectangleFigure());
		setLayouter(new VerticalLayouter());

		//Compartments
		nameCompartment = new ListFigure();
		attributeCompartment = new ListFigure();
		actionCompartment = new ListFigure();

		//Figures
		//name
		TextFigure nameFigure;
		nameCompartment.add(nameFigure = new TextFigure("State "+ FigureFactory.getInstance().getNumStates() ));
		nameFigure.set(LAYOUT_INSETS, new Insets2D.Double(4, 8, 4, 8));
		nameFigure.set(FONT_BOLD, true);
		nameFigure.setAttributeEnabled(FONT_BOLD, false);

		data.forceName("State "+ FigureFactory.getInstance().getNumStates());

		//actions
		ListFigure actions = new ListFigure();
		actionCompartment.add(actions);

		//sample data
		//TODO Make this editable from user.
		//TODO Add right click add action option 
		actions.add(new TextFigure("Action 1"));
		data.addAction("Action 1");
		actions.add(new TextFigure("Action 2"));
		data.addAction("Action 2");


		//attributes
		ListFigure attributes = new ListFigure();
		attributeCompartment.add(attributes);

		//sample data
		//TODO Make this editable from user
		//TODO Add right click add attribute option
		attributes.add(new TextFigure("Attribute 1"));
		data.addAction("Attribute 1");
		attributes.add(new TextFigure("Attribute 2"));
		data.addAction("Attribute 2");


		SeparatorLineFigure separator = new SeparatorLineFigure();

		//Order
		add(nameCompartment);
		add(separator);
		add(actionCompartment);
		add(separator);
		add(attributeCompartment);

		//Assignments
		dependencies = new HashSet<TransitionFigure>();
		nameFigure.addFigureListener(new NameAdapter(this));


		/*
		 * Layout of a state figure
		 * 

		name compartment
			text figure

		Separator

		actions compartment
			list figure
				text figure
				text figure
				.....

		Separator

		attributes compartment
			list figure
				text figure
				text figure
				.....
		 */

	}
	private ImageFigure imageFigure = null;
	private ListFigure nameCompartment = new ListFigure();
	public StateFigure(boolean type){ // true->start , false->end


		super(new EllipseFigure());

		imageFigure = new ImageFigure();
		imageFigure.set(STROKE_COLOR, new Color(255,255,255));
		imageFigure.setAttributeEnabled(STROKE_COLOR, false);

		// Name figure is not visible, but must exist for the factory
		nameCompartment = new ListFigure();
		add(nameCompartment);
		nameCompartment.setVisible(false);
		//Creates state, does different things for start and end states
		File file;
		if(type){

			//TODO Need to be able to ask the factory if a figure with this name exists to prevent 2 start states
			nameCompartment.add(new TextFigure("StartState[jh72%3tr#FHuu]")); //Unique name
			file = new File("src/org/umlMachine/view/images/start.png");
			isStart = true;
			data.setStart(true);
		}else{

			nameCompartment.add(new TextFigure("End State"+ FigureFactory.getInstance().getNumStates() ));
			file = new File("src/org/umlMachine/view/images/end.png");
			isEnd = true;
			data.setEnd(true);

		}

		try {
			imageFigure.loadImage(file);
		} catch (IOException e) {
		}


		data.forceName("State "+ FigureFactory.getInstance().getNumStates());

		dependencies = new HashSet<TransitionFigure>();
		super.setPresentationFigure(imageFigure);

		/*
		 * Layout of a start/end figure
		 * 

		name compartment
			text figure [hidden]

		 */

	}

	public int getType(){
		if (isStart) return -1;
		if (isEnd) return 1;
		return 0;		
	}

	@Override
	public Collection<Handle> createHandles(int detailLevel) {
		java.util.List<Handle> handles = new LinkedList<Handle>();
		switch (detailLevel) {
		case -1:
			handles.add(new BoundsOutlineHandle(getPresentationFigure(), false, true));
			break;
		case 0:
			handles.add(new MoveHandle(this, RelativeLocator.northWest()));
			handles.add(new MoveHandle(this, RelativeLocator.northEast()));
			handles.add(new MoveHandle(this, RelativeLocator.southWest()));
			handles.add(new MoveHandle(this, RelativeLocator.southEast()));

			//ConnectorHandle ch;
			if(!isEnd){ 
				//TODO: make transitions created from this handle look the same as the transition tool (low priority) 
				//handles.add(ch = new ConnectorHandle(new LocatorConnector(this, RelativeLocator.east()), new TransitionFigure()));
				//ch.setToolTipText("Drag the connector to another state.");
			}

			break;
		}
		return handles;

	}

	public void setName(String newValue) {
		getNameFigure().setText(newValue);
		data.setName(newValue);
	}

	public String getName() {
		return getNameFigure().getText();
	}

	private TextFigure getNameFigure() {
		return (TextFigure)((ListFigure) getChild(0)).getChild(0);
	}

	public void addAction(String action){
		//TODO
		//Actions live in ((ListFigure)getChild(2).getChild(0))

	}

	public List<String> getActions(){
		return new ArrayList<String>();
	}

	public void addAttribute(String attribute){
		//TODO
		//Attributes live in ((ListFigure)getChild(4).getChild(0))

	}

	@Override
	public StateFigure clone() {
		StateFigure that = (StateFigure) super.clone();
		that.dependencies = new HashSet<TransitionFigure>();
		that.getNameFigure().addFigureListener(new NameAdapter(that));
		//that.getDurationFigure().addFigureListener(new DurationAdapter(that));
		//that.updateStartTime();
		that.getData().forceName(that.getName());
		return that;
	}

	@Override
	public void read(DOMInput in) throws IOException {
		double x = in.getAttribute("x", 0d);
		double y = in.getAttribute("y", 0d);
		double w = in.getAttribute("w", 0d);
		double h = in.getAttribute("h", 0d);
		setBounds(new Point2D.Double(x, y), new Point2D.Double(x + w, y + h));
		readAttributes(in);
		in.openElement("model");
		in.openElement("name");
		setName((String) in.readObject());
		in.closeElement();
		in.openElement("duration");
		//setDuration((Integer) in.readObject());
		in.closeElement();
		in.openElement("data");
		data.setName(in.getAttribute("name", ""));
		if(in.getAttribute("type", "").equals("end"))
			data.setEnd(true);
		else if(in.getAttribute("type", "").equals("start"))
			data.setStart(true);
		in.openElement("actions");
		int actionCount = in.getElementCount();
		for(int i= 0; i!= actionCount; i++){
			in.openElement(i);//open action
			data.addAction(in.getText());
			in.closeElement();
		}
		in.closeElement();
		in.closeElement();
		in.closeElement();
		if(data.isStart()){
			//make it a start date
		}else if(data.isEnd()){
			//make it an end state
		}
	}

	@Override
	public void write(DOMOutput out) throws IOException {
		Rectangle2D.Double r = getBounds();
		out.addAttribute("x", r.x);
		out.addAttribute("y", r.y);
		writeAttributes(out);
		out.openElement("model");
		out.openElement("name");
		out.writeObject(getName());
		out.closeElement();
		out.openElement("duration");
		//out.writeObject(getDuration());
		out.closeElement();
		out.openElement("data");
		out.addAttribute("name", data.getName());
		if(data.isEnd())
			out.addAttribute("type", "end");
		else if(data.isStart())
			out.addAttribute("type", "start");
		else
			out.addAttribute("type", "nor");
		out.openElement("actions");
		for(String action : data.getActions()){
			out.openElement("action");
			out.addText(action);
			out.closeElement();
		}
		out.closeElement();
		out.closeElement();
		out.closeElement();
	}

	@Override
	public int getLayer() {
		return 0;
	}

	public Set<TransitionFigure> getDependencies() {
		return Collections.unmodifiableSet(dependencies);
	}

	public void addDependency(TransitionFigure f) {
		dependencies.add(f);

	}

	public void removeDependency(TransitionFigure f) {
		dependencies.remove(f);

	}

	public void highlight(boolean b){
		
		willChange();
		
		changed();

	}

	/**
	 * Returns dependent PertTasks which are directly connected via a
	 * PertDependency to this TaskFigure.
	 */
	public List<StateFigure> getSuccessors() {
		LinkedList<StateFigure> list = new LinkedList<StateFigure>();
		for (TransitionFigure c : getDependencies()) {
			if (c.getStartFigure() == this) {
				list.add((StateFigure) c.getEndFigure());
			}

		}
		return list;
	}

	/**
	 * Returns predecessor PertTasks which are directly connected via a
	 * PertDependency to this TaskFigure.
	 */
	public List<StateFigure> getPredecessors() {
		LinkedList<StateFigure> list = new LinkedList<StateFigure>();
		for (TransitionFigure c : getDependencies()) {
			if (c.getEndFigure() == this) {
				list.add((StateFigure) c.getStartFigure());
			}

		}
		return list;
	}

	/**
	 * Returns true, if the current task is a direct or
	 * indirect dependent of the specified task.
	 * If the dependency is cyclic, then this method returns true
	 * if <code>this</code> is passed as a parameter and for every other
	 * task in the cycle.
	 */
	public boolean isDependentOf(StateFigure t) {
		if (this == t) {
			return true;
		}

		for (StateFigure pre : getPredecessors()) {
			if (pre.isDependentOf(t)) {
				return true;
			}

		}
		return false;
	}



	public StateData getData() {
		return data;
	}

	// @Override
	//public String toString() {return "TaskFigure#" + hashCode() + " " + getName() + " " + getDuration() + " " + getStartTime();}

}

