package lost.tok.opTable.sourceDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;
import org.eclipse.jface.text.Document;

public class SourceDocument extends Document {
	Chapter rootChapter;

	HashMap<String, Chapter> map;

	RangeSearch r;

	public void set(org.dom4j.Document d) {
		map = new HashMap<String, Chapter>();
		r = new RangeSearch();

		Element root = d.getRootElement();

		rootChapter = new Chapter(getTitle(root), ""); //$NON-NLS-1$

		recChildren(rootChapter, root, Messages
				.getString("SourceDocument.ChapterLabel") + " "); //$NON-NLS-1$ //$NON-NLS-2$

		rootChapter.fixOffsetLength(0, map);

		set(rootChapter.toString());

		for (String key : map.keySet()) {
			// if (key.matches(".*/text/\\d+")) {
			Chapter c = map.get(key);
			r.add(c.offset, c.length, key);
			// }
		}
	}

	public Chapter getChapterFromOffset(Integer offset) {
		return map.get(r.search(offset));
	}

	public Collection<Chapter> getAllChapters() {
		return map.values();
	}

	protected String getTitle(Element root) {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + root.elementTextTrim("name") + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				Messages.getString("SourceDocument.AuthorLabel") + ":\t" + root.elementTextTrim("author") + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				"\n"; //$NON-NLS-1$
	}

	protected void recChildren(Chapter c, Element root, String chapterLabel) {
		Iterator itr = root.elementIterator("child"); //$NON-NLS-1$
		Integer sequenceNumber = 1;
		while (itr.hasNext()) {
			Element el = (Element) itr.next();
			Element next = el.element("chapter"); //$NON-NLS-1$
			String nextLabel = chapterLabel + String.valueOf(sequenceNumber++);
			Chapter child;
			if (next != null) {
				child = addChapter(next, nextLabel);
			} else {
				next = el.element("text"); //$NON-NLS-1$
				child = addText(next, nextLabel);
			}
			c.add(child);
		}
	}

	protected Chapter addText(Element el, String label) {

		String chapName = el.elementTextTrim("name"); //$NON-NLS-1$
		String chapLabel = getChapterLabel(label, chapName);

		Element textElement = el.element("content"); //$NON-NLS-1$

		Chapter c = new Chapter(chapLabel, chapName);

		c.add(new ChapterText(textElement.getUniquePath(),
				formatText(textElement)));

		return c;
	}

	protected String formatText(Element textElement) {
		return textElement.getStringValue().trim() + "\n"; //$NON-NLS-1$
	}

	protected Chapter addChapter(Element el, String label) {
		String chapName = el.elementTextTrim("name"); //$NON-NLS-1$
		String chapLabel = getChapterLabel(label, chapName);

		Chapter c = new Chapter(chapLabel, chapName);

		recChildren(c, el, label + "."); //$NON-NLS-1$

		return c;
	}

	protected String getChapterLabel(String label, String name) {
		return label + ":\t" + name + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
