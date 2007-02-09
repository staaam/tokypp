package lost.tok.opTable;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.Map.Entry;

import lost.tok.Excerption;
import lost.tok.Messages;
import lost.tok.ToK;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * The operation table is an extension of the Eclipse editor. It displays source
 * files. It allows the user to mark excerptions in it. It DOESN'T allow the
 * user to delete text from it
 * 
 * @author Team LOST
 * @version 0.2
 * 
 */
public class OperationTable extends TextEditor {

	public static final String EDITOR_ID = "lost.tok.opTable.OperationTable";

	/**
	 * Creates a new instance of the operation table Initialize it with the
	 * SourceDocumentProvider we wrote
	 */
	public OperationTable() {
		super();

		setDocumentProvider(new SourceDocumentProvider());
		markedExcerptions = new TreeMap<Integer, Excerption>();
		markedText = new TreeMap<Integer, Integer>();
	}

	/**
	 * Makes the docuemnt uneditable
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

		ISourceViewer srcview = this.getSourceViewer();
		assert (srcview != null);

		hookContextMenu(parent);
		this.getSourceViewerDecorationSupport(srcview).dispose();

		refreshDisplay();
	}

	// Shay: this should add the pop up action, but it doesn't work :(
	public void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		// System.out.println("editorContextMenuAboutToShow called");
		addAction(menu, "lost.tok.opTable.MarkPopUpMenu.Action"); //$NON-NLS-1$
	}

	// Shay: this should add the pop up action, but it doesn't work
	protected void hookContextMenu(Control parent) {

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		addAction(menuMgr, "lost.tok.opTable.MarkPopUpMenu.Action"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(false);
		Menu menu = menuMgr.createContextMenu(parent);
		parent.setMenu(menu);

		getSite().registerContextMenu(menuMgr, getSelectionProvider());
	}

	/*
	 * Canceled attribute! /** These excerptions contain all the displayed text
	 * (including the marked one) When the user cuts some text from the middle
	 * of an excerption, it should be splitted into two excerptions.
	 * 
	 * List<Excerption> displayedText;
	 */

	/**
	 * These excerptions contain all the marked text. The dictionary is the
	 * function: offset->Excerption
	 */
	SortedMap<Integer, Excerption> markedExcerptions;

	/**
	 * Contains the offset and length all the marked text. The dictionary is the
	 * function: offset->length Marked text should be highlighted when
	 * displayed. This Map should corrospond to the Excerption list (ie - the
	 * forth element should point to the text of the forth excerption)
	 */
	SortedMap<Integer, Integer> markedText;

	public void refreshDisplay() {
		StyleRange markedTextStyle = StyleManager.getMarkedStyle();
		StyleRange chapterTextStyle = StyleManager.getChapterStyle();

		ISourceViewer srcview = this.getSourceViewer();

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

	public void mark(TextSelection t) {
		SourceDocument doc = (SourceDocument) this.getSourceViewer()
				.getDocument();

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
						(ChapterText) currChap);
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
					(ChapterText) currChap);
		}

		refreshDisplay();
	}

	/**
	 * Updates markedExcerptions and markedText The mark should be inside a
	 * single ChapterText
	 * 
	 * @param offset
	 *            the mark's offset inside the document
	 * @param length
	 *            the length of the mark
	 * @param c
	 *            the chapter text which contains the marking
	 */
	protected void markChapterExcerption(int offset, int length, ChapterText c) {
		int mergedBegin = offset; // we will update this if we make a merge
		int mergedEnd = offset + length;

		// Searching for a previous marked to merge
		SortedMap<Integer, Integer> prefixMap = markedText.headMap(mergedBegin);
		while (!prefixMap.isEmpty()) {
			Integer beginBefore = prefixMap.lastKey();
			Integer endBefore = beginBefore + prefixMap.get(beginBefore);
			if (endBefore >= mergedBegin) {
				markedText.remove(beginBefore);
				markedExcerptions.remove(beginBefore);

				mergedBegin = Math.min(beginBefore, mergedBegin);
				mergedEnd = Math.max(endBefore, mergedEnd);
			} else {
				break; // we have reached a marked text that ends before we
				// start
			}
		}

		// Searching for a later marked to merge
		SortedMap<Integer, Integer> suffixMap = markedText.tailMap(mergedBegin);
		while (!suffixMap.isEmpty()) {
			Integer beginAfter = suffixMap.firstKey();
			Integer endAfter = beginAfter + suffixMap.get(beginAfter);
			if (mergedEnd >= beginAfter) {
				markedText.remove(beginAfter);
				markedExcerptions.remove(beginAfter);

				// mergedBegin = Math.min(beginAfter, mergedBegin); mergedBegin
				// is always <=
				mergedEnd = Math.max(endAfter, mergedEnd);
			} else {
				break;
			}
		}

		markedText.put(mergedBegin, mergedEnd - mergedBegin);
		int startInText = (mergedBegin - c.getOffset()) + c.getPathOffset();
		int endInText = (mergedEnd - c.getOffset()) + c.getPathOffset();
		String excerptionText = c.toString().substring(startInText, endInText);
		Excerption e = new Excerption(c.getExcerptionPath(), excerptionText,
				startInText, endInText);
		markedExcerptions.put(mergedBegin, e);
	}

	public List<Excerption> getMarked() {
		return new Vector<Excerption>(markedExcerptions.values());
	}

	public void clearMarked() {
		markedText.clear();
		markedExcerptions.clear();
		refreshDisplay();
	}

	public void cut(TextSelection t) {
		throw new UnsupportedOperationException(
				"Cut not allowed in this version"); //$NON-NLS-1$
	}

}
