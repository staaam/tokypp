package lost.tok.sourceParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lost.tok.Messages;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

/**
 * This was supposed to be the document provider for unparsed document.
 * Currnetly however, it is unused.
 * This was left here for the third iteration, perhaps
 * @author Shay
 */
public class UnparsedDocumentProvider extends FileDocumentProvider {

	protected void setDocumentContent(IDocument document,
			InputStream contentStream, String encoding) throws CoreException {
		SourceDocument srcDoc = (SourceDocument) document;

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = null;
			if (encoding == null)
				br = new BufferedReader(new InputStreamReader(contentStream));
			else
				br = new BufferedReader(new InputStreamReader(contentStream,
						encoding));

			int c = br.read();
			while (c != -1) {
				sb.append((char) c);
				c = br.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
			// throw new CoreException(new Status()...
		}

		// unused...
		srcDoc.setUnparsed(sb.toString(), "Anna Banana", "Shay"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected IDocument createEmptyDocument() {
		return new SourceDocument();
	}

}
