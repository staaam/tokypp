package lost.tok.rootDiscussions;

import java.util.LinkedList;
import java.util.List;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.GeneralFunctions;
import lost.tok.ToK;
import lost.tok.disEditor.DiscussionEditor;
import lost.tok.opTable.OperationTable;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
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
public class RootDiscussions extends OperationTable implements
		IObjectActionDelegate, ITextDoubleClickStrategy, ITextHover {

	public static final String EDITOR_ID = "lost.tok.opTable.ShowLinkedDiscussions";

	class DiscussionLink {

		private String discussion;

		private String linkSubject;

		private String type;

		public DiscussionLink(Element e) {
			this(e.element("discussionFile").getText(), e.element("type")
					.getText(), e.element("linkSubject").getText());
		}

		public DiscussionLink(String discussionFile, String type,
				String linkSubject) {
			discussion = Discussion.getNameFromFile(discussionFile);
			this.type = type;
			this.linkSubject = linkSubject;
		}

		public String getDiscussionFile() {
			return discussion;
		}

		public String getLinkSubject() {
			return linkSubject;
		}

		public String getType() {
			return type;
		}

	}

	private static QualifiedName discussionLinkQName = new QualifiedName(
			"lost.tok.opTable.ShowRootDiscussions", "excerptionSource");

	private static QualifiedName textOffsetQName = new QualifiedName(
			"lost.tok.opTable.ShowRootDiscussions", "textOffset");

	List<Excerption> rootExcerptions = null;

	private ISelection selection;

	private IWorkbenchPart targetPart;

	ToK tok = null;

	public RootDiscussions() {
		super();
		setSourceViewerConfiguration(new RootDisussionsSourceViewerConfiguration(
				this));
		System.out.println("ShowLinkedDiscussions");
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
			openDiscussionLink((DiscussionLink) excerption
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
			DiscussionLink discussionLink = (DiscussionLink) excerption
					.getProperty(discussionLinkQName);
			s += "Discussion: " + discussionLink.getDiscussionFile() + "\n"
					+ "Type: " + discussionLink.getType() + "\n" + "Subject: "
					+ discussionLink.getLinkSubject() + "\n" + "\n";
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
	 * @param file -
	 *            the source(root) file
	 */
	private void getRootDiscussions(IFile file) {
		rootExcerptions = new LinkedList<Excerption>();

		ToK tok = ToK.getProjectToK(file.getProject());

		XPath xpathSelector = DocumentHelper
				.createXPath("//sublink[sourceFile='" + file.getName() + "']");

		for (Object os : xpathSelector.selectNodes(GeneralFunctions
				.readFromXML(tok.getLinkFile()))) {
			Element s = (Element) os;

			DiscussionLink discussionLink = new DiscussionLink(s.getParent());

			for (Object oe : s.elements("excerption")) {
				Excerption e = new Excerption((Element) oe);
				e.setProperty(discussionLinkQName, discussionLink);

				SourceDocument document = (SourceDocument) getSourceViewer()
						.getDocument();

				ChapterText ct = document.getChapterText(e.getSourceFilePath());
				if (ct == null) {
					System.out.println("wrong excerption found (chapter '"
							+ e.getSourceFilePath() + "' not found)");
					continue;
				}

				e.setText(ct.toString().substring(e.getStartPos(),
						e.getEndPos()));
				e
						.setProperty(textOffsetQName, ct.getOffset()
								+ e.getStartPos());

				rootExcerptions.add(e);

				markChapterExcerption(ct.getOffset() + e.getStartPos(), e
						.getEndPos()
						- e.getStartPos(), ct);
			}
		}
	}

	@Override
	protected void hookContextMenu(Control parent) {
	}

	private void openDiscussionLink(DiscussionLink discussionLink) {
		Discussion d;
		try {
			d = tok.getDiscussion(discussionLink.discussion);
		} catch (CoreException e) {
			e.printStackTrace();
			return;
		}

		try {
			IWorkbenchWindow ww = getSite().getWorkbenchWindow();
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

	@Override
	public void refreshDisplay() {
		IEditorInput input = getEditorInput();
		if (input instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = (FileEditorInput) input;
			IFile file = fileEditorInput.getFile();
			tok = ToK.getProjectToK(file.getProject());
			getRootDiscussions(file);
		}

		super.refreshDisplay();
	}

	public void run(IAction action) {
		System.out.println("run");
		if (selection instanceof StructuredSelection) {
			StructuredSelection ss = (StructuredSelection) selection;
			for (Object o : ss.toList()) {
				if (o instanceof IFile) {
					IFile file = (IFile) o;
					try {
						targetPart
								.getSite()
								.getWorkbenchWindow()
								.getActivePage()
								.openEditor(new FileEditorInput(file),
										"lost.tok.opTable.ShowLinkedDiscussions");
					} catch (PartInitException e) {
						e.printStackTrace();
					}

				}

			}
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		System.out.println("selectionChanged");
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
		System.out.println("setActivePart");
	}
}