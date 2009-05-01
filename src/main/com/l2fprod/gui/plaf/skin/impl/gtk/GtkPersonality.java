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

import java.awt.Image;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.BorderUIResource;
import javax.swing.table.*;

import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.gui.plaf.skin.impl.*;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;
import com.l2fprod.util.ImageUtils;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.7 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkPersonality extends AbstractSkinPersonality {

  Image background;
  int backgroundPaintMode;

  DefaultButton menu;
  //    Border menuBorder;
  Border menuBarBorder;
  Border toolBarBorder;
  DefaultButton menuitemSelected;

  DefaultButton menuBackground;

  DefaultButton itemSelected, itemUnselected;

  DefaultButton comboBox;
  DefaultButton rolloverComboBox;
  DefaultButton disabledComboBox;

  DefaultButton focus;

  java.awt.Insets comboBoxInsets = new java.awt.Insets(1, 4, 1, 4);

  static int MINIMAL_SIZE = 50;

  /**
   * Constructor for the GtkPersonality object
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkPersonality(GtkParser parser) throws Exception {
    if (parser.getClass("GtkWindow") != null) {
      GtkStyle windowStyle = parser.getClass("GtkWindow").getStyle();
      GtkImage image = windowStyle.getEngine().findImage(new String[]{"function"},
          new String[]{"FLAT_BOX"});
      backgroundPaintMode = "TRUE".equals(image.getProperty("stretch")) ?
          ImageUtils.PAINT_STRETCH : ImageUtils.PAINT_TILE;
      background = image.getImage(parser.getDirectory());
      if (background != null && backgroundPaintMode == ImageUtils.PAINT_TILE) {
        int width = background.getWidth(ImageUtils.producer);
        int height = background.getHeight(ImageUtils.producer);
        int factor = Math.max(MINIMAL_SIZE / height, MINIMAL_SIZE / width);
        if (factor > 1) {
          background = ImageUtils.buildTile(background, factor);
        }
      }
      background = ImageUtils.toBufferedImage(background);
    }

    menuBackground = GtkUtils.newButton(parser, "GtkMenu",
        new String[]{"function"},
        new String[]{"BOX"});

    /*
     *  DefaultButton menuBorderImage = GtkUtils.newButton(parser, "GtkMenu",
     *  new String[]{"function"},
     *  new String[]{"BOX"}, true);
     *  menuBorder = new SkinBorder(new ImageIcon(menuBorderImage.top),
     *  new ImageIcon(menuBorderImage.bottom),
     *  new ImageIcon(menuBorderImage.left),
     *  new ImageIcon(menuBorderImage.right),
     *  new ImageIcon(menuBorderImage.topleft),
     *  new ImageIcon(menuBorderImage.topright),
     *  new ImageIcon(menuBorderImage.bottomleft),
     *  new ImageIcon(menuBorderImage.bottomright));
     */
    toolBarBorder = GtkUtils.newButton(parser, "GtkToolbar",
        new String[]{"function"},
        new String[]{"BOX"}, false, true, false, false);
    if (toolBarBorder != null) {
      toolBarBorder = new BorderUIResource(toolBarBorder);
    }
    
    menuBarBorder = GtkUtils.newButton(parser, "GtkMenuBar",
        new String[]{"function"},
        new String[]{"BOX"}, false, true, false, false);
    if (menuBarBorder != null) {
      menuBarBorder = new BorderUIResource(menuBarBorder);
    }
    
    menuitemSelected = GtkUtils.newButton(parser, "GtkMenuItem",
        new String[]{"function"},
        new String[]{"BOX"});

    itemUnselected = GtkUtils.newButton(parser, "GtkListItem",
        new String[]{"function", "state"},
        new String[]{"FLAT_BOX", "INSENSITIVE"});

    itemSelected = GtkUtils.newButton(parser, "GtkListItem",
        new String[]{"function", "state"},
        new String[]{"FLAT_BOX", null});

    comboBox = GtkUtils.newButton(parser, "GtkOptionMenu",
        new String[]{"function", "state"},
        new String[]{"BOX", null}, false, true, false, false);
    if (comboBox == null) {
      comboBox = GtkUtils.newButton(parser, "GtkOptionMenu",
          new String[]{"function"},
          new String[]{"TAB"}, false, true, true, false);
    }

    rolloverComboBox =
      GtkUtils.newButton(parser, "GtkOptionMenu",
                         new String[]{"function", "state"},
                         new String[]{"BOX", "PRELIGHT"}, false, true, false, false);

    disabledComboBox =
      GtkUtils.newButton(parser, "GtkOptionMenu",
                         new String[]{"function", "state"},
                         new String[]{"BOX", "INSENSITIVE"}, false, true, false, false);

    focus = GtkUtils.newButton(parser, "default",
        new String[]{"function"},
        new String[]{"FOCUS"}, true);
    if (focus != null) {
      focus.setCenterFill(ImageUtils.PAINT_NONE);
    }

  }

  /**
   * Gets the ComboBoxInsets attribute of the GtkPersonality object
   *
   * @return   The ComboBoxInsets value
   */
  public java.awt.Insets getComboBoxInsets() {
    if (comboBox != null) {
      return comboBox.getInsets();
    } else {
      return comboBoxInsets;
    }
  }

  /**
   * Gets the ComboBoxPreferredSize attribute of the GtkPersonality object
   *
   * @param c  Description of Parameter
   * @return   The ComboBoxPreferredSize value
   */
  public java.awt.Dimension getComboBoxPreferredSize(javax.swing.JComboBox c) {
    if (comboBox != null) {
      return comboBox.getMinimumSize();
    }
    else {
      return null;
    }
  }

  /**
   * Gets the TableHeaderRenderer attribute of the GtkPersonality object
   *
   * @return   The TableHeaderRenderer value
   */
  public TableCellRenderer getTableHeaderRenderer() {
    if (itemSelected != null && itemUnselected != null) {
      return new GtkTableHeaderRenderer(itemSelected, itemUnselected);
    }
    else {
      return new DefaultTableCellRenderer.UIResource();
    }
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return true;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    if (c instanceof JMenuBar && menuBarBorder != null) {
      c.setBorder(menuBarBorder);
    }
    if (c instanceof JToolBar && toolBarBorder != null) {
      c.setBorder(toolBarBorder);
    }

    return true;
  }

  public void uninstallSkin(JComponent c) {
  }

  /**
   * Paint the given component with a background.
   * There is no logic in this method, it only checks if
   * the menu background or the global background should be used.
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintDialog(java.awt.Graphics g, java.awt.Component c) {
    if (c instanceof JPopupMenu && menuBackground != null) {
      menuBackground.paint(g, c);
    } else {
      JComponent component = (JComponent)c;
      if (background != null) {
        java.awt.Rectangle rect = component.getVisibleRect();
        ImageUtils.paint(component, g, background,
                         rect.x, rect.y, rect.width, rect.height, true,
                         backgroundPaintMode);
      }
    }
    return true;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintFocus(java.awt.Graphics g, javax.swing.JComponent c) {
    /*
     *  removed for now, some components were very ugly with focus painted
     *  if (focus != null) {
     *  focus.paint(g, c);
     *  return true;
     *  } else {
     *  return false;
     *  }
     */
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintMenu(java.awt.Graphics g, javax.swing.JMenu c) {
    /*
     *  if (menu != null) {
     *  if (c.isTopLevelMenu()) {
     *  if (c.isArmed() || (c instanceof JMenu && c.getModel().isSelected())) {
     *  menu.paint(g, c);
     *  return true;
     *  } else {
     *  return paintDialog(g, c);
     *  }
     *  } else {
     *  return paintMenuItem(g, c);
     *  }
     *  } else
     */
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintMenuItem(java.awt.Graphics g, javax.swing.JMenuItem c) {
    if (menuitemSelected != null) {
      if (c.isArmed() || (c instanceof JMenu && c.getModel().isSelected())) {
        menuitemSelected.paint(g, c);
      }
      /*
       *  else {
       *  paintDialog(g, c);
       *  }
       */
      return true;
    }
    else {
      return false;
    }
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
  public boolean paintComboBox(java.awt.Graphics g,
      javax.swing.JComboBox c,
      java.awt.Rectangle bounds, boolean hasFocus, boolean isRollover) {
    if (!c.isEnabled() && disabledComboBox != null) {
      disabledComboBox.paint(g, c);
      return true;
    }
    
    if (isRollover && rolloverComboBox != null) {
      rolloverComboBox.paint(g, c);
      return true;
    }
    if (comboBox != null) {
      comboBox.paint(g, c);
      return true;
    }
    return false;
  }

  /**
   * @return       Description of the Returned Value
   * @deprecated   use getTableHeaderRenderer.
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
    if (itemSelected != null && itemUnselected != null) {
      return new GtkListCellRenderer(itemSelected, itemUnselected);
    }
    else {
      return new DefaultListCellRenderer.UIResource();
    }
  }

}

