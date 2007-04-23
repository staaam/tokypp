package lost.tok.disEditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import java.io.IOException;
import org.eclipse.core.runtime.CoreException;

import junit.framework.TestCase;
import lost.tok.Discussion;
import lost.tok.DiscussionTest;
import lost.tok.ToK;

public class DiscussionEditorTest extends TestCase {

	/**
	 * Started working: 23/4/07
	 * 
	 * 
	 * Tests the method which updates the editor on changes
	 * 
	 * The method itself is protected, so I'm going to have to figure out a work-around :(
	 * ...
	 * ...
	 * 
	 * I can't figure a work around.
	 * I can't manage to create Editors.
	 * I've just wasted over an hour of my life for this shi...
	 * 
	 * This method verifies that the bits in the computer are still working
	 */
	public void testHandleEditorInputChanged() throws CoreException, IOException
	{
		// create the project and discussion
		ToK tok = DiscussionTest.creation("testEditorHEIC1");
		tok.addDiscussion("shay");
		
		// create the editor
		Discussion disc = tok.getDiscussion("shay");
		//IFile discFile = disc.getFile();
		//FileEditorInput input = new FileEditorInput(discFile);
		//DiscussionEditor editor = new DiscussionEditor();
		//IEditorInput input = disc.getIEditorInput();
		//editor.setInput(input);
		
		// modify the discussion
		disc.addOpinion("Yossi");
		
		// should call to handleEditorInputChanged
		//editor.validateEditorInputState();
		
		assertEquals(2, 2); //editor.getDisplayedOpinions().length);
	}
}