package org.umlMachine.controller.tools;

import java.util.HashMap;
import java.util.Map;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;
import org.umlMachine.controller.FigureFactory;

@SuppressWarnings("serial")
public class StateCreationTool extends CreationTool {

	//This class creates states in the factory

	@SuppressWarnings("rawtypes")
	public StateCreationTool(HashMap<AttributeKey, Object> attributes) {
		super(FigureFactory.getInstance().getState());
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Figure createFigure() {
		//Figure f = (Figure) prototype.clone();
		Figure f = FigureFactory.getInstance().getState();
		getEditor().applyDefaultAttributesTo(f);
		if (prototypeAttributes != null) {
			for (Map.Entry<AttributeKey, Object> entry : prototypeAttributes.entrySet()) {
				f.set(entry.getKey(), entry.getValue());
			}
		}
		return f;
	}


}
