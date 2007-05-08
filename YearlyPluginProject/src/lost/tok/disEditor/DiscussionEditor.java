package lost.tok.disEditor;

import java.util.TreeMap;

import lost.tok.Discussion;
import lost.tok.Opinion;
import lost.tok.Quote;
import lost.tok.ToK;
import lost.tok.opTable.OperationTable;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
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
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

public class DiscussionEditor extends TextEditor {
	
	// Tree disTree;
	private static final String DISCUSSION = "discussion";

	private static final String QUOTE = "Quote";

	private static final String OPINION = "Opinion";

	public static final String EDITOR_ID = "lost.tok.disEditor.DiscussionEditor"; 

	private Discussion discussion = null;

	private TreeItem rootItem = null;

	private Composite editor = null;
	
	private int ctrlCurrentWidth = 0;
	
	public DiscussionEditor()
	{
		super();
	}
	
	public void createPartControl(Composite parent) {
		final Composite par = parent;
		editor = par;
		final Tree disTree = new Tree(parent, SWT.MULTI|SWT.WRAP|SWT.BORDER);
		
		
		// ***********************************************************
		// ************************ DELETE QUOTES AND OPINIONS ******* 
		// ***********************************************************
		disTree.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					// deletion of descussion or default opinion is NOT allowed
					if (disTree.getSelection()[0].getData().equals(DISCUSSION)
							|| (disTree.getSelection()[0].getData().equals(
									OPINION) && disTree.getSelection()[0]
									.getText().equalsIgnoreCase(
											Discussion.DEFAULT_OPINION))) {
						return;
					}

					// deleting an opinion
					if (disTree.getSelection()[0].getData().equals(OPINION)) {
						TreeItem defOp = null;
						TreeItem itmArr[] = disTree.getItem(0).getItems();

						for (TreeItem element : itmArr) {
							if (element.getText().equalsIgnoreCase(
									Discussion.DEFAULT_OPINION)) {
								defOp = element;
							}
						}

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
		// ************************ DOUBLE CLICK ON QUOTE  ******* 
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
				if (treeItem.getData(QUOTE) instanceof Quote) {
					Quote quote = (Quote) treeItem.getData(QUOTE);

					IWorkbenchWindow ww = getSite().getWorkbenchWindow();
					String editorId = OperationTable.EDITOR_ID;

//					FileEditorInput fileEditorInput = (FileEditorInput) getEditorInput();
//					ToK tok = ToK.getProjectToK(fileEditorInput.getFile()
//							.getProject());

					IFile source = quote.getSource().getFile();

					try {
						ww.getActivePage().openEditor(
								new FileEditorInput(source), editorId);
					} catch (PartInitException e1) {
						// e1.printStackTrace();
					}
				}

			}

			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

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
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = disTree.getSelection();
				if (selection.length > 0
						&& ((String) selection[0].getData()).equals(QUOTE)) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			};

			public void dragSetData(DragSourceEvent event) {
				event.data = dragSourceItem[0].getText();
			}

			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE) {
					// RemoveQuoteFromOpinion(dragSourceItem[0]);
					dragSourceItem[0].dispose();
				}
				dragSourceItem[0] = null;
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
					item.setText("basa");
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
		
		// **************************************************************************************************
		// *************************** ASSIGN TREE EDITOR *************************************************
		// **************************************************************************************************
		discussion = getDiscussion();

		rootItem = new TreeItem(disTree, SWT.MULTI|SWT.WRAP);

		rootItem.setText(discussion.getDiscName());
		rootItem.setData(DISCUSSION);

		parent.getChildren()[0].addControlListener(new ControlAdapter(){
			public void controlResized(ControlEvent e){
				if(ctrlCurrentWidth != par.getSize().x)
				{
					ctrlCurrentWidth = par.getSize().x;
					rootItem.removeAll();
					
					for (Opinion opinion : discussion.getOpinions()) {
						TreeItem opinionItem = addTreeOpinion(rootItem, opinion);
		
						for (Quote quote : discussion.getQuotes(opinion.getName())) {
							addTreeQuote(opinionItem, quote);
						}
					}
				}
			}

		});
		
//		disTree.addSelectionListener(new SelectionAdapter() {
//			public void widgetSelected(SelectionEvent e) {
////				final TreeEditor disTreeEditor = new TreeEditor(disTree);
////				disTreeEditor.horizontalAlignment = SWT.LEFT;
////				disTreeEditor.grabHorizontal = true;
////				disTreeEditor.minimumWidth = 50;
////				
////				// Clean up any previous editor control
////				Control oldEditor = disTreeEditor.getEditor();
////				if (oldEditor != null) oldEditor.dispose();
////		
////				// Identify the selected row
////				TreeItem item = (TreeItem)e.item;
////				if (item == null) return;
////		
////				// The control that will be the editor must be a child of the Tree
////				Text newEditor = new Text(disTree, SWT.WRAP | SWT.READ_ONLY | SWT.MULTI |SWT.V_SCROLL);
////				newEditor.setText(item.getText());
////				newEditor.addModifyListener(new ModifyListener() {
////					public void modifyText(ModifyEvent e) {
////						Text text = (Text)disTreeEditor.getEditor();
////						disTreeEditor.getItem().setText(text.getText());
////					}
////				});
////				newEditor.selectAll();
////				newEditor.setFocus();			
////				disTreeEditor.setEditor(newEditor, item);
//				
//				rootItem.removeAll();
//				
//				for (Opinion opinion : discussion.getOpinions()) {
//					TreeItem opinionItem = addTreeOpinion(rootItem, opinion);
//
//					for (Quote quote : discussion.getQuotes(opinion.getName())) {
//						addTreeQuote(opinionItem, quote);
//					}
//				}
//				
////				TreeItem infoItem = new TreeItem(disTree,SWT.WRAP);
////				infoItem.setText("x before  = " + par.getBounds().width);
//			}
//		});

//		int x = par.getBounds().width;
//		
//		TreeItem infoItem = new TreeItem(disTree,SWT.WRAP);
//		infoItem.setText("x after = " + x);
		
		// Image imageDisc = new Image(null, new
		// FileInputStream("C:/discussion.gif"));

		// rootItem.setImage(imageDisc);

//		for (Opinion opinion : discussion.getOpinions()) {
//			TreeItem opinionItem = addTreeOpinion(rootItem, opinion);
//
//			for (Quote quote : discussion.getQuotes(opinion.getName())) {
//				addTreeQuote(opinionItem, quote);
//			}
//		}
		

		// *************************************************************************************************
		
		// expends the root and it's opinions, but not the quotes
		expendToDepth(rootItem, 3); 
	}
	
	/**
	 * Expends the tree till it dies
	 * 
	 * This function will expend the Item and its children,
	 *  up to depthLeft levels
	 *  
	 * @param treeItem the item to expend
	 * @param depthLeft the number of levels to expend (1 means only this level)
	 */
	private void expendToDepth(TreeItem treeItem, int depthLeft)
	{
		if (depthLeft <= 0)
			return;
		treeItem.setExpanded(true);
		for (TreeItem child : treeItem.getItems())
			expendToDepth(child, depthLeft - 1);
	}

	private TreeItem addTreeOpinion(TreeItem treeItem, Opinion opinion) {
		TreeItem opinionItem = new TreeItem(treeItem, SWT.MULTI|SWT.WRAP);
		// Image imageOpin = new Image(null, new
		// FileInputStream("C:/opinion.gif"));

		setTreeOpinion(opinion, opinionItem);
		return opinionItem;
	}

	private void setTreeOpinion(Opinion opinion, TreeItem opinionItem) {
		opinionItem.setText(opinion.getName());
		opinionItem.setData(OPINION, opinion);
		opinionItem.setData(OPINION);
		// opinionItem.setImage(imageOpin);
	}

	private TreeItem addTreeQuote(TreeItem treeItem, Quote quote) {
		TreeItem quoteItem = new TreeItem(treeItem,SWT.MULTI|SWT.WRAP);
		// Image imageQuote = new Image(null, new
		// FileInputStream("C:/quote.gif"));

		setTreeQuote(quote, quoteItem);
		return quoteItem;
	}

	private void setTreeQuote(Quote quote, TreeItem quoteItem) {

		String quoteText = new String(quote.getText());
		quoteItem.setText(quoteText.substring(0, 10) + "...");
		
		quoteItem.setData(QUOTE, quote);
		quoteItem.setData(QUOTE);
		// quoteItem.setImage(imageQuote);
			
		ctrlCurrentWidth = editor.getSize().x;
		
		int editorWidth = editor.getSize().x;
		int lineSize = editorWidth/10;	
		
		if(lineSize == 0){
			lineSize = 100;
		}

		//QUOTE SONS ARE THIS QOUTE ONLY WORD WRAPPED
		int lineCnt = quoteText.length()/lineSize;
		
		for (int i = 0; i <= lineCnt ; i++) {
			TreeItem qText = new TreeItem(quoteItem,SWT.WRAP);
			
			if(i == lineCnt){
				qText.setText(quoteText.substring((i)*lineSize));
				break;
			}
			if(IsInMiddleOfWord(quoteText,lineCnt,i,lineSize))
				qText.setText(quoteText.substring((i)*lineSize,((i+1)*lineSize)) + "-");
			else
				qText.setText(quoteText.substring((i)*lineSize,((i+1)*lineSize)));
		}		
		
		//MAKE SONS QUOTE COMMENTS
		String quoteComment = new String(quote.getComment());
		
		if(quoteComment.trim().length() != 0)
		{		
			//make son saparator
			String saparator = new String("-");
			for (int i = 0; i < lineSize; i++) {
				saparator+="-";
			}
			TreeItem saparatQuote = new TreeItem(quoteItem,SWT.WRAP);
			saparatQuote.setText(saparator);

			int commentLineCnt = quoteComment.length()/lineSize;
			
			for (int i = 0; i <= commentLineCnt ; i++) {
				TreeItem qComment = new TreeItem(quoteItem,SWT.WRAP);
				if(i == commentLineCnt){
					qComment.setText(quoteComment.substring((i)*lineSize));
					break;
				}
				if(IsInMiddleOfWord(quoteComment,commentLineCnt,i,lineSize))
					qComment.setText(quoteComment.substring((i)*lineSize,((i+1)*lineSize)) + "-");
				else
					qComment.setText(quoteComment.substring((i)*lineSize,((i+1)*lineSize)));
			}		
			
			TreeItem sprtQuote = new TreeItem(quoteItem,SWT.WRAP);
			sprtQuote.setText(saparator);
		}
	}

	private boolean IsInMiddleOfWord(String quoteText,int totalLineNum, int lineNumber, int lineSize)
	{
		if(quoteText.charAt((lineNumber+1)*lineSize - 1) != ' ' &&
				quoteText.charAt((lineNumber+1)*lineSize) != ' ')
			return true;
		
		return false;
	}
	
	public void moveQuoteToDefault(TreeItem itemToMove) {
		Quote quote = getQuote(itemToMove);
		discussion.relocateQuote(quote.getID(), discussion
				.getOpinionsId(Discussion.DEFAULT_OPINION));
	}

	private Quote getQuote(TreeItem itemToMove) {
		Quote quote = (Quote) itemToMove.getData(QUOTE);
		return quote;
	}

	public void removeOpinionFromFile(TreeItem opinionToRemove) {
		Opinion opinion = getOpinion(opinionToRemove);
		discussion.removeOpinion(opinion.getId());
	}

	private Opinion getOpinion(TreeItem opinionToRemove) {
		Opinion opinion = (Opinion) opinionToRemove.getData(OPINION);
		return opinion;
	}

	public Discussion getDiscussion() {
		if (discussion != null) {
			return discussion;
		}

		if (!(super.getEditorInput() instanceof FileEditorInput)) {
			// TODO: error message
			return null;
		}

		try {
			FileEditorInput fileEditorInput = (FileEditorInput) super
					.getEditorInput();
			IFile file = fileEditorInput.getFile();

			Discussion d = ToK.getProjectToK(file.getProject()).getDiscussion(
					Discussion.getNameFromFile(file.getName()));
			return d;
		} catch (CoreException e) {
		}
		return null;
	}

	public void removeQuoteFromFile(TreeItem quoteToRemove) {
		Quote quote = getQuote(quoteToRemove);
		discussion.removeQuote(quote.getID());
	}

	public void moveQuoteToOpinion(TreeItem quoteToAdd) {
		Opinion opinion = (Opinion) quoteToAdd.getParentItem().getData(OPINION);
		Quote quote = getQuote(quoteToAdd);
		discussion.relocateQuote(quote.getID(), opinion.getId());
	}

	public void addOpinion(String opinionName) {
		discussion.addOpinion(opinionName);
		addTreeOpinion(rootItem, new Opinion(opinionName, discussion
				.getOpinionsId(opinionName)));
	}
	
	/**
	 * Returns all the displayed opinions
	 */
	public Opinion[] getDisplayedOpinions()
	{
		Opinion[] retVal = new Opinion[rootItem.getItemCount()]; 
		
		int i = 0;
		for (TreeItem opItem : rootItem.getItems())
		{
			retVal[i] = getOpinion(opItem);
			i++;			
		}
		return retVal;
	}
	
	/**
	 * Returns the quotes displayed in the tree
	 * Written for testing
	 * 
	 * @param opinionId the opinion whose quotes are to be returned 
	 * @return the quotes which are displayed under the given opinion id
	 */
	public Quote[] getDisplayedQuotes(Integer opinionId)
	{
		TreeItem[] opItems = rootItem.getItems();
		int i = 0;
		
		// move i to the opinion we are looking for
		while (i < opItems.length && getOpinion(opItems[i]).getId() != opinionId)
		{
			i++;		
		}
		
		if (i == opItems.length)
			return null; // dude, your opinion id doesn't even exist!
		// else
		
		TreeItem[] qItems = rootItem.getItem(i).getItems();
		Quote[] quotes = new Quote[ qItems.length ];
		
		for (int j = 0; j < quotes.length ; j++)
		{
			quotes[j] = getQuote(qItems[j]); 
		}
		
		return quotes;		
	}
		
	
	/**
	 * This method allows us to update the displayed information, when the
	 * files on the disk change.
	 * It updates the treeItems to match the file's content
	 * Note: Additions to the xml file will be displayed after the existing entries
	 */
	@Override
	protected void handleEditorInputChanged() {
		
		discussion = null; // lose the previous discussion		
		discussion = getDiscussion(); // update all the data
		
		synchronizeOpinions();
		// now the opinions should be synchronized with the file		
		synchronizeQuotes();
	}
	
	/**
	 * Synchronizes the opinions with a modified discussion file
	 * 
	 * Assumption: The discussion member is pointing to the new discussion
	 * 				and doesn't change
	 */
	private void synchronizeOpinions()
	{
		TreeMap<Integer, Opinion> existingOpinions =
			new TreeMap<Integer, Opinion>();
		
		// find all the opinions existing in the discussion (not the tree)
		for (Opinion opinion : discussion.getOpinions()) {
			int id = opinion.getId();
			existingOpinions.put(id, opinion);
		}
		
		// for each opinion in the tree, find its status (belongs, doesn't belong)
		for (TreeItem opinionItem : rootItem.getItems())
		{
			int id = getOpinion(opinionItem).getId();
			if (!existingOpinions.containsKey(id))
			{
				// remove the opinion from the view
				opinionItem.dispose();
			}
			else
			{
				// mark that the opinion is already in the view
				existingOpinions.remove(id);
			}
		}
		
		// add the opinions which weren't found in the tree
		//  new opinions are expended by default
		for (Integer opinionId : existingOpinions.keySet())
		{
			TreeItem opItem = addTreeOpinion(rootItem, existingOpinions.get(opinionId));
			opItem.setExpanded(true);
		}
	}

	/**
	 * Synchronizes the opinions with a modified discussion file
	 * 
	 * Assumption: The discussion member is pointing to the new discussion
	 * 				and doesn't change.
	 * 			   The opinions are already synchronized
	 *
	 */
	private void synchronizeQuotes() {
		TreeMap<Integer, TreeItem> treeOpinions =
			new TreeMap<Integer, TreeItem>();
		
		// map the opinions in the tree:  id->treeItem
		for (TreeItem opinionItem : rootItem.getItems())
		{
			int id = getOpinion(opinionItem).getId();
			treeOpinions.put(id, opinionItem);
		}
		
		// for each opinion, update its quotes
		for (Opinion opin : discussion.getOpinions())
		{
			int opinionId = opin.getId();
			
			TreeMap<Integer, Quote> existingQuotes =
				new TreeMap<Integer, Quote>();
			
			// find all the quotes existing in the opinion (not the tree)
			for (Quote quote : discussion.getQuotes(opin.getName()) ) {
				int id = quote.getID();
				existingQuotes.put(id, quote);
			}
			
			TreeItem opItem = treeOpinions.get(opinionId);
			
			// for each quote in the tree, find its status (belongs, doesn't belong)
			for (TreeItem quoteItem : opItem.getItems())
			{
				int id = getQuote(quoteItem).getID();
				if (!existingQuotes.containsKey(id))
				{
					// remove the opinion from the view
					quoteItem.dispose();
				}
				else
				{
					// mark that the opinion is already in the view
					existingQuotes.remove(id);
				}
			}
			
			// add the quotes which weren't found in the tree
			//  and expend them and their opinions
			for (Integer quoteId : existingQuotes.keySet())
			{
				TreeItem qItem = addTreeQuote(opItem, existingQuotes.get(quoteId));
				opItem.setExpanded(true);
				qItem.setExpanded(true);
			}
		}
	}
}
