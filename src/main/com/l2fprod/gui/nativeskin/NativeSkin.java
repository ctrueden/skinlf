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
package com.l2fprod.gui.nativeskin;

import java.awt.Window;
import java.awt.Image;

import com.l2fprod.gui.region.Region;
import com.l2fprod.gui.region.ImageRegion;
import com.l2fprod.util.OS;

/**
 * NativeSkin.<BR>
 * SkinLF got native. This class offers methods to help developers
 * build Skinnable applications.
 */
public abstract class NativeSkin {

  private static NativeSkin theInstance;

  /**
   * Return true if NativeSkin is supported on this platform.
   *
   * @return true if NativeSkin is supported on this platform.
   */
  public static boolean isSupported() {
    return OS.isWindows();
  }

  /**
   * Get the instance of the NativeSkin for this platform.
   *
   * @return a <code>NativeSkin</code> value
   */
  public static NativeSkin getInstance() {
    if (theInstance == null) {
      String impl = null;
      String library = null;

      if (OS.isOneDotFourOrMore()) {
        if (OS.isWindows()) {
          impl = "com.l2fprod.gui.nativeskin.win32.Win32NativeSkin";
          library = "nativeskinwin32JAWT";
        }        
      }
      
      /* NOT YET COMPILED
        if (OS.isUnix()) {
          impl = "com.l2fprod.gui.nativeskin.x11.X11NativeSkin";
          if (OS.isSolaris()) {
            library = "nativeskinsolaris";
          } else if (OS.isLinux()) {
          library = "nativeskinlinux";
        }
        }
      */
      if (library == null) {
        throw new Error("NativeSkin is not yet available for your platform: "
            + System.getProperty("os.name"));
      }

      try {
        System.loadLibrary(library);

        theInstance = (NativeSkin) Class.forName(impl).newInstance();
      } catch (Throwable th) {
        th.printStackTrace();
        throw new Error("Error while loading the SkinRegion library: " + th.getMessage());
      }
    }
    return theInstance;
  }

  /**
   * Set the transparency of the given Window.
   *
   * @param window a <code>Window</code> value
   * @param transparency an <code>int</code> value
   */
  public void setWindowTransparency(Window window, int transparency) {
    throw new Error("Not Implemented");
  }

  /**
   * Sets the WindowRegion attribute of the RegionBuilder object
   *
   * @param window  The new WindowRegion value
   * @param region  The new WindowRegion value
   * @param redraw  The new WindowRegion value
   */
  public void setWindowRegion(Window window, Region region, boolean redraw) {
    throw new Error("Not Implemented");
  }

  /**
   * Sets the Region for the graphical object identified by
   * the given native handle. This method may be used with
   * environment such as Eclipse/SWT where it is easy
   * to get the native handle of any "Shell" object as it is
   * a public member variable.
   *
   * @param handle an <code>int</code> value
   * @param region a <code>Region</code> value
   * @param redraw a <code>boolean</code> value
   */
  public void setWindowRegion(int handle, Region region, boolean redraw) {
    throw new Error("Not Implemented");
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
    throw new Error("Not Implemented");
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
    throw new Error("Not Implemented");
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
    throw new Error("Not Implemented");
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
    throw new Error("Not Implemented");
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
    throw new Error("Not Implemented");
  }

  /**
   * Description of the Method
   *
   * @param image  Description of Parameter
   * @return       Description of the Returned Value
   */
  public Region createRegion(Image image) {
    return createRegion(image, image.getWidth(null), image.getHeight(null));
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
    return new ImageRegion(image, width, height);
  }

  /**
   * Set the window to be always on top of the others.
   *
   * @param window a <code>Window</code> value
   * @param enable true to put window on top, false to restore the default behaviour
   */
  public void setAlwaysOnTop(Window window, boolean enable) {
    throw new Error("Not Implemented");
  }

}
