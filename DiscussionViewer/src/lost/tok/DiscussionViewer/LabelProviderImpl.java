

/*
 * Created on Fri Jun 22 20:03:23 IDT 2007
 *
 * Generated by GEMS 
 */
 
package lost.tok.DiscussionViewer;

import org.gems.designer.LabelProvider;


public class LabelProviderImpl implements LabelProvider {

	/**
	 * 
	 */
	public LabelProviderImpl() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see org.gems.designer.LabelProvider#getLabel(java.lang.Object)
	 */
	public String getLabel(Object key) {
		String name = key.getClass().getName();
		return key.getClass().getName().substring(name.lastIndexOf(".")+1);
	}

	/* (non-Javadoc)
	 * @see org.gems.designer.LabelProvider#getDescription(java.lang.Object)
	 */
	public String getDescription(Object key) {
		// TODO Auto-generated method stub
		return "";
	}

}
