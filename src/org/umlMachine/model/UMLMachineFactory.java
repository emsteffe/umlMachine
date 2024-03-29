/*
 * @(#)PertFactory.java
 *
 * Copyright (c) 1996-2010 by the original authors of JHotDraw and all its
 * contributors. All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the 
 * license agreement you entered into with the copyright holders. For details
 * see accompanying license terms.
 */

package org.umlMachine.model;

import org.jhotdraw.draw.liner.ElbowLiner;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.draw.connector.LocatorConnector;
import org.jhotdraw.draw.connector.ChopRectangleConnector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.umlMachine.view.figures.SeparatorLineFigure;
import org.umlMachine.view.figures.StateFigure;
import org.umlMachine.view.figures.TransitionFigure;
import org.jhotdraw.draw.*;
import org.jhotdraw.xml.*;
/**
 * PertFactory.
 * 
 * @author Werner Randelshofer
 * @version $Id: PertFactory.java 717 2010-11-21 12:30:57Z rawcoder $
 */
public class UMLMachineFactory extends DefaultDOMFactory {
    private final static Object[][] classTagArray = {
        { DefaultDrawing.class, "UMLDiagram" },
        { StateFigure.class, "task" },
        { TransitionFigure.class, "dep" },
        { ListFigure.class, "list" },
        { TextFigure.class, "text" },
        { GroupFigure.class, "g" },
        { TextAreaFigure.class, "ta" },
        { SeparatorLineFigure.class, "separator" },
        
        { ChopRectangleConnector.class, "rectConnector" },
        { LocatorConnector.class, "locConnector" },
        { RelativeLocator.class, "relativeLocator" },
        { ArrowTip.class, "arrowTip" },
        { ElbowLiner.class, "org.jhotdraw.draw.liner.ElbowLiner"}
    };
    
    /** Creates a new instance. */
    @SuppressWarnings("rawtypes")
	public UMLMachineFactory() {
        for (Object[] o : classTagArray) {
            addStorableClass((String) o[1], (Class) o[0]);
        }
    }
}
