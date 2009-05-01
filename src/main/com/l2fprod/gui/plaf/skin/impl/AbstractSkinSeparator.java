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

import com.l2fprod.gui.plaf.skin.SkinSeparator;

/**
 * Skin Separator. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:21:42 $
 */
public abstract class AbstractSkinSeparator extends AbstractSkinComponent implements SkinSeparator {

  public boolean paint(java.awt.Graphics g, javax.swing.JSeparator separator) {
    return false;
  }

  public java.awt.Dimension getPreferredSize(javax.swing.JSeparator separator,
                                             java.awt.Dimension uiPreferredSize) {
    return uiPreferredSize;
  }

  public boolean installSkin(javax.swing.JComponent component) {
    return true;
  }

}
