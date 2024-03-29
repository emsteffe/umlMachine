/*
simulation
out of diagram
	takes a Set<figures> (will take care of loading events file)
	returns true on end state. false on cancel or dead.

out of file
	takes nothing (will take care of file loading)
	returns true on end state. false on cancel, dead or early termination.
*/

package org.umlMachine.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.umlMachine.model.StateData;
import org.umlMachine.model.TransitionData;
import org.umlMachine.view.figures.StateFigure;

public class Simulator {
	
	private static Simulator instance = null;

	public static Simulator getInstance(){
		if(instance == null) instance = new Simulator();
		return instance;
	}
	
	
	public boolean simulate(){
		StateFigure state = FigureFactory.getInstance().findState("State 1");
		state.highlight(true);
		return true;
	}
	
	public boolean simulate(Set<StateFigure> s){
		return false;
	}


	public void fromFile() {
		new Thread(){
			public void run(){
				XMLController xml = new XMLController();
				FileHandler.setType("Serialized Figure");
		File serialized = FileHandler.getInstance().getFile();
		FileHandler.setType("Events File");
		File events = FileHandler.getInstance().getFile();
		FileHandler.setType("Output File");
		File out = FileHandler.getInstance().getFile();
		BufferedWriter writer = null;
		StateData current = null;
		Set<StateData> states = null ;
		try {
			states = xml.loadData(serialized);
		} catch (IOException e1) {
		}
				
		for(StateData state : states){
			if(state.isStart()){
				current= state;
				break;
			}
		}
			
		FigureFactory factory = FigureFactory.getInstance();
		
		BufferedReader reader =null;
		try{
			writer = new BufferedWriter(new FileWriter(out));
			reader = new BufferedReader(new FileReader(events));
			String line = reader.readLine();
			while(line != null && !current.isEnd()){
				boolean found = false;
				System.out.println("current "+current.getName());
				boolean foundInAction = false;
				for(String act : current.getActions()){
					if(line.equals(act.substring(0, act.indexOf("/")))){
						foundInAction = true;
						System.out.println("found action "+act);
						writer.write(act.substring(act.indexOf("/")+1));
						writer.newLine();
						break;
					}
				}
				if(foundInAction){
					//-------------------------------
							
						//System.out.println("found state "+current.getName());
					
				}else{
					List<TransitionData> data = current.getTransitionsOut();
					for(TransitionData trans : data){
						if(trans.getEvent().equals(line)){
							System.out.println("found transition "+trans.getEvent()+" a:"+trans.getActions().get(0));
							found = true;
							for(String toWrite: current.getExitActions()){
								writer.write(toWrite);
								writer.newLine();
							}
							for(String toWrite: trans.getActions()){
								writer.write(toWrite);
								writer.newLine();
							}
							current = trans.getEnd();
							for(String toWrite: current.getEntryActions()){
								writer.write(toWrite);
								writer.newLine();
							}
							break;
						}
					}
					if(!found && current.isChild()){
						//---------------------------------------------------------------------
						boolean foundInAction2 = false;
						for(String act : current.getParent().getActions()){
							if(line.equals(act.substring(0, act.indexOf("/")))){
								foundInAction2 = true;
								writer.write(act.substring(act.indexOf("/")+1));
								writer.newLine();
								break;
							}
						}
						if(foundInAction2){
							//-------------
						}else{
							List<TransitionData> datas = current.getParent().getTransitionsOut();
							for(TransitionData trans : datas){
								if(trans.getEvent().equals(line)){
									found = true;
									for(String toWrite: current.getExitActions()){
										writer.write(toWrite);
										writer.newLine();
									}
									for(String toWrite: trans.getActions()){
										writer.write(toWrite);
										writer.newLine();
									}
									current = trans.getEnd();
									for(String toWrite: current.getEntryActions()){
										writer.write(toWrite);
										writer.newLine();
									}
									break;
								}
							}
						}
					}
				}
				line = reader.readLine();
			}
		}catch(Exception e){}
		finally{
			if(reader != null)
			try {
				reader.close();
			} catch (IOException e) {
			}
			if(writer != null)
				try{
					writer.close();
				}catch(Exception e){
					
				}
			}
		
			}
	
		}.start();
	}


	public void fromDiagram() {
		
		new Thread(){
			public void run(){
				FileHandler.setType("Events File");
		File events = FileHandler.getInstance().getFile();
		FileHandler.setType("Output File");
		File out = FileHandler.getInstance().getFile();
		BufferedWriter writer = null;
		
			
		StateData current = FigureFactory.getInstance().findStart().getData();
		FigureFactory factory = FigureFactory.getInstance();
		
		BufferedReader reader =null;
		try{
			writer = new BufferedWriter(new FileWriter(out));
			reader = new BufferedReader(new FileReader(events));
			String line = reader.readLine();
			while(line != null && !current.isEnd()){
				boolean found = false;
				System.out.println("current "+current.getName());
				boolean foundInAction = false;
				for(String act : current.getActions()){
					if(line.equals(act.substring(0, act.indexOf("/")))){
						foundInAction = true;
						System.out.println("found action "+act);
						writer.write(act.substring(act.indexOf("/")+1));
						writer.newLine();
						break;
					}
				}
				if(foundInAction){
					//-------------------------------
							
						//System.out.println("found state "+current.getName());
					
				}else{
					List<TransitionData> data = current.getTransitionsOut();
					for(TransitionData trans : data){
						if(trans.getEvent().equals(line)){
							System.out.println("found transition "+trans.getEvent()+" a:"+trans.getActions().get(0));
							found = true;
							for(String toWrite: current.getExitActions()){
								writer.write(toWrite);
								writer.newLine();
							}
							for(String toWrite: trans.getActions()){
								writer.write(toWrite);
								writer.newLine();
							}
							current = trans.getEnd();
							for(String toWrite: current.getEntryActions()){
								writer.write(toWrite);
								writer.newLine();
							}
							current = trans.getEnd();
							break;
						}
					}
					if(!found && current.isChild()){
						//---------------------------------------------------------------------
						boolean foundInAction2 = false;
						for(String act : current.getParent().getActions()){
							if(line.equals(act.substring(0, act.indexOf("/")))){
								foundInAction2 = true;
								writer.write(act.substring(act.indexOf("/")+1));
								writer.newLine();
								break;
							}
						}
						if(foundInAction2){
							//-------------
						}else{
							List<TransitionData> datas = current.getParent().getTransitionsOut();
							for(TransitionData trans : datas){
								if(trans.getEvent().equals(line)){
									found = true;
									for(String toWrite: current.getExitActions()){
										writer.write(toWrite);
										writer.newLine();
									}
									for(String toWrite: trans.getActions()){
										writer.write(toWrite);
										writer.newLine();
									}
									current = trans.getEnd();
									for(String toWrite: current.getEntryActions()){
										writer.write(toWrite);
										writer.newLine();
									}
									current = trans.getEnd();
									break;
								}
							}
						}
					}
				}
				line = reader.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(reader != null)
			try {
				reader.close();
			} catch (IOException e) {
			}
			if(writer != null)
				try{
					writer.close();
					System.out.println("closing");
				}catch(Exception e){
					
				}
			}
		
			}
	
		}.start();
	}
	
}
	