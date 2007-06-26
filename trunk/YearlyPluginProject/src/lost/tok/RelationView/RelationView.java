package lost.tok.RelationView;

import java.util.List;

import lost.tok.Discussion;
import lost.tok.GeneralFunctions;
import lost.tok.Relation;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class RelationView extends ViewPart {
	
	public final static String ID = "lost.tok.RelationView.RelationView"; //$NON-NLS-1$
	
	public class RelationContentProvider implements IStructuredContentProvider {

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				List elements = (List) inputElement;
				return elements.toArray(new Object[elements.size()]);
			}
			return new Object[0];
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public class RelationLabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}

		public String getColumnText(Object element, int columnIndex) {
			String result = "";
	        Relation rel = (Relation) element;
	        switch (columnIndex) {
	            case 0:
	                result = rel.getFirstName();
	                break;
	            case 1:
	                result = rel.getSecondName();
	                break;
	            case 2:
	                result = rel.getRelationType();
	                break;
	            case 3:
	                result = rel.getComment();
	                break;
	        }
	        return result;
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

	}

	private static final String[] ColumnNames = 
		{"Discussion Entity",
		 "Discussion Entity",
		 "Relation Type",
		 "Comment"};
	private TableViewer tableViewer;
	
	@Override
	public void createPartControl(Composite parent) {
		tableViewer = new TableViewer(parent);
		tableViewer.setContentProvider(new RelationContentProvider());
		tableViewer.setLabelProvider(new RelationLabelProvider());
		Table table = tableViewer.getTable();
		for (String columnName : ColumnNames) {
			TableColumn tableColumn = new TableColumn(table, SWT.NONE);
			tableColumn.setText(columnName);
			tableColumn.setWidth(150);
		}
		table.setHeaderVisible(true);
		//tableViewer.setColumnProperties(ColumnNames);
		tableViewer.refresh();
	}

	@Override
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	public void update(Discussion d) {
		tableViewer.setInput(d.getRelations());
	}

	public static RelationView getView(boolean bringToTop) {
		return (RelationView) GeneralFunctions.getView(RelationView.ID, bringToTop);
	}

}
