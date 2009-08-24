/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.dataquality.properties;

import org.talend.core.model.properties.Item;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tdq Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.dataquality.properties.TdqItem#getFilename <em>Filename</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.dataquality.properties.PropertiesPackage#getTdqItem()
 * @model
 * @generated
 */
public interface TdqItem extends Item, ModelElement {
    /**
     * Returns the value of the '<em><b>Filename</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filename</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Filename</em>' attribute.
     * @see #setFilename(String)
     * @see org.talend.dataquality.properties.PropertiesPackage#getTdqItem_Filename()
     * @model
     * @generated
     */
    String getFilename();

    /**
     * Sets the value of the '{@link org.talend.dataquality.properties.TdqItem#getFilename <em>Filename</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Filename</em>' attribute.
     * @see #getFilename()
     * @generated
     */
    void setFilename(String value);

} // TdqItem
