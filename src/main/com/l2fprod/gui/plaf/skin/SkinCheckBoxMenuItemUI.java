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

import javax.swing.plaf.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:42 $
 */
public class SkinCheckBoxMenuItemUI extends SkinMenuItemUI {

  /**
   * Description of the Method
   *
   * @param item     Description of Parameter
   * @param e        Description of Parameter
   * @param path     Description of Parameter
   * @param manager  Description of Parameter
   */
  public void processMouseEvent(JMenuItem item, MouseEvent e, MenuElement path[], MenuSelectionManager manager) {
    Point p = e.getPoint();
    if (p.x >= 0 && p.x < item.getWidth() &&
        p.y >= 0 && p.y < item.getHeight()) {
      if (e.getID() == MouseEvent.MOUSE_RELEASED) {
        manager.clearSelectedPath();
        item.doClick(0);
      }
      else {
        manager.setSelectedPath(path);
      }
    }
    else if (item.getModel().isArmed()) {
      MenuElement newPath[] = new MenuElement[path.length - 1];
      int i;
      int c;
      for (i = 0, c = path.length - 1; i < c; i++) {
        newPath[i] = path[i];
      }
      manager.setSelectedPath(newPath);
    }
  }

  /**
   * Gets the PropertyPrefix attribute of the SkinCheckBoxMenuItemUI object
   *
   * @return   The PropertyPrefix value
   */
  protected String getPropertyPrefix() {
    return "CheckBoxMenuItem";
  }

  /**
   * Description of the Method
   */
  protected void installDefaults() {
    super.installDefaults();
    String prefix = getPropertyPrefix();
    if (menuItem.getSelectedIcon() == null ||
        menuItem.getSelectedIcon() instanceof UIResource) {
      menuItem.setSelectedIcon(
          UIManager.getIcon(prefix + ".checkIcon"));
    }
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinCheckBoxMenuItemUI();
  }

}
