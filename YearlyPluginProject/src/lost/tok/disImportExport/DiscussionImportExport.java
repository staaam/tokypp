package lost.tok.disImportExport;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

public class DiscussionImportExport extends Action 
implements IWorkbenchWindowActionDelegate {
	/** Called when the action is created. */
	public void init(IWorkbenchWindow window) {
	}

	/** Called when the action is discarded. */
	public void dispose() {
	}

	/** Called when the action is executed. */
	public void run(IAction action) {
		
	    Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay());
	    shell.open();
	}

	/** Called when objects in the editor are selected or deselected. */
	public void selectionChanged(IAction action, ISelection selection) {
	}
	}
