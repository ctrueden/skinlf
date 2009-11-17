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

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Created on 27/05/2000 by Frederic Lavigne, fred@L2FProd.com
 * 
 * @author $Author: l2fprod $ @created 27 avril 2002
 * @version $Revision: 1.12 $, $Date: 2009-11-17 18:53:47 $
 */
public interface Window {

  /**
   * Description of the Field
   */
  public final static String IS_SHADED_PROPERTY = "shaded";

  public final static String SHADE_BOUNDS_PROPERTY = "windowshadeBounds";

  /**
   * Gets the Container attribute of the Window object
   * 
   * @return The Container value
   */
  Container getContainer();

  /**
   * Adds a feature to the PropertyChangeListener attribute of the
   * Window object
   * 
   * @param listener The feature to be added to the
   *        PropertyChangeListener attribute
   */
  void addPropertyChangeListener(PropertyChangeListener listener);

  /**
   * Remove a feature to the PropertyChangeListener attribute of the
   * Window object
   * 
   * @param listener The feature to be remove from the
   *        PropertyChangeListener attribute
   */
  void removePropertyChangeListener(PropertyChangeListener listener);

  /**
   * Gets the Selected attribute of the Window object
   * 
   * @return The Selected value
   */
  boolean isSelected();

  /**
   * Sets the Selected attribute of the Window object
   * 
   * @param b The new Selected value
   * @exception PropertyVetoException Description of Exception
   */
  void setSelected(boolean b) throws PropertyVetoException;

  /**
   * Gets the Icon attribute of the Window object
   * 
   * @return The Icon value
   */
  boolean isIcon();

  /**
   * Sets the Icon attribute of the Window object
   * 
   * @param b The new Icon value
   * @exception PropertyVetoException Description of Exception
   */
  void setIcon(boolean b) throws PropertyVetoException;

  /**
   * Gets the Maximum attribute of the Window object
   * 
   * @return The Maximum value
   */
  boolean isMaximum();

  /**
   * Sets the Maximum attribute of the Window object
   * 
   * @param b The new Maximum value
   * @exception PropertyVetoException Description of Exception
   */
  void setMaximum(boolean b) throws PropertyVetoException;

  /**
   * Gets the Shaded attribute of the Window object
   * 
   * @return The Shaded value
   */
  boolean isShaded();

  /**
   * Sets the Shaded attribute of the Window object
   * 
   * @param b The new Shaded value
   */
  void setShaded(boolean b);

  /**
   * Gets the Maximizable attribute of the Window object
   * 
   * @return The Maximizable value
   */
  boolean isMaximizable();

  /**
   * Gets the Iconifiable attribute of the Window object
   * 
   * @return The Iconifiable value
   */
  boolean isIconifiable();

  /**
   * Gets the Closable attribute of the Window object
   * 
   * @return The Closable value
   */
  boolean isClosable();

  /**
   * Sets the Closed attribute of the Window object
   * 
   * @param b The new Closed value
   * @exception PropertyVetoException Description of Exception
   */
  void setClosed(boolean b) throws PropertyVetoException;

  /**
   * Gets the Resizable attribute of the Window object
   * 
   * @return The Resizable value
   */
  boolean isResizable();

  /**
   * Gets the Title attribute of the Window object
   * 
   * @return The Title value
   */
  String getTitle();

  /**
   * Gets the FrameIcon attribute of the Window object
   * 
   * @return The FrameIcon value
   */
  Icon getFrameIcon();

  /**
   * Description of the Method
   * 
   * @param event Description of Parameter
   */
  void dispatchEvent(AWTEvent event);

  /**
   * Description of the Class
   * 
   * @author fred @created 27 avril 2002
   */
  public static class InternalFrameWindow implements Window {

    JInternalFrame frame;

    boolean shaded = false;

    /**
     * Constructor for the InternalFrameWindow object
     * 
     * @param frame Description of Parameter
     */
    public InternalFrameWindow(JInternalFrame frame) {
      this.frame = frame;
    }

    /**
     * Sets the Selected attribute of the InternalFrameWindow object
     * 
     * @param b The new Selected value
     * @exception PropertyVetoException Description of Exception
     */
    public void setSelected(boolean b) throws PropertyVetoException {
      frame.setSelected(b);
    }

    /**
     * Sets the Icon attribute of the InternalFrameWindow object
     * 
     * @param b The new Icon value
     * @exception PropertyVetoException Description of Exception
     */
    public void setIcon(boolean b) throws PropertyVetoException {
      frame.setIcon(b);
    }

    /**
     * Sets the Maximum attribute of the InternalFrameWindow object
     * 
     * @param b The new Maximum value
     * @exception PropertyVetoException Description of Exception
     */
    public void setMaximum(boolean b) throws PropertyVetoException {
      frame.setMaximum(b);
    }

    /**
     * Sets the Shaded attribute of the InternalFrameWindow object
     * 
     * @param b The new Shaded value
     */
    public void setShaded(boolean b) {
      if (b == shaded) { return; }

      if (b == true) {
        Rectangle bounds = frame.getBounds();
        Rectangle p = new Rectangle(bounds.x, bounds.y, bounds.width,
            bounds.height);
        frame.putClientProperty(SHADE_BOUNDS_PROPERTY, p);
        frame.setBounds(p.x, p.y, p.width, frame.getMinimumSize().height - 2);
      } else {
        Point location = frame.getLocation();
        Rectangle p = (Rectangle)frame.getClientProperty(SHADE_BOUNDS_PROPERTY);
        frame.getDesktopPane().getDesktopManager().setBoundsForFrame(frame,
            location.x, location.y, p.width, p.height);
        frame.putClientProperty(SHADE_BOUNDS_PROPERTY, null);
      }
      shaded = b;
    }

    /**
     * Sets the Closed attribute of the InternalFrameWindow object
     * 
     * @param b The new Closed value
     * @exception PropertyVetoException Description of Exception
     */
    public void setClosed(boolean b) throws PropertyVetoException {
      if (OS.isOneDotThreeOrMore()) {
        try {
          Class.forName("javax.swing.JInternalFrame").getMethod(
              "doDefaultCloseAction", new Class[0]).invoke(frame, null);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        if (b == false) {
          frame.setClosed(b);
          return;
        }
        doDefaultCloseAction();
      }
    }

    /**
     * Gets the Container attribute of the InternalFrameWindow object
     * 
     * @return The Container value
     */
    public Container getContainer() {
      return frame;
    }

    /**
     * Gets the Selected attribute of the InternalFrameWindow object
     * 
     * @return The Selected value
     */
    public boolean isSelected() {
      return frame.isSelected();
    }

    /**
     * Gets the Icon attribute of the InternalFrameWindow object
     * 
     * @return The Icon value
     */
    public boolean isIcon() {
      return frame.isIcon();
    }

    /**
     * Gets the Maximum attribute of the InternalFrameWindow object
     * 
     * @return The Maximum value
     */
    public boolean isMaximum() {
      return frame.isMaximum();
    }

    /**
     * Gets the Shaded attribute of the InternalFrameWindow object
     * 
     * @return The Shaded value
     */
    public boolean isShaded() {
      return shaded;
    }

    /**
     * Gets the Maximizable attribute of the InternalFrameWindow
     * object
     * 
     * @return The Maximizable value
     */
    public boolean isMaximizable() {
      return frame.isMaximizable();
    }

    /**
     * Gets the Iconifiable attribute of the InternalFrameWindow
     * object
     * 
     * @return The Iconifiable value
     */
    public boolean isIconifiable() {
      return frame.isIconifiable();
    }

    /**
     * Gets the Closable attribute of the InternalFrameWindow object
     * 
     * @return The Closable value
     */
    public boolean isClosable() {
      return frame.isClosable();
    }

    /**
     * Gets the Resizable attribute of the InternalFrameWindow object
     * 
     * @return The Resizable value
     */
    public boolean isResizable() {
      return isShaded() == false && frame.isResizable();
    }

    /**
     * Gets the Title attribute of the InternalFrameWindow object
     * 
     * @return The Title value
     */
    public String getTitle() {
      return frame.getTitle();
    }

    /**
     * Gets the FrameIcon attribute of the InternalFrameWindow object
     * 
     * @return The FrameIcon value
     */
    public Icon getFrameIcon() {
      return frame.getFrameIcon();
    }

    /**
     * Adds a feature to the PropertyChangeListener attribute of the
     * InternalFrameWindow object
     * 
     * @param listener The feature to be added to the
     *        PropertyChangeListener attribute
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
      frame.addPropertyChangeListener(listener);
    }

    /**
     * Adds a feature to the PropertyChangeListener attribute of the
     * InternalFrameWindow object
     * 
     * @param listener The feature to be added to the
     *        PropertyChangeListener attribute
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
      frame.removePropertyChangeListener(listener);
    }

    /**
     * Description of the Method
     * 
     * @param event Description of Parameter
     */
    public void dispatchEvent(AWTEvent event) {
      frame.dispatchEvent(event);
    }

    // workaround for JDK1.2
    // seems to work as expected in most case
    /**
     * Description of the Method
     */
    private void doDefaultCloseAction() {
      int defaultCloseOperation = frame.getDefaultCloseOperation();
      frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      switch (defaultCloseOperation) {
      case WindowConstants.HIDE_ON_CLOSE:
        try {
          frame.setClosed(true);
          frame.setVisible(false);
          if (frame.isSelected()) {
            frame.setSelected(false);
          }
        } catch (PropertyVetoException pve) {}
        break;
      case WindowConstants.DISPOSE_ON_CLOSE:
        try {
          frame.setClosed(true);
          frame.dispose();
          // only executes if close wasn't vetoed.
        } catch (PropertyVetoException pve) {}
        break;
      case 3:
        // EXIT_ON_CLOSE:
        System.exit(0);
        break;
      case WindowConstants.DO_NOTHING_ON_CLOSE:
        try {
          frame.setClosed(true);
        } catch (PropertyVetoException pve) {}
      default:
        break;
      }
      frame.setDefaultCloseOperation(defaultCloseOperation);
    }

    public String toString() {
      return super.toString() + "[title=" + getTitle() + "]";
    }
  }

  static class FrameWindow implements Window {

    private JFrame frame = null;

    private JDialog dialog = null;

    private Rectangle oldBounds = null;

    private boolean shaded = false;

    private boolean selected = true;
    
    private Image cachedFrameImage = null;
    
    private Icon cachedFrameIcon = null;
    
    /**
     * Constructor for the SkinWindowWindow object
     */
    public FrameWindow() {
      frame = null;
    }

    public void setFrame(java.awt.Window argWin) {
      if (argWin instanceof JDialog) {
        dialog = (JDialog)argWin;
      } else if (argWin instanceof JFrame) {
        frame = (JFrame)argWin;
      } else {
        frame = null;
        dialog = null;
      }
      oldBounds = null;
      shaded = false;
    }

    /**
     * Sets the Selected attribute of the SkinWindowWindow object
     * 
     * @param b The new Selected value
     */
    public void setSelected(boolean b) {
      if (b) {
        if (frame != null) {
          frame.repaint();
        } else if (dialog != null) {
          dialog.repaint();
        }
      }
      this.selected = b;
    }

    public java.awt.Window getMainFrame() {
      java.awt.Window toreturn = null;
      if (frame != null)
        toreturn = frame;
      else if (dialog != null) toreturn = dialog;
      return toreturn;
    }

    /**
     * Sets the Icon attribute of the SkinWindowWindow object
     * 
     * @param b The new Icon value
     */
    public void setIcon(boolean b) {
      if (frame != null) {
        frame.setState(Frame.ICONIFIED);
        //frame.show();
      } else if (dialog != null) {
        // not support by dialog
      }
    }

    /**
     * Sets the Maximum attribute of the SkinWindowWindow object
     * 
     * @param b The new Maximum value
     */
    public void setMaximum(boolean b) {
      if (frame != null) {
        if (b && SkinRootPaneUI.getExtendedState(frame) != SkinRootPaneUI.Frame_MAXIMIZED_BOTH) {
          SkinRootPaneUI.setExtendedState(frame,
              SkinRootPaneUI.Frame_MAXIMIZED_BOTH);
        } else if (!b) {
            SkinRootPaneUI.setExtendedState(frame,
                    Frame.NORMAL);
        }
        dispatchEvent(new ComponentEvent(frame,
            ComponentEvent.COMPONENT_RESIZED));
      } else if (dialog != null) {
        // not available for JDialog
      }
    }

    /**
     * Sets the Shaded attribute of the SkinWindowWindow object
     * 
     * @param b The new Shaded value
     */
    public void setShaded(boolean b) {
      java.awt.Window window = (frame == null)?(java.awt.Window)dialog
          :(java.awt.Window)frame;

      if (window == null || b == shaded) { return; }

      if (b == true) {
        Rectangle bounds = window.getBounds();
        oldBounds = new Rectangle(bounds.x, bounds.y, bounds.width,
            bounds.height);
        window.setBounds(oldBounds.x, oldBounds.y, oldBounds.width, window
            .getMinimumSize().height - 2);
      } else {
        Point location = window.getLocation();
        window.setBounds(location.x, location.y, oldBounds.width,
            oldBounds.height);
        oldBounds = null;
      }
      shaded = b;
    }

    /**
     * Sets the Closed attribute of the SkinWindowWindow object
     * 
     * @param b The new Closed value
     */
    public void setClosed(boolean b) {
      if (frame != null)
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
      else if (dialog != null)
          dialog.dispatchEvent(new WindowEvent(dialog,
              WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Gets the Container attribute of the SkinWindowWindow object
     * 
     * @return The Container value
     */
    public Container getContainer() {
      if (frame != null)
        return frame.getContentPane();
      else if (dialog != null) return dialog.getContentPane();
      return null;
    }

    /**
     * Gets the Selected attribute of the SkinWindowWindow object
     * 
     * @return The Selected value
     */
    public boolean isSelected() {
      return selected;
      /** With JDK1.4 use:
      boolean toreturn = true;
      if (frame != null)
        toreturn = frame.isActive();
      else if (dialog != null) toreturn = dialog.isActive();
      return toreturn;
      **/
    }

    /**
     * Gets the Icon attribute of the SkinWindowWindow object
     * 
     * @return The Icon value
     */
    public boolean isIcon() {
      boolean toreturn = false;
      if (frame != null) toreturn = frame.getState() == Frame.ICONIFIED;
      return toreturn;
    }

    /**
     * Gets the Maximum attribute of the SkinWindowWindow object
     * 
     * @return The Maximum value
     */
    public boolean isMaximum() {
      boolean toreturn = false;
      if (frame != null)
          toreturn = SkinRootPaneUI.getExtendedState(frame) == SkinRootPaneUI.Frame_MAXIMIZED_BOTH;
      return toreturn;
    }

    /**
     * Gets the Maximizable attribute of the SkinWindowWindow object
     * 
     * @return The Maximizable value
     */
    public boolean isMaximizable() {
      boolean toreturn = false;
      if (frame != null) toreturn = frame.isResizable() && !isShaded();
      return toreturn;
    }

    /**
     * Gets the Shaded attribute of the SkinWindowWindow object
     * 
     * @return The Shaded value
     */
    public boolean isShaded() {
      return shaded;
    }

    /**
     * Gets the Iconifiable attribute of the SkinWindowWindow object
     * 
     * @return The Iconifiable value
     */
    public boolean isIconifiable() {
      boolean toreturn = false;
      if (frame != null) toreturn = frame.isResizable();
      return toreturn;
    }

    /**
     * Gets the Closable attribute of the SkinWindowWindow object
     * 
     * @return The Closable value
     */
    public boolean isClosable() {
      return true;
    }

    /**
     * Gets the Resizable attribute of the SkinWindowWindow object
     * 
     * @return The Resizable value
     */
    public boolean isResizable() {
      boolean toreturn = false;
      if (frame != null) {
        toreturn = frame.isResizable();
      } else if (dialog != null) { return dialog.isResizable() && !isShaded(); }
      return toreturn;
    }

    /**
     * Gets the Title attribute of the SkinWindowWindow object
     * 
     * @return The Title value
     */
    public String getTitle() {
      String title = "";
      if (frame != null)
        title = frame.getTitle();
      else if (dialog != null) title = dialog.getTitle();
      return title;
    }

    /**
     * Gets the FrameIcon attribute of the SkinWindowWindow object
     * 
     * @return The FrameIcon value
     */
    public Icon getFrameIcon() {
      Icon toreturn = null;
      Image frameImage = null;

      if (frame != null) {
        frameImage = frame.getIconImage();
      } else if (dialog != null) {
        // JDialog takes it from the parent frame
        Frame parent = (Frame)SwingUtilities.getAncestorOfClass(Frame.class,
            dialog);
        if (parent != null) {
          frameImage = parent.getIconImage();
        }
      }

      if (frameImage != null) {
        if (frameImage==cachedFrameImage) {
          return cachedFrameIcon;
        }
        toreturn = new ImageIcon(frameImage);
        cachedFrameImage = frameImage;
        cachedFrameIcon = toreturn;
      }

      return toreturn;

    }

    /**
     * Adds a feature to the PropertyChangeListener attribute of the
     * SkinWindowWindow object
     * 
     * @param listener The feature to be added to the
     *        PropertyChangeListener attribute
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
      if (frame != null)
        frame.addPropertyChangeListener(listener);
      else if (dialog != null) dialog.addPropertyChangeListener(listener);
    }

    /**
     * Removes a feature to the PropertyChangeListener attribute of
     * the SkinWindowWindow object
     * 
     * @param listener The feature to be added to the
     *        PropertyChangeListener attribute
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
      if (frame != null)
        frame.removePropertyChangeListener(listener);
      else if (dialog != null) dialog.removePropertyChangeListener(listener);
    }

    /**
     * Description of the Method
     * 
     * @param event Description of Parameter
     */
    public void dispatchEvent(AWTEvent event) {
      if (frame != null) {
        frame.dispatchEvent(event);
      } else if (dialog != null) {
        dialog.dispatchEvent(event);
      }
    }

    public String toString() {
      return super.toString() + "[title=" + getTitle() + "]";
    }
  }

}