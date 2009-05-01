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
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:23:51 $
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
