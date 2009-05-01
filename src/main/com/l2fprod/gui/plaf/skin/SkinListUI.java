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

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:43 $
 */
public final class SkinListUI extends BasicListUI {

  /**
   * Description of the Method
   */
  protected void installDefaults() {
    super.installDefaults();
    list.setCellRenderer(SkinLookAndFeel.getSkin().getPersonality().createListCellRenderer());
  }

  /**
   * Description of the Method
   *
   * @param list  Description of Parameter
   * @return      Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent list) {
    return new SkinListUI();
  }

}
