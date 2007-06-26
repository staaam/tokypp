package lost.tok;

import org.dom4j.Element;

public class Relation {

	Integer id1;
	Integer id2;
	String comment;
	String type;
	
	public Relation(Element e) {
		this(Integer.parseInt(e.element("id1").getText()),
				Integer.parseInt(e.element("id2").getText()),
				e.element("comment").getText(),
				e.element("type").getText());
	}

	public Relation(Integer id1, Integer id2, String comment, String type) {
		super();
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
		return Integer.toString(id1);
	}

	public String getSecondName() {
		return Integer.toString(id2);
	}

	public String getRelationType() {
		return type;
	}

	public String getComment() {
		return comment;
	}
	
	
}
