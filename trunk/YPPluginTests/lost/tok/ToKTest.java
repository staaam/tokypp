package lost.tok;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;

public class ToKTest extends TestCase {

	// basic project creation test
	public void testCreateProject() throws CoreException, IOException {

		int numProjectsBefore = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects().length;
		new ToK("michalsProj_1", "michalzim", Paths.SOURCE_EXAMPLE);

		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProjects().length == numProjectsBefore + 1);
		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
				"michalsProj_1").exists());
	}

	// tests to see if the project parameters have been set correctly
	public void testProjectProperties() throws CoreException, IOException {

		new ToK("michalsProj_2", "michalzim", Paths.SOURCE_EXAMPLE);

		// check project name
		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
				"michalsProj_2").getName().equals("michalsProj_2"));

		// check creator name
		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
				"michalsProj_2").getPersistentProperty(ToK.creatorQName).compareTo(
				"michalzim") == 0);

		// check root name
//		name = new QualifiedName("TOK Project root", "Root");
//		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
//				"michalsProj_2").getPersistentProperty(name).compareTo(
//				"source_example.src") == 0);

	}

	// ILLEGAL project creation test
	public void testIllegalCreateProject() throws CoreException, IOException {
		// root file not an src file
		int dotLoc = Paths.SOURCE_EXAMPLE.lastIndexOf('.');
		String noExt = Paths.SOURCE_EXAMPLE.substring(0, dotLoc - 1);
		String falseExt = noExt + ".xml";

		new ToK("michalsProj_3", "michalzim", falseExt);
		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
				"michalsProj_3").getFolder("Sources").getFile(falseExt)
				.exists() == false);

		// root file not existant
		new ToK("michalsProj_99", "michalzim",
				Paths.SOURCE_EXAMPLE + "aaa");
		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
				"michalsProj_99").getFolder("Sources").getFile(
				Paths.SOURCE_EXAMPLE + "aaa").exists() == false);
	}

	// testing that the two project Folders were created
	public void testProjectFolders() throws CoreException, IOException {

		new ToK("michalsProj_4", "michalzim",Paths.SOURCE_EXAMPLE);

		// getting the handles to the folders
		IFolder srcFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("michalsProj_4").getFolder("Sources");
		IFolder discFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("michalsProj_4").getFolder("Discussions");

		// checking that the folders exist
		assertTrue(srcFolder.exists() == true);
		assertTrue(discFolder.exists() == true);

	}

	// testing that the project files were created
	public void testProjectFiles() throws CoreException, IOException {

		new ToK("michalsProj_5", "michalzim",Paths.SOURCE_EXAMPLE);

		// getting the handles to the files
		// Authors file
		File authorsFile = new File(ResourcesPlugin.getWorkspace().getRoot()
				.getProject("michalsProj_5").getLocationURI().getPath()
				+ "/Authors.xml");
		// Links file
		File linksFile = new File(ResourcesPlugin.getWorkspace().getRoot()
				.getProject("michalsProj_5").getLocationURI().getPath()
				+ "/Links.xml");

		// checking that the files exist
		assertTrue(authorsFile.exists());
		assertTrue(linksFile.exists());

	}

	// testing that the project root were created correctly
	public void testProjectRoot() throws CoreException, IOException {
		
		// creating the file and getting its path
		IFile sysFile = Paths.getIFile(Paths.SOURCE_EXAMPLE);
		String sysFilePath = sysFile.getRawLocation().makeAbsolute().toOSString();

		// using the file to create a project
		new ToK("michalsProj_6", "michalzim",sysFilePath);

		// Root file
		IFolder srcFolder = ResourcesPlugin.getWorkspace().getRoot()
				.getProject("michalsProj_6").getFolder("root");
		IFile rootFile = srcFolder.getFile("source_example.src");

		// checking that the root exists
		assertTrue(rootFile.exists());
	}

	public void testAddDiscussion() throws CoreException, IOException {
		ToK tok;
		tok = new ToK("AriesProj1", "Arie", Paths.SOURCE_EXAMPLE);
		tok.addDiscussion("test");
		assertTrue(ResourcesPlugin.getWorkspace().getRoot().getProject(
				"AriesProj1").getFolder("Discussions").getFile("test.dis") != null);
	}

	//Evgeni
	//Testing valid source with invalid path
	public void testValidSourceInvalidPath() throws IOException
	{	
		System.out.println("\nTest Valid Source Invalid Path \n");
		ToK tok;
		try {
			tok = new ToK("Evgeni_project", "Evgeni", Paths.BASE + "1" + "Bavel_en.src" );			
		} 
		catch (Exception e){
			System.out.println("Failed to create a project");
			return;
		}

		String sourceFilePath = "C:\\Temp\\Bad_Path\\Rashi_en.src";
		tok.addSource(sourceFilePath);		

		assertTrue(true);
	}

	//Evgeni
	//Testing invalid sources valid path
	public void testInvalidSourceValidPath() throws IOException
	{		
		System.out.println("\nTest Invalid Source Valid Path\n");
		ToK tok;
		try {
			tok = new ToK("Evgeni_project", "Evgeni", Paths.BASE + "2" + "Bavel_en.src" );
		} 
		catch (Exception e){
			System.out.println("Failed to create a project");
			return;
		}

		String sourceFilePath = Paths.BASE + "Rashi_no_tag.src";
		tok.addSource(sourceFilePath);		

		assertTrue(true);
	}
	
	//Evgeni
	//Testing valid new source
	public void testValidSourceValidity() throws IOException
	{		
		System.out.println("\nTest Valid Source Validity\n");
		ToK tok;
		try {
			tok = new ToK("Evgeni_project", "Evgeni", Paths.BASE + "4" + "Bavel_en.src" );
		} 
		catch (Exception e){
			System.out.println("Failed to create a project");
			return;
		}

		String sourceFilePath = Paths.BASE + "2" + "Rashi_en.src";
		tok.addSource(sourceFilePath);		
	
		assertTrue(true);
	}
	
	//Evgeni
	//Testing addition of existing source
	public void testSourceExists() throws IOException
	{		
		System.out.println("\nTest Source Exists\n");
		ToK tok;
		try {
			tok = new ToK("Evgeni_project", "Evgeni", Paths.BASE + "5" + "Bavel_en.src" );
		} 
		catch (Exception e){
			System.out.println("Failed to create a project");
			return;
		}

		String sourceFilePath = Paths.BASE + "3" + "Rashi_en.src";
		tok.addSource(sourceFilePath);
		tok.addSource(sourceFilePath);

		assertTrue(true);
	}

	
	//Evgeni
	//Testing addition of source which author already exists in project
	public void testAuthorExists() throws IOException
	{		
		System.out.println("\nTest Author Exists\n");
		ToK tok;
		try {
			tok = new ToK("Evgeni_project", "Evgeni", Paths.BASE + "6" + "Bavel_en.src" );
		} 
		catch (Exception e){
			System.out.println("Failed to create a project");
			return;
		}

		String sourceFilePath_a = Paths.BASE + "4" + "Rashi_en.src";
		tok.addSource(sourceFilePath_a);
		String sourceFilePath_b = Paths.BASE + "5" + "Rashi_en.src";
		tok.addSource(sourceFilePath_b);

		assertTrue(true);
	}
	
	//Evgeni
	//Testing changing authors rank
	public void testChangeAuthorRank() throws IOException
	{		
		System.out.println("\nTest Change Author Rank\n");
		ToK tok;
		try {
			tok = new ToK("Evgeni_project", "Evgeni", Paths.BASE + "6" + "Bavel_en.src" );
		} 
		catch (Exception e){
			System.out.println("Failed to create a project");
			return;
		}

		String sourceFilePath = Paths.BASE + "4" + "Rashi_en.src";
		tok.addSource(sourceFilePath);
		Author author = new Author();
		author.name = "Rashi";
		author.rank = 3;
		tok.RankChangeByUser(author);

		assertTrue(true);
	}

}
