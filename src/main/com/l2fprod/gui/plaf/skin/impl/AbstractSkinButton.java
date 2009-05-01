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
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:21:42 $
 */
public class AbstractSkinButton extends AbstractSkinComponent implements SkinButton {

  public AbstractSkinButton() {
  }

  public AbstractSkinButton(Skin p_ParentSkin) {
    super(p_ParentSkin);
  }

  /**
   * Gets the CheckBoxIconSize attribute of the AbstractSkinButton object
   *
   * @return   The CheckBoxIconSize value
   */
  public Dimension getCheckBoxIconSize() {
    return null;
  }

  /**
   * Gets the RadioButtonIconSize attribute of the AbstractSkinButton object
   *
   * @return   The RadioButtonIconSize value
   */
  public Dimension getRadioButtonIconSize() {
    return null;
  }

  /**
   * Gets the RadioIcon attribute of the AbstractSkinButton object
   *
   * @param b  Description of Parameter
   * @return   The RadioIcon value
   */
  public Icon getRadioIcon(AbstractButton b) {
    return null;
  }

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
   * @param g  Description of Parameter
   * @param b  Description of Parameter
   * @return   Description of the Returned Value
   */
  public final boolean paintButton(Graphics g, AbstractButton b) {
    ButtonModel model = b.getModel();

    if (b.isEnabled() == false) {
      return paintDisabledButton(g, b);
    }
    else {
      //PENDING(fred): should handle disabledINButton,
      // when the toggle button is disabled but pressed (armed)
      if (b instanceof JToggleButton) {
        if ((model.isArmed() && model.isPressed()) || model.isSelected()) {
          return paintPressedToggle(g, b);
        }
        else if (model.isRollover()) {
          return paintRolloverToggle(g, b);
        }
        else {
          return paintToggle(g, b);
        }
      }
      else if (b instanceof JButton) {
        if (model.isPressed() && (b.isRolloverEnabled() == false || (b.isRolloverEnabled() && model.isRollover()))) {
          return paintPressedButton(g, b);
        }
        else if (model.isRollover()) {
          return paintRolloverButton(g, b);
        }
        else {
          return paintNormalButton(g, b);
        }
      }
    }

    return false;
  }

  protected boolean paintDisabledButton(Graphics g, AbstractButton b) {
    return false;
  }

  protected boolean paintPressedToggle(Graphics g, AbstractButton b) {
    return false;
  }

  protected boolean paintRolloverToggle(Graphics g, AbstractButton b) {
    return false;
  }

  protected boolean paintToggle(Graphics g, AbstractButton b) {
    return false;
  }

  protected boolean paintPressedButton(Graphics g, AbstractButton b) {
    return false;
  }

  protected boolean paintRolloverButton(Graphics g, AbstractButton b) {
    return false;
  }

  protected boolean paintNormalButton(Graphics g, AbstractButton b) {
    return false;
  }

}
