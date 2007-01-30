package lost.tok.sourceParser;

import java.util.LinkedList;

import lost.tok.ToK;
import lost.tok.opTable.SourceDocumentProvider;
import lost.tok.opTable.StyleManager;
import lost.tok.opTable.sourceDocument.Chapter;
import lost.tok.opTable.sourceDocument.ChapterText;
import lost.tok.opTable.sourceDocument.SourceDocument;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

public class SourceParser extends TextEditor 
{
	ToK tok = null;

	public SourceParser()
	{
		super();
		setDocumentProvider(new UnparsedDocumentProvider());
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
	static int i = 0; //FIXME(remove)	
	public void createNewChapter(int offset)
	{

		System.out.println(this.getCursorPosition()); // FIXME(Shay): Remove
		ISourceViewer srcview = this.getSourceViewer();
		SourceDocument document = (SourceDocument) srcview.getDocument();
		document.createNewChapter(offset, "Me New Chapter " + (i++)); // TODO(Shay): Add the friggin dialog
		refreshDisplay();
	}

}
