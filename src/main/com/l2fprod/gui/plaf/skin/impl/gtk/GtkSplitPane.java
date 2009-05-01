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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.JComponent;

import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.gui.plaf.skin.impl.*;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkSplitPane extends AbstractSkinSplitPane implements SkinSplitPane, SwingConstants {

  DefaultButton h_gutter, v_gutter;
  DefaultButton h_thumb, v_thumb;

  DefaultButton up, down, left, right;

  /**
   * Constructor for the GtkSplitPane object
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkSplitPane(GtkParser parser) throws Exception {

    h_gutter = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "orientation"},
        new String[]{"BOX", "HORIZONTAL"});

    v_gutter = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "orientation"},
        new String[]{"BOX", "VERTICAL"});

    h_thumb = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "orientation"},
        new String[]{"HANDLE", "HORIZONTAL"});
    if (h_thumb == null) {
      h_thumb = h_gutter;
    }

    v_thumb = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "orientation"},
        new String[]{"HANDLE", "VERTICAL"});
    if (v_thumb == null) {
      v_thumb = v_gutter;
    }

    up = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "arrow_direction"},
        new String[]{"ARROW", "UP"});

    down = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "arrow_direction"},
        new String[]{"ARROW", "DOWN"});

    left = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "arrow_direction"},
        new String[]{"ARROW", "LEFT"});

    right = GtkUtils.newButton(parser, "GtkPaned",
        new String[]{"function", "arrow_direction"},
        new String[]{"ARROW", "RIGHT"});
  }

  /**
   * Gets the PreferredSize attribute of the GtkSplitPane object
   *
   * @param splitpane  Description of Parameter
   * @return           The PreferredSize value
   */
  public Dimension getPreferredSize(JSplitPane splitpane) {
    Insets insets = splitpane.getInsets();
    int width = 0;
    int height = 0;
    if (splitpane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
      width = Math.max(Math.max(up.getWidth(), down.getWidth()), 10);
      height = splitpane.getHeight() + insets.top + insets.bottom;
    }
    else {
      height = Math.max(Math.max(left.getHeight(), right.getHeight()), 10);
      width = splitpane.getWidth() + insets.left + insets.right;
    }
    Dimension d = new Dimension(width, height);
    return (d);
    //return (splitpane.getOrientation() == JSplitPane.VERTICAL_SPLIT)
    //    ? new Dimension(Math.max(10, Math.min(up.getWidth(), v_thumb.getWidth())), 48)
    //	: new Dimension(48, Math.max(10, Math.min(left.getHeight(), h_thumb.getHeight())));
  }

  /**
   * Gets the ArrowPreferredSize attribute of the GtkSplitPane object
   *
   * @param direction  Description of Parameter
   * @return           The ArrowPreferredSize value
   */
  public Dimension getArrowPreferredSize(int direction) {
    switch (direction) {
      case NORTH:
        return new Dimension(up.getWidth(), up.getHeight());
      case SOUTH:
        return new Dimension(down.getWidth(), down.getHeight());
      case WEST:
        return new Dimension(left.getWidth(), left.getHeight());
      case EAST:
        return new Dimension(right.getWidth(), right.getHeight());
      default:
        throw new Error("Invalid direction " + direction);
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
    return true;
  }

  /**
   * Description of the Method
   *
   * @param g          Description of Parameter
   * @param b          Description of Parameter
   * @param direction  Description of Parameter
   * @return           Description of the Returned Value
   */
  public boolean paintArrow(java.awt.Graphics g, javax.swing.AbstractButton b, int direction) {
    java.awt.Dimension size = b.getSize();
    switch (direction) {

      case NORTH:
        down.paint(g, 0, 0, size.width, size.height, b);
        break;
      case SOUTH:
        up.paint(g, 0, 0, size.width, size.height, b);
        break;
      case WEST:
        left.paint(g, 0, 0, size.width, size.height, b);
        break;
      case EAST:
        right.paint(g, 0, 0, size.width, size.height, b);
    }
    return true;
  }

  // track is under thumb
  /**
   * Description of the Method
   *
   * @param g          Description of Parameter
   * @param splitpane  Description of Parameter
   * @param d          Description of Parameter
   * @return           Description of the Returned Value
   */
  public boolean paintGutter(Graphics g, JSplitPane splitpane, Dimension d) {
    if (splitpane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT) {
      h_gutter.paint(g, 0, 0, d.width, d.height, splitpane);
      return true;
    }
    else {
      v_gutter.paint(g, 0, 0, d.width, d.height, splitpane);
      return true;
    }
  }

  // thumb is the variable area
  /**
   * Description of the Method
   *
   * @param g          Description of Parameter
   * @param splitpane  Description of Parameter
   * @param d          Description of Parameter
   * @return           Description of the Returned Value
   */
  public boolean paintThumb(Graphics g, JSplitPane splitpane, Dimension d) {
    // the UI translate the graphics to thumbBounds.x and .y

    if (splitpane.getOrientation() == JSplitPane.HORIZONTAL_SPLIT && h_thumb != null) {
      h_thumb.paint(g, Math.max(0, (d.width - h_thumb.getWidth()) / 2),
          (d.height - h_thumb.getHeight()) / 2,
          Math.min(d.width, h_thumb.getWidth()),
          h_thumb.getHeight(), splitpane);
    }
    else if (splitpane.getOrientation() == JSplitPane.VERTICAL_SPLIT && v_thumb != null) {
      v_thumb.paint(g, (d.width - v_thumb.getWidth()) / 2,
          Math.max(0, (d.height - v_thumb.getHeight()) / 2),
          v_thumb.getWidth(),
          Math.min(d.height, v_thumb.getHeight()), splitpane);
    }
    else {
      return false;
    }
    return true;
  }

}
