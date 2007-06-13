package lost.tok.html;

import java.util.LinkedList;

import lost.tok.ToK;

/**
 * In case two discussions are linked to the same text
 * Clicking on that text in the source opens a window
 * in which we choose the correct discussion
 * @author Team Lost
 *
 */
public class DiscConflictPage extends HTMLPage {
	
	static private int counter = 0;
	@SuppressWarnings("unused")
	static private LinkedList<DiscussionPage> discs;
	
	
	public DiscConflictPage(LinkedList<DiscussionPage> d)
	{
		super( d.getFirst().getTok(), 
				"Select Discussion", 
				ToK.HTML_FOLDER + "/other/conf" + counter + ".html", 
				"../" + HTMLPage.DEFAULT_CSS );
		
		discs = d;
		counter ++;
		
		// make sure there really is a conflict
		assert(d.size() >= 2); 
	}

	@Override
	protected String getBody() {
		// TODO(Shay) Auto-generated method stub
		return null;
	}

}
