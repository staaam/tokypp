package lost.tok;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * A piece of text taken from a source. Includes all the information needed to
 * generate the xml entity (as defined in the Spec.)
 * 
 * @author Team LOST
 */
public class Excerption {
	String sourceFilePath;

	String text;

	Integer startPos;

	Integer endPos;

	public Excerption(String path, String text, int start, int end) {
		this.sourceFilePath = path;
		this.startPos = start;
		this.endPos = end;
		this.text = text;
	}

	// for debug and fun
	public String toString() {
		return sourceFilePath + "[" + startPos + ":" + endPos + "]";
	}

	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public int getStartPos() {
		return startPos;
	}

	public int getEndPos() {
		return endPos;
	}

	public String getText() {
		return text;
	}

	@Override
	public int hashCode() {
		return (sourceFilePath + "/" + startPos + "-" + endPos).hashCode();
	}

	public Element toXML() {
		Element e = DocumentHelper.createElement("excerption");
		e.addElement("sourceFilePath").addText(sourceFilePath);
		e.addElement("startPos").addText(startPos.toString());
		e.addElement("endPos").addText(endPos.toString());
		return e;
	}

}
