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

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

import com.l2fprod.util.ImageUtils;
import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.gui.plaf.skin.impl.*;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkButton extends AbstractSkinButton implements SkinButton {

  DefaultButton checkIN, checkOUT;
  DefaultButton optionIN, optionOUT;

  Icon disabledCheckIN, disabledCheckOUT;
  Icon disabledOptionIN, disabledOptionOUT;

  DefaultButton disabledButton, pressedButton, normalButton, rolloverButton;
  DefaultButton disabledButtonIN;

  DefaultButton toggleIN, toggleOUT, toggleRollover;

  /**
   * Constructor for the GtkButton object
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkButton(GtkParser parser) throws Exception {
    normalButton = GtkUtils.newButton(parser, "GtkButton",
        new String[]{"function", "state", "shadow"},
        new String[]{"BOX", "NORMAL", "OUT"});

    pressedButton = GtkUtils.newButton(parser, "GtkButton",
        new String[]{"function", "state", "shadow"},
        new String[]{"BOX", "ACTIVE", "IN"});

    /*
     *  focusButton = GtkUtils.newButton(parser, "GtkButton",
     *  new String[]{"function", "state", "shadow"},
     *  new String[]{"BOX", "SELECTED", "IN"});
     *  if (focusButton == null) focusButton=pressedButton;
     */
    disabledButton = GtkUtils.newButton(parser, "GtkButton",
        new String[]{"function", "state", "shadow"},
        new String[]{"BOX", "INSENSITIVE", "OUT"});

    disabledButtonIN = GtkUtils.newButton(parser, "GtkButton",
        new String[]{"function", "state", "shadow"},
        new String[]{"BOX", "INSENSITIVE", "IN"});

    rolloverButton = GtkUtils.newButton(parser, "GtkButton",
        new String[]{"function", "state"},
        new String[]{"BOX", "PRELIGHT"});

    // toggle button
    toggleIN = GtkUtils.newButton(parser, "GtkToggleButton",
        new String[]{"function", "shadow"},
        new String[]{"BOX", "IN"});

    toggleOUT = GtkUtils.newButton(parser, "GtkToggleButton",
        new String[]{"function", "shadow", "state"},
        new String[]{"BOX", "OUT", null}); //, false, true);

    toggleRollover = GtkUtils.newButton(parser, "GtkToggleButton",
        new String[]{"function", "state"},
        new String[]{"BOX", "PRELIGHT"});

    checkIN = GtkUtils.newButton(parser, "GtkCheckButton",
        new String[]{"function", "shadow"},
        new String[]{"CHECK", "IN"});
    disabledCheckIN = ImageUtils.getDisabledIcon(checkIN.center);

    checkOUT = GtkUtils.newButton(parser, "GtkCheckButton",
        new String[]{"function", "shadow"},
        new String[]{"CHECK", "OUT"});
    disabledCheckOUT = ImageUtils.getDisabledIcon(checkOUT.center);

    optionIN = GtkUtils.newButton(parser, "GtkRadioButton",
        new String[]{"function", "shadow"},
        new String[]{"OPTION", "IN"});
    disabledOptionIN = ImageUtils.getDisabledIcon(optionIN.center);

    optionOUT = GtkUtils.newButton(parser, "GtkRadioButton",
        new String[]{"function", "shadow"},
        new String[]{"OPTION", "OUT"});
    disabledOptionOUT = ImageUtils.getDisabledIcon(optionOUT.center);
  }

  /**
   * Gets the CheckBoxIconSize attribute of the GtkButton object
   *
   * @return   The CheckBoxIconSize value
   */
  public Dimension getCheckBoxIconSize() {
    if (checkIN != null) {
      return new Dimension(checkIN.getIconWidth(), checkIN.getIconHeight());
    }
    else {
      return new Dimension(13, 13);
    }
  }

  /**
   * Gets the RadioButtonIconSize attribute of the GtkButton object
   *
   * @return   The RadioButtonIconSize value
   */
  public Dimension getRadioButtonIconSize() {
    if (optionIN != null) {
      return new Dimension(optionIN.getIconWidth(), optionIN.getIconHeight());
    }
    else {
      return new Dimension(13, 13);
    }
  }

  /**
   * Gets the RadioIcon attribute of the GtkButton object
   *
   * @param b  Description of Parameter
   * @return   The RadioIcon value
   */
  public javax.swing.Icon getRadioIcon(javax.swing.AbstractButton b) {
    ButtonModel model = b.getModel();
    if ((b instanceof JRadioButton) || (b instanceof JRadioButtonMenuItem)) {
      if (model.isSelected() || (model.isPressed() && model.isArmed())) {
        return b.isEnabled() ? optionIN : disabledOptionIN;
        //} else
        //return optionOUT;
      }
      else if (model.isEnabled() == false) {
        if (model.isSelected()) {
          return b.isEnabled() ? optionOUT : disabledOptionOUT;
        }
        else {
          return b.isEnabled() ? optionOUT : disabledOptionOUT;
        }
      }
      else {
        return b.isEnabled() ? optionOUT : disabledOptionOUT;
      }
    }
    else if ((b instanceof JCheckBox) || (b instanceof JCheckBoxMenuItem)) {
      if (model.isSelected() || (model.isPressed() && model.isArmed())) {
        return b.isEnabled() ? checkIN : disabledCheckIN;
      }
      else if (model.isEnabled() == false) {
        if (model.isSelected()) {
          return b.isEnabled() ? checkOUT : disabledCheckOUT;
        }
        else {
          return b.isEnabled() ? checkOUT : disabledCheckOUT;
        }
      }
      else {
        return b.isEnabled() ? checkOUT : disabledCheckOUT;
      }
    }
    else {
      return null;
    }
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return true;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    return true;
  }

  protected boolean paintDisabledButton(Graphics g, AbstractButton b) {
    disabledButton.paint(g, b);
    return true;
  }

  protected boolean paintPressedToggle(Graphics g, AbstractButton b) {
    toggleIN.paint(g, b);
    return true;
  }

  protected boolean paintRolloverToggle(Graphics g, AbstractButton b) {
    toggleRollover.paint(g, b);
    return true;
  }

  protected boolean paintToggle(Graphics g, AbstractButton b) {
    toggleOUT.paint(g, b);
    return true;
  }

  protected boolean paintPressedButton(Graphics g, AbstractButton b) {
    pressedButton.paint(g, b);
    return true;
  }

  protected boolean paintRolloverButton(Graphics g, AbstractButton b) {
    rolloverButton.paint(g, b);
    return true;
  }

  protected boolean paintNormalButton(Graphics g, AbstractButton b) {
    normalButton.paint(g, b);
    return true;
  }

}
