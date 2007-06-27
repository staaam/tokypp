package lost.tok.authEditor;

import java.util.TreeMap;

import lost.tok.Author;
import lost.tok.AuthorsHandler;
import lost.tok.Discussion;
import lost.tok.Rank;
import lost.tok.ToK;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Authors rank groups tree editor. Displays authors 
 * rank group in tree vieww. Enables user to drag and drop
 * author from group to group, by that changing their rank
 * @author evgeni
 *
 */
public class AuthorsEditor extends TextEditor {
	
	public static final int DEFAULT_RANK_ID = 0;
	public static final String EDITOR_ID = "lost.tok.authEditor.AuthorsEditor";
	public static final String  AUTHORS_RANK_TREE = AuthorsHandler.AUTHORS_RANK_TREE;
	private static final String AUTHORS_GROUPS = "Authors Groups";
	private static final String AUTHOR = "Author";
	private static final String RANK = "Rank";
	private AuthorsHandler authHandler = null;
	private TreeItem rootItem = null;
	private int ctrlCurrentWidth = 0;
	
	
	/**
	 * Authors editor constructor
	 *
	 */
	public AuthorsEditor() 
	{
		super();
	}

	
	/**
	 * Creates the editor, initializes the tree.
	 * Defines listeners; drag, drop, resize, gain focus
	 */
	public void createPartControl(Composite parent) {
		final Composite par = parent;
		final Tree authTree = new Tree(parent, SWT.MULTI | SWT.WRAP | SWT.BORDER);
   

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

		final DragSource source = new DragSource(authTree, operations);
		source.setTransfer(types);

		final TreeItem[] dragSourceItem = new TreeItem[1];

		//add drag listener		
		source.addDragListener(new DragSourceListener() {
			
			//drag finished listener
			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE) {
					dragSourceItem[0].dispose();
				}
				dragSourceItem[0] = null;
			};

			//drag set data listener
			public void dragSetData(DragSourceEvent event) {
				event.data = dragSourceItem[0].getText();
			}

			//drag start listener
			public void dragStart(DragSourceEvent event) {
				
				//enable drag only if one author is selected
				TreeItem[] selection = authTree.getSelection();
				if (selection.length == 1 && 
						((String) selection[0].getData()).equals(AUTHOR)) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			}
		});

		//add drop listener
		DropTarget target = new DropTarget(authTree, operations);
		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			
			//drag over listener
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					Point pt = par.getDisplay().map(null, authTree, event.x, event.y);
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

			//drop listener
			public void drop(DropTargetEvent event) {
				if (((TreeItem) event.item).getData().equals(AUTHOR)
						|| ((TreeItem) event.item).getData().equals(AUTHORS_GROUPS)) {
					event.detail = DND.DROP_NONE;
					return;
				}
				
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				//calc the exact places where we can drop the item
				Author author = (Author) dragSourceItem[0].getData(AUTHOR);
				if (event.item == null) {
					TreeItem item = new TreeItem(authTree, SWT.NONE);
					item.setText("error");
				} 
				else {
					TreeItem item = (TreeItem) event.item;
					Point pt = par.getDisplay().map(null, authTree, event.x,
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
							if (((String) parent.getData()).equals(RANK)) {
								TreeItem newItem = new TreeItem(parent,
										SWT.NONE, index);
								setTreeAuthor(author, newItem);
								moveAuthorToRank(newItem);
								
								return;
							}
							event.detail = DND.DROP_NONE;
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							if (((String) parent.getData()).equals(RANK)) {
								TreeItem newItem = new TreeItem(parent,
										SWT.NONE, index + 1);
								setTreeAuthor(author, newItem);
								moveAuthorToRank(newItem);
								
								return;
							}
							event.detail = DND.DROP_NONE;
						} else {
							if (((String) item.getData()).equals(RANK)) {
								TreeItem newItem = new TreeItem(item, SWT.NONE);
								setTreeAuthor(author, newItem);
								moveAuthorToRank(newItem);
								
								return;
							}
							event.detail = DND.DROP_NONE;
						}
					} else {
						TreeItem[] items = authTree.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							if (((String) authTree.getData()).equals(RANK)) {
								TreeItem newItem = new TreeItem(authTree,
										SWT.NONE, index);
								setTreeAuthor(author, newItem);
								moveAuthorToRank(newItem);
								
								return;
							}
							event.detail = DND.DROP_NONE;
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							if (((String) authTree.getData()).equals(RANK)) {
								TreeItem newItem = new TreeItem(authTree,
										SWT.NONE, index + 1);
								setTreeAuthor(author, newItem);
								moveAuthorToRank(newItem);
								
								return;
							}
							event.detail = DND.DROP_NONE;
						} else {
							if (((String) item.getData()).equals(RANK)) {
								TreeItem newItem = new TreeItem(item, SWT.NONE);
								setTreeAuthor(author, newItem);
								moveAuthorToRank(newItem);
								
								return;
							}
							event.detail = DND.DROP_NONE;
						}
					}
				}
			}
		});


		//build trees root
		authHandler = getAuthorsHandler();
		rootItem = new TreeItem(authTree, SWT.MULTI | SWT.WRAP);
		rootItem.setText(AUTHORS_RANK_TREE);
		rootItem.setData(AUTHORS_GROUPS);

		//add editors listeners
		parent.getChildren()[0].addControlListener(new ControlAdapter() {
			
			//control resized listener
			public void controlResized(ControlEvent e) {
				if (ctrlCurrentWidth != par.getSize().x) {
					ctrlCurrentWidth = par.getSize().x;
					rootItem.removeAll();

					authHandler.updateFile();
					
					//add all other ranks
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
		
		//add focus listener
		parent.getChildren()[0].addFocusListener(new FocusListener(){

			//focus gained listener
			public void focusGained(FocusEvent e) {			
				authHandler.updateFile();
				par.setSize(ctrlCurrentWidth-1,par.getSize().y);
				
				for(Discussion discussion : authHandler.getMyToK().getDiscussions()){
					discussion.increaseModificationStamp();
				}
			}

			//focus lost listener
			public void focusLost(FocusEvent e) {}			
		});
		
		// expends the root and it's ranks, but not the authors
		expendToDepth(rootItem, 3);
	}

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
	

	/**
	 * Add authors group (rank) to tree
	 * @param treeItem
	 * @param rank
	 * @return
	 */
	private TreeItem addTreeRank(TreeItem treeItem, Rank rank) {
		TreeItem rankItem = new TreeItem(treeItem, SWT.MULTI | SWT.WRAP);

		setTreeRank(rank, rankItem);
		return rankItem;
	}

	/**
	 * Add author to the tree
	 * @param treeItem
	 * @param author
	 * @return
	 */
	private TreeItem addTreeAuthor(TreeItem treeItem, Author author) {
		TreeItem authorItem = new TreeItem(treeItem, SWT.MULTI | SWT.WRAP);

		setTreeAuthor(author, authorItem);
		return authorItem;
	}

	/**
	 * Return rank from tree item
	 * @param rankToRemove
	 * @return rank
	 */
	private Rank getRank(TreeItem rankToRemove) {
		Rank rank = (Rank) rankToRemove.getData(RANK);
		return rank;
	}

	/**
	 * Get author from tree item
	 * @param itemToMove
	 * @return author object
	 */
	private Author getAuthor(TreeItem itemToMove) {
		Author author = (Author) itemToMove.getData(AUTHOR);
		return author;
	}

	/**
	 * Set rank properties
	 * @param rank
	 * @param rankItem
	 */
	private void setTreeRank(Rank rank, TreeItem rankItem) {
		rankItem.setText(rank.getName());
		rankItem.setData(RANK, rank);
		rankItem.setData(RANK);
		// rankItem.setImage(imageOpin);
	}

	/**
	 * Set author properties
	 * @param author
	 * @param authorItem
	 */
	private void setTreeAuthor(Author author, TreeItem authorItem) {

		authorItem.setText(author.getName());

		authorItem.setData(AUTHOR, author);
		authorItem.setData(AUTHOR);
	}

	/**
	 * Synchronizes the ranks with a modified authors groups file
	 * 
	 * Assumption: The authors groups member is pointing to the new authors groups and
	 * doesn't change
	 */
	private void synchronizeRanks() {
		TreeMap<Integer, Rank> existingRanks = new TreeMap<Integer, Rank>();

		// find all the ranks existing in the authors groups (not the tree)
		for (Rank rank : authHandler.getRanks()) {
			int id = rank.getId();
			existingRanks.put(id, rank);
		}

		// for each rank in the tree, find its status 
		// (belongs, doesn't belong)
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

		// add the ranks which weren't found in the tree
		// new ranks are expended by default
		for (Integer rankId : existingRanks.keySet()) {
			TreeItem rankItem = addTreeRank(rootItem, existingRanks.get(rankId));
			rankItem.setExpanded(true);
		}
	}
	
	/**
	 * Return the handler of the authors file
	 * @return
	 */
	public AuthorsHandler getAuthorsHandler() {
		if (authHandler != null) {
			return authHandler;
		}

		if (!(super.getEditorInput() instanceof FileEditorInput)) {
			return null;
		}

		try {
			FileEditorInput fileEditorInput = (FileEditorInput) super
					.getEditorInput();
			IFile file = fileEditorInput.getFile();

			ToK tok = ToK.getProjectToK(file.getProject());

			AuthorsHandler d = new AuthorsHandler(tok,tok.getAuthorFile().getLocation().toOSString());
			
			return d;
		} 
		catch (Exception e) {
		}
		
		return null;
	}

	/**
	 * Returns all the displayed ranks
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
	 *            the rank whose authors are to be returned
	 * @return the authors which are displayed under the given rank id
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
			return null; //rank id doesn't exist

		TreeItem[] aItems = rootItem.getItem(i).getItems();
		Author[] authors = new Author[aItems.length];

		for (int j = 0; j < authors.length; j++) {
			authors[j] = getAuthor(aItems[j]);
		}

		return authors;
	}

	/**
	 * Move authors tree item to default rank
	 * @param itemToMove
	 */
	public void moveAuthorToDefault(TreeItem itemToMove) {
		Author author = getAuthor(itemToMove);
		authHandler.relocateAuthor(author.getName(), AuthorsEditor.DEFAULT_RANK_ID);
	}

	/**
	 * Move authors tree item to rank
	 * @param authorToAdd
	 */
	public void moveAuthorToRank(TreeItem authorToAdd) {
		Rank rank = (Rank) authorToAdd.getParentItem().getData(RANK);
		Author author = getAuthor(authorToAdd);
		authHandler.relocateAuthor(author.getName(), rank.getId());
	}

	/**
	 * Add new rank to tree
	 * @param rankName
	 */
	public void addRank(String rankName) {
		addTreeRank(rootItem, new Rank(rankName, authHandler
				.getRanksId(rankName)));
	}

	/**
	 * Remove author tree item from file
	 * @param authorToRemove
	 */
	public void removeAuthorFromFile(TreeItem authorToRemove) {
		Author author = getAuthor(authorToRemove);
		authHandler.removeAuthor(author.getName());
	}
	
	/**
	 * This method allows us to update the displayed information, when the files
	 * on the disk change. It updates the treeItems to match the file's content
	 * Note: Additions to the xml file will be displayed after the existing
	 * entries
	 */
	@Override
	protected void handleEditorInputChanged() {

		authHandler = null; // lose the previous authors groups
		authHandler = getAuthorsHandler();// update all the data

		synchronizeRanks();
		// now the ranks should be synchronized with the file
//		synchronizeAuthors();
	}
}

