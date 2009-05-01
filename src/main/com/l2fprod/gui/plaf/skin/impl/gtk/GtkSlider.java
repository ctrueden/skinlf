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
import com.l2fprod.gui.plaf.skin.SkinSlider;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkinSlider;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.GtkParser;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkSlider extends AbstractSkinSlider implements SkinSlider, SwingConstants {

  DefaultButton h_track, v_track;
  DefaultButton h_thumb, v_thumb;

  /**
   * Constructor for the GtkSlider object
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkSlider(GtkParser parser) throws Exception {
    h_thumb = GtkUtils.newButton(parser, "GtkRange",
        new String[]{"function", "orientation"},
        new String[]{"SLIDER", "HORIZONTAL"}, true);
    v_thumb = GtkUtils.newButton(parser, "GtkRange",
        new String[]{"function", "orientation"},
        new String[]{"SLIDER", "VERTICAL"}, true);

    h_track = GtkUtils.newButton(parser, "GtkRange",
        new String[]{"function", "detail", "orientation"},
        new String[]{"BOX", "trough", "HORIZONTAL"});
    v_track = GtkUtils.newButton(parser, "GtkRange",
        new String[]{"function", "detail", "orientation"},
        new String[]{"BOX", "trough", "VERTICAL"});
  }

  /**
   * Gets the PreferredSize attribute of the GtkSlider object
   *
   * @param slider  Description of Parameter
   * @return        The PreferredSize value
   */
  public Dimension getPreferredSize(JSlider slider, java.awt.Dimension uiPreferredSize) {
    if (slider.getOrientation() == HORIZONTAL) {
      uiPreferredSize.height = Math.max(uiPreferredSize.height, h_thumb.getHeight());
    } else {      
      uiPreferredSize.width = Math.max(uiPreferredSize.width, v_thumb.getWidth());
    }        
    return uiPreferredSize;
  }

  public Dimension getThumbSize(JSlider slider) {
    if (h_thumb != null) {
      if (slider.getOrientation() == HORIZONTAL) {
        return h_thumb.getPreferredSize();
      } else {
        return v_thumb.getPreferredSize();
      }
    }
    else {
      return null;
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
    c.setOpaque(false);
    return true;
  }

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param slider       Description of Parameter
   * @param trackBounds  Description of Parameter
   * @return             Description of the Returned Value
   */
  public boolean paintTrack(Graphics g, JSlider slider, Rectangle trackBounds) {
    if (h_track != null) {
      if (slider.getOrientation() == HORIZONTAL) {
        h_track.paint(g, 0, 0,
                      trackBounds.width, trackBounds.height, slider);
      } else {
        v_track.paint(g, 0, 0,
                      trackBounds.width, trackBounds.height, slider);
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param slider       Description of Parameter
   * @param thumbBounds  Description of Parameter
   * @return             Description of the Returned Value
   */
  public boolean paintThumb(Graphics g, JSlider slider, Rectangle thumbBounds) {
    // the UI translate the graphics to thumbBounds.x and .y
    if (h_thumb != null) {
      if (slider.getOrientation() == HORIZONTAL) {
        h_thumb.paint(g, 0, 0, h_thumb.getWidth(), h_thumb.getHeight(), slider);
      } else {
        v_thumb.paint(g, 0, 0, v_thumb.getWidth(), v_thumb.getHeight(), slider);
      }
      return true;
    } else {
      return false;
    }
  }

}
