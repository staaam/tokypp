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

	/**
	 * Creates a new Tree Of Knowledge
	 * @param projectName The name of the new project
	 * @return the new ToK which was created
	 */
	static public ToK creation(String projectName) throws CoreException,
			IOException {
		ToK tok = new ToK(projectName, "Guy", "");
		return tok;
	}
	
	// test that the add discussion work
	public void testPre() throws CoreException, IOException {
		ToK tok = creation("test0");
		Discussion disc;
		tok.addDiscussion("test");
		disc = tok.getDiscussion("test");
		assertTrue(disc.getDiscName().equals("test"));
		assertTrue(disc.getDescription().equals(""));
	}

	// test that the add discussion work
	public void testDescription() throws CoreException, IOException {
		ToK tok = creation("testDescription");
		Discussion disc;
		tok.addDiscussion("test", "description testDescription");
		disc = tok.getDiscussion("test");
		assertTrue(disc.getDescription().equals("description testDescription"));
	}
	
	// test that the add discussion work
	public void testNoDescription() throws CoreException, IOException {
		ToK tok = creation("testNoDescription");
		Discussion disc;
		tok.addDiscussion("test", "");
		disc = tok.getDiscussion("test");
		assertTrue(disc.getDescription() == "");
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
		disc.addOpinion(Discussion.DEFAULT_OPINION_DISPLAY);
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
		disc.addQuoteTest(11, 1);
		disc.relocateQuote(11, 2);

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
		disc.addQuoteTest(11, 1);
		disc.relocateQuote(11, 7);
		disc.relocateQuote(17, 2);

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
		disc.addQuoteTest(11, 1);
		disc.removeQuote(11);

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
		disc.addQuoteTest(11, 1);
		disc.removeQuote(17);

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
		disc.createRelation(2, 3, "link between 'one' and 'two'",
				Relation.getRelTypes()[0]);
		disc.createRelation(2, 4, "link between 'one' and 'three'",
				Relation.getRelTypes()[0]);

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
				.createXPath("//relation[id1='2']|//relation[id2='2']");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//relation[id1='2'][id2='3']|//relation[id1='3'][id2='2']");
		List result2 = xpathSelector2.selectNodes(doc);
		XPath xpathSelector3 = DocumentHelper
				.createXPath("//relation[id1='4'][id2='2']|//relation[id1='2'][id2='4']");
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
		disc.createRelation(2, 3, "good link", Relation.getRelTypes()[0]);
		disc.createRelation(2, 3, "cant link (already exist)", Relation.getRelTypes()[0]);
		disc.createRelation(2, 7, "cant link (opinion doesnt exist)",
				Relation.getRelTypes()[0]);

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

		XPath xpathSelector = DocumentHelper.createXPath("//relation");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 1);
	}

	// test removeOpinionLink
	public void testRemoveOpinionLink1() throws CoreException, IOException {
		ToK tok = creation("testROL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createRelation(2, 3, "link between 'one' and 'two'",
				Relation.getRelTypes()[0]);
		disc.createRelation(2, 4, "link between 'one' and 'three'",
				Relation.getRelTypes()[0]);
		disc.removeRelation(4, 2);

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

		XPath xpathSelector = DocumentHelper.createXPath("//relation");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 1);
	}

	// test removeOpinionLink, removing a link that doesnt exist
	public void testRemoveOpinionLink2() throws CoreException, IOException {
		ToK tok = creation("testROL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createRelation(2, 3, "link between 'one' and 'two'",
				Relation.getRelTypes()[0]);
		disc.createRelation(2, 4, "link between 'one' and 'three'",
				Relation.getRelTypes()[0]);
		disc.removeRelation(2, 7);

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

		XPath xpathSelector = DocumentHelper.createXPath("//relation");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeOpinion, opinion with quotes and relations
	public void testRemoveOpinion3() throws CoreException, IOException {
		ToK tok = creation("testRO3");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addOpinion("two");
		disc.addOpinion("three");
		disc.createRelation(2, 3, "should be removed", Relation.getRelTypes()[0]);
		disc.createRelation(2, 4, "should be removed", Relation.getRelTypes()[0]);
		disc.createRelation(3, 4, "shouldn't be removed", Relation.getRelTypes()[0]);
		disc.addQuoteTest(11, 2);
		disc.addQuoteTest(12, 2);
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

		XPath xpathSelector1 = DocumentHelper.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//quote");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 2);
	}

	// test createQuoteLink
	public void testCreateQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testCQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.addQuoteTest(13, 1);
		disc.createRelation(11, 12, "link between '1' and '2'",
				Relation.getRelTypes()[0]);
		disc.createRelation(11, 13, "link between '1' and '3'",
				Relation.getRelTypes()[0]);

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
				.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 2);
	}

	// test createQuoteLink, link already exist and link between quotes that
	// dont exist
	public void testCreateQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testCQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.addQuoteTest(13, 1);
		disc.createRelation(11, 12, "good link", Relation.getRelTypes()[0]);
		disc.createRelation(11, 12, "cant link (already exist)", Relation.getRelTypes()[0]);
		disc.createRelation(11, 17, "cant link (quote doesnt exist)",
				Relation.getRelTypes()[0]);

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

		XPath xpathSelector = DocumentHelper.createXPath("//relation");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeQuoteLink
	public void testRemoveQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testRQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.addQuoteTest(13, 1);
		disc.createRelation(11, 12, "link between '1' and '2'",
				Relation.getRelTypes()[0]);
		disc.createRelation(11, 13, "link between '1' and '3'",
				Relation.getRelTypes()[0]);
		disc.removeRelation(11, 12);

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

		XPath xpathSelector = DocumentHelper.createXPath("//relation");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 2);
	}

	// test removeQuoteLink, removing a link that doesnt exist
	public void testRemoveQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testRQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.addQuoteTest(13, 1);
		disc.createRelation(11, 12, "link between '1' and '2'",
				Relation.getRelTypes()[0]);
		disc.removeRelation(11, 13);
		disc.removeRelation(12, 17);

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

		XPath xpathSelector = DocumentHelper.createXPath("//relation");
		List result = xpathSelector.selectNodes(doc);
		assertTrue(result.size() == 1);
	}

	// test createOpinionQuoteLink
	public void testCreateOpinionQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testCOQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(11, 1);
		disc.createRelation(2, 11, "link between 'one' and '1'",
				Relation.getRelTypes()[0]);

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
				.createXPath("//relation[id1='2']|//relation[id2='2']");
		List result1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//relation[id1='11']|//relation[id2='11']");
		List result2 = xpathSelector2.selectNodes(doc);
		assertTrue(result1.size() == 1 && result2.size() == 0);
	}

	// test createOpinionQuoteLink, link already exist and link between elementss that
	// dont exist
	public void testCreateOpinionQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testCOQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.createRelation(2, 11, "good link", Relation.getRelTypes()[0]);
		disc.createRelation(2, 11, "cant link (already exist)", Relation.getRelTypes()[0]);
		disc.createRelation(1, 17, "cant link (quote doesnt exist)",
				Relation.getRelTypes()[0]);

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

		XPath xpathSelector1 = DocumentHelper.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 2);
	}

	// test removeOpinionQuoteLink
	public void testRemoveOpinionQuoteLink1() throws CoreException, IOException {
		ToK tok = creation("testROQL1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.createRelation(2, 11, "link between 'one' and '1'",
				Relation.getRelTypes()[0]);
		disc.createRelation(2, 12, "link between 'one' and '2'",
				Relation.getRelTypes()[0]);
		disc.removeRelation(2, 11);

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

		XPath xpathSelector1 = DocumentHelper.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 2);
	}

	// test removeOpinionQuoteLink, removing a link that doesnt exist
	public void testRemoveOpinionQuoteLink2() throws CoreException, IOException {
		ToK tok = creation("testROQL2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.createRelation(2, 11, "link between 'one' and '1'",
				Relation.getRelTypes()[0]);
		disc.removeRelation(2, 12);
		disc.removeRelation(2, 17);

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

		XPath xpathSelector1 = DocumentHelper.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 1);
	}
	
	// test removeOpinion, opinion with quote relations
	public void testRemoveOpinion4() throws CoreException, IOException {
		ToK tok = creation("testRO4");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.createRelation(2, 11, "should be removed", Relation.getRelTypes()[0]);
		disc.createRelation(2, 12, "should be removed", Relation.getRelTypes()[0]);
		disc.createRelation(1, 11, "shouldn't be removed", Relation.getRelTypes()[0]);
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

		XPath xpathSelector1 = DocumentHelper.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 1);
	}
	
	// test removeQuote, quote with opinion relations
	public void testRemoveQuote3() throws CoreException, IOException {
		ToK tok = creation("testRQ3");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		disc.addOpinion("one");
		disc.addQuoteTest(11, 1);
		disc.addQuoteTest(12, 1);
		disc.createRelation(2, 11, "should be removed", Relation.getRelTypes()[0]);
		disc.createRelation(2, 12, "shouldn't be removed", Relation.getRelTypes()[0]);
		disc.createRelation(1, 11, "should be removed", Relation.getRelTypes()[0]);
		disc.removeQuote(11);

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

		XPath xpathSelector1 = DocumentHelper.createXPath("//relation");
		List result1 = xpathSelector1.selectNodes(doc);
		assertTrue(result1.size() == 3);
	}
	
	/**
	 * Adding opinion after a relation is a bit more complex, so we test it
	 * Creates a document, adds a few relations and opinions,
	 * and verify that there is a correct number of both
	 */
	public void testAddOpAfterRel1() throws CoreException, IOException, DocumentException {
		ToK tok = creation("testOpAfterRel1");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		
		disc.addOpinion("hello1");
		disc.addOpinion("hello2");
		disc.createRelation(1, 2, "no comment", Relation.getRelTypes()[1]);
		disc.addOpinion("hello3"); // hopefully, this won't destroy the order

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		doc = reader.read(file);
		
		int numRel = DocumentHelper.createXPath("//relation").selectNodes(doc).size();
		int numOps = DocumentHelper.createXPath("//opinion").selectNodes(doc).size();
		assertEquals(3+1, numOps); // +1 for the def opinion
		assertEquals(1, numRel);
		
		// Note(Shay): I wanted to verify according to the xsd, 
		// but couldn't figure out how to use Xerces and the schema validation
	}
	
	/**
	 * Adding opinion after a relation is a bit more complex, so we test it
	 * Creates a document, adds a few relations and opinions,
	 * and verify that there is a correct number of both
	 */
	public void testAddOpAfterRel2() throws CoreException, IOException, DocumentException {
		ToK tok = creation("testOpAfterRel2");
		tok.addDiscussion("test");
		Discussion disc = tok.getDiscussion("test");
		
		disc.addOpinion("hello1");
		disc.addOpinion("hello2");
		disc.createRelation(1, 2, "no comment", Relation.getRelTypes()[1]);
		disc.addOpinion("hello3");
		disc.createRelation(3, 2, "no comment", Relation.getRelTypes()[0]);
		disc.addOpinion("hello4");
		disc.addOpinion("hello5");
		disc.createRelation(4, 5, "no comment", Relation.getRelTypes()[1]);
		disc.createRelation(5, 1, "no comment", Relation.getRelTypes()[0]);
		disc.addOpinion("hello6");

		Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		String path = disc.getMyToK().getDiscussionFolder().getFile(
				disc.getDiscName() + ".dis").getLocation().toOSString();
		File file = new File(path);
		doc = reader.read(file);
		
		int numRel = DocumentHelper.createXPath("//relation").selectNodes(doc).size();
		int numOps = DocumentHelper.createXPath("//opinion").selectNodes(doc).size();
		assertEquals(6+1, numOps); // +1 for the def opinion
		assertEquals(4, numRel);
	}
	
}
