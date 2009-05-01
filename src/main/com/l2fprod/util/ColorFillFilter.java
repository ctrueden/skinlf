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
package com.l2fprod.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RGBImageFilter;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:23:51 $
 */
public class ColorFillFilter extends RGBImageFilter {

  /**
   * Description of the Field
   */
  public int m_Red, m_Green, m_Blue;

  /**
   * Constructor for the ColorFillFilter object
   *
   * @param r  Description of Parameter
   * @param g  Description of Parameter
   * @param b  Description of Parameter
   */
  public ColorFillFilter(int r, int g, int b) {
    setColor(r, g, b);
    canFilterIndexColorModel = true;
  }

  /**
   * Constructor for the ColorFillFilter object
   *
   * @param color  Description of Parameter
   */
  public ColorFillFilter(Color color) {
    this(100 * color.getRed() / 255,
        100 * color.getGreen() / 255,
        100 * color.getBlue() / 255);
  }

  /**
   * Constructor for the ColorFillFilter object
   */
  public ColorFillFilter() {
    this(100, 100, 100);
  }

  /**
   * Sets the Color attribute of the ColorFillFilter object
   *
   * @param r  The new Color value
   * @param g  The new Color value
   * @param b  The new Color value
   */
  public void setColor(int r, int g, int b) {
    m_Red = r;
    m_Green = g;
    m_Blue = b;
    if (false && m_Red == 100 && m_Green == 100 && m_Blue == 100) {
      m_Red = 90;
      m_Green = 90;
      m_Blue = 90;
    }
  }

  /**
   * Sets the Red attribute of the ColorFillFilter object
   *
   * @param r  The new Red value
   */
  public void setRed(int r) {
    m_Red = r;
  }

  /**
   * Sets the Green attribute of the ColorFillFilter object
   *
   * @param g  The new Green value
   */
  public void setGreen(int g) {
    m_Green = g;
  }

  /**
   * Sets the Blue attribute of the ColorFillFilter object
   *
   * @param b  The new Blue value
   */
  public void setBlue(int b) {
    m_Blue = b;
  }

  /**
   * Description of the Method
   *
   * @param x    Description of Parameter
   * @param y    Description of Parameter
   * @param rgb  Description of Parameter
   * @return     Description of the Returned Value
   */
  public int filterRGB(int x, int y, int rgb) {
    if (rgb == 0 || rgb == ImageUtils.TRANSPARENT_PIXEL) {
      return rgb;
    }

    int r = ((rgb & 0x00ff0000) >> 16) * m_Red / 100;
    if (r < 0) {
      r = 0;
    }
    if (r > 255) {
      r = 255;
    }
    int g = ((rgb & 0x0000ff00) >> 8) * m_Green / 100;
    if (g < 0) {
      g = 0;
    }
    if (g > 255) {
      g = 255;
    }
    int b = ((rgb & 0x000000ff)) * m_Blue / 100;
    if (b < 0) {
      b = 0;
    }
    if (b > 255) {
      b = 255;
    }

    return 0xff000000 | (r << 16) | (g << 8) | (b << 0);
  }

  public void filter(BufferedImage src, BufferedImage dest) {
    for (int x = 0, c = src.getWidth(); x < c; x++) {
      for (int y = 0, d = src.getHeight(); y < d; y++) {
        dest.setRGB(x, y, filterRGB(x, y, src.getRGB(x, y)));
      }
    }
  }
}
