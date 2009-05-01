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
package com.l2fprod.gui.plaf.xtra;

import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * XTra Scrollbar. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:43 $
 */
public class XTraScrollBarUI extends BasicScrollBarUI {

  /**
   * Description of the Field
   */
  protected boolean useAlternateLayout = Boolean.TRUE.equals(UIManager.get("ScrollBar.alternateLayout"));

  /**
   * Sets the ThumbBounds attribute of the XTraScrollBarUI object
   *
   * @param x  The new ThumbBounds value
   * @param y  The new ThumbBounds value
   * @param w  The new ThumbBounds value
   * @param h  The new ThumbBounds value
   */
  public void setThumbBounds(int x, int y, int w, int h) {
    super.setThumbBounds(x, y, w, h);
  }

  /**
   * Gets the Dragging attribute of the XTraScrollBarUI object
   *
   * @return   The Dragging value
   */
  public boolean isDragging() {
    return isDragging;
  }

  /**
   * Gets the ThumbBounds attribute of the XTraScrollBarUI object
   *
   * @return   The ThumbBounds value
   */
  public Rectangle getThumbBounds() {
    return super.getThumbBounds();
  }

  /**
   * Gets the TrackBounds attribute of the XTraScrollBarUI object
   *
   * @return   The TrackBounds value
   */
  public Rectangle getTrackBounds() {
    return super.getTrackBounds();
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public JButton decrButton() {
    return decrButton;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public JButton incrButton() {
    return incrButton;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public JScrollBar scrollbar() {
    return scrollbar;
  }

  /**
   * Description of the Method
   *
   * @param sb  Description of Parameter
   */
  protected void layoutVScrollbar(JScrollBar sb) {
    if (useAlternateLayout) {
      alternateLayoutVScrollbar(sb);
    }
    else {
      super.layoutVScrollbar(sb);
    }
  }

  /**
   * Description of the Method
   *
   * @param sb  Description of Parameter
   */
  protected void alternateLayoutVScrollbar(JScrollBar sb) {
    Dimension sbSize = sb.getSize();
    Insets sbInsets = sb.getInsets();

    /*
     *  Width and left edge of the buttons and thumb.
     */
    int itemW = sbSize.width - (sbInsets.left + sbInsets.right);
    int itemX = sbInsets.left;

    /*
     *  Nominal locations of the buttons, assuming their preferred
     *  size will fit.
     */
    int incrButtonH = incrButton.getPreferredSize().height;
    int incrButtonY = sbSize.height - (sbInsets.bottom + incrButtonH);

    int decrButtonH = decrButton.getPreferredSize().height;
    int decrButtonY = incrButtonY - decrButtonH;

    /*
     *  The thumb must fit within the height left over after we
     *  subtract the preferredSize of the buttons and the insets.
     */
    int sbInsetsH = sbInsets.top + sbInsets.bottom;
    int sbButtonsH = decrButtonH + incrButtonH;
    float trackH = sbSize.height - (sbInsetsH + sbButtonsH);

    /*
     *  Compute the height and origin of the thumb.   The case
     *  where the thumb is at the bottom edge is handled specially
     *  to avoid numerical problems in computing thumbY.  Enforce
     *  the thumbs min/max dimensions.  If the thumb doesn't
     *  fit in the track (trackH) we'll hide it later.
     */
    float min = sb.getMinimum();
    float extent = sb.getVisibleAmount();
    float range = sb.getMaximum() - min;
    float value = sb.getValue();

    int thumbH = (range <= 0)
        ? getMaximumThumbSize().height : (int) (trackH * (extent / range));
    thumbH = Math.max(thumbH, getMinimumThumbSize().height);
    thumbH = Math.min(thumbH, getMaximumThumbSize().height);

    int thumbY = decrButtonY - thumbH;
    //incrButtonY - thumbH;
    if (sb.getValue() < (sb.getMaximum() - sb.getVisibleAmount())) {
      float thumbRange = trackH - thumbH;
      thumbY = (int) (0.5f + (thumbRange * ((value - min) / (range - extent))));
      //thumbY +=  decrButtonY + decrButtonH;
    }

    /*
     *  If the buttons don't fit, allocate half of the available
     *  space to each and move the lower one (incrButton) down.
     */
    int sbAvailButtonH = (sbSize.height - sbInsetsH);
    if (sbAvailButtonH < sbButtonsH) {
      incrButtonH = decrButtonH = sbAvailButtonH / 2;
      incrButtonY = sbSize.height - (sbInsets.bottom + incrButtonH);
    }
    decrButton.setBounds(itemX, decrButtonY, itemW, decrButtonH);
    incrButton.setBounds(itemX, incrButtonY, itemW, incrButtonH);

    /*
     *  Update the trackRect field.
     */
    int itrackY = sbInsets.top;
    int itrackH = decrButtonY - itrackY;
    trackRect.setBounds(itemX, itrackY, itemW, itrackH);

    /*
     *  If the thumb isn't going to fit, zero it's bounds.  Otherwise
     *  make sure it fits between the buttons.  Note that setting the
     *  thumbs bounds will cause a repaint.
     */
    if (thumbH >= (int) trackH) {
      setThumbBounds(0, 0, 0, 0);
    }
    else {
      if ((thumbY + thumbH) > decrButtonY) {
        thumbY = decrButtonY - thumbH;
      }
      /*
       *  if (thumbY  < (decrButtonY + decrButtonH)) {
       *  thumbY = decrButtonY + decrButtonH + 1;
       *  }
       */
      setThumbBounds(itemX, thumbY, itemW, thumbH);
    }
  }

  /**
   * Description of the Method
   *
   * @param sb  Description of Parameter
   */
  protected void layoutHScrollbar(JScrollBar sb) {
    if (useAlternateLayout) {
      alternateLayoutHScrollbar(sb);
    }
    else {
      super.layoutHScrollbar(sb);
    }
  }

  /**
   * Description of the Method
   *
   * @param sb  Description of Parameter
   */
  protected void alternateLayoutHScrollbar(JScrollBar sb) {
    Dimension sbSize = sb.getSize();
    Insets sbInsets = sb.getInsets();

    /*
     *  Height and top edge of the buttons and thumb.
     */
    int itemH = sbSize.height - (sbInsets.top + sbInsets.bottom);
    int itemY = sbInsets.top;

    /*
     *  Nominal locations of the buttons, assuming their preferred
     *  size will fit.
     */
    int incrButtonW = incrButton.getPreferredSize().width;
    int incrButtonX = sbSize.width - (sbInsets.right + incrButtonW);

    int decrButtonW = decrButton.getPreferredSize().width;
    int decrButtonX = incrButtonX - decrButtonW;

    /*
     *  The thumb must fit within the width left over after we
     *  subtract the preferredSize of the buttons and the insets.
     */
    int sbInsetsW = sbInsets.left + sbInsets.right;
    int sbButtonsW = decrButtonW + incrButtonW;
    float trackW = sbSize.width - (sbInsetsW + sbButtonsW);

    /*
     *  Compute the width and origin of the thumb.  Enforce
     *  the thumbs min/max dimensions.  The case where the thumb
     *  is at the right edge is handled specially to avoid numerical
     *  problems in computing thumbX.  If the thumb doesn't
     *  fit in the track (trackH) we'll hide it later.
     */
    float min = sb.getMinimum();
    float extent = sb.getVisibleAmount();
    float range = sb.getMaximum() - min;
    float value = sb.getValue();

    int thumbW = (range <= 0)
        ? getMaximumThumbSize().width : (int) (trackW * (extent / range));
    thumbW = Math.max(thumbW, getMinimumThumbSize().width);
    thumbW = Math.min(thumbW, getMaximumThumbSize().width);

    int thumbX = decrButtonX - thumbW;
    if (sb.getValue() < (sb.getMaximum() - sb.getVisibleAmount())) {
      float thumbRange = trackW - thumbW;
      thumbX = (int) (0.5f + (thumbRange * ((value - min) / (range - extent))));
      //thumbX +=  decrButtonX + decrButtonW;
    }

    /*
     *  If the buttons don't fit, allocate half of the available
     *  space to each and move the right one (incrButton) over.
     */
    int sbAvailButtonW = (sbSize.width - sbInsetsW);
    if (sbAvailButtonW < sbButtonsW) {
      incrButtonW = decrButtonW = sbAvailButtonW / 2;
      incrButtonX = sbSize.width - (sbInsets.right + incrButtonW);
    }

    decrButton.setBounds(decrButtonX, itemY, decrButtonW, itemH);
    incrButton.setBounds(incrButtonX, itemY, incrButtonW, itemH);

    /*
     *  Update the trackRect field.
     */
    int itrackX = sbInsets.left;
    //decrButtonX + decrButtonW;
    int itrackW = decrButtonX - itrackX;
    trackRect.setBounds(itrackX, itemY, itrackW, itemH);

    /*
     *  Make sure the thumb fits between the buttons.  Note
     *  that setting the thumbs bounds causes a repaint.
     */
    if (thumbW >= (int) trackW) {
      setThumbBounds(0, 0, 0, 0);
    }
    else {
      if (thumbX + thumbW > incrButtonX) {
        thumbX = incrButtonX - thumbW;
      }
      /*
       *  if (thumbX  < decrButtonX + decrButtonW) {
       *  thumbX = decrButtonX + decrButtonW + 1;
       *  }
       */
      setThumbBounds(thumbX, itemY, thumbW, itemH);
    }
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected TrackListener createTrackListener() {
    if (useAlternateLayout) {
      return new MyTrackListener();
    }
    else {
      return super.createTrackListener();
    }
  }

  /**
   * Description of the Method
   *
   * @param x  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent x) {
    return new XTraScrollBarUI();
  }

  /**
   * Track mouse drags.
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class MyTrackListener
       extends TrackListener {
    /**
     * Set the models value to the position of the top/left of the thumb
     * relative to the origin of the track.
     *
     * @param e  Description of Parameter
     */
    public void mouseDragged(MouseEvent e) {
      if (!XTraScrollBarUI.this.scrollbar().isEnabled() || !XTraScrollBarUI.this.isDragging()) {
        return;
      }

      Insets sbInsets = XTraScrollBarUI.this.scrollbar().getInsets();

      BoundedRangeModel model = XTraScrollBarUI.this.scrollbar().getModel();
      Rectangle thumbR = XTraScrollBarUI.this.getThumbBounds();
      int thumbMin;
      int thumbMax;
      int thumbPos;

      if (XTraScrollBarUI.this.scrollbar().getOrientation() == JScrollBar.VERTICAL) {
        thumbMin = sbInsets.top;
        //decrButton.getY() + decrButton.getHeight();
        thumbMax = XTraScrollBarUI.this.decrButton().getY()
        /*
         *  incrButton.getY()
         */
            - XTraScrollBarUI.this.getThumbBounds().height;
        thumbPos = Math.min(thumbMax, Math.max(thumbMin, (e.getY() - offset)));
        XTraScrollBarUI.this.setThumbBounds(thumbR.x, thumbPos, thumbR.width, thumbR.height);
      }
      else {
        thumbMin = sbInsets.left;
        //decrButton.getX() + decrButton.getWidth();
        thumbMax = XTraScrollBarUI.this.decrButton().getX()
        /*
         *  incrButton.getX()
         */
            - XTraScrollBarUI.this.getThumbBounds().width;
        thumbPos = Math.min(thumbMax, Math.max(thumbMin, (e.getX() - offset)));
        XTraScrollBarUI.this.setThumbBounds(thumbPos, thumbR.y, thumbR.width, thumbR.height);
      }

      /*
       *  Set the scrollbars value.  If the thumb has reached the end of
       *  the scrollbar, then just set the value to its maximum.  Otherwise
       *  compute the value as accurately as possible.
       */
      if (thumbPos == thumbMax) {
        XTraScrollBarUI.this.scrollbar().setValue(model.getMaximum() - model.getExtent());
      }
      else {
        float valueMax = model.getMaximum() - model.getExtent();
        float valueRange = valueMax - model.getMinimum();
        float thumbValue = thumbPos - thumbMin;
        float thumbRange = thumbMax - thumbMin;
        int value = (int) (0.5 + ((thumbValue / thumbRange) * valueRange));
        XTraScrollBarUI.this.scrollbar().setValue(value + model.getMinimum());
      }
    }
  }

}

