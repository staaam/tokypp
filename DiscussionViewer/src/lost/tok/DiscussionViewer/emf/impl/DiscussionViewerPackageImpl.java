/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package lost.tok.DiscussionViewer.emf.impl;

import lost.tok.DiscussionViewer.emf.AbstractDicObject;
import lost.tok.DiscussionViewer.emf.ContainmentConnection;
import lost.tok.DiscussionViewer.emf.DiscussionViewer;
import lost.tok.DiscussionViewer.emf.DiscussionViewerFactory;
import lost.tok.DiscussionViewer.emf.DiscussionViewerPackage;
import lost.tok.DiscussionViewer.emf.Memento;
import lost.tok.DiscussionViewer.emf.MementoValue;
import lost.tok.DiscussionViewer.emf.ModelObject;
import lost.tok.DiscussionViewer.emf.Opinion;
import lost.tok.DiscussionViewer.emf.Quote;
import lost.tok.DiscussionViewer.emf.RelationConnection;
import lost.tok.DiscussionViewer.emf.Root;
import lost.tok.DiscussionViewer.emf.Subtype;
import lost.tok.DiscussionViewer.emf.SubtypeLink;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DiscussionViewerPackageImpl extends EPackageImpl implements DiscussionViewerPackage
{
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mementoValueEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass mementoEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass subtypeEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass subtypeLinkEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass modelObjectEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass abstractDicObjectEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass opinionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass quoteEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass discussionViewerEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass relationConnectionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass containmentConnectionEClass = null;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EClass rootEClass = null;

  /**
   * Creates an instance of the model <b>Package</b>, registered with
   * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
   * package URI value.
   * <p>Note: the correct way to create the package is via the static
   * factory method {@link #init init()}, which also performs
   * initialization of the package, or returns the registered package,
   * if one already exists.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see org.eclipse.emf.ecore.EPackage.Registry
   * @see lost.tok.DiscussionViewer.emf.DiscussionViewerPackage#eNS_URI
   * @see #init()
   * @generated
   */
  private DiscussionViewerPackageImpl()
  {
    super(eNS_URI, DiscussionViewerFactory.eINSTANCE);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static boolean isInited = false;

  /**
   * Creates, registers, and initializes the <b>Package</b> for this
   * model, and for any others upon which it depends.  Simple
   * dependencies are satisfied by calling this method on all
   * dependent packages before doing anything else.  This method drives
   * initialization for interdependent packages directly, in parallel
   * with this package, itself.
   * <p>Of this package and its interdependencies, all packages which
   * have not yet been registered by their URI values are first created
   * and registered.  The packages are then initialized in two steps:
   * meta-model objects for all of the packages are created before any
   * are initialized, since one package's meta-model objects may refer to
   * those of another.
   * <p>Invocation of this method will not affect any packages that have
   * already been initialized.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #eNS_URI
   * @see #createPackageContents()
   * @see #initializePackageContents()
   * @generated
   */
  public static DiscussionViewerPackage init()
  {
    if (isInited) return (DiscussionViewerPackage)EPackage.Registry.INSTANCE.getEPackage(DiscussionViewerPackage.eNS_URI);

    // Obtain or create and register package
    DiscussionViewerPackageImpl theDiscussionViewerPackage = (DiscussionViewerPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DiscussionViewerPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DiscussionViewerPackageImpl());

    isInited = true;

    // Create package meta-data objects
    theDiscussionViewerPackage.createPackageContents();

    // Initialize created meta-data
    theDiscussionViewerPackage.initializePackageContents();

    // Mark meta-data to indicate it can't be changed
    theDiscussionViewerPackage.freeze();

    return theDiscussionViewerPackage;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMementoValue()
  {
    return mementoValueEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMementoValue_Name()
  {
    return (EAttribute)mementoValueEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMementoValue_Value()
  {
    return (EAttribute)mementoValueEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getMemento()
  {
    return mementoEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getMemento_Id()
  {
    return (EAttribute)mementoEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getMemento_Data()
  {
    return (EReference)mementoEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSubtype()
  {
    return subtypeEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getSubtype_Name()
  {
    return (EAttribute)subtypeEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSubtype_Base()
  {
    return (EReference)subtypeEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSubtype_Instances()
  {
    return (EReference)subtypeEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSubtype_Links()
  {
    return (EReference)subtypeEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getSubtypeLink()
  {
    return subtypeLinkEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSubtypeLink_Base()
  {
    return (EReference)subtypeLinkEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getSubtypeLink_Instance()
  {
    return (EReference)subtypeLinkEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getModelObject()
  {
    return modelObjectEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Name()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Id()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_X()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Y()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Width()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Height()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_ExpandedWidth()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_ExpandedHeight()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Expanded()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Subtype()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(9);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_Visible()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(10);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EAttribute getModelObject_ModelLinkTarget()
  {
    return (EAttribute)modelObjectEClass.getEStructuralFeatures().get(11);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getAbstractDicObject()
  {
    return abstractDicObjectEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAbstractDicObject_Relates1()
  {
    return (EReference)abstractDicObjectEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getAbstractDicObject_Relates2()
  {
    return (EReference)abstractDicObjectEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getOpinion()
  {
    return opinionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getOpinion_OneOpinion()
  {
    return (EReference)opinionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getQuote()
  {
    return quoteEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getQuote_ManyQuotes()
  {
    return (EReference)quoteEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getDiscussionViewer()
  {
    return discussionViewerEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRelationConnection()
  {
    return relationConnectionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRelationConnection_Source()
  {
    return (EReference)relationConnectionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRelationConnection_Target()
  {
    return (EReference)relationConnectionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getContainmentConnection()
  {
    return containmentConnectionEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContainmentConnection_Source()
  {
    return (EReference)containmentConnectionEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getContainmentConnection_Target()
  {
    return (EReference)containmentConnectionEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EClass getRoot()
  {
    return rootEClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_Mementos()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(0);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_Subtypes()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(1);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_RealRoot()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(2);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_AbstractDicObject()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(3);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_Opinion()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(4);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_Quote()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(5);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_DiscussionViewer()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(6);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_RelationConnection()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(7);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EReference getRoot_ContainmentConnection()
  {
    return (EReference)rootEClass.getEStructuralFeatures().get(8);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DiscussionViewerFactory getDiscussionViewerFactory()
  {
    return (DiscussionViewerFactory)getEFactoryInstance();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isCreated = false;

  /**
   * Creates the meta-model objects for the package.  This method is
   * guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void createPackageContents()
  {
    if (isCreated) return;
    isCreated = true;

    // Create classes and their features
    mementoValueEClass = createEClass(MEMENTO_VALUE);
    createEAttribute(mementoValueEClass, MEMENTO_VALUE__NAME);
    createEAttribute(mementoValueEClass, MEMENTO_VALUE__VALUE);

    mementoEClass = createEClass(MEMENTO);
    createEAttribute(mementoEClass, MEMENTO__ID);
    createEReference(mementoEClass, MEMENTO__DATA);

    subtypeEClass = createEClass(SUBTYPE);
    createEAttribute(subtypeEClass, SUBTYPE__NAME);
    createEReference(subtypeEClass, SUBTYPE__BASE);
    createEReference(subtypeEClass, SUBTYPE__INSTANCES);
    createEReference(subtypeEClass, SUBTYPE__LINKS);

    subtypeLinkEClass = createEClass(SUBTYPE_LINK);
    createEReference(subtypeLinkEClass, SUBTYPE_LINK__BASE);
    createEReference(subtypeLinkEClass, SUBTYPE_LINK__INSTANCE);

    modelObjectEClass = createEClass(MODEL_OBJECT);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__NAME);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__ID);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__X);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__Y);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__WIDTH);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__HEIGHT);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__EXPANDED_WIDTH);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__EXPANDED_HEIGHT);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__EXPANDED);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__SUBTYPE);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__VISIBLE);
    createEAttribute(modelObjectEClass, MODEL_OBJECT__MODEL_LINK_TARGET);

    abstractDicObjectEClass = createEClass(ABSTRACT_DIC_OBJECT);
    createEReference(abstractDicObjectEClass, ABSTRACT_DIC_OBJECT__RELATES1);
    createEReference(abstractDicObjectEClass, ABSTRACT_DIC_OBJECT__RELATES2);

    opinionEClass = createEClass(OPINION);
    createEReference(opinionEClass, OPINION__ONE_OPINION);

    quoteEClass = createEClass(QUOTE);
    createEReference(quoteEClass, QUOTE__MANY_QUOTES);

    discussionViewerEClass = createEClass(DISCUSSION_VIEWER);

    relationConnectionEClass = createEClass(RELATION_CONNECTION);
    createEReference(relationConnectionEClass, RELATION_CONNECTION__SOURCE);
    createEReference(relationConnectionEClass, RELATION_CONNECTION__TARGET);

    containmentConnectionEClass = createEClass(CONTAINMENT_CONNECTION);
    createEReference(containmentConnectionEClass, CONTAINMENT_CONNECTION__SOURCE);
    createEReference(containmentConnectionEClass, CONTAINMENT_CONNECTION__TARGET);

    rootEClass = createEClass(ROOT);
    createEReference(rootEClass, ROOT__MEMENTOS);
    createEReference(rootEClass, ROOT__SUBTYPES);
    createEReference(rootEClass, ROOT__REAL_ROOT);
    createEReference(rootEClass, ROOT__ABSTRACT_DIC_OBJECT);
    createEReference(rootEClass, ROOT__OPINION);
    createEReference(rootEClass, ROOT__QUOTE);
    createEReference(rootEClass, ROOT__DISCUSSION_VIEWER);
    createEReference(rootEClass, ROOT__RELATION_CONNECTION);
    createEReference(rootEClass, ROOT__CONTAINMENT_CONNECTION);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private boolean isInitialized = false;

  /**
   * Complete the initialization of the package and its meta-model.  This
   * method is guarded to have no affect on any invocation but its first.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void initializePackageContents()
  {
    if (isInitialized) return;
    isInitialized = true;

    // Initialize package
    setName(eNAME);
    setNsPrefix(eNS_PREFIX);
    setNsURI(eNS_URI);

    // Add supertypes to classes
    abstractDicObjectEClass.getESuperTypes().add(this.getModelObject());
    opinionEClass.getESuperTypes().add(this.getAbstractDicObject());
    quoteEClass.getESuperTypes().add(this.getAbstractDicObject());
    discussionViewerEClass.getESuperTypes().add(this.getModelObject());

    // Initialize classes and features; add operations and parameters
    initEClass(mementoValueEClass, MementoValue.class, "MementoValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMementoValue_Name(), ecorePackage.getEString(), "name", "0", 0, 1, MementoValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getMementoValue_Value(), ecorePackage.getEString(), "value", "0", 0, 1, MementoValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(mementoEClass, Memento.class, "Memento", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getMemento_Id(), ecorePackage.getEString(), "id", "0", 0, 1, Memento.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getMemento_Data(), this.getMementoValue(), null, "data", null, 0, 2000, Memento.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(subtypeEClass, Subtype.class, "Subtype", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getSubtype_Name(), ecorePackage.getEString(), "name", "AnonymousSubtype", 0, 1, Subtype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSubtype_Base(), this.getModelObject(), null, "base", null, 0, 1, Subtype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSubtype_Instances(), this.getModelObject(), null, "instances", null, 0, 2000, Subtype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSubtype_Links(), this.getSubtypeLink(), null, "links", null, 0, 20000, Subtype.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(subtypeLinkEClass, SubtypeLink.class, "SubtypeLink", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getSubtypeLink_Base(), this.getModelObject(), null, "base", null, 0, 1, SubtypeLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getSubtypeLink_Instance(), this.getModelObject(), null, "instance", null, 0, 1, SubtypeLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(modelObjectEClass, ModelObject.class, "ModelObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEAttribute(getModelObject_Name(), ecorePackage.getEString(), "Name", "0", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Id(), ecorePackage.getEString(), "Id", "0", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_X(), ecorePackage.getEInt(), "X", "0", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Y(), ecorePackage.getEInt(), "Y", "0", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Width(), ecorePackage.getEInt(), "Width", "100", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Height(), ecorePackage.getEInt(), "Height", "100", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_ExpandedWidth(), ecorePackage.getEInt(), "ExpandedWidth", "200", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_ExpandedHeight(), ecorePackage.getEInt(), "ExpandedHeight", "200", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Expanded(), ecorePackage.getEBoolean(), "Expanded", "false", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Subtype(), ecorePackage.getEBoolean(), "Subtype", "false", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_Visible(), ecorePackage.getEBoolean(), "Visible", "true", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEAttribute(getModelObject_ModelLinkTarget(), ecorePackage.getEString(), "ModelLinkTarget", "", 0, 1, ModelObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(abstractDicObjectEClass, AbstractDicObject.class, "AbstractDicObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getAbstractDicObject_Relates1(), this.getAbstractDicObject(), null, "Relates1", null, 0, 2147483647, AbstractDicObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getAbstractDicObject_Relates2(), this.getAbstractDicObject(), null, "Relates2", null, 0, 2147483647, AbstractDicObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(opinionEClass, Opinion.class, "Opinion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getOpinion_OneOpinion(), this.getQuote(), null, "oneOpinion", null, 0, 2147483647, Opinion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(quoteEClass, Quote.class, "Quote", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getQuote_ManyQuotes(), this.getOpinion(), null, "manyQuotes", null, 0, 2147483647, Quote.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(discussionViewerEClass, DiscussionViewer.class, "DiscussionViewer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

    initEClass(relationConnectionEClass, RelationConnection.class, "RelationConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRelationConnection_Source(), this.getAbstractDicObject(), null, "Source", null, 0, 1, RelationConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRelationConnection_Target(), this.getAbstractDicObject(), null, "Target", null, 0, 1, RelationConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(containmentConnectionEClass, ContainmentConnection.class, "ContainmentConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getContainmentConnection_Source(), this.getOpinion(), null, "Source", null, 0, 1, ContainmentConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getContainmentConnection_Target(), this.getQuote(), null, "Target", null, 0, 1, ContainmentConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    initEClass(rootEClass, Root.class, "Root", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
    initEReference(getRoot_Mementos(), this.getMemento(), null, "mementos", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_Subtypes(), this.getSubtype(), null, "subtypes", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_RealRoot(), this.getDiscussionViewer(), null, "RealRoot", null, 0, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_AbstractDicObject(), this.getAbstractDicObject(), null, "AbstractDicObject", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_Opinion(), this.getOpinion(), null, "Opinion", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_Quote(), this.getQuote(), null, "Quote", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_DiscussionViewer(), this.getDiscussionViewer(), null, "DiscussionViewer", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_RelationConnection(), this.getRelationConnection(), null, "RelationConnection", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
    initEReference(getRoot_ContainmentConnection(), this.getContainmentConnection(), null, "ContainmentConnection", null, 0, 2000, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

    // Create resource
    createResource(eNS_URI);
  }

} //DiscussionViewerPackageImpl
