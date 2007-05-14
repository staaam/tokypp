package lost.tok.opTable;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.ToK;
import lost.tok.disEditor.DiscussionEditor;
import lost.tok.excerptionsView.ExcerptionView;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The operation table is an extension of the Eclipse editor. It displays source
 * files. It allows the user to mark excerptions in it. It DOESN'T allow the
 * user to delete text from it
 * 
 * @author Team LOST
 * @version 0.2
 */
public class OperationTable extends TextEditor {

	@Override
	public void setFocus() {
		super.setFocus();
		if (((FileEditorInput)this.getEditorInput()).getFile().getProjectRelativePath().toPortableString().startsWith(ToK.ROOTS_FOLDER)){
			updateExcerptionView();
		}
	}
	
	@Override
	public void dispose() {
		ExcerptionView.getView().removeMonitoredEditor(this);
		super.dispose();
	}
	
	/** The ID of the operation table editor. */
	public static final String EDITOR_ID = "lost.tok.opTable.OperationTable"; //$NON-NLS-1$
	 
	/** The Constant EDITOR_CONTEXT. */
	public static final String EDITOR_CONTEXT = "lost.tok.opTable.PopUpMenu.Menu"; //$NON-NLS-1$
	
	/** The root discussions. */
	private RootDiscussionsPart rootDiscussions = null;
	
	/** The root discussions view. */
	private boolean rootDiscussionsView = false;

	/**
	 * Creates a new instance of the operation table Initialize it with the
	 * SourceDocumentProvider we wrote.
	 */
	public OperationTable() {
		super();

		rootDiscussions = new RootDiscussionsPart(this);
		setDocumentProvider(new SourceDocumentProvider());
		markedExcerptions = new TreeMap<Integer, Excerption>();
		markedText = new TreeMap<Integer, Integer>();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.editors.text.TextEditor#initializeEditor()
	 */
	protected void initializeEditor() {
        super.initializeEditor();
        setEditorContextMenuId(EDITOR_CONTEXT);
	}
	
	/**
	 * Makes the docuemnt uneditable.
	 * 
	 * @return false
	 */
	public boolean isEditable() {
		return false;
	}

	/*
	 * 
	 * Sets the source viewer as uneditable
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 * 
	 */
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		showChangeInformation(false);
		ISourceViewer srcview = getSourceViewer();
		assert (srcview != null);
		getSourceViewerDecorationSupport(srcview).dispose();
		
		// using Eclipse' word wrapping, but only in LTR languages
		if (GeneralFunctions.isLTR())
			srcview.getTextWidget().setWordWrap(true);
		refreshDisplay();
	}
	
	/** These excerptions contain all the marked text. The dictionary is the function: offset->Excerption */
	SortedMap<Integer, Excerption> markedExcerptions;

	/** Contains the offset and length all the marked text. The dictionary is the function: offset->length Marked text should be highlighted when displayed. This Map should corrospond to the Excerption list (ie - the forth element should point to the text of the forth excerption) */
	SortedMap<Integer, Integer> markedText;

	/**
	 * Update excerption view.
	 */
	private void updateExcerptionView() {
		ExcerptionView view = ExcerptionView.getView();
		if (view == null) return;
		view.updateMonitoredEditor(this);
	}
	
	/**
	 * Updates the colored lines and the general display of the editor.
	 */
	public void refreshDisplay() {
		if (rootDiscussionsView) {
			rootDiscussions.refreshDisplay();
		}
		updateExcerptionView();
		
		StyleRange markedTextStyle = StyleManager.getMarkedStyle();
		StyleRange chapterTextStyle = StyleManager.getChapterStyle();

		ISourceViewer srcview = getSourceViewer();

		/*
		 * CursorLinePainter a = new CursorLinePainter(srcview);
		 * a.deactivate(true);
		 * srcview.getTextWidget().addLineBackgroundListener(a);
		 */

		SourceDocument document = (SourceDocument) srcview.getDocument();
		Integer docLen = document.getLength();

		LinkedList<Chapter> allChapters = new LinkedList<Chapter>();
		for (Chapter chapter : document.getAllChapters()) {
			if (!(chapter instanceof ChapterText)) {
				allChapters.add(chapter);
			}
		}

		int nStyles = markedText.size();
		// nStyles += allChapters.size();

		int[] styleOffsetSize = new int[nStyles * 2];
		StyleRange[] styles = new StyleRange[nStyles];

		int i = 0;
		for (Entry<Integer, Integer> olEntry : markedText.entrySet()) {
			styleOffsetSize[2 * i] = olEntry.getKey();
			styleOffsetSize[2 * i + 1] = olEntry.getValue();
			styles[i] = markedTextStyle;
			i++;
		}

		// Note: This call removes all the existing styles, and draws only the
		// new one
		srcview.getTextWidget().setStyleRanges(0, docLen - 1, styleOffsetSize,
				styles);

		for (Chapter chapter : allChapters) {
			StyleRange range = chapterTextStyle;
			range.start = chapter.getOffset();
			range.length = chapter.getInnerLength();

			srcview.getTextWidget().setStyleRange(range);
		}

		srcview.getTextWidget().redraw();
	}

	/**
	 * Adds the selected text to the marked text set.
	 * 
	 * @param t the current selection, which should be added
	 */
	public void mark(TextSelection t) {
		mark(t, true);
	}
	
	/**
	 * Adds/Removes the selected text to/from the marked text set.
	 * 
	 * @param t the current selection, which should be added
	 * @param include whether the mark is inclusive (mark/unmark)
	 */
	public void mark(TextSelection t, boolean include) {
		SourceDocument doc = (SourceDocument) getSourceViewer().getDocument();

		Chapter cStart = doc.getChapterFromOffset(t.getOffset());
		Chapter cEnd = doc.getChapterFromOffset(t.getOffset() + t.getLength());

		Chapter currChap = cStart;
		int currOffset = t.getOffset();
		int lastOffset = t.getOffset() + t.getLength();

		while (currChap != cEnd) {
			if (currChap instanceof ChapterText) {
				int markLen = currChap.getOffset() + currChap.getInnerLength()
						- currOffset;
				markChapterExcerption(currOffset, markLen,
						(ChapterText) currChap, include);
			}
			currOffset = currChap.getOffset() + currChap.getInnerLength();
			currChap = doc.getChapterFromOffset(currOffset); // could be
			// improved, but
			// optimization
			// are last
		}
		// marking the last chapter
		if (currChap instanceof ChapterText) {
			markChapterExcerption(currOffset, lastOffset - currOffset,
					(ChapterText) currChap, include);
		}

		refreshDisplay();
	}

	/**
	 * Updates markedExcerptions and markedText
	 * The mark should be inside a single ChapterText.
	 * 
	 * @param offset the mark's offset inside the document
	 * @param length the length of the mark
	 * @param c the chapter text which contains the marking
	 */
	protected void markChapterExcerption(int offset, int length, ChapterText c) {
		markChapterExcerption(offset, length, c, true);
	}
	
	/**
	 * Updates markedExcerptions and markedText
	 * The mark should be inside a single ChapterText.
	 * 
	 * @param offset the mark's offset inside the document
	 * @param length the length of the mark
	 * @param c the chapter text which contains the marking
	 * @param include whether the mark is inclusive (mark/unmark)
	 */
	protected void markChapterExcerption(int offset, int length, ChapterText c, boolean include) {
		int[] m = findMergedExcerptions(offset, offset + length);

		if (include) {
			putExcerption(m[0], m[1], c);
		} else {
			putExcerption(m[0], offset, c);
			putExcerption(offset + length, m[1], c);
		}
	}

	private void putExcerption(int mergedBegin, int mergedEnd, ChapterText c) {
		int startInText = (mergedBegin - c.getOffset()) + c.getPathOffset();
		int endInText = (mergedEnd - c.getOffset()) + c.getPathOffset();
		String excerptionText = c.toString().substring(startInText, endInText);
		String trimmed = excerptionText.trim();
		if (trimmed.length() != excerptionText.length()) {
			int diff = excerptionText.indexOf(trimmed);
			startInText += diff;
			endInText = startInText + trimmed.length();
			
			mergedBegin += diff;
			mergedEnd = mergedBegin + trimmed.length();
			excerptionText = trimmed;
		}
		
		if (mergedBegin == mergedEnd) return;
		
		Excerption e = new Excerption(c.getExcerptionPath(), excerptionText,
				startInText, endInText);
		
		markedText.put(mergedBegin, mergedEnd - mergedBegin);
		markedExcerptions.put(mergedBegin, e);
	}

	private int[] findMergedExcerptions(int mergedBegin, int mergedEnd) {
		// Searching for a previous marked to merge
		SortedMap<Integer, Integer> prefixMap = markedText.headMap(mergedBegin);
		while (!prefixMap.isEmpty()) {
			Integer beginBefore = prefixMap.lastKey();
			Integer endBefore = beginBefore + prefixMap.get(beginBefore);
			
			// have we reached a marked text that ends before we start?
			if (endBefore < mergedBegin) break;
	
			markedText.remove(beginBefore);
			markedExcerptions.remove(beginBefore);
	
			mergedBegin = Math.min(beginBefore, mergedBegin);
			mergedEnd = Math.max(endBefore, mergedEnd);
		}
	
		// Searching for a later marked to merge
		SortedMap<Integer, Integer> suffixMap = markedText.tailMap(mergedBegin);
		while (!suffixMap.isEmpty()) {
			Integer beginAfter = suffixMap.firstKey();
			Integer endAfter = beginAfter + suffixMap.get(beginAfter);
			if (mergedEnd < beginAfter) break;
				
			markedText.remove(beginAfter);
			markedExcerptions.remove(beginAfter);
	
			// mergedBegin = Math.min(beginAfter, mergedBegin);
			// mergedBegin is always <=
			mergedEnd = Math.max(endAfter, mergedEnd);
		}
		
		int[] r = { mergedBegin,  mergedEnd };
		
		return r;
	}
	
	/**
	 * Returns the selected excerptions in the editor.
	 * 
	 * @return the excerptions
	 */
	public SortedMap<Integer, Excerption> getExcerptions() {
		return markedExcerptions;
	}
	
	/**
	 * Returns the selected excerptions in the editor.
	 * 
	 * @return the marked
	 */
	public List<Excerption> getMarked() {
		return new Vector<Excerption>(markedExcerptions.values());
	}

	/**
	 * Clears all the marked excerptions from the editor.
	 */
	public void clearMarked() {
		markedText.clear();
		markedExcerptions.clear();
		refreshDisplay();
	}

	/**
	 * Returns the Source Document displayed by the editor.
	 * 
	 * @return the document
	 */
	public SourceDocument getDocument() {
		return (SourceDocument) getSourceViewer().getDocument();
	}

	/** The old source viewer configuration. */
	private SourceViewerConfiguration oldSourceViewerConfiguration = null;

	/**
	 * Highlights the discussions links in the editor.
	 */
	public void showDiscussions() {
		if (oldSourceViewerConfiguration == null)
			oldSourceViewerConfiguration = getSourceViewerConfiguration();
		
		rootDiscussionsView = true;

		setSourceViewerConfiguration(
				new RootDisussionsSourceViewerConfiguration(rootDiscussions));
		
		getSourceViewer().setTextDoubleClickStrategy(
				rootDiscussions, IDocument.DEFAULT_CONTENT_TYPE);
		getSourceViewer().setTextHover(
				rootDiscussions, IDocument.DEFAULT_CONTENT_TYPE);
		
		clearMarked();
	}
	
	/**
	 * Hide the discussions links from the editor.
	 */
	public void hideDisucssions() {
		if (oldSourceViewerConfiguration != null)
			setSourceViewerConfiguration(oldSourceViewerConfiguration);
		
		rootDiscussionsView = false;

		getSourceViewer().setTextDoubleClickStrategy(
				oldSourceViewerConfiguration.getDoubleClickStrategy(getSourceViewer(), IDocument.DEFAULT_CONTENT_TYPE), IDocument.DEFAULT_CONTENT_TYPE);
		getSourceViewer().setTextHover(
				oldSourceViewerConfiguration.getTextHover(getSourceViewer(), IDocument.DEFAULT_CONTENT_TYPE), IDocument.DEFAULT_CONTENT_TYPE);

		clearMarked();
	}

	/**
	 * Returns the project associated with the currently displayed file.
	 * 
	 * @return the project
	 */
	public IProject getProject() {
		FileEditorInput fileEditorInput = 
			(FileEditorInput)getEditorInput();
		
		return fileEditorInput.getFile().getProject();
	}

	/**
	 * Removes a specific excerptions from the marked text.
	 * 
	 * @param i the index of the excerption (in terms of start offset in the text)
	 */
	public void removeExcerption(int i) {
		markedExcerptions.remove(i);
		markedText.remove(i);
	}
	
	/**
	 * Returns whether the editor is in View Root Discussions mode
	 *  
	 * @return true if in View Root Discussions mode, and false if not
	 */
	public boolean isRootDiscussionsView() {
		return rootDiscussionsView;
	}
	
}
