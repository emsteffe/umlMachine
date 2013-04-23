package org.umlMachine.controller.tools;

import java.awt.event.MouseEvent;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.tool.AbstractTool;

@SuppressWarnings("serial")
public class ValidateTool extends AbstractTool{

	public ValidateTool(){}
	public void mouseDragged(MouseEvent e){}

	@Override
	public void activate(DrawingEditor editor) {
		super.editor = editor;
		
		System.out.println("Validate Diragram");

	}

	@Override
	public void mouseEntered(MouseEvent evt) {		
		//This is questionable programming -- TODO fix it later
		super.deactivate(editor);
		fireToolDone();
	}
	





}
