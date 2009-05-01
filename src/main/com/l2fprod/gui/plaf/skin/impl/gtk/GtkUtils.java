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

import com.l2fprod.gui.plaf.skin.impl.gtk.parser.GtkParser;
import com.l2fprod.util.ImageUtils;

import java.awt.Image;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkUtils {

  /**
   * Description of the Field
   */
  public final static boolean DEBUG = "true".equals(com.l2fprod.util.AccessUtils.getProperty("debug.gtk"));

  /**
   * Description of the Method
   *
   * @param parser         Description of Parameter
   * @param style          Description of Parameter
   * @param keys           Description of Parameter
   * @param values         Description of Parameter
   * @return               Description of the Returned Value
   * @exception Exception  Description of Exception
   */
  public static GtkDefaultButton newButton(GtkParser parser,
      String style, String[] keys, String[] values) throws Exception {
    return newButton(parser, style, keys, values, false, false, true, false);
  }

  /**
   * Description of the Method
   *
   * @param parser         Description of Parameter
   * @param style          Description of Parameter
   * @param keys           Description of Parameter
   * @param values         Description of Parameter
   * @param useOverlay     Description of Parameter
   * @return               Description of the Returned Value
   * @exception Exception  Description of Exception
   */
  public static GtkDefaultButton newButton(GtkParser parser,
      String style, String[] keys, String[] values,
      boolean useOverlay) throws Exception {
    return newButton(parser, style, keys, values, useOverlay, false, true, false);
  }

  public static GtkDefaultButton newButton(GtkParser parser,
      String style, String[] keys, String[] values,
      boolean useOverlay, boolean exactMatch) throws Exception {
    return newButton(parser, style, keys, values,
              useOverlay, exactMatch, true, false);
  }

  /**
   * Description of the Method
   *
   * @param parser         Description of Parameter
   * @param style          Description of Parameter
   * @param keys           Description of Parameter
   * @param values         Description of Parameter
   * @param useOverlay     Description of Parameter
   * @param exactMatch     Description of Parameter
   * @return               Description of the Returned Value
   * @exception Exception  Description of Exception
   */
  public static GtkDefaultButton newButton(GtkParser parser,
      String style, String[] keys, String[] values,
      boolean useOverlay, boolean exactMatch, boolean useDefault, boolean useGap) throws Exception {
    if (DEBUG) {
      System.out.println("Looking in " + style + " for ");
      for (int i = 0, c = keys.length; i < c; i++) {
        System.out.println("\t" + keys[i] + " = " + values[i]);
      }
    }

    try {
      GtkDefaultButton button = null;
      // parser.getClass(style).getStyles
      GtkStyle[] styles;
      if (parser.getClass(style) == null) {
        styles = new GtkStyle[]{parser.getStyle(style)};
      }
      else {
        if (DEBUG) {
          System.out.println("style " + style + " exists as class");
        }
        styles = parser.getClass(style).getStyles();
      }

      if (DEBUG) {
        System.out.println("Style " + style + " (count:" + styles.length + ")");
      }

      for (int i = 0, c = styles.length; i < c; i++) {
        GtkStyle gtkstyle = styles[i];

        if (DEBUG) {
          System.out.println("\tSub: " + styles[i]);
        }

        if (gtkstyle != null) {
          GtkImage image = gtkstyle.getEngine().findImage(keys, values, exactMatch);
          if (DEBUG) {
            System.out.println("\t\tImage is " + image);
          }
          if (image != null) {
            
            Image bitmap = null;
            Image gap = null;
            Image gap_start = null;
            Image gap_end = null;

            GtkBorder border = (GtkBorder) image.getProperty(useOverlay ? "overlay_border" : "border");
            if (useOverlay && border == null) {
              border = (GtkBorder) image.getProperty("border");
            }
            if (useOverlay == false && border == null) {
              border = (GtkBorder) image.getProperty("overlay_border");
            }

            if (border == null) {
              border = new GtkBorder(0, 0, 0, 0);
            }

            bitmap = image.getImage(parser.getDirectory(), useOverlay ? "overlay_file" : "file");

            if (useOverlay && bitmap == null) {
              bitmap = image.getImage(parser.getDirectory(), "file");
            }
            if (useOverlay == false && bitmap == null) {
              bitmap = image.getImage(parser.getDirectory(), "overlay_file");
            }

            if(useGap) {
              gap = image.getImage(parser.getDirectory(), "gap_file");
              gap_start = image.getImage(parser.getDirectory(), "gap_start_file");
              gap_end = image.getImage(parser.getDirectory(), "gap_end_file");
            }
            
            button = new GtkDefaultButton(bitmap,
                                          gap,
                                          gap_start,
                                          gap_end,
                                          bitmap.getWidth(ImageUtils.producer),
                                          bitmap.getHeight(ImageUtils.producer),
                                          border.top, border.right, border.bottom, border.left);
            button.setCenterFill("TRUE".equals(image.getProperty(useOverlay ? "overlay_stretch" : "stretch")) ?
                                 ImageUtils.PAINT_STRETCH : ImageUtils.PAINT_TILE);
            // use by combobox to know if arrow has to be painted
            // big hack here!
            button.hasArrow = !"false".equals(image.getProperty("arrow"));
            break;
          }
        }
      }

      // if the button is still null and style != default
      // try more general style, this can give unpredictable result,
      // keys must be sorted by importance
      if (button == null && (!"default".equals(style)) && useDefault) {
        button = newButton(parser, "default", keys, values, false, true, false, false);

        int length = keys.length;
        while ((length > 0) && (button == null)) {
          length--;
          String[] subkeys = new String[length];
          System.arraycopy(keys, 0, subkeys, 0, length);
          button = newButton(parser, "default", subkeys, values, false, true, false, false);
        }

        // if the button is still null, our latest try is exactMatch in default
        if (button == null) {
          if (DEBUG) {
            System.out.println("defaulting to exactMatch");
          }
          button = newButton(parser, "default", keys, values, false, true);
        }
      }

      if (DEBUG && button == null) {
        System.out.println("button not found for " + style);
        System.out.println("image {");
        for (int i = 0, c = keys.length; i < c; i++) {
          System.out.println("\t" + keys[i] + " = " + values[i]);
        }
        System.out.println("}");
        Thread.dumpStack();
      }

      return button;
    } catch (java.io.FileNotFoundException e) {
      // can be thrown if the image specified in gtkrc points to a wrong file
      return null;
    } catch (RuntimeException e) {
      return null;
    }
  }

}
