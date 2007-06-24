package lost.tok.sorter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

/**
 * Determines the order of sources  
 * @author Shay
 *
 */
public class Sorter {
	
	/** The folder this sorter sorts */
	private IFolder folder;
	
	/**
	 * Creates a new sorter for the files in this directory
	 * @param folder the folder to sort
	 */
	public Sorter(IFolder folder)
	{
		this.folder = folder;
	}
	
	/**
	 * Reads the sorter.xml file and returns the resources described in it, according to their order
	 * 
	 * Note(Shay): Currently is a stub
	 */
	public IResource[] getSorted()
	{
		IResource[] resArr = null;
		try {
			resArr = folder.members();
		} catch (CoreException e) {
			e.printStackTrace();
		}
		
		// prints error messages for non file/dir resources
		for (IResource res : resArr)
		{
			if (!(res instanceof IFile) && !(res instanceof IFolder))
				System.err.println("Error: Resource " + res.getFullPath() + " is not a file or folder");
		}
		
		return resArr;
	}

}
