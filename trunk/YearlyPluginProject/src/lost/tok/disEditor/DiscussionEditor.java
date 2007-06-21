package lost.tok.disEditor;

import java.util.LinkedList;
import java.util.TreeMap;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.ToK;
import lost.tok.opTable.OperationTable;
import lost.tok.sourceDocument.ChapterText;
import lost.tok.sourceDocument.SourceDocument;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.StorageDocumentProvider;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

public class DiscussionEditor extends TextEditor {
	
	/******* M E M B E R S *******************************/
	
	public static final String EDITOR_ID = "lost.tok.disEditor.DiscussionEditor"; //$NON-NLS-1$

	private static final String DISCUSSION = "discussion";

	private static final String QUOTE = "Quote";

	private static final String OPINION = "Opinion";

	private static final String COMMENT_LINE = "Comment line";

	private Discussion discussion = null;

	private TreeItem rootItem = null;

	private Composite editor = null;

	private int ctrlCurrentWidth = 0;

	private long localModificationStamp;
	
	
	//******* C ' T O R *************************************
	
	public DiscussionEditor() {
		super();
	}

	
	//******* CREATOR OF THE TREE EDITOR *****************************
	
	public void createPartControl(Composite parent) {
		final Composite par = parent;
		editor = par;
		final Tree disTree = new Tree(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER);

		// ***********************************************************
		// ************************ DELETE QUOTES AND OPINIONS *******
		// ***********************************************************
		disTree.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					// deletion of discussion is not allowed
					if (disTree.getSelection()[0].getData().equals(DISCUSSION))
						return;
					
					// deletion of default opinion is NOT allowed
					if (disTree.getSelection()[0].getData().equals(OPINION) && 
							discussion.isDefaultOpinion(disTree.getSelection()[0].getText())) 
					{
						return;
					}

					// deleting an opinion
					if (disTree.getSelection()[0].getData().equals(OPINION)) {
						TreeItem defOp = null;
						TreeItem itmArr[] = disTree.getItem(0).getItems();

						// fetch default opinion
						for (TreeItem element : itmArr) {
							if (discussion.isDefaultOpinion(element.getText()) )
							{
								defOp = element;
							}
						}

						// if no default opinion create one
						if (defOp == null) {
							System.out.println("Shay: Shouldn't be here");
							defOp = new TreeItem(disTree.getItem(0), SWT.NONE);
							defOp.setText(Discussion.DEFAULT_OPINION_DISPLAY);
							defOp.setData(OPINION);
						}

						TreeItem itemArr[] = disTree.getSelection()[0]
								.getItems();

						for (int i = 0; i < itemArr.length; i++) {
							TreeItem tempItem = new TreeItem(defOp, SWT.NONE);
							setTreeQuote((Quote) itemArr[i].getData(QUOTE),
									tempItem);
							moveQuoteToDefault(itemArr[i]);

							itemArr[i].dispose();
							itemArr[i] = null;
						}
						removeOpinionFromFile(disTree.getSelection()[0]);
						disTree.getSelection()[0].dispose();
						disTree.getSelection()[0] = null;

						return;
					}
					removeQuoteFromFile(disTree.getSelection()[0]);
					disTree.getSelection()[0].dispose();
					disTree.getSelection()[0] = null;
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		// ***********************************************************
		// ************************ DOUBLE CLICK ON QUOTE *******
		// ***********************************************************
		disTree.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
				if (e.widget == null) {
					return;
				}

				Tree tree = (Tree) e.widget;
				TreeItem treeItem = tree.getSelection()[0];
				if (treeItem.getData(QUOTE) == null) {
					return;
				}

				// a double on a quote will lead to:
				// 1. "jumping" to the quote origin document
				// 2. highlighting the quote
				// 3. scrolling the editor to the begining of the quote
				if (treeItem.getData(QUOTE) instanceof Quote) {
					Quote quote = (Quote) treeItem.getData(QUOTE);

					IWorkbenchWindow ww = getSite().getWorkbenchWindow();
					String editorId = OperationTable.EDITOR_ID;

					// oppening the source document
					IFile source = quote.getSource().getFile();
					IEditorPart editorP = null;
					try {
						editorP = ww.getActivePage().openEditor(
								new FileEditorInput(source), editorId);
					} catch (PartInitException e1) {
						e1.printStackTrace();
					}

					// Marking the text in the editor
					OperationTable ot = (OperationTable) editorP;
					ot.clearMarked();

					// Creating a SourceDocument from source
					SourceDocument srcDoc = new SourceDocument();
					srcDoc.set(quote.getSource());

					boolean firstExcerp = true;
					int qBegining = 0, qLength = 0;
					for (Excerption ex : quote.getExcerptions()) {
						// getting the chapter in which the text appears
						ChapterText ct = srcDoc.getChapterTextFromXPath(ex
								.getXPath());
						// adding the offset of the chpater (in the whole doc)
						// to the offset of the excerption
						int exBegin = ct.getOffset() + ex.getStartPos();
						int exLength = ex.getEndPos() - ex.getStartPos();
						TextSelection ts = new TextSelection(exBegin, exLength);
						ot.mark(ts);

						// setting the begining and length of the quote
						if (firstExcerp) {
							qBegining = exBegin;
							qLength = exLength;
							firstExcerp = false;
						}
					}
					ot.refreshDisplay();

					// scrolling the editor to the position of the quote
					ot.resetHighlightRange();
					ot.setHighlightRange(qBegining, qLength, true);
				}

			}

			public void mouseDown(MouseEvent e) {
				// nothing
			}

			public void mouseUp(MouseEvent e) {
				// nothing
			}

		});

		// *************************************************
		// ***************** DRAG AND DROP *****************
		// *************************************************
		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final DragSource source = new DragSource(disTree, operations);
		source.setTransfer(types);

		final TreeItem[] dragSourceItem = new TreeItem[1];

		source.addDragListener(new DragSourceListener() {
			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE) {
					// RemoveQuoteFromOpinion(dragSourceItem[0]);
					dragSourceItem[0].dispose();
				}
				dragSourceItem[0] = null;
			};

			public void dragSetData(DragSourceEvent event) {
				event.data = dragSourceItem[0].getText();
			}

			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = disTree.getSelection();
				if (selection.length > 0
						&& ((String) selection[0].getData()).equals(QUOTE)) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			}
		});

		DropTarget target = new DropTarget(disTree, operations);
		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					Point pt = par.getDisplay().map(null, disTree, event.x,
							event.y);
					Rectangle bounds = item.getBounds();
					if (pt.y < bounds.y + bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
					} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_AFTER;
					} else {
						event.feedback |= DND.FEEDBACK_SELECT;
					}
				}
			}

			public void drop(DropTargetEvent event) {
				if (((TreeItem) event.item).getData().equals(QUOTE)
						|| ((TreeItem) event.item).getData().equals(DISCUSSION)) {
					event.detail = DND.DROP_NONE;
					return;
				}
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				Quote quote = (Quote) dragSourceItem[0].getData(QUOTE);
				if (event.item == null) {
					TreeItem item = new TreeItem(disTree, SWT.NONE);
					item.setText("basa"); //$NON-NLS-1$
				} else {
					TreeItem item = (TreeItem) event.item;
					Point pt = par.getDisplay().map(null, disTree, event.x,
							event.y);
					Rectangle bounds = item.getBounds();
					TreeItem parent = item.getParentItem();
					if (parent != null) {
						TreeItem[] items = parent.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							if (((String) parent.getData()).equals(OPINION)) {
								TreeItem newItem = new TreeItem(parent,
										SWT.NONE, index);
								setTreeQuote(quote, newItem);
								moveQuoteToOpinion(newItem);
								return;
							}
							event.detail = DND.DROP_NONE;
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							if (((String) parent.getData()).equals(OPINION)) {
								TreeItem newItem = new TreeItem(parent,
										SWT.NONE, index + 1);
								setTreeQuote(quote, newItem);
								moveQuoteToOpinion(newItem);
								return;
							}
							event.detail = DND.DROP_NONE;
						} else {
							if (((String) item.getData()).equals(OPINION)) {
								TreeItem newItem = new TreeItem(item, SWT.NONE);
								setTreeQuote(quote, newItem);
								moveQuoteToOpinion(newItem);
								return;
							}
							event.detail = DND.DROP_NONE;
						}
					} else {
						TreeItem[] items = disTree.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							if (((String) disTree.getData()).equals(OPINION)) {
								TreeItem newItem = new TreeItem(disTree,
										SWT.NONE, index);
								setTreeQuote(quote, newItem);
								moveQuoteToOpinion(newItem);
								return;
							}
							event.detail = DND.DROP_NONE;
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							if (((String) disTree.getData()).equals(OPINION)) {
								TreeItem newItem = new TreeItem(disTree,
										SWT.NONE, index + 1);
								setTreeQuote(quote, newItem);
								moveQuoteToOpinion(newItem);
								return;
							}
							event.detail = DND.DROP_NONE;
						} else {
							if (((String) item.getData()).equals(OPINION)) {
								TreeItem newItem = new TreeItem(item, SWT.NONE);
								setTreeQuote(quote, newItem);
								moveQuoteToOpinion(newItem);
								return;
							}
							event.detail = DND.DROP_NONE;
						}
					}
				}
			}
		});

		// ***********************************************************************
		// *************************** ASSIGN TREE EDITOR  *********************
		// **********************************************************************
		rootItem = new TreeItem(disTree, SWT.MULTI | SWT.WRAP);

		rootItem.setText(discussion.getDiscName() + " (" + "Creator: " + discussion.getCreatorName() + ")");
		rootItem.setData(DISCUSSION);

		parent.getChildren()[0].addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (ctrlCurrentWidth != par.getSize().x) {
					ctrlCurrentWidth = par.getSize().x;
					rootItem.removeAll();

					for (Opinion opinion : discussion.getOpinions()) {
						TreeItem opinionItem = addTreeOpinion(rootItem, opinion);

						for (Quote quote : discussion.getQuotes(opinion
								.getName())) {
							addTreeQuote(opinionItem, quote);
						}
					}

					rootItem.setExpanded(true);
				}
			}

		});


		// expends the root and it's opinions, but not the quotes
		expendToDepth(rootItem, 3);
	}

	
	
//	****** P R I V A T E  F U N C T I O N S *****************************************
	
	/**
	 * Expends the tree till it dies
	 * 
	 * This function will expend the Item and its children, up to depthLeft
	 * levels
	 * 
	 * @param treeItem
	 *            the item to expend
	 * @param depthLeft
	 *            the number of levels to expend (1 means only this level)
	 */
	private void expendToDepth(TreeItem treeItem, int depthLeft) {
		if (depthLeft <= 0)
			return;
		treeItem.setExpanded(true);
		for (TreeItem child : treeItem.getItems())
			expendToDepth(child, depthLeft - 1);
	}
	
//	add opinion to tree
	private TreeItem addTreeOpinion(TreeItem treeItem, Opinion opinion) {
		TreeItem opinionItem = new TreeItem(treeItem, SWT.MULTI | SWT.WRAP);
		// Image imageOpin = new Image(null, new
		// FileInputStream("C:/opinion.gif"));

		setTreeOpinion(opinion, opinionItem);
		return opinionItem;
	}

//	add quote to opinion
	private TreeItem addTreeQuote(TreeItem treeItem, Quote quote) {
		TreeItem quoteItem = new TreeItem(treeItem, SWT.MULTI | SWT.WRAP);
		// Image imageQuote = new Image(null, new
		// FileInputStream("C:/quote.gif"));

		setTreeQuote(quote, quoteItem);
		return quoteItem;
	}

//	return opinion from tree item
	private Opinion getOpinion(TreeItem opinionToRemove) {
		Opinion opinion = (Opinion) opinionToRemove.getData(OPINION);
		return opinion;
	}

	private Quote getQuote(TreeItem itemToMove) {
		Quote quote = (Quote) itemToMove.getData(QUOTE);
		return quote;
	}

//	set opinion properties
	private void setTreeOpinion(Opinion opinion, TreeItem opinionItem) {
		opinionItem.setText(opinion.getName());
		opinionItem.setData(OPINION, opinion);
		opinionItem.setData(OPINION);
		// opinionItem.setImage(imageOpin);
	}

//	set quote properties
	private void setTreeQuote(Quote quote, TreeItem quoteItem) {

		String quoteText = quote.getText();
		quoteItem.setText(quoteText.substring(0, java.lang.Math.min(40,
				quoteText.length())) + "..."); //$NON-NLS-1$

		quoteItem.setData(QUOTE, quote);
		quoteItem.setData(QUOTE);
		// quoteItem.setImage(imageQuote);

		ctrlCurrentWidth = editor.getSize().x;

		int editorWidth = editor.getSize().x;
		int lineSize = editorWidth / 10;

		if (lineSize == 0) {
			lineSize = 100;
		}

		int wrappedLines = addSplitted(quoteItem, quoteText, lineSize);
		
		if (quote.getComment().trim().length() != 0) {
			// make son saparator
			String saparator = ""; //$NON-NLS-1$
			for (int i = 0; i < lineSize; i++)
				saparator += "-"; //$NON-NLS-1$

			new TreeItem(quoteItem, SWT.WRAP).setText(saparator);
			wrappedLines++;
			quoteItem.setData(COMMENT_LINE, wrappedLines);
			addSplitted(quoteItem, quote.getComment(), lineSize);

			new TreeItem(quoteItem, SWT.WRAP).setText(saparator);
		}
	}


	private int addSplitted(TreeItem quoteItem, String quoteText, int lineSize) {
		LinkedList<String> splitWrap = splitWrap(quoteText, lineSize);
		for (String s : splitWrap)
			new TreeItem(quoteItem, SWT.WRAP).setText(s);
		
		return splitWrap.size();
	}


	private LinkedList<String> splitWrap(String quoteText, int lineSize) {
		LinkedList<String> ss = new LinkedList<String>();
		while (true) {
			if (quoteText.length() <= lineSize) {
				ss.add(quoteText);
				break;
			}
			String s = quoteText.substring(0, lineSize);
			int upto = s.lastIndexOf(' ');
			ss.add(quoteText.substring(0, upto));
			quoteText = quoteText.substring(upto + 1);
		}
		return ss;
	}

	/**
	 * Synchronizes the opinions with a modified discussion file
	 * 
	 * Assumption: The discussion member is pointing to the new discussion and
	 * doesn't change
	 */
	private void synchronizeOpinions() {
		TreeMap<Integer, Opinion> existingOpinions = new TreeMap<Integer, Opinion>();

		// find all the opinions existing in the discussion (not the tree)
		for (Opinion opinion : discussion.getOpinions()) {
			int id = opinion.getId();
			existingOpinions.put(id, opinion);
		}

		// for each opinion in the tree, find its status (belongs, doesn't
		// belong)
		for (TreeItem opinionItem : rootItem.getItems()) {
			int id = getOpinion(opinionItem).getId();
			if (!existingOpinions.containsKey(id)) {
				// remove the opinion from the view
				opinionItem.dispose();
			} else {
				// mark that the opinion is already in the view
				existingOpinions.remove(id);
			}
		}

		// add the opinions which weren't found in the tree
		// new opinions are expended by default
		for (Integer opinionId : existingOpinions.keySet()) {
			TreeItem opItem = addTreeOpinion(rootItem, existingOpinions
					.get(opinionId));
			opItem.setExpanded(true);
		}
	}

	/**
	 * Synchronizes the opinions with a modified discussion file
	 * 
	 * Assumption: The discussion member is pointing to the new discussion and
	 * doesn't change. The opinions are already synchronized
	 * 
	 */
	private void synchronizeQuotes() {
		//TODO: order quotes display by authors ranking, don't change discussion XML
		
		TreeMap<Integer, TreeItem> treeOpinions = new TreeMap<Integer, TreeItem>();

		// map the opinions in the tree: id->treeItem
		for (TreeItem opinionItem : rootItem.getItems()) {
			int id = getOpinion(opinionItem).getId();
			treeOpinions.put(id, opinionItem);
		}

		// for each opinion, update its quotes
		for (Opinion opin : discussion.getOpinions()) {
			int opinionId = opin.getId();

			TreeMap<Integer, Quote> existingQuotes = new TreeMap<Integer, Quote>();

			// find all the quotes existing in the opinion (not the tree)
			for (Quote quote : discussion.getQuotes(opin.getName())) {
				int id = quote.getID();
				existingQuotes.put(id, quote);
			}

			TreeItem opItem = treeOpinions.get(opinionId);

			// for each quote in the tree, find its status (belongs, doesn't
			// belong)
			for (TreeItem quoteItem : opItem.getItems()) {
				int id = getQuote(quoteItem).getID();
				if (!existingQuotes.containsKey(id)) {
					// remove the opinion from the view
					quoteItem.dispose();
				} else {
					// mark that the opinion is already in the view
					existingQuotes.remove(id);
				}
			}

			// add the quotes which weren't found in the tree
			// and expend them and their opinions
			for (Integer quoteId : existingQuotes.keySet()) {
				TreeItem qItem = addTreeQuote(opItem, existingQuotes
						.get(quoteId));
				opItem.setExpanded(true);
				qItem.setExpanded(true);
			}
		}
	}
	
	
//	****** P U B L I C  F U N C T I O N S *****************************************
	
//	return the discussion
	public Discussion getDiscussion() {
		return discussion;
	}

	/**
	 * Returns all the displayed opinions
	 */
	public Opinion[] getDisplayedOpinions() {
		Opinion[] retVal = new Opinion[rootItem.getItemCount()];

		int i = 0;
		for (TreeItem opItem : rootItem.getItems()) {
			retVal[i] = getOpinion(opItem);
			i++;
		}
		return retVal;
	}

	/**
	 * Returns the quotes displayed in the tree Written for testing
	 * 
	 * @param opinionId
	 *            the opinion whose quotes are to be returned
	 * @return the quotes which are displayed under the given opinion id
	 */
	public Quote[] getDisplayedQuotes(Integer opinionId) {
		TreeItem[] opItems = rootItem.getItems();
		int i = 0;

		// move i to the opinion we are looking for
		while (i < opItems.length
				&& getOpinion(opItems[i]).getId() != opinionId) {
			i++;
		}

		if (i == opItems.length)
			return null; // dude, your opinion id doesn't even exist!
		// else

		TreeItem[] qItems = rootItem.getItem(i).getItems();
		Quote[] quotes = new Quote[qItems.length];

		for (int j = 0; j < quotes.length; j++) {
			quotes[j] = getQuote(qItems[j]);
		}

		return quotes;
	}

//	move diven quote to default
	public void moveQuoteToDefault(TreeItem itemToMove) {
		Quote quote = getQuote(itemToMove);
		discussion.relocateQuote(quote.getID(), discussion.getDefOpID());// discussion.getOpinionsId(Discussion.DEFAULT_OPINION));
	}

//	move given quote to opinion
	public void moveQuoteToOpinion(TreeItem quoteToAdd) {
		Opinion opinion = (Opinion) quoteToAdd.getParentItem().getData(OPINION);
		Quote quote = getQuote(quoteToAdd);
		discussion.relocateQuote(quote.getID(), opinion.getId());
	}

//	add new opinion to tree
	public void addOpinion(String opinionName) {
		discussion.addOpinion(opinionName);
		addTreeOpinion(rootItem, new Opinion(opinionName, discussion
				.getOpinionsId(opinionName)));
	}
	
//	remove opinion from file
	public void removeOpinionFromFile(TreeItem opinionToRemove) {
		Opinion opinion = getOpinion(opinionToRemove);
		discussion.removeOpinion(opinion.getId());
	}

//	remove given quote from file
	public void removeQuoteFromFile(TreeItem quoteToRemove) {
		Quote quote = getQuote(quoteToRemove);
		discussion.removeQuote(quote.getID());
	}
	
	/*
	 * @see ITextEditor#selectAndReveal(int, int)
	 */
	public void selectAndReveal(int start, int length) {
		int id = start >> 19;
		int offset = start - (id << 19);
		boolean isComment = (id & 1) == 1;
		id >>= 1;
		//System.out.println("id: " + id + ", offset: " + offset);
		if (id == 0) {
			activateItem(rootItem);
			return;
		}
		
		for (TreeItem item : rootItem.getItems()) {
			if (getOpinion(item).getId() == id) {
				activateItem(item);
				break;
			}
			for (TreeItem qItem : item.getItems()) {
				if (getQuote(qItem).getID() == id) {
					activateItems(findQuoteItem(qItem, offset, length, isComment));
					break;
				}
			}
		}
	}

	private TreeItem[] findQuoteItem(TreeItem item, int offset, int length, boolean isComment) {
		TreeItem[] def = new TreeItem[] { item };
		
		Integer i = isComment ? (Integer)item.getData(COMMENT_LINE) : 0;
		if (i == null) {
			return def;
		}
		
		TreeItem[] items = item.getItems();
		for (; i < items.length && offset >= items[i].getText().length(); i++)
			offset -= items[i].getText().length();
		
		if (i == items.length)
			return def;
		
		LinkedList<TreeItem> l = new LinkedList<TreeItem>();
		offset += length;
		for (; i < items.length && offset > 0; i++) {
			l.add(items[i]);
			offset -= items[i].getText().length();
		}
		
		return l.toArray(new TreeItem[l.size()]);
	}

	private void activateItem(TreeItem item) {
		item.setExpanded(true);
		item.getParent().setSelection(item);
		item.getParent().setFocus();
	}
	
	private void activateItems(TreeItem[] item) {
		item[0].getParent().setSelection(item);
		item[0].getParent().setFocus();
	}
	
//	****** P R O T E C T E D  F U N C T I O N S *****************************************
	
	/**
	 * This method allows us to update the displayed information, when the files
	 * on the disk change. It updates the treeItems to match the file's content
	 * Note: Additions to the xml file will be displayed after the existing
	 * entries
	 */
	@Override
	public void setFocus() {
		super.setFocus();
		long t = discussion.getModificationStamp();
		if (t != localModificationStamp) {
			localModificationStamp = t;
			synchronizeOpinions();
			// now the opinions should be synchronized with the file
			synchronizeQuotes();
		}
	}
	
	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		setDocumentProvider(new StorageDocumentProvider());
		
		super.doSetInput(input);
		
		if (!(input instanceof FileEditorInput))
			return;

		FileEditorInput fileEditorInput = (FileEditorInput) input;
		IFile file = fileEditorInput.getFile();

		discussion = ToK.getProjectToK(file.getProject()).getDiscussion(file);
		localModificationStamp = discussion.getModificationStamp();
	}
	
}
