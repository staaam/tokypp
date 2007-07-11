
package lost.tok.DiscussionViewer;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.gems.designer.model.BasicConnectionType;
import org.gems.designer.model.Wire;
import org.gems.designer.model.AttributeValue;
import java.util.Vector;
import lost.tok.DiscussionViewer.emf.*;


public class ContainmentConnectionType extends BasicConnectionType {
    public static final String NAME = "Containment";
    public static final ContainmentConnectionType INSTANCE = 
        new ContainmentConnectionType();
    
    

    public static final IPropertyDescriptor[] CONTAINMENT_ATTRIBUTE_DESCRIPTORS =
    {
    };
    public static final Object[] CONTAINMENT_DEFAULT_ATTRIBUTE_VALUES = {
       
    };
    
      	 

    
    protected void getPropertyDescriptors(Vector desc){
    	desc.addAll(java.util.Arrays.asList(CONTAINMENT_ATTRIBUTE_DESCRIPTORS));
    	super.getPropertyDescriptors(desc);
    }
    
   
   
    
    private ContainmentConnectionType() {
        super(NAME,Opinion.class,Quote.class,"oneOpinion","manyQuotes");
        registerType(DiscussionViewerProvider.MODEL_ID,this);
    }
    
    protected ContainmentConnectionType(String type) {
        super(type);
        registerType(DiscussionViewerProvider.MODEL_ID,this);
    }
    
    public String getModelID() {
        return DiscussionViewerProvider.MODEL_ID;
    }
    
    public void installAttributes(Wire wire) {
    	EMFContainmentProxy proxy = new EMFContainmentProxy();
		installAttributes(wire,proxy);
    }
    
     public void installAttributes(Wire wire, EMFContainmentProxy proxy) {
        AttributeValue[] attributes = proxy.getAttributeValues();
        wire.installAttribute(NAME,attributes[0]);
        for(int i = 0; i < CONTAINMENT_ATTRIBUTE_DESCRIPTORS.length; i++) {
            wire.installAttribute((String)CONTAINMENT_ATTRIBUTE_DESCRIPTORS[i].getId(),attributes[i+1]);
        }
        super.installAttributes(wire);
    }
   

}

 
       
       
   