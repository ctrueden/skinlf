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
import com.l2fprod.gui.plaf.skin.SkinTitlePane;
import com.l2fprod.gui.plaf.skin.SkinUtils;
import com.l2fprod.gui.plaf.skin.SkinWindowButton;
import com.l2fprod.gui.plaf.skin.impl.AbstractSkinFrame;
import com.l2fprod.util.IniFile;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.9 $, $Date: 2009-05-01 13:21:08 $
 */
final class KdeFrame extends AbstractSkinFrame {

  DefaultButton topSelected, topUnselected;
  int topHeight = 17;
  java.util.Vector buttonList;
  int textShiftLeft = SkinTitlePane.ICON_OFFSET, textShiftRight = 0;
  int textAlignment = LEFT;
  boolean textAbsolutePosition = false;

  boolean pixmapUnderTitle = false;
  boolean titleFrameShaded = false;

  Border border;

  final static String[] TEXT_ALIGNMENTS = {
      "left", "middle", "right"
      };

  final static int LEFT = 0;
  final static int MIDDLE = 1;
  final static int RIGHT = 2;

  /**
   * Constructor for the KdeFrame object
   *
   * @param ini            Description of Parameter
   * @param skinURL        Description of Parameter
   * @exception Exception  Description of Exception
   */
  public KdeFrame(IniFile ini, URL skinURL) throws Exception {

    String path = ini.getKeyValue("Window Titlebar", "TitlebarPixmapActive");
    if (path != null) {
      Image image = SkinUtils.loadImage(new URL(skinURL, path));
      Insets border;
      if (ini.getKeyValue("Window Titlebar", "TitlebarPixmapActiveBorder") != null) {
        border = SkinUtils.stringToInsets(ini.getKeyValue("Window Titlebar", "TitlebarPixmapActiveBorder"));
      } else {
        border =
          new Insets(ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveTop"),
                     ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveLeft"),
                     ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveBottom"),
                     ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveRight"));
      }
      topSelected =
        new DefaultButton(image,
                          image.getWidth(null),
                          image.getHeight(null),
                          border.top,
                          border.right,
                          border.bottom,
                          border.left);
      topHeight = topSelected.getHeight();
    }

    path = ini.getKeyValue("Window Titlebar", "TitlebarPixmapInactive");
    if (path != null) {
      Image image = SkinUtils.loadImage(new URL(skinURL, path));
      Insets border;
      if (ini.getKeyValue("Window Titlebar", "TitlebarPixmapActiveBorder") != null) {
        border = SkinUtils.stringToInsets(ini.getKeyValue("Window Titlebar", "TitlebarPixmapActiveBorder"));
      } else {
        border =
          new Insets(ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveTop"),
                     ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveLeft"),
                     ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveBottom"),
                     ini.getKeyIntValue("Window Titlebar", "TitlebarPixmapActiveRight"));
      }
      topUnselected =
          new DefaultButton(image,
          image.getWidth(null),
          image.getHeight(null),
                            border.top,
                            border.right,
                            border.bottom,
                            border.left);
      //	    topUnselected = SkinUtils.loadImage(new URL(skinURL, path));
      topHeight = Math.max(topHeight, topUnselected.getHeight());
    }

    pixmapUnderTitle = "yes".equals(ini.getKeyValue("Window Titlebar", "PixmapUnderTitleText"));
    titleFrameShaded = "yes".equals(ini.getKeyValue("Window Titlebar", "TitleFrameShaded"));

    String textAlignmentValue = ini.getKeyValue("Window Titlebar", "TitleAlignment");
    if (textAlignmentValue != null) {
      textAlignmentValue = textAlignmentValue.toLowerCase();
      for (int i = 0, c = TEXT_ALIGNMENTS.length; i < c; i++) {
        if (TEXT_ALIGNMENTS[i].equals(textAlignmentValue)) {
          textAlignment = i;
          break;
        }
      }
    }
    buttonList = new java.util.Vector();
    textAbsolutePosition = "yes".equals(ini.getKeyValue("Window Titlebar", "TitleAbsolutePosition"));

    final String letters = "ABCDEF";
    if (ini.getSection("Window Button Layout") == null) {
      ini.addSection("Window Button Layout");
      ini.setKeyValue("Window Button Layout", "ButtonA", "Off");
      ini.setKeyValue("Window Button Layout", "ButtonB", "Off");
      ini.setKeyValue("Window Button Layout", "ButtonC", "Off");
      ini.setKeyValue("Window Button Layout", "ButtonD", "Minimize");
      ini.setKeyValue("Window Button Layout", "ButtonE", "Maximize");
      ini.setKeyValue("Window Button Layout", "ButtonF", "Close");
    }

    for (int i = 0, c = letters.length(); i < c; i++) {
      String button = ini.getKeyValue("Window Button Layout", "Button" + letters.charAt(i));
      if ((button != null) && ("Off".equalsIgnoreCase(button) == false)) {
        FrameButton fb = new FrameButton(ini, skinURL, button);
        
        int align = ini.getKeyIntValue("Window Button Layout", "Button"+letters.charAt(i)+"Align",-1);
        
        if (align==-1) {
            align = (i < c / 2) ? SkinTitlePane.ALIGN_TOP_LEFT :
                SkinTitlePane.ALIGN_TOP_RIGHT;
        } 
        fb.setAlign(align);
        fb.setEnabled(ini.getKeyBooleanValue("Window Button Layout", "Button"+letters.charAt(i)+"Enabled",true));
        fb.setTooltip(ini.getKeyValue("Window Button Layout", "Button"+letters.charAt(i)+"Tooltip"));
        
        if (fb.selectedIcon != null) {
          if (align== SkinTitlePane.ALIGN_TOP_LEFT) {
            textShiftLeft += fb.selectedIcon.getIconWidth();
          } else {
            textShiftRight += fb.selectedIcon.getIconWidth();
          }
          topHeight = Math.max(topHeight, fb.selectedIcon.getIconHeight());
        }
        buttonList.addElement(fb);
      }
    }
    textShiftLeft += 4;
    textShiftRight += 4;

    if ((ini.getSection("Window Border") != null) &&
        (ini.getSection("Window Border").size() > 0)) {
      border = new BorderUIResource(new KdeFrameBorder(ini, skinURL));
      UIManager.put("InternalFrame.border", border);
    }
    else {
      //border = UIManager.getBorder("InternalFrame.border");
    }
  }

  /**
   * Gets the TopPreferredSize attribute of the KdeFrame object
   *
   * @return   The TopPreferredSize value
   */
  public Dimension getTopPreferredSize() {
    return new Dimension(textShiftLeft + textShiftRight + 50, topHeight);
  }

  /**
   * Gets the WindowButtons attribute of the KdeFrame object
   *
   * @param align  Description of Parameter
   * @return       The WindowButtons value
   */
  public SkinWindowButton[] getWindowButtons(int align) {
    java.util.Vector buttons = new java.util.Vector();
    for (int i = 0, c = buttonList.size(); i < c; i++) {
      FrameButton newB = (FrameButton) buttonList.elementAt(i);
      if (newB.getAlign() == align) {
        buttons.addElement(newB.createButton());
      }
    }
    SkinWindowButton[] results = new SkinWindowButton[buttons.size()];
    buttons.copyInto(results);
    return results;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return true;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(JComponent c) {
    if (border != null) {
      c.setBorder(border);
      c.setOpaque(false);
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * Description of the Method
   *
   * @param g           Description of Parameter
   * @param c           Description of Parameter
   * @param isSelected  Description of Parameter
   * @param title       Description of Parameter
   * @return            Description of the Returned Value
   */
  public boolean paintTop(Graphics g, Component c, boolean isSelected, String title) {
    if (topSelected != null && topUnselected != null) {
      if (isSelected) {
        topSelected.paint(g, 0, 0, c);
      }
      else {
        topUnselected.paint(g, 0, 0, c);
      }
    }
    else {
      // fill a rectangle
      Color oldColor = g.getColor();
      if (isSelected) {
        g.setColor(UIManager.getColor("InternalFrame.activeTitleBackground"));
      }
      else {
        g.setColor(UIManager.getColor("InternalFrame.inactiveTitleBackground"));
      }
      g.fillRect(0, 0, ((JComponent) c).getWidth(), ((JComponent) c).getHeight());
      g.setColor(oldColor);
    }

    if (title != null) {
      FontMetrics fm = g.getFontMetrics();
      int fmHeight = fm.getHeight() - fm.getLeading();
      int baseline = (topHeight - fmHeight) / 2 + fm.getAscent() + fm.getLeading();
      int width = fm.stringWidth(title);

      int x = 0;

      switch (textAlignment) {
        case LEFT:
          x = textShiftLeft;
          break;
        case MIDDLE:
          if (textAbsolutePosition) {
            x = (((JComponent)c).getWidth() - width) / 2;
          } else {
            x = (((JComponent) c).getWidth() - textShiftLeft - textShiftRight) / 2 + textShiftLeft - width / 2;
          }
          break;
        case RIGHT:
          x = ((JComponent) c).getWidth() - width - textShiftRight;
          break;
      }

      if (pixmapUnderTitle == false) {
        Color oldColor = g.getColor();
        if (isSelected) {
          g.setColor(UIManager.getColor("InternalFrame.activeTitleBackground"));
        }
        else {
          g.setColor(UIManager.getColor("InternalFrame.inactiveTitleBackground"));
        }
        g.fillRect(x, 0, width, ((JComponent) c).getHeight());
        g.setColor(oldColor);
      }
      if (isSelected && titleFrameShaded) {
        Color oldColor = g.getColor();
        g.setColor(oldColor.darker().darker());
        g.drawString(title, x + 1, baseline + 1);
        g.setColor(oldColor);
      }
      g.drawString(title, x, baseline);
    }

    return true;
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private class FrameButton {
    ImageIcon selectedIcon;
    private ImageIcon rolloverIcon, downIcon, unselectedIcon;
    int align;
    int action = SkinTitlePane.NO_ACTION;
    boolean enabled = true;
    String command;
    String tooltip;

    /**
     * Constructor for the FrameButton object
     *
     * @param ini            Description of Parameter
     * @param skinURL        Description of Parameter
     * @param command        Description of Parameter
     * @exception Exception  Description of Exception
     */
    FrameButton(IniFile ini, URL skinURL, String command) throws Exception {
      if ("Iconify".equals(command)) {
        command = "Minimize";
      }

      String path = ini.getKeyValue("Window Titlebar", command + "Button");
      if (path != null) {
        selectedIcon = new ImageIcon(SkinUtils.loadImage(new URL(skinURL, path)));
        unselectedIcon = selectedIcon;
        downIcon = selectedIcon;
        rolloverIcon = selectedIcon;
      }
      path = ini.getKeyValue("Window Titlebar", command + "DownButton");
      if (path != null) {
        downIcon = new ImageIcon(SkinUtils.loadImage(new URL(skinURL, path)));
      }
      path = ini.getKeyValue("Window Titlebar", command + "InactiveButton");
      if (path != null) {
        unselectedIcon = new ImageIcon(SkinUtils.loadImage(new URL(skinURL, path)));
      }
      path = ini.getKeyValue("Window Titlebar", command + "RolloverButton");
      if (path != null) {
        rolloverIcon = new ImageIcon(SkinUtils.loadImage(new URL(skinURL, path)));
      }

      if ("Maximize".equalsIgnoreCase(command)) {
        action = SkinTitlePane.MAXIMIZE_ACTION;
      }
      else if ("Minimize".equalsIgnoreCase(command)) {
        action = SkinTitlePane.MINIMIZE_ACTION;
      }
      else if ("Close".equalsIgnoreCase(command)) {
        action = SkinTitlePane.CLOSE_ACTION;
      }
      this.command = command;
    }

    public void setEnabled(boolean b) {
        this.enabled = b;
    }
    
    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    /**
     * Sets the Align attribute of the FrameButton object
     *
     * @param align  The new Align value
     */
    public void setAlign(int align) {
      this.align = align;
    }

    /**
     * Gets the Align attribute of the FrameButton object
     *
     * @return   The Align value
     */
    public int getAlign() {
      return align;
    }

    /**
     * Description of the Method
     *
     * @return   Description of the Returned Value
     */
    public SkinWindowButton createButton() {
      SkinWindowButton button = new SkinWindowButton(-1, (topHeight - selectedIcon.getIconHeight()) / 2, align, action);
      if (selectedIcon != null) {
        button.setSize(selectedIcon.getIconWidth(), selectedIcon.getIconHeight());
        button.setIcon(unselectedIcon);
        button.setRolloverIcon(rolloverIcon!=null?rolloverIcon:selectedIcon);
        button.setRolloverSelectedIcon(rolloverIcon!=null?rolloverIcon:selectedIcon);
        button.setPressedIcon(downIcon);
        button.setSelectedIcon(selectedIcon);
        button.setDisabledIcon(unselectedIcon);
        button.setDisabledSelectedIcon(unselectedIcon);
        button.setActionCommand(command);
        button.setEnabled(enabled);
        if (tooltip!=null)
            button.setToolTipText(tooltip);
      }
      return button;
    }

  }

}
