/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package editor;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see editor.EditorFactory
 * @model kind="package"
 * @generated
 */
public interface EditorPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "editor";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "lost.tok.discussion";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "DiscussionEditor";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EditorPackage eINSTANCE = editor.impl.EditorPackageImpl.init();

	/**
	 * The meta object id for the '{@link editor.impl.DiscussionObjectImpl <em>Discussion Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see editor.impl.DiscussionObjectImpl
	 * @see editor.impl.EditorPackageImpl#getDiscussionObject()
	 * @generated
	 */
	int DISCUSSION_OBJECT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCUSSION_OBJECT__ID = 0;

	/**
	 * The number of structural features of the '<em>Discussion Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISCUSSION_OBJECT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link editor.impl.OpinionImpl <em>Opinion</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see editor.impl.OpinionImpl
	 * @see editor.impl.EditorPackageImpl#getOpinion()
	 * @generated
	 */
	int OPINION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINION__ID = DISCUSSION_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINION__NAME = DISCUSSION_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Relates</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINION__RELATES = DISCUSSION_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Opinion</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPINION_FEATURE_COUNT = DISCUSSION_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link editor.impl.QuoteImpl <em>Quote</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see editor.impl.QuoteImpl
	 * @see editor.impl.EditorPackageImpl#getQuote()
	 * @generated
	 */
	int QUOTE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUOTE__ID = DISCUSSION_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Opinion</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUOTE__OPINION = DISCUSSION_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Quote</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUOTE_FEATURE_COUNT = DISCUSSION_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link editor.impl.RelationImpl <em>Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see editor.impl.RelationImpl
	 * @see editor.impl.EditorPackageImpl#getRelation()
	 * @generated
	 */
	int RELATION = 2;

	/**
	 * The feature id for the '<em><b>First</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__FIRST = 0;

	/**
	 * The feature id for the '<em><b>Second</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__SECOND = 1;

	/**
	 * The number of structural features of the '<em>Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FEATURE_COUNT = 2;


	/**
	 * Returns the meta object for class '{@link editor.Opinion <em>Opinion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Opinion</em>'.
	 * @see editor.Opinion
	 * @generated
	 */
	EClass getOpinion();

	/**
	 * Returns the meta object for the attribute '{@link editor.Opinion#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see editor.Opinion#getName()
	 * @see #getOpinion()
	 * @generated
	 */
	EAttribute getOpinion_Name();

	/**
	 * Returns the meta object for the containment reference '{@link editor.Opinion#getRelates <em>Relates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Relates</em>'.
	 * @see editor.Opinion#getRelates()
	 * @see #getOpinion()
	 * @generated
	 */
	EReference getOpinion_Relates();

	/**
	 * Returns the meta object for class '{@link editor.Quote <em>Quote</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quote</em>'.
	 * @see editor.Quote
	 * @generated
	 */
	EClass getQuote();

	/**
	 * Returns the meta object for the containment reference '{@link editor.Quote#getOpinion <em>Opinion</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Opinion</em>'.
	 * @see editor.Quote#getOpinion()
	 * @see #getQuote()
	 * @generated
	 */
	EReference getQuote_Opinion();

	/**
	 * Returns the meta object for class '{@link editor.Relation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relation</em>'.
	 * @see editor.Relation
	 * @generated
	 */
	EClass getRelation();

	/**
	 * Returns the meta object for the container reference '{@link editor.Relation#getFirst <em>First</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>First</em>'.
	 * @see editor.Relation#getFirst()
	 * @see #getRelation()
	 * @generated
	 */
	EReference getRelation_First();

	/**
	 * Returns the meta object for the container reference '{@link editor.Relation#getSecond <em>Second</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Second</em>'.
	 * @see editor.Relation#getSecond()
	 * @see #getRelation()
	 * @generated
	 */
	EReference getRelation_Second();

	/**
	 * Returns the meta object for class '{@link editor.DiscussionObject <em>Discussion Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Discussion Object</em>'.
	 * @see editor.DiscussionObject
	 * @generated
	 */
	EClass getDiscussionObject();

	/**
	 * Returns the meta object for the attribute '{@link editor.DiscussionObject#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see editor.DiscussionObject#getId()
	 * @see #getDiscussionObject()
	 * @generated
	 */
	EAttribute getDiscussionObject_Id();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EditorFactory getEditorFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link editor.impl.OpinionImpl <em>Opinion</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see editor.impl.OpinionImpl
		 * @see editor.impl.EditorPackageImpl#getOpinion()
		 * @generated
		 */
		EClass OPINION = eINSTANCE.getOpinion();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPINION__NAME = eINSTANCE.getOpinion_Name();

		/**
		 * The meta object literal for the '<em><b>Relates</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPINION__RELATES = eINSTANCE.getOpinion_Relates();

		/**
		 * The meta object literal for the '{@link editor.impl.QuoteImpl <em>Quote</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see editor.impl.QuoteImpl
		 * @see editor.impl.EditorPackageImpl#getQuote()
		 * @generated
		 */
		EClass QUOTE = eINSTANCE.getQuote();

		/**
		 * The meta object literal for the '<em><b>Opinion</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUOTE__OPINION = eINSTANCE.getQuote_Opinion();

		/**
		 * The meta object literal for the '{@link editor.impl.RelationImpl <em>Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see editor.impl.RelationImpl
		 * @see editor.impl.EditorPackageImpl#getRelation()
		 * @generated
		 */
		EClass RELATION = eINSTANCE.getRelation();

		/**
		 * The meta object literal for the '<em><b>First</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATION__FIRST = eINSTANCE.getRelation_First();

		/**
		 * The meta object literal for the '<em><b>Second</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATION__SECOND = eINSTANCE.getRelation_Second();

		/**
		 * The meta object literal for the '{@link editor.impl.DiscussionObjectImpl <em>Discussion Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see editor.impl.DiscussionObjectImpl
		 * @see editor.impl.EditorPackageImpl#getDiscussionObject()
		 * @generated
		 */
		EClass DISCUSSION_OBJECT = eINSTANCE.getDiscussionObject();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DISCUSSION_OBJECT__ID = eINSTANCE.getDiscussionObject_Id();

	}

} //EditorPackage
