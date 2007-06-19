/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor.impl;

import editor.EditorPackage;
import editor.Quote;
import editor.Relation;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Quote</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link editor.impl.QuoteImpl#getOpinion <em>Opinion</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class QuoteImpl extends DiscussionObjectImpl implements Quote {
	/**
	 * The cached value of the '{@link #getOpinion() <em>Opinion</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOpinion()
	 * @generated
	 * @ordered
	 */
	protected Relation opinion = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QuoteImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return EditorPackage.Literals.QUOTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Relation getOpinion() {
		return opinion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOpinion(Relation newOpinion, NotificationChain msgs) {
		Relation oldOpinion = opinion;
		opinion = newOpinion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EditorPackage.QUOTE__OPINION, oldOpinion, newOpinion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOpinion(Relation newOpinion) {
		if (newOpinion != opinion) {
			NotificationChain msgs = null;
			if (opinion != null)
				msgs = ((InternalEObject)opinion).eInverseRemove(this, EditorPackage.RELATION__SECOND, Relation.class, msgs);
			if (newOpinion != null)
				msgs = ((InternalEObject)newOpinion).eInverseAdd(this, EditorPackage.RELATION__SECOND, Relation.class, msgs);
			msgs = basicSetOpinion(newOpinion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EditorPackage.QUOTE__OPINION, newOpinion, newOpinion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EditorPackage.QUOTE__OPINION:
				if (opinion != null)
					msgs = ((InternalEObject)opinion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EditorPackage.QUOTE__OPINION, null, msgs);
				return basicSetOpinion((Relation)otherEnd, msgs);
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
			case EditorPackage.QUOTE__OPINION:
				return basicSetOpinion(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EditorPackage.QUOTE__OPINION:
				return getOpinion();
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
			case EditorPackage.QUOTE__OPINION:
				setOpinion((Relation)newValue);
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
			case EditorPackage.QUOTE__OPINION:
				setOpinion((Relation)null);
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
			case EditorPackage.QUOTE__OPINION:
				return opinion != null;
		}
		return super.eIsSet(featureID);
	}

} //QuoteImpl