package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;

import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.Main;
import org.umlMachine.UMLMachineApplicationModel;
import org.umlMachine.UMLMachineView;

public class SaveTool extends AbstractTool {
	
	
	/*
	 * This tool can  handle Save, SaveAs, and Serialize functions
	 * It may be more practical to split it up into different tools
	 */

	public SaveTool(int x){
		
		if(x == 0) save();
		if(x < 0) saveAs();
		if(x > 0) serialize();

	}
	
	private void save(){
		//TODO
	}
	
	private void saveAs(){
		//TODO
	}
	
	private void serialize(){
		//TODO
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
