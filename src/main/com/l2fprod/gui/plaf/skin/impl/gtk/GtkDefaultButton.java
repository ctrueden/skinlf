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

import com.l2fprod.gui.icon.ArrowIcon;
import com.l2fprod.gui.plaf.skin.DefaultButton;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.SwingConstants;

final class GtkDefaultButton extends DefaultButton {

  /** mainly used by optionmenu */
  boolean hasArrow = true;

  public GtkDefaultButton(Image bitmap, int imageWidth, int imageHeight,
                          int topHeight, int rightWidth, int bottomHeight, int leftWidth) {
    super(bitmap, imageWidth, imageHeight,
          topHeight, rightWidth, bottomHeight, leftWidth, false);
  }

  public GtkDefaultButton(Image bitmap, Image gap, Image gap_start, Image gap_end, int imageWidth, int imageHeight,
          			      int topHeight, int rightWidth, int bottomHeight, int leftWidth) {
    super(bitmap, gap, gap_start, gap_end, imageWidth, imageHeight,
          topHeight, rightWidth, bottomHeight, leftWidth);
  }  
  
  public GtkDefaultButton(Image top, Image bottom, Image left, Image right,
                          Image topLeft, Image topRight, Image bottomLeft, Image bottomRight) {
    super(top, bottom, left, right,
          topLeft, topRight, bottomLeft, bottomRight);
  }

  public GtkDefaultButton(Image bitmap, int imageWidth, int imageHeight,
                          int topHeight, int rightWidth, int bottomHeight, int leftWidth, boolean tile) {
    super(bitmap, imageWidth, imageHeight,
          topHeight, rightWidth, bottomHeight, leftWidth, tile);
  }
      
  public void paint(Graphics g, Component c) {
    super.paint(g, c);
    if (!hasArrow) {
      ArrowIcon.paintArrow(g, 5,
                           c.getWidth() - rightWidth,
                           c.getHeight() / 2 - 3,
                           rightWidth,
                           c.getHeight(),
                           SwingConstants.SOUTH,
                           c.isEnabled());
    }
  }

}

