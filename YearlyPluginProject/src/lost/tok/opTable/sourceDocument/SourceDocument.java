package lost.tok.opTable.sourceDocument;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;
import org.eclipse.jface.text.Document;

public class SourceDocument extends Document {
	Chapter  rootChapter;
	String title;
	String author;
	//String subPath; Shay: Is this needed?

	HashMap<String, Chapter> map;

	RangeSearch r;

	public void set(org.dom4j.Document d) {
		map = new HashMap<String, Chapter>();
		r = new RangeSearch();

		Element root = d.getRootElement();
		
		title = root.elementTextTrim("name");
		author = root.elementTextTrim("author");

		rootChapter = new Chapter(getTitle(), "", root, Chapter.CHAPTER_STR + " ");

		rootChapter.fixOffsetLength(0, map);

		set(rootChapter.toString());

		for (String key : map.keySet()) {
			// if (key.matches(".*/text/\\d+")) {
			Chapter c = map.get(key);
			r.add(c.offset, c.length, key);
			// }
		}
	}
	
	public void setUnparsed(String s, String title, String author)
	{
		map = new HashMap<String, Chapter>();
		r = new RangeSearch();
		this.title = title;
		this.author = author;
		
		ChapterText ctext= new ChapterText(Chapter.UNPARSED_STR, s);
		Chapter firstChapter = new Chapter(Chapter.CHAPTER_STR + " 1:\t" + 
					Chapter.UNPARSED_STR + "\n",Chapter.UNPARSED_STR);
		firstChapter.add(ctext);
		rootChapter = new Chapter(getTitle(), "");
		rootChapter.add(firstChapter);
		
		rootChapter.fixOffsetLength(0, map);
		set(rootChapter.toString());

		for (String key : map.keySet()) {
			// if (key.matches(".*/text/\\d+")) {
			Chapter c = map.get(key);
			r.add(c.offset, c.length, key);
			// }
		}
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
		if (c instanceof ChapterText && Chapter.UNPARSED_STR.equals(c.getName()))
		{
			((ChapterText)c).createNewChapter(wordEndOffset - c.getOffset(), name);
		}
		// else the command is invalid, and is ignored
	}

	public Chapter getChapterFromOffset(Integer offset) {
		return map.get(r.search(offset));
	}

	public Collection<Chapter> getAllChapters() {
		return map.values();
	}

	protected String getTitle() {
		return Messages.getString("SourceDocument.TitleLabel") + ":\t" + title + "\n" +
				Messages.getString("SourceDocument.AuthorLabel") + ":\t" + author + "\n" + 
				"\n"; //$NON-NLS-1$
	}
}
