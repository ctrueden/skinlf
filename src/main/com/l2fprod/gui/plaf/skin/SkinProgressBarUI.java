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
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:24:43 $
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
    if (OS.isOneDotFourOrMore() && progressBar.isIndeterminate()) {
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

