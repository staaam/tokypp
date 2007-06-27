package lost.tok;

import org.dom4j.Element;

public class Relation {

	Integer id1;
	Integer id2;
	String comment;
	String type;
	
	Discussion d;
	
	public Relation(Discussion d, Element e) {
		this(d,
				Integer.parseInt(e.element("id1").getText()),
				Integer.parseInt(e.element("id2").getText()),
				e.element("comment").getText(),
				e.element("type").getText());
	}

	public Relation(Discussion d, Integer id1, Integer id2, String comment, String type) {
		super();
		this.d = d;
		this.id1 = id1;
		this.id2 = id2;
		this.comment = comment;
		this.type = type;
	}

	public void fillElement(Element e) {
		e.addElement("id1").addText(Integer.toString(id1));
		e.addElement("id2").addText(Integer.toString(id2));
		e.addElement("comment").addText(comment);
		e.addElement("type").addText(type);
	}

	public String getFirstName() {
		return getString(id1);
	}

	public String getSecondName() {
		return getString(id2);
	}

	private String getString(int id) {
		try {
			return d.getOpinion(id).getName();
		} catch (Exception e) {
		}
		try {
			return d.getQuote(id).getText();
		} catch (Exception e) {
		}
		return "Unknown id: '" + Integer.toString(id) + "'";
	}
	
	public String getRelationType() {
		return type;
	}

	public String getComment() {
		return comment;
	}
	
	
}
