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
 * Skin SplitPane. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:42 $
 */
public interface SkinSplitPane extends SkinComponent {

  /**
   * Gets the PreferredSize attribute of the SkinSplitPane object
   *
   * @param splitpane  Description of Parameter
   * @return           The PreferredSize value
   */
  java.awt.Dimension getPreferredSize(javax.swing.JSplitPane splitpane);

  /**
   * Gets the ArrowPreferredSize attribute of the SkinSplitPane object
   *
   * @param direction  Description of Parameter
   * @return           The ArrowPreferredSize value
   */
  java.awt.Dimension getArrowPreferredSize(int direction);

  /**
   * Description of the Method
   *
   * @param g          Description of Parameter
   * @param b          Description of Parameter
   * @param direction  Description of Parameter
   * @return           Description of the Returned Value
   */
  boolean paintArrow(java.awt.Graphics g, javax.swing.AbstractButton b, int direction);

  /**
   * Description of the Method
   *
   * @param g        Description of Parameter
   * @param divider  Description of Parameter
   * @param d        Description of Parameter
   * @return         Description of the Returned Value
   */
  boolean paintGutter(java.awt.Graphics g, javax.swing.JSplitPane divider, java.awt.Dimension d);

  /**
   * Description of the Method
   *
   * @param g        Description of Parameter
   * @param divider  Description of Parameter
   * @param d        Description of Parameter
   * @return         Description of the Returned Value
   */
  boolean paintThumb(java.awt.Graphics g, javax.swing.JSplitPane divider, java.awt.Dimension d);

}
