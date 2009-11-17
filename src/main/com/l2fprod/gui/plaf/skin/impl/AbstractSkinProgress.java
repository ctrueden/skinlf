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
package com.l2fprod.gui.plaf.skin.impl;

import java.awt.*;

import javax.swing.*;

import com.l2fprod.gui.plaf.skin.*;

/**
 * @author $Author: l2fprod $ @created 27 avril 2002
 * @version $Revision: 1.5 $, $Date: 2009-11-17 18:53:47 $
 */
public abstract class AbstractSkinProgress
  extends AbstractSkinComponent
  implements SkinProgress {

  protected DefaultButton progressBarBackHorizontal;
  protected DefaultButton progressBarHorizontal;

  protected DefaultButton progressBarBackVertical;
  protected DefaultButton progressBarVertical;

  protected int orientation = JProgressBar.HORIZONTAL;
  protected final Dimension minimumSize = new Dimension(50, 17);

  /**
   * Set UIManager.put("ProgressBar.windowAnimation", Boolean.TRUE) to use the
   * window painting mode if the progress bar is inderterminate.
   */
  protected boolean useWindow;

  public AbstractSkinProgress() {
    useWindow =
      Boolean.TRUE.equals(UIManager.get("ProgressBar.windowAnimation"));
  }

  /**
   * Gets the MinimumSize attribute of the AbstractSkinProgress object
   * 
   * @param progress Description of Parameter
   * @return The MinimumSize value
   */
  public final Dimension getMinimumSize(javax.swing.JProgressBar progress) {
    if (JProgressBar.VERTICAL == progress.getOrientation()) {
      return progressBarBackVertical.getMinimumSize();
    } else {
      return progressBarBackHorizontal.getMinimumSize();
    }
  }

  /**
   * Description of the Method
   * 
   * @return Description of the Returned Value
   */
  public final boolean status() {
    return progressBarHorizontal != null;
  }

  /**
   * Description of the Method
   * 
   * @param c Description of Parameter
   * @return Description of the Returned Value
   */
  public final boolean installSkin(JComponent c) {
    return true;
  }

  protected final void paintBackBar(Graphics g, JProgressBar progress) {
    if (JProgressBar.VERTICAL == progress.getOrientation()) {
      if (progressBarBackVertical != null) {
        progressBarBackVertical.paint(
          g,
          0,
          0,
          progress.getWidth(),
          progress.getHeight(),
          progress);
      }
    } else {
      if (progressBarBackHorizontal != null) {
        progressBarBackHorizontal.paint(
          g,
          0,
          0,
          progress.getWidth(),
          progress.getHeight(),
          progress);
      }
    }
  }

  /**
   * Description of the Method
   * 
   * @param g Description of Parameter
   * @param progress Description of Parameter
   * @return Description of the Returned Value
   */
  public final boolean paintProgress(
    java.awt.Graphics g,
    javax.swing.JProgressBar progress) {
    int x, y, width, height;
    x = y = width = height = 0;

    paintBackBar(g, progress);

    if ((progress.getValue() > progress.getMinimum())) {
      if (JProgressBar.VERTICAL == progress.getOrientation()) {
        height =
          (int) ((double)progress.getValue()
            * progress.getHeight()
            / progress.getMaximum());
        y = progress.getHeight() - height;
        width = progress.getWidth();
        progressBarVertical.paint(g, x, y, width, height, progress);
      } else {
        y = 0;
        width =
          (int) ((double)progress.getValue()
            * progress.getWidth()
            / progress.getMaximum());
        height = progress.getHeight();
        progressBarHorizontal.paint(g, x, y, width, height, progress);
      }
    }
    
    return true;
  }

  public final boolean paintIndeterminateProgress(
    Graphics g,
    JProgressBar progress,
    Rectangle rec) {
    paintBackBar(g, progress);

    if (JProgressBar.VERTICAL == progress.getOrientation()) {
      if (useWindow) {
        progressBarVertical.paintWindow(
          g,
          progress.getWidth(),
          progress.getHeight(),
          rec.x,
          rec.y,
          rec.width,
          rec.height,
          progress);
      } else {
        progressBarVertical.paint(
          g,
          rec.x,
          rec.y,
          rec.width,
          rec.height,
          progress);
      }
    } else {
      if (useWindow) {
        progressBarHorizontal.paintWindow(
          g,
          progress.getWidth(),
          progress.getHeight(),
          rec.x,
          rec.y,
          rec.width,
          rec.height,
          progress);
      } else {
        progressBarHorizontal.paint(
          g,
          rec.x,
          rec.y,
          rec.width,
          rec.height,
          progress);
      }
    }
    return true;
  }
}
