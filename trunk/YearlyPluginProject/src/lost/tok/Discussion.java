/**
 * 
 */
package lost.tok;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 * @author Team LOST
 * 
 */
public class Discussion {

	public static final String DEFAULT_OPINION = "Default Opinion";

	public static int NEXT_ID = 1;

	private ToK myToK;

	private String discName;

	private String creatorName;

	private Integer opinionsId = 1;

	private Integer quotesId = 1;

	// private Quote[] quotes;

	public static final String[] relTypes = { "opposition", "interpretation" };

	public Discussion(ToK myToK, String discName, String creatorName) {
		super();
		this.myToK = myToK;
		this.discName = discName;
		this.creatorName = creatorName;

		if (new File(getFullFileName()).exists()) {
			System.out.println("discussion " + discName + " already exists");
			return;
		}

		writeToXml(discussionSkeleton(discName, creatorName));
	}

	private Document discussionSkeleton(String discName, String creatorName) {
		Document doc = DocumentHelper.createDocument();
		Element disc = doc.addElement("discussion");
		disc.addElement("name").addText(discName);
		disc.addElement("user").addText(creatorName);
		Element defOpin = disc.addElement("opinion");
		defOpin.addElement("id").addText("1");
		defOpin.addElement("name").addText(DEFAULT_OPINION);
		return doc;
	}

	public Discussion(ToK myToK, String filename) {
		super();
		this.myToK = myToK;

		Document d = GeneralFunctions.readFromXML(filename);

		setDiscName(DocumentHelper.createXPath("/discussion/name")
				.selectSingleNode(d).getText());
		setCreatorName(DocumentHelper.createXPath("/discussion/user")
				.selectSingleNode(d).getText());
	}

	// write the document to the XML file
	private void writeToXml(Document doc) {
		GeneralFunctions.writeToXml(getFullFileName(), doc);
	}

	private String getFullFileName() {
		return myToK.getDiscussionFolder().getFile(discName + ".dis")
				.getLocation().toOSString();
	}

	private Document readFromXML() {
		return GeneralFunctions.readFromXML(getFullFileName());
	}

	public void addOpinion(String opinionName) {

		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//opinion[name='"
				+ opinionName + "']");
		List result = xpathSelector.selectNodes(doc);
		if (result.isEmpty() == false) {
			// does nothing if opinion already exist
			return;
		}

		// add the opinion
		opinionsId = opinionsId + 1;
		Element opinion = doc.getRootElement().addElement("opinion");
		opinion.addElement("id")
				.addText(java.lang.Integer.toString(opinionsId));
		opinion.addElement("name").addText(opinionName);

		writeToXml(doc);
	}

	public IFile getFile() {
		return myToK.getDiscussionFolder().getFile(discName + ".dis");
	}

	public void removeOpinion(Integer opinionId) {

		Document doc = readFromXML();

		if (opinionId == 1) {
			// Will not remove the defult opinion
			return;
		}

		// Move all the quotes from the given opinion to the defult opinion
		XPath xpathSelector3 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinionId) + "']/quote");
		List result3 = xpathSelector3.selectNodes(doc);
		XPath xpathSelector4 = DocumentHelper.createXPath("//opinion[id='1']");
		List result4 = xpathSelector4.selectNodes(doc);
		if (result4.size() != 1) {
			// cant found the defult opinion
			return;
		}
		Element defultOpinion = (Element) result4.get(0);
		for (Iterator i = result3.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
			defultOpinion.add(element);
		}

		// Remove all the opinions (should be only one) with the given id
		// if there are several opinions with the given id, fix it by removing
		// all of them
		// if there are no opinions with the given id, do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinionId) + "']");
		List result1 = xpathSelector1.selectNodes(doc);
		for (Iterator i = result1.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		// Remove all the opinion's relations
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//opinionRel[targetId='"
						+ java.lang.Integer.toString(opinionId) + "']");
		List result2 = xpathSelector2.selectNodes(doc);
		for (Iterator i = result2.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	// demo addQuote for testing
	public void addQuoteTest(Integer id, Integer opinionId) {

		Document doc = readFromXML();

		XPath xpathSelector0 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinionId) + "']");
		List result0 = xpathSelector0.selectNodes(doc);
		Element defOp = (Element) result0.get(0);
		defOp.addElement("quote").addElement("id").addText(
				java.lang.Integer.toString(id++));

		writeToXml(doc);
	}

	public void relocateQuote(Integer quoteId, Integer targetId) {

		Document doc = readFromXML();

		// If the quote and the target opinion can be found,
		// the quote will move to the target opinion
		XPath xpathSelector1 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quoteId) + "']");
		List result = xpathSelector1.selectNodes(doc);
		if (result.size() == 1) {
			Element element = (Element) result.get(0);
			XPath xpathSelector2 = DocumentHelper.createXPath("//opinion[id='"
					+ java.lang.Integer.toString(targetId) + "']");
			List targets = xpathSelector2.selectNodes(doc);
			if (targets.size() == 1) {
				Element target = (Element) targets.get(0);
				element.detach();
				target.add(element);
			}
		}

		writeToXml(doc);
	}

	public void removeQuote(Integer quoteId) {

		Document doc = readFromXML();

		// Remove all the quotes (should be only one) with the given id
		// if there are several quotes with the given id, fix it by removing all
		// of them
		// if there are no quotes with the given id, do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quoteId) + "']");
		List result = xpathSelector1.selectNodes(doc);
		for (Iterator i = result.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}
		
		// Remove all the quote's relations
		XPath xpathSelector2 = DocumentHelper
				.createXPath("//quoteRel[targetId='"
						+ java.lang.Integer.toString(quoteId) + "']");
		List result2 = xpathSelector2.selectNodes(doc);
		for (Iterator i = result2.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	
	public void createLink(Integer opinion1, Integer quote2,
			String comment, String type) {
		
	}
	
	
	public void createOpinionLink(Integer opinion1, Integer opinion2,
			String comment, String type) {

		Document doc = readFromXML();

		// Make sure that the 2 opinions exist
		XPath xpathSelector1 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion1) + "']");
		List op1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion2) + "']");
		List op2 = xpathSelector2.selectNodes(doc);
		if (op1.size() != 1 || op2.size() != 1) {
			return;
		}

		// chack that the relation does not exist
		XPath xpathSelector3 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion1)
				+ "']/opinionRel[targetId='"
				+ java.lang.Integer.toString(opinion2) + "']");
		List list = xpathSelector3.selectNodes(doc);
		if (list.isEmpty() == false) {
			return;
		}

		// add the relation to opinion 1
		Element o1 = (Element) op1.get(0);
		Element link1 = o1.addElement("opinionRel");
		link1.addElement("targetId").addText(
				java.lang.Integer.toString(opinion2));
		link1.addElement("comment").addText(comment);
		link1.addElement("type").addText(type);

		// add the relation to opinion 2
		Element o2 = (Element) op2.get(0);
		Element link2 = o2.addElement("opinionRel");
		link2.addElement("targetId").addText(
				java.lang.Integer.toString(opinion1));
		link2.addElement("comment").addText(comment);
		link2.addElement("type").addText(type);

		writeToXml(doc);
	}

	public void removeOpinionLink(Integer opinion1, Integer opinion2) {

		// create a Document containing the discussion
		Document doc = readFromXML();

		// Remove all the relations (should be only one) from opinion 1 to
		// opinion 2
		// if there are several relations fix it by removing all of them
		// if there are no relations do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion1)
				+ "']/opinionRel[targetId='"
				+ java.lang.Integer.toString(opinion2) + "']");
		List opl1 = xpathSelector1.selectNodes(doc);
		for (Iterator i = opl1.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		// Remove all the relations (should be only one) from opinion 2 to
		// opinion 1
		// if there are several relations fix it by removing all of them
		// if there are no relations do nothing
		XPath xpathSelector2 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion2)
				+ "']/opinionRel[targetId='"
				+ java.lang.Integer.toString(opinion1) + "']");
		List opl2 = xpathSelector2.selectNodes(doc);
		for (Iterator i = opl2.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	public void createQuoteLink(Integer quote1, Integer quote2,
			String comment, String type) {

		Document doc = readFromXML();

		// Make sure that the 2 quotes exist
		XPath xpathSelector1 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote1) + "']");
		List q1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote2) + "']");
		List q2 = xpathSelector2.selectNodes(doc);
		if (q1.size() != 1 || q2.size() != 1) {
			return;
		}

		// chack that the relation does not exist
		XPath xpathSelector3 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote1)
				+ "']/quoteRel[targetId='"
				+ java.lang.Integer.toString(quote2) + "']");
		List list = xpathSelector3.selectNodes(doc);
		if (list.isEmpty() == false) {
			return;
		}

		// add the relation to quote 1
		Element o1 = (Element) q1.get(0);
		Element link1 = o1.addElement("quoteRel");
		link1.addElement("targetId").addText(
				java.lang.Integer.toString(quote2));
		link1.addElement("comment").addText(comment);
		link1.addElement("type").addText(type);

		// add the relation to quote 2
		Element o2 = (Element) q2.get(0);
		Element link2 = o2.addElement("quoteRel");
		link2.addElement("targetId").addText(
				java.lang.Integer.toString(quote1));
		link2.addElement("comment").addText(comment);
		link2.addElement("type").addText(type);

		writeToXml(doc);
	}

	public void removeQuoteLink(Integer quote1, Integer quote2) {

		// create a Document containing the discussion
		Document doc = readFromXML();

		// Remove all the relations (should be only one) from quote 1 to
		// quote 2
		// if there are several relations fix it by removing all of them
		// if there are no relations does nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote1)
				+ "']/quoteRel[targetId='"
				+ java.lang.Integer.toString(quote2) + "']");
		List ql1 = xpathSelector1.selectNodes(doc);
		for (Iterator i = ql1.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		// Remove all the relations (should be only one) from quote 2 to
		// quote 1
		// if there are several relations fix it by removing all of them
		// if there are no relations does nothing
		XPath xpathSelector2 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote2)
				+ "']/quoteRel[targetId='"
				+ java.lang.Integer.toString(quote1) + "']");
		List ql2 = xpathSelector2.selectNodes(doc);
		for (Iterator i = ql2.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	public void createOpinionQuoteLink(Integer opinion1, Integer quote2,
			String comment, String type) {

		Document doc = readFromXML();

		// Make sure that the opinion and the quote exist
		XPath xpathSelector1 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion1) + "']");
		List op1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote2) + "']");
		List op2 = xpathSelector2.selectNodes(doc);
		if (op1.size() != 1 || op2.size() != 1) {
			return;
		}

		// chack that the relation does not exist
		XPath xpathSelector3 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion1)
				+ "']/quoteRel[targetId='"
				+ java.lang.Integer.toString(quote2) + "']");
		List list = xpathSelector3.selectNodes(doc);
		if (list.isEmpty() == false) {
			return;
		}

		// add the relation to opinion 1
		Element o1 = (Element) op1.get(0);
		Element link1 = o1.addElement("quoteRel");
		link1.addElement("targetId").addText(
				java.lang.Integer.toString(quote2));
		link1.addElement("comment").addText(comment);
		link1.addElement("type").addText(type);

		// add the relation to quote 2
		Element o2 = (Element) op2.get(0);
		Element link2 = o2.addElement("opinionRel");
		link2.addElement("targetId").addText(
				java.lang.Integer.toString(opinion1));
		link2.addElement("comment").addText(comment);
		link2.addElement("type").addText(type);

		writeToXml(doc);
	}

	public void removeOpinionQuoteLink(Integer opinion1, Integer quote2) {

		// create a Document containing the discussion
		Document doc = readFromXML();

		// Remove all the relations (should be only one) from opinion 1 to
		// quote 2
		// if there are several relations fix it by removing all of them
		// if there are no relations do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//opinion[id='"
				+ java.lang.Integer.toString(opinion1)
				+ "']/quoteRel[targetId='"
				+ java.lang.Integer.toString(quote2) + "']");
		List opl1 = xpathSelector1.selectNodes(doc);
		for (Iterator i = opl1.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		// Remove all the relations (should be only one) from quote 2 to
		// opinion 1
		// if there are several relations fix it by removing all of them
		// if there are no relations do nothing
		XPath xpathSelector2 = DocumentHelper.createXPath("//quote[id='"
				+ java.lang.Integer.toString(quote2)
				+ "']/opinionRel[targetId='"
				+ java.lang.Integer.toString(opinion1) + "']");
		List opl2 = xpathSelector2.selectNodes(doc);
		for (Iterator i = opl2.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}
	
	public void addQuote(Quote quote, String opinion) throws CoreException {

		if (!myToK.getProject().exists() || quote == null) {
			throwCoreException("problem with atributes to addQuote");
			return;
		}

		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//opinion[name='"
				+ opinion + "']");
		List result = xpathSelector.selectNodes(doc);
		if (result.size() != 1) {
			// cant found the defult opinion
			return;
		}
		Element defultOpinion = (Element) result.get(0);

		quote.setID(quotesId++);
		defultOpinion.add(quote.toXML());

		writeToXml(doc);

	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "Yearly_Plugin_Project",
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getDiscName() {
		return discName;
	}

	public void setDiscName(String discName) {
		this.discName = discName;
	}

	public ToK getMyToK() {
		return myToK;
	}

	public static int getNextId() {
		return NEXT_ID++;
	}

	public String[] getOpinionNames() {
		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//opinion/name");
		List result = xpathSelector.selectNodes(doc);

		String[] ss = new String[result.size()];
		int i = 0;
		for (Object object : result) {
			Element e = (Element) object;
			ss[i++] = e.getText();
		}

		return ss;
	}
	
	public Integer[] getOpinionIDs() {
		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//opinion/id");
		List result = xpathSelector.selectNodes(doc);

		Integer[] ss = new Integer[result.size()];
		int i = 0;
		for (Object object : result) {
			Element e = (Element) object;
			ss[i++] = Integer.valueOf(e.getText());
		}

		return ss;
	}

	
	public Quote[] getQuotes(String opinion) throws CoreException {

		int j = 0;
		Document doc = readFromXML();

		List result = doc.selectNodes("//opinion[name='" + opinion
				+ "']/quote");
		
		Quote[] quotes = new Quote[result.size()];
		for (Object object : result) {
			Element elem = (Element) object;
			quotes[j++] = new Quote(elem);
		}
		return quotes;
	}
}
