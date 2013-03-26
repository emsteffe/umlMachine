/*file save, load, serialize.

file save 
takes a Set<figures>
returns bool. true on save success

file load
takes nothing
returns a Set<figures>

serialize
takes a Set<figures>
returns bool. true on success storing

*/


package org.umlMachine.controller;


import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


import org.jhotdraw.gui.JFileURIChooser;
import org.umlMachine.figures.StateFigure;
import org.umlMachine.model.RefModel;
import org.umlMachine.model.StateData;
import org.umlMachine.model.TransitionData;

public class FileHandler {
	private JFileURIChooser d;
	private FileHandler(){
		d = new JFileURIChooser();
		fileSelect = new JFrame();
		FileHandlerListener k = new FileHandlerListener();
		d.addActionListener(k);
		fileSelect.add(d);
	}
	
	private static FileHandler instance = null;
	
	private static JFrame fileSelect;
	private static boolean done = false;
	private static int option=0;
	private static String file = "";
	
	public static FileHandler getInstance(){
		if(instance == null) instance = new FileHandler();
		return instance;
	}
	
	public boolean exportDiagram(){//both transitions and states
		boolean toReturn = false;
		XMLController xml = new XMLController();
		
		
		d.showSaveDialog((Component)RefModel.editor.getActiveView());
		while(!done){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
		}
		if(option != 0){
			try{
				System.out.println("got file from user: "+file);
				xml.exportFigures(new File(file));
			}catch(Exception e){
				System.out.println("could not export to file");
			}finally{
				cleanup();
				fileSelect.setVisible(false);
			}
		}
		
		
		return toReturn;
	}
	
	public void importDiagram(){
		d.showOpenDialog((Component)RefModel.editor.getActiveView());
		XMLController xml = new XMLController();
		while(!done){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
		}
		if(option != 0){
			try{
				System.out.println("got file from user: "+file);
				File fileIn = new File(file);
				if(!fileIn.exists()) throw new FileNotFoundException("file not found");
				Set<StateFigure> states = xml.importStateFigures(new File(file));//perform import states
				FigureFactory.getInstance().reloadFactory(states);
				Set<TransitionData> transitions = xml.importTransitions(new File(file));//perform import transitions
				FigureFactory.getInstance().addTransitions(transitions);
			}catch(Exception e){
				System.out.println("could not import file");
			}finally{
				cleanup();
				fileSelect.setVisible(false);
			}
		}
	}
	
	public Set<StateData> loadSerial(){
		d.showOpenDialog((Component)RefModel.editor.getActiveView());
		XMLController xml = new XMLController();
		Set<StateData> toReturn = null;
		while(!done){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
		}
		if(option != 0){
			try{
				System.out.println("got file from user: "+file);
				toReturn = xml.loadData(new File(file));
			}catch(Exception e){
				System.out.println("could not load file");
			}finally{
				cleanup();
				fileSelect.setVisible(false);
			}
		}
		return toReturn;
	}
	
	public boolean serialize(){
		boolean toReturn = false;
		XMLController xml = new XMLController();
		Set<StateData> toSerialize = new HashSet<StateData>();
		for(StateFigure state : FigureFactory.getInstance().getStates()){
			toSerialize.add(state.getData());
		}
		String serialOut = xml.serialize(toSerialize);
		
		d.showSaveDialog((Component) RefModel.editor.getActiveView());
		while(!done){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
		}
		if(option != 0){
			try{
				File fileOut = new File(file);
				System.out.println("got file from user: "+file);
				if(!fileOut.exists()) fileOut.createNewFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(fileOut));
				out.write(serialOut);
				out.close();
				toReturn = true;
			}catch(Exception e){
				System.out.println("could not serialize to file");
			}finally{
				cleanup();
				fileSelect.setVisible(false);
			}
		}
		
		
		return toReturn;
	}
	
	public static void main(String args[]){
		FileHandler test = FileHandler.getInstance();
		test.loadSerial();
	}
	
	private static void cleanup(){
		done = false;
		option=0;
		file = "";
	}
	
	public static final int CANCEL = 0;
	public static final int OK = 1;
	
	public static void done(){
		done = true;
	}
	
	public static void setOption(int oPtion){
		if(oPtion != CANCEL && oPtion != OK) return;
		option = oPtion;
	}
	
	public File getFile(){
		File toReturn = null;
		d.showOpenDialog((Component) (RefModel.editor.getActiveView()));
		XMLController xml = new XMLController();
		while(!done){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {}
		}
		if(option != 0){
			try{
				System.out.println("got file from user: "+file);
				toReturn = new File(file);
			}catch(Exception e){}
		}
		return toReturn;
	}
	
	public static void setFile(String fIle){
		file = fIle;
	}

	public void save() {
		// TODO Auto-generated method stub
		
	}

	public void saveAs() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}


