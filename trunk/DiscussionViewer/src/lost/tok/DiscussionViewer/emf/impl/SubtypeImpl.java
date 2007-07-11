/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import java.util.Collection;

import lost.tok.DiscussionViewer.emf.DiscussionViewerPackage;
import lost.tok.DiscussionViewer.emf.ModelObject;
import lost.tok.DiscussionViewer.emf.Subtype;
import lost.tok.DiscussionViewer.emf.SubtypeLink;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subtype</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.SubtypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.SubtypeImpl#getBase <em>Base</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.SubtypeImpl#getInstances <em>Instances</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.SubtypeImpl#getLinks <em>Links</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubtypeImpl extends EObjectImpl implements Subtype
{
  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = "AnonymousSubtype";

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getBase() <em>Base</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBase()
   * @generated
   * @ordered
   */
  protected ModelObject base = null;

  /**
   * The cached value of the '{@link #getInstances() <em>Instances</em>}' reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInstances()
   * @generated
   * @ordered
   */
  protected EList instances = null;

  /**
   * The cached value of the '{@link #getLinks() <em>Links</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getLinks()
   * @generated
   * @ordered
   */
  protected EList links = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected SubtypeImpl()
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
    return DiscussionViewerPackage.Literals.SUBTYPE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setName(String newName)
  {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DiscussionViewerPackage.SUBTYPE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelObject getBase()
  {
    if (base != null && base.eIsProxy())
    {
      InternalEObject oldBase = (InternalEObject)base;
      base = (ModelObject)eResolveProxy(oldBase);
      if (base != oldBase)
      {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiscussionViewerPackage.SUBTYPE__BASE, oldBase, base));
      }
    }
    return base;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ModelObject basicGetBase()
  {
    return base;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setBase(ModelObject newBase)
  {
    ModelObject oldBase = base;
    base = newBase;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DiscussionViewerPackage.SUBTYPE__BASE, oldBase, base));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getInstances()
  {
    if (instances == null)
    {
      instances = new EObjectResolvingEList(ModelObject.class, this, DiscussionViewerPackage.SUBTYPE__INSTANCES);
    }
    return instances;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getLinks()
  {
    if (links == null)
    {
      links = new EObjectContainmentEList(SubtypeLink.class, this, DiscussionViewerPackage.SUBTYPE__LINKS);
    }
    return links;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case DiscussionViewerPackage.SUBTYPE__LINKS:
        return ((InternalEList)getLinks()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
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
      case DiscussionViewerPackage.SUBTYPE__NAME:
        return getName();
      case DiscussionViewerPackage.SUBTYPE__BASE:
        if (resolve) return getBase();
        return basicGetBase();
      case DiscussionViewerPackage.SUBTYPE__INSTANCES:
        return getInstances();
      case DiscussionViewerPackage.SUBTYPE__LINKS:
        return getLinks();
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
      case DiscussionViewerPackage.SUBTYPE__NAME:
        setName((String)newValue);
        return;
      case DiscussionViewerPackage.SUBTYPE__BASE:
        setBase((ModelObject)newValue);
        return;
      case DiscussionViewerPackage.SUBTYPE__INSTANCES:
        getInstances().clear();
        getInstances().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.SUBTYPE__LINKS:
        getLinks().clear();
        getLinks().addAll((Collection)newValue);
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
      case DiscussionViewerPackage.SUBTYPE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case DiscussionViewerPackage.SUBTYPE__BASE:
        setBase((ModelObject)null);
        return;
      case DiscussionViewerPackage.SUBTYPE__INSTANCES:
        getInstances().clear();
        return;
      case DiscussionViewerPackage.SUBTYPE__LINKS:
        getLinks().clear();
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
      case DiscussionViewerPackage.SUBTYPE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case DiscussionViewerPackage.SUBTYPE__BASE:
        return base != null;
      case DiscussionViewerPackage.SUBTYPE__INSTANCES:
        return instances != null && !instances.isEmpty();
      case DiscussionViewerPackage.SUBTYPE__LINKS:
        return links != null && !links.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //SubtypeImpl