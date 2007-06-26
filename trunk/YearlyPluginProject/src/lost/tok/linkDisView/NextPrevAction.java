package lost.tok.linkDisView;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class NextPrevAction implements IViewActionDelegate {

	private LinkDisView view;

	public void run(IAction action) {
		assert (view != null);

		if (action.getId().endsWith(".next"))
			view.next();
		else
			view.prev();
	}

	public void init(IViewPart view) {
		assert (view instanceof LinkDisView);
		this.view = (LinkDisView) view;
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}