package org.umlMachine.controller.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import org.jhotdraw.app.action.app.ExitAction;
import org.jhotdraw.app.action.file.SaveFileAction;
import org.jhotdraw.util.ResourceBundleUtil;
import org.umlMachine.model.RefModel;

@SuppressWarnings("serial")
public class UMLMachineSaveAction extends AbstractAction {

	public final static String ID = new String("file.save");

	public UMLMachineSaveAction(){

		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.umlMachine.controller.actions.Labels");
		labels.configureAction(this, ID);

	}

	@Override
	public void actionPerformed(ActionEvent a) {	
		SaveFileAction action;
		if(RefModel.view.hasUnsavedChanges())
			action = new SaveFileAction(RefModel.app,RefModel.view,true );
		else 
			action = new SaveFileAction(RefModel.app,RefModel.view,false);
		action.actionPerformed(a);
	}


}
