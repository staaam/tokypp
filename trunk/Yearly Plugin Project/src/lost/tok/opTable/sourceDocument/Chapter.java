package lost.tok.opTable.sourceDocument;

import java.util.HashMap;
import java.util.LinkedList;

public class Chapter {
	String label;

	String name;

	Chapter parent;

	LinkedList<Chapter> children;

	Integer offset;

	Integer length;

	public Chapter(String label, String name) {
		this.label = label;
		this.name = name;
		this.parent = null;
		children = new LinkedList<Chapter>();
		offset = new Integer(0);
		length = new Integer(label.length());
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

	public void fixOffsetLength(Integer offset, HashMap<String, Chapter> map) {
		this.offset = offset;
		length = getInnerLength();

		map.put(label, this);

		for (Chapter c : children) {
			c.fixOffsetLength(offset + length, map);
			length += c.length;
		}
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
}
