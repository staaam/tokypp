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

package lost.tok;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class Source {
	public static final Object SOURCE_EXTENSION = "src"; //$NON-NLS-1$
	
	/** The IFile of the source */
	IFile file;
	/** The tree of knowledge this source belongs to */
	ToK tok;
	
	private SourceDocument srcDoc = null;

	/**
	 * Creates Source object
	 * 
	 * @param tok -
	 *            related source
	 * @param fileName -
	 *            source file (relative to project dir)
	 */
	public Source(ToK tok, String fileName) {
		if (tok == null)
			throw new IllegalArgumentException("ToK must not be null"); //$NON-NLS-1$
		this.tok = tok;
		this.file = tok.getProject().getFile(fileName);
	}

	/**
	 * Creates Source object
	 * 
	 * @param file -
	 *            source file
	 */
	public Source(IFile file) {
		this.tok = ToK.getProjectToK( file.getProject() );
		this.file = file;
	}

	/**
	 * Returns related IFile
	 * 
	 * @return related source IFile
	 */
	public IFile getFile() {
		return file;
	}

	/**
	 * Returns whether the source is root
	 * 
	 * @return true if source is root, and false if not
	 */
	public boolean isRoot() {
		return GeneralFunctions.fileInFolder(file, ToK.getProjectToK(file.getProject()).getRootsFolder());
	}

	/**
	 * Returns string representing the source
	 * 
	 * @return file name (relative to project dir)
	 */
	public String toString() {
		return file.getProjectRelativePath().toPortableString();
	}
	
	public static boolean isValid(InputStream inputStream) {
		return isValid(new StreamSource(inputStream));
	}

	// Validate source.xml file with source.xsd
	public static boolean isValid(StreamSource streamSource) {
		try {
			final String sl = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(sl);
			StreamSource ss = new StreamSource(GeneralFunctions.getInputStream("other/source.xsd")); //$NON-NLS-1$
			Schema schema = factory.newSchema(ss);
			
			Validator validator = schema.newValidator();
			validator.validate(streamSource);
			return true;
		} catch (Exception e) {
			return true;
		}
	}

	public boolean isValid() {
		try {
			InputStream content = file.getContents(true);
			return isValid(content);
		} catch (CoreException e) {
			return false;
		}
	}

	public static boolean isSource(IFile file) {
		if (!file.getFileExtension().equals(SOURCE_EXTENSION))
			return false;
		
		ToK tok = ToK.getProjectToK(file.getProject());
		if (tok == null) return false;
		
		return (GeneralFunctions.fileInFolder(file, tok.getSourcesFolder())
				|| GeneralFunctions.fileInFolder(file, tok.getRootsFolder()));
	}

	/** Returns the ToK of the source */
	public ToK getTok() {
		return tok;
	}
	
	/** Returns the name of the file's author */
	public String getAuthor() {
		return getSourceDocument().getAuthor();
	}
	
	public int getAuthorRank() {
		AuthorsHandler authHandler = new AuthorsHandler(tok);
		
		return authHandler.getAuthorRank(getAuthor());
	}
	
	/** Returns the internal name of the source (i.e. its title) */
	public String getTitle() {
		return getSourceDocument().getTitle();
	}
	
	/** Returns SourceDocument of the source */
	public SourceDocument getSourceDocument() {
		initSourceDocument();
		return srcDoc;
	}

	/** Initializes the title and author field of the source, if needed */
	private void initSourceDocument() {
		if (srcDoc != null)
			return;

		srcDoc = new SourceDocument();
		srcDoc.set(this);
	}

}







