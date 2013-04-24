
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
	private boolean isParent = false;
	private boolean isChild = false;
	private static RoundRectangleFigure figure;
	private StateData data = new StateData(isStart,isEnd,"state");
	
	
	/*
	 * Right Click Menu Items
	 * 
	 * Add Entry Action
	 * Add Internal Action
	 * Add Exit Action
	 * 
	 * Remove Action
	 * 
	 * Make Parent
	 * Make Child 
	 * Abandon Family
	 */
	
	@Override
	public Collection<Action> getActions(Point2D.Double p){
		LinkedList<Action> actions = new LinkedList<Action>();
		actions.add(new AddActionAction(this,"Add Entry Action"));
		actions.add(new AddActionAction(this,"Add Internal Action"));
		actions.add(new AddActionAction(this,"Add Exit Action"));
		actions.add(new MakeParentChildAction(this,"Make Parent"));
		actions.add(new MakeParentChildAction(this,"Make Child"));
		actions.add(new MakeParentChildAction(this,"Abandon Family"));
		return actions;
	}

	class AddActionAction extends AbstractAction{
		StateFigure fig;
		private String name;

		public AddActionAction(StateFigure fig, String name){
			super(name);
			this.name = name;
			this.fig = fig;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(isStart || isEnd){
				System.out.println("Can't add actions to this kind state.");
				return;
			}
			
			//go go gadget string parsing
			fig.addAction(name.substring(4,name.indexOf(' ', 4)) + "/Default Action" );			
		}

	}

	class MakeParentChildAction extends AbstractAction{
		StateFigure fig;
		String type;
		
		public MakeParentChildAction(StateFigure fig, String type){
			super(type);
			this.fig = fig;
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(type.equals("Make Parent")) fig.makeParent();
			if(type.equals("Make Child")) fig.makeChild();
			if(type.equals("Abandon Family")) fig.makeNormal();
		}
		
	}

	public StateFigure() {

		//The Figure itself
		super(figure);
		figure = new RoundRectangleFigure();
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
		internalActions.set(LAYOUT_INSETS, new Insets2D.Double(-1, 8, -1, 8));
		exitActions.set(LAYOUT_INSETS, new Insets2D.Double(0, 8, 4, 8));

		//entry
		addAction("Entry/action1");
		
		addAction("Entry/action5");
		removeAction("Entry/action5");
		
		//internal
		addAction("Internal/action2");
		
		addAction("Internal/action4");
		removeAction("Internal/action4");

		//exit
		addAction("Exit/action3");
		
		addAction("Exit/action6");
		removeAction("Exit/action6");

		
		/*
		 * Layout of a state figure
		 *
		name compartment
			text figure
		Separator
		actions compartment
			entry list figure
				text figure
				...
			internal list figure
				text figure
				...
			exit list figure
				text figure
				...
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
	
	public void shade(boolean on){
		//dummy stub
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
		willChange();
		addActionFigure(new TextFigure(a));
		changed();
	}
	
	public void removeAction(String a){
		data.removeAction(a);
		removeActionFigure(a);
		
	}
	
	private void addActionFigure(TextFigure action){
		ListFigure compartment = (ListFigure) getChild(2);
		String a = action.getText();
		
		if(a.startsWith("Entry/")){
			((ListFigure)compartment.getChild(0)).add(action);
			
		}else if(a.startsWith("Exit/")){
			((ListFigure)compartment.getChild(2)).add(action);
			
		}else{
			((ListFigure)compartment.getChild(1)).add(action);
		}

	}
	
		
	private void removeActionFigure(String a){
		
		ListFigure compartment = (ListFigure) getChild(2);
		ListFigure entryActions = (ListFigure) ((ListFigure)compartment.getChild(0));
		ListFigure internalActions = (ListFigure) ((ListFigure)compartment.getChild(1));
		ListFigure exitActions = (ListFigure) ((ListFigure)compartment.getChild(2));

		willChange();

		searchLoop: 
		if(a.startsWith("Entry/")){	
			int i = ((ListFigure)compartment.getChild(0)).getChildCount();
			while(i>0) {
				if(((TextFigure)entryActions.getChild(--i)).getText().equals(a)){
					entryActions.removeChild(i);
					break searchLoop;
				}
			}
		}else if(a.startsWith("Exit/")){
			int i = ((ListFigure)compartment.getChild(2)).getChildCount();
			while(i>0) {
				if(((TextFigure)exitActions.getChild(--i)).getText().equals(a)){
					exitActions.removeChild(i);
					break searchLoop;
				}
			}
		}else{
			int i = ((ListFigure)compartment.getChild(1)).getChildCount();
			while(i > 0) {
				if(((TextFigure)internalActions.getChild(--i)).getText().equals(a)){
					internalActions.removeChild(i);
					break searchLoop;
				}
			}
		}
		
		changed();
	}
	
	public List<String> getActions(){
		return data.getActions();
	}
	
	//TODO These methods need to be smarter
	public void makeParent(){
		isChild = false;
		isParent = true;
		highlight(false);
	}

	public void makeChild(){
		isParent = false;
		isChild  = true;
		highlight(false);
	}
	
	public void makeNormal(){
		isParent = false;
		isChild  = false;
		highlight(false);
	}
	
	
	/*
	 * Highlighting 
	 */

	public void highlight(boolean b){
		
		Color on = new Color(135,235,235);
		Color off = new Color(255,255,255);
		if(isParent) off = new Color(240,50,50);
		if(isChild) off = new Color(250,160,160);
			
		willChange();
		figure.setAttributeEnabled(FILL_COLOR, true);
		
		if(b){
			figure.set(FILL_COLOR, on);
		}else{
			figure.set(FILL_COLOR, off);
			this.setPresentationFigure(figure);
		}	
		

		this.setPresentationFigure(figure);
		figure.setAttributeEnabled(FILL_COLOR, false);
		this.
		changed();
	}

	///////////////////////////////////////
	//////////////////////////////////////

	/*
	 * Boring stuff :D
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

