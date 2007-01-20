/**
 * 
 */
package lost.tok;

import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Team LOST
 * 
 */
public class Quote {

	private Integer ID;

	private String sourceFilePath;

	private List<Excerption> excerptions;

	private String comment;

	public Quote(String sourceFilePath, List<Excerption> excerptions) {
		this(sourceFilePath, excerptions, "");
	}

	public Quote(String sourceFilePath, List<Excerption> excerptions,
			String comment) {
		super();
		this.sourceFilePath = sourceFilePath;
		this.excerptions = excerptions;
		this.comment = comment;

		ID = Math.abs(hashCode());
	}

	public int hashCode() {
		return 31 * sourceFilePath.hashCode() + excerptions.hashCode();
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Integer getID() {
		return ID;
	}

	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public List<Excerption> getExcerptions() {
		return excerptions;
	}

	public String getText() {
		String s = new String();
		Iterator i = excerptions.iterator();
		while (i.hasNext()) {
			s += ((Excerption) i.next()).getText();
			if (i.hasNext()) {
				s += " [...] ";
			}
		}
		return s;
	}

	public Element toXML() {
		Element e = DocumentHelper.createElement("quote");
		e.addElement("id").addText(ID.toString());
		e.addElement("sourceFile").addText(sourceFilePath);
		e.addElement("comment").addText(comment);
		for (Excerption excerption : excerptions) {
			e.add(excerption.toXML());
		}
		return e;

	}

}
