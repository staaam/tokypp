package lost.tok;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;

import junit.framework.TestCase;
import lost.tok.search.SearchOption;
import lost.tok.search.ToKSearchQuery;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.search.internal.ui.text.FileSearchResult;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.search.ui.text.Match;
import org.eclipse.search.ui.text.MatchEvent;

public class Search extends TestCase {
	
	static ToK tok;
	static String[] discussions = new String[] {
			"searchDiscussion1",
			"searchDiscussion2",
			"searchDiscussion3",
	};
	static String[] sources = new String[] {
		Paths.OR_AHAIM_EN,
		Paths.BABEL_EN,
		Paths.RASHI_EN,
};
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		if (tok != null) return;
		tok = new ToK("searchTestProject", "michael", "");
		
		for (String d : discussions)
			tok.addDiscussion(d);
		
		for (String s : sources)
			tok.addSource(s);
	}

	// search for empty string in none resource - should be no results
	public void testEmptySearchNoFiles() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		try {
			query = new ToKSearchQuery("",
				EnumSet.allOf(SearchOption.class),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		ISearchResult result = query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				
				assertTrue(false);
			}
			
		});
		query.run(new NullProgressMonitor());
	}

	// search for empty string with no options selected - should be no results
	public void testEmptySearchNoOptions() throws CoreException, IOException{
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery("",
				EnumSet.noneOf(SearchOption.class),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		ISearchResult result = query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				
				assertTrue(false);
			}
			
		});
		query.run(new NullProgressMonitor());
	}

	public void testDiscussionNameSearchCase() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "searchDiscussion";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.DSC_NAME, SearchOption.CASE_SENSITIVE),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final HashSet<String> dis = new HashSet<String>();
		for (String d : discussions)
			dis.add(d);
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				for (Match m : me.getMatches()) {
					assertTrue(m.getLength() == searchFor.length());
					IFile file = result.getFile(m.getElement());
					String f = file.getFullPath().toPortableString();
					String d = Discussion.getNameFromFile(f);
					assertTrue(dis.contains(d));
					dis.remove(d);
				}
			}
			
		});
		query.run(new NullProgressMonitor());
	}
	
	public void testDiscussionNameSearchNoCase() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "SeArCHdiSCuSSion";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.DSC_NAME),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final HashSet<String> dis = new HashSet<String>();
		for (String d : discussions)
			dis.add(d);
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				for (Match m : me.getMatches()) {
					assertTrue(m.getLength() == searchFor.length());
					IFile file = result.getFile(m.getElement());
					String f = file.getFullPath().toPortableString();
					String d = Discussion.getNameFromFile(f);
					assertTrue(dis.contains(d));
					dis.remove(d);
				}
			}
		});
		query.run(new NullProgressMonitor());
		assertTrue(dis.isEmpty());
	}

	public void testSourceNameSearchCase() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "Or Ahaim's Torah interpretation";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.SRC_TITLE, SearchOption.CASE_SENSITIVE),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final HashSet<String> dis = new HashSet<String>();
		dis.add("Or Ahaim's Torah interpretation");
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				for (Match m : me.getMatches()) {
					assertTrue(m.getLength() == searchFor.length());
					IFile file = result.getFile(m.getElement());
					String d = new Source(file).getTitle();
					assertTrue(dis.contains(d));
					dis.remove(d);
				}
			}
			
		});
		query.run(new NullProgressMonitor());
		assertTrue(dis.isEmpty());
	}
	
	public void testSourceNameSearchFailCase() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "or Ahaim's Torah interpretation";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.SRC_TITLE, SearchOption.CASE_SENSITIVE),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				assertTrue(false);
			}
			
		});
		query.run(new NullProgressMonitor());
	}
	
	public void testSourceAuthorSearchNoCase() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "raSHi";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.SRC_AUTHOR),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final HashSet<String> dis = new HashSet<String>();
		dis.add("Rashi's Torah interpretation");
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				for (Match m : me.getMatches()) {
					assertTrue(m.getLength() == searchFor.length());
					IFile file = result.getFile(m.getElement());
					String d = new Source(file).getTitle();
					assertTrue(dis.contains(d));
					dis.remove(d);
				}
			}
		});
		query.run(new NullProgressMonitor());
		assertTrue(dis.isEmpty());
	}

	public void testSourceNameExp() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "'s Tora*ret?tion";
		final String searchRes = "'s Torah interpretation";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.SRC_AUTHOR),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final HashSet<String> dis = new HashSet<String>();
		dis.add("Rashi's Torah interpretation");
		dis.add("Or Ahaim's Torah interpretation");
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				for (Match m : me.getMatches()) {
					assertTrue(m.getLength() == searchRes.length());
					IFile file = result.getFile(m.getElement());
					String d = new Source(file).getTitle();
					assertTrue(dis.contains(d));
					dis.remove(d);
				}
			}
		});
		query.run(new NullProgressMonitor());
		assertTrue(dis.isEmpty());
	}

	public void testSourceContent() throws CoreException, IOException {
		ToKSearchQuery query;
		LinkedList<IResource> resources = new LinkedList<IResource>();
		final String searchFor = "Come, we will build ourselves a city";
		resources.add(tok.getProject());
		try {
			query = new ToKSearchQuery(searchFor,
				EnumSet.of(SearchOption.SRC_CONTENT),
				resources);
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		
		final HashSet<String> dis = new HashSet<String>();
		dis.add("Tower of Bavel");
		
		final FileSearchResult result = (FileSearchResult) query.getSearchResult();
		result.addListener(new ISearchResultListener() {
			public void searchResultChanged(SearchResultEvent e) {
				if (!(e instanceof MatchEvent)) return;
				MatchEvent me = (MatchEvent) e;
				if (me.getKind() != MatchEvent.ADDED) return;
				for (Match m : me.getMatches()) {
					assertTrue(m.getLength() == searchFor.length());
					IFile file = result.getFile(m.getElement());
					String d = new Source(file).getTitle();
					assertTrue(dis.contains(d));
					dis.remove(d);
				}
			}
		});
		query.run(new NullProgressMonitor());
		assertTrue(dis.isEmpty());
	}
}
