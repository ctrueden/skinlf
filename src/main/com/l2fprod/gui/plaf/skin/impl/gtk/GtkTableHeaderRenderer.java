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
package com.l2fprod.gui.plaf.skin.impl.gtk;

import com.l2fprod.gui.plaf.skin.DefaultButton;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class GtkTableHeaderRenderer
  extends DefaultTableCellRenderer
  implements javax.swing.plaf.UIResource {

  boolean isSelected;
  boolean hasFocus;

  transient DefaultButton itemSelected, itemUnselected;

  /**
   * Constructor for the GtkTableHeaderRenderer object
   */
  public GtkTableHeaderRenderer(
    DefaultButton itemSelected,
    DefaultButton itemUnselected) {
    setOpaque(false);
    this.itemSelected = itemSelected;
    this.itemUnselected = itemUnselected;
  }

  /**
   * Gets the TableCellRendererComponent attribute of the
   * GtkTableHeaderRenderer object
   *
   * @param table       Description of Parameter
   * @param value       Description of Parameter
   * @param isSelected  Description of Parameter
   * @param hasFocus    Description of Parameter
   * @param row         Description of Parameter
   * @param column      Description of Parameter
   * @return            The TableCellRendererComponent value
   */
  public Component getTableCellRendererComponent(
    JTable table,
    Object value,
    boolean isSelected,
    boolean hasFocus,
    int row,
    int column) {
    if (table != null) {
      JTableHeader header = table.getTableHeader();
      if (header != null) {
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
      }
    }

    this.isSelected = isSelected;
    this.hasFocus = hasFocus;
    setText((value == null) ? "" : value.toString());
    
    Insets insets;
    if (isSelected || hasFocus) {
      insets = itemSelected.getInsets();
    } else {
      insets = itemUnselected.getInsets();
    }
    setBorder(new EmptyBorder(insets));
    
    return this;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   */
  protected void paintComponent(Graphics g) {
    if (isSelected || hasFocus) {
      itemSelected.paint(g, this);
    } else {
      itemUnselected.paint(g, this);
    }
    super.paintComponent(g);
  }
}
