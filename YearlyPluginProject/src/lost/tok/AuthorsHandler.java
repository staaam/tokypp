package lost.tok;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

public class AuthorsHandler {
	
	/**********************************************************************
	 * M E M B E R S
	 **********************************************************************/
	
	private ToK myToK;
	private Integer id = 1;
	public String name;
	public int rank;
	public static final String DEFAULT_RANK = Messages.getString("AuthorsHandler.DefRank");


	/**********************************************************************
	 * C ' T O R 
	 **********************************************************************/

	/**
	 * Constructor for Authors Handler from an XML file
	 * 
	 * @param myToK
	 * @param filename
	 */
	public AuthorsHandler(ToK myToK, String filename) 
	{	
		int max = 0;
		this.myToK = myToK;

		Document d = GeneralFunctions.readFromXML(filename);

		List result = DocumentHelper.createXPath("//id").selectNodes(d);
		for (Iterator i = result.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			if (Integer.valueOf(element.getText()) > max) {
				max = Integer.valueOf(element.getText());
			}
		}
		
		id = max;
		
		//writeToXml(myToK.authorsSkeleton());
	}

	/**
	 * Constructor for AuthorsHandler
	 * 
	 * @param myToK
	 * @param discName
	 * @param creatorName
	 */
	public AuthorsHandler(ToK toK) 
	{
		myToK = toK;
	}

	
	/**********************************************************************
	 * P U B L I C   M E T H O D S 
	 **********************************************************************/
	
	/**
	 * Retrieves file name without path and suffix
	 * @param authorsFile - path string
	 * @return authors file name without siffix
	 */
	public static String getNameFromFile(String authorsFile) {
		int begin = authorsFile.lastIndexOf('/');
		if (begin == -1) {
			begin = authorsFile.lastIndexOf('\\');
		}
		begin++;
		
		//TODO: change dis suffix to xml
		int end = authorsFile.lastIndexOf(".xml"); //$NON-NLS-1$

		if (end == -1)
			return null;

		return authorsFile.substring(begin, end);
	}

	
	/**
	 * Get name from resource
	 * @param resource
	 * @return
	 */
	public static String getNameFromResource(IResource resource) {
		return getNameFromFile(resource.getFullPath().toPortableString());
	}
	
	
	/**
	 * Adding an author to the xml file
	 * 
	 * @param author - the author to add
	 * @param rank
	 * @throws CoreException
	 */
	public void addAuthor(Author author, Integer rank) throws CoreException {

		if (!myToK.getProject().exists() || author == null) {
			throwCoreException("problem with atributes to addAuthor");
			return;
		}

		Document doc = readFromXML();

		// creating the xPath
		XPath xpathSelector = DocumentHelper.createXPath("//authorsGroup[id='" + rank + "']");
		List result = xpathSelector.selectNodes(doc);
		if (result.size() == 1) {
			Element authorsGroup = (Element) result.get(0);
			author.setID(++id);
			authorsGroup.add(author.toXML());
		
			writeToXml(doc);
		}
	}

	public IFile getFile() {
		return myToK.getAuthorFile();// getDiscussionFolder().getFile(discName + ".dis");
	}

	/** Returns the file associated with this as an IEditorInput */
	public IEditorInput getIEditorInput() {
		return new FileEditorInput(getFile());
	}

	public ToK getMyToK() {
		return myToK;
	}

	public Integer[] getRanksIDs() {
		Rank[] ranks = getRanks();

		Integer[] ss = new Integer[ranks.length];

		for (int i = 0; i < ranks.length; i++) {
			ss[i] = ranks[i].getId();
		}

		return ss;
	}

	public Rank[] getRanks() {
		Document doc = readFromXML();

		XPath xpathSelector = DocumentHelper.createXPath("//authorsGroup");
		List result = xpathSelector.selectNodes(doc);

		Rank[] ss = new Rank[result.size()];
		int i = 0;
		for (Object object : result) {
			Element e = (Element) object;
			ss[i++] = new Rank(e);
		}

		return ss;
	}
	
	public String[] getRanksNames() {
		Rank[] ranks = getRanks();

		String[] ss = new String[ranks.length];

		for (int i = 0; i < ranks.length; i++) {
			ss[i] = ranks[i].getName();
		}

		return ss;
	}



	public Integer getRanksId(String rankName) {
		for (Rank rank : getRanks()) {
			if (rankName.equals(rank.getName())) {
				return rank.getId();
			}
		}

		return null;
	}

	/**
	 * getting the authors from a rank
	 * 
	 * @param opinion
	 * @return quotes
	 */
	public Author[] getAuthors(String authorsGroup) {

		int j = 0;
		Document doc = readFromXML();

		List result = doc.selectNodes("//authorsGroup[name='" + authorsGroup + "']/author");
		
		Author[] authors = new Author[result.size()];
		for (Object object : result) {
			Element elem = (Element) object;
			authors[j++] = new Author(getRank(authorsGroup), elem.getText());
		}
		
		return authors;
	}

	/**
	 * take the author and put him in the target rank
	 * 
	 * @param srcRank
	 * @param trgtRank
	 */
	public void relocateAuthor(String authName, Integer trgtRank) {

		Document doc = readFromXML();

		// If the author and the target rank can be found,
		// the author will move to the target rank
		XPath xpathSelector1 = DocumentHelper.createXPath("//author[getText()='" + authName + "']");
		List result = xpathSelector1.selectNodes(doc);
		if (result.size() == 1) {
			Element element = (Element) result.get(0);
			XPath xpathSelector2 = DocumentHelper.createXPath("//authorsGroup[id='"
					+ java.lang.Integer.toString(trgtRank) + "']");
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
	 * remove the given quote from the discussion
	 * 
	 * @param quoteId
	 */
	public void removeAuthor(String authName) {

		Document doc = readFromXML();

		// Remove all the authors (should be only one) with the given name
		// if there are several authors with the given name, fix it by removing all
		// of them 
		// if there are no authors with the given name, do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//author[getText()='" + authName + "']");
		List result = xpathSelector1.selectNodes(doc);
		for (Iterator i = result.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	private String getFullFileName() {
		return getFile().getLocation().toOSString();
	}

	private Document readFromXML() {
		return GeneralFunctions.readFromXML(getFullFileName());
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "lost.tok",
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	// write the document to the XML file
	private void writeToXml(Document doc) {
		GeneralFunctions.writeToXml(getFullFileName(), doc);
	}
	
	private Integer getRank(String authorsGroup){
		return Integer.valueOf(authorsGroup.substring(authorsGroup.length()-1));
	}

}

