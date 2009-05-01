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
package com.l2fprod.gui;

import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import com.l2fprod.gui.plaf.skin.Skin;

import java.net.URL;
import javax.swing.*;

/**
 * SkinApplet.<BR>
 * Base class for applet that want to use the Skin Look And Feel.
 *
 * @author    <a href="mailto:fred@L2FProd.com">Frederic Lavigne</a>
 * @created   27 avril 2002
 */
public class SkinApplet extends JApplet {

  /**
   * Description of the Field
   */
  public static String THEMEPACK_TAG = "themepack";

  /**
   * Init the SkinLookAndFeel by using the applet parameter "themepack"
   * (THEMEPACK_TAG). if the parameter is not set, the themepack "themepack.zip"
   * is loaded.
   *
   * @exception Exception  if an error occurs while loading the themepack
   */
  public void initSkin() throws Exception {
    initSkin(THEMEPACK_TAG);
  }

  /**
   * Init the SkinLookAndFeel by using the applet parameter themepackTagName. if
   * the parameter is not set, the themepack "/themepack.zip" is loaded.
   *
   * @param themepackTagName  Description of Parameter
   * @exception Exception     if an error occurs while loading the themepack
   */
  public void initSkin(String themepackTagName) throws Exception {
    String themepack = getParameter(themepackTagName);

    URL themepackURL;

    // if no themepack has been provided in the applet tag
    // use the default themepack provided in the jar of the applet
    if (themepack == null) {
      themepackURL = SkinApplet.class.getResource("/themepack.zip");
    }
    else {
      // a themepack has been provided, relative to the codebase
      themepackURL = new URL(getCodeBase(), themepack);
    }

    Skin skin = SkinLookAndFeel.loadThemePack(themepackURL);
    SkinLookAndFeel.setSkin(skin);
    UIManager.setLookAndFeel(new SkinLookAndFeel());
  }

}
