package lost.tok.opTable;

import java.util.LinkedList;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.Link;
import lost.tok.Messages;
import lost.tok.Source;
import lost.tok.ToK;
import lost.tok.disEditor.DiscussionEditor;
import lost.tok.opTable.actions.AbstractEditorAction;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

/**
 * 
 * 
 * 
 * @author Michael Gelfand
 * 
 */
public class RootDiscussionsPart extends AbstractEditorAction implements
		ITextDoubleClickStrategy, ITextHover {

	private static QualifiedName discussionLinkQName = new QualifiedName(
			"lost.tok.opTable.ShowRootDiscussions", "excerptionSource"); //$NON-NLS-1$ //$NON-NLS-2$

	private static QualifiedName textOffsetQName = new QualifiedName(
			"lost.tok.opTable.ShowRootDiscussions", "textOffset"); //$NON-NLS-1$ //$NON-NLS-2$

	List<Excerption> rootExcerptions = null;

	private ISelection selection;

	private IWorkbenchPart targetPart;

	private OperationTable operationTable;

	@SuppressWarnings("unused")
	private ToK tok;

	public RootDiscussionsPart(OperationTable ot) {
		super();

		operationTable = ot;
	}

	/**
	 * 
	 * Event of Double Click
	 * 
	 * If double clicked position has no linked discussions nothing happens Else
	 * opens all linked discussions
	 * 
	 */
	public void doubleClicked(ITextViewer viewer) {
		List<Excerption> exs = getLinks(viewer.getSelectedRange().x);

		for (Excerption excerption : exs) {
			openDiscussionLink((Link) excerption
					.getProperty(discussionLinkQName));
		}
	}

	/**
	 * 
	 * Event of mouse hovering the text
	 * 
	 * If hovered area has no linked discussions nothing happens Else tooltip
	 * apears showing all linked discussions
	 * 
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		List<Excerption> exs = getLinks(hoverRegion.getOffset());
		String s = new String();
		for (Excerption excerption : exs) {
			Link discussionLink = (Link) excerption.getProperty(discussionLinkQName);
			s += Messages.getString("RootDiscussionsPart.disc") + discussionLink.getLinkedDiscussion().getDiscName() + "\n" //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("RootDiscussionsPart.type") + discussionLink.getDisplayLinkType() + "\n" + Messages.getString("RootDiscussionsPart.subj") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ discussionLink.getSubject() + "\n" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return s.trim();
	}

	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return new Region(offset, 0);
	}

	/**
	 * 
	 * @param position -
	 *            position in text
	 * @return All the excerptions that includes given position
	 */
	private List<Excerption> getLinks(int position) {
		List<Excerption> exs = new LinkedList<Excerption>();
		for (Excerption excerption : rootExcerptions) {
			int excerptionOffset = (Integer) excerption
					.getProperty(textOffsetQName);
			int excerptionLength = excerption.getEndPos()
					- excerption.getStartPos();
			if (excerptionOffset <= position
					&& position <= excerptionOffset + excerptionLength) {
				exs.add(excerption);
			}
		}
		return exs;
	}

	/**
	 * 
	 * Creates all required data structures
	 * 
	 * @param root -
	 *            the source(root) file
	 */
	private void getRootDiscussions(Source root) {
		rootExcerptions = new LinkedList<Excerption>();

		ToK tok = ToK.getProjectToK(root.getFile().getProject());

		XPath xpathSelector = DocumentHelper
				.createXPath("//sublink[sourceFile='" + root.toString() + "']"); //$NON-NLS-1$ //$NON-NLS-2$

		for (Object os : xpathSelector.selectNodes(GeneralFunctions
				.readFromXML(tok.getLinkFile()))) {
			Element sublinkElm = (Element)os;
			Element linkElm = sublinkElm.getParent();
			
			Link discussionLink = null;
			try {
				String discFileName = ToK.DISCUSSION_FOLDER + "/" + linkElm.element("discussionFile").getText();
				String discRealName = Discussion.getNameFromFile(discFileName); // real name == discussion title
				Discussion disc = tok.getDiscussion(discRealName);
				String linkXMLType = linkElm.element("type").getText();
				String linkSubject = linkElm.element("linkSubject").getText();

				discussionLink = new Link(disc, linkXMLType, tok.getLinkFile(), linkSubject);
			} catch (CoreException e1) {
				e1.printStackTrace();
				continue;
			}

			for (Object oe : sublinkElm.elements("excerption")) { //$NON-NLS-1$
				Excerption e = new Excerption((Element) oe);
				e.setProperty(discussionLinkQName, discussionLink);

				SourceDocument document = operationTable.getDocument();

				ChapterText ct = document.getChapterTextFromXPath(e.getXPath());
				if (ct == null) {
					System.out.println("wrong excerption found (chapter '" //$NON-NLS-1$
							+ e.getXPath() + "' not found)"); //$NON-NLS-1$
					continue;
				}

				e.setText(ct.toString().substring(e.getStartPos(), e.getEndPos()));
				e.setProperty(textOffsetQName, ct.getOffset() + e.getStartPos());

				rootExcerptions.add(e);

				operationTable.markChapterExcerption(
						ct.getOffset() + e.getStartPos(),
						e.getEndPos() - e.getStartPos(), 
						ct, StyleManager.getLinkStyle(discussionLink.getLinkType()));
			}
		}
	}

	private void openDiscussionLink(Link discussionLink) {
		Discussion d = discussionLink.getLinkedDiscussion();

		try {
			IWorkbenchWindow ww = operationTable.getSite().getWorkbenchWindow();
			// String editorId = ww.getWorkbench().getEditorRegistry()
			// .getDefaultEditor(d.getFile().getName()).getId();
			String editorId = DiscussionEditor.EDITOR_ID;

			ww.getActivePage().openEditor(new FileEditorInput(d.getFile()),
					editorId);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void refreshDisplay() {
		IEditorInput input = operationTable.getEditorInput();
		if (input instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			IFile file = fileEditorInput.getFile();
			tok = ToK.getProjectToK(file.getProject());
			getRootDiscussions(new Source(file));
		}
	}

	public void run(IAction action) {
		System.out.println("run"); //$NON-NLS-1$
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			for (Object o : ss.toList()) {
				if (o instanceof IFile) {
					IFile file = (IFile) o;
					try {
						targetPart.getSite().getWorkbenchWindow()
								.getActivePage().openEditor(
										new FileEditorInput(file),
										OperationTable.EDITOR_ID);
					} catch (PartInitException e) {
						e.printStackTrace();
					}

				}

			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}
}
