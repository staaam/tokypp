package lost.tok.disEditor;

import java.util.Iterator;

import lost.tok.GeneralFunctions;

import org.dom4j.Document;
import org.dom4j.Element;
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
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;

public class DiscussionEditor extends TextEditor {

//	Tree disTree;

	public DiscussionEditor() {
	}
	
	public void createPartControl(Composite parent) 
	{
		final Composite par = parent;
		final Tree disTree = new Tree(parent,SWT.BORDER);		
		
		disTree.addListener(SWT.MouseDoubleClick, new Listener()
						{
							public void handleEvent(Event event) 
							{	
							    Shell s = new Shell(par.getDisplay());
							    GridLayout gl = new GridLayout();
							    gl.numColumns = 1;
							    s.setLayout(gl);						    
							    Composite gc = new Composite(s,SWT.BORDER);							
							    GridLayout gla = new GridLayout();
							    gla.numColumns = 1;
							    gc.setLayout(gla);
							    final Text t1 = new Text(gc, SWT.MULTI);
							    /////////////////////////////////////////////////////////
							    
							    Point point = new Point(event.x,event.y);
							    
//							    if(event.widget == null)
							    t1.setText("Item name = \n" + ((TreeItem)((Tree)event.widget).getItem(point)).getText() +
							    		"\n");
							    
							    
							    /////////////////////////////////////////////////////////
							    GridData gd = new GridData(GridData.FILL_BOTH);
							    gd.horizontalSpan = 1;
							    gc.setLayoutData(gd);
							    gd = new GridData();
							    
							    s.pack();
							    s.open();
							}
						}
		);
		
		//************************ DELETE QUOTES AND OPINIONS ***********************************
		disTree.addKeyListener(new KeyListener() 
			{
		      public void keyPressed(KeyEvent e) 
		      {
				    if(e.keyCode == SWT.DEL)
				    {   
				    	if(disTree.getSelection()[0].getData().equals("discussion") ||	
				    			(disTree.getSelection()[0].getData().equals("opinion") &&
				    					disTree.getSelection()[0].getText().equalsIgnoreCase("default")))
				    	{
				    		return;
				    	}
				    	if(disTree.getSelection()[0].getData().equals("opinion"))
				    	{
				    		TreeItem defOp = null;
				    		TreeItem itmArr[] = disTree.getItem(0).getItems();
				    		
				    		for (int i = 0; i < itmArr.length; i++) {
								if(itmArr[i].getText().equalsIgnoreCase("default"))
								{
									defOp = itmArr[i];
								}
							}
				    		
				    		TreeItem itemArr[] = disTree.getSelection()[0].getItems();
				    		
				    		for (int i = 0; i < itemArr.length; i++) 
				    		{				    			
				    			TreeItem tempItem = new TreeItem(defOp,SWT.NONE);
				    			tempItem.setText(itemArr[i].getText());
				    			tempItem.setData(itemArr[i].getData());
				    			tempItem.setImage(itemArr[i].getImage());
				    			
				    			itemArr[i].dispose();				    			 
							}
				    	}
				    	
				    	disTree.getSelection()[0].dispose();
				    }
		      }

		      public void keyReleased(KeyEvent e) 
		      {}
		    });
		//*********************************************************************************************
		
		
		//***************** DRAG AND DROP ******************************************************************
	    Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
	    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

	    final DragSource source = new DragSource(disTree, operations);
	    source.setTransfer(types);
	    final TreeItem[] dragSourceItem = new TreeItem[1];
	    source.addDragListener(new DragSourceListener() {  public void dragStart(DragSourceEvent event) {
	        TreeItem[] selection = disTree.getSelection();
	        if (selection.length > 0 && selection[0].getItemCount() == 0) {
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
	        if (event.detail == DND.DROP_MOVE)
	          dragSourceItem[0].dispose();
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
	          Point pt = par.getDisplay().map(null, disTree, event.x, event.y);
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
	    	if (((TreeItem)event.item).getData().equals("quote") ||
	    			((TreeItem)event.item).getData().equals("discussion")){
		       event.detail = DND.DROP_NONE;
		       return;
		    } 
	        if (event.data == null) {
	          event.detail = DND.DROP_NONE;
	          return;
	        }
	        String text = (String) event.data;
	        if (event.item == null) {
	          TreeItem item = new TreeItem(disTree, SWT.NONE);
	          item.setText(text);
	        } else {
	          TreeItem item = (TreeItem) event.item;
	          Point pt = par.getDisplay().map(null, disTree, event.x, event.y);
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
	              TreeItem newItem = new TreeItem(parent, SWT.NONE,
	                  index);
	              newItem.setText(text);
	            } else if (pt.y > bounds.y + 2 * bounds.height / 3) {
	              TreeItem newItem = new TreeItem(parent, SWT.NONE,
	                  index + 1);
	              newItem.setText(text);
	            } else {
	              TreeItem newItem = new TreeItem(item, SWT.NONE);
	              newItem.setText(text);
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
	              TreeItem newItem = new TreeItem(disTree, SWT.NONE,
	                  index);
	              newItem.setText(text);
	            } else if (pt.y > bounds.y + 2 * bounds.height / 3) {
	              TreeItem newItem = new TreeItem(disTree, SWT.NONE,
	                  index + 1);
	              newItem.setText(text);
	            } else {
	              TreeItem newItem = new TreeItem(item, SWT.NONE);
	              newItem.setText(text);
	            }
	          }

	        }
	      }
	    });
		//**************************************************************************************************
		
	    
	    //*************************** FROM XML TO TREE ****************************************************
		if (!(super.getEditorInput() instanceof FileEditorInput)) 
		{
			//TODO: error message
			return;
		}

		FileEditorInput fileEditorInput = (FileEditorInput) getEditorInput();
		IFile file = fileEditorInput.getFile();
		
		try
		{
			Document discussionDocumentObject = GeneralFunctions.readFromXML(file);
			
			Iterator opinionsIterator = discussionDocumentObject.getRootElement().elementIterator("opinion");

			TreeItem rootItem = new TreeItem(disTree,SWT.NONE);

			rootItem.setText(discussionDocumentObject.getRootElement().element("name").getText());
			rootItem.setData("discussion");
			
			//Image imageDisc = new Image(null, new FileInputStream("C:/discussion.gif"));
			
			//rootItem.setImage(imageDisc);
			
			while (opinionsIterator.hasNext()) 
			{
				Element opinionElement = (Element) opinionsIterator.next();
				TreeItem opinionItem = new TreeItem(rootItem,SWT.NONE);
				//Image imageOpin = new Image(null, new FileInputStream("C:/opinion.gif"));				
				
				opinionItem.setText(opinionElement.element("name").getText());
				opinionItem.setData("opinion");
				//opinionItem.setImage(imageOpin);
				
				for(int i=0; i< 4; i++)
				{				
					TreeItem quoteItem = new TreeItem(opinionItem,SWT.NONE);
					//Image imageQuote = new Image(null, new FileInputStream("C:/quote.gif"));
					
					quoteItem.setText(opinionElement.element("quote").element("text").getText() + i);	
					quoteItem.setData("quote");				
					//quoteItem.setImage(imageQuote);
				}
			}
		} 
		catch(Exception e)
		{
			TreeItem eItem = new TreeItem(disTree,SWT.SINGLE);
			eItem.setText(e.getMessage());
		}
		//*************************************************************************************************
	}
	
	public void dispose() {
		super.dispose();
	}

}
