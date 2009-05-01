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

/**
 * Skin Frame. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:44 $
 */
public interface SkinFrame extends SkinComponent {

  /**
   * Gets the WindowButtons attribute of the SkinFrame object
   *
   * @param align  Description of Parameter
   * @return       The WindowButtons value
   */
  SkinWindowButton[] getWindowButtons(int align);

  /**
   * Gets the TopPreferredSize attribute of the SkinFrame object
   *
   * @return   The TopPreferredSize value
   */
  java.awt.Dimension getTopPreferredSize();

  /**
   * Description of the Method
   *
   * @param g           Description of Parameter
   * @param c           Description of Parameter
   * @param isSelected  Description of Parameter
   * @param title       Description of Parameter
   * @return            Description of the Returned Value
   */
  boolean paintTop(java.awt.Graphics g, java.awt.Component c, boolean isSelected, String title);

}
