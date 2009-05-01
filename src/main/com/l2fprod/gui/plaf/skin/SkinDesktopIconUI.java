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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicDesktopIconUI;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinDesktopIconUI extends BasicDesktopIconUI {
  /**
   * Description of the Field
   */
  protected PropertyChangeListener propertyChangeListener;

  JLabel iconPane;
  MouseInputListener mouseInputListener;

  /**
   * Gets the MinimumSize attribute of the SkinDesktopIconUI object
   *
   * @param c  Description of Parameter
   * @return   The MinimumSize value
   */
  public Dimension getMinimumSize(JComponent c) {
    return iconPane.getMinimumSize();
  }

  /**
   * Gets the MaximumSize attribute of the SkinDesktopIconUI object
   *
   * @param c  Description of Parameter
   * @return   The MaximumSize value
   */
  public Dimension getMaximumSize(JComponent c) {
    return iconPane.getMaximumSize();
  }

  /**
   * Gets the PreferredSize attribute of the SkinDesktopIconUI object
   *
   * @param c  Description of Parameter
   * @return   The PreferredSize value
   */
  public Dimension getPreferredSize(JComponent c) {
    return iconPane.getPreferredSize();
  }

  /**
   * Description of the Method
   */
  protected void installComponents() {
    frame = desktopIcon.getInternalFrame();
    desktopIcon.setBorder(null);
    iconPane = new JLabel(frame.getTitle(),
        frame.getFrameIcon(),
        JLabel.CENTER);
    iconPane.setHorizontalTextPosition(JLabel.CENTER);
    iconPane.setVerticalTextPosition(JLabel.BOTTOM);
    iconPane.setHorizontalAlignment(JLabel.CENTER);

    desktopIcon.setLayout(new BorderLayout());
    desktopIcon.add(iconPane, BorderLayout.CENTER);
    desktopIcon.setOpaque(true);
  }

  /**
   * Description of the Method
   */
  protected void installListeners() {
    mouseInputListener = createMouseInputListener();
    iconPane.addMouseMotionListener(mouseInputListener);
    iconPane.addMouseListener(mouseInputListener);
    if (propertyChangeListener == null) {
      propertyChangeListener = createPropertyChangeListener();
    }
    desktopIcon.getInternalFrame().addPropertyChangeListener(propertyChangeListener);
  }

  /**
   * Description of the Method
   */
  protected void uninstallListeners() {
    super.uninstallListeners();
    desktopIcon.getInternalFrame().removePropertyChangeListener(propertyChangeListener);
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
   */
  protected void uninstallComponents() {
    desktopIcon.setLayout(null);
    desktopIcon.remove(iconPane);
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinDesktopIconUI();
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class PropertyChangeHandler implements PropertyChangeListener {
    /**
     * Description of the Method
     *
     * @param evt  Description of Parameter
     */
    public void propertyChange(PropertyChangeEvent evt) {
      String prop = evt.getPropertyName();
      if (JInternalFrame.TITLE_PROPERTY.equals(prop)) {
        // PENDING(fred): we need to trim the title or define a maximum size
        // or display it on multiple lines ?
        iconPane.setText((String) evt.getNewValue());
      }
      else if (JInternalFrame.FRAME_ICON_PROPERTY.equals(prop)) {
        iconPane.setIcon((Icon) evt.getNewValue());
      }
    }
  }

}

