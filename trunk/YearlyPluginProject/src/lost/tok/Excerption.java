package lost.tok;

import lost.tok.GeneralFunctions.Properties;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * A piece of text taken from a source. Includes all the information needed to
 * generate the xml entity (as defined in the Spec.)
 * 
 * @author Team LOST
 */
public class Excerption extends Properties {
	Integer endPos;

	String sourceFilePath;

	Integer startPos;

	String text;

	public Excerption(Element excerption) {
		this(excerption.element("sourceFilePath").getText(), null, Integer
				.valueOf(excerption.element("startPos").getText()), Integer
				.valueOf(excerption.element("endPos").getText()));
	}

	public Excerption(String path, String text, int start, int end) {
		sourceFilePath = path;
		startPos = start;
		endPos = end;
		this.text = text;
	}

	public Element toXML() {
		Element e = DocumentHelper.createElement("excerption");
		e.addElement("sourceFilePath").addText(sourceFilePath);
		e.addElement("startPos").addText(startPos.toString());
		e.addElement("endPos").addText(endPos.toString());
		return e;
	}

	public int getEndPos() {
		return endPos;
	}

	public String getSourceFilePath() {
		return sourceFilePath;
	}

	public int getStartPos() {
		return startPos;
	}

	public String getText() {
		return text;
	}

	public int hashCode() {
		return (sourceFilePath + "/" + startPos + "-" + endPos).hashCode();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String toString() {
		return sourceFilePath + "[" + startPos + ":" + endPos + "]";
	}
}
