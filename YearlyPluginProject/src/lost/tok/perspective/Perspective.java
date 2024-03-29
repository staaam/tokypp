/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

/**
 * 
 */
package lost.tok.perspective;

import lost.tok.RelationView.RelationView;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.linkDisView.LinkDisView;
import lost.tok.navigator.InformationNavigator;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class creates the perspective Tree of Knowledge
 * 
 * @author LOST
 * 
 */
public class Perspective implements IPerspectiveFactory {

	public interface IPerspectivePlugin {
		/**
		 * Plugin id.
		 */
		public final static String PLUGIN_ID = "lost.tok.perspective"; //$NON-NLS-1$

		/**
		 * Test perspective id.
		 */
		public final static String TEST_PERSPECTIVE_ID = PLUGIN_ID + ".ToK"; //$NON-NLS-1$
	}

	/**
	 * The createInitialLayout method is called on the IPerspectiveFactory. This
	 * method defines the initial layout for a page
	 */
	public void createInitialLayout(IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);

	}

	/**
	 * Setting the look of things - what the user will see with this perspective
	 * 
	 * @param layout
	 */
	private void defineLayout(IPageLayout layout) {
		// Editors are placed for free.
		String editorArea = layout.getEditorArea();

		// Place package explorer to the left of
		// editor area.
		IFolderLayout left = layout.createFolder(
				"left", IPageLayout.LEFT, (float) 0.26, editorArea); //$NON-NLS-1$
		left.addView("org.eclipse.jdt.ui.PackageExplorer"); //$NON-NLS-1$
		left.addView(InformationNavigator.ID);
//		left.addView("org.eclipse.ui.views.ResourceNavigator");


		// Place the Excerption view underneath
		// editor area.
		IFolderLayout bottom = layout.createFolder(
				"bottom", IPageLayout.BOTTOM, (float) 0.75, editorArea); //$NON-NLS-1$
		bottom.addView(ExcerptionView.ID);
		bottom.addView(LinkDisView.ID);
		bottom.addView(RelationView.ID);
	}

	/**
	 * shortcuts for the perspective - menus and views
	 * 
	 * @param layout
	 */
	private void defineActions(IPageLayout layout) {
		// Add "new wizards".
		// this Adds it to the File->new menu and the right click menu
		layout.addNewWizardShortcut("lost.tok.newTokWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newDiscussionWizard.NewDiscussionWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newRelationWizard.NewRelationWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newLinkWizard.NewLinkWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.unparsedDocWizard"); //$NON-NLS-1$
		layout.addNewWizardShortcut("lost.tok.newFolder.NewFolder"); //$NON-NLS-1$
		//layout.addNewWizardShortcut("org.eclipse.ui.wizards.new.folder"); //$NON-NLS-1$

		
		// Add "show views".
		// (both thtough the menues and the shortcut on the bottom left corner)
		layout.addShowViewShortcut(ExcerptionView.ID);
		layout.addShowViewShortcut(IPageLayout.ID_RES_NAV);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET);
		layout.addShowViewShortcut(IPageLayout.ID_TASK_LIST);
		layout.addShowViewShortcut(IPageLayout.ID_BOOKMARKS);
		layout.addShowViewShortcut(IPageLayout.ID_EDITOR_AREA);
		layout.addShowViewShortcut("org.eclipse.jdt.ui.PackageExplorer"); //$NON-NLS-1$

	}

}
