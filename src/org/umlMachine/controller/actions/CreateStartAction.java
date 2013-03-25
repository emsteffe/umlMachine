package org.umlMachine.controller.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.jhotdraw.util.ResourceBundleUtil;

@SuppressWarnings("serial")
public class CreateStartAction extends AbstractAction {
	
	public final static String ID = new String("create.start");
	
	
	public CreateStartAction(){
		
		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.umlMachine.controller.actions.Labels");
        labels.configureAction(this, ID);
        
	}

	@Override
	public void actionPerformed(ActionEvent a) {		
		System.out.println("Create start state");
	}

}
