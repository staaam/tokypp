package lost.tok.opTable;

import java.io.InputStream;

import lost.tok.GeneralFunctions;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

/**
 * Provides Source Documents to the Operation Table editor
 */
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

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		monitor.done();
		// we don't want to change the xml
	}

}