/*******************************************************************************
* Copyright (c) 2005 Jules White. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Common Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/cpl-v10.html
* 
* Contributors: Jules White - initial API and implementation
******************************************************************************/


/*
 * Created on Fri Jun 22 20:03:23 IDT 2007
 *
 * Generated by GEMS 
 */
 
package lost.tok.DiscussionViewer;

import java.util.Hashtable;
import org.eclipse.swt.graphics.Color;
import org.gems.designer.model.ConnectionDecoration;
import org.eclipse.draw2d.ColorConstants;
import org.gems.designer.model.ConnectionAppearance;
import org.gems.designer.figures.Hints;
import org.gems.designer.Aspect;
import org.gems.designer.ModelProvider;
import org.gems.designer.model.ModelObject;
import org.eclipse.swt.widgets.Display;

public class GraphicsProviderImpl extends org.gems.designer.model.GraphicsProviderImpl {
   
    public String getModelID() {
        return DiscussionViewerProvider.MODEL_ID;
    }
   
    protected void createAspects() {
     super.createAspects();
    }
   
    protected void createConnectionAppearances() {
        	ConnectionAppearance Relationattr = new ConnectionAppearance(
                ConnectionAppearance.ARROW,
                ConnectionAppearance.ARROW,
                Hints.getColor("(0,0,0)"),
                2,
                3
              );
        	connectionAppearances_.put(RelationConnectionType.INSTANCE,Relationattr);
             
        	ConnectionAppearance Containmentattr = new ConnectionAppearance(
                ConnectionAppearance.DIAMOND,
                ConnectionAppearance.NONE,
                Hints.getColor("(0,0,0)"),
                0,
                1
              );
        	connectionAppearances_.put(ContainmentConnectionType.INSTANCE,Containmentattr);
             
        super.createConnectionAppearances();
    }
    protected void createConnectionDecorations() {
        
        super.createConnectionDecorations();
    }
    
    protected void createDrawingHints() {
    	
    	Hashtable AbstractDicObject_hints = new Hashtable(7);
    	
		
		AbstractDicObject_hints.put("background-color", "(255,255,255)");
		
		
		AbstractDicObject_hints.put("draw-name", "true");
		
		
		AbstractDicObject_hints.put("draw-type", "true");
		
		
		AbstractDicObject_hints.put("label-font", "System");
		
		
		AbstractDicObject_hints.put("label-fontsize", "8");
		
		
		AbstractDicObject_hints.put("label-fontstyle", "normal");
		
		
		AbstractDicObject_hints.put("label-foreground", "(0,0,0)");
		
		
		AbstractDicObject_hints.put("label-background", "(255,255,255)");
		
		drawingHints_.put(AbstractDicObject.class, AbstractDicObject_hints);
		
    	Hashtable Opinion_hints = new Hashtable(7);
    	
		
		Opinion_hints.put("background-color", "(255,255,255)");
		
		
		Opinion_hints.put("draw-name", "true");
		
		
		Opinion_hints.put("draw-type", "true");
		
		
		Opinion_hints.put("label-font", "System");
		
		
		Opinion_hints.put("label-fontsize", "8");
		
		
		Opinion_hints.put("label-fontstyle", "normal");
		
		
		Opinion_hints.put("label-foreground", "(0,0,0)");
		
		
		Opinion_hints.put("label-background", "(255,255,255)");
		
		drawingHints_.put(Opinion.class, Opinion_hints);
		
    	Hashtable Quote_hints = new Hashtable(7);
    	
		
		Quote_hints.put("background-color", "(255,255,255)");
		
		
		Quote_hints.put("draw-name", "true");
		
		
		Quote_hints.put("draw-type", "true");
		
		
		Quote_hints.put("label-font", "System");
		
		
		Quote_hints.put("label-fontsize", "8");
		
		
		Quote_hints.put("label-fontstyle", "normal");
		
		
		Quote_hints.put("label-foreground", "(0,0,0)");
		
		
		Quote_hints.put("label-background", "(255,255,255)");
		
		drawingHints_.put(Quote.class, Quote_hints);
		
    	Hashtable DiscussionViewer_hints = new Hashtable(7);
    	
		
		DiscussionViewer_hints.put("background-color", "(255,255,255)");
		
		
		DiscussionViewer_hints.put("draw-name", "true");
		
		
		DiscussionViewer_hints.put("draw-type", "true");
		
		
		DiscussionViewer_hints.put("label-font", "System");
		
		
		DiscussionViewer_hints.put("label-fontsize", "8");
		
		
		DiscussionViewer_hints.put("label-fontstyle", "normal");
		
		
		DiscussionViewer_hints.put("label-foreground", "(0,0,0)");
		
		
		DiscussionViewer_hints.put("label-background", "(255,255,255)");
		
		drawingHints_.put(DiscussionViewer.class, DiscussionViewer_hints);
		
		super.createDrawingHints();
	}
    
    public ModelProvider getModelProvider() {
        return DiscussionViewerProvider.getInstance();
    }
}


