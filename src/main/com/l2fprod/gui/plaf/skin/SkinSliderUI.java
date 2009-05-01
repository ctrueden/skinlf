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
import java.awt.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinSliderUI extends BasicSliderUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Constructor for the SkinSliderUI object
   *
   * @param b  Description of Parameter
   */
  public SkinSliderUI(JSlider b) {
    super(b);
  }

  public Dimension getPreferredHorizontalSize() {
    return skin.getSlider().getPreferredSize(slider, super.getPreferredHorizontalSize());
  }
  
  public Dimension getPreferredVerticalSize() {
    return skin.getSlider().getPreferredSize(slider, super.getPreferredVerticalSize());
  }
  
  public Dimension getMinimumHorizontalSize() {
    return skin.getSlider().getPreferredSize(slider, super.getMinimumHorizontalSize());
  }
  
  public Dimension getMinimumVerticalSize() {
    return skin.getSlider().getPreferredSize(slider, super.getMinimumVerticalSize());
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   */
  public void paintTrack(Graphics g) {
    Rectangle trackBounds = trackRect;

    int cx;

    int cy;
    if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
      cy = (trackBounds.height / 2) - 2;
      g.translate(trackBounds.x, trackBounds.y + cy);
      skin.getSlider().paintTrack(g, slider, trackBounds);
      g.translate(-trackBounds.x, -(trackBounds.y + cy));
    }
    else {
      cx = (trackBounds.width / 2) - 2;
      g.translate(trackBounds.x + cx, trackBounds.y);
      skin.getSlider().paintTrack(g, slider, trackBounds);
      g.translate(-(trackBounds.x + cx), -trackBounds.y);
    }
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   */
  public void paintThumb(Graphics g) {
    Rectangle knobBounds = thumbRect;

    g.translate(knobBounds.x, knobBounds.y);

    skin.getSlider().paintThumb(g, slider, knobBounds);

    g.translate(-knobBounds.x, -knobBounds.y);
  }

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  protected void installDefaults(JSlider b) {
    super.installDefaults(b);
    skin.getSlider().installSkin(b);
  }

  protected Dimension getThumbSize() {
    Dimension dim = skin.getSlider().getThumbSize(slider);
    return dim==null?super.getThumbSize():dim;
  }

  /**
   * Description of the Method
   *
   * @param x  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent x) {
    return new SkinSliderUI((JSlider) x);
  }

}
