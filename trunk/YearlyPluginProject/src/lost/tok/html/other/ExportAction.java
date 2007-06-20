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
	 * @throws CoreException when the deletion fails
	 */
	public void deleteHTMLDir(IProject tokProj) throws CoreException
	{
		IFolder htmlFolder = tokProj.getFolder(ToK.HTML_FOLDER);
		htmlFolder.delete(true, null);		
	}

}
