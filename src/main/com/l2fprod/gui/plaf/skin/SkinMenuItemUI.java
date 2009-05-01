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
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:24:43 $
 */
public class SkinMenuItemUI extends BasicMenuItemUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
    Graphics2D g2d = (Graphics2D)g;
    AlphaComposite alpha = (AlphaComposite)((JComponent)menuItem.getParent())
      .getClientProperty("alpha");
    Composite oldComposite = g2d.getComposite();
    if (alpha != null) {
      g2d.setComposite(alpha);
    }
    // end of if (alpha == null)

    skin.getPersonality().paintMenuItem(g, menuItem);

    if (alpha != null) {
      g2d.setComposite(oldComposite);
    }
  }

  /**
   * Description of the Method
   */
  protected void installDefaults() {
    super.installDefaults();
    menuItem.setOpaque(false);
  }

  /**
   * Description of the Method
   */
  protected void uninstallDefaults() {
    super.uninstallDefaults();
    menuItem.setOpaque(true);
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinMenuItemUI();
  }

}
