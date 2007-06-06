package lost.tok;

import org.eclipse.core.resources.IFile;

public class Source {
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
		String r = ToK.getProjectToK(file.getProject()).getRootFolder()
				.getFullPath().toPortableString();
		return file.getFullPath().toPortableString().startsWith(r);
	}

	/**
	 * Returns string representing the source
	 * 
	 * @return file name (relative to project dir)
	 */
	public String toString() {
		return file.getProjectRelativePath().toPortableString();
	}

	/** Returns the ToK of the source */
	public ToK getTok() {
		return tok;
	}

}
