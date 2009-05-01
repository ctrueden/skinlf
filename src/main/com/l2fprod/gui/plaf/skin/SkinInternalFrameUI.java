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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicInternalFrameUI;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinInternalFrameUI extends BasicInternalFrameUI {

  private Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Constructor for the SkinInternalFrameUI object
   *
   * @param b  Description of Parameter
   */
  public SkinInternalFrameUI(JInternalFrame b) {
    super(b);
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   */
  public void installUI(JComponent c) {
    super.installUI(c);
    skin.getFrame().installSkin(c);
  }

  /**
   * Description of the Method
   *
   * @param w  Description of Parameter
   * @return   Description of the Returned Value
   */
  protected JComponent createNorthPane(JInternalFrame w) {
    titlePane = new SkinTitlePane(w);
    return titlePane;
  }

  /**
   * Description of the Method
   *
   * @param w  Description of Parameter
   * @return   Description of the Returned Value
   */
  protected MouseInputAdapter createBorderListener(JInternalFrame w) {
    return new MyBorderListener();
  }

  /**
   * Description of the Method
   *
   * @param b  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent b) {
    return new SkinInternalFrameUI((JInternalFrame) b);
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  protected class MyBorderListener extends BorderListener {

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void mouseMoved(MouseEvent e) {
      JComponent pane = getNorthPane();
      if (e.getSource()==frame &&
          pane instanceof SkinTitlePane &&
          ((SkinTitlePane) pane).getWindow().isShaded()) {
        return;
      }
      super.mouseMoved(e);
    }

    /**
     * Description of the Method
     *
     * @param e  Description of Parameter
     */
    public void mouseDragged(MouseEvent e) {
      try {
        // do not resize the frame if it is shaded
        JComponent pane = getNorthPane();
        if (e.getSource()==frame &&
            pane instanceof SkinTitlePane &&
            ((SkinTitlePane) pane).getWindow().isShaded()) {
          return;
        }
        super.mouseDragged(e);
      } catch (NullPointerException ex) {
        // the nullpointerexception may be thrown by bug id 4383371
        // if parentBounds is not set
        // sending the frame back and front should fix the problem as an Ancestor propertychangeevent
        // will be sent
        // this bug has been fixed in JDK1.3
        // http://developer.java.sun.com/developer/bugParade/bugs/4383371.html
        ((JInternalFrame) e.getSource()).toBack();
        ((JInternalFrame) e.getSource()).toFront();
      }
    }
  }

  protected LayoutManager createLayoutManager(){
    return new MyInternalFrameLayout();
  }

  protected class MyInternalFrameLayout extends InternalFrameLayout {
    public Dimension minimumLayoutSize(Container c) {
      // The minimum size of the internal frame only takes into account the
      // title pane since you are allowed to resize the frames to the point
      // where just the title pane is visible.
      Dimension result = new Dimension();
      if (getNorthPane() != null &&
          getNorthPane() instanceof SkinTitlePane) {
        result = new Dimension(getNorthPane().getMinimumSize());
      }
      Insets i = frame.getInsets();
      result.width += i.left + i.right;
      result.height += i.top + i.bottom;
      
      return result;
    }
  }

}
