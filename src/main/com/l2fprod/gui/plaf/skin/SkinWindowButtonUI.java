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
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinWindowButtonUI extends BasicButtonUI {

  private static Rectangle iconRect = new Rectangle();


  // ********************************
  //          Create PLAF
  // ********************************
  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinWindowButtonUI();
  }
  
  // ********************************
  //          Paint Methods
  // ********************************

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
    AbstractButton b = (AbstractButton) c;

    iconRect.x = iconRect.y = 0;
    iconRect.width = b.getWidth();
    iconRect.height = b.getHeight();

    // Paint the Icon
    if (b.getIcon() != null) {
      paintIcon(g, c, iconRect);
    }

  }

  // ********************************
  //         Create Listeners
  // ********************************
  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   * @return   Description of the Returned Value
   */
  protected BasicButtonListener createButtonListener(AbstractButton b) {
    return new BasicButtonListener(b);
  }

  // ********************************
  //            Defaults
  // ********************************
  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  protected void installDefaults(final AbstractButton b) {
    super.installDefaults(b);
    b.setBorderPainted(false);
    b.setFocusPainted(false);
    b.setOpaque(false);

  }

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  protected void uninstallDefaults(AbstractButton b) {
    super.uninstallDefaults(b);
    b.setOpaque(true);
    b.setBorderPainted(true);
    b.setFocusPainted(true);
  }

}

