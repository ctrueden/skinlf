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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;

/**
 * A placeholder for default Tree icons.
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinTreeUI extends BasicTreeUI {

  /**
   * Description of the Field
   */
  protected final static int HALF_SIZE = 4;
  /**
   * Description of the Field
   */
  protected final static int SIZE = 9;

  /**
   * Description of the Method
   *
   * @param g       Description of Parameter
   * @param c       Description of Parameter
   * @param x       Description of Parameter
   * @param top     Description of Parameter
   * @param bottom  Description of Parameter
   */
  protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
    drawDashedVerticalLine(g, x, top, bottom);
  }

  /**
   * Description of the Method
   *
   * @param g      Description of Parameter
   * @param c      Description of Parameter
   * @param y      Description of Parameter
   * @param left   Description of Parameter
   * @param right  Description of Parameter
   */
  protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
    drawDashedHorizontalLine(g, y, left, right);
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinTreeUI();
  }

  /**
   * The minus sign button icon
   *
   * @author    fred
   * @created   27 avril 2002
   */
  static class ExpandedIcon implements Icon, Serializable {
    /**
     * Gets the IconWidth attribute of the ExpandedIcon object
     *
     * @return   The IconWidth value
     */
    public int getIconWidth() {
      return SIZE;
    }

    /**
     * Gets the IconHeight attribute of the ExpandedIcon object
     *
     * @return   The IconHeight value
     */
    public int getIconHeight() {
      return SIZE;
    }

    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     * @param g  Description of Parameter
     * @param x  Description of Parameter
     * @param y  Description of Parameter
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
      Color backgroundColor = c.getBackground();

      if (backgroundColor != null) {
        g.setColor(backgroundColor);
      }
      else {
        g.setColor(Color.white);
      }
      g.fillRect(x, y, SIZE - 1, SIZE - 1);
      g.setColor(Color.gray);
      g.drawRect(x, y, SIZE - 1, SIZE - 1);
      g.setColor(Color.black);
      g.drawLine(x + 2, y + HALF_SIZE, x + (SIZE - 3), y + HALF_SIZE);
    }

    /**
     * Description of the Method
     *
     * @return   Description of the Returned Value
     */
    public static Icon createExpandedIcon() {
      return new ExpandedIcon();
    }
  }

  /**
   * The plus sign button icon
   *
   * @author    fred
   * @created   27 avril 2002
   */
  static class CollapsedIcon extends ExpandedIcon {

    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     * @param g  Description of Parameter
     * @param x  Description of Parameter
     * @param y  Description of Parameter
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
      super.paintIcon(c, g, x, y);
      g.drawLine(x + HALF_SIZE, y + 2, x + HALF_SIZE, y + (SIZE - 3));
    }

    /**
     * Description of the Method
     *
     * @return   Description of the Returned Value
     */
    public static Icon createCollapsedIcon() {
      return new CollapsedIcon();
    }
  }

}
