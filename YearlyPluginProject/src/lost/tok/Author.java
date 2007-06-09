package lost.tok;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Author {

	/**********************************************************************
	 * M E M B E R S
	 **********************************************************************/
	
	private Integer ID;

	public String name;
	public Integer rank;

	
	/**********************************************************************
	 * C ' T O R
	 **********************************************************************/

	public Author(Integer authRank, String authName) {
		name = authName;
		rank = authRank;
		ID = Math.abs(hashCode());
	}

	public Author() {}
	
	/**********************************************************************
	 * P R O P E R T I E S
	 **********************************************************************/
	
	public void setID(Integer authID) {
		ID = authID;
	}

	public Integer getID() {
		return ID;
	}

	public void setRank(Integer authRank) {
		rank = authRank;
	}

	public Integer getRank() {
		return rank;
	}
	
	public void setName(String authName) {
		name = authName;
	}
	
	public String getName() {
		return name;
	}

	
	/**********************************************************************
	 * P U B L I C   M E T H O D S
	 **********************************************************************/
	
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
	
	public int hashCode() {
		return 57 * name.hashCode();
	}
}
