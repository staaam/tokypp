package lost.tok;

import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.ui.IEditorInput;

import junit.framework.TestCase;
import lost.tok.opTable.OperationTable;
import lost.tok.opTable.SourceDocumentProvider;
import lost.tok.sourceDocument.SourceDocument;

/**
 * Due to the difficulties with creating and testing an Editor using the
 * Eclipses Interface, Editor tests will be done manually
 */
public class OperationtTable_test extends TestCase {

	public void testStub() {
		// Nothing to test here
	}

	/*
	 * public void testLoadSource() { fail("Not yet implemented"); }
	 * 
	 * public void testDisplay() { fail("Not yet implemented"); }
	 * 
	 * public void testMark() { fail("Not yet implemented"); }
	 * 
	 * 
	 * public void testCreate() { OperationTable opT = new OperationTable();
	 * SourceDocumentProvider sdp =
	 * (SourceDocumentProvider)opT.getDocumentProvider(); SourceDocument doc =
	 * loadDoc(Paths.OR_AHAIM_EN); try { sdp.connect(doc); } catch
	 * (CoreException e) { e.printStackTrace(); } opT.setInput(input)
	 * opT.mark(new TextSelection(5, 40)); List<Excerption> excepts =
	 * opT.getMarked(); for (Excerption e : excepts) { System.out.println(e); } }
	 * 
	 * 
	 * protected SourceDocument loadDoc (String sourceFilename) { SAXReader
	 * reader = new SAXReader(); reader.setEncoding("UTF-8");
	 * 
	 * SourceDocument doc = new SourceDocument();
	 * 
	 * try { doc.set(reader.read(sourceFilename)); } catch (DocumentException e) {
	 * fail(Paths.FILE_NOT_FOUND_MESSAGE); } return doc; }
	 * 
	 * 
	 * 
	 * public void testCut1() { fail("Not yet implemented"); /* Source src = new
	 * Source();
	 * 
	 * OperationTable opTable = new OperationTable(); // can this exist alone?
	 * 
	 * opTable.loadSource(src);
	 * 
	 * opTable.cut(10, 30); List<Excerption> dispEList =
	 * opTable.getDislpayed();
	 * 
	 * assertEquals(2, dispEList.size()); assertEquals("Some text",
	 * dispEList.get(0).toString()); assertEquals("Some other text",
	 * dispEList.get(1).toString()); }
	 * 
	 * public void testCut2() { fail("Not yet implemented"); /*
	 * 
	 * Source src = new Source();
	 * 
	 * OperationTable opTable = new OperationTable(); // can this exist alone?
	 * 
	 * opTable.loadSource(src);
	 * 
	 * List<Excerption> dispEList = opTable.getDislpayed(); String :=
	 * getTextFrom(dispEList);
	 * 
	 * for (int i=0; i < 5; i++) { opTable.cut(10, 30); dispEList =
	 * opTable.getDislpayed(); assertEquals(x, dispEList.size());
	 * assertEquals("Some text", dispEList.get(0).toString());
	 * assertEquals("Some other text", dispEList.get(1).toString()); }
	 *  }
	 * 
	 * public void testGetMarked() { fail("Not yet implemented"); }
	 */
}

/*
 * Thoughts: Test 1: Load a source, delete some text, verify that the remaining
 * text does not contain the removed text (unless titles were marked)
 * 
 * Test 2: Load a source, delete some text, verify that the remaining text does
 * not contain the removed text (unless titles were marked). Repeat 5 times
 * 
 * Test 3: Load a source, mark some text, verify that it is indeed marked (using
 * something like a getMarked() method)
 * 
 * Test 4: Load a source, mark some text, delete that text. Verify that no text
 * is marked, and that the remaining text does not contain the deleted text.
 * 
 * Test 5: Load a source, mark some text, delete half of it + the next sentence.
 * Verify that the deleted text does not appear in the source and is not marked.
 * 
 * Test 6: Load a source. Mark some text x5 times. Verify that the correct text
 * is marked.
 * 
 * Test 7: Repeat tests 3, 4, 5, 6, and after each one create new quote. Verify
 * that the new quote is made up of only the marked text.
 */
