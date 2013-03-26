package org.umlMachine.controller.tools;

import java.util.HashMap;
import java.util.Map;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;
import org.umlMachine.controller.FigureFactory;
import org.umlMachine.figures.StateFigure;

import edu.umd.cs.findbugs.annotations.Nullable;

@SuppressWarnings("serial")
public class StateCreationTool extends CreationTool {

	//This class creates states in the factory

	public StateCreationTool(HashMap<AttributeKey, Object> attributes) {
		super(FigureFactory.getInstance().getState());
	}


	@SuppressWarnings("unchecked")
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
