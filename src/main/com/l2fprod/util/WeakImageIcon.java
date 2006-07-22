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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

class WeakImageIcon extends ImageIcon {

  public WeakImageIcon(Image image) {
    super(image);
  }
  
  public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
    ImageObserver observer = getImageObserver();
    if (observer == null) {
      g.drawImage(getImage(), x, y, null);
    } else {
      g.drawImage(getImage(), x, y, null);
    }
  }

}
