package lost.tok.opTable;

import java.io.InputStream;

import lost.tok.GeneralFunctions;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class SourceDocumentProvider extends FileDocumentProvider {
	protected void setDocumentContent(IDocument document,
			InputStream contentStream, String encoding) throws CoreException {
		((SourceDocument) document).set(GeneralFunctions
				.readFromXML(contentStream));
	}

	@Override
	protected IDocument createEmptyDocument() {
		return new SourceDocument();
	}

	// @Override
	// protected boolean setDocumentContent(IDocument document, IEditorInput
	// editorInput, String encoding) throws CoreException {
	// if (editorInput instanceof FileEditorInput) {
	// FileEditorInput fileEditorInput = (FileEditorInput) editorInput;
	// fileEditorInput.g
	// }
	// return super.setDocumentContent(document, editorInput, encoding);
	// }

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		monitor.done();
		// we don't want to change the xml
	}

}