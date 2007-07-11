/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import java.util.Collection;

import lost.tok.DiscussionViewer.emf.DiscussionViewerPackage;
import lost.tok.DiscussionViewer.emf.Opinion;
import lost.tok.DiscussionViewer.emf.Quote;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Opinion</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.OpinionImpl#getOneOpinion <em>One Opinion</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OpinionImpl extends AbstractDicObjectImpl implements Opinion
{
  /**
   * The cached value of the '{@link #getOneOpinion() <em>One Opinion</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOneOpinion()
   * @generated
   * @ordered
   */
  protected EList oneOpinion = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected OpinionImpl()
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
    return DiscussionViewerPackage.Literals.OPINION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getOneOpinion()
  {
    if (oneOpinion == null)
    {
      oneOpinion = new EObjectResolvingEList(Quote.class, this, DiscussionViewerPackage.OPINION__ONE_OPINION);
    }
    return oneOpinion;
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
      case DiscussionViewerPackage.OPINION__ONE_OPINION:
        return getOneOpinion();
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
      case DiscussionViewerPackage.OPINION__ONE_OPINION:
        getOneOpinion().clear();
        getOneOpinion().addAll((Collection)newValue);
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
      case DiscussionViewerPackage.OPINION__ONE_OPINION:
        getOneOpinion().clear();
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
      case DiscussionViewerPackage.OPINION__ONE_OPINION:
        return oneOpinion != null && !oneOpinion.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //OpinionImpl