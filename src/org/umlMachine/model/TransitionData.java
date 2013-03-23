package org.umlMachine.model;

public class TransitionData {
	
	private StateData start;
	private StateData end;
	private String trigger;
	private String action;
	private String event;
	private String condition;
	
	public TransitionData(){}
	
	public TransitionData(String action, StateData start, StateData end, String trigger,String event, String condition){
		this.start = start;
		this.end = end;
		this.trigger = trigger;
		this.action = action;
		this.condition = condition;
		this.event = event;
	}
	
	public void setStart(StateData start){this.start = start;}
	public void setEnd(StateData end){this.end = end;}
	public void setTrigger(String trigger){this.trigger = trigger;}
	public void setAction(String action){this.action = action;}
	public void setCondition(String condition){this.condition = condition;}
	public void setEvent(String event){this.event = event;}
	
	
	public StateData getStart(){
		return start;
	}
	public StateData getEnd(){
		return end;
	}
	public String getTrigger(){
		return trigger;
	}
	public String getAction(){
		return action;
	}
	public String getEvent(){
		return event;
	}
	public String condition(){
		return condition;
	}
	
	public String toXML(){
		return "<Transition action=\"" + action + 
				"\" start=\"" + start.getName() + 
				"\" end=\"" + end.getName() + 
				"\" trigger=\"" + trigger + 
				"\" event=\"" + event + 
				"\" condition=\"" + condition + 
				"/>";
	}
}
