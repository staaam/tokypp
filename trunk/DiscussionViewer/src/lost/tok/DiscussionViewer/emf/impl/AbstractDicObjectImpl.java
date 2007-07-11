/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import java.util.Collection;

import lost.tok.DiscussionViewer.emf.AbstractDicObject;
import lost.tok.DiscussionViewer.emf.DiscussionViewerPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Dic Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.AbstractDicObjectImpl#getRelates1 <em>Relates1</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.AbstractDicObjectImpl#getRelates2 <em>Relates2</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AbstractDicObjectImpl extends ModelObjectImpl implements AbstractDicObject
{
  /**
   * The cached value of the '{@link #getRelates1() <em>Relates1</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelates1()
   * @generated
   * @ordered
   */
  protected EList relates1 = null;

  /**
   * The cached value of the '{@link #getRelates2() <em>Relates2</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelates2()
   * @generated
   * @ordered
   */
  protected EList relates2 = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected AbstractDicObjectImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected EClass eStaticClass()
  {
    return DiscussionViewerPackage.Literals.ABSTRACT_DIC_OBJECT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getRelates1()
  {
    if (relates1 == null)
    {
      relates1 = new EObjectResolvingEList(AbstractDicObject.class, this, DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES1);
    }
    return relates1;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getRelates2()
  {
    if (relates2 == null)
    {
      relates2 = new EObjectResolvingEList(AbstractDicObject.class, this, DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES2);
    }
    return relates2;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES1:
        return getRelates1();
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES2:
        return getRelates2();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES1:
        getRelates1().clear();
        getRelates1().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES2:
        getRelates2().clear();
        getRelates2().addAll((Collection)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES1:
        getRelates1().clear();
        return;
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES2:
        getRelates2().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES1:
        return relates1 != null && !relates1.isEmpty();
      case DiscussionViewerPackage.ABSTRACT_DIC_OBJECT__RELATES2:
        return relates2 != null && !relates2.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //AbstractDicObjectImpl