package lost.tok.opTable.sourceDocument;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.ResourcesPlugin;

import junit.framework.TestCase;
import lost.tok.Paths;

public class SourceDocument_test extends TestCase {

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

	// FIXME(Shay): Since we cannot run eclipse in both Hebrew and English
	// Modes at the same time, one of these tests will always fail
	/*
	 * public void testSetDocumentHeb() { final String sourceFilename =
	 * Paths.OR_AHAIM_HE; final String expectedResFileName =
	 * Paths.OR_AHAIM_HE_RES;
	 * 
	 * SourceDocument doc = loadDoc(sourceFilename); String actual = doc.get();
	 * String expected = readFile(expectedResFileName);
	 * 
	 * assertEquals("fails in English mode",expected, actual); }
	 */
	
	public void testCreateDocToParse()
	{
		Chapter c = null;
		String text = "This is the story of an unparsed document"; 
		
		SourceDocument doc = new SourceDocument();
		doc.setUnparsed(text,"Source Parser","Shay Nahum");
		assertEquals("TITLE:\tSource Parser\nBY   :	Shay Nahum\n\n" +
				"Chapter 1:\t(Unparsed Text)\n" + text,
				doc.getChapterFromOffset(0).toString());
		c = doc.getChapterFromOffset(50);
		assertEquals("Chapter 1:\t(Unparsed Text)\n" + text, c.toString());
	}
	
	public void testCreateNewChapter()
	{
		String text = "This is the story of an unparsed document"; 
		
		SourceDocument doc = new SourceDocument();
		doc.setUnparsed(text,"Source Parser","Shay Nahum");
		
		doc.createNewChapter(30, "Document?");
	}

	public void testGetChapterFromOffset() {
		SourceDocument doc = loadDoc(Paths.OR_AHAIM_EN);
		Chapter c = null;

		c = doc.getChapterFromOffset(119);
		assertEquals("fails in Hebrew mode", 99, c.getOffset().intValue());
		assertEquals(23, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getName());

		c = doc.getChapterFromOffset(122);
		assertEquals(122, c.getOffset().intValue());
		assertEquals(64 + 401, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getParent().getName());
		assertTrue(c instanceof ChapterText);
		assertEquals("Genesis/chapter 11/Verse 1", ((ChapterText) c)
				.getExcerptionPath());

		c = doc.getChapterFromOffset(150);
		assertEquals(122, c.getOffset().intValue());
		assertEquals(64 + 401, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getParent().getName());

		c = doc.getChapterFromOffset(586);
		assertEquals(122, c.getOffset().intValue());
		assertEquals(64 + 401, c.getInnerLength().intValue());
		assertEquals("Verse 1", c.getParent().getName());

		c = doc.getChapterFromOffset(2664);
		assertEquals(2651, c.getOffset().intValue());
		assertEquals(520, c.getInnerLength().intValue());
		assertEquals("Verse 7", c.getParent().getName());
		assertTrue(c instanceof ChapterText);
		assertEquals("Genesis/chapter 11/Verse 7", ((ChapterText) c)
				.getExcerptionPath());

		c = doc.getChapterFromOffset(4200);
		assertEquals(3461, c.getOffset().intValue());
		assertEquals(740, c.getInnerLength().intValue());
		assertEquals("Verse 9", c.getParent().getName());
		assertTrue(c instanceof ChapterText);
		assertEquals("Genesis/chapter 11/Verse 9", ((ChapterText) c)
				.getExcerptionPath());
	}

	protected SourceDocument loadDoc(String sourceFilename) {
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");

		SourceDocument doc = new SourceDocument();

		try {
			doc.set(reader.read(sourceFilename));
		} catch (DocumentException e) {
			fail(Paths.FILE_NOT_FOUND_MESSAGE);
		}
		return doc;
	}

	protected String readFile(String filename) {
		StringBuffer buff = new StringBuffer();
		try {
			BufferedReader eReader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "UTF-8"));

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
