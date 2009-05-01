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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import com.l2fprod.gui.plaf.skin.DefaultButton;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class GtkListCellRenderer extends DefaultListCellRenderer
  implements javax.swing.plaf.UIResource {

  boolean isSelected;
  boolean cellHasFocus;

  transient DefaultButton itemSelected, itemUnselected;

  /**
   * Constructor for the GtkListCellRenderer object
   */
  public GtkListCellRenderer(DefaultButton itemSelected, DefaultButton itemUnselected) {
    setOpaque(false);
    this.itemSelected = itemSelected;
    this.itemUnselected = itemUnselected;
  }

  /**
   * Gets the ListCellRendererComponent attribute of the GtkListCellRenderer
   * object
   *
   * @param list          Description of Parameter
   * @param value         Description of Parameter
   * @param index         Description of Parameter
   * @param isSelected    Description of Parameter
   * @param cellHasFocus  Description of Parameter
   * @return              The ListCellRendererComponent value
   */
  public Component getListCellRendererComponent(JList list,
                                                Object value,
                                                int index,
                                                boolean isSelected,
                                                boolean cellHasFocus) {
    this.isSelected = isSelected;
    this.cellHasFocus = cellHasFocus;
    return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
  }
  
  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   */
  protected void paintComponent(Graphics g) {
    if (isSelected) {
      itemSelected.paint(g, this);
    }
    else {
      itemUnselected.paint(g, this);
    }
    //	    else if (cellHasFocus)
    //		itemUnselected.paint(g, this);
    super.paintComponent(g);
  }

}

