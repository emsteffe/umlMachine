package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.Main;
import org.umlMachine.UMLMachineApplicationModel;
import org.umlMachine.UMLMachineView;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.controller.FileHandler;

public class SaveTool extends AbstractTool {
	
	
	/*
	 * This tool can  handle Save, SaveAs, and Serialize functions
	 */
	
	private int x;

	public SaveTool(int x){
		this.x = x;
	}
	
	private void save(){
		
		System.out.println("Save");
		FileHandler.getInstance().save();
	}
	
	private void saveAs(){
		
		System.out.println("Save As");
		FileHandler.getInstance().saveAs();
		
	}
	
	private void serialize(){
		
		System.out.println("Serialize");
		FileHandler.getInstance().serialize();
	}
	
	
	
	@Override
	public void activate(DrawingEditor editor) {
		super.activate(editor);
		
		if(x == 0) save();
		if(x < 0) saveAs();
		if(x > 0) serialize();
		
		super.deactivate(editor);
		fireToolDone();
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {		
	}

}
