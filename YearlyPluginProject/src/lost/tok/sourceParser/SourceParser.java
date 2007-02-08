package lost.tok.sourceParser;

import java.util.LinkedList;

import lost.tok.ToK;
import lost.tok.opTable.SourceDocumentProvider;
import lost.tok.opTable.StyleManager;
import lost.tok.sourceDocument.Chapter;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;

public class SourceParser extends TextEditor 
{
	ToK tok = null;
	
	public static String EditorID = "lost.tok.sourceParser.SourceParser";

	public SourceParser()
	{
		super();
		//setDocumentProvider(new UnparsedDocumentProvider());
		setDocumentProvider(new SourceDocumentProvider());
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
		ISourceViewer srcview = this.getSourceViewer();
		assert (srcview != null);

		srcview.setEditable(true);
		srcview.getTextWidget().setWordWrap(false);
		
		// Note(Shay): Bah... looking for this function was tedious!
		this.getSourceViewerDecorationSupport(srcview).dispose();

		refreshDisplay();
	}

	public void refreshDisplay() {
	
		StyleRange chapterTextStyle = StyleManager.getChapterStyle();
		
		ISourceViewer srcview = this.getSourceViewer();
		
		SourceDocument document = (SourceDocument) srcview.getDocument();
		
		LinkedList<Chapter> allChapters = new LinkedList<Chapter>();
		for (Chapter chapter : document.getAllChapters()) {
			if (!(chapter instanceof ChapterText)) {
				allChapters.add(chapter);
			}
		}

		for (Chapter chapter : allChapters) {
			StyleRange range = chapterTextStyle;
			range.start = chapter.getOffset();
			range.length = chapter.getInnerLength();

			srcview.getTextWidget().setStyleRange(range);
		}

		srcview.getTextWidget().redraw();
	}

	public void openNewChapterDialog(int offset)
	{
		ISourceViewer srcview = this.getSourceViewer();
		SourceDocument document = (SourceDocument) srcview.getDocument();
		
		Chapter c = document.getChapterFromOffset(offset-1);
		
		if (c instanceof ChapterText)
		{
			Shell s = srcview.getTextWidget().getShell();
			EnterTitleDialog di = new EnterTitleDialog(s, this, offset, (ChapterText)c);
			di.open();
			// the dialog will call to createNewChapter once the user has entered a name
		}
	}
	
	public void createNewChapter(int offset, String name)
	{
		if (name.equals(Chapter.UNPARSED_STR))
			return; // Note: the user can't create chapters called (Unparsed Text)
		
		ISourceViewer srcview = this.getSourceViewer();
		SourceDocument document = (SourceDocument) srcview.getDocument();
		int oldTopLineIndex = srcview.getTopIndex(); 
		int newDocOffset = document.createNewChapter(offset-1, name);
		
		int newWidgetOffset = modelOffset2WidgetOffset(srcview, newDocOffset);
		srcview.getTextWidget().setCaretOffset(newWidgetOffset);
		srcview.setTopIndex(oldTopLineIndex);
		refreshDisplay();
	}
	
	public int getCaretLocation()
	{
		ISourceViewer srcview = this.getSourceViewer();
		int cursorWidgetOffset = srcview.getTextWidget().getCaretOffset(); 
		return widgetOffset2ModelOffset(srcview, cursorWidgetOffset);
	}
	
	public void close(boolean save)
	{
		super.close(save);
		
		// TODO(Shay): Consider calling to save
		// Get the target unparsed file and delete it
		IFile iFile = getInputIFile();
		if (iFile != null)
		{
			try {
				iFile.delete(true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** Returns the IFile opened in the editor */
	protected IFile getInputIFile()
	{
		IEditorInput iei = this.getEditorInput();
		if (iei instanceof IFileEditorInput)
		{
			IFileEditorInput ifei = (IFileEditorInput)iei;
			IFile inputFile = ifei.getFile();
			return inputFile;
		}
		return null;
	}
	

}
