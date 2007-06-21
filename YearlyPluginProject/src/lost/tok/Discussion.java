package lost.tok;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class Discussion implements Comparable<Discussion> {

	private static final String DISCUSSION_EXTENSION = "dis"; //$NON-NLS-1$

	/** The Default opinion's display name (as displayed to the user) */
	public static final String DEFAULT_OPINION_DISPLAY = Messages
			.getString("Discussion.DefOpinion"); //$NON-NLS-1$
	
	/** The Default opinion's name, as appearing in the xml file */
	public static final String DEFAULT_OPINION_XML = "General"; //$NON-NLS-1$

	/**
	 * The types of the relations, as strings displayable to the user The order
	 * of the strings should be the same as in the relXMLTypes array
	 */
	public static final String[] relDisplayNames = {
			Messages.getString("Discussion.1"), Messages.getString("Discussion.2") }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The types of the links, as the xml scheme defines The order of the
	 * strings should be the same as in the relDisplayNames array
	 */
	public static final String[] relXMLTypes = { "disagree", "explain" }; //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * Qualified Name of ToK's project that specifies latest discussion used
	 */
	public static final QualifiedName LATEST_QNAME = 
		new QualifiedName("lost.tok", "latest_discussion");  //$NON-NLS-1$ //$NON-NLS-2$
	
	private long localModificationStamp = 0;

	public static String getNameFromFile(String discussionFile) {
		int begin = discussionFile.lastIndexOf('/'); //$NON-NLS-1$
		if (begin == -1) {
			begin = discussionFile.lastIndexOf('\\'); //$NON-NLS-1$
		}
		begin++;
		int end = discussionFile.lastIndexOf("." + DISCUSSION_EXTENSION); //$NON-NLS-1$

		if (end == -1)
			return null;

		return discussionFile.substring(begin, end);
	}

	public static String getNameFromResource(IResource resource) {
		return getNameFromFile(resource.getFullPath().toPortableString());
	}

	private ToK myToK;

	private String discName;

	private String creatorName;

	/** The highest id in the discussion */
	private Integer id = 1;
	
	private Link link = null;

	private String linkType=null;

	private Source linkedSource=null;
	
	/** The id of the default opinion (might not be 1) */
	private int defaultOpinionID;
	
	/** The name of the discussion file */
	private IFile discussionFile = null;

	private String actualFile; 

	/**
	 * constructor for discussion from an XML file
	 */
	public Discussion(ToK myToK, String filename) {
		loadDiscussionFromFile(myToK, filename);
	}

	/**
	 * constructor for discussion from an XML file
	 */
	public Discussion(IFile file) {
		loadDiscussionFromFile(file);
	}

	/**
	 * Updates the Discussion with information from the disk
	 */
	private void loadDiscussionFromFile(ToK myToK, String fileName) {
		this.myToK = myToK;
		
		this.discussionFile = myToK.getProject().getFile(fileName);
		this.actualFile = fileName;

		loadDiscussion(GeneralFunctions.readFromXML(fileName));
	}

	/**
	 * Updates the Discussion with information from the disk
	 */
	private void loadDiscussionFromFile(IFile file) {
		myToK = ToK.getProjectToK(file.getProject());
		
		discussionFile = file;

		loadDiscussion(GeneralFunctions.readFromXML(file));
	}
	
	private void loadDiscussion(Document d) {
		setDiscName(DocumentHelper.createXPath("/discussion/name") //$NON-NLS-1$
				.selectSingleNode(d).getText());
		setCreatorName(DocumentHelper.createXPath("/discussion/user") //$NON-NLS-1$
				.selectSingleNode(d).getText());
		
		XPath defOpSelector = DocumentHelper.createXPath("/discussion/opinion[name='" + DEFAULT_OPINION_XML + "']/id");  //$NON-NLS-1$ //$NON-NLS-2$
		Node defOpIdNode = defOpSelector.selectSingleNode(d); 
		defaultOpinionID = Integer.valueOf( defOpIdNode.getText() );

		List result = DocumentHelper.createXPath("//id").selectNodes(d); //$NON-NLS-1$
		id = 0;
		for (Iterator i = result.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			if (Integer.valueOf(element.getText()) > id) {
				id = Integer.valueOf(element.getText());
			}
		}
		
		loadLinkFromFile();
	}
	
	/** Loads the discussion's link (if exists) from the links.xml file */
	private void loadLinkFromFile()
	{
		// Load the link from XML
		Document d = GeneralFunctions.readFromXML(myToK.getLinkFile());

		// Check if the link exist in the xml
		String filename = discussionFile.getFullPath().lastSegment();
		Node node = DocumentHelper.createXPath("/links/link[discussionFile=\'" + filename + "']").selectSingleNode(d);  //$NON-NLS-1$  //$NON-NLS-2$
		
		if (node == null)
		{
			// if it doesn't, do nothing
			return;
		}
		
		// else, create the link from the Element's information
		this.link = new Link(this, (Element)node);
	}

	/**
	 * first constructor for discussion
	 * 
	 * @param myToK
	 * @param discName
	 * @param creatorName
	 * @throws FileNotFoundException 
	 */
	public Discussion(ToK myToK, String discName, String creatorName) throws FileNotFoundException {
		this.myToK = myToK;
		this.discName = discName;
		this.creatorName = creatorName;
		this.defaultOpinionID = 1;
		this.id = 1;

		if (new File(getFullFileName()).exists()) {
			throw new FileNotFoundException();
		}

		writeToXml(discussionSkeleton(discName, creatorName));
	}

	/**
	 * add an opinion to the discussion
	 * 
	 * @param opinionName
	 */
	public void addOpinion(String opinionName) {

		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//opinion[name='" //$NON-NLS-1$
				+ opinionName + "']"); //$NON-NLS-1$
		List result = xpathSelector.selectNodes(doc);
		if (result.isEmpty() == false) {
			// does nothing if opinion already exist
			return;
		}
		
		// Can't add an opinion with the same name as the default opinion
		if (opinionName.equals(DEFAULT_OPINION_DISPLAY))
			return;

		// detach all the relations
		// Note(Shay): We do this in order to maintein the order enforced in the
		// xsd
		// First there must be all the opinions, and then the relations
		XPath relationsXPath = DocumentHelper.createXPath("//relation"); //$NON-NLS-1$
		List relationNodes = relationsXPath.selectNodes(doc);
		for (Iterator i = relationNodes.iterator(); i.hasNext();) {
			Element attached = (Element) i.next();
			attached.detach();
		}

		// add the opinion
		Element opinion = doc.getRootElement().addElement("opinion"); //$NON-NLS-1$
		opinion.addElement("id").addText(java.lang.Integer.toString(++id)); //$NON-NLS-1$
		opinion.addElement("name").addText(opinionName); //$NON-NLS-1$

		// reattach all the relations
		for (Iterator i = relationNodes.iterator(); i.hasNext();) {
			Element relationElement = (Element) i.next();
			doc.getRootElement().add(relationElement);
		}

		writeToXml(doc);
		
		myToK.setLatestDiscussionOpinion(getDiscName(), opinionName);
	}

	/**
	 * Adding a quote to the xml file
	 * 
	 * @param quote -
	 *            the quote to add
	 * @param opinionDisp The display name of the opinion to which the quote should be added
	 * @throws CoreException
	 */
	public void addQuote(Quote quote, String opinionDisp) throws CoreException {

		if (!myToK.getProject().exists() || quote == null) {
			GeneralFunctions.throwCoreException("problem with atributes to addQuote"); //$NON-NLS-1$
			return;
		}
		
		String opinionXML = opinionDisp;
		if (opinionXML.equals(DEFAULT_OPINION_DISPLAY))
			opinionXML = DEFAULT_OPINION_XML;
			
		Document doc = readFromXML();

		// creating the xPath
		XPath xpathSelector = DocumentHelper.createXPath("//opinion[name='" //$NON-NLS-1$
				+ opinionXML + "']"); //$NON-NLS-1$
		List result = xpathSelector.selectNodes(doc);
		if (result.size() != 1) {
			// cant found the defult opinion
			return;
		}
		Element defultOpinion = (Element) result.get(0);

		quote.setID(++id);
		defultOpinion.add(quote.toXML());

		writeToXml(doc);

		myToK.setLatestDiscussionOpinion(getDiscName(),	opinionDisp);
	}

	// demo addQuote for testing
	public void addQuoteTest(Integer id, Integer opinionId) {

		Document doc = readFromXML();

		XPath xpathSelector0 = DocumentHelper.createXPath("//opinion[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(opinionId) + "']"); //$NON-NLS-1$
		List result0 = xpathSelector0.selectNodes(doc);
		Element defOp = (Element) result0.get(0);
		defOp.addElement("quote").addElement("id").addText( //$NON-NLS-1$ //$NON-NLS-2$
				java.lang.Integer.toString(id++));

		writeToXml(doc);
	}

	/**
	 * create a relation between the 2 elements
	 * 
	 * @param element1
	 * @param element2
	 * @param comment
	 * @param type
	 */
	public void createRelation(Integer element1, Integer element2, String comment,
			String type) {

		Document doc = readFromXML();

		// Make sure that the 2 elements exist
		XPath xpathSelector1 = DocumentHelper.createXPath("//*[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element1) + "']"); //$NON-NLS-1$
		List op1 = xpathSelector1.selectNodes(doc);
		XPath xpathSelector2 = DocumentHelper.createXPath("//*[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element2) + "']"); //$NON-NLS-1$
		List op2 = xpathSelector2.selectNodes(doc);
		if (op1.size() != 1 || op2.size() != 1) {
			return;
		}

		// chack that the relation does not exist
		XPath xpathSelector3 = DocumentHelper.createXPath("//relation[id1='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element1) + "'][id2='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element2) + "']" //$NON-NLS-1$
				+ "|" //$NON-NLS-1$
				+ "//relation[id2='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element1) + "'][id1='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element2) + "']"); //$NON-NLS-1$
		List list = xpathSelector3.selectNodes(doc);
		if (list.isEmpty() == false) {
			return;
		}

		// add the relation
		Element link1 = doc.getRootElement().addElement("relation"); //$NON-NLS-1$
		link1.addElement("id1").addText( //$NON-NLS-1$
				java.lang.Integer.toString(element1));
		link1.addElement("id2").addText( //$NON-NLS-1$
				java.lang.Integer.toString(element2));
		link1.addElement("comment").addText(comment); //$NON-NLS-1$
		link1.addElement("type").addText(type); //$NON-NLS-1$

		writeToXml(doc);

	}

	public String getCreatorName() {
		return creatorName;
	}

	public String getDiscFileName() {
		return getFile().getName();
	}

	public String getDiscName() {
		return discName;
	}

	public IFile getFile() {
		return myToK.getDiscussionFolder().getFile(discName + "." + DISCUSSION_EXTENSION); //$NON-NLS-1$
	}

	/** Returns the file associated with this as an IEditorInput */
	public IEditorInput getIEditorInput() {
		return new FileEditorInput(getFile());
	}

	public ToK getMyToK() {
		return myToK;
	}

	public Integer[] getOpinionIDs() {
		Opinion[] ops = getOpinions();

		Integer[] ss = new Integer[ops.length];

		for (int i = 0; i < ops.length; i++) {
			ss[i] = ops[i].getId();
		}

		return ss;
	}

	public String[] getOpinionNames() {
		Opinion[] ops = getOpinions();

		String[] ss = new String[ops.length];

		for (int i = 0; i < ops.length; i++) {
			ss[i] = ops[i].getName();
			// Note(Shay): Discussion hides the opinion XML name
			if (ss[i].equals(DEFAULT_OPINION_XML))
				ss[i] = DEFAULT_OPINION_DISPLAY;
		}

		return ss;
	}

	public Opinion[] getOpinions() {
		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//opinion"); //$NON-NLS-1$
		List result = xpathSelector.selectNodes(doc);

		Opinion[] ss = new Opinion[result.size()];
		int i = 0;
		for (Object object : result) {
			Element e = (Element) object;
			ss[i++] = new Opinion(e);
		}

		return ss;
	}
	
	/**
	 * Returns true if the opinion's display name is the same as the def opinion's display name
	 * @param opinionName the name of the opinion
	 * @return true if it is the default opinion, false otherwise
	 */
	public boolean isDefaultOpinion(String opinionName)
	{
		return opinionName.equals( DEFAULT_OPINION_DISPLAY );
	}
	
	/** Returns true iff this is the def opinion */
	public boolean isDefaultOpinion(int opinionID)
	{
		return opinionID == defaultOpinionID;
	}

	public Integer getOpinionsId(String opinionName) {
		for (Opinion opinion : getOpinions()) {
			if (opinionName.equals(opinion.getName())) {
				return opinion.getId();
			}
		}

		return null;
	}

	/**
	 * getting the quotes from an opinion
	 * 
	 * @param opinionDisp The display name of the opinion
	 * @return quotes
	 */
	public Quote[] getQuotes(String opinionDisp) {

		int j = 0;
		Document doc = readFromXML();
		
		String opinionXML = opinionDisp;
		if (opinionXML.equals(DEFAULT_OPINION_DISPLAY))
			opinionXML = DEFAULT_OPINION_XML;

		List result = doc
				.selectNodes("//opinion[name='" + opinionXML + "']/quote"); //$NON-NLS-1$ //$NON-NLS-2$

		Quote[] quotes = new Quote[result.size()];
		for (Object object : result) {
			Element elem = (Element) object;
			quotes[j++] = new Quote(elem, myToK);
		}
		return quotes;
	}

	/**
	 * take the quote from the opinion with the target id and put it in the
	 * opinion with the target id
	 * 
	 * @param quoteId
	 * @param targetId
	 */
	public void relocateQuote(Integer quoteId, Integer targetId) {

		Document doc = readFromXML();

		// If the quote and the target opinion can be found,
		// the quote will move to the target opinion
		XPath xpathSelector1 = DocumentHelper.createXPath("//quote[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(quoteId) + "']"); //$NON-NLS-1$
		List result = xpathSelector1.selectNodes(doc);
		if (result.size() == 1) {
			Element element = (Element) result.get(0);
			XPath xpathSelector2 = DocumentHelper.createXPath("//opinion[id='" //$NON-NLS-1$
					+ java.lang.Integer.toString(targetId) + "']"); //$NON-NLS-1$
			List targets = xpathSelector2.selectNodes(doc);
			if (targets.size() == 1) {
				Element target = (Element) targets.get(0);
				element.detach();
				target.add(element);
			}
		}

		writeToXml(doc);
	}

	/**
	 * remove a relation between 2 elements
	 * 
	 * @param element1
	 * @param element2
	 */
	public void removeRelation(Integer element1, Integer element2) {

		// create a Document containing the discussion
		Document doc = readFromXML();

		// removes the relation, whether it is (element1, element2) or
		// (element2, element1)
		XPath xpathSelector1 = DocumentHelper.createXPath("//relation[id1='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element1) + "'][id2='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element2) + "']" //$NON-NLS-1$
				+ "|" //$NON-NLS-1$
				+ "//relation[id2='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element1) + "'][id1='" //$NON-NLS-1$
				+ java.lang.Integer.toString(element2) + "']"); //$NON-NLS-1$
		List opl1 = xpathSelector1.selectNodes(doc);
		for (Iterator i = opl1.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	/**
	 * remove the given opinion from the discussion
	 * 
	 * @param opinionId
	 */
	public void removeOpinion(Integer opinionId) {

		Document doc = readFromXML();

		if (opinionId == 1) {
			// Will not remove the defult opinion
			return;
		}

		// Move all the quotes from the given opinion to the defult opinion
		XPath xpathSelector3 = DocumentHelper.createXPath("//opinion[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(opinionId) + "']/quote"); //$NON-NLS-1$
		List result3 = xpathSelector3.selectNodes(doc);
		XPath xpathSelector4 = DocumentHelper.createXPath("//opinion[id='1']"); //$NON-NLS-1$
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
		XPath xpathSelector1 = DocumentHelper.createXPath("//opinion[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(opinionId) + "']"); //$NON-NLS-1$
		List result1 = xpathSelector1.selectNodes(doc);
		for (Iterator i = result1.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		removeAllRelations(opinionId, doc);

		writeToXml(doc);
	}

	/**
	 * remove the given quote from the discussion
	 * 
	 * @param quoteId
	 */
	public void removeQuote(Integer quoteId) {

		Document doc = readFromXML();

		// Remove all the quotes (should be only one) with the given id
		// if there are several quotes with the given id, fix it by removing all
		// of them
		// if there are no quotes with the given id, do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//quote[id='" //$NON-NLS-1$
				+ java.lang.Integer.toString(quoteId) + "']"); //$NON-NLS-1$
		List result = xpathSelector1.selectNodes(doc);
		for (Iterator i = result.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		// Remove all the quote's relations
		removeAllRelations(quoteId, doc);

		writeToXml(doc);
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setDiscName(String discName) {
		this.discName = discName;
	}

	private Document discussionSkeleton(String discName, String creatorName) {
		Document doc = DocumentHelper.createDocument();
		Element disc = doc.addElement("discussion"); //$NON-NLS-1$
		disc.addElement("name").addText(discName); //$NON-NLS-1$
		disc.addElement("user").addText(creatorName); //$NON-NLS-1$
		Element defOpin = disc.addElement("opinion"); //$NON-NLS-1$
		defOpin.addElement("id").addText("" + defaultOpinionID); //$NON-NLS-1$ //$NON-NLS-2$
		defOpin.addElement("name").addText(DEFAULT_OPINION_XML); //$NON-NLS-1$
		return doc;
	}

	private String getFullFileName() {
		return getFile().getLocation().toOSString();
	}

	private Document readFromXML() {
		return GeneralFunctions.readFromXML(getFullFileName());
	}

	/**
	 * Removes all the relations in which this item is involved
	 * 
	 * @param id
	 *            the id of the quote or opinion
	 * @param doc
	 *            the document we are working on
	 * @author Shay
	 */
	private void removeAllRelations(Integer id, Document doc) {
		List relationNodes;

		// remove relations in which we are the first item (id1)
		XPath id1relationxPath = DocumentHelper.createXPath("//relation[id1='" //$NON-NLS-1$
				+ java.lang.Integer.toString(id) + "']"); //$NON-NLS-1$
		relationNodes = id1relationxPath.selectNodes(doc);
		for (Iterator i = relationNodes.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		// remove relations in which we are the second item (id2)
		XPath id2relationxPath = DocumentHelper.createXPath("//relation[id2='" //$NON-NLS-1$
				+ java.lang.Integer.toString(id) + "']"); //$NON-NLS-1$
		relationNodes = id2relationxPath.selectNodes(doc);
		for (Iterator i = relationNodes.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}
	}

	// write the document to the XML file
	private void writeToXml(Document doc) {
		localModificationStamp++;
		GeneralFunctions.writeToXml(getFullFileName(), doc);
	}
	
	public static boolean isDiscussion(IFile file) {
		if (!file.getFileExtension().equals(DISCUSSION_EXTENSION))
			return false;
		
		ToK tok = ToK.getProjectToK(file.getProject());
		if (tok == null) return false;
	
		return GeneralFunctions.fileInFolder(file, tok.getDiscussionFolder());
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
	
	public String getLinkType() {
		return linkType;	
	}

	public void setLinkedSourceFile(Source sourceFile) {
		this.linkedSource = sourceFile;	
	}
	
	public Source getLinkedSourceFile() {
		return linkedSource;	
	}

	public int compareTo(Discussion d) {
		return getDiscName().compareTo(d.getDiscName());
	}
	
	public void setLink(Link link) {
		this.link = link;
		
	}
	
	public Link getLink() {
		return link;
		
	}
	
	/** Returns the id of the default opinion in this discussion */
	public int getDefOpID()
	{
		return defaultOpinionID;
	}

	/** Returns stamp of last modification. If modification stamp
	 * changed, the discussion was changed
	 * @return last modification stamp
	 **/ 
	public long getModificationStamp() {
		return localModificationStamp;
	}
}
	
