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
package com.l2fprod.gui.nativeskin.win32;

import java.awt.*;
import java.awt.image.PixelGrabber;

import com.l2fprod.gui.nativeskin.*;
import com.l2fprod.gui.region.*;
import com.l2fprod.util.AccessUtils;
import com.l2fprod.util.OS;

/**
 * Win32 Implementation.<br>
 *
 * @author    $Author: l2fprod $
 * @author    Herve Lemaitre (setWindowImageRegion0)
 * @created   27 avril 2002
 * @version   $Revision: 1.5 $, $Date: 2009-05-01 13:24:46 $
 */
public final class Win32NativeSkin extends NativeSkin {
  
  public void setWindowTransparency(Window window, int transparency) {
    setWindowTransparency0(getHWND(window), transparency);
  }

  /**
   * Sets the WindowRegion attribute of the Win32RegionBuilder object
   *
   * @param window  The new WindowRegion value
   * @param region  The new WindowRegion value
   * @param redraw  The new WindowRegion value
   */
  public void setWindowRegion(Window window, Region region, boolean redraw) {
    setWindowRegion(getHWND(window), region, redraw);
  }

  public void setWindowRegion(int handle, Region region, boolean redraw) {
    if (region instanceof ImageRegion) {
      region = createRegion(((ImageRegion) region).getImage(),
          ((ImageRegion) region).getImage().getWidth(null),
          ((ImageRegion) region).getImage().getHeight(null));
    }
    setWindowRegion0(handle, ((Win32Region) region).nativeHandle, redraw);
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
    return new Win32Region(createEllipticRegion0(x1, y1, x2, y2));
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
    return new Win32Region(createRectangleRegion0(x1, y1, x2, y2));
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
    return new Win32Region(createRoundRectangleRegion0(x1, y1, x2, y2, x3, y3));
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
    if (xpoints == null || ypoints == null || (xpoints.length != ypoints.length)) {
      throw new IllegalArgumentException("xpoints and ypoints must be != null and size must not differ");
    }
    return new Win32Region(createPolygonRegion0(xpoints, ypoints, fillMode));
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
    return new Win32Region(combineRegions0(((Win32Region) region1).nativeHandle, ((Win32Region) region2).nativeHandle, combineMode));
  }

  public void setAlwaysOnTop(Window window, boolean enable) {
    setAlwaysOnTop0(getHWND(window), enable);
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
    int[] pixels = new int[width * height];

    PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height,
        pixels, 0, width);
    try {
      pg.grabPixels();
    } catch (InterruptedException e) {
      System.err.println("interrupted waiting for pixels!");
      e.printStackTrace();
    }

    return new Win32Region(createRegion0(pixels, width, height));
  }

  /**
   * Sets the WindowRegion0 attribute of the Win32RegionBuilder class
   *
   * @param hwnd    The new WindowRegion0 value
   * @param region  The new WindowRegion0 value
   * @param redraw  The new WindowRegion0 value
   */
  private native static void setWindowRegion0(int hwnd, long region, boolean redraw);

  /**
   * Gets the HWND attribute of the Win32RegionBuilder class
   *
   * @param window  Description of Parameter
   * @return        The HWND value
   */
  private static int getHWND(Window window) {
    if (window.getPeer() == null) {
      window.addNotify();
    }

    if (OS.isOneDotFourOrMore()) {
      return getHWND0(window);
    } else {
      try {
      Object drawingSurfaceInfo;
      int hwnd = 0;
            
      // Get the drawing surface
      // drawingSurfaceInfo =
      //   ((sun.awt.DrawingSurface) (window.getPeer())).getDrawingSurfaceInfo();
      drawingSurfaceInfo =
        Class.forName("sun.awt.DrawingSurface").
        getMethod("getDrawingSurfaceInfo", null).
        invoke(window.getPeer(), null);

      if (null != drawingSurfaceInfo) {
        // drawingSurfaceInfo.lock();
        AccessUtils.invoke(drawingSurfaceInfo, "lock", null, null);

        // Get the Win32 specific information
        // win32DrawingSurface =
        //   (sun.awt.Win32DrawingSurface) drawingSurfaceInfo.getSurface();
        Object win32DrawingSurface =
          AccessUtils.invoke(drawingSurfaceInfo, "getSurface", null, null);

        // hwnd = win32DrawingSurface.getHWnd();
        hwnd = AccessUtils.getAsInt(win32DrawingSurface, "getHWnd");

        // drawingSurfaceInfo.unlock();
        AccessUtils.invoke(drawingSurfaceInfo, "unlock", null, null);
      }
      return hwnd;
        
      } catch (Throwable throwable) {
        throwable.printStackTrace();
        throw new Error(throwable);
      }
    }
  }

  /**
   * Get the Windows handle of window using native code.
   *
   * @param window a <code>Window</code> value
   * @return an <code>int</code> value
   */
  private native static int getHWND0(Window window);

  private native static void setWindowTransparency0(int hwnd, int transparency);

  /**
   * Description of the Method
   *
   * @param x1  Description of Parameter
   * @param y1  Description of Parameter
   * @param x2  Description of Parameter
   * @param y2  Description of Parameter
   * @return    Description of the Returned Value
   */
  private native static long createEllipticRegion0(int x1, int y1, int x2, int y2);

  /**
   * Description of the Method
   *
   * @param x1  Description of Parameter
   * @param y1  Description of Parameter
   * @param x2  Description of Parameter
   * @param y2  Description of Parameter
   * @return    Description of the Returned Value
   */
  private native static long createRectangleRegion0(int x1, int y1, int x2, int y2);

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
  private native static long createRoundRectangleRegion0(int x1, int y1,
      int x2, int y2,
      int x3, int y3);

  /**
   * Description of the Method
   *
   * @param xpoints   Description of Parameter
   * @param ypoints   Description of Parameter
   * @param fillMode  Description of Parameter
   * @return          Description of the Returned Value
   */
  private native static long createPolygonRegion0(int[] xpoints, int[] ypoints, int fillMode);

  /**
   * Description of the Method
   *
   * @param region1      Description of Parameter
   * @param region2      Description of Parameter
   * @param combineMode  Description of Parameter
   * @return             Description of the Returned Value
   */
  private native static long combineRegions0(long region1, long region2, int combineMode);

  /**
   * Description of the Method
   *
   * @param pixels  Description of Parameter
   * @param width   Description of Parameter
   * @param height  Description of Parameter
   * @return        Description of the Returned Value
   */
  private native static long createRegion0(int[] pixels, int width, int height);

  private native static void setAlwaysOnTop0(int handle, boolean enable);
}
