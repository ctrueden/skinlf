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
package com.l2fprod.gui.plaf.skin.impl;

import java.awt.*;
import javax.swing.*;

import com.l2fprod.gui.plaf.skin.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:42 $
 */
public class AbstractSkinTab extends AbstractSkinComponent implements SkinTab {

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g             Description of Parameter
   * @param tabPlacement  Description of Parameter
   * @param isSelected    Description of Parameter
   * @param x             Description of Parameter
   * @param y             Description of Parameter
   * @param w             Description of Parameter
   * @param h             Description of Parameter
   * @return              Description of the Returned Value
   */
  public boolean paintTab(Graphics g, int tabPlacement, boolean isSelected, int x, int y, int w, int h) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g              Description of Parameter
   * @param tabPlacement   Description of Parameter
   * @param selectedIndex  Description of Parameter
   * @param x              Description of Parameter
   * @param y              Description of Parameter
   * @param w              Description of Parameter
   * @param h              Description of Parameter
   * @return               Description of the Returned Value
   */
  public boolean paintContent(java.awt.Graphics g, int tabPlacement, int selectedIndex,
      int x, int y, int w, int h) {
    return false;
  }

  public boolean paintGap(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h) {
    return false;
  }  
}
