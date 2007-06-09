package lost.tok.authEditor;


import java.util.TreeMap;

import lost.tok.Author;
import lost.tok.AuthorsHandler;
import lost.tok.Discussion;
import lost.tok.Rank;
import lost.tok.ToK;

import org.eclipse.core.resources.IFile;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

public class AuthorsEditor extends TextEditor {
	
	/******* M E M B E R S *******************************/
	
	public static final String EDITOR_ID = "lost.tok.authEditor.AuthorsEditor";
	private static final String DISCUSSION = "discussion";
	private static final String QUOTE = "Quote";
	private static final String OPINION = "Opinion";
	private AuthorsHandler authHandler = null;
//	private Composite editor = null;
	private TreeItem rootItem = null;
	private int ctrlCurrentWidth = 0;
	
	//******* C ' T O R *************************************
	public AuthorsEditor() 
	{
		super();
	}

	
	//******* CREATOR OF THE TREE EDITOR *****************************
	
	public void createPartControl(Composite parent) {
		final Composite par = parent;
//		editor = par;
		final Tree authTree = new Tree(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER);

		/**************************************************************
		 * T E M P O R A R Y   S H A D O W E D
		 **************************************************************
		
		// ***********************************************************
		// ************************ DELETE QUOTES AND OPINIONS *******
		// ***********************************************************
		disTree.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					// deletion of descussion or default opinion is NOT allowed
					if (disTree.getSelection()[0].getData().equals(DISCUSSION)
							|| (disTree.getSelection()[0].getData().equals(
									OPINION) && discussion.getOpinionsId(
									disTree.getSelection()[0].getText())
									.equals(Discussion.DEFAULT_OPINION_ID))) {
						return;
					}

					// deleting an opinion
					if (disTree.getSelection()[0].getData().equals(OPINION)) {
						TreeItem defOp = null;
						TreeItem itmArr[] = disTree.getItem(0).getItems();

						// fatch default opinion
						for (TreeItem element : itmArr) {
							if (discussion.getOpinionsId(element.getText())
									.equals(Discussion.DEFAULT_OPINION_ID)) {
								defOp = element;
							}
						}

						// if no default opinion create one
						if (defOp == null) {
							defOp = new TreeItem(disTree.getItem(0), SWT.NONE);
							defOp.setText(Discussion.DEFAULT_OPINION);
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

					// Loading the quote's xml document, and creating a
					// SourceDocument from it
					Document xmlSrcDoc = GeneralFunctions.readFromXML(quote
							.getSource().getFile());
					SourceDocument srcDoc = new SourceDocument();
					srcDoc.set(xmlSrcDoc);

					boolean firstExcerp = true;
					int qBegining = 0, qLength = 0;
					for (Excerption ex : quote.getExcerptions()) {
						// getting the chapter in which the text appears
						ChapterText ct = srcDoc.getChapterText(ex
								.getPathInSourceFile());
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

*/
		// ***********************************************************************
		// *************************** ASSIGN TREE EDITOR  *********************
		// **********************************************************************
		authHandler = getAuthorsHandler();

		rootItem = new TreeItem(authTree, SWT.MULTI | SWT.WRAP);

		rootItem.setText("Authors rank groups");
		rootItem.setData(DISCUSSION);

		parent.getChildren()[0].addControlListener(new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				if (ctrlCurrentWidth != par.getSize().x) {
					ctrlCurrentWidth = par.getSize().x;
					rootItem.removeAll();

					for (Rank rank : authHandler.getRanks()) {
						TreeItem rankItem = addTreeRank(rootItem, rank);

						for (Author author : authHandler.getAuthors(rank.getName())) {
							addTreeAuthor(rankItem, author);
						}
					}

					rootItem.setExpanded(true);
				}
			}

		});


		// expends the root and it's opinions, but not the authors
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
	private TreeItem addTreeRank(TreeItem treeItem, Rank rank) {
		TreeItem rankItem = new TreeItem(treeItem, SWT.MULTI | SWT.WRAP);

		setTreeRank(rank, rankItem);
		return rankItem;
	}

//	add author to rank
	private TreeItem addTreeAuthor(TreeItem treeItem, Author author) {
		TreeItem authorItem = new TreeItem(treeItem, SWT.MULTI | SWT.WRAP);

		setTreeAuthor(author, authorItem);
		return authorItem;
	}

//	return opinion from tree item
	private Rank getRank(TreeItem rankToRemove) {
		Rank rank = (Rank) rankToRemove.getData(OPINION);
		return rank;
	}

	private Author getAuthor(TreeItem itemToMove) {
		Author author = (Author) itemToMove.getData(QUOTE);
		return author;
	}


//	set opinion properties
	private void setTreeRank(Rank rank, TreeItem rankItem) {
		rankItem.setText(rank.getName());
		rankItem.setData(OPINION, rank);
		rankItem.setData(OPINION);
		// opinionItem.setImage(imageOpin);
	}

//	set author properties
	private void setTreeAuthor(Author author, TreeItem authorItem) {

		authorItem.setText(author.getName());

		authorItem.setData(QUOTE, author);
		authorItem.setData(QUOTE);
	}

	/**
	 * Synchronizes the opinions with a modified discussion file
	 * 
	 * Assumption: The discussion member is pointing to the new discussion and
	 * doesn't change
	 */
	private void synchronizeRanks() {
		TreeMap<Integer, Rank> existingRanks = new TreeMap<Integer, Rank>();

		// find all the opinions existing in the discussion (not the tree)
		for (Rank rank : authHandler.getRanks()) {
			int id = rank.getId();
			existingRanks.put(id, rank);
		}

		// for each opinion in the tree, find its status (belongs, doesn't
		// belong)
		for (TreeItem rankItem : rootItem.getItems()) {
			int id = getRank(rankItem).getId();
			if (!existingRanks.containsKey(id)) {
				// remove the rank from the view
				rankItem.dispose();
			} else {
				// mark that the rank is already in the view
				existingRanks.remove(id);
			}
		}

		// add the opinions which weren't found in the tree
		// new opinions are expended by default
		for (Integer rankId : existingRanks.keySet()) {
			TreeItem rankItem = addTreeRank(rootItem, existingRanks.get(rankId));
			rankItem.setExpanded(true);
		}
	}


	/**
	 * Synchronizes the opinions with a modified discussion file
	 * 
	 * Assumption: The discussion member is pointing to the new discussion and
	 * doesn't change. The opinions are already synchronized
	 * 
	 */	
/*	private void synchronizeAuthorss() {
		TreeMap<Integer, TreeItem> treeRanks = new TreeMap<Integer, TreeItem>();

		// map the ranks in the tree: id->treeItem
		for (TreeItem rankItem : rootItem.getItems()) {
			int id = getRank(rankItem).getId();
			treeRanks.put(id, rankItem);
		}

		// for each rank, update its authors
		for (Rank ran : authHandler.getRanks()) {
			int rankId = ran.getId();

			TreeMap<Integer, Author> existingAuthors = new TreeMap<Integer, Author>();

			// find all the quotes existing in the opinion (not the tree)
			for (Author author : authHandler.getAuthors(ran.getName())) {
				int id = author.getID();
				existingAuthors.put(id, author);
			}

			TreeItem ranItem = treeRanks.get(rankId);

			// for each quote in the tree, find its status (belongs, doesn't
			// belong)
			for (TreeItem authorItem : ranItem.getItems()) {
				int id = getAuthor(authorItem).getID();
				if (!existingAuthors.containsKey(id)) {
					// remove the opinion from the view
					authorItem.dispose();
				} else {
					// mark that the opinion is already in the view
					existingAuthors.remove(id);
				}
			}

			// add the quotes which weren't found in the tree
			// and expend them and their opinions
			for (Integer authorId : existingAuthors.keySet()) {
				TreeItem aItem = addTreeAuthor(ranItem, existingAuthors.get(authorId));
				ranItem.setExpanded(true);
				aItem.setExpanded(true);
			}
		}
	}
*/	
	
//	****** P U B L I C  F U N C T I O N S *****************************************
	
//	return the AuthorsHandler
	public AuthorsHandler getAuthorsHandler() {
		if (authHandler != null) {
			return authHandler;
		}

		if (!(super.getEditorInput() instanceof FileEditorInput)) {
			// todo - print error message
			return null;
		}

		try {
			FileEditorInput fileEditorInput = (FileEditorInput) super
					.getEditorInput();
			IFile file = fileEditorInput.getFile();

			ToK tok = ToK.getProjectToK(file.getProject());

			AuthorsHandler d = new AuthorsHandler(tok,tok.getAuthorFile().toString());
			
			return d;
		} 
		catch (Exception e) {
		}
		
		return null;
	}

	/**
	 * Returns all the displayed opinions
	 */
	public Rank[] getDisplayedRanks() {
		Rank[] retVal = new Rank[rootItem.getItemCount()];

		int i = 0;
		for (TreeItem opItem : rootItem.getItems()) {
			retVal[i] = getRank(opItem);
			i++;
		}
		return retVal;
	}

	/**
	 * Returns the authors displayed in the tree Written for testing
	 * 
	 * @param rankId
	 *            the rank whose quotes are to be returned
	 * @return the quotes which are displayed under the given opinion id
	 */
	public Author[] getDisplayedRanks(Integer rankId) {
		TreeItem[] ranItems = rootItem.getItems();
		int i = 0;

		// move i to the rank we are looking for
		while (i < ranItems.length
				&& getRank(ranItems[i]).getId() != rankId) {
			i++;
		}

		if (i == ranItems.length)
			return null; // dude, your opinion id doesn't even exist!
		// else

		TreeItem[] aItems = rootItem.getItem(i).getItems();
		Author[] authors = new Author[aItems.length];

		for (int j = 0; j < authors.length; j++) {
			authors[j] = getAuthor(aItems[j]);
		}

		return authors;
	}

//	move diven quote to default
	public void moveAuthorToDefault(TreeItem itemToMove) {
		Author author = getAuthor(itemToMove);
		authHandler.relocateAuthor(author.getName(), Discussion.DEFAULT_OPINION_ID);
	}

//	move given quote to opinion
	public void moveAuthorToRank(TreeItem authorToAdd) {
		Rank rank = (Rank) authorToAdd.getParentItem().getData(OPINION);
		Author author = getAuthor(authorToAdd);
		authHandler.relocateAuthor(author.getName(), rank.getId());
	}

//	add new opinion to tree
	public void addRank(String rankName) {
//		authHandler.addRank(rankName);
		addTreeRank(rootItem, new Rank(rankName, authHandler
				.getRanksId(rankName)));
	}

/*	
//	remove opinion from file
	public void removeRankFromFile(TreeItem rankToRemove) {
		Rank rank = getRank(rankToRemove);
		authHandler.removeRank(rank.getId());
	}
*/

//	remove given quote from file
	public void removeAuthorFromFile(TreeItem authorToRemove) {
		Author author = getAuthor(authorToRemove);
		authHandler.removeAuthor(author.getName());
	}
	
	
	
//	****** P R O T E C T E D  F U N C T I O N S *****************************************
	
	/**
	 * This method allows us to update the displayed information, when the files
	 * on the disk change. It updates the treeItems to match the file's content
	 * Note: Additions to the xml file will be displayed after the existing
	 * entries
	 */
	@Override
	protected void handleEditorInputChanged() {

		authHandler = null; // lose the previous discussion
		authHandler = getAuthorsHandler();// update all the data

		synchronizeRanks();
		// now the opinions should be synchronized with the file
//		synchronizeAuthors();
	}
}

