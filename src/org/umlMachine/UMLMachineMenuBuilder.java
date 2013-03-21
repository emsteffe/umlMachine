package org.umlMachine;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JMenu;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultMenuBuilder;
import org.jhotdraw.app.View;
import org.umlMachine.controller.actions.*;

import edu.umd.cs.findbugs.annotations.Nullable;

public class UMLMachineMenuBuilder extends DefaultMenuBuilder {


	// Remove menu items we don't need
	public void addUndoItems(JMenu m, Application app, @Nullable View v) {}
	public void addClipboardItems(JMenu m, Application app, @Nullable View v) {}

	
	//Serialize
	@Override
    public void addExportFileItems(JMenu m, Application app, @Nullable View v) {
		
        ActionMap am = app.getActionMap(v);
        Action a;
        
        
        if (null != (a = am.get(SerializeAction.ID))) {
            m.add(a);
        }
    }
    
	
	//Save
	//Save as
    @Override
    public void addSaveFileItems(JMenu m, Application app, @Nullable View v) {
    	
        ActionMap am = app.getActionMap(v);
        Action a;
        
        
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
    public void addOtherEditItems(JMenu m, Application app, @Nullable View v) {
    	
    	ActionMap am = app.getActionMap(v);
        Action a;
        
        
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
    public void addOtherViewItems(JMenu m, Application app, @Nullable View v) {
    	
    	ActionMap am = app.getActionMap(v);
        Action a;
        
        
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


