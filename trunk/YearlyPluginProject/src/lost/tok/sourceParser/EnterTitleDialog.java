package lost.tok.sourceParser;

import lost.tok.opTable.StyleManager;
import lost.tok.sourceDocument.ChapterText;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EnterTitleDialog extends PopupDialog implements KeyListener {

	Text chapNameText = null;

	SourceParser editor;

	int chapOffset;

	ChapterText targetChap;

	/**
	 * Creates a new dialog for entering a new chapter's name
	 * 
	 * @param shell
	 *            the shell on which the dialog will be displayed
	 * @param myEditor
	 *            the editor which will be called upon finish
	 * @param offset
	 *            the offset of the target in the text
	 * @param ct
	 *            the chapter text which would be splitted \ renamed
	 */
	EnterTitleDialog(Shell shell, SourceParser myEditor, int offset,
			ChapterText ct) {
		super(shell, PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE, true, true, false,
				false, "New Chapter Name:",
				"Write the name of the new chapter. Enter to finish, Esc to cancel");
		editor = myEditor;
		chapOffset = offset;
		targetChap = ct;
	}

	public Composite createDialogArea(Composite composite) {
		Composite diComposite = (Composite) super.createDialogArea(composite);

		chapNameText = new Text(diComposite, SWT.SINGLE);
		String defaultFontName = chapNameText.getFont().getFontData()[0]
				.getName();
		chapNameText.setFont(new Font(Display.getCurrent(), defaultFontName,
				13, SWT.BOLD));
		chapNameText.setTextLimit(30);
		// TODO(Shay): Set the default text to the last one written
		GridData gd = new GridData(300, 28);
		chapNameText.setLayoutData(gd);

		chapNameText.addKeyListener(this);
		return diComposite;
	}

	public void keyPressed(KeyEvent e) {
		// nothing to do here :(

	}

	public void keyReleased(KeyEvent e) {
		if (!targetChap.isLegalName(chapNameText.getText())) {
			chapNameText.setForeground(StyleManager.DISABLED_FG_TEXT_COLOR);
		} else {
			chapNameText.setForeground(StyleManager.NORMAL_FG_TEXT_COLOR);
			// if the name is legal, check if enter was pressed
			if (e.character == SWT.CR && chapNameText.getText().length() != 0) {
				// return the resutl
				String newChapterName = chapNameText.getText();
				editor.createNewChapter(chapOffset, newChapterName);
				this.close();
			}
		}
	}

}
