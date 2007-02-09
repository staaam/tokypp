package lost.tok.linkTable;

import java.util.List;

import lost.tok.Excerption;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.opTable.AbstractEditorAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * An action that sends the selected excerptions to the Excerptions Viewer
 * 
 * @author Team Lost
 * 
 */
public class LinkAction extends AbstractEditorAction {

	public void run(IAction action) {
		IViewPart view = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().findView(ExcerptionView.ID);

		if (view == null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().activate(new ExcerptionView());
			view = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().findView(ExcerptionView.ID);
		}

		ExcerptionView expViewer = (ExcerptionView) view;

		assert (activeEditor != null);
		if (currentSelection == null || currentSelection.isEmpty()
				|| currentSelection.getLength() == 0) {
			return;
		}

		List<Excerption> excerptions = ((LinkageEditor) activeEditor)
				.getMarked();

		IEditorInput input = ((LinkageEditor) activeEditor).getEditorInput();
		String fileName = null;
		FileEditorInput fileEditorInput = null;
		if (input instanceof FileEditorInput) {
			fileEditorInput = (FileEditorInput) input;
			fileName = fileEditorInput.getFile().getName();
		}
		Excerption[] exps = new Excerption[excerptions.size()];
		int i = 0;
		for (Object element0 : excerptions) {
			Excerption element = (Excerption) element0;
			exps[i++] = element;
		}
		expViewer.addExcerptions(fileName, exps, fileEditorInput.getFile());
		expViewer.setFocus();
	}
}
