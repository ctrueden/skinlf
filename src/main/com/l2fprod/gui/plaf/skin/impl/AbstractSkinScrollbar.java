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
public class AbstractSkinScrollbar extends AbstractSkinComponent implements SwingConstants, SkinScrollbar {

  public AbstractSkinScrollbar() {
  }

  public AbstractSkinScrollbar(Skin p_ParentSkin) {
    super(p_ParentSkin);
  }

  /**
   * Gets the PreferredSize attribute of the AbstractSkinScrollbar object
   *
   * @param scrollbar  Description of Parameter
   * @return           The PreferredSize value
   */
  public java.awt.Dimension getPreferredSize(javax.swing.JScrollBar scrollbar) {
    return scrollbar.getPreferredSize();
  }

  /**
   * Gets the MinimumThumbSize attribute of the AbstractSkinScrollbar object
   *
   * @return   The MinimumThumbSize value
   */
  public Dimension getMinimumThumbSize() {
    return null;
  }

  /**
   * Gets the ArrowPreferredSize attribute of the AbstractSkinScrollbar object
   *
   * @param direction  Description of Parameter
   * @return           The ArrowPreferredSize value
   */
  public java.awt.Dimension getArrowPreferredSize(int direction) {
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
   * @param g          Description of Parameter
   * @param b          Description of Parameter
   * @param direction  Description of Parameter
   * @return           Description of the Returned Value
   */
  public boolean paintArrow(Graphics g, AbstractButton b, int direction) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param scrollbar    Description of Parameter
   * @param trackBounds  Description of Parameter
   * @return             Description of the Returned Value
   */
  public boolean paintTrack(Graphics g, JScrollBar scrollbar, Rectangle trackBounds) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param scrollbar    Description of Parameter
   * @param thumbBounds  Description of Parameter
   * @return             Description of the Returned Value
   */
  public boolean paintThumb(Graphics g, JScrollBar scrollbar, Rectangle thumbBounds) {
    return false;
  }

}
