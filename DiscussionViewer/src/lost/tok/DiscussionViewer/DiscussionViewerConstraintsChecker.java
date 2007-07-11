/*******************************************************************************
* Copyright (c) 2005 Jules White. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Common Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/cpl-v10.html
* 
* Contributors: Jules White - initial API and implementation
******************************************************************************/

package lost.tok.DiscussionViewer;

import org.gems.designer.model.AbstractConstraintsChecker;

import org.gems.designer.model.Container;
import org.gems.designer.model.ModelObject;
import org.gems.designer.model.ModelUtilities;
import org.gems.designer.model.Model;
import org.gems.designer.model.event.ModelChangeEvent;
import org.gems.designer.model.ExecutableConstraint;
import org.gems.designer.model.ExecutableEventConstraint;
import org.gems.designer.model.Root;
import org.gems.designer.metamodel.ConstraintMemento;
import org.gems.designer.model.actions.EventInterestFactory;
import org.gems.designer.model.actions.EventInterestFactoryRepository;
import org.gems.designer.model.actions.PersistentModelEventInterest;

public class DiscussionViewerConstraintsChecker extends AbstractConstraintsChecker {

    /**
     * 
     */
    public DiscussionViewerConstraintsChecker() {
        super();
    }
    
    public void createConstraints() {
        addConnectionConstraint(AbstractDicObject.class,
                                AbstractDicObject.class,
                                0,
                                2147483647,
                                0,
                                2147483647,
                                RelationConnectionType.INSTANCE);
       
        addConnectionConstraint(Opinion.class,
                                Quote.class,
                                0,
                                2147483647,
                                0,
                                2147483647,
                                ContainmentConnectionType.INSTANCE);
       
        addConnectionConstraint(Opinion.class,
                                Quote.class,
                                0,
                                2147483647,
                                0,
                                2147483647,
                                ContainmentConnectionType.INSTANCE);
       
       
        addContainmentConstraint(DiscussionViewer.class,
                                 Opinion.class,
                                 0,
                                 2147483647);
        addContainmentConstraint(DiscussionViewer.class,
                                 Quote.class,
                                 0,
                                 2147483647);
        addContainmentConstraint(DiscussionViewer.class,
                                 Opinion.class,
                                 0,
                                 2147483647);
        addContainmentConstraint(DiscussionViewer.class,
                                 Quote.class,
                                 0,
                                 2147483647);
        addContainmentConstraint(Root.class,
                                 Opinion.class,
                                 0,
                                 Integer.MAX_VALUE);
        addContainmentConstraint(Root.class,
                                 Quote.class,
                                 0,
                                 Integer.MAX_VALUE);
        
    }
    
    public java.util.List<org.gems.designer.Memento> getConstraintMementos(){
        java.util.LinkedList<org.gems.designer.Memento> mems = new java.util.LinkedList<org.gems.designer.Memento>();
    	 
        return mems;
    }


}
