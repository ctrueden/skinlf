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

import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.text.View;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinToggleButtonUI extends SkinButtonUI {

  // ********************************
  //          Paint Methods
  // ********************************
  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
    AbstractButton b = (AbstractButton) c;
    ButtonModel model = b.getModel();

    Dimension size = b.getSize();
    FontMetrics fm = g.getFontMetrics();

    Insets i = c.getInsets();

    Rectangle viewRect = new Rectangle(size);

    viewRect.x += i.left;
    viewRect.y += i.top;
    viewRect.width -= (i.right + viewRect.x);
    viewRect.height -= (i.bottom + viewRect.y);

    Rectangle iconRect = new Rectangle();
    Rectangle textRect = new Rectangle();

    Font f = c.getFont();
    g.setFont(f);

    // layout the text and icon
    String text = SwingUtilities.layoutCompoundLabel(
        c, fm, b.getText(), b.getIcon(),
        b.getVerticalAlignment(), b.getHorizontalAlignment(),
        b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
        viewRect, iconRect, textRect, b.getText() == null ? 0 : b.getIconTextGap()
        );

    g.setColor(b.getBackground());

    clearTextShiftOffset();

    if (model.isArmed() && model.isPressed() || model.isSelected()) {
      paintButtonPressed(g, b);
    }

    skin.getButton().paintButton(g, b);

    // Paint the Icon
    if (b.getIcon() != null) {
      paintIcon(g, b, iconRect);
    }

    // Draw the Text
    if (text != null && !text.equals("")) {
      View v = (View) c.getClientProperty("html");
      //BasicHTML.propertyKey);
      if (v != null) {
        v.paint(g, textRect);
      }
      else {
        paintText(g, (JComponent)b,
                  textRect,
                  text);
      }
    }

    if (b.isFocusPainted() && b.hasFocus()) {
      // paint UI specific focus
      paintFocus(g, b, viewRect, textRect, iconRect);
    }
  }

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent b) {
    return new SkinToggleButtonUI();
  }

}
