/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.util;

import lost.tok.DiscussionViewer.emf.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage
 * @generated
 */
public class DiscussionViewerAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static DiscussionViewerPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewerAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = DiscussionViewerPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch the delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected DiscussionViewerSwitch modelSwitch =
    new DiscussionViewerSwitch()
    {
      public Object caseMementoValue(MementoValue object)
      {
        return createMementoValueAdapter();
      }
      public Object caseMemento(Memento object)
      {
        return createMementoAdapter();
      }
      public Object caseSubtype(Subtype object)
      {
        return createSubtypeAdapter();
      }
      public Object caseSubtypeLink(SubtypeLink object)
      {
        return createSubtypeLinkAdapter();
      }
      public Object caseModelObject(ModelObject object)
      {
        return createModelObjectAdapter();
      }
      public Object caseAbstractDicObject(AbstractDicObject object)
      {
        return createAbstractDicObjectAdapter();
      }
      public Object caseOpinion(Opinion object)
      {
        return createOpinionAdapter();
      }
      public Object caseQuote(Quote object)
      {
        return createQuoteAdapter();
      }
      public Object caseDiscussionViewer(DiscussionViewer object)
      {
        return createDiscussionViewerAdapter();
      }
      public Object caseRelationConnection(RelationConnection object)
      {
        return createRelationConnectionAdapter();
      }
      public Object caseContainmentConnection(ContainmentConnection object)
      {
        return createContainmentConnectionAdapter();
      }
      public Object caseRoot(Root object)
      {
        return createRootAdapter();
      }
      public Object defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  public Adapter createAdapter(Notifier target)
  {
    return (Adapter)modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.MementoValue <em>Memento Value</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.MementoValue
   * @generated
   */
  public Adapter createMementoValueAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.Memento <em>Memento</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.Memento
   * @generated
   */
  public Adapter createMementoAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.Subtype <em>Subtype</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.Subtype
   * @generated
   */
  public Adapter createSubtypeAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.SubtypeLink <em>Subtype Link</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.SubtypeLink
   * @generated
   */
  public Adapter createSubtypeLinkAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.ModelObject <em>Model Object</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.ModelObject
   * @generated
   */
  public Adapter createModelObjectAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.AbstractDicObject <em>Abstract Dic Object</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.AbstractDicObject
   * @generated
   */
  public Adapter createAbstractDicObjectAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.Opinion <em>Opinion</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.Opinion
   * @generated
   */
  public Adapter createOpinionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.Quote <em>Quote</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.Quote
   * @generated
   */
  public Adapter createQuoteAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.DiscussionViewer <em>Discussion Viewer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewer
   * @generated
   */
  public Adapter createDiscussionViewerAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.RelationConnection <em>Relation Connection</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.RelationConnection
   * @generated
   */
  public Adapter createRelationConnectionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.ContainmentConnection <em>Containment Connection</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.ContainmentConnection
   * @generated
   */
  public Adapter createContainmentConnectionAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link lost.tok.DiscussionViewer.emf.Root <em>Root</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see lost.tok.DiscussionViewer.emf.Root
   * @generated
   */
  public Adapter createRootAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //DiscussionViewerAdapterFactory
