/*******************************************************************************
* Copyright (c) 2005 Jules White. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Common Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/cpl-v10.html
* 
* Contributors: Jules White - initial API and implementation
******************************************************************************/

package lost.tok.DiscussionViewer;


import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.gems.designer.GemsEditor;
import org.gems.designer.GemsPlugin;

/**
 * @author Jules White
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DSMLEditor extends GemsEditor {

    static {
        DiscussionViewerPlugin.getDefault().getPreferenceStore().setDefault(
           PALETTE_SIZE, DEFAULT_PALETTE_SIZE);
    }
    /**
     * 
     */
    public DSMLEditor() {
        super();
        
    }
    
    protected String getModelID() {
   	 	return DiscussionViewerProvider.MODEL_ID;
	}
    
    protected PaletteRoot getPaletteRoot() {
        if( root == null ){
            root = DiscussionViewerProvider.getInstance().getPaletteProvider().createPalette();
        }
        return root;
    }
    
    protected FlyoutPreferences getPalettePreferences() {
        return new FlyoutPreferences() {
            public int getDockLocation() {
                return DiscussionViewerPlugin.getDefault().getPreferenceStore()
                      .getInt(PALETTE_DOCK_LOCATION);
                
            }
            public int getPaletteState() {
                return DiscussionViewerPlugin.getDefault().getPreferenceStore().getInt(PALETTE_STATE);
            }
            public int getPaletteWidth() {
                return DEFAULT_PALETTE_SIZE;
            }
            public void setDockLocation(int location) {
              DiscussionViewerPlugin.getDefault().getPreferenceStore()
                      .setValue(PALETTE_DOCK_LOCATION, location);
            }
            public void setPaletteState(int state) {
              DiscussionViewerPlugin.getDefault().getPreferenceStore()
                      .setValue(PALETTE_STATE, state);
            }
            public void setPaletteWidth(int width) {
              DiscussionViewerPlugin.getDefault().getPreferenceStore()
                      .setValue(PALETTE_SIZE, width);
            }
        };
    }
    
     protected ClassLoader getClassLoaderForSerialization() {
        return getClass().getClassLoader();
    }
    

}
