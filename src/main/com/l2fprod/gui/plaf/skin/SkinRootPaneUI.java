/*
 * Adapted from MetalRootPaneUI comments below were copied from that class
 */

package com.l2fprod.gui.plaf.skin;

import com.l2fprod.util.AccessUtils;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JRootPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicRootPaneUI;

/**
 * 
 * Provides the metal look and feel implementation of <code>RootPaneUI</code>.
 * <p>
 * <code>MetalRootPaneUI</code> provides support for the <code>windowDecorationStyle</code>
 * property of <code>JRootPane</code>.<code>MetalRootPaneUI</code> does
 * this by way of installing a custom <code>LayoutManager</code>, a private
 * <code>Component</code> to render the appropriate widgets, and a private
 * <code>Border</code>. The <code>LayoutManager</code> is always
 * installed, regardless of the value of the <code>windowDecorationStyle</code>
 * property, but the <code>Border</code> and <code>Component</code> are
 * only installed/added if the <code>windowDecorationStyle</code> is other
 * than <code>JRootPane.NONE</code>.
 * <p>
 * <strong>Warning:</strong> Serialized objects of this class will not be
 * compatible with future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running the
 * same version of Swing. As of 1.4, support for long term storage of all
 * JavaBeans <sup><font size="-2">TM</font></sup> has been added to the
 * <code>java.beans</code> package. Please see {@link java.beans.XMLEncoder}.
 * 
 * @version 1.16 02/04/02
 * @author Terry Kellerman
 * @since 1.4
 */
public final class SkinRootPaneUI extends BasicRootPaneUI {

  // TO MAKE THE CODE COMPILE WITH JDK < 1.4
  public final static int Frame_MAXIMIZED_BOTH =
    AccessUtils.getIntValue(JFrame.class, "MAXIMIZED_BOTH");

  public final static int JRootPane_NONE =
    AccessUtils.getIntValue(JRootPane.class, "NONE");

  public static int getExtendedState(Frame p_Frame) {
    return AccessUtils.getAsInt(p_Frame, "getExtendedState");
  }

  public static void setExtendedState(Frame p_Frame, int p_Value) {
    AccessUtils.setAsInt(p_Frame, "setExtendedState", p_Value);
  }

  public static int getWindowDecorationStyle(JRootPane p_Pane) {
    return AccessUtils.getAsInt(p_Pane, "getWindowDecorationStyle");
  }

  private Skin skin = SkinLookAndFeel.getSkin();
  private Window.FrameWindow title = null;
  /**
   * Keys to lookup borders in defaults table.
   */
  private static final String[] borderKeys =
    new String[] {
      null,
      "RootPane.frameBorder",
      "RootPane.plainDialogBorder",
      "RootPane.informationDialogBorder",
      "RootPane.errorDialogBorder",
      "RootPane.colorChooserDialogBorder",
      "RootPane.fileChooserDialogBorder",
      "RootPane.questionDialogBorder",
      "RootPane.warningDialogBorder" };
  /**
   * The amount of space (in pixels) that the cursor is changed on.
   */
  private static final int CORNER_DRAG_WIDTH = 16;

  /**
   * Region from edges that dragging is active from.
   */
  private static final int BORDER_DRAG_THICKNESS = 5;

  /**
   * Window the <code>JRootPane</code> is in.
   */
  private java.awt.Window window;

  /** Make sure the title pane is repainted when needed */
  private WindowListener windowListener;
  
  /**
   * <code>JComponent</code> providing window decorations. This will be null
   * if not providing window decorations.
   */
  private SkinTitlePane titlePane;

  /**
   * <code>MouseInputListener</code> that is added to the parent <code>Window</code>
   * the <code>JRootPane</code> is contained in.
   */
  private MouseInputListener mouseInputListener;
  /**
   * The <code>LayoutManager</code> that is set on the <code>JRootPane</code>.
   */
  private LayoutManager layoutManager;

  /**
   * <code>LayoutManager</code> of the <code>JRootPane</code> before we
   * replaced it.
   */
  private LayoutManager savedOldLayout;

  /**
   * <code>JRootPane</code> providing the look and feel for.
   */
  private JRootPane root;

  /**
   * <code>Cursor</code> used to track the cursor set by the user. This is
   * initially <code>Cursor.DEFAULT_CURSOR</code>.
   */
  private Cursor lastCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

  /**
   * Creates a UI for a <code>JRootPane</code>.
   * 
   * @param c the JRootPane the RootPaneUI will be created for
   * @return the RootPaneUI implementation for the passed in JRootPane
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinRootPaneUI();
  }

  private static java.awt.Window translateSource(MouseEvent ev) {
    Object source = ev.getSource();
    java.awt.Window w;

    if (source.getClass() == SkinTitlePane.class) {
      SkinTitlePane titleSource = (SkinTitlePane)source;
      Window.FrameWindow win = (Window.FrameWindow)titleSource.getWindow();
      w = win.getMainFrame();
    } else
      w = (java.awt.Window)ev.getSource();
    return w;
  }

  /**
   * Invokes supers implementation of <code>installUI</code> to install the
   * necessary state onto the passed in <code>JRootPane</code> to render the
   * metal look and feel implementation of <code>RootPaneUI</code>. If the
   * <code>windowDecorationStyle</code> property of the <code>JRootPane</code>
   * is other than <code>JRootPane.NONE</code>, this will add a custom
   * <code>Component</code> to render the widgets to <code>JRootPane</code>,
   * as well as installing a custom <code>Border</code> and <code>LayoutManager</code>
   * on the <code>JRootPane</code>.
   * 
   * @param c the JRootPane to install state onto
   */
  public void installUI(JComponent c) {
    super.installUI(c);
    root = (JRootPane)c;
    int style = getWindowDecorationStyle(root);
    if (style != JRootPane_NONE) {
      installClientDecorations(root);

    }
    //skin.getFrame().installSkin(c);

  }

  /**
   * Invokes supers implementation to uninstall any of its state. This will
   * also reset the <code>LayoutManager</code> of the <code>JRootPane</code>.
   * If a <code>Component</code> has been added to the <code>JRootPane</code>
   * to render the window decoration style, this method will remove it.
   * Similarly, this will revert the Border and LayoutManager of the <code>JRootPane</code>
   * to what it was before <code>installUI</code> was invoked.
   * 
   * @param c the JRootPane to uninstall state from
   */
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    uninstallClientDecorations(root);

    layoutManager = null;
    mouseInputListener = null;
    windowListener = null;
    root = null;
  }

  /**
   * Installs the appropriate <code>Border</code> onto the <code>JRootPane</code>.
   */
  void installBorder(JRootPane root) {
    int style = getWindowDecorationStyle(root);

    if (style == JRootPane_NONE) {
      LookAndFeel.uninstallBorder(root);
    } else {

      //LookAndFeel.installBorder(root, borderKeys[style]);
      skin.getFrame().installSkin(root);
    }
  }

  /**
   * Removes any border that may have been installed.
   */
  private void uninstallBorder(JRootPane root) {
    LookAndFeel.uninstallBorder(root);
  }

  /**
   * Installs the necessary Listeners on the parent <code>Window</code>, if
   * there is one.
   * <p>
   * This takes the parent so that cleanup can be done from <code>removeNotify</code>,
   * at which point the parent hasn't been reset yet.
   * 
   * @param parent The parent of the JRootPane
   */
  private void installWindowListeners(JRootPane root, Component parent) {
    if (parent instanceof java.awt.Window) {
      window = (java.awt.Window)parent;
    } else {
      window = SwingUtilities.getWindowAncestor(parent);
    }
    if (window != null) {

      if (mouseInputListener == null) {
        mouseInputListener = createWindowMouseInputListener(root);
      }

      if (windowListener == null) {
        windowListener = createWindowListener();
      }
      
      window.addMouseListener(mouseInputListener);
      window.addMouseMotionListener(mouseInputListener);
      window.addWindowListener(windowListener);
    }
  }

  /**
   * Uninstalls the necessary Listeners on the <code>Window</code> the
   * Listeners were last installed on.
   */
  private void uninstallWindowListeners(JRootPane root) {
    if (window != null) {

      window.removeMouseListener(mouseInputListener);
      window.removeMouseMotionListener(mouseInputListener);
      window.removeWindowListener(windowListener);
    }
  }

  /**
   * Installs the appropriate LayoutManager on the <code>JRootPane</code> to
   * render the window decorations.
   */
  private void installLayout(JRootPane root) {
    if (layoutManager == null) {
      layoutManager = createLayoutManager();
    }
    savedOldLayout = root.getLayout();
    root.setLayout(layoutManager);
  }

  /**
   * Uninstalls the previously installed <code>LayoutManager</code>.
   */
  private void uninstallLayout(JRootPane root) {
    if (savedOldLayout != null) {
      root.setLayout(savedOldLayout);
      savedOldLayout = null;
    }
  }

  /**
   * Installs the necessary state onto the JRootPane to render client
   * decorations. This is ONLY invoked if the <code>JRootPane</code> has a
   * decoration style other than <code>JRootPane.NONE</code>.
   */
  private void installClientDecorations(JRootPane root) {
    installBorder(root);

    JComponent titlePane = createTitlePane(root);

    setTitlePane(root, titlePane);
    installWindowListeners(root, root.getParent());
    installLayout(root);
    adjustIconAndBackground();
    if (window != null) {
      root.revalidate();
      root.repaint();

    }
  }

  /**
   * @param window
   */
  private void adjustIconAndBackground() {
    if (window != null) {

      title.setFrame(window);

      // the titlePane may not have been updated yet as it was created with a
      // empty FrameWindow so update its actions.
      titlePane.enableActions();
      
      String[] colors = skin.getColors();
      boolean cont = true;
      for (int i = 0; i < colors.length && cont; i++) {
        String string = colors[i];
        if (string.equalsIgnoreCase("desktop")) {
          cont = false;
          try {
            Color background = Color.decode(colors[i + 1]);
            window.setBackground(background);
          } catch (NumberFormatException e) {
          }
        }
      }
      java.awt.Window target = title.getMainFrame();
      if (target instanceof JFrame) {
        JFrame current = (JFrame)target;
        Icon provided = titlePane.getWindow().getFrameIcon();
        Icon general = UIManager.getIcon("InternalFrame.icon");
        Icon toWork = provided;
        if (provided == null)
          toWork = general;
        if (toWork != null) {
          BufferedImage theIcon =
            new BufferedImage(
              toWork.getIconWidth(),
              toWork.getIconHeight(),
              BufferedImage.TYPE_INT_ARGB);
          Graphics graph = theIcon.getGraphics();
          toWork.paintIcon(null, graph, 0, 0);
          current.setIconImage(theIcon);
        }
      }
    }

  }

  private void resetIconAndBackground() {
    if (window != null) {

      window.setBackground(null);
      title.setFrame(null);
    }

  }

  /**
   * Uninstalls any state that <code>installClientDecorations</code> has
   * installed.
   * <p>
   * NOTE: This may be called if you haven't installed client decorations yet
   * (ie before <code>installClientDecorations</code> has been invoked).
   */
  private void uninstallClientDecorations(JRootPane root) {
    uninstallBorder(root);
    uninstallWindowListeners(root);
    setTitlePane(root, null);
    uninstallLayout(root);
    resetIconAndBackground();
    // We have to revalidate/repaint root if the style is JRootPane.NONE
    // only. When we needs to call revalidate/repaint with other styles
    // the installClientDecorations is always called after this method
    // imediatly and it will cause the revalidate/repaint at the proper
    // time.
    int style = getWindowDecorationStyle(root);
    if (style == JRootPane_NONE) {
      root.repaint();
      root.revalidate();
    }
    // Reset the cursor, as we may have changed it to a resize cursor
    if (window != null) {
      window.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    window = null;
  }

  /**
   * Returns the <code>JComponent</code> to render the window decoration
   * style.
   */
  private JComponent createTitlePane(JRootPane root) {
    JComponent titlePane =
      new SkinTitlePane(title = new Window.FrameWindow()) {
        // overriden to set the popup to not be lightweight. it
    // resulted in the popupmenu being hidden, not correctly
    // z-ordered. Using a JWindow does the trick
  protected JMenu createSystemMenu() {
        JMenu menu = new JMenu("    ");
        menu.getPopupMenu().setLightWeightPopupEnabled(false);
        return menu;
      }
    };
    titlePane.setOpaque(false);
    return titlePane;
  }

  /**
   * Returns a <code>MouseListener</code> that will be added to the <code>Window</code>
   * containing the <code>JRootPane</code>.
   */
  private MouseInputListener createWindowMouseInputListener(JRootPane root) {
    return new MouseInputHandler();
  }

  private WindowListener createWindowListener() {
    return new WindowAdapter() {      
      public void windowActivated(WindowEvent e) {
        try {
          title.setSelected(true);
          titlePane.repaint();
        } catch (PropertyVetoException ex) {}
      }      
      public void windowDeactivated(WindowEvent e) {
        try {
          title.setSelected(false);
          titlePane.repaint();
        } catch (PropertyVetoException ex) {}
      }
    };
  }
  
  /**
   * Returns a <code>LayoutManager</code> that will be set on the <code>JRootPane</code>.
   */
  private LayoutManager createLayoutManager() {
    return new MetalRootLayout();
  }

  /**
   * Sets the window title pane -- the JComponent used to provide a plaf a way
   * to override the native operating system's window title pane with one whose
   * look and feel are controlled by the plaf. The plaf creates and sets this
   * value; the default is null, implying a native operating system window
   * title pane.
   * 
   * @param content the <code>JComponent</code> to use for the window title
   *          pane.
   */
  private void setTitlePane(JRootPane root, JComponent titlePane) {
    JLayeredPane layeredPane = root.getLayeredPane();
    JComponent oldTitlePane = getTitlePane();

    if (oldTitlePane != null) {
      oldTitlePane.setVisible(false);
      layeredPane.remove(oldTitlePane);
    }
    if (titlePane != null) {
      layeredPane.add(titlePane, JLayeredPane.FRAME_CONTENT_LAYER);
      titlePane.setVisible(true);
    }
    this.titlePane = (SkinTitlePane)titlePane;
  }

  /**
   * Returns the <code>JComponent</code> rendering the title pane. If this
   * returns null, it implies there is no need to render window decorations.
   * 
   * @return the current window title pane, or null
   * @see #setTitlePane
   */
  private JComponent getTitlePane() {
    return titlePane;
  }

  private Window.FrameWindow getFrameWindow() {
    return title;
  }
  private java.awt.Window getMainWindow() {
    return window;
  }

  /**
   * Returns the <code>JRootPane</code> we're providing the look and feel
   * for.
   */
  private JRootPane getRootPane() {
    return root;
  }

  /**
   * Invoked when a property changes. <code>MetalRootPaneUI</code> is
   * primarily interested in events originating from the <code>JRootPane</code>
   * it has been installed on identifying the property <code>windowDecorationStyle</code>.
   * If the <code>windowDecorationStyle</code> has changed to a value other
   * than <code>JRootPane.NONE</code>, this will add a <code>Component</code>
   * to the <code>JRootPane</code> to render the window decorations, as well
   * as installing a <code>Border</code> on the <code>JRootPane</code>. On
   * the other hand, if the <code>windowDecorationStyle</code> has changed to
   * <code>JRootPane.NONE</code>, this will remove the <code>Component</code>
   * that has been added to the <code>JRootPane</code> as well resetting the
   * Border to what it was before <code>installUI</code> was invoked.
   * 
   * @param e A PropertyChangeEvent object describing the event source and the
   *          property that has changed.
   */
  public void propertyChange(PropertyChangeEvent e) {
    super.propertyChange(e);

    String propertyName = e.getPropertyName();
    if (propertyName == null) {
      return;
    }

    if (propertyName.equals("windowDecorationStyle")) {
      JRootPane root = (JRootPane)e.getSource();
      int style = getWindowDecorationStyle(root);

      // This is potentially more than needs to be done,
      // but it rarely happens and makes the install/uninstall process
      // simpler. MetalTitlePane also assumes it will be recreated if
      // the decoration style changes.
      uninstallClientDecorations(root);
      if (style != JRootPane_NONE) {
        installClientDecorations(root);
      }
    } else if (propertyName.equals("ancestor")) {
      uninstallWindowListeners(root);
      if (getWindowDecorationStyle((JRootPane)e.getSource())
        != JRootPane_NONE) {
        installWindowListeners(root, root.getParent());
      }
    }
    return;
  }

  public static void adjust(
    Rectangle bounds,
    Dimension min,
    int deltaX,
    int deltaY,
    int deltaWidth,
    int deltaHeight) {
    bounds.x += deltaX;
    bounds.y += deltaY;
    bounds.width += deltaWidth;
    bounds.height += deltaHeight;
    if (min != null) {
      if (bounds.width < min.width) {
        int correction = min.width - bounds.width;
        if (deltaX != 0) {
          bounds.x -= correction;
        }
        bounds.width = min.width;
      }
      if (bounds.height < min.height) {
        int correction = min.height - bounds.height;
        if (deltaY != 0) {
          bounds.y -= correction;
        }
        bounds.height = min.height;
      }
    }
  }

  /**
   * Returns the corner that contains the point <code>x</code>,<code>y</code>,
   * or -1 if the position doesn't match a corner.
   */
  public static int calculateCorner(Component c, int x, int y) {
    int xPosition = calculatePosition(x, c.getWidth());
    int yPosition = calculatePosition(y, c.getHeight());

    if (xPosition == -1 || yPosition == -1) {
      return -1;
    }
    return yPosition * 5 + xPosition;
  }

  /**
   * Returns the Cursor to render for the specified corner. This returns 0 if
   * the corner doesn't map to a valid Cursor
   */
  public static int getCursor(int corner) {
    if (corner == -1) {
      return 0;
    }
    return SkinRootPaneUI.cursorMapping[corner];
  }

  /**
   * Returns an integer indicating the position of <code>spot</code> in
   * <code>width</code>. The return value will be: 0 if
   * < BORDER_DRAG_THICKNESS 1 if < CORNER_DRAG_WIDTH 2 if >=
   * CORNER_DRAG_WIDTH &&< width - BORDER_DRAG_THICKNESS 3 if >= width -
   * CORNER_DRAG_WIDTH 4 if >= width - BORDER_DRAG_THICKNESS 5 otherwise
   */
  public static int calculatePosition(int spot, int width) {
    if (spot < SkinRootPaneUI.BORDER_DRAG_THICKNESS) {
      return 0;
    }
    if (spot < SkinRootPaneUI.CORNER_DRAG_WIDTH) {
      return 1;
    }
    if (spot >= (width - SkinRootPaneUI.BORDER_DRAG_THICKNESS)) {
      return 4;
    }
    if (spot >= (width - SkinRootPaneUI.CORNER_DRAG_WIDTH)) {
      return 3;
    }
    return 2;
  }

  /**
   * A custom layout manager that is responsible for the layout of layeredPane,
   * glassPane, menuBar and titlePane, if one has been installed.
   */
  // NOTE: Ideally this would extends JRootPane.RootLayout, but that
  //       would force this to be non-static.
  private static class MetalRootLayout implements LayoutManager2 {
    /**
     * Returns the amount of space the layout would like to have.
     * 
     * @param parent the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's preferred size
     */
    public Dimension preferredLayoutSize(Container parent) {
      Dimension cpd, mbd, tpd;
      int cpWidth = 0;
      int cpHeight = 0;
      int mbWidth = 0;
      int mbHeight = 0;
      int tpWidth = 0;
      int tpHeight = 0;
      Insets i = parent.getInsets();
      JRootPane root = (JRootPane)parent;

      if (root.getContentPane() != null) {
        cpd = root.getContentPane().getPreferredSize();
      } else {
        cpd = root.getSize();
      }
      if (cpd != null) {
        cpWidth = cpd.width;
        cpHeight = cpd.height;
      }

      if (root.getJMenuBar() != null) {
        mbd = root.getJMenuBar().getPreferredSize();
        if (mbd != null) {
          mbWidth = mbd.width;
          mbHeight = mbd.height;
        }
      }

      if (getWindowDecorationStyle(root) != JRootPane_NONE
        && (root.getUI() instanceof SkinRootPaneUI)) {
        JComponent titlePane = ((SkinRootPaneUI)root.getUI()).getTitlePane();
        if (titlePane != null) {
          tpd = titlePane.getPreferredSize();
          if (tpd != null) {
            tpWidth = tpd.width;
            tpHeight = tpd.height;
          }
        }
      }

      return new Dimension(
        Math.max(Math.max(cpWidth, mbWidth), tpWidth) + i.left + i.right,
        cpHeight + mbHeight + tpHeight + i.top + i.bottom);
    }

    /**
     * Returns the minimum amount of space the layout needs.
     * 
     * @param parent the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's minimum size
     */
    public Dimension minimumLayoutSize(Container parent) {
      Dimension cpd, mbd, tpd;
      int cpWidth = 0;
      int cpHeight = 0;
      int mbWidth = 0;
      int mbHeight = 0;
      int tpWidth = 0;
      int tpHeight = 0;
      Insets i = parent.getInsets();
      JRootPane root = (JRootPane)parent;

      // This code is enabled only if the parent is not a window with
      // look and feel decorations. Otherwise the look and feel honors
      // the minimum size and the window can not be resized to a small
      // size in the case where a component in the component hierarchy
      // returns a wrong/big minimum size. In such case, the minimum
      // size will be the one of the title pane.
      if (!(root.getParent() instanceof java.awt.Window
        && getWindowDecorationStyle(root) != JRootPane_NONE)) {
        if (root.getContentPane() != null) {
          cpd = root.getContentPane().getMinimumSize();
        } else {
          cpd = root.getSize();
        }
        if (cpd != null) {
          cpWidth = cpd.width;
          cpHeight = cpd.height;
        }
      }

      if (root.getJMenuBar() != null) {
        mbd = root.getJMenuBar().getMinimumSize();
        if (mbd != null) {
          mbWidth = mbd.width;
          mbHeight = mbd.height;
        }
      }
      if (getWindowDecorationStyle(root) != JRootPane_NONE
        && (root.getUI() instanceof SkinRootPaneUI)) {
        JComponent titlePane = ((SkinRootPaneUI)root.getUI()).getTitlePane();
        if (titlePane != null) {
          tpd = titlePane.getMinimumSize();
          if (tpd != null) {
            tpWidth = tpd.width;
            tpHeight = tpd.height;
          }
        }
      }

      // FYI, MetalRootLayout has a bug in the dimension as it uses
      // tpWidth in the height calculation
      // Now logged as
      // http://developer.java.sun.com/developer/bugParade/bugs/4916923.html
      return new Dimension(
        Math.max(Math.max(cpWidth, mbWidth), tpWidth) + i.left + i.right,
        cpHeight + mbHeight + tpHeight + i.top + i.bottom);
    }

    /**
     * Returns the maximum amount of space the layout can use.
     * 
     * @param target the Container for which this layout manager is being used
     * @return a Dimension object containing the layout's maximum size
     */
    public Dimension maximumLayoutSize(Container target) {
      Dimension cpd, mbd, tpd;
      int cpWidth = Integer.MAX_VALUE;
      int cpHeight = Integer.MAX_VALUE;
      int mbWidth = Integer.MAX_VALUE;
      int mbHeight = Integer.MAX_VALUE;
      int tpWidth = Integer.MAX_VALUE;
      int tpHeight = Integer.MAX_VALUE;
      Insets i = target.getInsets();
      JRootPane root = (JRootPane)target;

      if (root.getContentPane() != null) {
        cpd = root.getContentPane().getMaximumSize();
        if (cpd != null) {
          cpWidth = cpd.width;
          cpHeight = cpd.height;
        }
      }

      if (root.getJMenuBar() != null) {
        mbd = root.getJMenuBar().getMaximumSize();
        if (mbd != null) {
          mbWidth = mbd.width;
          mbHeight = mbd.height;
        }
      }

      if (getWindowDecorationStyle(root) != JRootPane_NONE
        && (root.getUI() instanceof SkinRootPaneUI)) {
        JComponent titlePane = ((SkinRootPaneUI)root.getUI()).getTitlePane();
        if (titlePane != null) {
          tpd = titlePane.getMaximumSize();
          if (tpd != null) {
            tpWidth = tpd.width;
            tpHeight = tpd.height;
          }
        }
      }

      int maxHeight = Math.max(Math.max(cpHeight, mbHeight), tpHeight);
      // Only overflows if 3 real non-MAX_VALUE heights, sum to > MAX_VALUE
      // Only will happen if sums to more than 2 billion units. Not likely.
      if (maxHeight != Integer.MAX_VALUE) {
        maxHeight = cpHeight + mbHeight + tpHeight + i.top + i.bottom;
      }

      int maxWidth = Math.max(Math.max(cpWidth, mbWidth), tpWidth);
      // Similar overflow comment as above
      if (maxWidth != Integer.MAX_VALUE) {
        maxWidth += i.left + i.right;
      }

      return new Dimension(maxWidth, maxHeight);
    }

    /**
     * Instructs the layout manager to perform the layout for the specified
     * container.
     * 
     * @param parent the Container for which this layout manager is being used
     */
    public void layoutContainer(Container parent) {
      JRootPane root = (JRootPane)parent;
      Rectangle b = root.getBounds();
      Insets i = root.getInsets();
      int nextY = 0;
      int w = b.width - i.right - i.left;
      int h = b.height - i.top - i.bottom;

      if (root.getLayeredPane() != null) {
        root.getLayeredPane().setBounds(i.left, i.top, w, h);
      }
      if (root.getGlassPane() != null) {
        root.getGlassPane().setBounds(i.left, i.top, w, h);
      }
      // Note: This is laying out the children in the layeredPane,
      // technically, these are not our children.
      if (getWindowDecorationStyle(root) != JRootPane_NONE
        && (root.getUI() instanceof SkinRootPaneUI)) {
        JComponent titlePane = ((SkinRootPaneUI)root.getUI()).getTitlePane();
        if (titlePane != null) {
          Dimension tpd = titlePane.getPreferredSize();
          if (tpd != null) {
            int tpHeight = tpd.height;
            titlePane.setBounds(0, 0, w, tpHeight);
            nextY += tpHeight;
          }
        }
      }
      if (root.getJMenuBar() != null) {
        Dimension mbd = root.getJMenuBar().getPreferredSize();
        root.getJMenuBar().setBounds(0, nextY, w, mbd.height);
        nextY += mbd.height;
      }
      if (root.getContentPane() != null) {
        Dimension cpd = root.getContentPane().getPreferredSize();
        root.getContentPane().setBounds(0, nextY, w, h < nextY ? 0 : h - nextY);
      }
    }

    public void addLayoutComponent(String name, Component comp) {
    }
    public void removeLayoutComponent(Component comp) {
    }
    public void addLayoutComponent(Component comp, Object constraints) {
    }
    public float getLayoutAlignmentX(Container target) {
      return 0.0f;
    }
    public float getLayoutAlignmentY(Container target) {
      return 0.0f;
    }
    public void invalidateLayout(Container target) {
    }
  }

  /**
   * Maps from positions to cursor type. Refer to calculateCorner and
   * calculatePosition for details of this.
   */
  private static final int[] cursorMapping =
    new int[] {
      Cursor.NW_RESIZE_CURSOR,
      Cursor.NW_RESIZE_CURSOR,
      Cursor.N_RESIZE_CURSOR,
      Cursor.NE_RESIZE_CURSOR,
      Cursor.NE_RESIZE_CURSOR,
      Cursor.NW_RESIZE_CURSOR,
      0,
      0,
      0,
      Cursor.NE_RESIZE_CURSOR,
      Cursor.W_RESIZE_CURSOR,
      0,
      0,
      0,
      Cursor.E_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR,
      0,
      0,
      0,
      Cursor.SE_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR,
      Cursor.SW_RESIZE_CURSOR,
      Cursor.S_RESIZE_CURSOR,
      Cursor.SE_RESIZE_CURSOR,
      Cursor.SE_RESIZE_CURSOR };

  /**
   * MouseInputHandler is responsible for handling resize/moving of the Window.
   * It sets the cursor directly on the Window when then mouse moves over a hot
   * spot.
   */
  private class MouseInputHandler implements MouseInputListener {
    /**
     * Set to true if the drag operation is moving the window.
     */
    private boolean isMovingWindow;

    /**
     * Used to determine the corner the resize is occuring from.
     */
    private int dragCursor;

    /**
     * X location the mouse went down on for a drag operation.
     */
    private int dragOffsetX;

    /**
     * Y location the mouse went down on for a drag operation.
     */
    private int dragOffsetY;

    /**
     * Width of the window when the drag started.
     */
    private int dragWidth;

    /**
     * Height of the window when the drag started.
     */
    private int dragHeight;

    public void mousePressed(MouseEvent ev) {
      if (getWindowDecorationStyle(root) == JRootPane_NONE) {
        return;
      }
      Point dragWindowOffset = ev.getPoint();
      java.awt.Window w = translateSource(ev);
      if (w != null) {
        w.toFront();
      }
      Point convertedDragWindowOffset =
        SwingUtilities.convertPoint(w, dragWindowOffset, getTitlePane());

      if (getTitlePane() != null
        && getTitlePane().contains(convertedDragWindowOffset)) {
        if (!getFrameWindow().isMaximum()
          && dragWindowOffset.y >= BORDER_DRAG_THICKNESS
          && dragWindowOffset.x >= BORDER_DRAG_THICKNESS
          && dragWindowOffset.x < w.getWidth() - BORDER_DRAG_THICKNESS) {
          isMovingWindow = true;
          dragOffsetX = dragWindowOffset.x;
          dragOffsetY = dragWindowOffset.y;
        }
      }
      //this was an else if before but the title panel would not
      //cause a resize
      if (getFrameWindow().isResizable()
        && !getFrameWindow().isShaded()
        && !getFrameWindow().isMaximum()) {
        //&& ((frameState & Frame_MAXIMIZED_BOTH) == 0)
        //|| (d != null && d.isResizable())) {
        dragOffsetX = dragWindowOffset.x;
        dragOffsetY = dragWindowOffset.y;
        dragWidth = w.getWidth();
        dragHeight = w.getHeight();
        //System.out.println("Calulation cursor");
        dragCursor =
          SkinRootPaneUI.getCursor(
            SkinRootPaneUI.calculateCorner(
              w,
              dragWindowOffset.x,
              dragWindowOffset.y));
      }
    }

    public void mouseReleased(MouseEvent ev) {
      if (dragCursor != 0 && window != null && !window.isValid()) {
        // Some Window systems validate as you resize, others won't,
        // thus the check for validity before repainting.
        window.validate();
        getRootPane().repaint();
      }
      isMovingWindow = false;
      dragCursor = 0;
    }

    public void mouseMoved(MouseEvent ev) {
      JRootPane root = getRootPane();

      if (getWindowDecorationStyle(root) == JRootPane_NONE) {
        return;
      }

      java.awt.Window w = translateSource(ev);

      // Update the cursor
      int cursor =
        SkinRootPaneUI.getCursor(
          SkinRootPaneUI.calculateCorner(w, ev.getX(), ev.getY()));

      if (cursor != 0
        && getFrameWindow().isResizable()
        && !getFrameWindow().isShaded()
        && !getFrameWindow().isMaximum()) {
        w.setCursor(Cursor.getPredefinedCursor(cursor));
      } else {
        w.setCursor(lastCursor);
      }
    }

    public void mouseDragged(MouseEvent ev) {
      java.awt.Window w = translateSource(ev);
      Point pt = ev.getPoint();
      //			System.out.println("MovingWindow:"+isMovingWindow+"
      // dragcursor:"+dragCursor);
      if (isMovingWindow) {
        Point windowPt = w.getLocationOnScreen();

        windowPt.x += pt.x - dragOffsetX;
        windowPt.y += pt.y - dragOffsetY;
        w.setLocation(windowPt);
      } else if (dragCursor != 0) {
        Rectangle r = w.getBounds();
        Rectangle startBounds = new Rectangle(r);
        Dimension min = w.getMinimumSize();

        switch (dragCursor) {
          case Cursor.E_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              0,
              0,
              pt.x + (dragWidth - dragOffsetX) - r.width,
              0);
            break;
          case Cursor.S_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              0,
              0,
              0,
              pt.y + (dragHeight - dragOffsetY) - r.height);
            break;
          case Cursor.N_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              0,
              pt.y - dragOffsetY,
              0,
              - (pt.y - dragOffsetY));
            break;
          case Cursor.W_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              pt.x - dragOffsetX,
              0,
              - (pt.x - dragOffsetX),
              0);
            break;
          case Cursor.NE_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              0,
              pt.y - dragOffsetY,
              pt.x + (dragWidth - dragOffsetX) - r.width,
              - (pt.y - dragOffsetY));
            break;
          case Cursor.SE_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              0,
              0,
              pt.x + (dragWidth - dragOffsetX) - r.width,
              pt.y + (dragHeight - dragOffsetY) - r.height);
            break;
          case Cursor.NW_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              pt.x - dragOffsetX,
              pt.y - dragOffsetY,
              - (pt.x - dragOffsetX),
              - (pt.y - dragOffsetY));
            break;
          case Cursor.SW_RESIZE_CURSOR :
            SkinRootPaneUI.adjust(
              r,
              min,
              pt.x - dragOffsetX,
              0,
              - (pt.x - dragOffsetX),
              pt.y + (dragHeight - dragOffsetY) - r.height);
            break;
          default :
            break;
        }
        if (!r.equals(startBounds)) {
          if (getFrameWindow().isResizable()) {
            w.setBounds(r);
            // Defer repaint/validate on mouseReleased unless dynamic
            // layout is active.
            if (Boolean
              .TRUE
              .equals(
                AccessUtils.invoke(
                  Toolkit.getDefaultToolkit(),
                  "isDynamicLayoutActive",
                  null,
                  null))) {
              w.validate();
              getRootPane().repaint();

            }
            getFrameWindow().dispatchEvent(
              new ComponentEvent(
                getMainWindow(),
                ComponentEvent.COMPONENT_RESIZED));
          }
        }
      }
    }

    public void mouseEntered(MouseEvent ev) {
      java.awt.Window w = translateSource(ev);
      lastCursor = w.getCursor();
      mouseMoved(ev);
    }

    public void mouseExited(MouseEvent ev) {
      java.awt.Window w = translateSource(ev);
      w.setCursor(lastCursor);
    }

    public void mouseClicked(MouseEvent ev) {
      java.awt.Window w = translateSource(ev);
      Frame f;

      if (w instanceof Frame) {
        f = (Frame)w;
      } else {
        return;
      }

      Point convertedPoint =
        SwingUtilities.convertPoint(w, ev.getPoint(), getTitlePane());

      int state = getExtendedState(f);
      if (getTitlePane() != null && getTitlePane().contains(convertedPoint)) {
        if ((ev.getClickCount() % 2) == 0
          && ((ev.getModifiers() & InputEvent.BUTTON1_MASK) != 0)) {
          if (f.isResizable()) {
            if ((state & Frame_MAXIMIZED_BOTH) != 0) {
              setExtendedState(f, state & ~Frame_MAXIMIZED_BOTH);
            } else {
              setExtendedState(f, state | Frame_MAXIMIZED_BOTH);
            }
            return;
          }
        }
      }
    }

  }
}
