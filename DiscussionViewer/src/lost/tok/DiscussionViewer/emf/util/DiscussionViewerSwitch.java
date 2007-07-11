/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.util;

import java.util.List;

import lost.tok.DiscussionViewer.emf.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage
 * @generated
 */
public class DiscussionViewerSwitch
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static DiscussionViewerPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewerSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = DiscussionViewerPackage.eINSTANCE;
    }
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  public Object doSwitch(EObject theEObject)
  {
    return doSwitch(theEObject.eClass(), theEObject);
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  protected Object doSwitch(EClass theEClass, EObject theEObject)
  {
    if (theEClass.eContainer() == modelPackage)
    {
      return doSwitch(theEClass.getClassifierID(), theEObject);
    }
    else
    {
      List eSuperTypes = theEClass.getESuperTypes();
      return
        eSuperTypes.isEmpty() ?
          defaultCase(theEObject) :
          doSwitch((EClass)eSuperTypes.get(0), theEObject);
    }
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  protected Object doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case DiscussionViewerPackage.MEMENTO_VALUE:
      {
        MementoValue mementoValue = (MementoValue)theEObject;
        Object result = caseMementoValue(mementoValue);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.MEMENTO:
      {
        Memento memento = (Memento)theEObject;
        Object result = caseMemento(memento);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.SUBTYPE:
      {
        Subtype subtype = (Subtype)theEObject;
        Object result = caseSubtype(subtype);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.SUBTYPE_LINK:
      {
        SubtypeLink subtypeLink = (SubtypeLink)theEObject;
        Object result = caseSubtypeLink(subtypeLink);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.MODEL_OBJECT:
      {
        ModelObject modelObject = (ModelObject)theEObject;
        Object result = caseModelObject(modelObject);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT:
      {
        AbstractDicObject abstractDicObject = (AbstractDicObject)theEObject;
        Object result = caseAbstractDicObject(abstractDicObject);
        if (result == null) result = caseModelObject(abstractDicObject);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.OPINION:
      {
        Opinion opinion = (Opinion)theEObject;
        Object result = caseOpinion(opinion);
        if (result == null) result = caseAbstractDicObject(opinion);
        if (result == null) result = caseModelObject(opinion);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.QUOTE:
      {
        Quote quote = (Quote)theEObject;
        Object result = caseQuote(quote);
        if (result == null) result = caseAbstractDicObject(quote);
        if (result == null) result = caseModelObject(quote);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.DISCUSSION_VIEWER:
      {
        DiscussionViewer discussionViewer = (DiscussionViewer)theEObject;
        Object result = caseDiscussionViewer(discussionViewer);
        if (result == null) result = caseModelObject(discussionViewer);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.RELATION_CONNECTION:
      {
        RelationConnection relationConnection = (RelationConnection)theEObject;
        Object result = caseRelationConnection(relationConnection);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION:
      {
        ContainmentConnection containmentConnection = (ContainmentConnection)theEObject;
        Object result = caseContainmentConnection(containmentConnection);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case DiscussionViewerPackage.ROOT:
      {
        Root root = (Root)theEObject;
        Object result = caseRoot(root);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Memento Value</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Memento Value</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseMementoValue(MementoValue object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Memento</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Memento</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseMemento(Memento object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Subtype</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Subtype</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseSubtype(Subtype object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Subtype Link</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Subtype Link</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseSubtypeLink(SubtypeLink object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Model Object</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Model Object</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseModelObject(ModelObject object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Abstract Dic Object</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Abstract Dic Object</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseAbstractDicObject(AbstractDicObject object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Opinion</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Opinion</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseOpinion(Opinion object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Quote</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Quote</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseQuote(Quote object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Discussion Viewer</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Discussion Viewer</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseDiscussionViewer(DiscussionViewer object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Relation Connection</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Relation Connection</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseRelationConnection(RelationConnection object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Containment Connection</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Containment Connection</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseContainmentConnection(ContainmentConnection object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>Root</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>Root</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public Object caseRoot(Root object)
  {
    return null;
  }

  /**
   * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  public Object defaultCase(EObject object)
  {
    return null;
  }

} //DiscussionViewerSwitch
