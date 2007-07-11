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
import org.gems.designer.AbstractPaletteProvider;
import org.gems.designer.ModelProvider;
import org.gems.designer.PaletteProvider;

/**
 * @author Jules White
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DiscussionViewerPaletteProvider extends AbstractPaletteProvider implements PaletteProvider{
    public PaletteRoot createPalette() {
        return super.createPalette();
    }
    public ModelProvider getModelProvider() {
        return DiscussionViewerProvider.getInstance();
    }
}
