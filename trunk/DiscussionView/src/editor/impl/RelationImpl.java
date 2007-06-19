/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor.impl;

import editor.DiscussionObject;
import editor.EditorPackage;
import editor.Opinion;
import editor.Quote;
import editor.Relation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Relation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link editor.impl.RelationImpl#getFirst <em>First</em>}</li>
 *   <li>{@link editor.impl.RelationImpl#getSecond <em>Second</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RelationImpl extends EObjectImpl implements Relation {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RelationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EditorPackage.Literals.RELATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiscussionObject getFirst() {
		if (eContainerFeatureID != EditorPackage.RELATION__FIRST) return null;
		return (DiscussionObject)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFirst(DiscussionObject newFirst, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newFirst, EditorPackage.RELATION__FIRST, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirst(DiscussionObject newFirst) {
		if (newFirst != eInternalContainer() || (eContainerFeatureID != EditorPackage.RELATION__FIRST && newFirst != null)) {
			if (EcoreUtil.isAncestor(this, newFirst))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newFirst != null)
				msgs = ((InternalEObject)newFirst).eInverseAdd(this, EditorPackage.OPINION__RELATES, Opinion.class, msgs);
			msgs = basicSetFirst(newFirst, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.RELATION__FIRST, newFirst, newFirst));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DiscussionObject getSecond() {
		if (eContainerFeatureID != EditorPackage.RELATION__SECOND) return null;
		return (DiscussionObject)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSecond(DiscussionObject newSecond, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSecond, EditorPackage.RELATION__SECOND, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecond(DiscussionObject newSecond) {
		if (newSecond != eInternalContainer() || (eContainerFeatureID != EditorPackage.RELATION__SECOND && newSecond != null)) {
			if (EcoreUtil.isAncestor(this, newSecond))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSecond != null)
				msgs = ((InternalEObject)newSecond).eInverseAdd(this, EditorPackage.QUOTE__OPINION, Quote.class, msgs);
			msgs = basicSetSecond(newSecond, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.RELATION__SECOND, newSecond, newSecond));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditorPackage.RELATION__FIRST:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetFirst((DiscussionObject)otherEnd, msgs);
			case EditorPackage.RELATION__SECOND:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSecond((DiscussionObject)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditorPackage.RELATION__FIRST:
				return basicSetFirst(null, msgs);
			case EditorPackage.RELATION__SECOND:
				return basicSetSecond(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case EditorPackage.RELATION__FIRST:
				return eInternalContainer().eInverseRemove(this, EditorPackage.OPINION__RELATES, Opinion.class, msgs);
			case EditorPackage.RELATION__SECOND:
				return eInternalContainer().eInverseRemove(this, EditorPackage.QUOTE__OPINION, Quote.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EditorPackage.RELATION__FIRST:
				return getFirst();
			case EditorPackage.RELATION__SECOND:
				return getSecond();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EditorPackage.RELATION__FIRST:
				setFirst((DiscussionObject)newValue);
				return;
			case EditorPackage.RELATION__SECOND:
				setSecond((DiscussionObject)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case EditorPackage.RELATION__FIRST:
				setFirst((DiscussionObject)null);
				return;
			case EditorPackage.RELATION__SECOND:
				setSecond((DiscussionObject)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case EditorPackage.RELATION__FIRST:
				return getFirst() != null;
			case EditorPackage.RELATION__SECOND:
				return getSecond() != null;
		}
		return super.eIsSet(featureID);
	}

} //RelationImpl