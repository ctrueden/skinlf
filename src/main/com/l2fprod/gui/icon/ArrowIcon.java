/* ====================================================================
 *
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright (c) @YEAR@ L2FProd.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by L2FProd.com
 *        (http://www.L2FProd.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not
 *    be used to endorse or promote products derived from this software
 *    without prior written permission. For written permission, please
 *    contact info@L2FProd.com.
 *
 * 5. Products derived from this software may not be called "SkinLF"
 *    nor may "SkinLF" appear in their names without prior written
 *    permission of L2FProd.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL L2FPROD.COM OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package com.l2fprod.gui.icon;

import java.awt.*;
import javax.swing.*;

public class ArrowIcon implements Icon, SwingConstants {

  private static final int DEFAULT_SIZE = 5;

  private int size;
  private int direction;
  private int nbOfIcon = 1;

  public ArrowIcon(int direction) {
    this(DEFAULT_SIZE, direction);
  }

  public ArrowIcon(int size, int direction) {
    this.size = size;
    this.direction = direction;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    if (direction == NORTH || direction == SOUTH) {
      paintTriangle(g, x, y, getIconWidth(), getIconHeight(), direction, c.isEnabled());
    } else if (direction == WEST || direction==EAST) {
      paintTriangle(g, x, y, getIconWidth(), getIconHeight(), direction, c.isEnabled());
    }
  }

  public int getIconWidth() {
    return size;
  }

  public int getIconHeight() {
    return size;
  }

  private void paintTriangle(Graphics g, int x, int y, int width, int height,
			     int direction, boolean isEnabled) {
    paintArrow(g, size,
               x, y, width, height,
               direction, isEnabled);
  }

  public static void paintArrow(Graphics g,
                                int size,
                                int x, int y, int width, int height,
                                int direction, boolean isEnabled) {
    Color oldColor = g.getColor();
    int midW, midH, i, j, maxSize;

    j = 0;
    maxSize = Math.max(size, 2);
    midW = width / 2;
    midH = height / 2;

    g.translate(x, y);
    if(isEnabled)
      g.setColor(UIManager.getColor("controlDkShadow"));
    else
      g.setColor(UIManager.getColor("controlShadow"));

    switch(direction)       {
    case NORTH:
      for(i = 0; i < maxSize; i++)      {
        g.drawLine(midW-i, i, midW+i, i);
      }
      if(!isEnabled)  {
        g.setColor(UIManager.getColor("controlLtHighlight"));
        g.drawLine(midW-i+2, i, midW+i, i);
      }
      break;
    case SOUTH:
      if(!isEnabled)  {
        g.translate(1, 1);
        g.setColor(UIManager.getColor("controlLtHighlight"));
        for(i = maxSize-1; i >= 0; i--)   {
          g.drawLine(midW-i, j, midW+i, j);
          j++;
        }
        g.translate(-1, -1);
        g.setColor(UIManager.getColor("controlShadow"));
      }

      j = 0;
      for(i = maxSize-1; i >= 0; i--)   {
        g.drawLine(midW-i, j, midW+i, j);
        j++;
      }
      break;
    case WEST:
      for(i = 0; i < maxSize; i++)      {
        g.drawLine(i, midH-i, i, midH+i);
      }
      if(!isEnabled)  {
        g.setColor(UIManager.getColor("controlLtHighlight"));
        g.drawLine(i, midH-i+2, i, midH+i);
      }
      break;
    case EAST:
      if(!isEnabled)  {
        g.translate(1, 1);
        g.setColor(UIManager.getColor("controlLtHighlight"));
        for(i = maxSize-1; i >= 0; i--)   {
          g.drawLine(j, midH-i, j, midH+i);
          j++;
        }
        g.translate(-1, -1);
        g.setColor(UIManager.getColor("controlShadow"));
      }

      j = 0;
      for(i = maxSize-1; i >= 0; i--)   {
        g.drawLine(j, midH-i, j, midH+i);
        j++;
      }
      break;
    }
    g.translate(-x, -y);
    g.setColor(oldColor);
  }
}
