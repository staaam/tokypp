package lost.tok.excerptionsView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lost.tok.Excerption;
import lost.tok.newLinkWizard.NewLinkWizard;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.QualifiedName;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class ExcerptionView extends ViewPart {
	class FileExcerption {

		List<Excerption> excerptions = new ArrayList<Excerption>();

		String sourceFileName = new String();

		public void addExcerption(Excerption exp) {
			exp.setProperty(new QualifiedName("id", "id"), nextId++);
			excerptions.add(exp);
		}

		public List<Excerption> getExcerptions() {
			return excerptions;
		}

		public String getSourceFileName() {
			return sourceFileName;
		}

		public void setExcerptions(List<Excerption> excerptions) {
			this.excerptions = excerptions;
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
			return (TreeObject[]) children.toArray(new TreeObject[children
					.size()]);
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
				if (invisibleRoot == null)
					initialize();
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
			if (parent instanceof TreeParent)
				return ((TreeParent) parent).hasChildren();
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
			invisibleRoot = new TreeParent("");

			for (Iterator iter = objects.iterator(); iter.hasNext();) {
				FileExcerption element = (FileExcerption) iter.next();
				TreeParent parentFile = new TreeParent(element
						.getSourceFileName());
				for (Iterator iterator = element.getExcerptions().iterator(); iterator
						.hasNext();) {
					Excerption exp = (Excerption) iterator.next();
					String expText = exp.getText();
					
					String expPrefix = expText.length() < 40 ? expText: expText.substring(0, 40);
					TreeObject temp = new TreeObject(expPrefix);
					temp.setId(Integer.valueOf((Integer)exp.getProperty(new QualifiedName("id","id"))));
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
			if (obj instanceof TreeParent)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					imageKey);
		}

		public String getText(Object obj) {
			return obj.toString();
		}
	}

	public final static String ID = "lost.tok.excerptionsView.ExcerptionView";

	private static int nextId = 0;

	private Action action1;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	private Action doubleClickAction;

	private DrillDownAdapter drillDownAdapter;

	private List<FileExcerption> objects = new ArrayList<FileExcerption>();

	private TreeViewer viewer;
	
	/**
	 * The constructor.
	 */
	public ExcerptionView() {

	}

	public void addExcerptions(String sourceFileName, Excerption[] exp) {
		for (Iterator iter = objects.iterator(); iter.hasNext();) {
			FileExcerption element = (FileExcerption) iter.next();
			if (element.getSourceFileName().compareTo(sourceFileName) == 0) {
				for (int i = 0; i < exp.length; i++) {
					element.addExcerption(exp[i]);
				}
				((ViewContentProvider) viewer.getContentProvider())
						.treeBuildAndRefresh();
				return;
			}
		}
		// new source file
		FileExcerption temp = new FileExcerption();
		temp.setSourceFileName(sourceFileName);
		for (int i = 0; i < exp.length; i++) {
			temp.addExcerption(exp[i]);
		}
		objects.add(temp);
		((ViewContentProvider) viewer.getContentProvider())
				.treeBuildAndRefresh();
	}

	public List<Excerption> getExcerptions(String fileName){
		for (FileExcerption element : objects) {
			if (element.getSourceFileName().compareTo(fileName) == 0){
				return element.getExcerptions();
			}
		}
		return null;
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
		manager.add(action1);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
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
				doubleClickAction.run();
			}
		});
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				
				ITreeSelection selection = (ITreeSelection)viewer.getSelection();
				List list = selection.toList();
				List<Integer> selectedIds = new ArrayList<Integer>(); 
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					TreeObject element = (TreeObject) iter.next();
					selectedIds.add(element.getId());
				}
								
				NewLinkWizard wizard = new NewLinkWizard();
				wizard.init(PlatformUI.getWorkbench(),(IStructuredSelection) viewer.getSelection());
				WizardDialog dialog= new WizardDialog(new Shell(),wizard);
				dialog.setTitle("Link discussion to root");
				dialog.updateSize();
				dialog.create();
				dialog.open();
			}
		};
		action1.setText("Link to root");
		action1.setToolTipText("Link this excerption(s) to a root file");
		

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Excerptions View", message);
	}

	
	public Tree getTree(){
		return viewer.getTree();
	}
	
	public IContentProvider getContentProvider(){
		return viewer.getContentProvider();
	}
	public Object getInput(){
		return	viewer.getInput();
	}
	
	public IBaseLabelProvider getLabelProvider(){
		return viewer.getLabelProvider();
	}

}