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
import com.l2fprod.gui.plaf.skin.SkinTab;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkinTab;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.GtkParser;
import com.l2fprod.util.ImageUtils;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkTab extends AbstractSkinTab implements SkinTab {

  DefaultButton selected_top, unselected_top;
  DefaultButton selected_bottom, unselected_bottom;
  DefaultButton selected_left, unselected_left;
  DefaultButton selected_right, unselected_right;

  DefaultButton border;

  /**
   * Constructor for the GtkTab object
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkTab(GtkParser parser) throws Exception {
    unselected_top = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", "ACTIVE", "BOTTOM"}, false, false, false, false);
    selected_top = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", null, "BOTTOM"}, false, true, true, false);

    unselected_bottom = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", "ACTIVE", "TOP"}, false, true, false, false);
    selected_bottom = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", null, "TOP"}, false, true, false, false);

    unselected_left = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", "ACTIVE", "LEFT"}, false, true, false, false);
    selected_left = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", null, "LEFT"}, false, true, false, false);

    unselected_right = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", "ACTIVE", "RIGHT"}, false, true, false, false);
    selected_right = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "state", "gap_side"},
        new String[]{"EXTENSION", null, "RIGHT"}, false, true, false, false);

    if (unselected_bottom == null) {
      unselected_bottom = unselected_top.getTopToBottom();
    }

    if (selected_bottom == null) {
      selected_bottom = selected_top.getTopToBottom();
    }

    if (unselected_left == null) {
      unselected_left = unselected_top.rotateCounterClockWise();
    }

    if (selected_left == null) {
      selected_left = selected_top.rotateCounterClockWise();
    }

    if (unselected_right == null) {
      unselected_right = unselected_top.rotateClockWise();
    }

    if (selected_right == null) {
      selected_right = selected_top.rotateClockWise();
    }

    border = GtkUtils.newButton(parser, "GtkNotebook",
        new String[]{"function", "gap_side"},
        new String[]{"BOX_GAP", "TOP"}, false, true, false, true);
    if (border != null) {
      border.center = null;
    }
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return selected_top != null;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    return true;
  }

  /**
   * Description of the Method
   *
   * @param g             Description of Parameter
   * @param tabPlacement  Description of Parameter
   * @param isSelected    Description of Parameter
   * @param x             Description of Parameter
   * @param y             Description of Parameter
   * @param w             Description of Parameter
   * @param h             Description of Parameter
   * @return              Description of the Returned Value
   */
  public boolean paintTab(java.awt.Graphics g, int tabPlacement, boolean isSelected, int x, int y, int w, int h) {
      
      if (isSelected) {

      //HACK: set the height smaller, so that the tab is placed exactly on the content border
      h -= 2;

      switch (tabPlacement) {
        case SwingConstants.TOP:
          selected_top.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        case SwingConstants.BOTTOM:
          selected_bottom.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        case SwingConstants.LEFT:
          selected_left.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        case SwingConstants.RIGHT:
          selected_right.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        default:
          selected_top.paint(g, x, y, w, h, ImageUtils.producer);
      }
    }
    else {
        
      // HACK: set the width greater, so that the unselected tabs are placed exactly side by side
      w += 1;
      
      switch (tabPlacement) {
        case SwingConstants.TOP:
          unselected_top.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        case SwingConstants.BOTTOM:
          unselected_bottom.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        case SwingConstants.LEFT:
          unselected_left.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        case SwingConstants.RIGHT:
          unselected_right.paint(g, x, y, w, h, ImageUtils.producer);
          break;
        default:
          unselected_top.paint(g, x, y, w, h, ImageUtils.producer);
      }
    }
    return true;
  }

  /**
   * Description of the Method
   *
   * @param g              Description of Parameter
   * @param tabPlacement   Description of Parameter
   * @param selectedIndex  Description of Parameter
   * @param x              Description of Parameter
   * @param y              Description of Parameter
   * @param w              Description of Parameter
   * @param h              Description of Parameter
   * @return               Description of the Returned Value
   */
  public boolean paintContent(java.awt.Graphics g, int tabPlacement, int selectedIndex,
      int x, int y, int w, int h) {
    if (border != null) {
      border.paint(g, x, y, w, h, ImageUtils.producer);
      return true;
    }
    else {
      return false;
    }
  }
  
  public boolean paintGap(java.awt.Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    if (border != null) {
      border.paintGap(g, x, y, w, ImageUtils.producer);
      return true;
    }
    else {
      return false;
    }
  }
}
