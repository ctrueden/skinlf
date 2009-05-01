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
 * Skin Slider. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:44 $
 */
public interface SkinSlider extends SkinComponent {

  /**
   * Gets the PreferredSize attribute of the SkinSlider object
   *
   * @param slider  Description of Parameter
   * @return        The PreferredSize value
   */
  java.awt.Dimension getPreferredSize(javax.swing.JSlider slider,
                                      java.awt.Dimension uiPreferredSize);

  java.awt.Dimension getThumbSize(javax.swing.JSlider slider);

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param slider       Description of Parameter
   * @param trackBounds  Description of Parameter
   * @return             Description of the Returned Value
   */
  boolean paintTrack(java.awt.Graphics g, javax.swing.JSlider slider, java.awt.Rectangle trackBounds);

  /**
   * Description of the Method
   *
   * @param g            Description of Parameter
   * @param slider       Description of Parameter
   * @param thumbBounds  Description of Parameter
   * @return             Description of the Returned Value
   */
  boolean paintThumb(java.awt.Graphics g, javax.swing.JSlider slider, java.awt.Rectangle thumbBounds);

}
