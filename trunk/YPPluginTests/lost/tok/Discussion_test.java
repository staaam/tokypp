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

public class Discussion_test extends TestCase {

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
	private void creation(ToK tok, String projectName) throws CoreException,
			IOException {
		tok.createToKProject(projectName, "Guy", Paths.SOURCE_EXAMPLE);
	}

	// test that the add discussion work
	public void testPre() throws CoreException, IOException {
		ToK tok = new ToK();
		Discussion disc;
		creation(tok, "test0");
		tok.addDiscussion("test");
		disc = tok.getDiscussion("test");
		assertTrue(disc.getDiscName() == "test");
	}

	// test addOpinion
	public void testAddOpinion1() throws CoreException, IOException {
		ToK tok = new ToK();
		creation(tok, "testAO1");
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
		ToK tok = new ToK();
		creation(tok, "testAO2");
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
		ToK tok = new ToK();
		creation(tok, "testRO1");
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
		ToK tok = new ToK();
		creation(tok, "testRO2");
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
		ToK tok = new ToK();
		creation(tok, "testRQ1");
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
		ToK tok = new ToK();
		creation(tok, "testRQ2");
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
		ToK tok = new ToK();
		creation(tok, "testRMQ1");
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
		ToK tok = new ToK();
		creation(tok, "testRMQ2");
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
		ToK tok = new ToK();
		creation(tok, "testCOL1");
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
		ToK tok = new ToK();
		creation(tok, "testCOL2");
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
		ToK tok = new ToK();
		creation(tok, "testROL1");
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
		ToK tok = new ToK();
		creation(tok, "testROL2");
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
		ToK tok = new ToK();
		creation(tok, "testRO3");
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

}
