package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;

import org.jhotdraw.draw.tool.AbstractTool;

public class SimulateTool extends AbstractTool {

	/*
	 * This tool can handle both simulate diagram and simulate from file
	 * It may be more practical to split it up into different tools
	 */

	/*
	 * Create a new simulation tool, pass true for diagram and false for from file
	 */
	public SimulateTool(boolean type){

		if(type) {
			diagram();
		}else{
			fromFile();
		}
		
	}

	private void diagram(){
		//TODO
	}

	private void fromFile(){
		//TODO
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}
