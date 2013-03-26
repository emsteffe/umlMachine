/*
 * @(#)PertApplicationModel.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */
package org.umlMachine;

import edu.umd.cs.findbugs.annotations.Nullable;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.draw.tool.ConnectionTool;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import org.jhotdraw.app.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.action.*;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.util.*;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.controller.tools.*;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.model.RefModel;

/**
 * PertApplicationModel.
 * 
 * @author Werner Randelshofer.
 * @version $Id: PertApplicationModel.java 717 2010-11-21 12:30:57Z rawcoder $
 */
@SuppressWarnings("serial")
public class UMLMachineApplicationModel extends DefaultApplicationModel {


    @SuppressWarnings("unused")
	private static class ToolButtonListener implements ItemListener {

        private Tool tool;
        private DrawingEditor editor;

        public ToolButtonListener(Tool t, DrawingEditor editor) {
            this.tool = t;
            this.editor = editor;
        }

        @Override
        public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                editor.setTool(tool);
            }
        }
    }
    /**
     * This editor is shared by all views.
     */
    private DefaultDrawingEditor sharedEditor;
    /** Creates a new instance. */
    public UMLMachineApplicationModel() {
    }

    @Override
    public ActionMap createActionMap(Application a, @Nullable View v) {
        return super.createActionMap(a, v);
    }

    public DefaultDrawingEditor getSharedEditor() {
        if (sharedEditor == null) {
            sharedEditor = new DefaultDrawingEditor();
        }
        return sharedEditor;
    }

    @Override
    public void initView(Application a, @Nullable View p) {
        if (a.isSharingToolsAmongViews()) {
            ((UMLMachineView) p).setEditor(getSharedEditor());
        }
    }
    

    @SuppressWarnings("rawtypes")
	private void addButtonsTo(JToolBar tb, final DrawingEditor editor) {
        // AttributeKeys for the entitie sets
        HashMap<AttributeKey, Object> attributes;
        RefModel.editor = editor;
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.umlMachine.Labels");
        attributes = new HashMap<AttributeKey, Object>();
        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
        attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
        
        // Tools Start
        
        ButtonFactory.addSelectionToolTo(tb, editor);
        tb.addSeparator();
        
        //creation
        ButtonFactory.addToolTo(tb, editor, new StateCreationTool(attributes), "createState", labels);
        ButtonFactory.addToolTo(tb, editor, new ConnectionTool(FigureFactory.getInstance().getTransition(), attributes), "createTransition", labels);
        ButtonFactory.addToolTo(tb, editor, new ImageCreationTool(true, attributes), "createStart", labels);
        ButtonFactory.addToolTo(tb, editor, new ImageCreationTool(false, attributes), "createEnd", labels);
        
        tb.addSeparator();
        
        //functions
        ButtonFactory.addToolTo(tb, editor, new SaveTool(0), "save", labels);
        ButtonFactory.addToolTo(tb, editor, new SaveTool(1), "serialize", labels);
        tb.addSeparator();
        ButtonFactory.addToolTo(tb, editor, new SimulateTool(false), "fromfile", labels);
        ButtonFactory.addToolTo(tb, editor, new SimulateTool(true), "simulateDiagram", labels);
        
        
        // Implement this in later deliverable
        ButtonFactory.addToolTo(tb, editor, new AnimateTool(), "animate", labels);
        FigureFactory.getInstance().reloadFactory(new HashSet<StateFigure>());
        
       
    }

    /**
     * Creates toolbars for the application.
     * This class always returns an empty list. Subclasses may return other
     * values.
     */
    @Override
    public java.util.List<JToolBar> createToolBars(Application a, @Nullable View pr) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
        UMLMachineView p = (UMLMachineView) pr;

        DrawingEditor editor;
        if (p == null) {
            editor = getSharedEditor();
        } else {
            editor = p.getEditor();
        }

        LinkedList<JToolBar> list = new LinkedList<JToolBar>();
        JToolBar tb;
        tb = new JToolBar();
        addButtonsTo(tb, editor);
        tb.setName(drawLabels.getString("window.drawToolBar.title"));
        list.add(tb);
                
        return list;
    }

    /** Creates the MenuBuilder. */
    @Override
    protected MenuBuilder createMenuBuilder() {
        return new UMLMachineMenuBuilder();
    }

    @Override
    public URIChooser createOpenChooser(Application a, @Nullable View v) {
        JFileURIChooser c = new JFileURIChooser();
        c.addChoosableFileFilter(new ExtensionFileFilter("Pert Diagram", "xml"));
        return c;
    }

    @Override
    public URIChooser createSaveChooser(Application a, @Nullable View v) {
        JFileURIChooser c = new JFileURIChooser();
        c.addChoosableFileFilter(new ExtensionFileFilter("Pert Diagram", "xml"));
        return c;
    }
}
