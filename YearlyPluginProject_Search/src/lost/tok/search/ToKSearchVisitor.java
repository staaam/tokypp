package lost.tok.search;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lost.tok.Discussion;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.filebuffers.FileBuffers;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.search.internal.ui.text.FileMatch;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.Match;

public class ToKSearchVisitor implements IResourceVisitor {

	private String searchPattern;
	private EnumSet<SearchOption> searchOptions;
	private ToKSearchResult searchResult;
	private Pattern pattern;

	public ToKSearchVisitor(String searchPattern, EnumSet<SearchOption> searchOptions, ToKSearchResult searchResult) {
		this.searchPattern = searchPattern;
		this.searchOptions = searchOptions;
		this.searchResult = searchResult;
		
		compilePattern();
	}

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

	public boolean visit(IResource resource) throws CoreException {
		if (resource instanceof IFile) {
			ToK tok = ToK.getProjectToK(resource.getProject());
			if (tok == null) return false;
			
			IFile file = (IFile) resource;
			if (Source.isSource(file)) {
				sourceSearch(file);
			}
			else if (Discussion.isDiscussion(file)) {
				discussionSearch(file);
			}
			return false;
		}
		return true;
	}

	private void discussionSearch(IFile file) {
		//System.out.println("Discussion search: " + file.getName());
	}

	private void sourceSearch(IFile file) {
		//System.out.println("Source search: " + file.getName());
		if (!(searchOptions.contains(SearchOption.SRC_AUTHOR) ||
			  searchOptions.contains(SearchOption.SRC_TITLE) ||
			  searchOptions.contains(SearchOption.SRC_CONTENT)))
			return;
		
		SourceDocument sd = new SourceDocument();
		sd.set(file);
		
		if (searchOptions.contains(SearchOption.SRC_AUTHOR)) {
			search(searchPattern, sd.getAuthor());
		}
			
		if (searchOptions.contains(SearchOption.SRC_TITLE)) {
			search(searchPattern, sd.getTitle());
		}

		if (searchOptions.contains(SearchOption.SRC_CONTENT)) {
			LinkedList<Chapter> l = new LinkedList<Chapter>();
			for (Chapter c : sd.getAllChapters()) {
				if (!(c instanceof ChapterText)) continue;
				
				ChapterText ct = (ChapterText) c;
				for (TextSelection t : search(searchPattern, ct.getText())) {
					//searchResult.addMatch(new Match(new SingleResult(file), t.getOffset(), t.getLength()));
					if (FileBuffers.getTextFileBufferManager().getTextFileBuffer(file.getFullPath()) == null) {
						try {
							FileBuffers.getTextFileBufferManager().connect(file.getFullPath(), null);
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					searchResult.addMatch(new FileMatch(file, t.getOffset(), t.getLength()));
				}
				
			}
		}
	}

	private LinkedList<TextSelection> search(String what, String where) {
		Matcher m = pattern.matcher(where);
		LinkedList<TextSelection> l = new LinkedList<TextSelection>();
		while (m.find()) {
			System.out.println("at " + m.start() + "-" + m.end() + " match");
			l.add(new TextSelection(m.start(), m.end() - m.start() + 1));
		}
		return l;
	}
}
