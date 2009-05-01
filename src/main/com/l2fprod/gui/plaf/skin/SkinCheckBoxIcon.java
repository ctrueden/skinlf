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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.plaf.UIResource;

/**
 * Created on 05/04/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:24:42 $
 */
final class SkinCheckBoxIcon implements Icon, java.io.Serializable, UIResource {

  /**
   * Gets the IconHeight attribute of the SkinCheckBoxIcon object
   *
   * @return   The IconHeight value
   */
  public int getIconHeight() {
    return SkinLookAndFeel.getSkin().getButton().getCheckBoxIconSize().height;
  }

  /**
   * Gets the IconWidth attribute of the SkinCheckBoxIcon object
   *
   * @return   The IconWidth value
   */
  public int getIconWidth() {
    return SkinLookAndFeel.getSkin().getButton().getCheckBoxIconSize().width;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @param g  Description of Parameter
   * @param x  Description of Parameter
   * @param y  Description of Parameter
   */
  public void paintIcon(Component c, Graphics g, int x, int y) {
    SkinLookAndFeel.getSkin().getButton().getRadioIcon((AbstractButton) c).paintIcon(c, g, x, y);
  }

}
