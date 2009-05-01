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

import com.l2fprod.gui.plaf.xtra.XTraScrollBarUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinScrollBarUI extends XTraScrollBarUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Gets the PreferredSize attribute of the SkinScrollBarUI object
   *
   * @param c  Description of Parameter
   * @return   The PreferredSize value
   */
  public Dimension getPreferredSize(JComponent c) {
    return skin.getScrollbar().getPreferredSize((JScrollBar) c);
  }

  /**
   * Gets the MinimumThumbSize attribute of the SkinScrollBarUI object
   *
   * @return   The MinimumThumbSize value
   */
  protected Dimension getMinimumThumbSize() {
    return skin.getScrollbar().getMinimumThumbSize();
  }

  /**
   * Description of the Method
   *
   * @param orientation  Description of Parameter
   * @return             Description of the Returned Value
   */
  protected JButton createDecreaseButton(int orientation) {
    return new SkinArrowButton(orientation);
  }

  /**
   * Description of the Method
   *
   * @param orientation  Description of Parameter
   * @return             Description of the Returned Value
   */
  protected JButton createIncreaseButton(int orientation) {
    return new SkinArrowButton(orientation);
  }

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param c            Description of Parameter
   * @param trackBounds  Description of Parameter
   */
  protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
    //        g.setColor(trackColor);
    //        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

    g.translate(trackBounds.x, trackBounds.y);
    skin.getScrollbar().paintTrack(g, scrollbar, trackBounds);
    g.translate(-trackBounds.x, -trackBounds.y);

    if (trackHighlight == DECREASE_HIGHLIGHT) {
      paintDecreaseHighlight(g);
    }
    else if (trackHighlight == INCREASE_HIGHLIGHT) {
      paintIncreaseHighlight(g);
    }
  }

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param c            Description of Parameter
   * @param thumbBounds  Description of Parameter
   */
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
    if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
      return;
    }

    g.translate(thumbBounds.x, thumbBounds.y);

    skin.getScrollbar().paintThumb(g, scrollbar, thumbBounds);

    g.translate(-thumbBounds.x, -thumbBounds.y);
  }

  /**
   * Description of the Method
   *
   * @param x  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent x) {
    return new SkinScrollBarUI();
  }

}

