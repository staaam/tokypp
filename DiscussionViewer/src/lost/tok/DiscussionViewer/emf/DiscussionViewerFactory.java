/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage
 * @generated
 */
public interface DiscussionViewerFactory extends EFactory
{
  /**
   * The singleton instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  DiscussionViewerFactory eINSTANCE = lost.tok.DiscussionViewer.emf.impl.DiscussionViewerFactoryImpl.init();

  /**
   * Returns a new object of class '<em>Memento Value</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Memento Value</em>'.
   * @generated
   */
  MementoValue createMementoValue();

  /**
   * Returns a new object of class '<em>Memento</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Memento</em>'.
   * @generated
   */
  Memento createMemento();

  /**
   * Returns a new object of class '<em>Subtype</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Subtype</em>'.
   * @generated
   */
  Subtype createSubtype();

  /**
   * Returns a new object of class '<em>Subtype Link</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Subtype Link</em>'.
   * @generated
   */
  SubtypeLink createSubtypeLink();

  /**
   * Returns a new object of class '<em>Model Object</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Model Object</em>'.
   * @generated
   */
  ModelObject createModelObject();

  /**
   * Returns a new object of class '<em>Abstract Dic Object</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Abstract Dic Object</em>'.
   * @generated
   */
  AbstractDicObject createAbstractDicObject();

  /**
   * Returns a new object of class '<em>Opinion</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Opinion</em>'.
   * @generated
   */
  Opinion createOpinion();

  /**
   * Returns a new object of class '<em>Quote</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Quote</em>'.
   * @generated
   */
  Quote createQuote();

  /**
   * Returns a new object of class '<em>Discussion Viewer</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Discussion Viewer</em>'.
   * @generated
   */
  DiscussionViewer createDiscussionViewer();

  /**
   * Returns a new object of class '<em>Relation Connection</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Relation Connection</em>'.
   * @generated
   */
  RelationConnection createRelationConnection();

  /**
   * Returns a new object of class '<em>Containment Connection</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Containment Connection</em>'.
   * @generated
   */
  ContainmentConnection createContainmentConnection();

  /**
   * Returns a new object of class '<em>Root</em>'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return a new object of class '<em>Root</em>'.
   * @generated
   */
  Root createRoot();

  /**
   * Returns the package supported by this factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the package supported by this factory.
   * @generated
   */
  DiscussionViewerPackage getDiscussionViewerPackage();

} //DiscussionViewerFactory
