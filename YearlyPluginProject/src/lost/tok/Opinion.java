package lost.tok;

import org.dom4j.Element;
import org.eclipse.core.runtime.QualifiedName;

public class Opinion {
	/**
	 * Qualified Name of ToK's project that specifies latest opinion used
	 */
	public static final QualifiedName LATEST_QNAME = 
		new QualifiedName("lost.tok", "latest_opinion");  //$NON-NLS-1$ //$NON-NLS-2$

	String name;

	int id;

	public Opinion(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}

	public Opinion(Element e) {
		this(e.element("name").getText(), Integer.valueOf(e.element("id") //$NON-NLS-1$ //$NON-NLS-2$
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
