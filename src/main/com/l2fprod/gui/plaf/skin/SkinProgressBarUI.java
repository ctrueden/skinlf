/* ====================================================================
 *
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright (c) @YEAR@ L2FProd.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by L2FProd.com
 *        (http://www.L2FProd.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not
 *    be used to endorse or promote products derived from this software
 *    without prior written permission. For written permission, please
 *    contact info@L2FProd.com.
 *
 * 5. Products derived from this software may not be called "SkinLF"
 *    nor may "SkinLF" appear in their names without prior written
 *    permission of L2FProd.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL L2FPROD.COM OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package com.l2fprod.gui.plaf.skin;

import com.l2fprod.util.OS;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2003-12-06 21:53:26 $
 */
public final class SkinProgressBarUI extends BasicProgressBarUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**                                                             
   * Used to hold the location and size of the bouncing box (returned
   * by getBox) to be painted.  
   */                                                             
  private Rectangle boxRect;

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
  	//if the progessBar is indererminate draw a window on the full 
  	//progressBar the size of the box to be shown.  That box is determine
  	//in the basicprogressbarui getBox() method
    if (OS.isOneDotFour() && progressBar.isIndeterminate()) {
      skin.getProgress().
        paintIndeterminateProgress(g, progressBar, getBox(boxRect));
    } else {
	    skin.getProgress().paintProgress(g, progressBar);
    }

    int barRectX = 0;
    int barRectY = 0;
    int barRectWidth = progressBar.getWidth();
    int barRectHeight = progressBar.getHeight();
    Insets b = progressBar.getInsets();
    
    // area for border
    barRectX += b.left;
    barRectY += b.top;
    barRectWidth -= (b.right + barRectX);
    barRectHeight -= (b.bottom + barRectY);
    int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

    // Deal with possible text painting
    if (progressBar.isStringPainted()) {
      paintString(g, barRectX, barRectY,
          barRectWidth, barRectHeight,
          amountFull, b);
    }
  }

  /**
   * Description of the Method
   */
  protected void installDefaults() {
    super.installDefaults();
    progressBar.setBorder(null);
    progressBar.setOpaque(true);
    progressBar.setBorderPainted(false);
    progressBar.setMinimumSize(skin.getProgress().getMinimumSize(progressBar));
  }

  public void installUI(JComponent c) {
    super.installUI(c);
    if (OS.isOneDotFour()) {
      //JDK 1.4 has a bug where all the support for indeterminate is
      //not recreated when a look and feel is changed at runtime thus
      //resulting in nullpointerexception in updateSize():
      //java.lang.NullPointerException
      //  at javax.swing.plaf.basic.BasicProgressBarUI.updateSizes(BasicProgressBarUI.java:433)
      //  at javax.swing.plaf.basic.BasicProgressBarUI.getBox(BasicProgressBarUI.java:375)
      //  at com.l2fprod.gui.plaf.skin.SkinProgressBarUI.paint(SkinProgressBarUI.java:84)
      //
      //plus it does not re-enable the animation when the lnf has
      //changed. The following code fixes this.
      //
      //logged as http://developer.java.sun.com/developer/bugParade/bugs/4862295.html 
      if (progressBar.isIndeterminate()) {
        progressBar.setIndeterminate(false);
        progressBar.setIndeterminate(true);
      }
    }
  }

  /**
   * Description of the Method
   */
  protected void uninstallDefaults() {
    super.uninstallDefaults();
    progressBar.setOpaque(true);
    progressBar.setBorderPainted(true);
  }

  /**
   * Description of the Method
   *
   * @param x  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent x) {
    return new SkinProgressBarUI();
  }

}

