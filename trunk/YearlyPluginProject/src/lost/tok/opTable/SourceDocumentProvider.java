/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

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