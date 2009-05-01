/**
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright @YEAR@ L2FProd.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.l2fprod.gui.plaf.skin;

/**
 * Skin Personality. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:44 $
 */
public interface SkinPersonality extends SkinComponent {

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  boolean paintDialog(java.awt.Graphics g, java.awt.Component c);

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  boolean paintFocus(java.awt.Graphics g, javax.swing.JComponent c);

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  boolean paintMenu(java.awt.Graphics g, javax.swing.JMenu c);

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  boolean paintMenuItem(java.awt.Graphics g, javax.swing.JMenuItem c);

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  boolean paintBackground(java.awt.Graphics g, java.awt.Component c);

  /**
   * Description of the Method
   *
   * @param g         Description of Parameter
   * @param c         Description of Parameter
   * @param bounds    Description of Parameter
   * @param hasFocus  Description of Parameter
   * @return          Description of the Returned Value
   */
  boolean paintComboBox(java.awt.Graphics g,
      javax.swing.JComboBox c,
      java.awt.Rectangle bounds, boolean hasFocus, boolean isRollover);

  /**
   * Gets the ComboBoxInsets attribute of the SkinPersonality object
   *
   * @return   The ComboBoxInsets value
   */
  java.awt.Insets getComboBoxInsets();

  /**
   * Gets the ComboBoxPreferredSize attribute of the SkinPersonality object
   *
   * @param c  Description of Parameter
   * @return   The ComboBoxPreferredSize value
   */
  java.awt.Dimension getComboBoxPreferredSize(javax.swing.JComboBox c);

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  javax.swing.ListCellRenderer createListCellRenderer();

  /**
   * @return       Description of the Returned Value
   * @deprecated   replaced by getTableHeaderRenderer which returns a shared
   *      instance
   */
  javax.swing.table.TableCellRenderer createTableHeaderRenderer();

  /**
   * Gets the TableHeaderRenderer attribute of the SkinPersonality object
   *
   * @return   The TableHeaderRenderer value
   */
  javax.swing.table.TableCellRenderer getTableHeaderRenderer();

}
