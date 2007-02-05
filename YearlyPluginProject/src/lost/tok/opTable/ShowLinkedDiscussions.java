package lost.tok.opTable;

import lost.tok.GeneralFunctions;
import lost.tok.ToK;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

public class ShowLinkedDiscussions extends OperationTable implements IObjectActionDelegate {

	ToK tok = null;

	private ISelection selection;
	private IWorkbenchPart targetPart;

	public ShowLinkedDiscussions() {
		super();
		System.out.println("ShowLinkedDiscussions");
	}
	
	protected void hookContextMenu(Control parent) {
	}

	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		// TODO Auto-generated method stub
		super.doSetInput(input);
		
		if (input instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			IFile file = fileEditorInput.getFile();
			ToK tok = ToK.getProjectToK(file.getProject());
			
			GeneralFunctions.readFromXML(tok.getLinkFile());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
		System.out.println("setActivePart");
	}

	public void run(IAction action) {
		System.out.println("run");
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			for (Object o : ss.toList()) {
				if (o instanceof IFile) {
					IFile file = (IFile) o;
					try {
						targetPart.getSite()
							.getWorkbenchWindow()
							.getActivePage()
							.openEditor(
									new FileEditorInput(file),
									"lost.tok.opTable.ShowLinkedDiscussions");
					} catch (PartInitException e) {
						e.printStackTrace();
					}

				}
				
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		System.out.println("selectionChanged");
	}

}
