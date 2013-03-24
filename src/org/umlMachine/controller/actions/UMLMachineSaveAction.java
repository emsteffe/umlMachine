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
import org.jhotdraw.app.action.file.SaveFileAction;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.Worker;
import org.jhotdraw.gui.event.SheetEvent;
import org.jhotdraw.gui.event.SheetListener;
import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.net.URIUtil;
import org.jhotdraw.util.ResourceBundleUtil;

import edu.umd.cs.findbugs.annotations.Nullable;

@SuppressWarnings("serial")
public class UMLMachineSaveAction extends SaveFileAction {

	public final static String ID = "file.save";
	private boolean saveAs;
	private Component oldFocusOwner;

	/** Creates a new instance. */
	public UMLMachineSaveAction(Application app, @Nullable View view) {
		this(app, view, false);
	}

	/** Creates a new instance. */
	public UMLMachineSaveAction(Application app, @Nullable View view, boolean saveAs) {
		super(app, view);
		this.saveAs = saveAs;
		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.UMLMachine.actions.Labels");
		labels.configureAction(this, ID);
	}

	protected URIChooser getChooser(View view) {
		URIChooser chsr = (URIChooser) (view.getComponent()).getClientProperty("saveChooser");
		if (chsr == null) {
			chsr = getApplication().getModel().createSaveChooser(getApplication(), view);
			view.getComponent().putClientProperty("saveChooser", chsr);
		}
		return chsr;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		final View view = getActiveView();
		if (view == null) {
			return;
		}
		if (view.isEnabled()) {
			oldFocusOwner = SwingUtilities.getWindowAncestor(view.getComponent()).getFocusOwner();
			view.setEnabled(false);

			if (!saveAs && view.getURI() != null && view.canSaveTo(view.getURI())) {
				saveViewToURI(view, view.getURI(), null);
			} else {
				URIChooser fileChooser = getChooser(view);

				JSheet.showSaveSheet(fileChooser, view.getComponent(), new SheetListener() {

					@Override
					public void optionSelected(final SheetEvent evt) {
						if (evt.getOption() == JFileChooser.APPROVE_OPTION) {
							final URI uri;
							if ((evt.getChooser() instanceof JFileURIChooser) && (evt.getFileChooser().getFileFilter() instanceof ExtensionFileFilter)) {
								uri = ((ExtensionFileFilter) evt.getFileChooser().getFileFilter()).makeAcceptable(evt.getFileChooser().getSelectedFile()).toURI();
							} else {
								uri = evt.getChooser().getSelectedURI();
							}
							saveViewToURI(view, uri, evt.getChooser());
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
	}

	protected void saveViewToURI(final View view, final URI file,
			@Nullable final URIChooser chooser) {
		view.execute(new Worker() {

			@Override
			protected Object construct() throws IOException {
				view.write(file, chooser);
				return null;
			}

			@Override
			protected void done(Object value) {
				view.setURI(file);
				view.markChangesAsSaved();
				int multiOpenId = 1;
				for (View p : view.getApplication().views()) {
					if (p != view && p.getURI() != null && p.getURI().equals(file)) {
						multiOpenId = Math.max(multiOpenId, p.getMultipleOpenId() + 1);
					}
				}
				getApplication().addRecentURI(file);
				view.setMultipleOpenId(multiOpenId);
			}

			@Override
			protected void failed(Throwable value) {
				value.printStackTrace();
				String message = value.getMessage() != null ? value.getMessage() : value.toString();
				ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.UMLMachine.actions.Labels");
				JSheet.showMessageSheet(getActiveView().getComponent(),
						"<html>" + UIManager.getString("OptionPane.css")
						+ "<b>" + labels.getFormatted("file.save.couldntSave.message", URIUtil.getName(file)) + "</b><p>"
						+ ((message == null) ? "" : message),
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