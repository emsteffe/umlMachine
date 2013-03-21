package org.umlMachine.controller.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.file.ExportFileAction;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.gui.event.SheetEvent;
import org.jhotdraw.gui.event.SheetListener;
import org.jhotdraw.net.URIUtil;
import org.jhotdraw.util.ResourceBundleUtil;

import edu.umd.cs.findbugs.annotations.Nullable;

@SuppressWarnings("serial")
public class SerializeAction extends ExportFileAction {
	
	public final static String ID = "serialize";	
	 private Component oldFocusOwner;

	 
	    /** Creates a new instance. */
	    public SerializeAction(Application app, @Nullable View view) {
	        super(app, view);
	        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.UMLMahcine.actions.Labels");
	        labels.configureAction(this, ID);
	    }

	    @Override
	    public void actionPerformed(ActionEvent evt) {
	        final View view = (View) getActiveView();
	        if (view.isEnabled()) {
	            ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.UMLMahcine.actions.Labels");

	            oldFocusOwner = SwingUtilities.getWindowAncestor(view.getComponent()).getFocusOwner();
	            view.setEnabled(false);

	            URIChooser fileChooser = getApplication().getExportChooser(view);

	            JSheet.showSheet(fileChooser, view.getComponent(), labels.getString("filechooser.export"), new SheetListener() {

	                @Override
	                public void optionSelected(final SheetEvent evt) {
	                    if (evt.getOption() == JFileChooser.APPROVE_OPTION) {
	                        final URI uri = evt.getChooser().getSelectedURI();
	                        if (evt.getChooser()instanceof JFileURIChooser) {
	                        exportView(view, uri, evt.getChooser());
	                        } else {
	                        exportView(view, uri, null);
	                        }
	                    } else {
	                        view.setEnabled(true);
	                        if (oldFocusOwner != null) {
	                            oldFocusOwner.requestFocus();
	                        }
	                    }
	                }
	            });
	        }
	    }

	    protected void exportView(final View view, final URI uri,
	            @Nullable final URIChooser chooser) {
	        view.execute(new Worker() {

	            @Override
	            protected Object construct() throws IOException {
	                view.write(uri,chooser);
	                return null;
	            }

	            @Override
	            protected void failed(Throwable value) {
	                System.out.flush();
	                ((Throwable) value).printStackTrace();
	                // FIXME localize this error messsage
	                JSheet.showMessageSheet(view.getComponent(),
	                        "<html>" + UIManager.getString("OptionPane.css") +
	                        "<b>Couldn't export to the file \"" + URIUtil.getName(uri) + "\".<p>" +
	                        "Reason: " + value,
	                        JOptionPane.ERROR_MESSAGE);
	            }

	            @Override
	            protected void finished() {
	                view.setEnabled(true);
	                SwingUtilities.getWindowAncestor(view.getComponent()).toFront();
	                if (oldFocusOwner != null) {
	                    oldFocusOwner.requestFocus();
	                }
	            }
	        });
	    }

}
