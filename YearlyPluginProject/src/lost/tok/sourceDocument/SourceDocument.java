package lost.tok.sourceDocument;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lost.tok.GeneralFunctions;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.Document;

public class SourceDocument extends Document {
	Chapter rootChapter;

	String title;

	String author;

	// String subPath; Shay: Is this needed?

	List<Chapter> chapters;

	RangeSearch r;

	private IFile outputFile;

	public void set(org.dom4j.Document d) {
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();

		Element root = d.getRootElement();
		title = root.elementTextTrim("name");
		author = root.elementTextTrim("author");

		rootChapter = new Chapter(getTitle(), "", root, Chapter.CHAPTER_STR
				+ " ");
		update();
	}

	/**
	 * Recalculates the chapter's offset and rangeSearch
	 */
	public void update() {
		chapters.clear();
		r.clear();

		rootChapter.fixOffsetLength(0);
		rootChapter.getTree(chapters);

		for (Chapter c : chapters) {
			r.add(c.getOffset(), c.length, c);
		}

		set(rootChapter.toString());
	}

	public void setUnparsed(String s, String title, String author) {
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();
		this.title = title;
		this.author = author;

		ChapterText ctext = new ChapterText(Chapter.UNPARSED_STR, s.trim()
				+ "\n");
		Chapter firstChapter = new Chapter(Chapter.CHAPTER_STR + " 1:\t"
				+ Chapter.UNPARSED_STR + "\n", Chapter.UNPARSED_STR);
		firstChapter.add(ctext);
		rootChapter = new Chapter(getTitle(), "");
		rootChapter.add(firstChapter);

		update();
	}

	/**
	 * Creates a new {chapter,sub chapter,text} ending at that offset. If the
	 * offset is an unparsed text instance, creates a new chapter (and splits
	 * the instance) Otherwise, closes a previous unparsed text instance (if
	 * exists, and not too far) Returns the best new offset for the caret in the
	 * document, or -1 if unchanged
	 */
	public int createNewChapter(Integer offset, String name) {
		String fullText = this.get();
		int wordEndOffset = offset;
		char offset_char = fullText.charAt(wordEndOffset);
		while (wordEndOffset < fullText.length()
				&& (!Character.isWhitespace(offset_char))) {
			wordEndOffset++;
			offset_char = fullText.charAt(wordEndOffset);
		}

		Chapter c = this.getChapterFromOffset(wordEndOffset);
		if (c instanceof ChapterText) {
			ChapterText newChap = ((ChapterText) c).createNewChapter(
					wordEndOffset - c.getOffset(), name);
			update();
			return newChap.offset;
		}
		// else the command is invalid, and it is ignored
		return -1;
	}

	public Chapter getChapterFromOffset(Integer offset) {
		return r.search(offset);
	}

	public Collection<Chapter> getAllChapters() {
		return chapters;
	}

	// a function that converts a SourceDocument class to an XML document
	public void toXML(IProject tokProj, String sourceName) {
		outputFile = tokProj.getFolder("Sources").getFile(sourceName);

		// create the XML and fill the name and author tags
		createXML();

		// start the iteration on the first level of chapters
		for (Chapter c : rootChapter.children) {
			String currXMLPath = "/source/child";
			preorderFunc(c, currXMLPath);
		}
	}

	// create the XML for the source and fill the name and author tags
	private void createXML() {

		if (!outputFile.exists()) {
			try {
				OutputFormat outformat = OutputFormat.createPrettyPrint();
				outformat.setEncoding("UTF-8");
				IPath res = outputFile.getLocation();
				FileWriter fw = new FileWriter(res.toOSString());
				XMLWriter writer = new XMLWriter(fw, outformat);
				writer.write(sourceSkeleton());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// filling the name and author tags
	private org.dom4j.Document sourceSkeleton() {
		// Create the Skeleton of the source file
		org.dom4j.Document sourceDoc = DocumentHelper.createDocument();
		Element e = sourceDoc.addElement("source");
		e.addElement("name").addText(title);
		e.addElement("author").addText(author);
		e.addElement("child");

		return sourceDoc;
	}

	// a function that runs on the tree in a preorder manner.
	// for every node it will enter the information into the XML file.
	private void preorderFunc(Chapter chapter, String currXMLPath) {
		if (nextChapterIsText(chapter)) {
			// the son of this node is a text node
			treatTextNode(chapter, currXMLPath);
			return;
		} else {
			// this is a regular chapter node
			treatChapterNode(chapter, currXMLPath);
			currXMLPath = currXMLPath + "/chapter[name='" + chapter.getName()
					+ "']/child";
			for (Chapter c : chapter.children) {
				preorderFunc(c, currXMLPath);
			}
		}
	}

	// chaecks if the son of the given chapter is a text node
	private boolean nextChapterIsText(Chapter chapter) {
		if (chapter.children.getFirst() instanceof ChapterText)
			return true;
		return false;
	}

	// entering chapter information to the XML
	private void treatChapterNode(Chapter c, String currXMLPath) {
		// for each node we create the apropriate tags
		org.dom4j.Document doc = GeneralFunctions.readFromXML(outputFile);

		XPath xpathSelector = DocumentHelper.createXPath(currXMLPath);
		List result = xpathSelector.selectNodes(doc);

		Element chapterElm = (Element) result.get(0);
		chapterElm.addElement("name").addText(c.getName());
		chapterElm.addElement("child");

		GeneralFunctions.writeToXml(outputFile, doc);

		System.out.println("chapter name is: " + c.getName());
	}

	// entering chapter & text information to the XML
	private void treatTextNode(Chapter c, String currXMLPath) {
		// if it is a text node we write the contents
		org.dom4j.Document doc = GeneralFunctions.readFromXML(outputFile);

		XPath xpathSelector = DocumentHelper.createXPath(currXMLPath);
		List result = xpathSelector.selectNodes(doc);

		Element chapterElm = (Element) result.get(0);
		Element textElm = chapterElm.addElement("text");
		// the name is of the current chapter, while the content is from the son
		textElm.addElement("name").addText(c.getName());
		textElm.addElement("content").addText(getTextofChild(c));

		GeneralFunctions.writeToXml(outputFile, doc);

		System.out.println("   text node");

	}

	// retrieve the text from the child node
	private String getTextofChild(Chapter c) {
		ChapterText textC = (ChapterText) c.children.getFirst();
		return textC.text;

	}

	protected String getTitle() {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + title
				+ "\n" + Messages.getString("SourceDocument.AuthorLabel")
				+ ":\t" + author + "\n" + "\n"; //$NON-NLS-1$
	}

	public int getAbsoluteOffset(String sourceFilePath, int offset) {
		Chapter c = getChapter(sourceFilePath);
		if (c == null) {
			return -1;
		}
		return c.getOffset() + offset;
	}

	public Chapter getChapter(String chapterPath) {
		return rootChapter.getChapter(chapterPath);
	}

	public ChapterText getChapterText(String chapterPath) {
		return rootChapter.getChapterText(chapterPath);
	}
}
