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
package com.l2fprod.gui.border;

import java.awt.Color;

/**
 * LineBorder.<BR>
 * This class extends the standard line border but allows the user to customize
 * the LineBorder roundedCorners variable which was not public in jdk1.2.2.
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:46 $
 */
public class LineBorder extends javax.swing.border.LineBorder {

  /**
   * Creates a line border with the specified color and a thickness = 1.
   *
   * @param color  the color for the border
   */
  public LineBorder(Color color) {
    super(color);
  }

  /**
   * Creates a line border with the specified color and thickness.
   *
   * @param color      the color of the border
   * @param thickness  the thickness of the border
   */
  public LineBorder(Color color, int thickness) {
    super(color, thickness);
  }

  /**
   * Creates a line border with the specified color, thickness, and corner
   * shape.
   *
   * @param color           the color of the border
   * @param thickness       the thickness of the border
   * @param roundedCorners  whether or not border corners should be round
   */
  public LineBorder(Color color, int thickness, boolean roundedCorners) {
    super(color, thickness);
    this.roundedCorners = roundedCorners;
  }

}
