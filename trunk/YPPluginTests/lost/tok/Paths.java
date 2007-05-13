package lost.tok;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * Contains the paths to all the used test files
 */
public class Paths {
	
	/** The name of the tests plug-in */
	private static final String BUNDLE_NAME = "YPPluginTests";

	/** A friendly message for FileNotFound */
	static public final String FILE_NOT_FOUND_MESSAGE = "File Not Found";

	/** The best folder which should contain all the tests */
	final static public String BASE = "TestFiles/";

	final static public String SOURCE_EXAMPLE = BASE + "source_example.src";

	final static public String RASHI_EN = BASE + "Rashi_en.src";

	final static public String BABEL_EN = BASE + "Bavel_en.src";

	final static public String OR_AHAIM_EN = BASE + "Or_Ahaim_en.src";

	final static public String OR_AHAIM_HE = BASE + "Or_Ahaim_he.src";

	final static public String SHORT_AHAIM_EN = BASE + "Short_Ahaim_en.src";

	final static public String OR_AHAIM_EN_RES = BASE + "Or_Ahaim_en.res";

	final static public String OR_AHAIM_HE_RES = BASE + "Or_Ahaim_he.res";

	final static public String SHORT_AHAIM_EN_RES = BASE + "Short_Ahaim_en.res";
	
	/**
	 * Returns an input stream of an internal test file
	 * @param filePath the path to the file in the project's files
	 * @return input stream of the file
	 * @throws IOException if opening fails
	 */
	static public InputStream getInputStream(String filePath) throws IOException{
		Path path = new Path(filePath);
        Bundle bundle = Platform.getBundle(BUNDLE_NAME);

        return FileLocator.openStream(bundle, path, false);
	}

	/**
	 * Returns an IFile of an internal test file
	 * 
	 * Creates a new project (called testFiles) and copies the requested file into it
	 * Returns an IFile pointing to that file
	 * 
	 * @param filePath the path to the file in the project's files
	 * @return an IFile with the same content
	 * @throws IOException if opening fails
	 * @throws CoreException if there's an internal failure
	 */
	static public IFile getIFile(String filepath) throws CoreException, IOException
	{
		// get, open or create the project
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject("TestFiles");
		
		if (!proj.exists())
			proj.create(new NullProgressMonitor());

		if (!proj.isOpen())
			proj.open(new NullProgressMonitor());
		
		// get or create the TestFiles folder 
		IFolder folder = proj.getFolder("TestFiles");
		
		if (!folder.exists())
		{
			folder.create(true, true, new NullProgressMonitor());
			proj.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		
		InputStream s = getInputStream(filepath);
		
		IFile file = proj.getFile(filepath);
		
		if (!file.exists())
		{
			file.create(s, true, new NullProgressMonitor());
			proj.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		
		return file; 
	}
}
