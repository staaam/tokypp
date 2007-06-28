package lost.tok;

import org.dom4j.Element;

public class Relation {

	private static final String ELM_TYPE = "type"; //$NON-NLS-1$
	private static final String ELM_COMMENT = "comment"; //$NON-NLS-1$
	private static final String ELM_ID2 = "id2"; //$NON-NLS-1$
	private static final String ELM_ID1 = "id1"; //$NON-NLS-1$
	Object element1;
	Object element2;
	
	String comment;
	String type;
	
	Discussion d;
	
	public Relation(Discussion d, Element e) {
		this(d,
				Integer.parseInt(e.element(ELM_ID1).getText()),
				Integer.parseInt(e.element(ELM_ID2).getText()),
				e.element(ELM_COMMENT).getText(),
				e.element(ELM_TYPE).getText());
	}

	public Relation(Discussion d, Integer id1, Integer id2, String comment, String type) {
		super();
		this.d = d;
		this.element1 = getElement(id1);
		this.element2 = getElement(id2);
		this.comment = comment;
		this.type = type;
	}

	public void fillElement(Element e) {
		e.addElement(ELM_ID1).addText(Integer.toString(getId(element1)));
		e.addElement(ELM_ID2).addText(Integer.toString(getId(element2)));
		e.addElement(ELM_COMMENT).addText(comment);
		e.addElement(ELM_TYPE).addText(type);
	}

	private int getId(Object element) {
		if (element instanceof Opinion) {
			Opinion o = (Opinion) element;
			return o.getId();
		}
		if (element instanceof Quote) {
			Quote q = (Quote) element;
			return q.getID();
		}
		return 0;
	}

	public String getFirstName() {
		return getText(element1);
	}

	public String getSecondName() {
		return getText(element2);
	}

	public Object getFirst() {
		return element1;
	}

	public Object getSecond() {
		return element2;
	}
	
	private String getText(Object element) {
		if (element instanceof Opinion) {
			Opinion o = (Opinion) element;
			return o.getName();
		}
		if (element instanceof Quote) {
			Quote q = (Quote) element;
			return q.getText();
		}
		return ""; //$NON-NLS-1$
	}
	
	private Object getElement(int id) {
		try {
			return d.getOpinion(id);
		} catch (Exception e) {
		}
		try {
			return d.getQuote(id);
		} catch (Exception e) {
		}
		return null;
	}
	
	public String getRelationType() {
		return type;
	}

	public String getComment() {
		return comment;
	}
	
	
}
