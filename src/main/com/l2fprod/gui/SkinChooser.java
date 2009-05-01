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

import com.l2fprod.gui.plaf.skin.CompoundSkin;
import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import com.l2fprod.gui.plaf.skin.SkinPreviewWindow;
import com.l2fprod.util.OS;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.*;

/**
 * Skin Chooser. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:24:45 $
 */
public class SkinChooser extends JPanel {

  private JList skinList;
  private String[] directories;

  private JCheckBox backgroundCheckBox, scrollBarCheckBox;

  private ResourceBundle bundle;

  private boolean themePackMode = false;

  final static String REFRESH_CMD = "refresh";
  final static String PREVIEW_CMD = "preview";
  final static String GETSKINS_CMD = "getskins";

  /**
   * Construct a new SkinChooser pane.
   */
  public SkinChooser() {
    loadResourceBundle();

    setLayout(new BorderLayout(3, 3));

    JPanel listPane = new JPanel(new BorderLayout(3, 3));

    JPanel buttonPane = new JPanel(new GridLayout(1, 3, 3, 3));

    JButton button = new JButton(bundle.getString("SkinChooser.getskins"));
    buttonPane.add(button);
    button.addActionListener(new GetSkinsAction());
    button.setToolTipText(bundle.getString("SkinChooser.getskins.tip"));

    button = new JButton(bundle.getString("SkinChooser.preview"));
    buttonPane.add(button);
    button.addActionListener(new PreviewAction());

    button = new JButton(bundle.getString("SkinChooser.refresh"));
    buttonPane.add(button);
    button.addActionListener(new RefreshAction());

    listPane.add("Center", new JScrollPane(skinList = new JList()));
    listPane.add("South", buttonPane);

    add("Center", listPane);

    Box optionPane = Box.createVerticalBox();
    optionPane.add(backgroundCheckBox = new JCheckBox(bundle.getString("SkinChooser.enableBackground")));
    optionPane.add(scrollBarCheckBox = new JCheckBox(bundle.getString("SkinChooser.enableScrollBar")));
    add("East", optionPane);
  }

  /**
   * Set search paths
   *
   * @param directories  search paths
   */
  public void setSkinLocations(String[] directories) {
    this.directories = directories;
    Vector skins = new Vector();
    for (int i = 0, c = directories.length; i < c; i++) {
      buildSkinList(skins, new File(directories[i]));
    }
    skinList.setListData(skins);
  }

  /**
   * Set theme pack mode to true if you want to select a theme pack from the
   * chooser.
   *
   * @param b  the new value
   */
  public void setThemePackMode(boolean b) {
    themePackMode = b;
  }

  /**
   * @return   search paths
   */
  public String[] getSkinLocations() {
    return directories;
  }

  /**
   * @return   true if the chooser shows only theme packs
   */
  public boolean getThemePackMode() {
    return themePackMode;
  }

  /**
   * @return   the currently selected skins
   */
  public String[] getSelectedSkins() {
    return (String[]) skinList.getSelectedValues();
  }

  /**
   * Refresh the skin list.
   *
   * @see   #setSkinLocations
   */
  public void refreshList() {
    if ((directories != null) && (directories.length > 0)) {
      setSkinLocations(directories);
    }
  }

  /**
   * Apply current selection. <br>
   * The method sets the current skin (SkinLookAndFeel.setSkin) then calls
   * UIManager.setLookAndFeel.
   *
   * @exception Exception  Description of Exception
   */
  public void apply() throws Exception {
    Object[] values = skinList.getSelectedValues();
    if ((values == null) ||
        (themePackMode && values.length != 1)
        || (!themePackMode && values.length != 2)) {
      return;
    }

    UIManager.put("JDesktopPane.backgroundEnabled",
        backgroundCheckBox.isSelected() ? Boolean.TRUE : null);
    UIManager.put("ScrollBar.alternateLayout",
        scrollBarCheckBox.isSelected() ? Boolean.TRUE : null);

    Skin skin = null;

    if (themePackMode) {
      skin = SkinLookAndFeel.loadThemePack((String) values[0]);
    }
    else {
      skin = new CompoundSkin(SkinLookAndFeel.loadSkin((String) values[0]),
          SkinLookAndFeel.loadSkin((String) values[1]));
    }

    SkinLookAndFeel.setSkin(skin);

    UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
  }

  /**
   * Recursively traverse <code>directory</code> and add skin files to <code>v</code>
   * . <br>
   * Skin files are added if <code>accept(skinFile)</code> returns <code>true</code>
   *
   * @param v          vector to store skin list
   * @param directory  the directory to list for skin files
   */
  protected void buildSkinList(Vector v, File directory) {
    if (!directory.isDirectory() || !directory.exists()) {
      return;
    }

    String[] files = directory.list();
    File f;
    for (int i = 0, c = files.length; i < c; i++) {
      f = new File(directory, files[i]);
      if (f.isDirectory()) {
        buildSkinList(v, f);
      }
      else if (accept(f)) {
        try {
          v.addElement(f.getCanonicalPath());
        } catch (IOException e) {
        }
      }
    }
  }

  /**
   * Check if a given file is a skin file. <br>
   * Subclasses can override this method to provide better handling of skin
   * files. <br>
   * The default implementation checks if the file ends with gtkrc or themerc.
   *
   * @param f  the file to check
   * @return   true if the file is a valid skin file
   */
  protected boolean accept(File f) {
    return (f.isDirectory() == false &&
        ((themePackMode && f.getName().endsWith(".zip")) ||
        (f.getName().endsWith("gtkrc") || f.getName().endsWith("themerc"))));
  }

  /**
   * Description of the Method
   */
  protected void showPreviewWindow() {
    Skin oldSkin = SkinLookAndFeel.getSkin();
    LookAndFeel oldLAF = UIManager.getLookAndFeel();

    try {
      Object[] values = skinList.getSelectedValues();
      if ((values == null) || (values.length != 2)) {
        return;
      }

      Skin skin = new CompoundSkin(SkinLookAndFeel.loadSkin((String) values[0]),
          SkinLookAndFeel.loadSkin((String) values[1]));

      SkinLookAndFeel.setSkin(skin);
      UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");

      Window window = new SkinPreviewWindow();
      window.setVisible(true);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      SkinLookAndFeel.setSkin(oldSkin);
      try {
        UIManager.setLookAndFeel(oldLAF);
      } catch (UnsupportedLookAndFeelException e) {
      }
    }
  }

  /**
   * Description of the Method
   */
  private void loadResourceBundle() {
    bundle = ResourceBundle.getBundle("com.l2fprod.gui.plaf.skin.resources.skin");
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private class RefreshAction extends AbstractAction {
    /**
     * Constructor for the RefreshAction object
     */
    public RefreshAction() {
      super(REFRESH_CMD);
    }

    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void actionPerformed(ActionEvent event) {
      refreshList();
    }
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private class GetSkinsAction extends AbstractAction {
    /**
     * Constructor for the GetSkinsAction object
     */
    public GetSkinsAction() {
      super(GETSKINS_CMD);
    }

    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void actionPerformed(ActionEvent event) {
      try {
        OS.openDocument(bundle.getString("SkinChooser.getskins.url"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private class PreviewAction extends AbstractAction {
    /**
     * Constructor for the PreviewAction object
     */
    public PreviewAction() {
      super(PREVIEW_CMD);
    }

    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void actionPerformed(ActionEvent event) {
      showPreviewWindow();
    }
  }

}
