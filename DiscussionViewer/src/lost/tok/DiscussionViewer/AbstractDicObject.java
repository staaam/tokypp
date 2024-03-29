

/*
 * Created on Fri Jun 22 20:03:23 IDT 2007
 *
 * Generated by GEMS 
 */
 
package lost.tok.DiscussionViewer;


/*******************************************************************************
* Copyright (c) 2005 Jules White. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Common Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/cpl-v10.html
* 
* Contributors: Jules White - initial API and implementation
******************************************************************************/

import org.gems.designer.metamodel.gen.AttributeInfo;
import org.gems.designer.model.AttributeValidator;
import org.gems.designer.ModelProvider;
import org.gems.designer.ModelRepository;
import org.gems.designer.model.Visitor;
import org.gems.designer.model.ConnectionType;
import org.gems.designer.model.Wire;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

import java.util.Vector;
import org.gems.designer.model.LogicElement;
import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.gems.designer.model.ModelUtilities;
import org.eclipse.emf.ecore.EObject;
import lost.tok.DiscussionViewer.emf.*;

public class AbstractDicObject extends org.gems.designer.model.LinkedModel  implements Adapter, EMFModelObject, org.gems.designer.model.EMFModelElement{

	private lost.tok.DiscussionViewer.emf.AbstractDicObject model_;
	private Notifier target_;
	
	public AbstractDicObject(){		
	}
	
	public AbstractDicObject(lost.tok.DiscussionViewer.emf.AbstractDicObject model){
		model_ = model;
		super.setName(model_.getName());
		super.setID(model_.getId());
    	model_.eAdapters().add(this);
	}
	
	public lost.tok.DiscussionViewer.emf.AbstractDicObject getModel(){
		if(model_ == null){
			model_ = lost.tok.DiscussionViewer.emf.impl.DiscussionViewerFactoryImpl.eINSTANCE.createAbstractDicObject();
			super.setName(model_.getName());
			model_.eAdapters().add(this);
		}
		return model_;
	}
	
	public lost.tok.DiscussionViewer.emf.AbstractDicObject getExtendedModel(){
		return getModel();
	}
	
	public lost.tok.DiscussionViewer.emf.AbstractDicObject getEMFObject(){
		return getModel();
	}
	
       
    public String getName(){
    	return getExtendedModel().getName();
    }
    
    public boolean isVisible(){
    	return getExtendedModel().isVisible();
    }
    
    
    public void setVisible(boolean b){
    	getExtendedModel().setVisible(b);
    	super.setVisible(b);
    }
    
    public void setName(String name){
    	getExtendedModel().setName(name);
    	super.setName(name);
    }
    
    public String getID(){
    	return getExtendedModel().getId();
    }
    
    public boolean isSubtype(){
    	return getExtendedModel().isSubtype();
    }
    
    public void setSubtype(boolean b){
    	getExtendedModel().setSubtype(b);
    	super.setSubtype(b);
    }
    
    public void setID(String id){
    	getExtendedModel().setId(id);
    	super.setID(id);
    }
    
    public Point getLocation(){
    	return new Point(getExtendedModel().getX(),getExtendedModel().getY());
    }
    
    public void setLocation(Point p) {
    	getExtendedModel().setX(p.x);
    	getExtendedModel().setY(p.y);
        super.setLocation(p);
    }
    
    public Dimension getSize() {
        return new Dimension(getExtendedModel().getWidth(),getExtendedModel().getHeight());
    }
    
    public org.gems.designer.Subtype createSubtype(){
    	return new EMFSubtypeImpl(this,getName());
    }
    
    public void setSize(Dimension d){
    	getExtendedModel().setWidth(d.width);
    	getExtendedModel().setHeight(d.height);
    	super.setSize(d);
    }
    
    public EObject getEMFModelElement(){
    	return getEMFObject();
    }
    
    public String getModelLinkTarget(){
    	return getExtendedModel().getModelLinkTarget();
    }
    
    public void setModelLinkTarget(String target){
    	getExtendedModel().setModelLinkTarget(target);
 		super.setModelLinkTarget(target);
    }
    	
    	public Notifier getTarget() {
    		return target_;
    	}
    	
    	public void setTarget(Notifier newTarget) {
    		target_ = newTarget;
    	}
    	
    	public boolean isAdapterForType(Object type) {
    		return getModel() == type;
    	}
    
    
    

    public static final IPropertyDescriptor[] ABSTRACTDICOBJECT_ATTRIBUTE_DESCRIPTORS =
    {
    };
    public static final Object[] ABSTRACTDICOBJECT_DEFAULT_ATTRIBUTE_VALUES = {
       
    };
    
      	 

    
    protected void getPropertyDescriptors(Vector desc){
    	desc.addAll(java.util.Arrays.asList(ABSTRACTDICOBJECT_ATTRIBUTE_DESCRIPTORS));
    	super.getPropertyDescriptors(desc);
    }
    
   
    	
    public String findAttributeType(String attr){
      return super.findAttributeType(attr);
    }
    public Object getAttribute(String attr){
    	
     return super.getAttribute(attr);
   }
  
   
    public void setAttribute(String attr, Object val){
         if(attr != null){
           AttributeValidator validator = AttributeValidators.getInstance().getValidator(attr);
           if(validator != null &&
              !validator.validValue(this,attr,val)){
                return;
              }
         }	
         if(attr.equals(org.gems.designer.model.Atom.NAME)){
        	 getModel().setName((String)val);
         }   													
        super.setAttribute(attr,val);
   }
   
   public void notifyChanged(Notification notification) {
   		int type = notification.getEventType();
   		int featureId = notification.getFeatureID(DiscussionViewerPackage.class);
    		
   		switch(type) {
   		case Notification.SET:
   		
   		case Notification.ADD:
   		case Notification.REMOVE:
   		}
   }
    

	
	public List<AbstractDicObject> getRelates1(){
		return (List<AbstractDicObject>)ModelUtilities.getConnectedTargets(this,AbstractDicObject.class,RelationConnectionType.INSTANCE);
	}
	
	
		
	public List<AbstractDicObject> getRelates2(){
		return (List<AbstractDicObject>)ModelUtilities.getConnectedSources(this,AbstractDicObject.class,RelationConnectionType.INSTANCE);
	}
	

@Override
public void connectOutput(Wire w) {
	
	ConnectionType type = w.getConnectionType();
	if(type != null){
	
	
		if(type.getName().equals(RelationConnectionType.NAME)){
	
					getModel().getRelates1().add(((EMFModelObject)w.getTarget()).getEMFObject());
				
      }
	
	}
	super.connectOutput(w);
}

@Override
public void connectInput(Wire w) {

	ConnectionType type = w.getConnectionType();
	if(type != null){
	
	
		if(type.getName().equals(RelationConnectionType.NAME)){
	
					getModel().getRelates2().add(((EMFModelObject)w.getSource()).getEMFObject());
				
    }
	
	}
	super.connectInput(w);
}

@Override
public void disconnectInput(Wire w) {
	
	ConnectionType type = w.getConnectionType();
	if(type != null){
	
	
		if(type.getName().equals(RelationConnectionType.NAME)){
	
					getModel().getRelates2().remove(((EMFModelObject)w.getSource()).getEMFObject());
				
    }
	
	}
	super.disconnectInput(w);
}


@Override
public void disconnectOutput(Wire w) {
	
	ConnectionType type = w.getConnectionType();
	if(type != null){
	
		if(type.getName().equals(RelationConnectionType.NAME)){
		    ((lost.tok.DiscussionViewer.Root)ModelRepository.getInstance().getInstanceRepository().getInstance(getModelInstanceID()).getRoot()).removeConnection(w);
			RelationConnection con = (RelationConnection)w.getAttribute(RelationConnectionType.NAME);
	
					getModel().getRelates1().remove(((EMFModelObject)w.getTarget()).getEMFObject());
				
    }
	
	}
	super.disconnectOutput(w);
}


public void connectInput(Wire w, boolean modifymodel) {
	if(!modifymodel)
		super.connectInput(w);
	else
		connectInput(w);
}

public void disconnectInput(Wire w, boolean modifymodel) {
	if(!modifymodel)
		super.disconnectInput(w);
	else
		disconnectInput(w);
}



public void connectOutput(Wire w, boolean modifymodel) {
	if(!modifymodel)
		super.connectOutput(w);
	else
		connectOutput(w);
}

public void disconnectOutput(Wire w, boolean modifymodel) {
	if(!modifymodel)
		super.disconnectOutput(w);
	else
		disconnectOutput(w);
}



    
    
    public String getConnectionRole(ConnectionType ct, boolean src){
		
	    if(ct instanceof RelationConnectionType && src){
	       return "Relates1";
	    }
	
	    if(ct instanceof RelationConnectionType && !src){
	       return "Relates2";
	    }
	
	  return super.getConnectionRole(ct,src);
	}
	
	public List<String> getRelationshipRoles(){
    	java.util.List<String> roles = super.getRelationshipRoles();
    		
	   roles.add("Relates1");
	
	    roles.add("Relates2");
	
    	return roles;
    }
	
	
	public ConnectionType getConnectionTypeForRole(String role){
		
	    if("Relates1".equalsIgnoreCase(role)){
	       return RelationConnectionType.INSTANCE;
	    }
	
	    if("Relates2".equalsIgnoreCase(role)){
	       return RelationConnectionType.INSTANCE;
	    }
	
	  return super.getConnectionTypeForRole(role);
	}
	
    public Object getRoleValue(String role){
		
	    if("Relates1".equalsIgnoreCase(role)){
	       return getRelates1();
	    }
	
	    if("Relates2".equalsIgnoreCase(role)){
	       return getRelates2();
	    }
	
	
	
	  return super.getRoleValue(role);
	}
	
	
    
    public boolean isAbstract(){return true;}
     
    public ModelProvider getModelProvider() {
        ModelProvider provider = super.getModelProvider();
        if(provider == null){
        	provider = new DiscussionViewerProvider();
        	ModelRepository.getInstance().registerModelProvider(provider);
        }
        return provider;
    }
    
    protected void buildContainmentNames() {
    }
    
    	public List<String> getContainmentRoles(){
    	List<String> roles = super.getContainmentRoles();
    	
    	return roles;
    }
    
    public Class getParentForRole(String role){
    	
    	return super.getParentForRole(role);
    }
    
    public String getRoleForParent(Class c){
    	
    	return super.getRoleForParent(c);
    }	
    
public void accept(Visitor visitor) {
   if(visitor instanceof DiscussionViewerVisitor)
   	((DiscussionViewerVisitor)visitor).visitAbstractDicObject(this);
   else
     super.accept(visitor);
}
   
    
    
    
    public boolean isExpanded(){
    	return getExtendedModel().isExpanded();
    }
    
    public void setExpanded(boolean ex){
    	getExtendedModel().setExpanded(ex);
    	super.setExpanded(ex);
    }
    
    public Dimension getExpandSize() {
        return new Dimension(getModel().getExpandedWidth(),getModel().getExpandedHeight());
    }
    
    public void setExpandSize(Dimension d){
    	getExtendedModel().setExpandedWidth(d.width);
    	getExtendedModel().setExpandedHeight(d.height);
    	super.setExpandSize(d);
    }
    
    public String getModelID() {
        return DiscussionViewerProvider.MODEL_ID;
    }
    

}

