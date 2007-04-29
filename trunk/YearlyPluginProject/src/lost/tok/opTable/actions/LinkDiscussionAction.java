package lost.tok.opTable.actions;

import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.jface.action.IAction;

/**
 * The action of linking a discussion to the currently marked text
 */
public class LinkDiscussionAction extends AbstractEditorAction {
	public void run(IAction action) {
		assert (activeEditor != null);

		ExcerptionView.getView().linkDiscussion();
	}
}
