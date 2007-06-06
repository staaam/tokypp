package lost.tok.sourceDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lost.tok.GeneralFunctions;
import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.ToK;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.Document;

/**
 * A document which can be displayed to the user From this document the user
 * creates quotes or links discussions
 */
public class SourceDocument extends Document {
	/** The root chapter of the sourceDocument */
	private Chapter rootChapter;

	/** The title of the document (includes the source path) */
	private String title;

	/** The author of the document */
	private String author;

	/** A list of all the documents chapters */
	private List<Chapter> chapters;

	/** A range search which can return a chapter, given an offset */
	private RangeSearch r;

	private IFile outputFile;
	
	/** A mapping from xPaths to the Chapters of the SourceDocument */
	private HashMap<String, Chapter> xPathToChapter;

	/** Returns true if there are any unparsed chapters in the document */
	public boolean containsUnparsed() {
		return rootChapter.containsUnparsed();
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

		Chapter c = getChapterFromOffset(wordEndOffset);
		if (c instanceof ChapterText) {
			ChapterText newChap = ((ChapterText) c).createNewChapter(
					wordEndOffset - c.getOffset(), name);
			update();
			return newChap.offset;
		}
		// else the command is invalid, and it is ignored
		return -1;
	}

	public int getAbsoluteOffset(String ePath, int offset) {
		Chapter c = getChapterFromEPath(ePath);
		if (c == null) {
			return -1;
		}
		return c.getOffset() + offset;
	}

	/**
	 * Returns ALL the chapters in this document
	 */
	public List<Chapter> getAllChapters() {
		return chapters;
	}

	/** Returns a chapter by its excerption path */
	public Chapter getChapterFromEPath(String chapterPath) {
		return rootChapter.getChapter(chapterPath);
	}

	/**
	 * Returns the chapter residing in the given offset of the document
	 * 
	 * @param offset
	 *            an offset inside the document
	 * @return the chapter containing this offset
	 */
	public Chapter getChapterFromOffset(Integer offset) {
		return r.search(offset);
	}

	/** Returns a chapter text by its excerption path */
	public ChapterText getChapterTextFromEPath(String chapterPath) {
		return rootChapter.getChapterText(chapterPath);
	}
	
	
	/** Returns a chapter text by its unique xPath */
	public ChapterText getChapterTextFromXPath(String xPath) {
		// lazy init
		if (xPathToChapter == null)
			initXPathToChapter();
				
		return (ChapterText) xPathToChapter.get(xPath);
	}

	/** Creates a source document from a source file */
	public void set(Source src) {
		set(src.getFile());
	}
	
	/** Creates a source document from a source file */
	public void set(IFile file) {
		set(GeneralFunctions.readFromXML(file));
	}
	
	/** Creates a source document from a source xml */
	public void set(org.dom4j.Document d) {
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();

		Element root = d.getRootElement();
		title = root.elementTextTrim("name"); //$NON-NLS-1$
		author = root.elementTextTrim("author"); //$NON-NLS-1$

		rootChapter = new Chapter(getHeader(),
				"", root, Chapter.CHAPTER_STR + " "); //$NON-NLS-1$ //$NON-NLS-2$
		update();
	}

	/**
	 * Creates an unparsed document from the given string
	 * 
	 * @param s
	 *            the string to be parsed (the document's text)
	 * @param title
	 *            the title of the document (including source path)
	 * @param author
	 *            the author of the document
	 */
	public void setUnparsed(String s, String title, String author) {
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();
		this.title = title;
		this.author = author;

		ChapterText ctext = new ChapterText(Chapter.UNPARSED_STR, s.trim() + "\n", null); //$NON-NLS-1$
		Chapter firstChapter = new Chapter(Chapter.CHAPTER_STR + " 1:\t" + //$NON-NLS-1$
				Chapter.UNPARSED_STR + "\n", Chapter.UNPARSED_STR, null); //$NON-NLS-1$
		firstChapter.add(ctext);
		rootChapter = new Chapter(getHeader(), "", null); //$NON-NLS-1$
		rootChapter.add(firstChapter);

		update();
	}

	public String toString() {
		return rootChapter.toString();
	}

	/** Converts a SourceDocument object into an XML document */
	public void toXML(IProject tokProj, String sourceName) {

		outputFile = tokProj.getFolder(ToK.SOURCES_FOLDER).getFile(sourceName);

		if (outputFile.exists())
			return; // nothing we can do

		// create the XML and fill the name and author tags
		try {
			// Print the entire xml to the byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setTrimText(false);
			outformat.setEncoding("UTF-8"); //$NON-NLS-1$
			XMLWriter writer = new XMLWriter(baos, outformat);
			writer.write(toXMLDocument());
			writer.flush();

			// create a resource from the byte array
			ByteArrayInputStream bais = new ByteArrayInputStream(baos
					.toByteArray());
			outputFile.create(bais, true, null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CoreException e) {
			e.printStackTrace();
		}

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

	/** fills the name and author tags */
	private org.dom4j.Document toXMLDocument() {
		// Create the Skeleton of the source file
		org.dom4j.Document sourceDoc = DocumentHelper.createDocument();
		Element e = sourceDoc.addElement("source"); //$NON-NLS-1$
		e.addElement("name").addText(title); //$NON-NLS-1$
		e.addElement("author").addText(author); //$NON-NLS-1$

		for (Chapter c : rootChapter.getChildren())
			c.addToXml(e); // will add the chapter and its offsprings

		return sourceDoc;
	}

	/** Returns the title (the upper label) of the source document */
	protected String getHeader() {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + title + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Messages.getString("SourceDocument.AuthorLabel") + ":\t" + author + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"\n"; //$NON-NLS-1$
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
	
	private void initXPathToChapter()
	{
		xPathToChapter = new HashMap<String, Chapter>( getAllChapters().size() );
		
		for (Chapter c : getAllChapters())
		{
			if (c.getChildren().size() == 1 && c.getChildren().getFirst() instanceof ChapterText)
				continue; // dummy chapter
			
			// makes sure we don't overrun an existing key (shouldn't happen)
			assert( !xPathToChapter.containsKey(c.getXPath()) );
			
			xPathToChapter.put( c.getXPath(), c );
		}
	}
}
