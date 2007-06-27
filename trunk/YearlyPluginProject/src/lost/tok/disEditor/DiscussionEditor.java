package lost.tok.disEditor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

import lost.tok.Discussion;
import lost.tok.Excerption;
import lost.tok.Link;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.SubLink;
import lost.tok.ToK;
import lost.tok.RelationView.RelationView;
import lost.tok.imageManager.ImageManager;
import lost.tok.imageManager.ImageType;
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
						ChapterText ct = srcDoc.getChapterTextFromEPath(ex.getEPath());
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
		
		rootItem.setImage(ImageManager.getImage(ImageType.DISCUSSION));
		
		rootItem.setText(discussion.getDiscName() + " (" + "Creator: " + discussion.getCreatorName() + ")");
		rootItem.setData(DISCUSSION);
//		ICommandImageService o = (ICommandImageService) getSite().getWorkbenchWindow().getWorkbench().getAdapter(ICommandImageService.class);
//		ImageDescriptor i = o.getImageDescriptor("lost.tok.images.discusion");;

		parent.getChildren()[0].addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (ctrlCurrentWidth != par.getSize().x) {
					ctrlCurrentWidth = par.getSize().x;
					rootItem.removeAll();

					addDescription(rootItem);
					addLinks(rootItem);

					for (Opinion opinion : discussion.getOpinions()) {
						TreeItem opinionItem = addTreeOpinion(rootItem, opinion);
						//opinionItem.setImage(opinImage);
						for (Quote quote : discussion.getQuotes(opinion
								.getName())) {
							//TreeItem quoteItem =
							addTreeQuote(opinionItem, quote);
							//quoteItem.setImage(quoteImage);
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
	

	private void addDescription(TreeItem parent) {
		String description = discussion.getDescription();
		if (description == null || description.length() == 0) return;
		
		TreeItem descItem = new TreeItem(parent, SWT.MULTI | SWT.WRAP);
		descItem.setImage(ImageManager.getImage(ImageType.DESCRIPTION));
		descItem.setText("Description");
		
		addSplitted(descItem, description, getLineSize());
	}
	
	private void addLinks(TreeItem parent) {
		Link l = discussion.getLink();
		if (l == null) return;

		TreeItem linkItem = new TreeItem(parent, SWT.MULTI | SWT.WRAP);
		linkItem.setImage(ImageManager.getImage(ImageType.LINK));
		linkItem.setText("Link type: " + l.getDisplayLinkType()
				+ ", Link subject: '" + l.getSubject() + "'");
		
		for (SubLink s : l.getSubLinkList()) {
			TreeItem sublinkItem = new TreeItem(linkItem, SWT.MULTI | SWT.WRAP);
			sublinkItem.setImage(ImageManager.getImage(ImageType.ROOT));
			
			String text = s.getText();
			sublinkItem.setText("(Root: '" + s.getLinkedSource().getTitle()
					+ ") " + text.substring(0, Math.min(40, text.length())) + "...");
			
			addSplitted(sublinkItem, text, getLineSize());
		}
	}

//	add opinion to tree
	private TreeItem addTreeOpinion(TreeItem parent, Opinion opinion) {
		TreeItem opinionItem = new TreeItem(parent, SWT.MULTI | SWT.WRAP);
		setTreeOpinion(opinion, opinionItem);
		return opinionItem;
	}

//	add quote to opinion
	private TreeItem addTreeQuote(TreeItem parent, Quote quote) {
		TreeItem quoteItem = new TreeItem(parent, SWT.MULTI | SWT.WRAP);
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
		opinionItem.setImage(ImageManager.getImage(ImageType.OPINION));
		opinionItem.setText(opinion.getName());
		opinionItem.setData(OPINION, opinion);
		opinionItem.setData(OPINION);
		// opinionItem.setImage(imageOpin);
	}
	
//	set quote properties
	private void setTreeQuote(Quote quote, TreeItem quoteItem) {

		String quoteText = quote.getText();
		quoteItem.setImage(ImageManager.getImage(ImageType.QUOTE));
		quoteItem.setText(quoteText.substring(0, java.lang.Math.min(40,
				quoteText.length())) + "..."); //$NON-NLS-1$

		quoteItem.setData(QUOTE, quote);
		quoteItem.setData(QUOTE);
		// quoteItem.setImage(imageQuote);

		ctrlCurrentWidth = editor.getSize().x;

		int lineSize = getLineSize();

		int wrappedLines = addSplitted(quoteItem, quoteText, lineSize);
		
		if (quote.getComment().trim().length() != 0) {
			// make son saparator
			TreeItem treeItem = new TreeItem(quoteItem, SWT.WRAP);
			treeItem.setText("----- " + "Comment" + " -----");
			treeItem.setImage(ImageManager.getImage(ImageType.QUOTE));
			
			wrappedLines++;
			quoteItem.setData(COMMENT_LINE, wrappedLines);
			addSplitted(quoteItem, quote.getComment(), lineSize);
		}
	}


	private int getLineSize() {
		int editorWidth = editor.getSize().x;
		int lineSize = editorWidth / 10;

		if (lineSize == 0) {
			lineSize = 100;
		}
		return lineSize;
	}


	private int addSplitted(TreeItem quoteItem, String quoteText, int lineSize) {
		LinkedList<String> splitWrap = splitWrap(quoteText, lineSize);
		for (String s : splitWrap) {
			TreeItem treeItem = new TreeItem(quoteItem, SWT.WRAP);
			treeItem.setText(s);
			treeItem.setImage(ImageManager.getImage(ImageType.EXCERPTION));
		}
		
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
			if (getOpinion(opinionItem) == null) {
				// updateLinks
				continue;
			}
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
	private void synchronizeAllQuotes() {
		//TODO: order quotes display by authors ranking, don't change discussion XML
		
		TreeMap<Integer, TreeItem> treeOpinions = new TreeMap<Integer, TreeItem>();

		// map the opinions in the tree: id->treeItem
		for (TreeItem opinionItem : rootItem.getItems()) {
			if (getOpinion(opinionItem) == null)
				continue;

			int id = getOpinion(opinionItem).getId();
			treeOpinions.put(id, opinionItem);
		}

		// for each opinion, update its quotes
		for (Opinion opin : discussion.getOpinions()) {
			int opinionId = opin.getId();
			TreeItem opItem = treeOpinions.get(opinionId);

			synchronizeOpQuotes(opin, opItem);
		}
	}

	/**
	 * Synchronizes the quotes of a specific opinions
	 * Makes sure the quotes are in the same order as the ranks file
	 * Expends new quotes that were added to the opinion
	 * @param fileOp the Opinion object, like the one in the discussion file
	 * @param displayOp the opinion displayed in the discussion editor
	 */
	private void synchronizeOpQuotes(Opinion fileOp, TreeItem displayOp) {
		
		HashSet<Integer> newQuotesIds = new HashSet<Integer>();

		// find all the quotes existing in the opinion (not the tree)
		for (Quote quote : discussion.getQuotes(fileOp.getName())) {
			int id = quote.getID();
			newQuotesIds.add(id);
		}
		// now new quotes has all the quotes in the file

		// for each quote in the tree, find its status (belongs, doesn't belong)
		for (TreeItem quoteItem : displayOp.getItems()) {
			int id = getQuote(quoteItem).getID();
			if (!newQuotesIds.contains(id)) {
				// remove the opinion from the view
				quoteItem.dispose();
			} else {
				// mark that the opinion is already in the view
				newQuotesIds.remove(id);
			}
		}
		// now new quotes contains only the new quotes id's
		
		// Find whether there's a differnce between the order of the file and our order
		// if there is, clean all the opinion and add the quotes according to their order
		TreeItem[] quoteItems = displayOp.getItems();
		Quote[] fileQuotes = discussion.getSortedQuotes( fileOp.getName() );
		
		boolean difference = (quoteItems.length != fileQuotes.length);
		int i = 0;
		
		while (!difference && i < quoteItems.length)
		{
			int dispId = getQuote(quoteItems[i]).getID();
			int fileId = fileQuotes[i].getID();
			
			difference = difference || (dispId != fileId);
			i ++;
		}
		
		if (!difference)
			return; // all ok :)
		
		// else, build this opinion branch anew
		for (TreeItem ti : quoteItems)
			ti.dispose();
		
		// add all the quotes back to the tree, according to their order
		// expend the quotes that are new
		for (Quote q : fileQuotes) {
			TreeItem qItem = addTreeQuote(displayOp, q);
			if ( newQuotesIds.contains( q.getID() ) )
				qItem.setExpanded(true);
		}
		
		// expend the opinion if it contains new quotes
		if (newQuotesIds.size() > 0)
			displayOp.setExpanded(true);
		
		return;
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
			if (getOpinion(item) == null) continue; 
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
		updateViews();
		super.setFocus();
		long t = discussion.getModificationStamp();
		if (t != localModificationStamp) {
			localModificationStamp = t;
			synchronizeOpinions();
			// now the opinions should be synchronized with the file
			synchronizeAllQuotes();
		}
	}
	
	private void updateViews() {
		RelationView view = RelationView.getView(true);
		if (view != null)
			view.update(discussion);
		
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
