package org.umlMachine.controller.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import org.jhotdraw.util.ResourceBundleUtil;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.controller.FileHandler;
import org.umlMachine.model.StateData;
import org.umlMachine.model.TransitionData;

@SuppressWarnings("serial")
public class AnimateAction extends AbstractAction {

	public final static String ID = "animate";

	public AnimateAction(){

		ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.umlMachine.controller.actions.Labels");
		labels.configureAction(this, ID);

	}

	@Override
	public void actionPerformed(ActionEvent a) {		
		try{
			new Thread(){
				public void run(){
			File events = FileHandler.getInstance().getFile();
			StateData current = FigureFactory.getInstance().findStart().getData();
			FigureFactory factory = FigureFactory.getInstance();
			if(!current.isStart() && !current.isEnd())
				FigureFactory.getInstance().findState(current.getTransitionsOut().get(0).getEnd()).shade(true);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
			BufferedReader reader =null;
			try{
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
							break;
						}
					}
					if(foundInAction){
						if(!current.isStart() && !current.isEnd())
							FigureFactory.getInstance().findState(current).shade(false);
						Thread.sleep(1000);
						if(!current.isStart() && !current.isEnd()){
							FigureFactory.getInstance().findState(current).shade(true);
								
							//System.out.println("found state "+current.getName());
						}
					}else{
						List<TransitionData> data = current.getTransitionsOut();
						for(TransitionData trans : data){
							if(trans.getEvent().equals(line)){
								System.out.println("found transition "+trans.getEvent()+" a:"+trans.getActions().get(0));
								found = true;
								if(!current.isStart() && !current.isEnd())
									FigureFactory.getInstance().findState(current).shade(false);
								Thread.sleep(1000);
								current = trans.getEnd();
								if(!current.isStart() && !current.isEnd())
									FigureFactory.getInstance().findState(current).shade(true);
								break;
							}
						}
						if(!found && current.isChild()){
							System.out.println("could not find but current is a child");
							//---------------------------------------------------------------------
							boolean foundInAction2 = false;
							for(String act : current.getParent().getActions()){
								if(line.equals(act.substring(0, act.indexOf("/")))){
									foundInAction2 = true;
									break;
								}
							}
							if(foundInAction2){
								if(!current.isStart() && !current.isEnd())
									FigureFactory.getInstance().findState(current).shade(false);
								Thread.sleep(1000);
								if(!current.getParent().isStart() && !current.getParent().isEnd()){
									FigureFactory.getInstance().findState(current.getParent()).shade(true);
										
									System.out.println("found state "+current.getParent().getName());
								}
							}else{
								List<TransitionData> datas = current.getParent().getTransitionsOut();
								for(TransitionData trans : datas){
									if(trans.getEvent().equals(line)){
										System.out.println("found transition out to "+trans.getEnd().getName());
										found = true;
										if(!current.isStart() && !current.isEnd())
											FigureFactory.getInstance().findState(current).shade(false);
										Thread.sleep(1000);
										current = trans.getEnd();
										if(!trans.getEnd().isStart() && !trans.getEnd().isEnd())
											FigureFactory.getInstance().findState(trans.getEnd()).shade(true);
										break;
									}
								}
							}
						}
					}
					Thread.sleep(2000);
					line = reader.readLine();
				}
			}catch(Exception e){}
			finally{
				
				if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}

				this.interrupt();
				}
				}
			}.start();
	}catch(Throwable e){}
	}


}
