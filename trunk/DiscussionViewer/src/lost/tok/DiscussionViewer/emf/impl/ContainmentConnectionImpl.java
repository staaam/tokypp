/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import lost.tok.DiscussionViewer.emf.ContainmentConnection;
import lost.tok.DiscussionViewer.emf.DiscussionViewerPackage;
import lost.tok.DiscussionViewer.emf.Opinion;
import lost.tok.DiscussionViewer.emf.Quote;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Containment Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.ContainmentConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.ContainmentConnectionImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContainmentConnectionImpl extends EObjectImpl implements ContainmentConnection
{
  /**
   * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSource()
   * @generated
   * @ordered
   */
  protected Opinion source = null;

  /**
   * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTarget()
   * @generated
   * @ordered
   */
  protected Quote target = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ContainmentConnectionImpl()
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
    return DiscussionViewerPackage.Literals.CONTAINMENT_CONNECTION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Opinion getSource()
  {
    if (source != null && source.eIsProxy())
    {
      InternalEObject oldSource = (InternalEObject)source;
      source = (Opinion)eResolveProxy(oldSource);
      if (source != oldSource)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiscussionViewerPackage.CONTAINMENT_CONNECTION__SOURCE, oldSource, source));
      }
    }
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Opinion basicGetSource()
  {
    return source;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setSource(Opinion newSource)
  {
    Opinion oldSource = source;
    source = newSource;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DiscussionViewerPackage.CONTAINMENT_CONNECTION__SOURCE, oldSource, source));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Quote getTarget()
  {
    if (target != null && target.eIsProxy())
    {
      InternalEObject oldTarget = (InternalEObject)target;
      target = (Quote)eResolveProxy(oldTarget);
      if (target != oldTarget)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiscussionViewerPackage.CONTAINMENT_CONNECTION__TARGET, oldTarget, target));
      }
    }
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Quote basicGetTarget()
  {
    return target;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTarget(Quote newTarget)
  {
    Quote oldTarget = target;
    target = newTarget;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DiscussionViewerPackage.CONTAINMENT_CONNECTION__TARGET, oldTarget, target));
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
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__SOURCE:
        if (resolve) return getSource();
        return basicGetSource();
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__TARGET:
        if (resolve) return getTarget();
        return basicGetTarget();
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
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__SOURCE:
        setSource((Opinion)newValue);
        return;
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__TARGET:
        setTarget((Quote)newValue);
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
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__SOURCE:
        setSource((Opinion)null);
        return;
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__TARGET:
        setTarget((Quote)null);
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
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__SOURCE:
        return source != null;
      case DiscussionViewerPackage.CONTAINMENT_CONNECTION__TARGET:
        return target != null;
    }
    return super.eIsSet(featureID);
  }

} //ContainmentConnectionImpl