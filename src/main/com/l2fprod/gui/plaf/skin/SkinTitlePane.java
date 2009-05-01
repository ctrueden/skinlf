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

import com.l2fprod.util.OS;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * Description of the Class
 *
 * @author    fred
 */
public class SkinTitlePane extends BasicInternalFrameTitlePane {

  /**
   * Description of the Field
   */
  protected Window m_Window;

  private SkinWindowButton[] m_WindowButtons;
  
  /**
   * Description of the Field
   */
  private Action shadeAction;

  private Skin skin;

  private WindowListener m_WindowListener;
  
  public final static int ICON_OFFSET = 16;

  /**
   * Align button relative to top left of window
   */
  public final static int ALIGN_TOP_LEFT = 0;

  /**
   * Align button relative to the top right of window
   */
  public final static int ALIGN_TOP_RIGHT = 1;

  /**
   * Description of the Field
   */
  public final static int CLOSE_ACTION = 0;

  /**
   * Description of the Field
   */
  public final static int MAXIMIZE_ACTION = 22;

  /**
   * Description of the Field
   */
  public final static int MINIMIZE_ACTION = 23;

  /**
   * Description of the Field
   */
  public final static int NO_ACTION = -1;

  /**
   * Constructor for the SkinTitlePane object
   *
   * @param f  Description of Parameter
   */
  public SkinTitlePane(JInternalFrame f) {
    this(new Window.InternalFrameWindow(f));
  }

  /**
   * Constructor for the SkinTitlePane object
   *
   * @param f  Description of Parameter
   */
  public SkinTitlePane(Window f) {
    super(null);
    m_Window = f;

    // in JDK1.4 Sun changed the way listener is added remove before
    // it was in add/remove notify now add is made in installDefaults
    // and remove by BasicInternalFrameUI
    if (OS.isOneDotFourOrMore()) {
      installListeners();
    }
  }

  /**
   * Gets the Window attribute of the SkinTitlePane object
   *
   * @return   The Window value
   */
  public Window getWindow() {
    return m_Window;
  }

  /**
   * Gets the PreferredSize attribute of the SkinTitlePane object
   *
   * @return   The PreferredSize value
   */
  public Dimension getPreferredSize() {
    return skin.getFrame().getTopPreferredSize();
  }

  /**
   * Gets the MinimumSize attribute of the SkinTitlePane object
   *
   * @return   The MinimumSize value
   */
  public Dimension getMinimumSize() {
    return skin.getFrame().getTopPreferredSize();
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   */
  public void paintComponent(Graphics g) {
    boolean isSelected = m_Window.isSelected();

    Font f = g.getFont();

    if (m_Window.getTitle() != null) {
      if (isSelected) {
        g.setColor(selectedTextColor);
      }
      else {
        g.setColor(notSelectedTextColor);
      }
      g.setFont(UIManager.getFont("InternalFrame.titleFont"));
    }

    skin.getFrame().paintTop(g, this, isSelected, m_Window.getTitle());

    g.setFont(f);

  }

  /**
   * Sets the ButtonIcons attribute of the SkinTitlePane object
   */
  protected void setButtonIcons() {
  }

  /**
   * Description of the Method
   */
  protected void installTitlePane() {
    installDefaults();
    
    createActions();
    enableActions();
    assembleSystemMenu();
    setLayout(createLayout());

    add(menuBar);

    setOpaque(true);

    skin = SkinLookAndFeel.getSkin();

    createButtons();
  }

  /**
   * Description of the Method
   */
  protected void createActions() {
    maximizeAction = new MaximizeAction();
    maximizeAction.setEnabled(true);
    iconifyAction = new IconifyAction();
    iconifyAction.setEnabled(true);
    closeAction = new CloseAction();
    closeAction.setEnabled(true);
    restoreAction = new RestoreAction();
    restoreAction.setEnabled(true);
    shadeAction = new ShadeAction();
    shadeAction.setEnabled(true);
  }

  /**
   * Overriden to register on the window
   */
  protected void installListeners() {
    // the window container may be null when the titlePane is used by
    // a JFrame/JDialog
    if (m_Window == null || m_Window.getContainer() == null) {
      return;
    }

    if (propertyChangeListener == null) {
      propertyChangeListener = createPropertyChangeListener();
    }
    m_Window.addPropertyChangeListener(propertyChangeListener);

    if (m_Window instanceof Window.FrameWindow) {
      if (m_WindowListener == null) {
        m_WindowListener = createWindowListener();
      }
      ((Window.FrameWindow)m_Window).getMainFrame().addWindowListener(
        m_WindowListener);
    }
  }

  /**
   * called by the SkinRootPaneUI when the window container has been
   * set
   */
  void windowSet() {
    installListeners();
  }
  
  /**
   * Tracks window activation events to update the buttons and the
   * titlebar
   */
  private WindowListener createWindowListener() {
    return new WindowAdapter() {      
      public void windowActivated(WindowEvent e) {
        try {
          m_Window.setSelected(true);
          updateButtons();
        } catch (PropertyVetoException ex) {}
      }      
      public void windowDeactivated(WindowEvent e) {
        try {
          m_Window.setSelected(false);
          updateButtons();
        } catch (PropertyVetoException ex) {}
      }
    };
  }
  
  /**
   * Overriden to unregister on the window
   */
  protected void uninstallListeners() {
    m_Window.removePropertyChangeListener(propertyChangeListener);
    if (m_Window instanceof Window.FrameWindow && m_WindowListener != null) {
      ((Window.FrameWindow)m_Window).getMainFrame().removeWindowListener(
        m_WindowListener);
    }
  }

  /**
   * Same as parent class except it does not initialize the icons
   */
  protected void installDefaults() {
    selectedTitleColor = UIManager.getColor("InternalFrame.activeTitleBackground");
    selectedTextColor = UIManager.getColor("InternalFrame.activeTitleForeground");
    notSelectedTitleColor = UIManager.getColor("InternalFrame.inactiveTitleBackground");
    notSelectedTextColor = UIManager.getColor("InternalFrame.inactiveTitleForeground");
  }

  /**
   * Description of the Method
   */
  protected void createButtons() {
    SkinWindowButton[] buttonsLeft =
        skin.getFrame().getWindowButtons(ALIGN_TOP_LEFT);
    if (buttonsLeft != null) {
      for (int i = 0, c = buttonsLeft.length; i < c; i++) {
        addButton(buttonsLeft[i]);
      }
    }

    SkinWindowButton[] buttonsRight =
        skin.getFrame().getWindowButtons(ALIGN_TOP_RIGHT);
    if (buttonsRight != null) {
      for (int i = 0, c = buttonsRight.length; i < c; i++) {
        addButton(buttonsRight[i]);
      }
    }
    
    m_WindowButtons = new SkinWindowButton[buttonsLeft.length
      + buttonsRight.length];
    System.arraycopy(buttonsLeft, 0, m_WindowButtons, 0, buttonsLeft.length);
    System.arraycopy(buttonsRight, 0, m_WindowButtons, buttonsLeft.length,
      buttonsRight.length);
  }

  private void updateButtons() {
    for (int i = 0; i < m_WindowButtons.length; i++) {
      m_WindowButtons[i].setSelected(m_Window.isSelected());
    }
    repaint();    
  }
  
  /**
   * Adds a feature to the Button attribute of the SkinTitlePane object
   *
   * @param button  The feature to be added to the Button attribute
   */
  protected void addButton(SkinWindowButton button) {
    switch (button.getWindowAction()) {
      case CLOSE_ACTION:
        button.addActionListener(closeAction);
        registerButtonForAction(button, closeAction);
        break;
      case MAXIMIZE_ACTION:
        button.addActionListener(maximizeAction);
        registerButtonForAction(button, maximizeAction);
        break;
      case MINIMIZE_ACTION:
        button.addActionListener(iconifyAction);
        registerButtonForAction(button, iconifyAction);
        break;
    }
    add(button);
  }

  /**
   * Adds a feature to the SystemMenuItems attribute of the SkinTitlePane object
   *
   * @param systemMenu  The feature to be added to the SystemMenuItems attribute
   */
  protected void addSystemMenuItems(JMenu systemMenu) {
    JMenuItem mi = systemMenu.add(restoreAction);
    mi.setMnemonic('R');
    mi = systemMenu.add(iconifyAction);
    mi.setMnemonic('n');
    mi = systemMenu.add(maximizeAction);
    mi.setMnemonic('x');
    if (!Boolean.TRUE.equals(UIManager.get("TitlePane.disableShade"))) {
      systemMenu.add(shadeAction);
    }
    systemMenu.add(new JSeparator());
    mi = systemMenu.add(closeAction);
    mi.setMnemonic('C');
  }

  protected JMenuBar createSystemMenuBar() {
    menuBar = new SystemMenuBar();
    menuBar.setBorderPainted(false);
    return menuBar;
  }

  /**
   * Description of the Method
   */
  protected void enableActions() {
    if (m_Window == null) {
      return;
    }    
    restoreAction.setEnabled(m_Window.isMaximum() || m_Window.isIcon());
    maximizeAction.setEnabled(m_Window.isMaximizable());
    iconifyAction.setEnabled(m_Window.isIconifiable() && !m_Window.isIcon());
    closeAction.setEnabled(m_Window.isClosable());
    shadeAction.setEnabled(!m_Window.isMaximum() && !m_Window.isIcon());
    doLayout();
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected PropertyChangeListener createPropertyChangeListener() {
    return new PropertyChangeHandler();
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  protected LayoutManager createLayout() {
    return new TitlePaneLayout();
  }

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   * @return   Description of the Returned Value
   */
  protected PropertyChangeListener createActionChangeListener(AbstractButton b) {
    return new ActionChangedListener(b);
  }

  // end TitlePaneLayout

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   * @param a  Description of Parameter
   */
  private void registerButtonForAction(AbstractButton b, Action a) {
    PropertyChangeListener actionPropertyChangeListener =
        createActionChangeListener(b);
    a.addPropertyChangeListener(actionPropertyChangeListener);
    b.setEnabled(a.isEnabled());
  }

  /**
   * Description of the Class
   *
   * @author    fred
   */
  public class PropertyChangeHandler implements PropertyChangeListener {
    /**
     * Description of the Method
     *
     * @param evt  Description of Parameter
     */
    public void propertyChange(PropertyChangeEvent evt) {

      String prop = evt.getPropertyName();

      if (JInternalFrame.IS_SELECTED_PROPERTY.equals(prop)) {
        updateButtons();
        repaint();
        return;
      }

	    enableActions();
      
      revalidate();
      repaint();
    }
  }

  /**
   * Description of the Class
   *
   * @author    fred
   */
  public class TitlePaneLayout implements LayoutManager {
    /**
     * Adds a feature to the LayoutComponent attribute of the TitlePaneLayout
     * object
     *
     * @param name  The feature to be added to the LayoutComponent attribute
     * @param c     The feature to be added to the LayoutComponent attribute
     */
    public void addLayoutComponent(String name, Component c) {
    }

    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     */
    public void removeLayoutComponent(Component c) {
    }

    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     * @return   Description of the Returned Value
     */
    public Dimension preferredLayoutSize(Container c) {
      return new Dimension(100, 18);
    }

    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     * @return   Description of the Returned Value
     */
    public Dimension minimumLayoutSize(Container c) {
      return preferredLayoutSize(c);
    }

    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     */
    public void layoutContainer(Container c) {
      int w = getWidth();
      int nmembers = c.getComponentCount();
      int atlX = 2;
      int atrX = 0;

      menuBar.setBounds(atlX, (getHeight() - 16) / 2, 16, 16);
      atlX += 18;

      for (int i = 1
      /*
       *  skip menubar
       */
          ; i < nmembers; i++) {
        SkinWindowButton m = (SkinWindowButton) c.getComponent(i);
        m.setSelected(m_Window.isSelected());
        // only adjust the system buttons.
        boolean userDefinedAction =(m.getWindowAction()==SkinTitlePane.NO_ACTION); 
        if (!userDefinedAction)
            m.setVisible(m.isEnabled());
        if (m.isEnabled() || userDefinedAction) {
          if (m.getAlign() == ALIGN_TOP_LEFT) {
            if (m.getXCoord() == -1) {
              m.setLocation(atlX, Math.max(m.getYCoord(), 1));
              atlX += m.getWidth();
            }
            else {
              m.setLocation(m.getXCoord(), m.getYCoord());
            }
          }
          else if (m.getAlign() == ALIGN_TOP_RIGHT) {
            if (m.getXCoord() == -1) {
              m.setLocation(w - atrX - m.getWidth(), Math.max(m.getYCoord(), 1));
              atrX += m.getWidth();
            }
            else {
              m.setLocation(w - m.getXCoord(), m.getYCoord());
            }
          }
        }
      }
    }
  }

  /**
   * This inner class is marked &quot;public&quot; due to a compiler bug. This
   * class should be treated as a &quot;protected&quot; inner class. Instantiate
   * it only within subclasses of <Foo>.
   *
   * @author    fred
   */
  public class CloseAction extends AbstractAction {
    /**
     * Constructor for the CloseAction object
     */
    public CloseAction() {
      super("Close");
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void actionPerformed(ActionEvent e) {
      if (m_Window.isClosable()) {
        try {
          m_Window.setClosed(true);
        } catch (PropertyVetoException e0) {
        }
      }
    }
  }

  // end CloseAction

  /**
   * This inner class is marked &quot;public&quot; due to a compiler bug. This
   * class should be treated as a &quot;protected&quot; inner class. Instantiate
   * it only within subclasses of <Foo>.
   *
   * @author    fred
   */
  public class MaximizeAction extends AbstractAction {
    /**
     * Constructor for the MaximizeAction object
     */
    public MaximizeAction() {
      super("Maximize");
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void actionPerformed(ActionEvent e) {
      if (m_Window.isMaximizable()) {
        if (!m_Window.isMaximum()) {
          try {
            m_Window.setMaximum(true);
          } catch (PropertyVetoException e5) {
          }
        }
        else {
          try {
            m_Window.setMaximum(false);
            if (m_Window.isIconifiable() && m_Window.isIcon()) {
              m_Window.setIcon(false);
            }
          } catch (PropertyVetoException e6) {
          }
        }
      }
    }
  }

  // MaximizeAction

  /**
   * This inner class is marked &quot;public&quot; due to a compiler bug. This
   * class should be treated as a &quot;protected&quot; inner class. Instantiate
   * it only within subclasses of <Foo>.
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class IconifyAction extends AbstractAction {
    /**
     * Constructor for the IconifyAction object
     */
    public IconifyAction() {
      super("Minimize");
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void actionPerformed(ActionEvent e) {
      if (m_Window.isIconifiable()) {
        if (!m_Window.isIcon()) {
          try {
            m_Window.setIcon(true);
          } catch (PropertyVetoException e1) {
          }
        }
        else {
          try {
            m_Window.setIcon(false);
            if (m_Window.isMaximizable() && m_Window.isMaximum()) {
              m_Window.setMaximum(false);
            }
          } catch (PropertyVetoException e1) {
          }
        }
      }
    }
  }

  // end IconifyAction

  /**
   * This inner class is marked &quot;public&quot; due to a compiler bug. This
   * class should be treated as a &quot;protected&quot; inner class. Instantiate
   * it only within subclasses of <Foo>.
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class RestoreAction extends AbstractAction {
    /**
     * Constructor for the RestoreAction object
     */
    public RestoreAction() {
      super("Restore");
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void actionPerformed(ActionEvent e) {
      if (m_Window.isMaximizable() && m_Window.isMaximum()) {
        try {
          m_Window.setMaximum(false);
        } catch (PropertyVetoException e4) {
        }
      }
      else if (m_Window.isIconifiable() && m_Window.isIcon()) {
        try {
          m_Window.setIcon(false);
        } catch (PropertyVetoException e4) {
        }
      }
    }
  }

  // end RestoreAction

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class ShadeAction extends AbstractAction {
    /**
     * Constructor for the ShadeAction object
     */
    public ShadeAction() {
      super("Shade");
    }

    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void actionPerformed(ActionEvent event) {
      m_Window.setShaded(!m_Window.isShaded());
    }
  }

  /**
   * This inner class is marked &quot;public&quot; due to a compiler bug. This
   * class should be treated as a &quot;protected&quot; inner class. Instantiate
   * it only within subclasses of <Foo>.
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class SystemMenuBar extends JMenuBar {
    /**
     * Gets the FocusTraversable attribute of the SystemMenuBar object
     *
     * @return   The FocusTraversable value
     */
    public boolean isFocusTraversable() {
      return false;
    }

    /**
     * Gets the Opaque attribute of the SystemMenuBar object
     *
     * @return   The Opaque value
     */
    public boolean isOpaque() {
      return true;
    }

    /**
     * Description of the Method
     */
    public void requestFocus() {
    }

    /**
     * Description of the Method
     *
     * @param g  Description of Parameter
     */
    public void paint(Graphics g) {
      Icon icon = m_Window.getFrameIcon();
      if (icon == null) {
        icon = UIManager.getIcon("InternalFrame.icon");
      }
      if (icon != null) {
        // Resize to 16x16 if necessary.
        if (icon instanceof ImageIcon && (icon.getIconWidth() > 16 || icon.getIconHeight() > 16)) {
          Image img = ((ImageIcon) icon).getImage();
          ((ImageIcon) icon).setImage(img.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        }
        icon.paintIcon(this, g, 0, 0);
      }
    }
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private class ActionChangedListener implements PropertyChangeListener {
    AbstractButton button;

    /**
     * Constructor for the ActionChangedListener object
     *
     * @param b  Description of Parameter
     */
    ActionChangedListener(AbstractButton b) {
      super();
      setTarget(b);
    }

    /**
     * Sets the Target attribute of the ActionChangedListener object
     *
     * @param b  The new Target value
     */
    public void setTarget(AbstractButton b) {
      this.button = b;
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void propertyChange(PropertyChangeEvent e) {
      String propertyName = e.getPropertyName();
      if (e.getPropertyName().equals(Action.NAME)) {
        String text = (String) e.getNewValue();
        button.setText(text);
        button.repaint();
      }
      else if (propertyName.equals("enabled")) {
        Boolean enabledState = (Boolean) e.getNewValue();
        button.setEnabled(enabledState.booleanValue());
        button.repaint();
      }
      else if (e.getPropertyName().equals(Action.SMALL_ICON)) {
        Icon icon = (Icon) e.getNewValue();
        button.setIcon(icon);
        button.invalidate();
        button.repaint();
      }
    }
  }

}
// End Title Pane Class

