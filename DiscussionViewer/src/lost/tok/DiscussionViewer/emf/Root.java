/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getMementos <em>Mementos</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getSubtypes <em>Subtypes</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getRealRoot <em>Real Root</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getAbstractDicObject <em>Abstract Dic Object</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getOpinion <em>Opinion</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getQuote <em>Quote</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getDiscussionViewer <em>Discussion Viewer</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getRelationConnection <em>Relation Connection</em>}</li>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Root#getContainmentConnection <em>Containment Connection</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot()
 * @model
 * @generated
 */
public interface Root extends EObject
{
  /**
   * Returns the value of the '<em><b>Mementos</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.Memento}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Mementos</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Mementos</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_Mementos()
   * @model type="lost.tok.DiscussionViewer.emf.Memento" containment="true" upper="2000"
   * @generated
   */
  EList getMementos();

  /**
   * Returns the value of the '<em><b>Subtypes</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.Subtype}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Subtypes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Subtypes</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_Subtypes()
   * @model type="lost.tok.DiscussionViewer.emf.Subtype" containment="true" upper="2000"
   * @generated
   */
  EList getSubtypes();

  /**
   * Returns the value of the '<em><b>Real Root</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Real Root</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Real Root</em>' containment reference.
   * @see #setRealRoot(DiscussionViewer)
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_RealRoot()
   * @model containment="true"
   * @generated
   */
  DiscussionViewer getRealRoot();

  /**
   * Sets the value of the '{@link lost.tok.DiscussionViewer.emf.Root#getRealRoot <em>Real Root</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Real Root</em>' containment reference.
   * @see #getRealRoot()
   * @generated
   */
  void setRealRoot(DiscussionViewer value);

  /**
   * Returns the value of the '<em><b>Abstract Dic Object</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.AbstractDicObject}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Abstract Dic Object</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Abstract Dic Object</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_AbstractDicObject()
   * @model type="lost.tok.DiscussionViewer.emf.AbstractDicObject" containment="true" upper="2000"
   * @generated
   */
  EList getAbstractDicObject();

  /**
   * Returns the value of the '<em><b>Opinion</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.Opinion}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Opinion</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Opinion</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_Opinion()
   * @model type="lost.tok.DiscussionViewer.emf.Opinion" containment="true" upper="2000"
   * @generated
   */
  EList getOpinion();

  /**
   * Returns the value of the '<em><b>Quote</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.Quote}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Quote</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Quote</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_Quote()
   * @model type="lost.tok.DiscussionViewer.emf.Quote" containment="true" upper="2000"
   * @generated
   */
  EList getQuote();

  /**
   * Returns the value of the '<em><b>Discussion Viewer</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.DiscussionViewer}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Discussion Viewer</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Discussion Viewer</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_DiscussionViewer()
   * @model type="lost.tok.DiscussionViewer.emf.DiscussionViewer" containment="true" upper="2000"
   * @generated
   */
  EList getDiscussionViewer();

  /**
   * Returns the value of the '<em><b>Relation Connection</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.RelationConnection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Relation Connection</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Relation Connection</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_RelationConnection()
   * @model type="lost.tok.DiscussionViewer.emf.RelationConnection" containment="true" upper="2000"
   * @generated
   */
  EList getRelationConnection();

  /**
   * Returns the value of the '<em><b>Containment Connection</b></em>' containment reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.ContainmentConnection}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Containment Connection</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Containment Connection</em>' containment reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getRoot_ContainmentConnection()
   * @model type="lost.tok.DiscussionViewer.emf.ContainmentConnection" containment="true" upper="2000"
   * @generated
   */
  EList getContainmentConnection();

} // Root