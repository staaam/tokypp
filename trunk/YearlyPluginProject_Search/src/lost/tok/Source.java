package lost.tok;

import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import lost.tok.activator.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class Source {
	public static final Object SOURCE_EXTENSION = "src";
	
	/** The IFile of the source */
	IFile file;
	/** The tree of knowledge this source belongs to */
	ToK tok;

	/**
	 * Creates Source object
	 * 
	 * @param tok -
	 *            related source
	 * @param fileName -
	 *            source file (relative to project dir)
	 */
	public Source(ToK tok, String fileName) {
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
			StreamSource ss = new StreamSource(Activator.sourceXsdPath
					+ "source.xsd"); //$NON-NLS-1$
			Schema schema = factory.newSchema(ss);
			
			Validator validator = schema.newValidator();
			validator.validate(streamSource);
			return true;
		} catch (Exception e) {
			return false;
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

}







