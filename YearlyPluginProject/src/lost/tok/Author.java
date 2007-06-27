package lost.tok;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Contains all data related to an author of
 * a source in tree of knowledge
 * @author evgeni
 *
 */
public class Author {

	private Integer ID;
	public String name;
	public Integer rank;

	/**
	 * Author class constructor
	 * @param authRank
	 * @param authName
	 */
	public Author(Integer authRank, String authName) {
		name = authName;
		rank = authRank;
		ID = Math.abs(hashCode());
	}

	/**
	 * Author class default constructor
	 */
	public Author() {}
	
	/**
	 * Set authors ID
	 * @param authID
	 */
	public void setID(Integer authID) {
		ID = authID;
	}

	/**
	 * Get authors ID
	 * @return
	 */
	public Integer getID() {
		return ID;
	}

	/**
	 * Set authors rank
	 * @param authRank
	 */
	public void setRank(Integer authRank) {
		rank = authRank;
	}

	/**
	 * Get authors rank
	 * @return
	 */
	public Integer getRank() {
		return rank;
	}
	
	/**
	 * Set authors name
	 * @param authName
	 */
	public void setName(String authName) {
		name = authName;
	}
	
	/**
	 * Get authors name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Creating an author XML element
	 * 
	 * @return Element of Author
	 */
	public Element toXML() {
		Element e = DocumentHelper.createElement("author");
		e.addText(getName());
		
		return e;
	}
	
	/**
	 * Create authors hash code
	 */
	public int hashCode() {
		return 57 * name.hashCode();
	}
}
