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

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
public final class GtkBorder {

  /**
   * Description of the Field
   */
  public int top, right, bottom, left;

  /**
   * Constructor for the GtkBorder object
   *
   * @param top     Description of Parameter
   * @param right   Description of Parameter
   * @param bottom  Description of Parameter
   * @param left    Description of Parameter
   */
  public GtkBorder(int top, int right, int bottom, int left) {
    this.top = top;
    this.right = right;
    this.bottom = bottom;
    this.left = left;
  }

  /**
   * Constructor for the GtkBorder object
   */
  public GtkBorder() {
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public String toString() {
    return "{ " + top + ", " + right + ", " + bottom + ", " + left + " }";
  }

}
