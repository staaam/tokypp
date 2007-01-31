package lost.tok.sourceParser;

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

public class EnterTitleDialog extends PopupDialog implements KeyListener{
	
	Text chapNameText = null;
	SourceParser editor;
	int chapOffset;
	
	EnterTitleDialog(Shell shell, SourceParser myEditor, int offset)
	{
		super(shell, PopupDialog.INFOPOPUPRESIZE_SHELLSTYLE,
				true, true, false, false,
				"New Chapter Name:",
				"Write the name of the new chapter. Enter to finish, Esc to cancel");
		editor = myEditor;
		chapOffset = offset;
	}
	
	public Composite createDialogArea(Composite composite)
	{
		Composite diComposite = (Composite) super.createDialogArea(composite);
		
		chapNameText = new Text(diComposite, SWT.SINGLE);
		String defaultFontName = chapNameText.getFont().getFontData()[0].getName();
		chapNameText.setFont(new Font(Display.getCurrent(), defaultFontName, 13, SWT.BOLD ));
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
		//final int ENTER_CODE = 13;
		if (e.character == SWT.CR && chapNameText.getText().length() != 0)
		{
			String newChapterName = chapNameText.getText();
			editor.createNewChapter(chapOffset, newChapterName);
			this.close();
		}
	}
	
	
	
	
	

}
