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
package com.l2fprod.gui.plaf.skin;

import com.l2fprod.gui.icon.ArrowIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.CellRendererPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * @author    $Author: l2fprod $
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinComboBoxUI extends BasicComboBoxUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  private boolean rollover = false;

  private MouseListener rolloverListener;

  public SkinComboBoxUI() {
    // Install a new renderer for the selected value which will work with
    // both Java 1.4 and 1.5.
    currentValuePane = new CellRendererPane() {
      public void paintComponent(
          Graphics g,
          Component c,
          Container p,
          int x,
          int y,
          int w,
          int h,
          boolean shouldValidate) {
        JComponent jc = (JComponent) c;
        jc.setOpaque(false);
        super.paintComponent(g, c, p, x, y, w, h, shouldValidate);
        // This is a bit hacky, but restoring this property to it's previous
        // value does NOT work on J2SE 1.5.0_05 somehow.
        jc.setOpaque(true);
      }
    };
  }

  /**
   * Gets the PreferredSize attribute of the SkinComboBoxUI object
   *
   * @param c  Description of Parameter
   * @return   The PreferredSize value
   */
  public Dimension getPreferredSize(JComponent c) {
    Dimension prefs = super.getMinimumSize(c);
    Dimension combo = skin.getPersonality().getComboBoxPreferredSize(comboBox);
    prefs.width = Math.max(prefs.width, combo.width);
    prefs.height = Math.max(prefs.height, combo.height);
    return prefs;
  }

  /**
   * Gets the MinimumSize attribute of the SkinComboBoxUI object
   *
   * @param c  Description of Parameter
   * @return   The MinimumSize value
   */
  public Dimension getMinimumSize(JComponent c) {
    return getPreferredSize(c);
  }

  protected void installListeners() {
    super.installListeners();
    rolloverListener = createRolloverListener();
    comboBox.addMouseListener(rolloverListener);
  }

  protected void uninstallListeners() {
    super.uninstallListeners();
    comboBox.removeMouseListener(rolloverListener);
    rolloverListener = null;
  }

  protected MouseListener createRolloverListener() {
    return new RolloverMouseListener();
  }

  public class RolloverMouseListener extends MouseAdapter {
    public void mouseEntered(MouseEvent event) {
      rollover = true;
      comboBox.repaint();
    }
    public void mouseExited(MouseEvent event) {
      rollover = false;
      comboBox.repaint();
    }
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
    hasFocus = comboBox.hasFocus();
    Rectangle r = rectangleForCurrentValue();
    skin.getPersonality().paintComboBox(g, comboBox, r, hasFocus, rollover);
    if (!comboBox.isEditable() || editor == null || !editor.isVisible()) {
      paintCurrentValue(g, r, false);
    }
  }
  
  /**
   * Gets the Insets attribute of the SkinComboBoxUI object
   *
   * @return   The Insets value
   */
  protected Insets getInsets() {
    return skin.getPersonality().getComboBoxInsets();
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected JButton createArrowButton() {
    JButton button = new JButton(new ArrowIcon(ArrowIcon.SOUTH));
    return button;
  }

  /**
   * Description of the Method
   */
  protected void installComponents() {
    super.installComponents();
    arrowButton.setVisible(false);
  }

  protected void configureEditor() {
    super.configureEditor();
    editor.addMouseListener(rolloverListener);

    if (comboBox.getEditor() != null &&
        comboBox.getEditor() instanceof UIResource &&
        editor instanceof JComponent) {
      ((JComponent)editor).setOpaque(false);
    }
  }

  protected void unconfigureEditor() {
    super.unconfigureEditor();
    editor.removeMouseListener(rolloverListener);
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected Rectangle rectangleForCurrentValue() {
    int width = comboBox.getWidth();
    int height = comboBox.getHeight();
    Insets insets = getInsets();
    return new Rectangle(insets.left, insets.top,
                         width - (insets.left + insets.right),
                         height - (insets.top + insets.bottom));
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected LayoutManager createLayoutManager() {
    return new SkinComboBoxLayoutManager();
  }

  protected JComboBox comboBox() {
    return comboBox;
  }

  /**
   * Description of the Method
   */
  protected void installKeyboardActions() {
    super.installKeyboardActions();

    ActionListener downAction =
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (comboBox().isEnabled()) {
            if (isPopupVisible()) {
              selectNextPossibleValue();
            }
            else {
              setPopupVisible(comboBox(), true);
            }
          }
        }
      };

    comboBox.registerKeyboardAction(downAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    comboBox.registerKeyboardAction(downAction,
        KeyStroke.getKeyStroke("KP_DOWN"),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    ActionListener altAction =
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (comboBox().isEnabled() && isPopupVisible()) {
            togglePopup();
          }
        }
      };

    comboBox.registerKeyboardAction(altAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.ALT_MASK),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    comboBox.registerKeyboardAction(altAction,
        KeyStroke.getKeyStroke("alt KP_DOWN"),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    comboBox.registerKeyboardAction(altAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.ALT_MASK),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    comboBox.registerKeyboardAction(altAction,
        KeyStroke.getKeyStroke("alt KP_UP"),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    ActionListener upAction =
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if (comboBox().isEnabled() && isPopupVisible()) {
            selectPreviousPossibleValue();
          }
        }
      };

    comboBox.registerKeyboardAction(upAction,
        KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    comboBox.registerKeyboardAction(upAction,
        KeyStroke.getKeyStroke("KP_UP"),
        JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  /**
   * Description of the Method
   */
  protected void uninstallKeyboardActions() {
    super.uninstallKeyboardActions();
    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke("KP_DOWN"));

    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.ALT_MASK));
    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke("alt KP_DOWN"));

    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke("KP_UP"));

    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.ALT_MASK));
    comboBox.unregisterKeyboardAction(KeyStroke.getKeyStroke("alt KP_UP"));
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected Component editor() {
    return editor;
  }

  /**
   * Gets the PopupVisible attribute of the SkinComboBoxUI object
   *
   * @return   The PopupVisible value
   */
  boolean isPopupVisible() {
    return super.isPopupVisible(comboBox);
  }

  /**
   * Description of the Method
   */
  void togglePopup() {
    toggleOpenClose();
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinComboBoxUI();
  }

  public PropertyChangeListener createPropertyChangeListener() {
    return new SkinPropertyChangeListener();
  }

  /**
   * This inner class is marked &quot;public&quot; due to a compiler bug. This
   * class should be treated as a &quot;protected&quot; inner class. Instantiate
   * it only within subclasses of <FooUI>.
   */
  public class SkinPropertyChangeListener extends
    BasicComboBoxUI.PropertyChangeHandler {
    public void propertyChange(PropertyChangeEvent e) {
      super.propertyChange(e);
      String propertyName = e.getPropertyName();

      if (propertyName.equals("background")) {
        Color color = (Color)e.getNewValue();
        arrowButton.setBackground(color);
        listBox.setBackground(color);

      } else if (propertyName.equals("foreground")) {
        Color color = (Color)e.getNewValue();
        arrowButton.setForeground(color);
        listBox.setForeground(color);
      }
    }
  }

  /**
   * Description of the Class
   * 
   * @author fred
   */
  public class SkinComboBoxLayoutManager implements LayoutManager {
    /**
     * Adds a feature to the LayoutComponent attribute of the
     * SkinComboBoxLayoutManager object
     *
     * @param name  The feature to be added to the LayoutComponent attribute
     * @param comp  The feature to be added to the LayoutComponent attribute
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * Description of the Method
     *
     * @param comp  Description of Parameter
     */
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * Description of the Method
     *
     * @param parent  Description of Parameter
     * @return        Description of the Returned Value
     */
    public Dimension preferredLayoutSize(Container parent) {
      return parent.getPreferredSize();
    }

    /**
     * Description of the Method
     *
     * @param parent  Description of Parameter
     * @return        Description of the Returned Value
     */
    public Dimension minimumLayoutSize(Container parent) {
      return parent.getMinimumSize();
    }

    /**
     * Description of the Method
     *
     * @param parent  Description of Parameter
     */
    public void layoutContainer(Container parent) {
      Rectangle cvb;
      if (SkinComboBoxUI.this.editor() != null) {
        cvb = rectangleForCurrentValue();
        SkinComboBoxUI.this.editor().setBounds(cvb);
      }
    }
  }

}
