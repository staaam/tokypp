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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;

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
	
	@SuppressWarnings("unchecked")
	public Quote(Element elem, ToK tok){
		this(elem.element("sourceFile").getText(),
				new ArrayList<Excerption>(),
				elem.element("comment").getText());
		ID = Integer.valueOf(elem.element("id").getText());

		SourceDocument sd = new SourceDocument();
		sd.set(GeneralFunctions.readFromXML(tok.getSource(sourceFilePath)));
		
		List<Element> exps = elem.elements("excerption");
		for (Element exp : exps) {
			Excerption e = new Excerption(exp);
			
			String text = sd.getChapterText(e.getSourceFilePath()).getText();
			int start = e.getStartPos();
			int end = e.getEndPos();
			String exText = text.substring(start, end);
			e.setText(exText);
			
			excerptions.add(e);
		}
	}
	
	/**
	 * Returns a prefix of length j of the first excerption of the quote
	 *	if j == 0 returns the whole excerption
	 * @param j
	 * @param projectName 
	 * @return
	 */
	
	public String getPrefix(int j, String projectName) {
		IFile file = (ToK.getProjectToK(ResourcesPlugin.getWorkspace().getRoot().getProject(projectName))).getSource(getSourceFilePath());
		SourceDocument sourceDoc = new SourceDocument();
		sourceDoc.set(GeneralFunctions.readFromXML(file));
		Excerption excerption = getExcerptions().get(0);
		String text = sourceDoc.getChapterText(excerption.getSourceFilePath()).getText().substring(excerption.getStartPos(), excerption.getEndPos());
		
		if (j==0){
			return text;
		}
		else{
			int i = j > text.length() ? text.length() : j;
			String expPrefix = text.substring(0, i) + "...";
			return expPrefix;
		}
	}
}
