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

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import javax.swing.text.View;

import java.awt.*;

/**
 * @author    $Author: zombi $
 * @created   27 avril 2002
 * @version   $Revision: 1.5 $, $Date: 2009-12-22 10:27:33 $
 */
public class SkinButtonUI extends BasicButtonUI {

  /**
   * Description of the Field
   */
  protected int dashedRectGapX;
  /**
   * Description of the Field
   */
  protected int dashedRectGapY;
  /**
   * Description of the Field
   */
  protected int dashedRectGapWidth;
  /**
   * Description of the Field
   */
  protected int dashedRectGapHeight;

  /**
   * Description of the Field
   */
  protected Color focusColor;
  /**
   * Description of the Field
   */
  protected Skin skin = SkinLookAndFeel.getSkin();

  // ********************************
  //          Create PLAF
  // ********************************
  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinButtonUI();
  }

  // ********************************
  //         Paint Methods
  // ********************************

  /*
   *  These rectangles/insets are allocated once for all
   *  ButtonUI.paint() calls.  Re-using rectangles rather than
   *  allocating them in each paint call substantially reduced the time
   *  it took paint to run.  Obviously, this method can't be re-entered.
   */
  private static Rectangle viewRect = new Rectangle();
  private static Rectangle textRect = new Rectangle();
  private static Rectangle iconRect = new Rectangle();

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

    FontMetrics fm = g.getFontMetrics();

    Insets i = c.getInsets();

    viewRect.x = i.left;
    viewRect.y = i.top;
    viewRect.width = b.getWidth() - (i.right + viewRect.x);
    viewRect.height = b.getHeight() - (i.bottom + viewRect.y);

    textRect.x = textRect.y = textRect.width = textRect.height = 0;
    iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

    Font f = c.getFont();
    g.setFont(f);

    // layout the text and icon
    String text = SwingUtilities.layoutCompoundLabel(
        c, fm, b.getText(), b.getIcon(),
        b.getVerticalAlignment(), b.getHorizontalAlignment(),
        b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
        viewRect, iconRect, textRect,
        b.getText() == null ? 0 : b.getIconTextGap()
        );

    clearTextShiftOffset();

    // perform UI specific press action, e.g. Windows L&F shifts text
    if (model.isArmed() && model.isPressed()) {
      paintButtonPressed(g, b);
    }

    skin.getButton().paintButton(g, b);

    // Paint the Icon
    if (b.getIcon() != null) {
      paintIcon(g, c, iconRect);
    }

    if (text != null && !text.equals("")) {
      //PENDING(fred): BasicHTML is not used because package private in JDK1.2 (not in 1.3)
      View v = (View) c.getClientProperty("html");
      //BasicHTML.propertyKey
      if (v != null) {
        v.paint(g, textRect);
      }
      else {
        paintText(g, c, textRect, text);
      }
    }

    if (b.isFocusPainted() && b.hasFocus()) {
      // paint UI specific focus
      paintFocus(g, b, viewRect, textRect, iconRect);
    }
  }

  /**
   * Gets the FocusColor attribute of the SkinButtonUI object
   *
   * @return   The FocusColor value
   */
  protected Color getFocusColor() {
    return focusColor;
  }

  // ********************************
  //         Create Listeners
  // ********************************
  //    protected BasicButtonListener createButtonListener(AbstractButton b) {
  //	return new SkinButtonListener(b);
  //    }

  // ********************************
  //            Defaults
  // ********************************
  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  protected void installDefaults(final AbstractButton b) {
    super.installDefaults(b);

    String pp = getPropertyPrefix();
    dashedRectGapX = UIManager.getInt(pp + "dashedRectGapX");
    dashedRectGapY = UIManager.getInt(pp + "dashedRectGapY");
    dashedRectGapWidth = UIManager.getInt(pp + "dashedRectGapWidth");
    dashedRectGapHeight = UIManager.getInt(pp + "dashedRectGapHeight");
    focusColor = UIManager.getColor(pp + "focus");

    b.setBorderPainted(false);
    b.setFocusPainted(true);
    b.setOpaque(false);
    b.setRolloverEnabled(true);

    skin.getButton().installSkin(b);

  }

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  protected void uninstallDefaults(AbstractButton b) {
    super.uninstallDefaults(b);
    b.setBorderPainted(true);
    b.setFocusPainted(true);
    b.setOpaque(true);
  }

  /**
   * Description of the Method
   *
   * @param g         Description of Parameter
   * @param b         Description of Parameter
   * @param viewRect  Description of Parameter
   * @param textRect  Description of Parameter
   * @param iconRect  Description of Parameter
   */
  protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
    // focus painted same color as text on Basic??
    int width = b.getWidth();
    int height = b.getHeight();
    g.setColor(getFocusColor());
    BasicGraphicsUtils.drawDashedRect(g, dashedRectGapX, dashedRectGapY,
        width - dashedRectGapWidth, height - dashedRectGapHeight);
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param b  Description of Parameter
   */
  protected void paintButtonPressed(Graphics g, AbstractButton b) {
    setTextShiftOffset();
  }

}

