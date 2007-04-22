package lost.tok.opTable.actions;

import lost.tok.Messages;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.opTable.wizards.NewLinkWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class LinkDiscussionAction extends AbstractEditorAction {
	public void run(IAction action) {
		assert (activeEditor != null);

		IEditorInput editorInput = activeEditor.getEditorInput();		
		if (editorInput instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = (FileEditorInput) editorInput;
			
			IFile file = fileEditorInput.getFile();

			IProject project = file.getProject();

			NewLinkWizard wizard = new NewLinkWizard();
			wizard.init(PlatformUI.getWorkbench(),
					ExcerptionView.getView().getRoots());
			WizardDialog dialog = new WizardDialog(new Shell(), wizard);
			dialog.setTitle(Messages.getString("ExcerptionView.7")); //$NON-NLS-1$
			dialog.updateSize();
			dialog.create();
			wizard.setProjectName(project.getName());
			dialog.open();
		}
		//ExcerptionView.getView().linkDiscussionAction()
	}
}
