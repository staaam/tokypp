package lost.tok.opTable;

import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.ToK;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

public class ShowLinkedDiscussions extends OperationTable implements IObjectActionDelegate {

	ToK tok = null;

	private ISelection selection;
	private IWorkbenchPart targetPart;

	public ShowLinkedDiscussions() {
		super();
		System.out.println("ShowLinkedDiscussions");
	}
	
	protected void hookContextMenu(Control parent) {
	}

	@Override
	public void refreshDisplay() {
		IEditorInput input = getEditorInput();
		if (input instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			IFile file = fileEditorInput.getFile();
			ToK tok = ToK.getProjectToK(file.getProject());
			
			XPath xpathSelector = DocumentHelper
				.createXPath("//sublink[sourceFile='"
					+ file.getProjectRelativePath() + "']");
			
			for (Object os : xpathSelector.selectNodes(GeneralFunctions.readFromXML(tok.getLinkFile()))) {
				Element s = (Element) os;
				
				Element parent = s.getParent();
				
				parent.element("discussionFile");
				parent.element("type");
				parent.element("linkSubject");
				
				for (Object oe : s.elements("excerption")) {
					Excerption e = new Excerption((Element) oe);
					
					SourceDocument document = (SourceDocument) getSourceViewer().getDocument();
					
					ChapterText ct = document.getChapterText(e.getSourceFilePath());
					if (ct == null) {
						System.out.println("wrong excerption found (chapter '" + e.getSourceFilePath() + "' not found)");
						continue;
					}
									
					e.setText(ct.toString().substring(e.getStartPos(), e.getEndPos()));
					
					markChapterExcerption(
							ct.getOffset() + e.getStartPos(),
							e.getEndPos() - e.getStartPos(),
							ct);
				}
			}
		}

		super.refreshDisplay();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
		System.out.println("setActivePart");
	}

	public void run(IAction action) {
		System.out.println("run");
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			for (Object o : ss.toList()) {
				if (o instanceof IFile) {
					IFile file = (IFile) o;
					try {
						targetPart.getSite()
							.getWorkbenchWindow()
							.getActivePage()
							.openEditor(
									new FileEditorInput(file),
									"lost.tok.opTable.ShowLinkedDiscussions");
					} catch (PartInitException e) {
						e.printStackTrace();
					}

				}
				
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		System.out.println("selectionChanged");
	}

}
