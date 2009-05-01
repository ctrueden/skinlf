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

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;

/**
 * Description of the Class
 *
 * @author    fred
 */
public final class SkinOptionPaneUI extends BasicOptionPaneUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinOptionPaneUI();
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
    // don't paint a root panel, a popup panel and glasspane
    if (c.getParent() instanceof JRootPane ||
        c.getClass().getName().endsWith("JPanelPopup") ||
        (c.getParent() instanceof RootPaneContainer &&
         ((((RootPaneContainer)c.getParent()).getGlassPane() == c)
          || (((RootPaneContainer)c.getParent()).getLayeredPane() == c)
          ))) {
      return;
    }

    // always paint a ContentPane
    if (c.getParent() instanceof RootPaneContainer &&
        (((RootPaneContainer)c.getParent()).getContentPane() == c)) {
      skin.getPersonality().paintDialog(g, c);
      return;
    }
    
    // finally paint a JComponent if it is opaque and if it has not changed
    // its background color
    if (c.isOpaque() && c.getBackground() instanceof ColorUIResource) {
      skin.getPersonality().paintDialog(g, c);
    }
  }

  protected void installDefaults() {
    super.installDefaults();
    skin.getPersonality().installSkin(optionPane);
  }

  protected void uninstallDefaults() {
    super.uninstallDefaults();
    skin.getPersonality().uninstallSkin(optionPane);
  }

}
