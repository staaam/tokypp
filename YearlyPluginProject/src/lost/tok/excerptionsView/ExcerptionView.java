package lost.tok.excerptionsView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lost.tok.Excerption;
import lost.tok.Messages;
import lost.tok.opTable.OperationTable;
import lost.tok.opTable.wizards.NewLinkWizard;
import lost.tok.wizards.NewDiscussion;

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
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

/**
 * 
 * The Excerption View collects all the excerptions created by the Linkage
 * Editor It provides a visual feedback to the user of the current excerptions
 * 
 */
public class ExcerptionView extends ViewPart {
	//private List<FileExcerption> objects = new ArrayList<FileExcerption>();
//	private Hashtable<IFile, List<Excerption> > 
//		fileToExcerption = new Hashtable<IFile, List<Excerption>>();
	
//	private Hashtable<String, List<Excerption>>
//		nameToExcerption = new Hashtable<String, List<Excerption>>();
//	
//	private Hashtable<String, IProject>
//		nameToProject = new Hashtable<String, IProject>();
	
	class OTSet extends HashSet<OperationTable> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8498357334519966448L;
		
		private Hashtable<String, OperationTable> nameToOT = new Hashtable<String, OperationTable>();
		
		@Override
		public boolean add(OperationTable o) {
			if (!super.add(o)) return false;
			nameToOT.put(ExcerptionView.nameFromOT(o), o);
			return true;
		}

		@Override
		public boolean remove(Object o) {
			if (!super.remove(o)) return false;
			nameToOT.remove(ExcerptionView.nameFromOT((OperationTable)o));
			return true;
		}
		
		public boolean remove(String name) {
			return remove(get(name));
		}

		public Set<String> getNames() {
			return nameToOT.keySet();
		}
		
		public OperationTable get(String name) {
			if (!nameToOT.containsKey(name)) return null;
			return nameToOT.get(name);
		}
	}

	private Hashtable<IProject, OTSet> projectOTs = new Hashtable<IProject, OTSet>();
	
	private IProject currentProject = null;
	
//	class NameSorter extends ViewerSorter {
//	}

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

			if (currentProject != null && projectOTs.containsKey(currentProject)) {
				for (OperationTable ot : getOTs()) {
					TreeParent parentFile = new TreeParent(ot.getTitle());
					for (Integer i : ot.getExcerptions().keySet()) {
						Excerption exp = ot.getExcerptions().get(i);
						String expText = exp.getText();
	
	//					String expPrefix = expText.length() < 40 ? expText
	//							: expText.substring(0, 40);
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
	@SuppressWarnings("unused")
	private DrillDownAdapter drillDownAdapter;

	private IAction linkExistingAction;

	private IAction linkNewAction;

	private TreeViewer viewer;

	/**
	 * The constructor.
	 */
	public ExcerptionView() {

	}

	/**
	 * Adds a list of excerptions from one rrot file to the view
	 * 
	 * @param sourceFileName
	 *            the root file name
	 * @param exp
	 *            excerptions
	 * @param file
	 *            Resource representing the file
	 */
//	public void addExcerptions(String filename, List<Excerption> exp) {
//		if (!nameToExcerption.containsKey(filename)) {
//			nameToExcerption.put(filename, new LinkedList<Excerption>());
//		}
//		nameToExcerption.get(filename).addAll(exp);
//
//		refresh();
//	}
	
	public void clear() {
		refresh();
	}

	private void refresh() {
		((ViewContentProvider) viewer.getContentProvider()).treeBuildAndRefresh();
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
		//viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(linkNewAction);
		manager.add(linkExistingAction);
		manager.add(deleteAction);
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(linkNewAction);
		manager.add(linkExistingAction);
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

	public Tree getTree() {
		return viewer.getTree();
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

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				linkExistingAction.run();
			}
		});
	}

	private void makeActions() {
		linkExistingAction = new Action() {
			public void run() {

				ITreeSelection selection = (ITreeSelection) viewer
						.getSelection();
				List list = selection.toList();
				List<Integer> selectedIds = new ArrayList<Integer>();
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					TreeObject element = (TreeObject) iter.next();
					selectedIds.add(element.getId());
				}

				NewLinkWizard wizard = new NewLinkWizard();
				wizard.init(PlatformUI.getWorkbench(),
						(IStructuredSelection) viewer.getSelection());
				WizardDialog dialog = new WizardDialog(new Shell(), wizard);
				dialog.setTitle(Messages.getString("ExcerptionView.7")); //$NON-NLS-1$
				dialog.updateSize();
				dialog.create();
				dialog.open();
			}
		};
		linkExistingAction.setText(Messages.getString("ExcerptionView.8")); //$NON-NLS-1$
		linkExistingAction.setToolTipText(Messages
				.getString("ExcerptionView.9")); //$NON-NLS-1$
		linkExistingAction.setImageDescriptor(ImageDescriptor.createFromFile(
				this.getClass(), "../../../../icons/link_ico.gif")); //$NON-NLS-1$

		linkNewAction = new Action() {
			public void run() {
				NewDiscussion discWizard = new NewDiscussion();
				discWizard.init(PlatformUI.getWorkbench(),
						(IStructuredSelection) viewer.getSelection());
				discWizard.setProject(currentProject);
				Shell shell = new Shell();
				WizardDialog discDialog = new WizardDialog(shell, discWizard);
				discDialog.setTitle(Messages.getString("ExcerptionView.6")); //$NON-NLS-1$
				discDialog.updateSize();
				discDialog.create();
				discDialog.open();

				NewLinkWizard linkWizard = new NewLinkWizard();
				linkWizard.init(PlatformUI.getWorkbench(),
						(IStructuredSelection) viewer.getSelection());
				WizardDialog linkDialog = new WizardDialog(shell, linkWizard);
				linkDialog.setTitle(Messages.getString("ExcerptionView.7")); //$NON-NLS-1$
				linkDialog.updateSize();
				linkDialog.create();
				linkDialog.open();
			}
		};
		linkNewAction.setText(Messages.getString("ExcerptionView.5")); //$NON-NLS-1$
		linkNewAction.setToolTipText(Messages.getString("ExcerptionView.4")); //$NON-NLS-1$
		linkNewAction.setImageDescriptor(ImageDescriptor.createFromFile(this
				.getClass(), "../../../../icons/link_new_ico.gif")); //$NON-NLS-1$

		deleteAction = new Action() {
			public void run() {
				ITreeSelection selection = (ITreeSelection) viewer.getSelection();
				//List list = selection.toList();
				//List<Integer> selectedIds = new ArrayList<Integer>();
				//List<String> selectedFiles = new ArrayList<String>();
				
				HashSet<OperationTable> ots = new HashSet<OperationTable>();
				
				for (Iterator iter = selection.iterator(); iter.hasNext();) {
					TreeObject element = (TreeObject) iter.next();
					
					OperationTable operationTable = null;
					if (element instanceof TreeParent) {
						// a whole file is selected
						operationTable = getOTs().get(element.getName());
						operationTable.clearMarked();
					} else {
						operationTable = getOTs().get(element.getParent().getName());
						operationTable.removeExcerption(element.getId());
					}
					ots.add(operationTable);
				}
				
				for (OperationTable ot : ots) {
					ot.refreshDisplay();
				}

//				for (String filename : selectedFiles) {
//					nameToExcerption.remove(filename);
//				}
//
//				for (Integer id : selectedIds) {
//					for (String filename : nameToExcerption.keySet()) {
//						List<Excerption> excerptions = nameToExcerption.get(filename);
//						for (Iterator<Excerption> iter = excerptions.iterator(); iter.hasNext();) {
//							if (((Integer) iter.next().getProperty(idQName)).compareTo(id) != 0)
//								continue;
//
//							iter.remove();
//							break;
//						}
//					}
//				}
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
	
	public void updateMonitoredEditor(OperationTable ot) {
		currentProject = ot.getProject();
		if (!projectOTs.containsKey(currentProject))
			projectOTs.put(currentProject, new OTSet());
				
		if (!getOTs().contains(ot))
			getOTs().add(ot);
		
		refresh();
	}

	public static String nameFromOT(OperationTable ot) {
		return ot.getTitle();
	}
//	public void setExcerptions(String fileName, List<Excerption> exps, IProject project) {
//		nameToExcerption.put(fileName, exps);
//		nameToProject.put(fileName, project);
//		for (Excerption exp : exps) {
//			exp.setProperty(idQName, nextId++);
//		}
//		refresh();
//	}

	/**
	 * Returns the ExcerptionView object. If the view not shown
	 * in current perspective, function shows it
	 *  
	 * @return ExceptionView object
	 */
	public static ExcerptionView getView() {
		IWorkbenchPage activePage = 
			PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage();
		
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
		
		return (ExcerptionView)view;
	}

	public IStructuredSelection getRoots() {
		return (IStructuredSelection) viewer.getSelection();
	}

	public IProject getProject() {
		return currentProject;
	}
}