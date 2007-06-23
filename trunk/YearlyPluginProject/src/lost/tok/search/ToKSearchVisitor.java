package lost.tok.search;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lost.tok.Discussion;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;
import lost.tok.sourceDocument.SourceDocument.KEYWORD;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.search.internal.ui.text.FileMatch;
import org.eclipse.search.internal.ui.text.FileSearchResult;

/**
 * Used to search single resource
 * 
 * @author Michael Gelfand
 *
 */
public class ToKSearchVisitor implements IResourceVisitor {

	private String searchPattern;
	private EnumSet<SearchOption> searchOptions;
	private FileSearchResult searchResult;
	private Pattern pattern;

	public ToKSearchVisitor(String searchPattern, EnumSet<SearchOption> searchOptions, FileSearchResult searchResult) {
		this.searchPattern = searchPattern;
		this.searchOptions = searchOptions;
		this.searchResult = searchResult;
		
		compilePattern();
	}

	/**
	 * Creates search pattern from entered text
	 *
	 */
	private void compilePattern() {
		String s = "";
		int last = 0;
		while (true) {
			int i = searchPattern.indexOf('*', last);
			int j = searchPattern.indexOf('?', last);
			// m = minimal index of 'special' symbol
			int m = (i == -1 ) ? j :
				    (j == -1 || i < j) ? i : j;
			
			if (m == -1) {
				// no special symbols left
				s += Pattern.quote(searchPattern.substring(last));
				break;
			}
				
			if ('\\' == searchPattern.charAt(m-1)) {
				s += Pattern.quote(searchPattern.substring(last, m-1) +
					searchPattern.charAt(m-1));
			} else {
				s += Pattern.quote(searchPattern.substring(last, m)) +
					((searchPattern.charAt(m) == '*') ? ".*" : ".");
			}
			last = m+1;
		}
		pattern = Pattern.compile(s, Pattern.UNICODE_CASE | Pattern.DOTALL |
				(searchOptions.contains(SearchOption.CASE_SENSITIVE) ? 0 : Pattern.CASE_INSENSITIVE));
	}

	/**
	 * Actually starts the resource search
	 */
	public boolean visit(IResource resource) throws CoreException {
		if (!(resource instanceof IFile))
			return true;
		ToK tok = ToK.getProjectToK(resource.getProject());
		if (tok == null) return false;
		
		IFile file = (IFile) resource;
		
		if (Source.isSource(file)) {
			sourceSearch(file);
		}
		
		if (Discussion.isDiscussion(file)) {
			discussionSearch(file);
		}
		
		return false;
	}

	/**
	 * Searches the given discussion file
	 * @param file the discussion file
	 */
	private void discussionSearch(IFile file) {
		Discussion d = ToK.getProjectToK(file.getProject()).getDiscussion(file);
		
		if (d == null) return;
		
		if (searchOptions.contains(SearchOption.DSC_CREATOR)) {
			search(file, d.getCreatorName(), 1);
		}
		
		if (searchOptions.contains(SearchOption.DSC_NAME)) {
			search(file, d.getDiscName(), 1);
		}

//		if (searchOptions.contains(SearchOption.DSC_LINK_SUBJ) && d.getLink() != null) {
//			search(file, d.getLink().getSubject(), 1);
//		}

		if (!(searchOptions.contains(SearchOption.DSC_OPINIONS) ||
			  searchOptions.contains(SearchOption.DSC_QUOTES) || 
			  searchOptions.contains(SearchOption.DSC_QUOTE_COMMENTS)))
			return;
		
		for (Opinion opinion : d.getOpinions()) {
			if (searchOptions.contains(SearchOption.DSC_OPINIONS))
				search(file, opinion.getName(), (opinion.getId() << 20));
			
			if (searchOptions.contains(SearchOption.DSC_QUOTES) || 
				searchOptions.contains(SearchOption.DSC_QUOTE_COMMENTS))
				for (Quote quote : d.getQuotes(opinion.getName())) {
					if (searchOptions.contains(SearchOption.DSC_QUOTES))
						search(file, quote.getText(), ((quote.getID() << 1) + 0) << 19);
					
					if (searchOptions.contains(SearchOption.DSC_QUOTE_COMMENTS))
						search(file, quote.getComment(), ((quote.getID() << 1) + 1) << 19);
				}
		}
		
		
	}

	/**
	 * Searches the given source file
	 * @param file the source file
	 */
	private void sourceSearch(IFile file) {
		if (!(searchOptions.contains(SearchOption.SRC_AUTHOR) ||
			  searchOptions.contains(SearchOption.SRC_TITLE) ||
			  searchOptions.contains(SearchOption.SRC_CONTENT)))
			return;
		SourceDocument sd = new SourceDocument();
		sd.set(file);
		
		if (searchOptions.contains(SearchOption.SRC_TITLE))
			keywordSearch(file, sd, KEYWORD.TITLE);
		
		if (searchOptions.contains(SearchOption.SRC_AUTHOR))
			keywordSearch(file, sd, KEYWORD.AUTHOR);
			
		if (!searchOptions.contains(SearchOption.SRC_CONTENT))
			return;
		
		for (Chapter c : sd.getAllChapters()) {
			if (!(c instanceof ChapterText)) continue;
			
			ChapterText ct = (ChapterText) c;
			search(file, ct.getText(), ct.getOffset());
		}
	}

	private void keywordSearch(IFile file, SourceDocument sd, KEYWORD keyword) {
		int offset = sd.getKeywordOffset(keyword);
		String text = sd.getKeywordValue(keyword);
		search(file, text, offset);
	}

	private void search(IFile file, String text, int offset) {
		for (TextSelection t : search(searchPattern, text)) {
			searchResult.addMatch(new FileMatch(file, offset + t.getOffset(), t.getLength()));
		}
	}

	private LinkedList<TextSelection> search(String what, String where) {
		Matcher m = pattern.matcher(where);
		LinkedList<TextSelection> l = new LinkedList<TextSelection>();
		while (m.find()) {
			System.out.println("at " + m.start() + "-" + m.end() + " match");
			l.add(new TextSelection(m.start(), m.end() - m.start()));
		}
		return l;
	}
}
