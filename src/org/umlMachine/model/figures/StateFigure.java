
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
 * Lowell Johnson
 */
@SuppressWarnings("serial")
public class StateFigure extends GraphicalCompositeFigure {

	private boolean isEnd = false;
	private boolean isStart = false;
	private StateData data = new StateData(isStart,isEnd,"state");


	private static class NameAdapter extends FigureAdapter {
		public NameAdapter(StateFigure target) {}
		public void attributeChanged(FigureEvent e) {}
	}


	/*
	 * From discussion
	 * TODO Make this work
	 */

	/*
	willChange();
	this.StateFigure.willChange();

	changed();
	this.StateFigue.changed();
	 */

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

	/*
	 * End From Discussion
	 */

	public StateFigure() {

		//The Figure itself
		super(new RoundRectangleFigure());
		setLayouter(new VerticalLayouter());

		//Compartments
		ListFigure nameCompartment = new ListFigure();
		ListFigure actionCompartment = new ListFigure();
		SeparatorLineFigure separator = new SeparatorLineFigure();
		add(nameCompartment);
		add(separator);
		add(actionCompartment);

		//Internals
		//Name
		TextFigure nameFigure;
		nameCompartment.add(nameFigure = new TextFigure("State "+ (int)(FigureFactory.getInstance().getNumStates() + 1) ));
		nameFigure.set(LAYOUT_INSETS, new Insets2D.Double(4, 8, 4, 8));
		nameFigure.set(FONT_BOLD, true);
		nameFigure.setAttributeEnabled(FONT_BOLD, false);

		//Actions
		ListFigure entryActions = new ListFigure();
		ListFigure internalActions = new ListFigure();
		ListFigure exitActions = new ListFigure();
		actionCompartment.add(entryActions);
		actionCompartment.add(internalActions);
		actionCompartment.add(exitActions);
		entryActions.set(LAYOUT_INSETS, new Insets2D.Double(4, 8, 0, 8));
		internalActions.set(LAYOUT_INSETS, new Insets2D.Double(1, 8, 1, 8));
		exitActions.set(LAYOUT_INSETS, new Insets2D.Double(0, 8, 4, 8));

		//entry

		//TODO
		entryActions.add(new TextFigure("Entry/action"));

		//internal

		//TODO
		internalActions.add(new TextFigure("Event/action"));

		//exit

		//TODO
		exitActions.add(new TextFigure("Exit/action"));


		//I'm sure this is important
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

		 */

	}

	public StateFigure(boolean type){ // true=start , false=end

		//Figure itself
		super(new EllipseFigure());
		ImageFigure imageFigure = new ImageFigure();
		imageFigure.set(STROKE_COLOR, new Color(255,255,255));
		imageFigure.setAttributeEnabled(STROKE_COLOR, false);
		super.setPresentationFigure(imageFigure);

		// Name figure is not visible, but must exist for the factory
		ListFigure nameCompartment = new ListFigure();
		add(nameCompartment);
		nameCompartment.setVisible(false);

		//Creates state, does different things for start and end states
		File file;
		nameCompartment.add(new TextFigure(""));
		data.forceName("State "+ FigureFactory.getInstance().getNumStates());

		if(type){
			file = new File("src/org/umlMachine/view/images/start.png");
			isStart = true;
			data.setStart(true);
		}else{
			file = new File("src/org/umlMachine/view/images/end.png");
			isEnd = true;
			data.setEnd(true);
		}

		try {
			imageFigure.loadImage(file);
		} catch (IOException e) {}

		/*
		 * Layout of a start/end figure
		 * 
		 * 
		name compartment [hidden]
			text figure 
		 */

	}

	/*
	 * Name get/set
	 */

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

	/*
	 * Actions get/set
	 */

	public void addAction(String a){
		data.addAction(a);
		//TODO Add it to the figure
	}

	public List<String> getActions(){
		return data.getActions();
	}

	/*
	 * Highlighting 
	 */

	public void highlight(boolean b){
		willChange();
		//TODO		
		changed();

	}

	///////////////////////////////////////
	//////////////////////////////////////

	/*
	 * Boring stuff 
	 */

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

	@Override
	public int getLayer() {
		return 0;
	}	

	public StateData getData() {
		return data;
	}

	@Override
	//I really hope JHotDraw is not not using this thing. TODO maybe
	public StateFigure clone() {
		StateFigure that = (StateFigure) super.clone();
		that.getNameFigure().addFigureListener(new NameAdapter(that));
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

}

