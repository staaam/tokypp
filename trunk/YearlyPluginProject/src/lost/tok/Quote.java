/**
 * 
 */
package lost.tok;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Team LOST
 * 
 */
public class Quote {

	private Integer ID;

	private Source source;

	private List<Excerption> excerptions;

	private String comment;

	public Quote(Source source, List<Excerption> excerptions) {
		this(source, excerptions, ""); //$NON-NLS-1$
	}

	public Quote(Source source, List<Excerption> excerptions, String comment) {
		super();
		this.source = source;
		this.excerptions = excerptions;
		this.comment = comment;

		ID = Math.abs(hashCode());
	}

	public int hashCode() {
		return 31 * source.toString().hashCode() + excerptions.hashCode();
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Integer getID() {
		return ID;
	}

	public Source getSource() {
		return source;
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
		return Excerption.concat( excerptions );
	}

	/**
	 * Creating a quote XML element
	 * 
	 * @return Element of Quote
	 */
	public Element toXML() {
		Element e = DocumentHelper.createElement("quote"); //$NON-NLS-1$
		e.addElement("id").addText(ID.toString()); //$NON-NLS-1$
		e.addElement("sourceFile").addText(source.toString()); //$NON-NLS-1$
		e.addElement("comment").addText(comment); //$NON-NLS-1$
		for (Excerption excerption : excerptions) {
			e.add(excerption.toXML());
		}
		return e;

	}

	@SuppressWarnings("unchecked")//$NON-NLS-1$
	public Quote(Element elem, ToK tok) {
		this(new Source(tok, elem.element("sourceFile").getText()), //$NON-NLS-1$
				new ArrayList<Excerption>(), elem.element("comment").getText()); //$NON-NLS-1$
		ID = Integer.valueOf(elem.element("id").getText()); //$NON-NLS-1$

		SourceDocument sd = new SourceDocument();
		sd.set(source);

		List<Element> exps = elem.elements("excerption"); //$NON-NLS-1$
		for (Element exp : exps) {
			Excerption e = new Excerption(exp);

			String text = sd.getChapterTextFromXPath(e.getXPath()).getText();
			int start = e.getStartPos();
			int end = e.getEndPos();
			String exText = text.substring(start, end);
			e.setText(exText);

			excerptions.add(e);
		}
	}

	/**
	 * Returns a prefix of length j of the first excerption of the quote if j ==
	 * 0 returns the whole excerption
	 * 
	 * @param j
	 * @param projectName
	 * @return
	 */

	public String getPrefix(int j) {
		String text = excerptions.get(0).getText();

		if (j == 0 || j >= text.length()) {
			return text;
		}

		return text.substring(0, j) + "..."; //$NON-NLS-1$
	}

	// public String getPrefix(int j, String projectName) {
	// IFile file = (ToK.getProjectToK(ResourcesPlugin.getWorkspace()
	// .getRoot().getProject(projectName)))
	// .getSource(getSourceFilePath());
	// SourceDocument sourceDoc = new SourceDocument();
	// sourceDoc.set(GeneralFunctions.readFromXML(file));
	// Excerption excerption = getExcerptions().get(0);
	// String text = sourceDoc.getChapterText(excerption.getSourceFilePath())
	// .getText().substring(excerption.getStartPos(),
	// excerption.getEndPos());
	//
	// if (j == 0) {
	// return text;
	// } else {
	// int i = j > text.length() ? text.length() : j;
	// String expPrefix = text.substring(0, i) + "..."; //$NON-NLS-1$
	// return expPrefix;
	// }
	// }
}
