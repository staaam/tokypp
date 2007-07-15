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

package lost.tok.html.other;

import lost.tok.ToK;
import lost.tok.html.IndexPage;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This action creates an html site for the ToK project, on the user's disk
 * @author Team Lost
 */
public class ExportAction implements IObjectActionDelegate{

	// private IWorkbenchPart workbench = null;
	private ISelection selection = null;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// workbench = targetPart;
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	/**
	 * Creates an html site for the ToK project, on the user's disk
	 */
	public void run(IAction action) {
		try {
			if (! (selection instanceof TreeSelection) )
			{
				System.out.println("lost.tok.html.other.ExportAction was actiavated on non tree selection"); //$NON-NLS-1$
				return;
			}
			
			TreeSelection ts = (TreeSelection) selection;
			assert( ts.size() == 1 );
			assert( ts.getFirstElement() instanceof IProject );
			
			IProject proj = (IProject)ts.getFirstElement();
			deleteHTMLDir(proj);
			
			ToK tok = ToK.getProjectToK(proj);
			
			IndexPage page = new IndexPage(tok);
			page.generatePage();
		}
		catch (Exception e)
		{
			// Generally, exceptions shouldn't occur in the above code
			// We want to catch and debug any runtime exceptions occuring
			// So we have added this catch clause
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Deletes the html folder of the given project
	 * @param tokProj the project to delete the html folder from
	 */
	public void deleteHTMLDir(IProject tokProj)
	{
		IFolder htmlFolder = tokProj.getFolder(ToK.HTML_FOLDER);
		try {
			htmlFolder.delete(true, null);
		} catch (CoreException e)	{
			System.err.println("Error deleting html directory before generating a new one\n"); //$NON-NLS-1$
			e.printStackTrace();
		}
	}

}
