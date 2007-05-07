package lost.tok;

import org.eclipse.core.resources.IFile;

public class Source {
	IFile file;

	/**
	 * Creates Source object
	 * 
	 * @param tok - related source
	 * @param fileName - source file (relative to project dir)
	 */
	public Source(ToK tok, String fileName) {
		file = tok.getProject().getFile(fileName);
	}

	public Source(IFile file) {
		this.file = file;
	}

	public IFile getFile() {
		return file;
	}
	
	public String toString() {
		return file.getProjectRelativePath().toPortableString();
	}

}
