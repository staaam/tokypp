package lost.tok.RelationView;

import java.util.ArrayList;
import java.util.List;

import lost.tok.Discussion;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class RelationView extends ViewPart {
	
	public final static String ID = "lost.tok.RelationView.RelationView"; //$NON-NLS-1$
	
	public class Relation {
		public String getFirstName(){
			return "Arie";
		}
		
		public String getSecondName(){
			return "Also Arie";
		}
		
		public String getrelationType(){
			return Discussion.relDisplayNames[0];
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
	            case 0:  // COMPLETED_COLUMN
	                break;
	            case 1 :
	                result = rel.getFirstName();
	                break;
	            case 2 :
	                result = rel.getSecondName();
	                break;
	            case 3 :
	                result = rel.getrelationType();
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



	public class RelationContentProvider implements IContentProvider {

		public void dispose() {
			// TODO Auto-generated method stub

		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// TODO Auto-generated method stub

		}

	}



	private static final String[] ColumnNames = {"Discussion Entity","Discussion Entity","Relation Type"};
	private TableViewer table;
	private List<Relation> relationList = new ArrayList<Relation>();

	
	@Override
	public void createPartControl(Composite parent) {
		relationList.add(new Relation());
		createTableViewer(parent);
	}

	

	private void createTableViewer(Composite parent) {
		// TODO Auto-generated method stub
		table = new TableViewer(parent);
		table.setContentProvider(new RelationContentProvider());
		table.setLabelProvider(new RelationLabelProvider());
		table.setInput(relationList);
		table.setColumnProperties(ColumnNames);
		table.refresh();
	}



	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

}
