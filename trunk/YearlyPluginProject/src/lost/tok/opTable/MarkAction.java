package lost.tok.opTable;

import java.util.List;

import lost.tok.Excerption;
import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public class MarkAction extends AbstractEditorAction {

	public void run(IAction action) {
		assert (activeEditor != null);
		if (currentSelection == null || currentSelection.isEmpty()
				|| currentSelection.getLength() == 0) {
			return;
		}

		((OperationTable) activeEditor).mark(currentSelection);
		
		updateExcerptionView();
	}

	private void updateExcerptionView() {
		assert (activeEditor != null);
		if (currentSelection == null || currentSelection.isEmpty()
				|| currentSelection.getLength() == 0) {
			return;
		}

		OperationTable opTable = (OperationTable) activeEditor;
		

		FileEditorInput fileEditorInput = 
			(FileEditorInput)opTable.getEditorInput();
		IFile file = fileEditorInput.getFile();
		String fileName = file.getName();
		
		List<Excerption> excerptions = opTable.getMarked();
		Excerption[] exps = new Excerption[excerptions.size()];
		excerptions.toArray(exps);

		ExcerptionView.getView().setExcerptions(fileName, exps, file);
		//expViewer.setFocus();
	}
}
