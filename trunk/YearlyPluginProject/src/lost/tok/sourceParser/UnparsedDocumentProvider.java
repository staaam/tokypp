package lost.tok.sourceParser;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lost.tok.sourceDocument.SourceDocument;

public class UnparsedDocumentProvider extends FileDocumentProvider {

	protected void setDocumentContent(IDocument document,
			InputStream contentStream, String encoding) throws CoreException {
		SourceDocument srcDoc = (SourceDocument)document;
		
		StringBuffer sb = new StringBuffer();
		
		try {
			BufferedReader br = null;
			if (encoding == null)
				br = new BufferedReader( new InputStreamReader(contentStream));
			else
				br = new BufferedReader( new InputStreamReader(contentStream, encoding));
			
			int c = br.read();
			while (c != -1)
			{
				sb.append((char)c);
				c = br.read();
			}			
		} catch (IOException e)
		{
			e.printStackTrace();
			//throw new CoreException(new Status()...
		}
		
		// 	TODO(Shay) add a dialog or something to get the names
		srcDoc.setUnparsed(sb.toString(), "Anna Banana", "Shay");
	}

	@Override
	protected IDocument createEmptyDocument() {
		return new SourceDocument();
	}

	@Override
	protected void doSaveDocument(IProgressMonitor monitor, Object element,
			IDocument document, boolean overwrite) throws CoreException {
		monitor.done(); // FIXME(Shay): Allow document export
	}

}
