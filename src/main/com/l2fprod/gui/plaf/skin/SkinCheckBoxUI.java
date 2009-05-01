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

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinCheckBoxUI extends SkinRadioButtonUI {

  private final static String propertyPrefix = "CheckBox" + ".";

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
    return new SkinCheckBoxUI();
  }

  /**
   * Gets the PropertyPrefix attribute of the SkinCheckBoxUI object
   *
   * @return   The PropertyPrefix value
   */
  public String getPropertyPrefix() {
    return propertyPrefix;
  }

  // ********************************
  //          Defaults
  // ********************************
  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   */
  public void installDefaults(AbstractButton b) {
    super.installDefaults(b);
    icon = UIManager.getIcon(getPropertyPrefix() + "icon");
  }

}

