package lost.tok.linkTable;

import java.util.Iterator;
import java.util.List;

import lost.tok.Excerption;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.opTable.AbstractEditorAction;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

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
				|| currentSelection.getLength() == 0)
			return;

		List<Excerption> excerptions = ((LinkageEditor) activeEditor)
				.getMarked();

		IEditorInput input = ((LinkageEditor) activeEditor).getEditorInput();
		String fileName = null;
		if (input instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			fileName = fileEditorInput.getFile().getName();
		}
		Excerption[] exps = new Excerption[excerptions.size()];
		int i = 0;
		for (Iterator iter = excerptions.iterator(); iter.hasNext();) {
			Excerption element = (Excerption) iter.next();
			exps[i++] = element;
		}
		expViewer.addExcerptions(fileName, exps);
		expViewer.setFocus();
	}
}
