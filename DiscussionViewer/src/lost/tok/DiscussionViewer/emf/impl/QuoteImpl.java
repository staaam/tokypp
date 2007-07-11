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
 * An implementation of the model object '<em><b>Quote</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.QuoteImpl#getManyQuotes <em>Many Quotes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QuoteImpl extends AbstractDicObjectImpl implements Quote
{
  /**
   * The cached value of the '{@link #getManyQuotes() <em>Many Quotes</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getManyQuotes()
   * @generated
   * @ordered
   */
  protected EList manyQuotes = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected QuoteImpl()
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
    return DiscussionViewerPackage.Literals.QUOTE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getManyQuotes()
  {
    if (manyQuotes == null)
    {
      manyQuotes = new EObjectResolvingEList(Opinion.class, this, DiscussionViewerPackage.QUOTE__MANY_QUOTES);
    }
    return manyQuotes;
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
      case DiscussionViewerPackage.QUOTE__MANY_QUOTES:
        return getManyQuotes();
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
      case DiscussionViewerPackage.QUOTE__MANY_QUOTES:
        getManyQuotes().clear();
        getManyQuotes().addAll((Collection)newValue);
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
      case DiscussionViewerPackage.QUOTE__MANY_QUOTES:
        getManyQuotes().clear();
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
      case DiscussionViewerPackage.QUOTE__MANY_QUOTES:
        return manyQuotes != null && !manyQuotes.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //QuoteImpl