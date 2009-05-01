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

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.l2fprod.util.IniFile;
import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkin;

/**
 * KDE (The K Desktop Environment) Skin Support. <BR>
 * KdeSkin can be used in conjunction with a kde.themerc file.<BR>
 * You can find skins at:
 * <LI> <A HREF="http://kde.themes.org">kde.themes.org</A> <BR>
 * <BR>
 * Simply extract the skin file in a directory and use:<BR>
 * <BR>
 * <B> <CODE>
 * SkinLookAndFeel.setSkin(new KdeSkin("c:\downloads\myskin\kde\kde.themerc"));<BR>
 * UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
 * </CODE> </B> <BR>
 * <BR>
 * to enable skins in your application ! <BR>
 * <BR>
 * <BR>
 * <BR>
 *
 *
 * @author    fred
 */
public final class KdeSkin extends AbstractSkin {

  private String[] colors;

  private final static Object[] swingToKde = {
      "desktop", new String[]{"desktop", "background"},
      "activeCaption", new String[]{"activeBackground", "background"},
      "activeCaptionText", new String[]{"activeForeground", "foreground"},
      "activeCaptionBorder", new String[]{""},
      "inactiveCaption", new String[]{"inactiveBackground", "background"},
      "inactiveCaptionText", new String[]{"inactiveForeground", "foreground"},
      "inactiveCaptionBorder", new String[]{""},
      "window", new String[]{"windowBackground", "background"},
      "windowBorder", new String[]{""},
      "windowText", new String[]{"windowForeground", "foreground"},
      "menu", new String[]{"background"},
      "menuPressedItemB", new String[]{"selectBackground"},
      "menuPressedItemF", new String[]{"selectForeground"},
      "menuText", new String[]{"foreground"},
      "text", new String[]{"background"},
      "textText", new String[]{"foreground"},
      "textHighlight", new String[]{"selectBackground"},
      "textHighlightText", new String[]{"selectForeground"},
      "textInactiveText", new String[]{""},
      "control", new String[]{"background"},
      "controlText", new String[]{"foreground"},
      "controlHighlight", new String[]{""},
      "controlLtHighlight", new String[]{""},
      "controlShadow", new String[]{""},
      "controlDkShadow", new String[]{""},
      "scrollbar", new String[]{""},
      "info", new String[]{""},
      "infoText", new String[]{""},
      };

  /**
   * Construct a new KDE skin with the given filename
   *
   * @param filename       path to a kde (themerc) skin file
   * @exception Exception  Description of Exception
   */
  public KdeSkin(String filename) throws Exception {
    this(SkinUtils.toURL(new File(filename)));
  }

  /**
   * Constructor for the KdeSkin object
   *
   * @param skinURL        Description of Parameter
   * @exception Exception  Description of Exception
   */
  public KdeSkin(URL skinURL) throws Exception {
    this(skinURL, skinURL.openStream());
  }

  /**
   * Constructor for the KdeSkin object
   *
   * @param skinURL        Description of Parameter
   * @param input          Description of Parameter
   * @exception Exception  Description of Exception
   */
  public KdeSkin(URL skinURL, InputStream input) throws Exception {
    IniFile ini = new IniFile(input);

    personality = new KdePersonality(ini, skinURL);
    frame = new KdeFrame(ini, skinURL);

    java.util.Vector colorList = new java.util.Vector();
    for (int i = 0; i < swingToKde.length; i = i + 2) {
      // "swingcolor", { "c1", "c2" }
      String[] locals = (String[]) swingToKde[i + 1];
      if (locals != null && locals.length > 0) {
        for (int j = 0, d = locals.length; j < d; j++) {
          if (ini.getKeyValue("Colors", locals[j]) != null) {
            colorList.addElement(swingToKde[i]);
            colorList.addElement(SkinUtils.decodeColor(ini.getKeyValue("Colors", locals[j])));
            break;
          }
        }
      }
    }
    colors = new String[colorList.size()];
    colorList.copyInto(colors);

  }

  /**
   * Gets the Colors attribute of the KdeSkin object
   *
   * @return   The Colors value
   */
  public String[] getColors() {
    return colors;
  }

}
