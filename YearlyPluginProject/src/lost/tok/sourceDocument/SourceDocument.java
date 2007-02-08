package lost.tok.sourceDocument;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import lost.tok.GeneralFunctions;
import lost.tok.Messages;
import lost.tok.ToK;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.Document;

public class SourceDocument extends Document {
	/** The root chapter of the sourceDocument */
	private Chapter  rootChapter;
	/** The title of the document (includes the source path) */
	private String title;
	/** The author of the document */
	private String author;
	/** A list of all the documents chapters */
	private List<Chapter> chapters;
	/** A range search which can return a chapter, given an offset */
	private RangeSearch r;
	
	private IFile outputFile;

	/** Creates a source document from a source xml */
	public void set(org.dom4j.Document d) {
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();

		Element root = d.getRootElement();	
		title = root.elementTextTrim("name"); //$NON-NLS-1$
		author = root.elementTextTrim("author"); //$NON-NLS-1$

		rootChapter = new Chapter(getTitle(), "", root, Chapter.CHAPTER_STR + " "); //$NON-NLS-1$ //$NON-NLS-2$
		update();
	}
	
	/**
	 * Recalculates the chapter's offset and rangeSearch
	 */
	public void update()
	{
		chapters.clear();
		r.clear();
		
		rootChapter.fixOffsetLength(0);
		rootChapter.getTree(chapters);
		
		for (Chapter c : chapters) {
			r.add(c.getOffset(), c.length, c);
		}
		
		set(rootChapter.toString());
	}
	
	/**
	 * Creates an unparsed document from the given string
	 * @param s the string to be parsed (the document's text)
	 * @param title the title of the document (including source path)
	 * @param author the author of the document
	 */
	public void setUnparsed(String s, String title, String author)
	{
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();
		this.title = title;
		this.author = author;
		
		ChapterText ctext= new ChapterText(Chapter.UNPARSED_STR, s.trim() + "\n"); //$NON-NLS-1$
		Chapter firstChapter = new Chapter(Chapter.CHAPTER_STR + " 1:\t" +  //$NON-NLS-1$
					Chapter.UNPARSED_STR + "\n",Chapter.UNPARSED_STR); //$NON-NLS-1$
		firstChapter.add(ctext);
		rootChapter = new Chapter(getTitle(), ""); //$NON-NLS-1$
		rootChapter.add(firstChapter);
		
		update();
	}
	
	/** 
	 * Creates a new {chapter,sub chapter,text} ending at that offset.
	 * If the offset is an unparsed text instance, creates a new chapter (and splits the instance)
	 * Otherwise, closes a previous unparsed text instance (if exists, and not too far)  
	 * Returns the best new offset for the caret in the document, or -1 if unchanged
	 */
	public int createNewChapter(Integer offset, String name)
	{
		String fullText = this.get();
		int wordEndOffset = offset;
		char offset_char = fullText.charAt(wordEndOffset);
		while ( wordEndOffset < fullText.length() && 
				(!Character.isWhitespace(offset_char)))
		{
			wordEndOffset++;
			offset_char = fullText.charAt(wordEndOffset);		
		}

		Chapter c = this.getChapterFromOffset(wordEndOffset);		
		if (c instanceof ChapterText)
		{
			ChapterText newChap = ((ChapterText)c).createNewChapter(wordEndOffset - c.getOffset(), name);
			update();
			return newChap.offset;
		}
		// else the command is invalid, and it is ignored
		return -1;
	}
	
	/** Returns true if there are any unparsed chapters in the document */
	public boolean containsUnparsed()
	{
		return rootChapter.containsUnparsed();
	}

	/**
	 * Returns the chapter residing in the given offset of the document
	 * @param offset an offset inside the document
	 * @return the chapter containing this offset
	 */
	public Chapter getChapterFromOffset(Integer offset) {
		return r.search(offset);
	}

	/**
	 * Returns ALL the chapters in this document
	 */
	public Collection<Chapter> getAllChapters() {
		return chapters;
	}
	
	/** Converts a SourceDocument object into an XML document */
	public void toXML(IProject tokProj, String sourceName)
	{
		outputFile = tokProj.getFolder(ToK.SOURCES_FOLDER).getFile(sourceName);
		
		//create the XML and fill the name and author tags
		createXML();
		
		//start the iteration on the first level of chapters
		for (Chapter c: rootChapter.children){ 
			String currXMLPath = "/source/child"; //$NON-NLS-1$
			preorderFunc(c,currXMLPath);
		}
	}
	
	/** create the XML for the source and fill the name and author tags */
	private void createXML() {
		
		if (!outputFile.exists()){
			try {
				// Print the entire xml to the byte array
				ByteArrayOutputStream baos = new ByteArrayOutputStream(500);
				OutputFormat outformat = OutputFormat.createPrettyPrint();
				outformat.setEncoding("UTF-8"); //$NON-NLS-1$
				XMLWriter writer = new XMLWriter(baos, outformat);
				writer.write(sourceSkeleton());
				writer.flush();
				
				// create a resource from the byte array				
				ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				outputFile.create(bais, true, null);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** fills the name and author tags */
	private org.dom4j.Document sourceSkeleton() {
		// Create the Skeleton of the source file
		org.dom4j.Document sourceDoc = DocumentHelper.createDocument();
		Element e = sourceDoc.addElement("source"); //$NON-NLS-1$
		e.addElement("name").addText(title); //$NON-NLS-1$
		e.addElement("author").addText(author); //$NON-NLS-1$
		e.addElement("child"); //$NON-NLS-1$
	
		return sourceDoc;
	}
	
	/** Runs on the tree in a preorder manner.
	 * 	for every node it will enter the information into the XML file. 
	 */
	private void preorderFunc(Chapter chapter, String currXMLPath){
		if (nextChapterIsText(chapter)){
			//the son of this node is a text node
			treatTextNode(chapter,currXMLPath);
			return;
		}
		else{
			//this is a regular chapter node
			treatChapterNode(chapter,currXMLPath);
			currXMLPath = currXMLPath + "/chapter[name='" + chapter.getName() + "']/child"; //$NON-NLS-1$ //$NON-NLS-2$
			for (Chapter c: chapter.children){ 
				preorderFunc(c,currXMLPath);
			}
		}
	}
	
	

	//chaecks if the son of the given chapter is a text node
	private boolean nextChapterIsText(Chapter chapter) {
		if (chapter.children.getFirst() instanceof ChapterText )
			return true;
		return false;
	}

	//entering chapter information to the XML
	private void treatChapterNode(Chapter c, String currXMLPath){
		//for each node we create the apropriate tags
		org.dom4j.Document doc = GeneralFunctions.readFromXML(outputFile);
		
		XPath xpathSelector = DocumentHelper.createXPath(currXMLPath);
		List result = xpathSelector.selectNodes(doc);
		
		Element chapterElm = (Element) result.get(0);
		chapterElm.addElement("name").addText(c.getName());			 //$NON-NLS-1$
		chapterElm.addElement("child"); //$NON-NLS-1$
	
		
		GeneralFunctions.writeToXml(outputFile, doc);

		System.out.println("chapter name is: " + c.getName()); //$NON-NLS-1$
	}
	
	//entering chapter & text information to the XML
	private void treatTextNode(Chapter c, String currXMLPath) {
		//if it is a text node we write the contents
		org.dom4j.Document doc = GeneralFunctions.readFromXML(outputFile);
		
		XPath xpathSelector = DocumentHelper.createXPath(currXMLPath);
		List result = xpathSelector.selectNodes(doc);
		
		Element chapterElm = (Element) result.get(0);
		Element textElm = chapterElm.addElement("text"); //$NON-NLS-1$
		//the name is of the current chapter, while the content is from the son
		textElm.addElement("name").addText(c.getName()); //$NON-NLS-1$
		textElm.addElement("content").addText(getTextofChild(c)); //$NON-NLS-1$
		
		GeneralFunctions.writeToXml(outputFile, doc);
		
		System.out.println("   text node"); //$NON-NLS-1$
		
	}

	//retrieve the text from the child node
	private String getTextofChild(Chapter c) {
		ChapterText textC = (ChapterText) c.children.getFirst();
		return textC.text;
		
	}

	/** Returns the title (the upper label) of the source document */
	protected String getTitle() {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + title + "\n" + //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				Messages.getString("SourceDocument.AuthorLabel") + ":\t" + author + "\n" +  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				"\n"; //$NON-NLS-1$
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
