/*
 * @(#)ButtonFactory.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.umlMachine.draw.action;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.jhotdraw.app.action.edit.PasteAction;
import org.jhotdraw.app.action.edit.CutAction;
import org.jhotdraw.app.action.edit.CopyAction;
import org.jhotdraw.app.action.edit.DuplicateAction;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.draw.tool.DelegationSelectionTool;
import org.jhotdraw.draw.event.ToolAdapter;
import org.jhotdraw.draw.event.ToolEvent;
import org.jhotdraw.draw.event.ToolListener;
import org.jhotdraw.draw.action.ColorIcon;
import org.jhotdraw.draw.decoration.LineDecoration;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.event.SelectionComponentRepainter;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.util.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.beans.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.ColorChooserUI;
import javax.swing.text.*;
import org.jhotdraw.app.action.*;
import org.jhotdraw.app.Disposable;
import org.jhotdraw.color.HSBColorSpace;
import static org.jhotdraw.draw.AttributeKeys.*;
import org.jhotdraw.geom.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.gui.JComponentPopup;
import org.jhotdraw.gui.JFontChooser;

/**
 * ButtonFactory.
 * <p>
 * Design pattern:<br>
 * Name: Abstract Factory.<br>
 * Role: Abstract Factory.<br>
 * Partners: {@link org.jhotdraw.samples.draw.DrawApplicationModel} as Client, 
 * {@link org.jhotdraw.samples.draw.DrawView} as Client,
 * {@link org.jhotdraw.samples.draw.DrawingPanel} as Client.
 *
 * FIXME - All buttons created using the ButtonFactory must automatically
 * become disabled/enabled, when the DrawingEditor is disabled/enabled.
 *
 * @author Werner Randelshofer
 * @version $Id: ButtonFactory.java 717 2010-11-21 12:30:57Z rawcoder $
 */
public class ButtonFactory extends org.jhotdraw.draw.action.ButtonFactory {
	
	public ButtonFactory(){
		
		//a change
	}

    
}
