package lost.tok;

import java.io.IOException;
import junit.framework.TestCase;
import org.eclipse.core.runtime.CoreException;

/**
 * Tests the authors file handler
 * @author evgeni
 */
public class AuthorsTest extends TestCase {

	/**
	 * Creates a new Tree Of Knowledge
	 * @param projectName The name of the new project
	 * @return the new ToK which was created
	 */
	static public ToK creation(String projectName) throws CoreException,
			IOException {
		ToK tok = new ToK(projectName, "Evgeni", "");
		return tok;
	}
	
	/**
	 * Test add author
	 * @throws CoreException
	 * @throws IOException
	 */
	public void testAddAuthor() throws CoreException, IOException {
		ToK tok = creation("test");

		AuthorsHandler authHanlde = new AuthorsHandler(tok);
		
		authHanlde.addAuthor(new Author(new Integer(1),"Evgeni"),new Integer(1));

		int relocAuthNewRank = authHanlde.getAuthorRank("Evgeni");
		
		assertTrue(relocAuthNewRank == 1);
	}
	
	/**
	 * Test relocate Author
	 * @throws CoreException
	 * @throws IOException
	 */
	public void testRelocateAuthor() throws CoreException, IOException {
		ToK tok = creation("test2");

		AuthorsHandler authHanlde = new AuthorsHandler(tok);
		
		authHanlde.addAuthor(new Author(new Integer(1),"Evgeni"),new Integer(1));
		authHanlde.relocateAuthor("Evgeni", new Integer(2));

		int relocAuthNewRank = authHanlde.getAuthorRank("Evgeni");
		assertTrue(relocAuthNewRank == 2);
	}

}
