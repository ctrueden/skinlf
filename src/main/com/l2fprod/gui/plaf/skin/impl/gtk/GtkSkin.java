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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.*;

import com.l2fprod.gui.border.*;
import com.l2fprod.gui.plaf.skin.*;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkin;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;

/**
 * GTK (The Gimp Toolkit) Skin Support. <BR>
 * GtkSkin can be used in conjunction with a gtkrc file.<BR>
 * You can find skins at:
 * <LI> <A HREF="http://gtk.themes.org">gtk.themes.org</A> <BR>
 * <BR>
 * Simply extract the skin file in a directory and use:<BR>
 * <BR>
 * <B> <CODE>
 * SkinLookAndFeel.setSkin(new GtkSkin("c:\downloads\myskin\gtk\gtkrc"));<BR>
 * UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
 * </CODE> </B> <BR>
 * <BR>
 * to enable skins in your application ! <BR>
 * <BR>
 * <BR>
 * <BR>
 * Created on 28/01/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: zombi $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-12-22 10:25:05 $
 */
public final class GtkSkin extends AbstractSkin {

  String[] colors;

  static String[] swingToGtk = {
      "desktop", "",
      "activeCaption", "",
      "activeCaptionText", "",
      "activeCaptionBorder", "",
      "inactiveCaption", "",
      "inactiveCaptionText", "",
      "inactiveCaptionBorder", "",
      "window", "window.bg[NORMAL]",
      "windowBorder", "window.bg[NORMAL]",
      "windowText", "window.fg[NORMAL]",
      "menu", "menu.bg[NORMAL]",
      "menuPressedItemB", "bg[ACTIVE]",
      "menuPressedItemF", "fg[ACTIVE]",
      "menuText", "fg[NORMAL]",
      "text", "bg[NORMAL]",
      "textText", "fg[NORMAL]",
      "textHighlight", "bg[SELECTED]",
      "textHighlightText", "fg[SELECTED]",
      "textInactiveText", "fg[INSENSITIVE]",
      "control", "button.bg[NORMAL]",
      "controlText", "button.fg[NORMAL]",
      "controlHighlight", "",
      "controlLtHighlight", "",
      "controlShadow", "",
      "controlDkShadow", "",
      "scrollbar", "",
      "info", "",
      "infoText", "",
      };

  /**
   * Construct a new GtkSkin using the given filename
   *
   * @param filename       path to a gtk skin (gtkrc) file
   * @exception Exception  Description of Exception
   */
  public GtkSkin(String filename) throws Exception {
    this(SkinUtils.toURL(new File(filename)));
  }

  /**
   * Construct a new GtkSkin using the given url
   *
   * @param url            path to a gtk skin (gtkrc) file
   * @exception Exception  Description of Exception
   */
  public GtkSkin(java.net.URL url) throws Exception {
    GtkParser parser = new GtkParser(url);
    parser.buildStructure();

    init(parser);
  }

  /**
   * Constructor for the GtkSkin object
   *
   * @param url            Description of Parameter
   * @param input          Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkSkin(java.net.URL url, java.io.InputStream input) throws Exception {
    GtkParser parser = new GtkParser(input);
    parser.setDirectory(url);
    parser.buildStructure();

    init(parser);
  }

  /**
   * Constructor for the GtkSkin object
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  public GtkSkin(GtkParser parser) throws Exception {
    init(parser);
  }

  /**
   * Gets the Personality attribute of the GtkSkin object
   *
   * @return   The Personality value
   */
  public SkinPersonality getPersonality() {
    return personality;
  }

  /**
   * Gets the Button attribute of the GtkSkin object
   *
   * @return   The Button value
   */
  public SkinButton getButton() {
    return button;
  }

  /**
   * Gets the Frame attribute of the GtkSkin object
   *
   * @return   The Frame value
   */
  public SkinFrame getFrame() {
    return null;
  }

  /**
   * Gets the Tab attribute of the GtkSkin object
   *
   * @return   The Tab value
   */
  public SkinTab getTab() {
    return tab;
  }

  /**
   * Gets the Progress attribute of the GtkSkin object
   *
   * @return   The Progress value
   */
  public SkinProgress getProgress() {
    return progress;
  }

  /**
   * Gets the Colors attribute of the GtkSkin object
   *
   * @return   The Colors value
   */
  public String[] getColors() {
    return colors;
  }

  /**
   * Gets the Scrollbar attribute of the GtkSkin object
   *
   * @return   The Scrollbar value
   */
  public SkinScrollbar getScrollbar() {
    return scrollbar;
  }

  /**
   * Gets the SplitPane attribute of the GtkSkin object
   *
   * @return   The SplitPane value
   */
  public SkinSplitPane getSplitPane() {
    return splitpane;
  }

  /**
   * Gets the Slider attribute of the GtkSkin object
   *
   * @return   The Slider value
   */
  public SkinSlider getSlider() {
    return slider;
  }

  /**
   * Description of the Method
   *
   * @param parser         Description of Parameter
   * @exception Exception  Description of Exception
   */
  private void init(GtkParser parser) throws Exception {
    personality = new GtkPersonality(parser);
    button = new GtkButton(parser);
    tab = new GtkTab(parser);
    progress = new GtkProgress(parser);
    scrollbar = new GtkScrollbar(parser);
    splitpane = new GtkSplitPane(parser);
    slider = new GtkSlider(parser);
    separator = new GtkSeparator(parser);

    java.util.Vector colorList = new java.util.Vector();
    for (int i = 0; i < swingToGtk.length; i = i + 2) {
      String colorName = swingToGtk[i + 1];
      String color = null;

      if ("".equals(colorName)) {
        continue;
      }

      int index = colorName.indexOf(".");
      if (index != -1) {
        if (parser.getStyle(colorName.substring(0, index)) != null) {
          color = (String) parser.getStyle(colorName.substring(0, index)).getProperty(colorName.substring(index + 1));
        }
        if (color == null) {
          colorName = colorName.substring(index + 1);
        }
      }

      if (color == null) {
        color = (String) parser.getStyle("default").getProperty(colorName);
      }

      if (color != null) {
        colorList.addElement(swingToGtk[i]);
        colorList.addElement(SkinUtils.decodeColor(color));
      }
    }
    colors = new String[colorList.size()];
    colorList.copyInto(colors);

    disabledTextBorder =
      GtkUtils.newButton(parser, "default",
                         new String[]{"function", "state", "detail"},
                         new String[]{"FLAT_BOX", "INSENSITIVE", "text"}, true);

    textBorder =
      GtkUtils.newButton(parser, "default",
                         new String[]{"function", "state", "detail"},
                         new String[]{"FLAT_BOX", null, "text"}, true);
    
    if (disabledTextBorder != null && textBorder != null) {
      textBorder = new BorderUIResource(new ActiveBorder(textBorder, disabledTextBorder));
    }

    /* NEED MORE WORK ON TOOLTIP
    tooltipBorder =
      GtkUtils.newButton(parser, "default",
                         new String[]{"function", "detail"},
                         new String[]{"FLAT_BOX", "tooltip"});
    */
  }

  javax.swing.border.Border textBorder;
  javax.swing.border.Border disabledTextBorder;
  /* NEED MORE WORK ON TOOLTIP
     javax.swing.border.Border tooltipBorder;
  */
  public void initComponentDefaults(UIDefaults table) {
    super.initComponentDefaults(table);
    // set border on components but based on property (for now)
    if (Boolean.TRUE.equals(table.get("EnableBorders"))) {
      if (textBorder != null) {
        table.put("TextField.border", textBorder);
        table.put("PasswordField.border", textBorder);
        table.put("ScrollPane.border", textBorder);
        table.put("Spinner.border", textBorder);
        //      table.put("ComboBox.border", textBorder);
      }
      /* NEED MORE WORK ON TOOLTIP
      if (tooltipBorder != null) {
        table.put("ToolTip.border", tooltipBorder);
      }
      */
    }
  }

  /**
   * Get the user's current GTK skin location.<br>
   * This could be used on a Linux platform. It looks for the user theme in the
   * ~/.gtkrc user file.
   *
   * @return                              the skin location or null if the
   *      user's current skin can't be found
   * @exception GtkSkinNotFoundException  Description of Exception
   */
  public static String getDefaultSkinLocation() throws GtkSkinNotFoundException {
    String home = System.getProperty("user.home");
    if (home == null) {
      return null;
    }

    String gtkrc = home + File.separator + ".gtkrc";
    File f = new File(gtkrc);
    if (!f.exists()) {
      throw new GtkSkinNotFoundException();
    }

    try {
      BufferedReader br = new BufferedReader(new FileReader(f));
      String s = br.readLine();
      while (s != null) {
        if (s.indexOf("gtkrc") != -1) {
          String s2 = s.substring(9, s.length() - 1);
          File f2 = new File(s2);
          if (f2.exists()) {
            return f2.getCanonicalPath();
          }
        }
        s = br.readLine();
      }
    } catch (Exception e) {
      throw new GtkSkinNotFoundException();
    }
    throw new GtkSkinNotFoundException();
  }

}
