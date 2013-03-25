package org.umlMachine.controller.tools;

import java.util.Map;

import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.tool.CreationTool;
import org.umlMachine.controller.FigureFactory;

import edu.umd.cs.findbugs.annotations.Nullable;

@SuppressWarnings("serial")
public class StateCreationTool extends CreationTool {

	//This class creates states in the factory

	protected Figure prototype;
	

    public StateCreationTool(String prototypeClassName, @Nullable Map<AttributeKey, Object> attributes) {
        super(prototypeClassName, attributes, null);
        prototype = FigureFactory.getInstance().getState();
        prototypeAttributes = attributes;
		
	}

}
