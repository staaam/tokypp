package lost.tok.excerptionsView.actions;

import lost.tok.excerptionsView.ExcerptionView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

/**
 * The action of linking a discussion to the currently marked text
 */
public class LinkDiscussionAction implements IViewActionDelegate {
	private ExcerptionView view;

	public void run(IAction action) {
		assert (view != null);

		view.linkDiscussion();
	}

	public void init(IViewPart view) {
		assert (view instanceof ExcerptionView);
		this.view = (ExcerptionView) view;
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
