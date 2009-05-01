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

import com.l2fprod.gui.plaf.skin.DefaultButton;
import com.l2fprod.gui.plaf.skin.SkinUtils;
import com.l2fprod.util.IniFile;

import java.net.URL;

/**
 * Created on 08/04/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
final class KdeFrameBorder extends DefaultButton {

  /**
   * Constructor for the KdeFrameBorder object
   *
   * @param ini            Description of Parameter
   * @param skinURL        Description of Parameter
   * @exception Exception  Description of Exception
   */
  public KdeFrameBorder(IniFile ini, URL skinURL) throws Exception {
    super(ini.isNullOrEmpty("Window Border", "shapePixmapTop") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapTop"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapBottom") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapBottom"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapLeft") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapLeft"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapRight") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapRight"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapTopLeft") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapTopLeft"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapTopRight") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapTopRight"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapBottomLeft") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapBottomLeft"))),
        ini.isNullOrEmpty("Window Border", "shapePixmapBottomRight") ? null :
        SkinUtils.loadImage(new URL(skinURL, ini.getKeyValue("Window Border", "shapePixmapBottomRight"))));
  }

}
