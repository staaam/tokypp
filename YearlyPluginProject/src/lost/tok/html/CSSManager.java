package lost.tok.html;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.activator.Activator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
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
	/** Pictures, Icons and scripts which should always be generated */
	private static final String[] extraFilesToGen = { ToK.HTML_FOLDER + "/other/bg/orang117.gif" }; //$NON-NLS-1$
	
	/** The CSS file used by most of the html project */
	public static String DEFAULT_CSS = "other/nice.css"; //$NON-NLS-1$
	
	/** The CSS file used by RTL langs */
	public static String DEFAULT_RTL_CSS = "other/niceRTL.css"; //$NON-NLS-1$
	
	/** The project's tok */
	private ToK tok;
	/** Paths of files which should be generated */
	private HashSet<String> pathsToGen;
	
	public CSSManager(ToK tok) {
		this.tok = tok;
		pathsToGen = new HashSet<String>();
		
		for (String path : extraFilesToGen)
			pathsToGen.add(path);
	}
	
	/**
	 * Adds a source to the manager
	 * @param src the source whose css should be managed
	 * @return the path from the matching source page to the css file
	 */
	public String add(Source src)
	{
		IProject proj = src.getTok().getProject();
		IPath path = src.getFile().getProjectRelativePath();
			
		if (GeneralFunctions.isLTR())
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			return HTMLPage.getHTMLRelPath(proj, path) + DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS);
			return HTMLPage.getHTMLRelPath(proj, path) + DEFAULT_RTL_CSS;
		}
	}
	
	/**
	 * Adds a discussion to the manager
	 * @param disc the discussion whose css should be managed
	 * @return the path from the discussion page to the css file
	 */
	public String add(Discussion disc)
	{
		IProject proj = disc.getMyToK().getProject();
		IPath path = disc.getFile().getProjectRelativePath();
		
		if (GeneralFunctions.isLTR())
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			return HTMLPage.getHTMLRelPath(proj, path) + DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS);
			return HTMLPage.getHTMLRelPath(proj, path) + DEFAULT_RTL_CSS;
		}	
	}
	
	/**
	 * Adds a discussion conflict to be managed by the css manager
	 * @param i the index of the conflict
	 * @return the path from the discussion to the css
	 */
	public String addDiscConflict(int i)
	{
		if (GeneralFunctions.isLTR())
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			return "../" + DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS);
			return "../" + DEFAULT_RTL_CSS;
		}		
	}
	
	/** Adds the index page and returns the path to the css file */
	public String addIndexPage()
	{
		if (GeneralFunctions.isLTR())
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			return DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS);
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS);
			return DEFAULT_RTL_CSS;
		}	
	}
	
	/**
	 * Generates all the pages used by the site
	 */
	public void generatePages()
	{
		String[] paths = new String[pathsToGen.size()];
		paths = pathsToGen.toArray(paths);
		for (String path : paths)
		{
			try {
				generateFile(tok, path); 
			} catch (CoreException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
