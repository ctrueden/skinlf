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
package com.l2fprod.gui.nativeskin.x11;

import java.awt.*;

import com.l2fprod.gui.region.*;
import com.l2fprod.gui.nativeskin.NativeSkin;

/**
 * X11 Implementation. <br>
 *
 *
 * @author    Herve Lemaitre
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $
 */
public final class X11NativeSkin extends NativeSkin {

  /**
   * Sets the WindowRegion attribute of the X11NativeSkin object
   *
   * @param window  The new WindowRegion value
   * @param region  The new WindowRegion value
   * @param redraw  The new WindowRegion value
   */
  public void setWindowRegion(Window window, Region region, boolean redraw) {
    /*
     *  if (region instanceof ImageRegion) {
     *  / grab pixel of the image
     *  int width    = ((ImageRegion)region).getImage().getWidth(null);
     *  int height   = ((ImageRegion)region).getImage().getHeight(null);
     *  int[] pixels = new int[width * height];
     *  PixelGrabber pg = new PixelGrabber(((ImageRegion)region).getImage(), 0, 0, width, height,
     *  pixels, 0, width);
     *  try {
     *  pg.grabPixels();
     *  } catch (InterruptedException e) {
     *  System.err.println("interrupted waiting for pixels!");
     *  e.printStackTrace();
     *  }
     *  / Get the drawing surface
     *  DrawingSurfaceInfo drawingSurfaceInfo =
     *  ((DrawingSurface)(window.getPeer())).getDrawingSurfaceInfo();
     *  if (drawingSurfaceInfo != null) {
     *  drawingSurfaceInfo.lock();
     *  X11DrawingSurface x11DrawingSurface =
     *  (X11DrawingSurface)drawingSurfaceInfo.getSurface();
     *  setWindowRegion0(x11DrawingSurface.getDisplay(),
     *  x11DrawingSurface.getDrawable(),
     *  pixels,
     *  width,
     *  height);
     *  drawingSurfaceInfo.unlock();
     *  }
     *  }
     */
  }

  /**
   * Sets the WindowRegion0 attribute of the X11NativeSkin class
   *
   * @param display   The new WindowRegion0 value
   * @param drawable  The new WindowRegion0 value
   * @param pixels    The new WindowRegion0 value
   * @param width     The new WindowRegion0 value
   * @param height    The new WindowRegion0 value
   */
  private native static void setWindowRegion0(int display,
      int drawable,
      int[] pixels,
      int width,
      int height);

}
// end class Region
