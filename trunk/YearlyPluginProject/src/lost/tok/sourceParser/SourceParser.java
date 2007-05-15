package lost.tok.sourceParser;

import lost.tok.GeneralFunctions;
import lost.tok.Messages;
import lost.tok.opTable.SourceDocumentProvider;
import lost.tok.opTable.StyleManager;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * The source parsrer. This editor allows the user to parse a document and
 * create from it an xml file
 */
public class SourceParser extends TextEditor {
	/** True if the document is dirty */
	boolean dirty;

	/** The last chapter name that was entered */
	String lastChapterName = null;

	/** The Editor's id in plugins.xml */
	public static String EditorID = "lost.tok.sourceParser.SourceParser"; //$NON-NLS-1$

	/** C'tor */
	public SourceParser() {
		super();
		// setDocumentProvider(new UnparsedDocumentProvider());
		setDocumentProvider(new SourceDocumentProvider());
		dirty = false;
	}

	/** Makes the docuemnt uneditable */
	public boolean isEditable() {
		return false;
	}

	/**
	 * Sets the source viewer as uneditable
	 */
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		showChangeInformation(false);

		ISourceViewer srcview = getSourceViewer();
		assert (srcview != null);

		// Note(Shay): Bah... looking for this function was tedious!
		getSourceViewerDecorationSupport(srcview).dispose();

		// using Eclipse' word wrapping, but only in LTR languages
		if (GeneralFunctions.isLTR())
			srcview.getTextWidget().setWordWrap(true);

		refreshDisplay();
	}

	/** Colors the needed chapters, and highlight texts accordingly */
	public void refreshDisplay() {

		StyleRange parsedChapterTextStyle = StyleManager.getChapterStyle();
		StyleRange normalTextStyle = StyleManager.getNormalStyle();
		StyleRange unparsedChapterTextStyle = StyleManager
				.getUnparsedChapterStyle();
		StyleRange unparsedTextStyle = StyleManager.getUnparsedStyle();

		ISourceViewer srcview = getSourceViewer();

		SourceDocument document = (SourceDocument) srcview.getDocument();
		Integer docLen = document.getLength();

		int nStyles = document.getAllChapters().size();

		// two arrays - one for the offset,length of the styles
		int[] styleOffsetSize = new int[nStyles * 2];
		// and the other for the styles themselves
		StyleRange[] styles = new StyleRange[nStyles];

		int i = 0;
		for (Chapter chapter : document.getAllChapters()) {
			styleOffsetSize[2 * i] = chapter.getOffset();
			styleOffsetSize[2 * i + 1] = chapter.getInnerLength();
			if (!(chapter instanceof ChapterText)) {
				if (chapter.containsUnparsed()) {
					styles[i] = unparsedChapterTextStyle;
				} else {
					styles[i] = parsedChapterTextStyle;
				}
			} else {
				if (chapter.isUnparsed()) {
					styles[i] = unparsedTextStyle;
				} else {
					styles[i] = normalTextStyle;
				}
			}
			i++;
		}

		// Note: This call removes all the existing styles, and draws only the
		// new one
		srcview.getTextWidget().setStyleRanges(0, docLen - 1, styleOffsetSize,
				styles);

		srcview.getTextWidget().redraw();
	}

	/** Opens the "enter new title" dialog, with a certain offset */
	public void openNewChapterDialog(int offset) {
		ISourceViewer srcview = getSourceViewer();
		SourceDocument document = (SourceDocument) srcview.getDocument();

		Chapter c = document.getChapterFromOffset(offset - 1);

		if (c instanceof ChapterText) {
			Shell s = srcview.getTextWidget().getShell();
			EnterTitleDialog di = new EnterTitleDialog(s, this, offset,
					(ChapterText) c, lastChapterName);
			di.open();
			// the dialog will call to createNewChapter once the user has
			// entered a name
		}
	}

	/** Creates a new chapter */
	public void createNewChapter(int offset, String name) {
		lastChapterName = name;

		ISourceViewer srcview = getSourceViewer();
		SourceDocument document = (SourceDocument) srcview.getDocument();
		int oldTopLineIndex = srcview.getTopIndex();
		int newDocOffset = document.createNewChapter(offset - 1, name);

		int newWidgetOffset = modelOffset2WidgetOffset(srcview, newDocOffset);
		srcview.getTextWidget().setCaretOffset(newWidgetOffset);
		srcview.setTopIndex(oldTopLineIndex);
		refreshDisplay();

		boolean oldDirty = dirty;
		dirty = !document.containsUnparsed();
		if (dirty != oldDirty) {
			firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	/** returns the current caret location, in terms of the document */
	public int getCaretLocation() {
		ISourceViewer srcview = getSourceViewer();
		int cursorWidgetOffset = srcview.getTextWidget().getCaretOffset();
		return widgetOffset2ModelOffset(srcview, cursorWidgetOffset);
	}

	/** Marks the editor as dirty (which means it can be saved) */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * Saves the document as a parsed document. This method can only be called
	 * if the document is already parsed. This method also deletes the temporary
	 * unparsed document
	 */
	public void doSave(IProgressMonitor monitor) {
		dirty = false;

		monitor.beginTask(Messages.getString("SourceParser.1"), 4); //$NON-NLS-1$

		IFile unParsedIFile = getInputIFile();
		if (unParsedIFile == null) {
			monitor.worked(3);
			return;
		}

		// the unparsed file name
		String upName = unParsedIFile.getFullPath().lastSegment();
		String srcName = upName.substring(0, upName.lastIndexOf('.')) + ".src"; //$NON-NLS-1$

		IProject tProj = unParsedIFile.getProject();

		ISourceViewer srcview = getSourceViewer();
		SourceDocument document = (SourceDocument) srcview.getDocument();
		monitor.worked(1);

		monitor.setTaskName(Messages.getString("SourceParser.3")); //$NON-NLS-1$
		document.toXML(tProj, srcName);
		monitor.worked(1);

		monitor.setTaskName(Messages.getString("SourceParser.4")); //$NON-NLS-1$
		// IFile srcIFile =
		// tProj.getFolder(ToK.SOURCES_FOLDER).getFile(srcName);

		monitor.worked(1);

		monitor.setTaskName(Messages.getString("SourceParser.5")); //$NON-NLS-1$

		try {
			unParsedIFile.delete(true, null);
			tProj.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		monitor.worked(1);
		monitor.done();

		// trying to refresh again, in case the previous refresh didn't work
		try {
			tProj.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	/** Returns the IFile opened in the editor */
	protected IFile getInputIFile() {
		IEditorInput iei = getEditorInput();
		if (iei instanceof IFileEditorInput) {
			IFileEditorInput ifei = (IFileEditorInput) iei;
			IFile inputFile = ifei.getFile();
			return inputFile;
		}
		return null;
	}

}
