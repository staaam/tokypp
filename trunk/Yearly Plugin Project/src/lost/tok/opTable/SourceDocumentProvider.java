package lost.tok.opTable;

import java.io.InputStream;
import java.util.List;

import lost.tok.opTable.sourceDocument.SourceDocument;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.ProcessingInstruction;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.part.FileEditorInput;

public class SourceDocumentProvider extends FileDocumentProvider {
	protected void setDocumentContent(IDocument document,
			InputStream contentStream, String encoding) throws CoreException {
		try {
			// contentStream.reset();

			SAXReader reader = new SAXReader();

			((SourceDocument) document).set(reader.read(contentStream));
		} catch (DocumentException e) {
			e.printStackTrace();
		}

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