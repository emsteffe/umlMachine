package org.umlMachine.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.jhotdraw.gui.JFileURIChooser;

public class FileHandlerListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		System.out.println(((JFileURIChooser)e.getSource()).getSelectedFile());
		if(e.getActionCommand().equals("CancelSelection")){
			FileHandler.setOption(FileHandler.CANCEL);
			FileHandler.done();
		}else if(e.getActionCommand().equals("ApproveSelection")){
			FileHandler.setOption(FileHandler.OK);
			FileHandler.setFile(((JFileURIChooser)e.getSource()).getSelectedFile().getAbsolutePath());
			FileHandler.done();
		}
	}

}
