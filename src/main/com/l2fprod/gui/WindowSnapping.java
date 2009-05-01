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

import java.awt.Window;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;
import javax.swing.SwingConstants;

/**
 * WindowSnapping. <br>
 * Created on 15/06/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:45 $
 */
public class WindowSnapping {

  static SnapListener sharedSnap = new SnapListener();

  /**
   * Description of the Method
   *
   * @param snap      Description of Parameter
   * @param position  Description of Parameter
   */
  public static void snap(Window snap, int position) {
    // snap to active window
    sharedSnap.snaps.addElement(new Snap(snap, position));
  }

  /**
   * Description of the Method
   *
   * @param snap      Description of Parameter
   * @param position  Description of Parameter
   * @param target    Description of Parameter
   */
  public static void snap(Window snap, int position, Window target) {
    // snap to target
    SnapListener l = new SnapListener();
    l.snaps.addElement(new Snap(snap, position));
    target.addWindowListener(l);
  }

  /**
   * Description of the Method
   *
   * @param target  Description of Parameter
   */
  public static void registerSnapping(Window target) {
    if (target != null) {
      target.addWindowListener(sharedSnap);
    }
  }

  /**
   * Description of the Method
   *
   * @param target  Description of Parameter
   */
  public static void unregisterSnapping(Window target) {
    if (target != null) {
      target.removeWindowListener(sharedSnap);
    }
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private static class SnapListener extends WindowAdapter implements ComponentListener {
    Vector snaps = new Vector();
    Window lastActivate = null;
    boolean ignoreEvents = false;

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void windowActivated(WindowEvent e) {
      if (ignoreEvents) {
        // ok the next event will be processed
        ignoreEvents = false;
        return;
      }

      if (lastActivate != null) {
        lastActivate.removeComponentListener(this);
      }
      attachSnapTo(e.getWindow());
      lastActivate = e.getWindow();
      lastActivate.addComponentListener(this);
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void componentHidden(ComponentEvent e) {
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void componentMoved(ComponentEvent e) {
      attachSnapTo(lastActivate);
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void componentShown(ComponentEvent e) {
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void componentResized(ComponentEvent e) {
    }

    /**
     * Description of the Method
     *
     * @param target  Description of Parameter
     */
    public void attachSnapTo(Window target) {
      synchronized (snaps) {
        // notify the listener that it should not handle the next windowActivateEvent
        ignoreEvents = true;
        for (int i = 0, c = snaps.size(); i < c; i++) {
          ((Snap) snaps.elementAt(i)).attachTo(target);
        }
        // restore focus to target
        target.requestFocus();
        target.toFront();
      }
    }
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  private static class Snap {
    Window snap;
    int position;

    /**
     * Constructor for the Snap object
     *
     * @param snap      Description of Parameter
     * @param position  Description of Parameter
     */
    public Snap(Window snap, int position) {
      this.snap = snap;
      this.position = position;
    }

    /**
     * Description of the Method
     *
     * @param target  Description of Parameter
     */
    public void attachTo(Window target) {
      // i'm a snap window, i should follow my target
      Rectangle targetBounds = target.getBounds();
      int x;
      int y;
      switch (position) {
        default:
        case SwingConstants.NORTH_WEST:
          // top left corner
          x = targetBounds.x;
          y = targetBounds.y - snap.getSize().height;
          break;
        case SwingConstants.NORTH_EAST:
          // top right corner
          x = targetBounds.x + targetBounds.width - snap.getSize().width;
          y = targetBounds.y - snap.getSize().height;
          break;
        case SwingConstants.SOUTH_WEST:
          // bottom left corner
          x = targetBounds.x;
          y = targetBounds.y + targetBounds.height;
          break;
        case SwingConstants.SOUTH_EAST:
          x = targetBounds.x + targetBounds.width - snap.getSize().width;
          y = targetBounds.y + targetBounds.height;
          break;
        case SwingConstants.WEST:
          x = targetBounds.x - snap.getSize().width;
          y = targetBounds.y;
          break;
        case SwingConstants.EAST:
          x = targetBounds.x + targetBounds.width;
          y = targetBounds.y;
          break;
      }
      snap.setLocation(x, y);
      //	    snap.toFront();
    }
  }

}

