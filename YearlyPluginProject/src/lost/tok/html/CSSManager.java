package lost.tok.html;

import java.io.IOException;
import java.io.InputStream;

import lost.tok.ToK;
import lost.tok.activator.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Creates, Generates and Picks CSS files for the html exporter
 * @author Team Lost
 */
public class CSSManager 
{
	/** The project's tok */
	private ToK tok;
	
	public CSSManager(ToK tok) {
		this.tok = tok;
	}
	
	/**
	 * Generates all the pages used by the site
	 */
	public void generatePages()
	{
		try {
			generateFile(tok, ToK.HTML_FOLDER + "/" + HTMLPage.DEFAULT_CSS);
			generateFile(tok, ToK.HTML_FOLDER + "/other/bg/orang117.gif");
		} catch (CoreException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates an IFile from an internal file
	 * 
	 * The IFile will be created in the same path as filepath
	 * 
	 * @param filePath the path to the file in the project's files
	 * @throws IOException if opening fails
	 * @throws CoreException if there's an internal failure
	 */
	static private void generateFile(ToK tok, String filepath) throws CoreException, IOException
	{	
		// get the file from the bundle
		Path path = new Path(filepath);
        Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		InputStream s = FileLocator.openStream(bundle, path, false);
		
		// create the path inside the project
		IProject proj = tok.getProject();
		HTMLPage.generatePath(proj, filepath);
		
		// create the file itself
		IFile file = proj.getFile(filepath);
		
		if (!file.exists())
			file.create(s, true, new NullProgressMonitor());
	}

}
