package lost.tok.sourceDocument;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import lost.tok.Messages;

import org.dom4j.Element;

/**
 * A chapter in the SourceDocument
 */
public class Chapter {

	/** The title before each chapter */
	static public final String CHAPTER_STR = ""; //$NON-NLS-1$

	// Note(Shay) used to be: Messages.getString("SourceDocument.ChapterLabel");

	/** The name of an unparsed text excerpt */
	static public final String UNPARSED_STR = Messages
			.getString("Chapter.UnparsedTitle"); //$NON-NLS-1$

	/** The displayed label of the chapter */
	protected String label;

	/** The name of the chapter. Part of its title */
	protected String name;

	/** The parent of this chapter. null for root */
	protected Chapter parent;

	/** The chapters below this one */
	protected LinkedList<Chapter> children;

	/** The offset of the chapter from the start of the DISPLAYED document */
	protected Integer offset;

	/** The length of the chapter, including its subchapters */
	protected Integer length;
	
	/** 
	 * The xPath of this Element inside the XML document
	 * 
	 * If this SourceDocument was not created from an XML (i.e. in the source parser),
	 *  then this field is null
	 */
	protected String xPath;

	/** Creates a childless Chapter */
	public Chapter(String label, String name, String xPath) {
		this.label = label;
		this.name = name;
		parent = null;
		children = new LinkedList<Chapter>();
		offset = new Integer(0);
		length = new Integer(label.length());
		
		this.xPath = xPath;
	}

	/** Creates a chapter whose children are derived from the root element */
	public Chapter(String label, String name, Element root, String chapterLabel) {
		this(label, name, root.getUniquePath());

		Iterator itr = root.elementIterator("child"); //$NON-NLS-1$
		// Integer sequenceNumber = 1;
		while (itr.hasNext()) {
			Element el = (Element) itr.next();
			Element next = el.element("chapter"); //$NON-NLS-1$
			String nextLabel = chapterLabel + (children.size() + 1);
			Chapter child;
			if (next != null) {
				child = addChapter(next, nextLabel);
			} else {
				next = el.element("text"); //$NON-NLS-1$
				child = addText(next, nextLabel);
			}
			add(child);
		}
	}

	public String toString() {
		String s = label;
		for (Chapter c : children) {
			String childText = c.toString();
			// map.put(c.label, c);
			s += childText;
		}
		return s;
	}

	/** Adds a new subchapter to (this) */
	public void add(Chapter child) {
		child.parent = this;
		children.addLast(child);
	}

	/** Returns the length of this chapter only (without its sons) */
	public Integer getInnerLength() {
		return label.length();
	}

	/**
	 * Fixes the offsets and lengths of this chapter and its sons
	 * 
	 * @param offset
	 *            the offset of this chapter in the greater document
	 */
	public void fixOffsetLength(Integer offset) {
		this.offset = offset;
		length = getInnerLength();

		for (Chapter c : children) {
			c.fixOffsetLength(offset + length);
			length += c.length;
		}
	}

	/**
	 * Updates the label of the current chapter
	 */
	public void updateLabel() {
		String NewLabelBase = ""; //$NON-NLS-1$
		Chapter son = this;
		while (son.parent != null) {
			int index = son.parent.children.indexOf(son);
			NewLabelBase = "." + (index + 1) + NewLabelBase; //$NON-NLS-1$
			son = son.parent;
		}
		NewLabelBase = CHAPTER_STR + " " + NewLabelBase.substring(1); //$NON-NLS-1$
		label = getChapterLabel(NewLabelBase, name);
	}

	/** Returns this chapter and all its offsprings, sorted DFS-wise */
	public void getTree(List<Chapter> l) {
		l.add(this);
		for (Chapter child : children) {
			child.getTree(l);
		}
	}

	/** Returns the chapter's parent */
	public Chapter getParent() {
		return parent;
	}

	public LinkedList<Chapter> getChildren() {
		return children;
	}

	/**
	 * Sets the chapter's parent anew
	 * 
	 * @param parent
	 *            the name of the new parent
	 */
	public void setParent(Chapter parent) {
		this.parent = parent;
	}

	/** Returns the offset of this chapter in the greater document */
	public Integer getOffset() {
		return offset;
	}

	/** Returns the name of this chapter */
	public String getName() {
		return name;
	}

	/** Returns true if this chapter contains unparsed text */
	public boolean isUnparsed() {
		return false;
	}

	/** Returns true if somewhere under this chapter there is unparsed text */
	public boolean containsUnparsed() {
		for (Chapter c : children) {
			if (c.containsUnparsed()) {
				return true;
			}
		}
		return false;
	}

	/** Creates a new chapter from the element, and returns it */
	static private Chapter addChapter(Element el, String label) {
		String chapName = el.elementTextTrim("name"); //$NON-NLS-1$
		String chapLabel = getChapterLabel(label, chapName);

		Chapter c = new Chapter(chapLabel, chapName, el, label + "."); //$NON-NLS-1$

		return c;
	}

	/** Creates a new chapter containing a text paragraph, and returns it */
	static private Chapter addText(Element el, String label) {
		String chapName = el.elementTextTrim("name"); //$NON-NLS-1$
		String chapLabel = getChapterLabel(label, chapName);

		Element textElement = el.element("content"); //$NON-NLS-1$

		// Note(Shay): should we really create a new chapter here?
		Chapter c = new Chapter(chapLabel, chapName, el.getUniquePath());

		c.add(new ChapterText(chapName, formatText(textElement), el.getUniquePath()));

		return c;
	}

	static private String formatText(Element textElement) {
		return textElement.getStringValue().trim() + "\n"; //$NON-NLS-1$
	}

	static private String getChapterLabel(String label, String name) {
		return label + ":\t" + name + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Returns the chapter according to the chapter path
	 * 
	 * @param chapterPath
	 *            a slash-seperated list of names of chapters' names pointing to
	 *            a chapter
	 * @return the chpater pointed by the list
	 */
	public Chapter getChapter(String chapterPath) {
		int slash = chapterPath.indexOf("/"); //$NON-NLS-1$
		String chapterName = (slash == -1) ? chapterPath : chapterPath
				.substring(0, slash);

		for (Chapter chapter : children) {
			if (chapter.getName().compareTo(chapterName) == 0) {
				return (slash == -1) ? chapter : chapter.getChapter(chapterPath
						.substring(slash + 1));
			}
		}
		return null;
	}

	/**
	 * Returns the text of a chapter according to its chapter path
	 * 
	 * @param chapterPath
	 *            a slash-seperated list of names of chapters' names pointing to
	 *            a chapter
	 * @return The text of the pointed chapter
	 */
	public ChapterText getChapterText(String chapterPath) {
		Chapter c = getChapter(chapterPath);
		if (c.children.getFirst() instanceof ChapterText) {
			return (ChapterText) c.children.getFirst();
		}
		return null;
	}

	/**
	 * Adds this Chapter and its sons to the xml
	 * 
	 * @param element
	 *            the element under which this chapter will be added
	 */
	public void addToXml(Element element) {
		Element chapTextElement = element.addElement("child"); //$NON-NLS-1$
		if ((children.size() == 1)
				&& (children.getFirst() instanceof ChapterText)) {
			// Note(Shay): This chapter contains only text
			// if we decide to remove those lone chpaters, we should remove this
			children.getFirst().addToXml(chapTextElement);
		} else {
			// this chapter has other chapters as children
			Element chapElement = chapTextElement.addElement("chapter"); //$NON-NLS-1$
			chapElement.addElement("name").addText(getName()); //$NON-NLS-1$
			for (Chapter c : children)
				c.addToXml(chapElement);
		}
	}

	/**
	 * Returns the xPath of the element in the source XML file
	 * Returns null if the SourceDocument was not created from an XML file
	 */
	public String getXPath() {
		return xPath;
	}
	
	/**
	 * Returns the number of chapters above this one
	 * Returns zero for root
	 */
	public int getDepth() {
		if (parent == null)
			return 0;
		// else
		return parent.getDepth() + 1;
	}
}
