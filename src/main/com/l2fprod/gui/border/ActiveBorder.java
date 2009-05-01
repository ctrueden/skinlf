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
package com.l2fprod.gui.border;

import java.awt.*;
import javax.swing.border.*;

/**
 * ActiveBorder.<BR>
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:46 $
 */
public class ActiveBorder implements Border {

  private Border enabledBorder;

  private Border disabledBorder;

  public ActiveBorder(Border p_EnabledBorder, Border p_DisabledBorder) {
    enabledBorder = p_EnabledBorder;
    disabledBorder = p_DisabledBorder;
  }

  public Insets getBorderInsets(Component c) {
    return c.isEnabled()?
      enabledBorder.getBorderInsets(c):
      disabledBorder.getBorderInsets(c);
  }

  public boolean isBorderOpaque() {
    return true;
  }

  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    if (c.isEnabled()) {
      enabledBorder.paintBorder(c, g, x, y, width, height);
    } else {
      disabledBorder.paintBorder(c, g, x, y, width, height);
    }
  }

}

    
    
