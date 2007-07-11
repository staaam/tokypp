
package lost.tok.DiscussionViewer;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.gems.designer.model.BasicConnectionType;
import org.gems.designer.model.Wire;
import org.gems.designer.model.AttributeValue;
import java.util.Vector;
import lost.tok.DiscussionViewer.emf.*;


public class RelationConnectionType extends BasicConnectionType {
    public static final String NAME = "Relation";
    public static final RelationConnectionType INSTANCE = 
        new RelationConnectionType();
    
    

    public static final IPropertyDescriptor[] RELATION_ATTRIBUTE_DESCRIPTORS =
    {
    };
    public static final Object[] RELATION_DEFAULT_ATTRIBUTE_VALUES = {
       
    };
    
      	 

    
    protected void getPropertyDescriptors(Vector desc){
    	desc.addAll(java.util.Arrays.asList(RELATION_ATTRIBUTE_DESCRIPTORS));
    	super.getPropertyDescriptors(desc);
    }
    
   
   
    
    private RelationConnectionType() {
        super(NAME,AbstractDicObject.class,AbstractDicObject.class,"Relates1","Relates2");
        registerType(DiscussionViewerProvider.MODEL_ID,this);
    }
    
    protected RelationConnectionType(String type) {
        super(type);
        registerType(DiscussionViewerProvider.MODEL_ID,this);
    }
    
    public String getModelID() {
        return DiscussionViewerProvider.MODEL_ID;
    }
    
    public void installAttributes(Wire wire) {
    	EMFRelationProxy proxy = new EMFRelationProxy();
		installAttributes(wire,proxy);
    }
    
     public void installAttributes(Wire wire, EMFRelationProxy proxy) {
        AttributeValue[] attributes = proxy.getAttributeValues();
        wire.installAttribute(NAME,attributes[0]);
        for(int i = 0; i < RELATION_ATTRIBUTE_DESCRIPTORS.length; i++) {
            wire.installAttribute((String)RELATION_ATTRIBUTE_DESCRIPTORS[i].getId(),attributes[i+1]);
        }
        super.installAttributes(wire);
    }
   

}

 
       
       
   