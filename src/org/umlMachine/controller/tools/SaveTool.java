package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;
import org.umlMachine.controller.FileHandler;

@SuppressWarnings("serial")
public class SaveTool extends AbstractTool {
	
	public void mouseDragged(MouseEvent e) {}
	
	/*
	 * This tool can  handle Save, SaveAs, and Serialize functions
	 */
	
	private int x;

	public SaveTool(int x){
		this.x = x;
	}
	
	private void save(){
		FileHandler.getInstance().exportDiagram();
		System.out.println("Save");

	}
	
	private void saveAs(){
		FileHandler.getInstance().exportDiagram();
		System.out.println("Save As");

		
	}
	
	private void serialize(){
		FileHandler.getInstance().serialize();
		System.out.println("Serialize");
		
	}
		
	
	@Override
	public void activate(DrawingEditor editor) {
		super.activate(editor);
		
		if(x == 0) save();
		if(x < 0) saveAs();
		if(x > 0) serialize();
		
	}	
	
	@Override
	public void mouseEntered(MouseEvent evt) {		
		//This is questionable programming -- TODO fix it later
		super.deactivate(editor);
		fireToolDone();
	}
}
