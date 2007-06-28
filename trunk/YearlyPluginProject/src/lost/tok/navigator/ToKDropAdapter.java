package lost.tok.navigator;

import lost.tok.GeneralFunctions;

import org.dom4j.Document;
import org.dom4j.Element;
import org.eclipse.core.internal.resources.Folder;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.views.navigator.NavigatorDropAdapter;

public class ToKDropAdapter extends NavigatorDropAdapter{

	public ToKDropAdapter(StructuredViewer viewer) {
		super(viewer);
	}
	
	public boolean performDrop(final Object data) {
		Boolean temp = super.performDrop(data);
		
		String st;
		String name;
		
		Class<? extends Object> c = data.getClass();
		if (c == TreeSelection.class){
			st = data.toString();
			name = st.substring(st.lastIndexOf('/') + 1, st.length() - 1);
		}
		else {
			String[] da=(String[]) data;
			st = da[0];
			name = st.substring(st.lastIndexOf('\\') + 1);
		}
		Folder f = (Folder) (this.getCurrentTarget());
		
		IFile file = f.getFile("order.xml");
		
		Document d = GeneralFunctions.readFromXML(file);
		
		Element e = d.getRootElement().addElement("sub");
		e.addElement("name").addText(name);
		e.addElement("type").addText("file");
		
		GeneralFunctions.writeToXml(file, d);
		
		return temp;
	}
	

}
