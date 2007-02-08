package lost.tok;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.CoreException;

public class DiscussionTest extends TestCase {

	/*
	 * Shay: I've made an attempt to clean all the files on exit, but it doesn't
	 * work public void tearDown() { IWorkspace ws =
	 * ResourcesPlugin.getWorkspace(); String wsRoot =
	 * ws.getRoot().getLocation().toOSString(); File f = new File(wsRoot); for
	 * (String s : f.list()) { deleteFileOrFolder(new File(s)); } }
	 * 
	 * private void deleteFileOrFolder(File file) { if (file.isDirectory()) {
	 * for (String s : file.list()) { deleteFileOrFolder(new File(s)); } }
	 * file.deleteOnExit(); }
	 */
	private ToK creation(String projectName) throws CoreException,
			IOException {
		ToK tok = new ToK(projectName, "Guy", Paths.SOURCE_EXAMPLE);
		return tok;
	}
	
	// test that the add discussion work
	public void testPre() throws CoreException, IOException {
		ToK tok = creation("test0");
		Discussion disc;
		tok.addDiscussion("test");
		disc = tok.getDiscussion("test");
		assertTrue(disc.getDiscName() == "test");
	}

	// test addOpinion
	public void testAddOpinion1() throws CoreException, IOException {
		ToK tok = creation("testAO1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper
				.createXPath("//opinion[name='one']");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 1);
	}

	// test that you cant add 2 opinions with the same name
	public void testAddOpinion2() throws CoreException, IOException {
		ToK tok = creation("testAO2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("Default Opinion");
		disc.addOpinion("one");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//opinion");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeOpinion
	public void testRemoveOpinion1() throws CoreException, IOException {
		ToK tok = creation("testRO1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.removeOpinion(2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//opinion");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// chack that you cant remove the defult opinion, and removing of non
	// existiong opinions
	public void testRemoveOpinion2() throws CoreException, IOException {
		ToK tok = creation("testRO2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.removeOpinion(1);
		disc.removeOpinion(3);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//opinion");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test relocateQuote
	public void testRelocateQuote1() throws CoreException, IOException {
		ToK tok = creation("testRQ1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.relocateQuote(1, 2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper
				.createXPath("//opinion[name='one']/quote");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//opinion[id='1']/quote");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 0);
	}

	// test relocateQuote, bad quote id or target id
	public void testRelocateQuote2() throws CoreException, IOException {
		ToK tok = creation("testRQ2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.relocateQuote(1, 7);
		disc.relocateQuote(7, 2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper
				.createXPath("//opinion[name='one']/quote");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//opinion[id='1']/quote");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 0 && result2.size() == 1);
	}

	// test removeQuote
	public void testRemoveQuote1() throws CoreException, IOException {
		ToK tok = creation("testRMQ1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(1, 1);
		disc.removeQuote(1);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//quote");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.isEmpty());
	}

	// test removeQuote, Quote doesnt exist
	public void testRemoveQuote2() throws CoreException, IOException {
		ToK tok = creation("testRMQ2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(1, 1);
		disc.removeQuote(7);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//quote");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 1);
	}

	// test createOpinionLink
	public void testCreateOpinionLink1() throws CoreException, IOException {
		ToK tok = creation("testCOL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createOpinionLink(2, 3, "link between 'one' and 'two'",
				"opposition");
		disc.createOpinionLink(2, 4, "link between 'one' and 'three'",
				"opposition");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper
				.createXPath("//opinion[id='2']/opinionRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//opinion[id='3']/opinionRel[targetId='2']");
		List result2 = xpathSelector2.selectNodes(doc);
		XPath xpathSelector3 = DocumentHelper
				.createXPath("//opinion[id='4']/opinionRel[targetId='2']");
		List result3 = xpathSelector3.selectNodes(doc);
		assertTrue(result1.size() == 2 && result2.size() == 1
				&& result3.size() == 1);
	}

	// test createOpinionLink, link already exist and link between opinion that
	// dont exist
	public void testCreateOpinionLink2() throws CoreException, IOException {
		ToK tok = creation("testCOL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.createOpinionLink(2, 3, "good link", "opposition");
		disc.createOpinionLink(2, 3, "cant link (already exist)", "opposition");
		disc.createOpinionLink(2, 7, "cant link (opinion doesnt exist)",
				"opposition");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//opinionRel");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeOpinionLink
	public void testRemoveOpinionLink1() throws CoreException, IOException {
		ToK tok = creation("testROL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createOpinionLink(2, 3, "link between 'one' and 'two'",
				"opposition");
		disc.createOpinionLink(2, 4, "link between 'one' and 'three'",
				"opposition");
		disc.removeOpinionLink(4, 2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//opinionRel");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeOpinionLink, removing a link that doesnt exist
	public void testRemoveOpinionLink2() throws CoreException, IOException {
		ToK tok = creation("testROL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createOpinionLink(2, 3, "link between 'one' and 'two'",
				"opposition");
		disc.createOpinionLink(2, 4, "link between 'one' and 'three'",
				"opposition");
		disc.removeOpinionLink(2, 7);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//opinionRel");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 4);
	}

	// test removeOpinion, opinion with quotes and relations
	public void testRemoveOpinion3() throws CoreException, IOException {
		ToK tok = creation("testRO3");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createOpinionLink(2, 3, "should be removed", "opposition");
		disc.createOpinionLink(2, 4, "should be removed", "opposition");
		disc.createOpinionLink(3, 4, "shouldn't be removed", "opposition");
		disc.addQuoteTest(1, 2);
		disc.addQuoteTest(2, 2);
		disc.removeOpinion(2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper.createXPath("//opinionRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//quote");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 2 && result2.size() == 2);
	}

	// test createQuoteLink
	public void testCreateQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testCQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.addQuoteTest(3, 1);
		disc.createQuoteLink(1, 2, "link between '1' and '2'",
				"opposition");
		disc.createQuoteLink(1, 3, "link between '1' and '3'",
				"opposition");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper
				.createXPath("//quote[id='1']/quoteRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//quote[id='2']/quoteRel[targetId='1']");
		List result2 = xpathSelector2.selectNodes(doc);
		XPath xpathSelector3 = DocumentHelper
				.createXPath("//quote[id='3']/quoteRel[targetId='1']");
		List result3 = xpathSelector3.selectNodes(doc);
		assertTrue(result1.size() == 2 && result2.size() == 1
				&& result3.size() == 1);
	}

	// test createQuoteLink, link already exist and link between quotes that
	// dont exist
	public void testCreateQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testCQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.addQuoteTest(3, 1);
		disc.createQuoteLink(1, 2, "good link", "opposition");
		disc.createQuoteLink(1, 2, "cant link (already exist)", "opposition");
		disc.createQuoteLink(1, 7, "cant link (quote doesnt exist)",
				"opposition");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//quoteRel");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeQuoteLink
	public void testRemoveQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testRQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.addQuoteTest(3, 1);
		disc.createQuoteLink(1, 2, "link between '1' and '2'",
				"opposition");
		disc.createQuoteLink(1, 3, "link between '1' and '3'",
				"opposition");
		disc.removeQuoteLink(1, 2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//quoteRel");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeQuoteLink, removing a link that doesnt exist
	public void testRemoveQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testRQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.addQuoteTest(3, 1);
		disc.createQuoteLink(1, 2, "link between '1' and '2'",
				"opposition");
		disc.removeQuoteLink(1, 3);
		disc.removeQuoteLink(2, 7);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector = DocumentHelper.createXPath("//quoteRel");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test createOpinionQuoteLink
	public void testCreateOpinionQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testCOQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.createOpinionQuoteLink(2, 1, "link between 'one' and '1'",
				"opposition");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper
				.createXPath("//opinion[id='2']/quoteRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//quote[id='1']/opinionRel");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 1);
	}

	// test createOpinionQuoteLink, link already exist and link between elementss that
	// dont exist
	public void testCreateOpinionQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testCOQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.createOpinionQuoteLink(2, 1, "good link", "opposition");
		disc.createOpinionQuoteLink(2, 1, "cant link (already exist)", "opposition");
		disc.createQuoteLink(1, 7, "cant link (quote doesnt exist)",
				"opposition");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper.createXPath("//quoteRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//opinionRel");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 1);
	}

	// test removeOpinionQuoteLink
	public void testRemoveOpinionQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testROQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.createOpinionQuoteLink(2, 1, "link between 'one' and '1'",
				"opposition");
		disc.createOpinionQuoteLink(2, 2, "link between 'one' and '2'",
				"opposition");
		disc.removeOpinionQuoteLink(2, 1);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper.createXPath("//quoteRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//opinionRel");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 1);
	}

	// test removeOpinionQuoteLink, removing a link that doesnt exist
	public void testRemoveOpinionQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testROQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.createOpinionQuoteLink(2, 1, "link between 'one' and '1'",
				"opposition");
		disc.removeOpinionQuoteLink(2, 2);
		disc.removeQuoteLink(2, 7);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper.createXPath("//quoteRel");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//opinionRel");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 1);
	}
	
	// test removeOpinion, opinion with quote relations
	public void testRemoveOpinion4() throws CoreException, IOException {
		ToK tok = creation("testRO4");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.createOpinionQuoteLink(2, 1, "should be removed", "opposition");
		disc.createOpinionQuoteLink(2, 2, "should be removed", "opposition");
		disc.createOpinionQuoteLink(1, 1, "shouldn't be removed", "opposition");
		disc.removeOpinion(2);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper.createXPath("//opinionRel");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 1);
	}
	
	// test removeQuote, quote with opinion relations
	public void testRemoveQuote3() throws CoreException, IOException {
		ToK tok = creation("testRQ3");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(1, 1);
		disc.addQuoteTest(2, 1);
		disc.createOpinionQuoteLink(2, 1, "should be removed", "opposition");
		disc.createOpinionQuoteLink(2, 2, "should be removed", "opposition");
		disc.createOpinionQuoteLink(1, 1, "shouldn't be removed", "opposition");
		disc.removeQuote(1);

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		XPath xpathSelector1 = DocumentHelper.createXPath("//quoteRel");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 1);
	}
	
}
