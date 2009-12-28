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
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.text.View;

import java.awt.*;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class SkinRadioButtonUI extends BasicRadioButtonUI {

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
  //          Paint Methods
  // ********************************
  /*
   *  These Dimensions/Rectangles are allocated once for all
   *  RadioButtonUI.paint() calls.  Re-using rectangles
   *  rather than allocating them in each paint call substantially
   *  reduced the time it took paint to run.  Obviously, this
   *  method can't be re-entered.
   */
  private static Dimension size = new Dimension();
  private static Rectangle viewRect = new Rectangle();
  private static Rectangle iconRect = new Rectangle();
  private static Rectangle textRect = new Rectangle();


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
    return new SkinRadioButtonUI();
  }

  // ********************************
  //           Defaults
  // ********************************
  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  public void installDefaults(AbstractButton b) {
    super.installDefaults(b);
    //    dashedRectGapX = ((Integer)UIManager.get("Button.dashedRectGapX")).intValue();
    //	    dashedRectGapY = ((Integer)UIManager.get("Button.dashedRectGapY")).intValue();
    //	    dashedRectGapWidth = ((Integer)UIManager.get("Button.dashedRectGapWidth")).intValue();
    //	    dashedRectGapHeight = ((Integer)UIManager.get("Button.dashedRectGapHeight")).intValue();
    focusColor = Color.black;
    //UIManager.getColor(getPropertyPrefix() + "focus");
    b.setOpaque(false);
  }

  /**
   * paint the radio button
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public synchronized void paint(Graphics g, JComponent c) {
    AbstractButton b = (AbstractButton) c;
    ButtonModel model = b.getModel();

    Font f = c.getFont();
    g.setFont(f);
    FontMetrics fm = g.getFontMetrics();

    size = b.getSize(size);
    viewRect.x = viewRect.y = 0;
    viewRect.width = size.width;
    viewRect.height = size.height;
    iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
    textRect.x = textRect.y = textRect.width = textRect.height = 0;

    Icon icon = null;

	if (b.isSelected()) {
		if (b.isRolloverEnabled() && model.isRollover()) {
			icon = b.getRolloverSelectedIcon();
			if (icon == null) {
				icon = b.getSelectedIcon();
			}
		} else {
			icon = b.getSelectedIcon();
		}
	} else if (b.isRolloverEnabled() && model.isRollover()) {
           icon = b.getRolloverIcon();
    } 
		
	if (icon == null) {
		icon = b.getIcon();
	}

    if (icon == null) {
      icon = skin.getButton().getRadioIcon(b);
    }

    String text =
        SwingUtilities.layoutCompoundLabel(
        c, fm, b.getText(), icon,
        b.getVerticalAlignment(), b.getHorizontalAlignment(),
        b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
        viewRect, iconRect, textRect, getDefaultTextIconGap(b));

    // fill background
    if (c.isOpaque()) {
      g.setColor(b.getBackground());
      g.fillRect(0, 0, size.width, size.height);
    }

    if (icon != null) {
      icon.paintIcon(c, g, iconRect.x, iconRect.y);
    }

    // Draw the Text
    if (text != null) {
      View v = (View) c.getClientProperty("html");
      // BasicHTML.propertyKey
      if (v != null) {
        v.paint(g, textRect);
      }
      else {
        if (model.isEnabled()) {
          // *** paint the text normally
          g.setColor(b.getForeground());
          BasicGraphicsUtils.drawString(g, text, model.getMnemonic(),
              textRect.x,
              textRect.y + fm.getAscent());
        }
        else {
          // *** paint the text disabled
          g.setColor(b.getBackground().brighter());
          BasicGraphicsUtils.drawString(g, text, model.getMnemonic(),
              textRect.x + 1,
              textRect.y + fm.getAscent() + 1);
          g.setColor(b.getBackground().darker());
          BasicGraphicsUtils.drawString(g, text, model.getMnemonic(),
              textRect.x,
              textRect.y + fm.getAscent());
        }
      }
    }

    if (b.hasFocus() && b.isFocusPainted() &&
        textRect.width > 0 && textRect.height > 0) {
      paintFocus(g, textRect, size);
    }
  }

  /**
   * Gets the FocusColor attribute of the SkinRadioButtonUI object
   *
   * @return   The FocusColor value
   */
  protected Color getFocusColor() {
    return focusColor;
  }

  /**
   * Description of the Method
   *
   * @param g         Description of Parameter
   * @param textRect  Description of Parameter
   * @param d         Description of Parameter
   */
  protected void paintFocus(Graphics g, Rectangle textRect, Dimension d) {
    g.setColor(getFocusColor());
    BasicGraphicsUtils.drawDashedRect(g, textRect.x, textRect.y, textRect.width, textRect.height);
  }

}

