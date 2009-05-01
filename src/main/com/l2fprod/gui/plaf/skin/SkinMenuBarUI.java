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
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinMenuBarUI extends BasicMenuBarUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
    skin.getPersonality().paintDialog(g, c);
  }

  /**
   * Description of the Method
   */
  protected void installDefaults() {
    super.installDefaults();
    skin.getPersonality().installSkin(menuBar);
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinMenuBarUI();
  }

}
