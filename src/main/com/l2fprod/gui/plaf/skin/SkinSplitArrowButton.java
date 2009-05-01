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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinSplitArrowButton extends JButton implements SwingConstants {

  /**
   * Description of the Field
   */
  protected int direction;
  /**
   * Description of the Field
   */
  protected Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Constructor for the SkinSplitArrowButton object
   *
   * @param direction  Description of Parameter
   */
  public SkinSplitArrowButton(int direction) {
    super();
    setRequestFocusEnabled(false);
    setDirection(direction);
    setBackground(UIManager.getColor("control"));
    Dimension size = getPreferredSize();
    setSize(size.width, size.height);
  }

  /**
   * Sets the Direction attribute of the SkinSplitArrowButton object
   *
   * @param dir  The new Direction value
   */
  public void setDirection(int dir) {
    direction = dir;
  }

  /**
   * Sets the Border attribute of the SkinSplitArrowButton object
   *
   * @param b  The new Border value
   */
  public void setBorder(Border b) {
  }

  /**
   * Gets the Direction attribute of the SkinSplitArrowButton object
   *
   * @return   The Direction value
   */
  public int getDirection() {
    return direction;
  }

  /**
   * Gets the PreferredSize attribute of the SkinSplitArrowButton object
   *
   * @return   The PreferredSize value
   */
  public Dimension getPreferredSize() {
    return skin.getSplitPane().getArrowPreferredSize(direction);
  }

  /**
   * Gets the MinimumSize attribute of the SkinSplitArrowButton object
   *
   * @return   The MinimumSize value
   */
  public Dimension getMinimumSize() {
    return new Dimension(5, 5);
  }

  /**
   * Gets the MaximumSize attribute of the SkinSplitArrowButton object
   *
   * @return   The MaximumSize value
   */
  public Dimension getMaximumSize() {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Gets the Insets attribute of the SkinSplitArrowButton object
   *
   * @return   The Insets value
   */
  public Insets getInsets() {
    return new Insets(0, 0, 0, 0);
  }

  /**
   * Gets the FocusTraversable attribute of the SkinSplitArrowButton object
   *
   * @return   The FocusTraversable value
   */
  public boolean isFocusTraversable() {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   */
  public void paint(Graphics g) {
    skin.getSplitPane().paintArrow(g, this, direction);
  }

}

