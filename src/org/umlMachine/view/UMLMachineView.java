/*
 * @(#)PertView.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 *
 */
package org.umlMachine.view;

import org.jhotdraw.app.action.edit.RedoAction;
import org.jhotdraw.app.action.edit.UndoAction;
import org.jhotdraw.draw.io.OutputFormat;
import org.jhotdraw.draw.io.InputFormat;
import org.jhotdraw.draw.io.ImageOutputFormat;
import org.jhotdraw.draw.print.DrawingPageable;
import org.jhotdraw.draw.io.DOMStorableInputOutputFormat;
import java.awt.print.Pageable;
import java.util.*;
import org.jhotdraw.gui.*;
import org.jhotdraw.undo.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.URI;
import javax.swing.*;
import javax.swing.border.*;
import org.jhotdraw.app.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.gui.URIChooser;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.model.TransitionData;
import org.umlMachine.model.UMLMachineFactory;
import org.umlMachine.view.figures.StateFigure;

/**
 * A view for Pert diagrams.
 *
 * @author Werner Randelshofer
 * @version $Id: PertView.java 727 2011-01-09 13:23:59Z rawcoder $
 */
@SuppressWarnings("serial")
public class UMLMachineView extends AbstractView {

    public final static String GRID_VISIBLE_PROPERTY = "gridVisible";
    /**
     * Each view uses its own undo redo manager.
     * This allows for undoing and redoing actions per view.
     */
    private UndoRedoManager undo;
    /**
     * Depending on the type of an application, there may be one editor per
     * view, or a single shared editor for all views.
     */
    private DrawingEditor editor;

    /**
     * Creates a new view.
     */
    public UMLMachineView() {
        initComponents();

        scrollPane.setLayout(new PlacardScrollPaneLayout());
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));

        setEditor(new DefaultDrawingEditor());
        undo = new UndoRedoManager();
        view.setDrawing(createDrawing());
        view.getDrawing().addUndoableEditListener(undo);
        initActions();
        undo.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setHasUnsavedChanges(undo.hasSignificantEdits());
            }
        });

    }

    /**
     * Creates a new Drawing for this view.
     */
    protected Drawing createDrawing() {
        DefaultDrawing drawing = new DefaultDrawing();
        DOMStorableInputOutputFormat ioFormat =
                new DOMStorableInputOutputFormat(new UMLMachineFactory());
        LinkedList<InputFormat> inputFormats = new LinkedList<InputFormat>();
        inputFormats.add(ioFormat);
        drawing.setInputFormats(inputFormats);
        LinkedList<OutputFormat> outputFormats = new LinkedList<OutputFormat>();
        outputFormats.add(ioFormat);
        outputFormats.add(new ImageOutputFormat());
        drawing.setOutputFormats(outputFormats);
        return drawing;
    }

    /**
     * Creates a Pageable object for printing this view.
     */
    public Pageable createPageable() {
        return new DrawingPageable(view.getDrawing());

    }

    public DrawingEditor getEditor() {
        return editor;
    }

    public void setEditor(DrawingEditor newValue) {
        DrawingEditor oldValue = editor;
        if (oldValue != null) {
            oldValue.remove(view);
        }
        editor = newValue;
        if (newValue != null) {
            newValue.add(view);
        }
    }

    /**
     * Initializes view specific actions.
     */
    private void initActions() {
        getActionMap().put(UndoAction.ID, undo.getUndoAction());
        getActionMap().put(RedoAction.ID, undo.getRedoAction());
    }

    @Override
    protected void setHasUnsavedChanges(boolean newValue) {
        super.setHasUnsavedChanges(newValue);
        undo.setHasSignificantEdits(newValue);
    }

    /**
     * Writes the view to the specified uri.
     */
    @Override
    public void write(URI f, URIChooser chooser) throws IOException {
        Drawing drawing = view.getDrawing();
        OutputFormat outputFormat = drawing.getOutputFormats().get(0);
        outputFormat.write(f, drawing);
        BufferedWriter writer=null;
       	try{
       		File file = new File(f.toString().substring(6));
       		writer = new BufferedWriter(new FileWriter(file,true));
       		Set<StateFigure> states = FigureFactory.getInstance().getStates();
       		//System.out.println(FigureFactory.getInstance().getNumStates());
       		writer.write("\r\n<transitions>");
       			for(StateFigure state : states){
       				//System.out.println("writing state "+state.getName());
       				//System.out.println("has " + state.getDependencies().size() + "transitions");
       				//System.out.println("data has "+state.getData().getTransitionsOut());
       				
       				for(TransitionData transition : state.getData().getTransitionsOut()){
       					writer.write("\r\n");
       					writer.write(transition.toXML());
       				}
       			}
       		writer.write("\r\n</transitions>");
       	}catch(IOException e){} finally{if(writer != null)writer.close();}
    }

    /**
     * Reads the view from the specified uri.
     */
    @Override
    public void read(URI f, URIChooser chooser) throws IOException {
        try {
            final Drawing drawing = createDrawing();
            InputFormat inputFormat = drawing.getInputFormats().get(0);
            inputFormat.read(f, drawing, true);
            BufferedReader reader = null;
            try{
           		File file = new File(f.toString().substring(6));
           		reader = new BufferedReader(new FileReader(file));
           		String line = reader.readLine();
           		HashSet<TransitionData> data = new HashSet<TransitionData>();
           		//transition is now multiline able.
           		//
           		while(line != null){
           			System.out.println("reading line "+line);
           			String wholeTrans = "";
           			if(line.contains("<transition ")){
               			wholeTrans = wholeTrans + line;
               			while(!line.contains("</transition>")){
               				line = reader.readLine();
               				wholeTrans = wholeTrans + line;
               			}
               			
               			TransitionData toAdd = new TransitionData();
               			wholeTrans = wholeTrans.replaceAll("\t", "");
           				toAdd.setFromXML(wholeTrans);
           				data.add(toAdd);
               		}
           			line = reader.readLine();
           		}
           		
           		
           		
           		for(TransitionData transition : data){
           			transition.getStart().addTransitionOut(transition);
           		}
           	}catch(IOException e){}finally {if(reader != null)reader.close();}
            
            
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    view.getDrawing().removeUndoableEditListener(undo);
                    view.setDrawing(drawing);
                    view.getDrawing().addUndoableEditListener(undo);
                    undo.discardAllEdits();
                }
            });
        } catch (InterruptedException e) {
            InternalError error = new InternalError();
            e.initCause(e);
            throw error;
        } catch (InvocationTargetException e) {
            InternalError error = new InternalError();
            e.initCause(e);
            throw error;
        }
    }

    /**
     * Clears the view.
     */
    @Override
    public void clear() {
        final Drawing newDrawing = createDrawing();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    view.getDrawing().removeUndoableEditListener(undo);
                    view.setDrawing(newDrawing);
                    view.getDrawing().addUndoableEditListener(undo);
                    undo.discardAllEdits();
                }
            });
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean canSaveTo(URI uri) {
        return uri.getPath().endsWith(".xml");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        view = new org.jhotdraw.draw.DefaultDrawingView();

        setLayout(new java.awt.BorderLayout());

        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(view);

        add(scrollPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scrollPane;
    private org.jhotdraw.draw.DefaultDrawingView view;
    // End of variables declaration//GEN-END:variables
}
