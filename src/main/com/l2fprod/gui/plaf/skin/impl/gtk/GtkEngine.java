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
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:21:08 $
 */
public final class GtkEngine {

  /**
   * Description of the Field
   */
  public GtkStyle style;

  java.util.Vector images = new java.util.Vector();

  /**
   * Gets the Images attribute of the GtkEngine object
   *
   * @return   The Images value
   */
  public java.util.Vector getImages() {
    return images;
  }

  /**
   * Adds a feature to the Image attribute of the GtkEngine object
   *
   * @param image  The feature to be added to the Image attribute
   */
  public void addImage(GtkImage image) {
    images.addElement(image);
    image.style = style;
  }

  /**
   * Description of the Method
   *
   * @param keys    Description of Parameter
   * @param values  Description of Parameter
   * @return        Description of the Returned Value
   */
  public GtkImage findImage(Object[] keys, Object[] values) {
    return findImage(keys, values, false);
  }

  /**
   * Description of the Method
   *
   * @param keys        Description of Parameter
   * @param values      Description of Parameter
   * @param exactMatch  Description of Parameter
   * @return            Description of the Returned Value
   */
  public GtkImage findImage(Object[] keys, Object[] values, boolean exactMatch) {
    GtkImage image = null;
    int imageMatchKeys = 0;

    for (int i = 0, c = images.size(); i < c; i++) {
      GtkImage currentImage = (GtkImage) images.elementAt(i);
      int j = 0;

      while ((j < keys.length) &&
          ((values[j] == null && currentImage.getProperty(keys[j]) == null) ||
          (values[j] != null) && values[j].equals(currentImage.getProperty(keys[j])))) {
        j++;
      }

      if (j == keys.length) {
        image = currentImage;
        imageMatchKeys = j;
        break;
      }
      else if (j>0 && j>= imageMatchKeys) {
        image = currentImage;
        imageMatchKeys = j;
      }
    }

    if (exactMatch) {
      if (imageMatchKeys < keys.length) {
        return null;
      } else {
        return image;
      }
    } else {
      return image;
    }
  }

}
