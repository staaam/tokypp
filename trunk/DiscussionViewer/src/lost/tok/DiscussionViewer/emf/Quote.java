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
 * A representation of the model object '<em><b>Quote</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link lost.tok.DiscussionViewer.emf.Quote#getManyQuotes <em>Many Quotes</em>}</li>
 * </ul>
 * </p>
 *
 * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getQuote()
 * @model
 * @generated
 */
public interface Quote extends AbstractDicObject
{
  /**
   * Returns the value of the '<em><b>Many Quotes</b></em>' reference list.
   * The list contents are of type {@link lost.tok.DiscussionViewer.emf.Opinion}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Many Quotes</em>' reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Many Quotes</em>' reference list.
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#getQuote_ManyQuotes()
   * @model type="lost.tok.DiscussionViewer.emf.Opinion" upper="2147483647"
   * @generated
   */
  EList getManyQuotes();

} // Quote