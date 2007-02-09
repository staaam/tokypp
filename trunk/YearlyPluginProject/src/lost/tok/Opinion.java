package lost.tok;

import org.dom4j.Element;

public class Opinion {
	String name;

	int id;

	public Opinion(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Opinion(Element e) {
		this(e.element("name").getText(), Integer.valueOf(e.element("id")
				.getText()));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
