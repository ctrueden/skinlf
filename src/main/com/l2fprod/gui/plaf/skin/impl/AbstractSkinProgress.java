/*
 * ====================================================================
 * 
 * @PROJECT.FULLNAME@ @VERSION@ License.
 * 
 * Copyright (c) @YEAR@ L2FProd.com. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *  3. The end-user documentation included with the redistribution, if any,
 * must include the following acknowlegement: "This product includes software
 * developed by L2FProd.com (http://www.L2FProd.com/)." Alternately, this
 * acknowlegement may appear in the software itself, if and wherever such
 * third-party acknowlegements normally appear.
 *  4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not be
 * used to endorse or promote products derived from this software without prior
 * written permission. For written permission, please contact info@L2FProd.com.
 *  5. Products derived from this software may not be called "SkinLF" nor may
 * "SkinLF" appear in their names without prior written permission of
 * L2FProd.com.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * L2FPROD.COM OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package com.l2fprod.gui.plaf.skin.impl;

import java.awt.*;

import javax.swing.*;

import com.l2fprod.gui.plaf.skin.*;

/**
 * @author $Author: l2fprod $ @created 27 avril 2002
 * @version $Revision: 1.4 $, $Date: 2005-10-09 13:36:38 $
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
