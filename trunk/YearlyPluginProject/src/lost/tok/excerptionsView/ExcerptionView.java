package lost.tok.excerptionsView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import lost.tok.Excerption;
import lost.tok.Messages;
import lost.tok.newDiscussionWizard.NewDiscussionWizard;
import lost.tok.newLinkWizard.NewLinkWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.Action;
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
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
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
	class FileExcerption {

		List<Excerption> excerptions = new ArrayList<Excerption>();

		IFile sourcefile;

		String sourceFileName = new String();

		public void addExcerption(Excerption exp) {
			exp.setProperty(new QualifiedName("id", "id"), nextId++); //$NON-NLS-1$ //$NON-NLS-2$
			excerptions.add(exp);
		}

		public List<Excerption> getExcerptions() {
			return excerptions;
		}

		public IFile getSourcefile() {
			return sourcefile;
		}

		public String getSourceFileName() {
			return sourceFileName;
		}

		public void setExcerptions(List<Excerption> excerptions) {
			this.excerptions = excerptions;
		}

		public void setSourcefile(IFile sourcefile) {
			this.sourcefile = sourcefile;
		}

		public void setSourceFileName(String sourceFileName) {
			this.sourceFileName = sourceFileName;
		}

	}

	class NameSorter extends ViewerSorter {
	}

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

			for (Object element0 : objects) {
				FileExcerption element = (FileExcerption) element0;
				TreeParent parentFile = new TreeParent(element
						.getSourceFileName());
				for (Object element1 : element.getExcerptions()) {
					Excerption exp = (Excerption) element1;
					String expText = exp.getText();

					String expPrefix = expText.length() < 40 ? expText
							: expText.substring(0, 40);
					TreeObject temp = new TreeObject(expPrefix);
					temp.setId(Integer.valueOf((Integer) exp
							.getProperty(new QualifiedName("id", "id")))); //$NON-NLS-1$ //$NON-NLS-2$
					parentFile.addChild(temp);
				}
				invisibleRoot.addChild(parentFile);
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

	private static int nextId = 0;

	private Action deleteAction;

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

	private Action linkExistingAction;

	private Action linkNewAction;

	private List<FileExcerption> objects = new ArrayList<FileExcerption>();

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
	public void addExcerptions(String sourceFileName, Excerption[] exp,
			IFile file) {
		for (Object element0 : objects) {
			FileExcerption element = (FileExcerption) element0;
			if (element.getSourceFileName().compareTo(sourceFileName) == 0) {
				for (Excerption element1 : exp) {
					element.addExcerption(element1);
				}
				((ViewContentProvider) viewer.getContentProvider())
						.treeBuildAndRefresh();
				return;
			}
		}
		// new source file
		FileExcerption temp = new FileExcerption();
		temp.setSourceFileName(sourceFileName);
		temp.setSourcefile(file);
		for (Excerption element : exp) {
			temp.addExcerption(element);
		}
		objects.add(temp);
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
		viewer.setSorter(new NameSorter());
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
	public List<Excerption> getExcerptions(String fileName) {
		for (FileExcerption element : objects) {
			if (element.getSourceFileName().compareTo(fileName) == 0) {
				return element.getExcerptions();
			}
		}
		return null;
	}

	public Object getInput() {
		return viewer.getInput();
	}

	public IBaseLabelProvider getLabelProvider() {
		return viewer.getLabelProvider();
	}

	private IFile getProject() {
		ITreeSelection selection = (ITreeSelection) viewer.getSelection();
		List list = selection.toList();
		IFile file = null;
		String fileName = ((TreeObject) list.get(0)).getParent().getName();

		try {
			for (FileExcerption object : objects) {
				if (object.getSourceFileName().compareTo(fileName) == 0) {
					file = object.getSourcefile();
					break;
				}
			}
		} catch (ConcurrentModificationException e) {
		}
		return file;
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

				IFile file = getProject();

				IProject project = file.getProject();

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
				wizard.setProjectName(project.getName());
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

				IFile file = getProject();

				IProject project = file.getProject();

				NewDiscussionWizard discWizard = new NewDiscussionWizard();
				discWizard.init(PlatformUI.getWorkbench(),
						(IStructuredSelection) viewer.getSelection());
				discWizard.setProject(project);
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
				linkWizard.setProjectName(project.getName());
				linkDialog.open();
			}
		};
		linkNewAction.setText(Messages.getString("ExcerptionView.5")); //$NON-NLS-1$
		linkNewAction.setToolTipText(Messages.getString("ExcerptionView.4")); //$NON-NLS-1$
		linkNewAction.setImageDescriptor(ImageDescriptor.createFromFile(this
				.getClass(), "../../../../icons/link_new_ico.gif")); //$NON-NLS-1$

		deleteAction = new Action() {
			public void run() {

				ITreeSelection selection = (ITreeSelection) viewer
						.getSelection();
				List list = selection.toList();
				List<Integer> selectedIds = new ArrayList<Integer>();
				List<String> selectedFiles = new ArrayList<String>();
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					TreeObject element = (TreeObject) iter.next();
					if (element instanceof TreeParent) {
						// a whole file is selected
						selectedFiles.add(element.getName());
					}
					selectedIds.add(element.getId());
				}

				for (Object element : selectedFiles) {
					String fileName = (String) element;
					try {
						for (FileExcerption object : objects) {
							if (object.getSourceFileName().compareTo(fileName) == 0) {
								objects.remove(object);
							}
						}
					} catch (ConcurrentModificationException e) {
					}
				}

				for (Object element0 : selectedIds) {
					Integer element = (Integer) element0;
					for (FileExcerption object : objects) {
						List<Excerption> excerptions = object.getExcerptions();
						try {
							for (Excerption exp : excerptions) {
								int id = Integer.valueOf((Integer) exp
										.getProperty(new QualifiedName(
												"id", "id"))); //$NON-NLS-1$ //$NON-NLS-2$
								if (element.compareTo(id) == 0) {
									excerptions.remove(exp);
								}
							}
						} catch (ConcurrentModificationException e) {
						}
					}
				}
				((ViewContentProvider) viewer.getContentProvider())
						.treeBuildAndRefresh();
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

}