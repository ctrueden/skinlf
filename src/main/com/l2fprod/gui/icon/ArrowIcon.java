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
package com.l2fprod.gui.icon;

import java.awt.*;
import javax.swing.*;

public class ArrowIcon implements Icon, SwingConstants {

  private static final int DEFAULT_SIZE = 5;

  private int size;
  private int direction;

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
