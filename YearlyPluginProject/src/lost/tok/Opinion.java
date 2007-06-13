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
		this.name = name;
		this.id = id;
	}

	public Opinion(Element e) {
		name = e.element("name").getText();  //$NON-NLS-1$
		id = Integer.valueOf(e.element("id").getText());  //$NON-NLS-1$
		
		// Note(Shay): Sets the name as the display name
		if ( name.equals(Discussion.DEFAULT_OPINION_XML) )
			name = Discussion.DEFAULT_OPINION_DISPLAY;
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
