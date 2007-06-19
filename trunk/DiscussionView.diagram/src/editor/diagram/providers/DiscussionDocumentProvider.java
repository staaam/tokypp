package editor.diagram.providers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

public class DiscussionDocumentProvider implements IDocumentProvider {

	public void aboutToChange(Object element) {
		// TODO Auto-generated method stub

	}

	public void addElementStateListener(IElementStateListener listener) {
		// TODO Auto-generated method stub

	}

	public boolean canSaveDocument(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	public void changed(Object element) {
		// TODO Auto-generated method stub

	}

	public void connect(Object element) throws CoreException {
		// TODO Auto-generated method stub

	}

	public void disconnect(Object element) {
		// TODO Auto-generated method stub

	}

	public IAnnotationModel getAnnotationModel(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	public IDocument getDocument(Object element) {
		return null;
	}

	public long getModificationStamp(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getSynchronizationStamp(Object element) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isDeleted(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mustSaveDocument(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeElementStateListener(IElementStateListener listener) {
		// TODO Auto-generated method stub

	}

	public void resetDocument(Object element) throws CoreException {
		// TODO Auto-generated method stub

	}

	public void saveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		// TODO Auto-generated method stub

	}

}
