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
package com.l2fprod.gui.plaf.skin.impl.kde;

import com.l2fprod.gui.plaf.skin.SkinUtils;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkinPersonality;
import com.l2fprod.util.ImageUtils;
import com.l2fprod.util.IniFile;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.JComponent;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
final class KdePersonality extends AbstractSkinPersonality {

  Image wallpaper;
  int wallpaperPaintMode;

  /**
   * Constructor for the KdePersonality object
   *
   * @param ini            Description of Parameter
   * @param skinURL        Description of Parameter
   * @exception Exception  Description of Exception
   */
  public KdePersonality(IniFile ini, URL skinURL) throws Exception {
    String path = ini.getKeyValue("Display", "Wallpaper0");
    if (path != null) {
      try {
        wallpaper = SkinUtils.loadImage(new URL(skinURL, path));
        wallpaperPaintMode =
            "Scaled".equalsIgnoreCase(ini.getKeyValue("Display", "WallpaperMode0")) ?
            ImageUtils.PAINT_STRETCH : ImageUtils.PAINT_TILE;
      } catch (Exception e) {
        // silently ignore the wallpaper loading error
        wallpaper = null;
      }
    }
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean paintBackground(Graphics g, Component c) {
    if (wallpaper != null) {
      ImageUtils.paint(c, g, wallpaper, wallpaperPaintMode);
      return true;
    }
    else {
      return false;
    }
  }

}

