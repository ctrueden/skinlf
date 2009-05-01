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
 * Skin Button. <br>
 * Include support for buttons, toggle buttons and radio.
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:43 $
 */
public interface SkinButton extends SkinComponent {

  /**
   * Gets the CheckBoxIconSize attribute of the SkinButton object
   *
   * @return   The CheckBoxIconSize value
   */
  java.awt.Dimension getCheckBoxIconSize();

  /**
   * Gets the RadioButtonIconSize attribute of the SkinButton object
   *
   * @return   The RadioButtonIconSize value
   */
  java.awt.Dimension getRadioButtonIconSize();

  /**
   * Gets the RadioIcon attribute of the SkinButton object
   *
   * @param b  Description of Parameter
   * @return   The RadioIcon value
   */
  javax.swing.Icon getRadioIcon(javax.swing.AbstractButton b);

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param b  Description of Parameter
   * @return   Description of the Returned Value
   */
  boolean paintButton(java.awt.Graphics g, javax.swing.AbstractButton b);

}
