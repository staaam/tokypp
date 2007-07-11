/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Opinion</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Opinion#getOneOpinion <em>One Opinion</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getOpinion()
 * @model
 * @generated
 */
public interface Opinion extends AbstractDicObject
{
  /**
   * Returns the value of the '<em><b>One Opinion</b></em>' reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.Quote}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>One Opinion</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>One Opinion</em>' reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getOpinion_OneOpinion()
   * @model type="lost.tok.DiscussionViewer.emf.Quote" upper="2147483647"
   * @generated
   */
  EList getOneOpinion();

} // Opinion