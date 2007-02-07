package lost.tok.sourceDocument;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;

public class Chapter {

	/** The title before each chapter */
	static public final String CHAPTER_STR = Messages
			.getString("SourceDocument.ChapterLabel");

	/** The name of an unparsed text excerpt */
	static public final String UNPARSED_STR = "(Unparsed Text)";

	String label;

	/** The name of the chapter. Part of its title */
	String name;

	/** The parent of this chapter. null for root */
	Chapter parent;

	/** The chapters below this one */
	LinkedList<Chapter> children;

	/** The offset of the chapter from the start of the DISPLAYED document */
	Integer offset;

	/** The length of the chapter, including its subchapters */
	Integer length;

	/** Creates a childless Chapter */
	public Chapter(String label, String name) {
		this.label = label;
		this.name = name;
		this.parent = null;
		children = new LinkedList<Chapter>();
		offset = new Integer(0);
		length = new Integer(label.length());
	}

	/** Creates a chapter whose children are derived from the root element */
	public Chapter(String label, String name, Element root, String chapterLabel) {
		this(label, name);

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
			this.add(child);
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

	public void add(Chapter child) {
		child.parent = this;
		children.addLast(child);
	}

	public Integer getInnerLength() {
		return label.length();
	}

	public void fixOffsetLength(Integer offset) {
		this.offset = offset;
		length = getInnerLength();

		for (Chapter c : children) {
			c.fixOffsetLength(offset + length);
			length += c.length;
		}
	}

	public void updateLabel() {
		String NewLabelBase = "";
		Chapter son = this;
		while (son.parent != null) {
			int index = son.parent.children.indexOf(son);
			NewLabelBase = "." + (index + 1) + NewLabelBase;
			son = son.parent;
		}
		NewLabelBase = CHAPTER_STR + " " + NewLabelBase.substring(1);
		this.label = getChapterLabel(NewLabelBase, name);
	}

	/** Returns this chapter and all its offsprings, sorted DFS-wise */
	public void getTree(List<Chapter> l) {
		l.add(this);
		for (Chapter child : children)
			child.getTree(l);
	}

	public Chapter getParent() {
		return parent;
	}

	public void setParent(Chapter parrent) {
		this.parent = parrent;
	}

	public Integer getOffset() {
		return offset;
	}

	public String getName() {
		return name;
	}

	public boolean isUnparsed() {
		return false;
	}

	static private Chapter addChapter(Element el, String label) {
		String chapName = el.elementTextTrim("name"); //$NON-NLS-1$
		String chapLabel = getChapterLabel(label, chapName);

		Chapter c = new Chapter(chapLabel, chapName, el, label + "."); //$NON-NLS-1$

		return c;
	}

	static private Chapter addText(Element el, String label) {
		String chapName = el.elementTextTrim("name"); //$NON-NLS-1$
		String chapLabel = getChapterLabel(label, chapName);

		Element textElement = el.element("content"); //$NON-NLS-1$

		// Note(Shay): should we really create a new chapter here?
		Chapter c = new Chapter(chapLabel, chapName);

		c.add(new ChapterText(textElement.getUniquePath(),
				formatText(textElement)));

		return c;
	}

	static private String formatText(Element textElement) {
		return textElement.getStringValue().trim() + "\n"; //$NON-NLS-1$
	}

	static private String getChapterLabel(String label, String name) {
		return label + ":\t" + name + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	public Chapter getChapter(String chapterPath) {
		int slash = chapterPath.indexOf("/");
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

	public ChapterText getChapterText(String chapterPath) {
		Chapter c = getChapter(chapterPath);
		if (c.children.getFirst() instanceof ChapterText) {
			return (ChapterText) c.children.getFirst();
		}
		return null;
	}
}
