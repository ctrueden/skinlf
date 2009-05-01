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

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinPopupMenuUI extends BasicPopupMenuUI {

  PopupMenuListener m_PopupListener;
  AncestorListener m_AncestorListener;

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Description of the Method
   */
  public void installDefaults() {
    super.installDefaults();
    skin.getPersonality().installSkin(popupMenu);
    if (Boolean.TRUE.equals(UIManager.get("PopupMenu.animation"))) {
      popupMenu.setOpaque(false);
    }
  }

  /**
   * Description of the Method
   */
  public void installListeners() {
    super.installListeners();
    if (Boolean.TRUE.equals(UIManager.get("PopupMenu.animation"))) {
      popupMenu.addAncestorListener(m_AncestorListener = new SkinPopupAncestorListener());
      popupMenu.addPopupMenuListener(m_PopupListener = new SkinPopupMenuListener());
    }
  }

  /**
   * Description of the Method
   *
   * @param g  Description of Parameter
   * @param c  Description of Parameter
   */
  public void paint(Graphics g, JComponent c) {
    Graphics2D g2d = (Graphics2D) g;
    AlphaComposite alpha = (AlphaComposite) c.getClientProperty("alpha");
    if (alpha != null) {
      g2d.setComposite(alpha);
    }
    skin.getPersonality().paintDialog(g, c);
    super.paint(g, c);
  }

  /**
   * Description of the Method
   */
  protected void uninstallDefaults() {
    super.uninstallDefaults();
    popupMenu.setOpaque(true);
  }

  /**
   * Description of the Method
   */
  protected void uninstallListeners() {
    super.uninstallListeners();
    if (m_AncestorListener != null) {
      popupMenu.removeAncestorListener(m_AncestorListener);
    }
    if (m_PopupListener != null) {
      popupMenu.removePopupMenuListener(m_PopupListener);
    }
    popupMenu.putClientProperty("alpha", null);
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent c) {
    return new SkinPopupMenuUI();
  }

  public JPopupMenu popupMenu() {
    return popupMenu;
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  class SkinPopupAncestorListener implements AncestorListener {
    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void ancestorAdded(AncestorEvent event) {
      Component parent = event.getComponent().getParent();
      if (parent instanceof JComponent) {
        ((JComponent) parent).setDoubleBuffered(false);
        ((JComponent) parent).setOpaque(false);
      }
      // end of if (parent instanceof JComponent)
    }

    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void ancestorMoved(AncestorEvent event) {
    }

    /**
     * Description of the Method
     *
     * @param event  Description of Parameter
     */
    public void ancestorRemoved(AncestorEvent event) {
    }
  }

  /**
   * <p>Responsible for the fade-in effect of menus.</p>
   * <p>I re-wrote it using javax.swing.timer because the old version accessed
   * Swing components from outside of the event thread, which could
   * theoretically lead to data corruption. --William Tracy, 2/20/04</p>
   *
   * @author    fred
   * @created   27 avril 2002
   * @author William Tracy
   */
  class SkinPopupMenuListener implements PopupMenuListener {
    AlphaComposite current;
    Timer timer = new Timer(25, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (current == null || current.getAlpha() >= 0.75) {
                timer.stop();
            } else {
                current = AlphaComposite.getInstance(current.getRule(), current.getAlpha() + 0.05f);
                popupMenu().putClientProperty("alpha", current);
                popupMenu().repaint();
            }
        }
    });

    /**
     * Called if menu is canceled.
     *
     * @param e  Cancelling event
     */
    public void popupMenuCanceled(PopupMenuEvent e) {
      timer.stop();
    }

    /**
     * Called if menu will become invisible.
     *
     * @param e  Event which causes menu to become invisible
     */
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
      timer.stop();
    }

    /**
     * Called if menu will become visible. Sets the starting fade-in color and
     * starts the fade timer.
     *
     * @param e  Event which causes the menu to become visible
     */
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      current = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.0f);
      timer.start();
    }
  }

}
