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
package com.l2fprod.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;

import com.l2fprod.gui.plaf.skin.SkinTitlePane;
import com.l2fprod.gui.plaf.skin.SkinWindowButton;

/**
 * @author    $Author: zombi $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2005-07-02 21:47:29 $
 */
public class WindowUtils {

  /**
   * Description of the Method
   *
   * @param w  Description of Parameter
   */
  public static void centerOnScreen(Window w) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension size = w.getSize();
    w.setLocation((screenSize.width - size.width) / 2,
        (screenSize.height - size.height) / 2);
  }

  /**
   * Description of the Method
   *
   * @param w  Description of Parameter
   * @param x  Description of Parameter
   * @param y  Description of Parameter
   */
  public static void sizeTo(Window w, double x, double y) {
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    w.setSize((int) (size.width * x), (int) (size.height * y));
  }

  public static JButton getWindowButton(JFrame window, String name) {
      return getWindowButton(window.getRootPane(), name);
  }

  public static JButton getWindowButton(JDialog window, String name) {
      return getWindowButton(window.getRootPane(), name);
  }

  public static JButton getWindowButton(JRootPane rootPane, String name) {
      JLayeredPane jlp = rootPane.getLayeredPane();
      for (int i=0;i<jlp.getComponentCount();i++) {
          Component comp = jlp.getComponent(i);
          if (comp instanceof SkinTitlePane) {
              SkinTitlePane  stp = (SkinTitlePane) comp;
              for (int j=0;j<stp.getComponentCount();j++) {
                  Component comp2 = stp.getComponent(j);
                  if (comp2 instanceof SkinWindowButton) {
                      SkinWindowButton swb = (SkinWindowButton) comp2;
                      if (name.equals(swb.getActionCommand()))
                          return swb;
                  }
              }
              
          }
      }
      return null;
      
  }

}
