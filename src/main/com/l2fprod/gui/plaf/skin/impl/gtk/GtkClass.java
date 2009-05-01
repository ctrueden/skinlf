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

import java.util.ArrayList;
import java.util.List;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
public final class GtkClass {

  public String name;
  public List styles;

  /**
   * Constructor for the GtkClass object
   */
  public GtkClass() {
    styles = new ArrayList();
  }

  /**
   * Sets the Name attribute of the GtkClass object
   *
   * @param s  The new Name value
   */
  public void setName(String s) {
    name = s;
  }

  /**
   * Gets the Style attribute of the GtkClass object
   *
   * @return   The Style value
   */
  public GtkStyle getStyle() {
    return (GtkStyle) styles.get(0);
  }

  /**
   * Gets the Styles attribute of the GtkClass object
   *
   * @return   The Styles value
   */
  public GtkStyle[] getStyles() {
    return (GtkStyle[])styles.toArray(new GtkStyle[styles.size()]);
  }

  /**
   * Adds a feature to the StyleName attribute of the GtkClass object
   *
   * @param stylename  The feature to be added to the StyleName attribute
   */
  public void addStyleName(String stylename) {
    if (styles.contains(stylename) == false) {
      styles.add(stylename);
    }
  }

}
