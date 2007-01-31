package lost.tok.sourceDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Element;
import org.eclipse.jface.text.Document;

public class SourceDocument extends Document {
	Chapter  rootChapter;
	String title;
	String author;
	//String subPath; Shay: Is this needed?

	List<Chapter> chapters;

	RangeSearch r;

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
		
		set(rootChapter.toString());

		for (Chapter c : chapters) {
			r.add(c.getOffset(), c.length, c);
		}
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
	 * Related to Source Parser. 
	 */
	public void createNewChapter(Integer offset, String name)
	{
		String fullText = this.get();
		int wordEndOffset = offset;
		char offset_char = fullText.charAt(wordEndOffset);
		while ( wordEndOffset < fullText.length() && 
				(Character.isDigit(offset_char) || Character.isLetter(offset_char)))
		{
			wordEndOffset++;
			offset_char = fullText.charAt(wordEndOffset);		
		}

		Chapter c = this.getChapterFromOffset(wordEndOffset);		
		if (c instanceof ChapterText)
		{
			((ChapterText)c).createNewChapter(wordEndOffset - c.getOffset(), name);
			update();
		}
		// else the command is invalid, and it is ignored
	}

	public Chapter getChapterFromOffset(Integer offset) {
		return r.search(offset);
	}

	public Collection<Chapter> getAllChapters() {
		return chapters;
	}

	protected String getTitle() {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + title + "\n" +
				Messages.getString("SourceDocument.AuthorLabel") + ":\t" + author + "\n" + 
				"\n"; //$NON-NLS-1$
	}
}
