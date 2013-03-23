package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FileHandler;
import org.umlMachine.controller.Simulator;

public class SimulateTool extends AbstractTool {

	/*
	 * This tool can handle both simulate diagram and simulate from file
	 */

	private boolean type;
	
	/*
	 * Create a new simulation tool, pass true for diagram and false for from file
	 */
	public SimulateTool(boolean type){
		this.type = type;
	}

	private void diagram(){
		
		System.out.println("Simulate from Diagram");
		Simulator.getInstance().fromDiagram();
	}

	private void fromFile(){
		
		System.out.println("Simulate from File");
		Simulator.getInstance().fromFile();

	}
	
	
	@Override
	public void activate(DrawingEditor editor) {
		super.activate(editor);
		
		if(type) diagram();
		if(!type) fromFile();
		
		super.deactivate(editor);
		fireToolDone();
	}


	@Override
	public void mouseDragged(MouseEvent e) {
	}


}
