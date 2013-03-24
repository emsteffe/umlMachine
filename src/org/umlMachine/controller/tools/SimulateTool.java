package org.umlMachine.controller.tools;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FileHandler;
import org.umlMachine.controller.Simulator;

public class SimulateTool extends AbstractTool {

	public void mouseDragged(MouseEvent e) {}

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

	}

	@Override
	public void mouseEntered(MouseEvent evt) {		
		//This is questionable programming -- TODO fix it later
		super.deactivate(editor);
		fireToolDone();
	}

}
