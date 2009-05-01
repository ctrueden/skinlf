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

import java.awt.*;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinTabbedPaneUI extends BasicTabbedPaneUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Description of the Method
   *
   * @param g             Description of Parameter
   * @param tabPlacement  Description of Parameter
   * @param tabIndex      Description of Parameter
   * @param x             Description of Parameter
   * @param y             Description of Parameter
   * @param w             Description of Parameter
   * @param h             Description of Parameter
   * @param isSelected    Description of Parameter
   */
  protected void paintTabBorder(Graphics g, int tabPlacement,
      int tabIndex,
      int x, int y, int w, int h,
      boolean isSelected) {
    skin.getTab().paintTab(g, tabPlacement, isSelected, x, y, w, h);
  }

  /**
   * Description of the Method
   *
   * @param g              Description of Parameter
   * @param tabPlacement   Description of Parameter
   * @param selectedIndex  Description of Parameter
   */
  protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
    int width = tabPane.getWidth();
    int height = tabPane.getHeight();
    Insets insets = tabPane.getInsets();

    int x = insets.left;
    int y = insets.top;
    int w = width - insets.right - insets.left;
    int h = height - insets.top - insets.bottom;

    switch (tabPlacement) {
      case LEFT:
        x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
        w -= (x - insets.left);
        break;
      case RIGHT:
        w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
        break;
      case BOTTOM:
        h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
        break;
      case TOP:
      default:
        y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
        h -= (y - insets.top);

    }
    if (skin.getTab().paintContent(g, tabPlacement, selectedIndex, x, y, w, h) == false) {
      paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    }
    else
    {
      Rectangle rect = getTabBounds(selectedIndex, calcRect);
      skin.getTab().paintGap(g, tabPlacement, selectedIndex, rect.x, y, rect.width, h);
    }
  }

  /**
   * Description of the Method
   *
   * @param g             Description of Parameter
   * @param tabPlacement  Description of Parameter
   * @param tabIndex      Description of Parameter
   * @param x             Description of Parameter
   * @param y             Description of Parameter
   * @param w             Description of Parameter
   * @param h             Description of Parameter
   * @param isSelected    Description of Parameter
   */
  protected void paintTabBackground(Graphics g, int tabPlacement,
      int tabIndex,
      int x, int y, int w, int h,
      boolean isSelected) {
  }
  
  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinTabbedPaneUI();
  }
}

