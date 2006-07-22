/* ====================================================================
 *
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright (c) @YEAR@ L2FProd.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by L2FProd.com
 *        (http://www.L2FProd.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not
 *    be used to endorse or promote products derived from this software
 *    without prior written permission. For written permission, please
 *    contact info@L2FProd.com.
 *
 * 5. Products derived from this software may not be called "SkinLF"
 *    nor may "SkinLF" appear in their names without prior written
 *    permission of L2FProd.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL L2FPROD.COM OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
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
