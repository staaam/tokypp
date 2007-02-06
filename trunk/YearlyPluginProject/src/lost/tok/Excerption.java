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
	
	public Excerption(Element excerption) {
		this(
				excerption.element("sourceFilePath").getText(),
				null,
				Integer.valueOf(excerption.element("startPos").getText()),
				Integer.valueOf(excerption.element("endPos").getText()));
	}

	public Element toXML() {
		Element e = DocumentHelper.createElement("excerption");
		e.addElement("sourceFilePath").addText(sourceFilePath);
		e.addElement("startPos").addText(startPos.toString());
		e.addElement("endPos").addText(endPos.toString());
		return e;
	}

	public void setText(String text) {
		this.text = text;
	}

}
