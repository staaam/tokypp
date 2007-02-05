package lost.tok.sourceDocument;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import lost.tok.Excerption;


import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.Document;

public class SourceDocument extends Document {
	Chapter  rootChapter;
	String title;
	String author;
	//String subPath; Shay: Is this needed?

	List<Chapter> chapters;

	RangeSearch r;
	
	private IProject tokProj;
	private String sourceName;

	public void set(org.dom4j.Document d) {
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();

		Element root = d.getRootElement();	
		title = root.elementTextTrim("name");
		author = root.elementTextTrim("author");

		rootChapter = new Chapter(getTitle(), "", root, Chapter.CHAPTER_STR + " ");
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
	
	public void setUnparsed(String s, String title, String author)
	{
		chapters = new LinkedList<Chapter>();
		r = new RangeSearch();
		this.title = title;
		this.author = author;
		
		ChapterText ctext= new ChapterText(Chapter.UNPARSED_STR, s.trim() + "\n");
		Chapter firstChapter = new Chapter(Chapter.CHAPTER_STR + " 1:\t" + 
					Chapter.UNPARSED_STR + "\n",Chapter.UNPARSED_STR);
		firstChapter.add(ctext);
		rootChapter = new Chapter(getTitle(), "");
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

	public Chapter getChapterFromOffset(Integer offset) {
		return r.search(offset);
	}

	public Collection<Chapter> getAllChapters() {
		return chapters;
	}
	
	public void toXML(IProject tokProj, String sourceName)
	{
		//create the XML and fill the name and author tags
		createXML();
		
		//start the iteration on the first level of chapters
		for (Chapter c: rootChapter.children){ 
			String currXMLPath = "/source/child";
			preorderFunc(c,currXMLPath);
		}
	}
	
	private void createXML() {
		IFile parsedSource = tokProj.getFolder("Sources").getFile(sourceName);
		if (!parsedSource.exists()){
			try {
				OutputFormat outformat = OutputFormat.createPrettyPrint();
				outformat.setEncoding("UTF-8");
				IPath res = parsedSource.getLocation();
				FileWriter fw = new FileWriter(res.toOSString());
				XMLWriter writer = new XMLWriter(fw, outformat);
				writer.write(sourceSkeleton());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private org.dom4j.Document sourceSkeleton() {
		// Create the Skeleton of the source file
		org.dom4j.Document sourceDoc = DocumentHelper.createDocument();
		Element e = sourceDoc.addElement("source");
		e.addElement("name").addText(title);
		e.addElement("author").addText(author);
		e.addElement("child");
	
		return sourceDoc;
	}
	
	private org.dom4j.Document readFromXML() {
		String path = tokProj.getFolder("Sources").getFile(sourceName).getLocation().toOSString();
		
		org.dom4j.Document doc = DocumentHelper.createDocument();
		SAXReader reader = new SAXReader();
		try {
			BufferedReader rdr = new BufferedReader(new InputStreamReader(
					new FileInputStream(path), "UTF-8"));
			doc = reader.read(rdr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	
	private void writeToXml(org.dom4j.Document doc) {
		try {
			OutputFormat outformat = OutputFormat.createPrettyPrint();
			outformat.setEncoding("UTF-8");
			String outputFileName = (tokProj.getFolder("Sources").getFile(sourceName)).getLocation().toOSString();
			BufferedWriter wrtr = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outputFileName), "UTF-8"));

			XMLWriter writer = new XMLWriter(wrtr, outformat);
			writer.write(doc);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void preorderFunc(Chapter chapter, String currXMLPath){
		if (nextChapterIsText(chapter)){
			treatTextNode(chapter,currXMLPath);
			return;
		}
		else{
			treatChapterNode(chapter,currXMLPath);
			currXMLPath = currXMLPath + "/chapter[name='" + chapter.getName() + "']/child";
			for (Chapter c: chapter.children){ 
				preorderFunc(c,currXMLPath);
			}
		}
	}
	
	

	private boolean nextChapterIsText(Chapter chapter) {
		if (chapter.children.getFirst() instanceof ChapterText )
			return true;
		return false;
	}

	private void treatChapterNode(Chapter c, String currXMLPath){
		//for each node we create the apropriate tags
		org.dom4j.Document doc = readFromXML();
		
		XPath xpathSelector = DocumentHelper.createXPath(currXMLPath);
		List result = xpathSelector.selectNodes(doc);
		
		Element chapterElm = (Element) result.get(0);
		chapterElm.addElement("name").addText(c.getName());			
		chapterElm.addElement("child");
	
		writeToXml(doc);
		System.out.println("chapter name is: " + c.getName());
	}
	
	private void treatTextNode(Chapter c, String currXMLPath) {
//		if it is a text node we write the contents
		org.dom4j.Document doc = readFromXML();
		
		XPath xpathSelector = DocumentHelper.createXPath(currXMLPath);
		List result = xpathSelector.selectNodes(doc);
		
		Element chapterElm = (Element) result.get(0);
		Element textElm = chapterElm.addElement("text");
		textElm.addElement("name").addText(c.getName());
		textElm.addElement("content").addText(getTextofChild(c));
		
		writeToXml(doc);
		System.out.println("   text node");
		
	}

	private String getTextofChild(Chapter c) {
		ChapterText textC = (ChapterText) c.children.getFirst();
		return textC.text;
		
	}

	protected String getTitle() {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + title + "\n" +
				Messages.getString("SourceDocument.AuthorLabel") + ":\t" + author + "\n" + 
				"\n"; //$NON-NLS-1$
	}
}
