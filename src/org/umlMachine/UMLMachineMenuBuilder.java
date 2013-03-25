package org.umlMachine;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JMenu;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultMenuBuilder;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.file.CloseFileAction;
import org.umlMachine.controller.actions.*;

import edu.umd.cs.findbugs.annotations.Nullable;

public class UMLMachineMenuBuilder extends DefaultMenuBuilder {


	// Remove menu items we don't need
	public void addUndoItems(JMenu m, Application app, @Nullable View v) {}
	public void addClipboardItems(JMenu m, Application app, @Nullable View v) {}
	public void addSelectionItems(JMenu m, Application app, @Nullable View v) {}
    public void addAboutItems(JMenu m, Application app, @Nullable View v) {}


	
	//Serialize
    //Close
	@Override
    public void addCloseFileItems(JMenu m, Application app, @Nullable View v) {
        ActionMap am = app.getActionMap(v);
        Action a;
      
        am.put(SerializeAction.ID,new SerializeAction()); 

        if (null != (a = am.get(SerializeAction.ID))) {
            m.add(a);
        }
        
        m.addSeparator();
        
        //TODO need to kill figure factory when program is closed
        if (null != (a = am.get(CloseFileAction.ID))) {
            m.add(a);
        }
        
    }
    
	
	//Save
	//Save as
    @Override
    public void addSaveFileItems(JMenu m, Application app, @Nullable View v) {
    	
        ActionMap am = app.getActionMap(v);
        Action a;
        
        am.put(UMLMachineSaveAction.ID,new UMLMachineSaveAction()); 
        am.put(UMLMachineSaveAsAction.ID,new UMLMachineSaveAsAction()); 
               
        if (null != (a = am.get(UMLMachineSaveAction.ID))) {
            m.add(a);
        }
        
        if (null != (a = am.get(UMLMachineSaveAsAction.ID))) {
            m.add(a);
        }
    }
    
    
    //Create State
    //Create Transition
    //Create Start
    //Create End
    @Override
    public void addOtherEditItems(JMenu m, Application app, @Nullable View v) {
    	
    	ActionMap am = app.getActionMap(v);
        Action a;
        
        am.put(CreateStateAction.ID,new CreateStateAction()); 
        am.put(CreateTransitionAction.ID,new CreateTransitionAction()); 
        am.put(CreateStartAction.ID,new CreateStartAction()); 
        am.put(CreateEndAction.ID,new CreateEndAction()); 

        
        if (null != (a = am.get(CreateStateAction.ID))) {
            m.add(a);
        }

        if (null != (a = am.get(CreateTransitionAction.ID))) {
            m.add(a);
        }
        
        if (null != (a = am.get(CreateStartAction.ID))) {
            m.add(a);
        }

        if (null != (a = am.get(CreateEndAction.ID))) {
            m.add(a);
        }
        
    }

    //Simulate from file
    //Simulate diagram
    //Animate diagram
    @Override
    public void addOtherWindowItems(JMenu m, Application app, @Nullable View v) {
    	
    	ActionMap am = app.getActionMap(v);
        Action a;
        
        am.put(SimulateFromFileAction.ID,new SimulateFromFileAction()); 
        am.put(SimulateDiagramAction.ID,new SimulateDiagramAction()); 
        am.put(AnimateAction.ID,new AnimateAction());

        
        if (null != (a = am.get(SimulateFromFileAction.ID))) {
            m.add(a);
        }

        if (null != (a = am.get(SimulateDiagramAction.ID))) {
            m.add(a);
        }
        
        if (null != (a = am.get(AnimateAction.ID))) {
            m.add(a);
        }
        
        
    }
    


}


