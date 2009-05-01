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

import javax.swing.JButton;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.5 $, $Date: 2009-05-01 13:24:43 $
 */
public final class SkinWindowButton extends JButton {

  private int align, action;
  private int xcoord, ycoord;

  /**
   * Constructor for the SkinWindowButton object
   *
   * @param align   Description of Parameter
   * @param action  Description of Parameter
   */
  public SkinWindowButton(int align, int action) {
    this(-1, -1, align, action);
  }

  /**
   * Constructor for the SkinWindowButton object
   *
   * @param xcoord  Description of Parameter
   * @param ycoord  Description of Parameter
   * @param align   Description of Parameter
   * @param action  Description of Parameter
   */
  public SkinWindowButton(int xcoord, int ycoord, int align, int action) {
    this.xcoord = xcoord;
    this.ycoord = ycoord;
    this.align = align;
    this.action = action;
    setBorderPainted(false);
    setFocusPainted(false);
    setOpaque(false);
    setIcon(getIcon());
    setRolloverIcon(getRolloverIcon());
  }

	public boolean isFocusTraversable() {
    return false;
  }

	public void requestFocus() {};

  /**
   * Gets the UIClassID attribute of the SkinWindowButton object
   *
   * @return   The UIClassID value
   */
  public String getUIClassID() {
    return "WindowButtonUI";
  }

  /**
   * Gets the XCoord attribute of the SkinWindowButton object
   *
   * @return   The XCoord value
   */
  public int getXCoord() {
    return xcoord;
  }

  /**
   * Gets the YCoord attribute of the SkinWindowButton object
   *
   * @return   The YCoord value
   */
  public int getYCoord() {
    return ycoord;
  }

  /**
   * Gets the Align attribute of the SkinWindowButton object
   *
   * @return   The Align value
   */
  public int getAlign() {
    return align;
  }

  /**
   * Gets the WindowAction attribute of the SkinWindowButton object
   *
   * @return   The WindowAction value
   */
  public int getWindowAction() {
    return action;
  }

}

