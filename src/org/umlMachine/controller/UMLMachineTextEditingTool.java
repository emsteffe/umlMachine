
package org.umlMachine.controller;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.text.FloatingTextField;
import org.jhotdraw.draw.tool.TextEditingTool;

import java.awt.*;
import java.awt.event.*;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.util.ResourceBundleUtil;

@SuppressWarnings("serial")
public class UMLMachineTextEditingTool extends TextEditingTool {
	@Nullable private FloatingTextField textField;
	@Nullable private TextHolderFigure typingTarget;
	private int context = 0;

	public UMLMachineTextEditingTool(TextHolderFigure typingTarget) {
		this(typingTarget,0);
	}

	/*     
	 * Context lets the text figure itself know where it lives
	 * so it can enforce valid text conventions
	 * 0 = Default, no validation
	 * 1 = State action, exactly one '/' required
	 * 2 = Transition, exactly zero '/' required
	 */
	public UMLMachineTextEditingTool(TextHolderFigure typingTarget, int context){
		super(typingTarget);
		this.typingTarget = typingTarget;
		this.context = context;
	}

	@Override
	public void deactivate(DrawingEditor editor) {
		endEdit();
		super.deactivate(editor);
	}

	/**
	 * If the pressed figure is a TextHolderFigure it can be edited.
	 */
	@Override
	public void mousePressed(MouseEvent e) {    	
		if (typingTarget != null) {
			beginEdit(typingTarget);
			updateCursor(getView(), e.getPoint());
		}
	}

	protected void beginEdit(TextHolderFigure textHolder) {

		if (textField == null) {
			textField = new FloatingTextField();
			textField.addActionListener(this);
		}

		if (textHolder != typingTarget && typingTarget != null) {
			endEdit();
		}

		textField.createOverlay(getView(), textHolder);
		textField.requestFocus();
		typingTarget = textHolder;
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
	}

	protected void endEdit() {
		if (typingTarget != null) {
			typingTarget.willChange();

			TextHolderFigure editedFigure = typingTarget;
			String oldText = typingTarget.getText();
			String newText = textField.getText();

			if(validate()){

				//Cleans up the internal event names for Entry and Exit
				if(oldText.startsWith("Entry/")){
					newText = new String("Entry" + newText.substring(newText.indexOf("/")));
					
				}else if(oldText.startsWith("Exit/")){
					newText = new String("Exit" + newText.substring(newText.indexOf("/")));
				}
				
				typingTarget.willChange();
				typingTarget.setText(newText);
				typingTarget.changed();

			}

			typingTarget.changed();
			typingTarget = null;

			textField.endOverlay();
		}
		//	        view().checkDamage();
	}

	private boolean validate(){

		final String oldText = typingTarget.getText();
		final String newText = textField.getText();

		if(newText.length() < 1) return false; //

		switch(context){
		//No validation
		case 0:
			return true;

			//State Action
		case 1:
			//I could invert all of these boolean expressions and just directly return that
			if(newText.startsWith("/") || newText.endsWith("/")) return false; //Must have e/a
			if(!newText.contains("/")) return false; //Does not contain '/'
			if(newText.indexOf("/") != newText.lastIndexOf("/")) return false; //Too many '/'
			//Can't name an internal event Entry or Exit
			if(((!oldText.startsWith("Entry/") && !oldText.startsWith("Exit/")) 
					&& (newText.startsWith("Entry/") || newText.startsWith("Exit/")))){
				return false;
			}
			
			return true;

			//Transition Action
		case 2:
			return !newText.contains("/");

		}

		return true;
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			fireToolDone();
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		endEdit();
		fireToolDone();
	}

	public boolean isEditing() {
		return typingTarget != null;
	}

	@Override
	public void updateCursor(DrawingView view, Point p) {
		if (view.isEnabled()) {
			view.setCursor(Cursor.getPredefinedCursor(isEditing() ? Cursor.DEFAULT_CURSOR : Cursor.CROSSHAIR_CURSOR));
		} else {
			view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
