package lost.tok.excerptionsView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import lost.tok.Excerption;
import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.opTable.OperationTable;
import lost.tok.opTable.wizards.NewLinkWizard;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * The Excerption View collects all the excerptions created by the Linkage
 * Editor It provides a visual feedback to the user of the current excerptions
 * 
 */
public class ExcerptionView extends ViewPart {

	class OTSet extends HashSet<OperationTable> {
		private static final long serialVersionUID = 8498357334519966448L;

		private Hashtable<String, OperationTable> nameToOT = new Hashtable<String, OperationTable>();

		@Override
		public boolean add(OperationTable o) {
			if (!super.add(o))
				return false;
			nameToOT.put(ExcerptionView.nameFromOT(o), o);
			return true;
		}

		@Override
		public boolean remove(Object o) {
			if (!super.remove(o))
				return false;
			nameToOT.remove(ExcerptionView.nameFromOT((OperationTable) o));
			return true;
		}

		public boolean remove(String name) {
			return remove(get(name));
		}

		public Set<String> getNames() {
			return nameToOT.keySet();
		}

		public OperationTable get(String name) {
			if (!nameToOT.containsKey(name))
				return null;
			return nameToOT.get(name);
		}
	}

	private Hashtable<IProject, OTSet> projectOTs = new Hashtable<IProject, OTSet>();

	private IProject currentProject = null;

	class TreeObject implements IAdaptable {
		private int id;

		private String name;

		private TreeParent parent;

		public TreeObject(String name) {
			this.name = name;
		}

		public Object getAdapter(Class key) {
			return null;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public TreeParent getParent() {
			return parent;
		}

		public void setId(int id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setParent(TreeParent parent) {
			this.parent = parent;
		}

		public String toString() {
			return getName();
		}
	}

	class TreeParent extends TreeObject {
		private ArrayList<TreeObject> children;

		public TreeParent(String name) {
			super(name);
			children = new ArrayList<TreeObject>();
		}

		public void addChild(TreeObject child) {
			children.add(child);
			child.setParent(this);
		}

		public TreeObject[] getChildren() {
			return children.toArray(new TreeObject[children.size()]);
		}

		public boolean hasChildren() {
			return children.size() > 0;
		}

		public void removeChild(TreeObject child) {
			children.remove(child);
			child.setParent(null);
		}

	}

	class ViewContentProvider implements IStructuredContentProvider,
			ITreeContentProvider {
		private TreeParent invisibleRoot;

		public void dispose() {
		}

		public Object[] getChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).getChildren();
			}
			return new Object[0];
		}

		public Object[] getElements(Object parent) {
			if (parent.equals(getViewSite())) {
				if (invisibleRoot == null) {
					initialize();
				}
				return getChildren(invisibleRoot);
			}
			return getChildren(parent);
		}

		public Object getParent(Object child) {
			if (child instanceof TreeObject) {
				return ((TreeObject) child).getParent();
			}
			return null;
		}

		public boolean hasChildren(Object parent) {
			if (parent instanceof TreeParent) {
				return ((TreeParent) parent).hasChildren();
			}
			return false;
		}

		/*
		 * We will set up a dummy model to initialize tree heararchy. In a real
		 * code, you will connect to a real model and expose its hierarchy.
		 */
		private void initialize() {
			treeBuildAndRefresh();
		}

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		private void treeBuildAndRefresh() {
			invisibleRoot = new TreeParent(""); //$NON-NLS-1$

			if (currentProject != null
					&& projectOTs.containsKey(currentProject)) {
				for (OperationTable ot : getOTs()) {
					TreeParent parentFile = new TreeParent(ExcerptionView
							.nameFromOT(ot));
					for (Integer i : ot.getExcerptions().keySet()) {
						Excerption exp = ot.getExcerptions().get(i);
						String expText = exp.getText();

						// String expPrefix = expText.length() < 40 ? expText
						// : expText.substring(0, 40);
						String expPrefix = expText;
						TreeObject temp = new TreeObject(expPrefix);
						temp.setId(i);
						parentFile.addChild(temp);
					}
					if (parentFile.hasChildren()) {
						invisibleRoot.addChild(parentFile);
					}
				}
			}
			viewer.add(viewer.getTree(), invisibleRoot);
			viewer.refresh();
			viewer.expandAll();
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeParent) {
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			}
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					imageKey);
		}

		public String getText(Object obj) {
			return obj.toString();
		}
	}

	public final static String ID = "lost.tok.excerptionsView.ExcerptionView"; //$NON-NLS-1$

	private IAction deleteAction;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	// private Action doubleClickAction;
	@SuppressWarnings("unused")//$NON-NLS-1$
	private DrillDownAdapter drillDownAdapter;

	private IAction linkDiscussionAction;

	private TreeViewer viewer;

	/**
	 * The constructor.
	 */
	public ExcerptionView() {

	}

	private void refresh() {
		((ViewContentProvider) viewer.getContentProvider())
				.treeBuildAndRefresh();
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		drillDownAdapter = new DrillDownAdapter(viewer);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(linkDiscussionAction);
		manager.add(deleteAction);
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(linkDiscussionAction);
	}

	public IContentProvider getContentProvider() {
		return viewer.getContentProvider();
	}

	/**
	 * Returns the excerptions from the given file name
	 */
	public List<Excerption> getExcerptions(String filename) {
		return getOTs().get(filename).getMarked();
	}

	private OTSet getOTs() {
		return projectOTs.get(currentProject);
	}

	public Object getInput() {
		return viewer.getInput();
	}

	public IBaseLabelProvider getLabelProvider() {
		return viewer.getLabelProvider();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				ExcerptionView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	/**
	 * Should handle double click event in Excerption View
	 * When source double clicked, it becomes active
	 * When excerption double clicked, it's source becomes
	 * active and scrolled to selected excerption 
	 *
	 */
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ITreeSelection selection = (ITreeSelection) viewer.getSelection();
				TreeObject element = (TreeObject) selection.getFirstElement();

				TreeObject sourceElement = element;
				if (!(element instanceof TreeParent))
					sourceElement = element.getParent();
				
				OperationTable operationTable = getOTs().get(sourceElement.getName());
				operationTable.activate();
				
				if (!(element instanceof TreeParent))
					operationTable.scrollToExcerption(element.getId());
			}
		});
	}

	private void makeActions() {
		linkDiscussionAction = new Action() {
			public void run() {
				linkDiscussion();
			}
		};
		linkDiscussionAction.setText(Messages.getString("ExcerptionView.8")); //$NON-NLS-1$
		linkDiscussionAction.setToolTipText(Messages
				.getString("ExcerptionView.9")); //$NON-NLS-1$
		linkDiscussionAction.setImageDescriptor(ImageDescriptor.createFromFile(
				this.getClass(), "../../../../icons/link_ico.gif")); //$NON-NLS-1$

		deleteAction = new Action() {
			public void run() {
				ITreeSelection selection = (ITreeSelection) viewer
						.getSelection();

				HashSet<OperationTable> ots = new HashSet<OperationTable>();

				for (Iterator iter = selection.iterator(); iter.hasNext();) {
					TreeObject element = (TreeObject) iter.next();

					OperationTable operationTable = null;
					if (element instanceof TreeParent) {
						// a whole file is selected
						operationTable = getOTs().get(element.getName());
						operationTable.clearMarked();
					} else {
						operationTable = getOTs().get(
								element.getParent().getName());
						operationTable.removeExcerption(element.getId());
					}
					ots.add(operationTable);
				}

				for (OperationTable ot : ots) {
					ot.refreshDisplay();
				}

			}
		};
		deleteAction.setText(Messages.getString("ExcerptionView.13")); //$NON-NLS-1$
		deleteAction.setToolTipText(Messages.getString("ExcerptionView.14")); //$NON-NLS-1$
		deleteAction.setImageDescriptor(ImageDescriptor.createFromFile(this
				.getClass(), "../../../../icons/delete.gif")); //$NON-NLS-1$
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * Updates excerptions of given Operation Table in Excerption View If given
	 * Operation Table still not monitored - adds it
	 * 
	 * @param ot -
	 *            Operation Table to update
	 */
	public void updateMonitoredEditor(OperationTable ot) {
		currentProject = ot.getProject();
		if (!projectOTs.containsKey(currentProject))
			projectOTs.put(currentProject, new OTSet());

		if (!getOTs().contains(ot))
			getOTs().add(ot);

		refresh();
	}

	/**
	 * Removes Operation Table from monitored by excerption view
	 * 
	 * @param ot -
	 *            operation table to remove
	 */
	public void removeMonitoredEditor(OperationTable ot) {
		if (!projectOTs.containsKey(ot.getProject())
				|| !projectOTs.get(ot.getProject()).contains(ot))
			return;

		projectOTs.get(ot.getProject()).remove(ot);
		refresh();
	}

	/**
	 * Returns the name of the Operation Table (name of it's source)
	 * 
	 * @param ot -
	 *            operation table to get name of
	 * @return name to show in excerption view
	 */
	public static String nameFromOT(OperationTable ot) {
		return new Source(((FileEditorInput) ot.getEditorInput()).getFile())
				.toString();
	}

	/**
	 * Returns the ExcerptionView object. If the view not shown in current
	 * perspective, function shows it
	 * 
	 * @return ExceptionView object
	 */
	public static ExcerptionView getView() {
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		if (activePage == null) {
			return null;
		}

		IViewPart view = activePage.findView(ExcerptionView.ID);

		try {
			if (view == null) {
				view = activePage.showView(ExcerptionView.ID);
			}
		} catch (PartInitException e) {
		}

		return (ExcerptionView) view;
	}

	/**
	 * Returns the project, excerption view is currently works with
	 * 
	 * @return current active project
	 */
	public IProject getProject() {
		return currentProject;
	}

	/**
	 * Shows Link Discussion wizard Shows error message if no roots availible
	 * 
	 */
	public void linkDiscussion() {
		if (!hasRoots()) {
			MessageBox mb = new MessageBox(new Shell());
			mb.setText(Messages.getString("ExcerptionView.Error")); //$NON-NLS-1$
			mb.setMessage(Messages.getString("ExcerptionView.NoMarkedRoots")); //$NON-NLS-1$
			mb.open();
			return;
		}
		WizardDialog dialog = new WizardDialog(new Shell(), new NewLinkWizard());
		dialog.setTitle(Messages.getString("ExcerptionView.7")); //$NON-NLS-1$
		dialog.open();
	}

	/**
	 * Returns whether the view has visible roots
	 * 
	 * @return true if view has roots with excerptions, and false if not
	 */
	public boolean hasRoots() {
		return !getRoots().isEmpty();
	}

	/**
	 * Returns list of roots contained in the view
	 * 
	 * @return list of strings with name of the roots
	 */
	public List<String> getRoots() {
		List<String> l = new LinkedList<String>();
		for (TreeItem i : viewer.getTree().getItems()) {
			if (new Source(ToK.getProjectToK(getProject()), i.getText())
					.isRoot())
				l.add(i.getText());
		}
		return l;
	}
}