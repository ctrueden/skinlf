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
package com.l2fprod.gui.plaf.skin;

import com.l2fprod.util.ImageUtils;

import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * @author    $Author: zombi $
 * @created   27 avril 2002
 * @version   $Revision: 1.8 $, $Date: 2009-12-22 10:25:06 $
 */
public final class SkinUtils {

  /**
   * Description of the Field
   */
  public final static boolean DEBUG =
    "true".equals(com.l2fprod.util.AccessUtils.getProperty("debug.skinlf"));

  /*
   *  Font f = null;
   *  Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
   *  for (int i = 0, c = fonts.length; i < c; i++) {
   *  if (fonts[i].getFontName().equals(name)) {
   *  f = fonts[i].deriveFont(type, size);
   *  break;
   *  }
   *  }
   *  return f;
   *  }
   */
  /**
   * Sets the Font attribute of the SkinUtils class
   *
   * @param f  The new Font value
   */
  public static void setFont(Font f) {
    UIDefaults defs = UIManager.getDefaults();
    setGlobalFont(defs, f);
  }

  /**
   * Set every font to the specified one.
   * @param defs
   * @param f
   */
	public static void setGlobalFont(UIDefaults defs, Font f) {
		for (Enumeration keys = defs.keys(); keys.hasMoreElements();) {
			Object o = keys.nextElement();
			if (o instanceof String) {
				String aKey = (String) o;
				if (aKey.endsWith(".font") || aKey.endsWith(".titleFont")
						|| aKey.endsWith(".acceleratorFont")) {
					if (defs.get(aKey) instanceof FontUIResource) {
						UIManager.put(aKey, f);
					}
				}
			}
		}
	}  
  
  
  /**
   * Gets the Font attribute of the SkinUtils class
   *
   * @param name  Description of Parameter
   * @param type  Description of Parameter
   * @param size  Description of Parameter
   * @return      The Font value
   */
  public static Font getFont(String name, int type, int size) {
    return new Font(name, type, size);
  }

  /**
   * Description of the Method
   *
   * @param filename       Description of Parameter
   * @return               Description of the Returned Value
   * @exception Exception  Description of Exception
   */
  public static Image loadImage(String filename) throws Exception {
    return loadImage(toURL(new File(filename)));
  }

  /**
   * Description of the Method
   *
   * @param url            Description of Parameter
   * @return               Description of the Returned Value
   * @exception Exception  Description of Exception
   */
  public static Image loadImage(URL url) throws Exception {
    Image img = null;
    byte[] imageByte = SkinLookAndFeel.getURLContent(url);
    img = Toolkit.getDefaultToolkit().
      createImage(imageByte, 0, imageByte.length);

    CustomImageObserver custom = new CustomImageObserver();
    Object lock = custom.getLock();

    synchronized (lock) {
      int width = img.getWidth(custom);
      int height = img.getHeight(custom);
      if (height < 1 && width < 1)
        lock.wait();
    }

    return ImageUtils.transparent(img);
  }

  /**
   * Description of the Method
   *
   * @param f                                   Description of Parameter
   * @return                                    Description of the Returned
   *      Value
   * @exception java.net.MalformedURLException  Description of Exception
   */
  public static URL toURL(File f) throws java.net.MalformedURLException {
    String path = f.getAbsolutePath();
    if (File.separatorChar != '/') {
      path = path.replace(File.separatorChar, '/');
    }
    if (!path.startsWith("/")) {
      path = "/" + path;
    }
    if (!path.endsWith("/") && f.isDirectory()) {
      path = path + "/";
    }
    return new URL("file", "", path);
  }

  // color is R,G,B
  // supported format of the string are
  // { r, g, b }
  // r g b
  // r,g,b
  // r.r, g.g, b.b
  /**
   * Description of the Method
   *
   * @param color  Description of Parameter
   * @return       Description of the Returned Value
   */
  public static String decodeColor(String color) {
    if (color.startsWith("#")) {
      return color;
    }
    java.util.StringTokenizer token =
      new java.util.StringTokenizer(color, ", {}");
    String result = "#";
    String r = token.nextToken();
    String g = token.nextToken();
    String b = token.nextToken();
    if (is0to1color(r) && is0to1color(g) && is0to1color(b)) {
      r = (int) (Float.parseFloat(r) * 255) + "";
      g = (int) (Float.parseFloat(g) * 255) + "";
      b = (int) (Float.parseFloat(b) * 255) + "";
    } else {
      r = (int) Float.parseFloat(r) + "";
      g = (int) Float.parseFloat(g) + "";
      b = (int) Float.parseFloat(b) + "";
    }
    result += toHexString(Integer.parseInt(r));
    result += toHexString(Integer.parseInt(g));
    result += toHexString(Integer.parseInt(b));
    return result;
  }

  /**
   * Description of the Method
   *
   * @param composite  Description of Parameter
   * @return           Description of the Returned Value
   */
  static boolean is0to1color(String composite) {
    int index = composite.indexOf(".");
    if (index != -1 && composite.substring(0, index).length() <= 1) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Description of the Method
   *
   * @param i  Description of Parameter
   * @return   Description of the Returned Value
   */
  static String toHexString(int i) {
    if (i == 0) {
      return "00";
    } else {
      return Integer.toHexString(i).toUpperCase();
    }
  }

  /**
   * Convert strings like 12, 12, 54, 45 to an insets
   * where values are LEFT, RIGHT, TOP, BOTTOM
   */
  public static Insets stringToInsets(String border) {
    StringTokenizer token = new StringTokenizer(border, ",");
    int left = Integer.parseInt(token.nextToken().trim());
    int right = Integer.parseInt(token.nextToken().trim());
    int top = Integer.parseInt(token.nextToken().trim());
    int bottom = Integer.parseInt(token.nextToken().trim());
    return new Insets(top, left, bottom, right);
  }

}
