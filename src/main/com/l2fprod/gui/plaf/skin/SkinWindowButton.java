/* ====================================================================
 *
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright (c) @YEAR@ L2FProd.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by L2FProd.com
 *        (http://www.L2FProd.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not
 *    be used to endorse or promote products derived from this software
 *    without prior written permission. For written permission, please
 *    contact info@L2FProd.com.
 *
 * 5. Products derived from this software may not be called "SkinLF"
 *    nor may "SkinLF" appear in their names without prior written
 *    permission of L2FProd.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL L2FPROD.COM OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package com.l2fprod.gui.plaf.skin;

import javax.swing.JButton;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2004-07-18 19:20:16 $
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

