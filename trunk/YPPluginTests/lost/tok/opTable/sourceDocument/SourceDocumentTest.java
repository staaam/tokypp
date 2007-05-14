package lost.tok.opTable.sourceDocument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import junit.framework.TestCase;
import lost.tok.GeneralFunctions;
import lost.tok.Paths;
import lost.tok.ToK;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

/**
 * A set of tests for the source document
 */
public class SourceDocumentTest extends TestCase {

	/**
	 * Reads an English document Verifies that the displayed result is ok
	 */
	public void testSetDocumentEng() {
		final String[] sourceFilenames = { Paths.OR_AHAIM_EN,
				Paths.SHORT_AHAIM_EN };
		final String[] expectedResFileNames = { Paths.OR_AHAIM_EN_RES,
				Paths.SHORT_AHAIM_EN_RES };

		assertEquals(sourceFilenames.length, expectedResFileNames.length); // testing
		// the
		// test

		for (int i = 0; i < sourceFilenames.length; i++) {
			SourceDocument doc = loadDoc(sourceFilenames[i]);
			String actual = doc.get();
			String expected = readFile(expectedResFileNames[i]);
			assertEquals("fails in Hebrew mode", expected, actual);
		}
	}
	
	/** 
	 * Tests that document creation works as it should, for the source parser
	 * This method creates a short document, and verifies that it contains 
	 * a title, a single chpater and the original text 
	 */
	public void testCreateDocToParse()
	{
		Chapter c = null;
		String text = "This is the story of an unparsed document"; 
		
		SourceDocument doc = new SourceDocument();
		doc.setUnparsed(text,"Source Parser","Shay Nahum");
		assertEquals("Title:\tSource Parser\nBy   :	Shay Nahum\n\n" +
				" 1:\t(Unparsed Text)\n" + text + "\n",
				doc.getChapterFromOffset(0).toString());
		c = doc.getChapterFromOffset(50);
		assertEquals(" 1:\t(Unparsed Text)\n" + text + "\n", c.toString());
	}
	
	/** Simple test for the creation of a new chpater */
	public void testCreateNewChapter()
	{
		String text = "This is the story of an unparsed document"; 
		
		SourceDocument doc = new SourceDocument();
		doc.setUnparsed(text,"Source Parser","Shay Nahum");
		
		doc.createNewChapter(30, "Document?");
	}
	
	/** 
	 * ABC parsing text
	 * Given a list of the abc letters, splits each one to a seperate chapter
	 * Verifies that they were split correctly
	 */
	public void testCreateNewChapter2()
	{
		String text = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
		String letters = "abcdefghijklmnopqrstuvwxyz";
				
		SourceDocument doc = new SourceDocument();
		doc.setUnparsed(text,"ABC","Eliezer Ben-Yehuda");
		
		int caret_loc = 0;
		for (int i = 0; i < (text.length()+1)/2; i ++)
		{
			boolean isAtChapterStart = (doc.getChapterFromOffset(caret_loc).getOffset() == caret_loc);
			
			// 10000 is set to avoid inf loops during the tests
			while (isAtChapterStart || (doc.createNewChapter(caret_loc, ""+i) == -1 && caret_loc < 10000))
			{
				caret_loc ++;
				isAtChapterStart = (doc.getChapterFromOffset(caret_loc).getOffset() == caret_loc);
			}
		}
		
		int numChapterTexts = 0;
		int i = 0;
		for (Chapter c : doc.getAllChapters())
		{
			if (c instanceof ChapterText)
			{
				numChapterTexts ++;
				// verifies that the chapters content is as it should be
				assertEquals("" + letters.charAt(i), c.toString().trim());
				i++;
			}
		}
		// verifies that we've passed on all the chapters
		assertEquals((text.length() + 1) / 2, numChapterTexts);
	}
	
	/** 
	 * Checks the preverified abc parsing can be parsed and loaded
	 */
	public void testToXml()
	{
		/* create the initial abc document */
		String text = "a b c d e f g h i j k l m n o p q r s t u v w x y z";
		
		SourceDocument baseDoc = new SourceDocument();
		baseDoc.setUnparsed(text,"ABC","Eliezer Ben-Yehuda");
		
		int caret_loc = 0;
		for (int i = 0; i < (text.length()+1)/2; i ++)
		{
			boolean isAtChapterStart = (baseDoc.getChapterFromOffset(caret_loc).getOffset() == caret_loc);
			
			// 10000 is set to avoid inf loops during the tests
			while (isAtChapterStart || (baseDoc.createNewChapter(caret_loc, ""+i) == -1 && caret_loc < 10000))
			{
				caret_loc ++;
				isAtChapterStart = (baseDoc.getChapterFromOffset(caret_loc).getOffset() == caret_loc);
			}
		}
		
		/* Write that document to a file, read it back, and compare */
		IFile srcFile;
		
		IProject proj = ResourcesPlugin.getWorkspace().getRoot().getProject("tmlTemp");
		if (!proj.exists())
		{
			new ToK("tmlTemp", "tml", "none");
		}
		srcFile = proj.getFile(ToK.SOURCES_FOLDER + "\\mesrc.src");
		if (srcFile.exists())
		{
			try {
				srcFile.delete(true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
				
		baseDoc.toXML(proj, "mesrc.src");
		
		Document secDoc = GeneralFunctions.readFromXML(srcFile);
		
		SourceDocument doc2 = new SourceDocument();
		doc2.set(secDoc);
		
		assertEqualDocuments(baseDoc, doc2);
	}

	/** 
	 * Checks the getChapterFromOffset method.
	 * Calls the function with various interesting
	 * inputs and verifies correctness
	 */
	public void testGetChapterFromOffset() {
		SourceDocument doc = loadDoc(Paths.OR_AHAIM_EN);
		Chapter c = null;

		c = doc.getChapterFromOffset(100);
		assertEquals("fails in Hebrew mode", 85, c.getOffset().intValue());
		assertEquals(16, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getName());

		c = doc.getChapterFromOffset(101);
		assertEquals(101, c.getOffset().intValue());
		assertEquals(64 + 401, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getParent().getName());
		assertTrue(c instanceof ChapterText);
		assertEquals("Genesis/chapter 11/Verse 1", ((ChapterText) c)
				.getExcerptionPath());

		c = doc.getChapterFromOffset(150);
		assertEquals(101, c.getOffset().intValue());
		assertEquals(64 + 401, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getParent().getName());

		c = doc.getChapterFromOffset(565);
		assertEquals(101, c.getOffset().intValue());
		assertEquals(64 + 401, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getParent().getName());

		c = doc.getChapterFromOffset(2664);
		assertEquals(2588, c.getOffset().intValue());
		assertEquals(520, c.getInnerLength().intValue());
		assertEquals("Verse 7", c.getParent().getName());
		assertTrue(c instanceof ChapterText);
		assertEquals("Genesis/chapter 11/Verse 7", ((ChapterText) c)
				.getExcerptionPath());

		c = doc.getChapterFromOffset(4000);
		assertEquals(3384, c.getOffset().intValue());
		assertEquals(740, c.getInnerLength().intValue());
		assertEquals("Verse 9", c.getParent().getName());
		assertTrue(c instanceof ChapterText);
		assertEquals("Genesis/chapter 11/Verse 9", ((ChapterText) c)
				.getExcerptionPath());
	}
	
	/**
	 * Verifies that the two docs are equal
	 * @param d1 first doc to compare
	 * @param d2 second doc to compare
	 */
	protected void assertEqualDocuments(SourceDocument d1, SourceDocument d2)
	{	
		assertEquals(d1.toString(), d2.toString());
	}

	/**
	 * Reads and creates a SourceDocument object
	 */
	protected SourceDocument loadDoc(String sourceFilename) {
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");

		SourceDocument doc = new SourceDocument();

		try {
			doc.set(reader.read(Paths.getInputStream(sourceFilename)));
		} catch (DocumentException e) {
			fail(Paths.FILE_NOT_FOUND_MESSAGE);
		} catch (IOException e) {
			fail(Paths.FILE_NOT_FOUND_MESSAGE);
		}

		
		return doc;
	}
	
	/**
	 * Reads a file from the disk
	 */
	protected String readFile(String filename) {
		StringBuffer buff = new StringBuffer();
		try {
			BufferedReader eReader = new BufferedReader(new InputStreamReader(
					Paths.getInputStream(filename), "UTF-8"));

			int nextChar = eReader.read();
			while (nextChar != -1) {
				buff.append((char) nextChar);
				nextChar = eReader.read();
			}
			eReader.close();
		} catch (IOException e) {
			fail(Paths.FILE_NOT_FOUND_MESSAGE);
		}

		return buff.substring(1); // skipping the windows UTF-8 char
	}

}
