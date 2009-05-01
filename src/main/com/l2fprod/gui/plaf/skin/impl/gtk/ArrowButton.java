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

import java.awt.*;
import javax.swing.*;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;
import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.util.ImageUtils;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
final class ArrowButton {

  DefaultButton normal, focus, pressed;

  /**
   * Constructor for the ArrowButton object
   *
   * @param parser         Description of Parameter
   * @param direction      Description of Parameter
   * @exception Exception  Description of Exception
   */
  public ArrowButton(GtkParser parser, String direction) throws Exception {
    normal = GtkUtils.newButton(parser, "arrows",
        new String[]{"function", "state", "arrow_direction"},
        new String[]{"ARROW", "NORMAL", direction});
    normal.setCenterFill(ImageUtils.PAINT_STRETCH);
    focus = GtkUtils.newButton(parser, "arrows",
        new String[]{"function", "state", "arrow_direction"},
        new String[]{"ARROW", "PRELIGHT", direction});
    focus.setCenterFill(ImageUtils.PAINT_STRETCH);
    pressed = GtkUtils.newButton(parser, "arrows",
        new String[]{"function", "shadow", "arrow_direction"},
        new String[]{"ARROW", "IN", direction});
    pressed.setCenterFill(ImageUtils.PAINT_STRETCH);
  }

  /**
   * Gets the Width attribute of the ArrowButton object
   *
   * @return   The Width value
   */
  public int getWidth() {
    return normal.getWidth();
  }

  /**
   * Gets the Height attribute of the ArrowButton object
   *
   * @return   The Height value
   */
  public int getHeight() {
    return normal.getHeight();
  }

  /**
   * Gets the PreferredSize attribute of the ArrowButton object
   *
   * @return   The PreferredSize value
   */
  public Dimension getPreferredSize() {
    return new java.awt.Dimension(getWidth(), getHeight());
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param b  Description of Parameter
   */
  public void paint(Graphics g, AbstractButton b) {
    ButtonModel model = b.getModel();
    if (b.isEnabled() == false) {
      normal.paint(g, b);
    }
    else if (model.isArmed() && model.isPressed()) {
      pressed.paint(g, b);
    }
    else if (model.isRollover() && (focus != null)) {
      focus.paint(g, b);
    }
    else {
      normal.paint(g, b);
    }
  }

}
