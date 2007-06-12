package lost.tok.html.other;

import lost.tok.ToK;
import lost.tok.html.IndexPage;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class ExportAction implements IObjectActionDelegate{

	// private IWorkbenchPart workbench = null;
	private ISelection selection = null;
	
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// workbench = targetPart;
	}
	
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void run(IAction action) {
		try {
			if (! (selection instanceof TreeSelection) )
			{
				System.out.println("lost.tok.html.other.ExportAction was actiavated on non tree selection");
				return;
			}
			
			TreeSelection ts = (TreeSelection) selection;
			assert(ts.size() == 1);
			assert( ts.getFirstElement() instanceof IProject );
			
			IProject proj = (IProject)ts.getFirstElement();
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

}
