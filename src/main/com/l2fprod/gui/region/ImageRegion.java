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
package com.l2fprod.gui.region;

import java.awt.Image;

/**
 * Created on 21/12/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:42 $
 */
public final class ImageRegion extends Region {

  Image img;
  int width;
  int height;

  /**
   * Constructor for the ImageRegion object
   *
   * @param img  Description of Parameter
   */
  public ImageRegion(Image img) {
    this(img, img.getWidth(null), img.getHeight(null));
  }

  /**
   * Constructor for the ImageRegion object
   *
   * @param img     Description of Parameter
   * @param width   Description of Parameter
   * @param height  Description of Parameter
   */
  public ImageRegion(Image img, int width, int height) {
    this.img = img;
    this.width = width;
    this.height = height;
  }

  /**
   * Gets the Image attribute of the ImageRegion object
   *
   * @return   The Image value
   */
  public Image getImage() {
    return img;
  }

  /**
   * Gets the Width attribute of the ImageRegion object
   *
   * @return   The Width value
   */
  public int getWidth() {
    return width;
  }

  /**
   * Gets the Height attribute of the ImageRegion object
   *
   * @return   The Height value
   */
  public int getHeight() {
    return height;
  }

}
