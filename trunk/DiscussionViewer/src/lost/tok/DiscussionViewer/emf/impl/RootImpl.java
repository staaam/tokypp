/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import java.util.Collection;

import lost.tok.DiscussionViewer.emf.AbstractDicObject;
import lost.tok.DiscussionViewer.emf.ContainmentConnection;
import lost.tok.DiscussionViewer.emf.DiscussionViewer;
import lost.tok.DiscussionViewer.emf.DiscussionViewerPackage;
import lost.tok.DiscussionViewer.emf.Memento;
import lost.tok.DiscussionViewer.emf.Opinion;
import lost.tok.DiscussionViewer.emf.Quote;
import lost.tok.DiscussionViewer.emf.RelationConnection;
import lost.tok.DiscussionViewer.emf.Root;
import lost.tok.DiscussionViewer.emf.Subtype;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getMementos <em>Mementos</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getSubtypes <em>Subtypes</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getRealRoot <em>Real Root</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getAbstractDicObject <em>Abstract Dic Object</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getOpinion <em>Opinion</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getQuote <em>Quote</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getDiscussionViewer <em>Discussion Viewer</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getRelationConnection <em>Relation Connection</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.impl.RootImpl#getContainmentConnection <em>Containment Connection</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RootImpl extends EObjectImpl implements Root
{
  /**
   * The cached value of the '{@link #getMementos() <em>Mementos</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getMementos()
   * @generated
   * @ordered
   */
  protected EList mementos = null;

  /**
   * The cached value of the '{@link #getSubtypes() <em>Subtypes</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getSubtypes()
   * @generated
   * @ordered
   */
  protected EList subtypes = null;

  /**
   * The cached value of the '{@link #getRealRoot() <em>Real Root</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRealRoot()
   * @generated
   * @ordered
   */
  protected DiscussionViewer realRoot = null;

  /**
   * The cached value of the '{@link #getAbstractDicObject() <em>Abstract Dic Object</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAbstractDicObject()
   * @generated
   * @ordered
   */
  protected EList abstractDicObject = null;

  /**
   * The cached value of the '{@link #getOpinion() <em>Opinion</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOpinion()
   * @generated
   * @ordered
   */
  protected EList opinion = null;

  /**
   * The cached value of the '{@link #getQuote() <em>Quote</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getQuote()
   * @generated
   * @ordered
   */
  protected EList quote = null;

  /**
   * The cached value of the '{@link #getDiscussionViewer() <em>Discussion Viewer</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getDiscussionViewer()
   * @generated
   * @ordered
   */
  protected EList discussionViewer = null;

  /**
   * The cached value of the '{@link #getRelationConnection() <em>Relation Connection</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRelationConnection()
   * @generated
   * @ordered
   */
  protected EList relationConnection = null;

  /**
   * The cached value of the '{@link #getContainmentConnection() <em>Containment Connection</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getContainmentConnection()
   * @generated
   * @ordered
   */
  protected EList containmentConnection = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RootImpl()
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
    return DiscussionViewerPackage.Literals.ROOT;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getMementos()
  {
    if (mementos == null)
    {
      mementos = new EObjectContainmentEList(Memento.class, this, DiscussionViewerPackage.ROOT__MEMENTOS);
    }
    return mementos;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getSubtypes()
  {
    if (subtypes == null)
    {
      subtypes = new EObjectContainmentEList(Subtype.class, this, DiscussionViewerPackage.ROOT__SUBTYPES);
    }
    return subtypes;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewer getRealRoot()
  {
    return realRoot;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetRealRoot(DiscussionViewer newRealRoot, NotificationChain msgs)
  {
    DiscussionViewer oldRealRoot = realRoot;
    realRoot = newRealRoot;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiscussionViewerPackage.ROOT__REAL_ROOT, oldRealRoot, newRealRoot);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setRealRoot(DiscussionViewer newRealRoot)
  {
    if (newRealRoot != realRoot)
    {
      NotificationChain msgs = null;
      if (realRoot != null)
        msgs = ((InternalEObject)realRoot).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiscussionViewerPackage.ROOT__REAL_ROOT, null, msgs);
      if (newRealRoot != null)
        msgs = ((InternalEObject)newRealRoot).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiscussionViewerPackage.ROOT__REAL_ROOT, null, msgs);
      msgs = basicSetRealRoot(newRealRoot, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DiscussionViewerPackage.ROOT__REAL_ROOT, newRealRoot, newRealRoot));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getAbstractDicObject()
  {
    if (abstractDicObject == null)
    {
      abstractDicObject = new EObjectContainmentEList(AbstractDicObject.class, this, DiscussionViewerPackage.ROOT__ABSTRACT_DIC_OBJECT);
    }
    return abstractDicObject;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getOpinion()
  {
    if (opinion == null)
    {
      opinion = new EObjectContainmentEList(Opinion.class, this, DiscussionViewerPackage.ROOT__OPINION);
    }
    return opinion;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getQuote()
  {
    if (quote == null)
    {
      quote = new EObjectContainmentEList(Quote.class, this, DiscussionViewerPackage.ROOT__QUOTE);
    }
    return quote;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getDiscussionViewer()
  {
    if (discussionViewer == null)
    {
      discussionViewer = new EObjectContainmentEList(DiscussionViewer.class, this, DiscussionViewerPackage.ROOT__DISCUSSION_VIEWER);
    }
    return discussionViewer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getRelationConnection()
  {
    if (relationConnection == null)
    {
      relationConnection = new EObjectContainmentEList(RelationConnection.class, this, DiscussionViewerPackage.ROOT__RELATION_CONNECTION);
    }
    return relationConnection;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList getContainmentConnection()
  {
    if (containmentConnection == null)
    {
      containmentConnection = new EObjectContainmentEList(ContainmentConnection.class, this, DiscussionViewerPackage.ROOT__CONTAINMENT_CONNECTION);
    }
    return containmentConnection;
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
      case DiscussionViewerPackage.ROOT__MEMENTOS:
        return ((InternalEList)getMementos()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__SUBTYPES:
        return ((InternalEList)getSubtypes()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__REAL_ROOT:
        return basicSetRealRoot(null, msgs);
      case DiscussionViewerPackage.ROOT__ABSTRACT_DIC_OBJECT:
        return ((InternalEList)getAbstractDicObject()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__OPINION:
        return ((InternalEList)getOpinion()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__QUOTE:
        return ((InternalEList)getQuote()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__DISCUSSION_VIEWER:
        return ((InternalEList)getDiscussionViewer()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__RELATION_CONNECTION:
        return ((InternalEList)getRelationConnection()).basicRemove(otherEnd, msgs);
      case DiscussionViewerPackage.ROOT__CONTAINMENT_CONNECTION:
        return ((InternalEList)getContainmentConnection()).basicRemove(otherEnd, msgs);
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
      case DiscussionViewerPackage.ROOT__MEMENTOS:
        return getMementos();
      case DiscussionViewerPackage.ROOT__SUBTYPES:
        return getSubtypes();
      case DiscussionViewerPackage.ROOT__REAL_ROOT:
        return getRealRoot();
      case DiscussionViewerPackage.ROOT__ABSTRACT_DIC_OBJECT:
        return getAbstractDicObject();
      case DiscussionViewerPackage.ROOT__OPINION:
        return getOpinion();
      case DiscussionViewerPackage.ROOT__QUOTE:
        return getQuote();
      case DiscussionViewerPackage.ROOT__DISCUSSION_VIEWER:
        return getDiscussionViewer();
      case DiscussionViewerPackage.ROOT__RELATION_CONNECTION:
        return getRelationConnection();
      case DiscussionViewerPackage.ROOT__CONTAINMENT_CONNECTION:
        return getContainmentConnection();
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
      case DiscussionViewerPackage.ROOT__MEMENTOS:
        getMementos().clear();
        getMementos().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__SUBTYPES:
        getSubtypes().clear();
        getSubtypes().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__REAL_ROOT:
        setRealRoot((DiscussionViewer)newValue);
        return;
      case DiscussionViewerPackage.ROOT__ABSTRACT_DIC_OBJECT:
        getAbstractDicObject().clear();
        getAbstractDicObject().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__OPINION:
        getOpinion().clear();
        getOpinion().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__QUOTE:
        getQuote().clear();
        getQuote().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__DISCUSSION_VIEWER:
        getDiscussionViewer().clear();
        getDiscussionViewer().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__RELATION_CONNECTION:
        getRelationConnection().clear();
        getRelationConnection().addAll((Collection)newValue);
        return;
      case DiscussionViewerPackage.ROOT__CONTAINMENT_CONNECTION:
        getContainmentConnection().clear();
        getContainmentConnection().addAll((Collection)newValue);
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
      case DiscussionViewerPackage.ROOT__MEMENTOS:
        getMementos().clear();
        return;
      case DiscussionViewerPackage.ROOT__SUBTYPES:
        getSubtypes().clear();
        return;
      case DiscussionViewerPackage.ROOT__REAL_ROOT:
        setRealRoot((DiscussionViewer)null);
        return;
      case DiscussionViewerPackage.ROOT__ABSTRACT_DIC_OBJECT:
        getAbstractDicObject().clear();
        return;
      case DiscussionViewerPackage.ROOT__OPINION:
        getOpinion().clear();
        return;
      case DiscussionViewerPackage.ROOT__QUOTE:
        getQuote().clear();
        return;
      case DiscussionViewerPackage.ROOT__DISCUSSION_VIEWER:
        getDiscussionViewer().clear();
        return;
      case DiscussionViewerPackage.ROOT__RELATION_CONNECTION:
        getRelationConnection().clear();
        return;
      case DiscussionViewerPackage.ROOT__CONTAINMENT_CONNECTION:
        getContainmentConnection().clear();
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
      case DiscussionViewerPackage.ROOT__MEMENTOS:
        return mementos != null && !mementos.isEmpty();
      case DiscussionViewerPackage.ROOT__SUBTYPES:
        return subtypes != null && !subtypes.isEmpty();
      case DiscussionViewerPackage.ROOT__REAL_ROOT:
        return realRoot != null;
      case DiscussionViewerPackage.ROOT__ABSTRACT_DIC_OBJECT:
        return abstractDicObject != null && !abstractDicObject.isEmpty();
      case DiscussionViewerPackage.ROOT__OPINION:
        return opinion != null && !opinion.isEmpty();
      case DiscussionViewerPackage.ROOT__QUOTE:
        return quote != null && !quote.isEmpty();
      case DiscussionViewerPackage.ROOT__DISCUSSION_VIEWER:
        return discussionViewer != null && !discussionViewer.isEmpty();
      case DiscussionViewerPackage.ROOT__RELATION_CONNECTION:
        return relationConnection != null && !relationConnection.isEmpty();
      case DiscussionViewerPackage.ROOT__CONTAINMENT_CONNECTION:
        return containmentConnection != null && !containmentConnection.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //RootImpl