package lost.tok.html;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Source;
import lost.tok.ToK;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * Creates, Generates and Picks CSS files for the html exporter
 * @author Team Lost
 */
public class CSSManager 
{
	/** Pictures, Icons and scripts which should always be generated */
	private static final String[] extraFilesToGen = { 
		ToK.HTML_FOLDER + "/other/bg/orang117.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/dtree.js",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/base.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/empty.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/folder.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/folderopen.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/join.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/joinbottom.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/line.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/minus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/minusbottom.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/nolines_minus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/nolines_plus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/page.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/plus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/plusbottom.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/tree1.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/tree2.jpg",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/disc.png",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/source.png",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/img/root.png",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/base.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/empty.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/folder.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/folderopen.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/join.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/joinbottom.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/line.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/minus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/minusbottom.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/nolines_minus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/nolines_plus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/page.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/plus.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/plusbottom.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/tree1.gif",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/tree2.jpg",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/disc.png",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/source.png",  //$NON-NLS-1$
		ToK.HTML_FOLDER + "/other/menu/imgRTL/root.png",  //$NON-NLS-1$
	 }; 
	
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
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			return HTMLPage.getHTMLRelPath(proj, path) + DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS); //$NON-NLS-1$
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
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			return HTMLPage.getHTMLRelPath(proj, path) + DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS); //$NON-NLS-1$
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
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			return "../" + DEFAULT_CSS; //$NON-NLS-1$
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS); //$NON-NLS-1$
			return "../" + DEFAULT_RTL_CSS; //$NON-NLS-1$
		}		
	}
	
	/** Adds the index page and returns the path to the css file */
	public String addIndexPage()
	{
		if (GeneralFunctions.isLTR())
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			return DEFAULT_CSS;
		}
		else
		{
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_CSS); //$NON-NLS-1$
			pathsToGen.add(ToK.HTML_FOLDER + "/" + DEFAULT_RTL_CSS); //$NON-NLS-1$
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
		InputStream s = GeneralFunctions.getInputStream(filepath);
		
		// create the path inside the project
		IProject proj = tok.getProject();
		HTMLPage.generatePath(proj, filepath);
		
		// create the file itself
		IFile file = proj.getFile(filepath);
		
		if (!file.exists())
			file.create(s, true, new NullProgressMonitor());
	}

}
