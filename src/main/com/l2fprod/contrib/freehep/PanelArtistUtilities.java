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
package com.l2fprod.contrib.freehep;
// was originally org.freehep.swing.graphics;

import java.awt.geom.AffineTransform;

/**
 * This class is a collection of static methods which are useful for
 * implementations of the PanelArtist interface. Most methods return an
 * AffineTransform which will perform some common operation on a window.
 *
 * @author    Charles Loomis
 * @created   27 avril 2002
 * @version   $Id: PanelArtistUtilities.java,v 1.1 2001/11/04 11:10:27 l2fprod
 *      Exp $
 */
public class PanelArtistUtilities {

  /**
   * This returns an affine transform which will flip the vertical axis around.
   * (NOTE: that this transform should be pre-concatenated with the existing
   * one!) The returned transform will maintain the centerpoint of the window
   * and flip the direction of the y-axis.
   *
   * @param height  Description of Parameter
   * @return        The YFlipTransform value
   */
  public static AffineTransform getYFlipTransform(int height) {
    return new AffineTransform(1., 0., 0., -1., 0., height);
  }

  /**
   * This returns an affine transform which will rotate the contents of the
   * window by 90 degrees. (NOTE: that this transform should be pre-concatenated
   * with the existing one!) The returned transform will rotate the contents of
   * the window by 90 degrees while keeping the centerpoint the same. The x and
   * y-scaling will be adjusted to keep the same area visible.
   *
   * @param width   Description of Parameter
   * @param height  Description of Parameter
   * @return        The CCWRotateTransform value
   */
  public static AffineTransform
      getCCWRotateTransform(int width, int height) {

    return new AffineTransform(0.,
        -((double) height) / width,
        ((double) width) / height,
        0.,
        0.,
        height);
  }

  /**
   * This returns an affine transform which will rotate the contents of the
   * window by -90 degrees. (NOTE: that this transform should be
   * pre-concatenated with the existing one!) The returned transform will rotate
   * the contents of the window by -90 degrees while keeping the centerpoint the
   * same. The x and y-scaling will be adjusted to keep the same area visible.
   *
   * @param width   Description of Parameter
   * @param height  Description of Parameter
   * @return        The CWRotateTransform value
   */
  public static AffineTransform
      getCWRotateTransform(int width, int height) {

    return new AffineTransform(0.,
        ((double) height) / width,
        -((double) width) / height,
        0.,
        (double) width,
        0.);
  }

}
