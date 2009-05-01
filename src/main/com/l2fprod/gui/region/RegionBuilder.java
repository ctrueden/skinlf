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

import java.awt.Window;
import java.awt.Image;

import com.l2fprod.gui.nativeskin.NativeSkin;

/**
 * SkinRegion.<br>
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:42 $
 */
public final class RegionBuilder implements com.l2fprod.gui.nativeskin.NativeConstants {

  static RegionBuilder theInstance = new RegionBuilder();

  public RegionBuilder getInstance() {
    return theInstance;
  }

  public static boolean isSupported() {
    return NativeSkin.isSupported();
  }

  /**
   * Sets the WindowRegion attribute of the RegionBuilder object
   *
   * @param window  The new WindowRegion value
   * @param region  The new WindowRegion value
   * @param redraw  The new WindowRegion value
   */
  public void setWindowRegion(Window window, Region region, boolean redraw) {
    NativeSkin.getInstance().setWindowRegion(window, region, redraw);
  }

  /**
   * Sets the Region for the graphical object identified by the given native handle.
   * This method may be used with environment such as Eclipse/SWT where it is easy
   * to get the native handle of any "Shell" object.
   *
   * @param handle an <code>int</code> value
   * @param region a <code>Region</code> value
   * @param redraw a <code>boolean</code> value
   */
  public void setWindowRegion(int handle, Region region, boolean redraw) {
    NativeSkin.getInstance().setWindowRegion(handle, region, redraw);
  }

  /**
   * Description of the Method
   *
   * @param x1  Description of Parameter
   * @param y1  Description of Parameter
   * @param x2  Description of Parameter
   * @param y2  Description of Parameter
   * @return    Description of the Returned Value
   */
  public Region createEllipticRegion(int x1, int y1, int x2, int y2) {
    return NativeSkin.getInstance().createEllipticRegion(x1, y1, x2, y2);
  }

  /**
   * Description of the Method
   *
   * @param x1  Description of Parameter
   * @param y1  Description of Parameter
   * @param x2  Description of Parameter
   * @param y2  Description of Parameter
   * @return    Description of the Returned Value
   */
  public Region createRectangleRegion(int x1, int y1, int x2, int y2) {
    return NativeSkin.getInstance().createRectangleRegion(x1, y1, x2, y2);
  }

  /**
   * Description of the Method
   *
   * @param x1  Description of Parameter
   * @param y1  Description of Parameter
   * @param x2  Description of Parameter
   * @param y2  Description of Parameter
   * @param x3  Description of Parameter
   * @param y3  Description of Parameter
   * @return    Description of the Returned Value
   */
  public Region createRoundRectangleRegion(int x1, int y1, int x2, int y2, int x3, int y3) {
    return NativeSkin.getInstance().createRoundRectangleRegion(x1, y1, x2, y2, x3, y3);
  }

  /**
   * Description of the Method
   *
   * @param xpoints   Description of Parameter
   * @param ypoints   Description of Parameter
   * @param fillMode  Description of Parameter
   * @return          Description of the Returned Value
   */
  public Region createPolygonRegion(int[] xpoints, int[] ypoints, int fillMode) {
    return NativeSkin.getInstance().createPolygonRegion(xpoints, ypoints, fillMode);
  }

  /**
   * Description of the Method
   *
   * @param region1      Description of Parameter
   * @param region2      Description of Parameter
   * @param combineMode  Description of Parameter
   * @return             Description of the Returned Value
   */
  public Region combineRegions(Region region1, Region region2, int combineMode) {
    return NativeSkin.getInstance().combineRegions(region1, region2, combineMode);
  }

  /**
   * Description of the Method
   *
   * @param image  Description of Parameter
   * @return       Description of the Returned Value
   */
  public Region createRegion(Image image) {
    return NativeSkin.getInstance().createRegion(image);
  }

  /**
   * Description of the Method
   *
   * @param image   Description of Parameter
   * @param width   Description of Parameter
   * @param height  Description of Parameter
   * @return        Description of the Returned Value
   */
  public Region createRegion(Image image, int width, int height) {
    return NativeSkin.getInstance().createRegion(image, width, height);
  }

}
