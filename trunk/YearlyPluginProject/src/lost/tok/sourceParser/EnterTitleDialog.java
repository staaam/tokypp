/* Tree of Knowledge - An information management Eclipse plugin
 * Copyright (C) 2007 Team Lost
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 * Boston, MA  02110-1301, USA.
 */

package lost.tok.sourceParser;

import lost.tok.Messages;
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

/**
 * A small dialog in which the user can enter the name of the new chapter he
 * creates The dialog allows only legal names to be entered And supports default
 * names which are automatically dispalyed
 */
public class EnterTitleDialog extends PopupDialog implements KeyListener {

	/** The text box part of the dialog */
	Text chapNameText = null;

	/** The editor which we are connected to */
	SourceParser editor;

	/** The offset of the target in the text */
	int chapOffset;

	/** The chapter to split or rename */
	ChapterText targetChap;

	/** The default name displayed to the user */
	String defName;

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
	 * @param defaultName
	 *            the name that will be displayed at first
	 */
	EnterTitleDialog(Shell shell, SourceParser myEditor, int offset,
			ChapterText ct, String defaultName) {
		super(shell, PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE, true, true, false,
				false, Messages.getString("EnterTitleDialog.Title"), //$NON-NLS-1$
				Messages.getString("EnterTitleDialog.Description")); //$NON-NLS-1$
		editor = myEditor;
		chapOffset = offset;
		targetChap = ct;
		defName = defaultName;
	}

	/**
	 * Creates the dialog and its fields
	 */
	public Composite createDialogArea(Composite composite) {
		Composite diComposite = (Composite) super.createDialogArea(composite);

		chapNameText = new Text(diComposite, SWT.SINGLE);
		String defaultFontName = chapNameText.getFont().getFontData()[0]
				.getName();
		chapNameText.setFont(new Font(Display.getCurrent(), defaultFontName,
				13, SWT.BOLD));
		chapNameText.setTextLimit(30);
		if (defName != null) {
			// setting initial text
			chapNameText.setText(defName);
			chapNameText.setSelection(0, defName.length());
		}
		GridData gd = new GridData(300, 28);
		chapNameText.setLayoutData(gd);

		chapNameText.addKeyListener(this);
		return diComposite;
	}

	/** this function is currently on a strike (empty implementation) */
	public void keyPressed(KeyEvent e) {
		// nothing to do here :(

	}

	/**
	 * Receives keys typed by the user Verifies that the current input is legal
	 * (disable the box if it is not) If the user types enter, closes the dialog
	 * and creates the new chapter
	 */
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
				close();
			}
		}
	}

}
