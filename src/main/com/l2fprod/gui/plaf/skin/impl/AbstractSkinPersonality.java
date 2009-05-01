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
package com.l2fprod.gui.plaf.skin.impl;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import com.l2fprod.gui.plaf.skin.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:21:42 $
 */
public class AbstractSkinPersonality extends AbstractSkinComponent implements SkinPersonality {

  /**
   * Gets the ComboBoxInsets attribute of the AbstractSkinPersonality object
   *
   * @return   The ComboBoxInsets value
   */
  public java.awt.Insets getComboBoxInsets() {
    return null;
  }

  /**
   * Gets the ComboBoxPreferredSize attribute of the AbstractSkinPersonality
   * object
   *
   * @param c  Description of Parameter
   * @return   The ComboBoxPreferredSize value
   */
  public java.awt.Dimension getComboBoxPreferredSize(javax.swing.JComboBox c) {
    return null;
  }

  /**
   * Gets the TableHeaderRenderer attribute of the AbstractSkinPersonality
   * object
   *
   * @return   The TableHeaderRenderer value
   */
  public TableCellRenderer getTableHeaderRenderer() {
    return null;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    return false;
  }

  public void uninstallSkin(JComponent c) {
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintDialog(Graphics g, Component c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintFocus(Graphics g, JComponent c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintMenu(Graphics g, JMenu c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintMenuItem(Graphics g, JMenuItem c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintBackground(Graphics g, Component c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g         Description of Parameter
   * @param c         Description of Parameter
   * @param bounds    Description of Parameter
   * @param hasFocus  Description of Parameter
   * @return          Description of the Returned Value
   */
  public boolean paintComboBox(Graphics g, JComboBox c, Rectangle bounds, boolean hasFocus, boolean isRollover) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public TableCellRenderer createTableHeaderRenderer() {
    return getTableHeaderRenderer();
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public ListCellRenderer createListCellRenderer() {
    return null;
  }

}
