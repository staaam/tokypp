/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok;

import java.util.Iterator;
import java.util.List;

import lost.tok.authEditor.AuthorsEditor;

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

/**
 * Handles all functionality related to authors file 
 * in tree of knowledge project
 * @author evgeni
 *
 */
public class AuthorsHandler {

	private ToK myToK;
	private Integer id = 1;
	public String name;
	public int rank;
	public static final String DEFAULT_RANK = Messages.getString("AuthorsHandler.DefRank");
	public static final String RANK = Messages.getString("AuthorsHandler.Rank");
	public static final String AUTHORS_RANK_TREE = Messages.getString("AuthorsHandler.RankTree");
	public static final String AUTHORS_HIGHEST_RANK = Messages.getString("AuthorsHandler.Highest");
	public static final String AUTHORS_LOWEST_RANK = Messages.getString("AuthorsHandler.Lowest");

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
	}

	/**
	 * Constructor for AuthorsHandler from ToK
	 * 
	 * @param myToK
	 * @param discName
	 * @param creatorName
	 */
	public AuthorsHandler(ToK toK) 
	{
		myToK = toK;
	}

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
		
		int end = authorsFile.lastIndexOf(".xml");

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

	/**
	 * Update authors file with new authors that 
	 * may have been added (in new sources)
	 */
	public void updateFile(){	
		Source[] sources = myToK.getSources();
		
		for(Source src : sources){
			
			String authName = src.getAuthor();
			Document doc = readFromXML();
			Integer defRank = AuthorsEditor.DEFAULT_RANK_ID;

			XPath xpathSelector = DocumentHelper.createXPath("//author[.='" + authName + "']");
			List result = xpathSelector.selectNodes(doc);
			
			//author is NOT in file
			if (result.size() == 0) {
				try {
					this.addAuthor(new Author(defRank,authName), defRank);
				} 
				catch (CoreException e) {				
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Get authors file
	 * @return IFile of authors file
	 */
	public IFile getFile() {
		return myToK.getAuthorFile();
	}

	/**
	 * Returns the file associated with this as an IEditorInput
	 * @return IEditorInput
	 */ 
	public IEditorInput getIEditorInput() {
		return new FileEditorInput(getFile());
	}

	/**
	 * Returns the ToK
	 * @return ToK
	 */
	public ToK getMyToK() {
		return myToK;
	}

	/**
	 * Get ranks
	 * @return array of integers (ranks ids)
	 */
	public Integer[] getRanksIDs() {
		Rank[] ranks = getRanks();

		Integer[] ss = new Integer[ranks.length];

		for (int i = 0; i < ranks.length; i++) {
			ss[i] = ranks[i].getId();
		}

		return ss;
	}

	/**
	 * Get ranks
	 * @return array of ranks objects
	 */
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
	
	/**
	 * Get ranks names
	 * @return array of strings, ranks names
	 */
	public String[] getRanksNames() {
		Rank[] ranks = getRanks();

		String[] ss = new String[ranks.length];

		for (int i = 0; i < ranks.length; i++) {
			ss[i] = ranks[i].getName();
		}

		return ss;
	}

	/**
	 * Get rank's ID
	 * @param rankName
	 * @return rank's ID
	 */
	public Integer getRanksId(String rankName) {
		for (Rank rank : getRanks()) {
			if (rankName.equals(rank.getName())) {
				return rank.getId();
			}
		}

		return null;
	}

	/**
	 * Getting the authors from a rank
	 * 
	 * @param rank
	 * @return authors
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
	 * Relocate author to target rank
	 * 
	 * @param srcRank
	 * @param trgtRank
	 */
	public void relocateAuthor(String authName, Integer trgtRank) {

		Document doc = readFromXML();

		// If the author and the target rank can be found,
		// the author will move to the target rank
		XPath xpathSelector1 = DocumentHelper.createXPath("//author[.='" + authName + "']");
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
	 * Remove author from the authors groups
	 * 
	 * @param authorName
	 */
	public void removeAuthor(String authName) {

		Document doc = readFromXML();

		// Remove all the authors (should be only one) with the given name
		// if there are several authors with the given name, fix it by removing all
		// of them if there are no authors with the given name, do nothing
		XPath xpathSelector1 = DocumentHelper.createXPath("//author[getText()='" + authName + "']");
		List result = xpathSelector1.selectNodes(doc);
		for (Iterator i = result.iterator(); i.hasNext();) {
			Element element = (Element) i.next();
			element.detach();
		}

		writeToXml(doc);
	}

	/**
	 * Get author's rank
	 * @param authName
	 * @return the rank of given author
	 */
	public int getAuthorRank(String authName){
		
		int resultRank = 0;
		
		try{
			Document doc = readFromXML();
	
			XPath xpathSelector1 = DocumentHelper.createXPath("//authorsGroup/author[.='" + authName + "']/../id");
			List result = xpathSelector1.selectNodes(doc);
			
			if (result.size() == 1) {
				Element element = (Element) result.get(0);
	
				resultRank = Integer.valueOf(element.getText());
			}
		}
		catch(Exception e){
		}
		
		return resultRank;
	}
	
	/**
	 * Fet full file name
	 * @return full file name
	 */
	private String getFullFileName() {
		return getFile().getLocation().toOSString();
	}

	/**
	 * Read from XML
	 * @return document associated with authors fiel
	 */
	private Document readFromXML() {
		return GeneralFunctions.readFromXML(getFullFileName());
	}

	/**
	 * Throw core exception 
	 * @param message
	 * @throws CoreException
	 */
	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "lost.tok",
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * Write the document to the XML file
	 * @param doc
	 */
	private void writeToXml(Document doc) {
		GeneralFunctions.writeToXml(getFullFileName(), doc);
	}
	
	/**
	 * Get rank
	 * @param authorsGroup
	 * @return rank ID of given rank
	 */
	private Integer getRank(String authorsGroup){
		
		//default rank
		if(authorsGroup.equals(DEFAULT_RANK))
			return 0;
		
		return Integer.valueOf(authorsGroup.substring(authorsGroup.length()-1));
	}
}


