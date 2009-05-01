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
package com.l2fprod.gui.plaf.skin.impl.gtk;

import com.l2fprod.gui.plaf.skin.DefaultButton;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkinSeparator;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.GtkParser;

/**
 * Skin Separator. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkSeparator extends AbstractSkinSeparator {

  DefaultButton hline;
  DefaultButton vline;

  public GtkSeparator(GtkParser parser) throws Exception{
    hline = GtkUtils.newButton(parser, "default",
                               new String[]{"function"},
                               new String[]{"HLINE"});
    vline = GtkUtils.newButton(parser, "default",
                               new String[]{"function"},
                               new String[]{"VLINE"});
  }

  public boolean status() {
    return (hline != null) && (vline != null);
  }

  public boolean paint(java.awt.Graphics g, javax.swing.JSeparator separator) {
    if (javax.swing.JSeparator.HORIZONTAL == separator.getOrientation()) {
      hline.paint(g, separator);
    } else {
      vline.paint(g, separator);
    }
    return true;
  }

  public java.awt.Dimension getPreferredSize(javax.swing.JSeparator separator,
                                             java.awt.Dimension uiPreferredSize) {
    if (javax.swing.JSeparator.HORIZONTAL == separator.getOrientation()) {
      uiPreferredSize.height = hline.getHeight();
    } else {
      uiPreferredSize.width = vline.getWidth();
    }
    return uiPreferredSize;
  }

}
