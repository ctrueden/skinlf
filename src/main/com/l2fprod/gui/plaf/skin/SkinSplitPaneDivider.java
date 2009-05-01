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

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public final class SkinSplitPaneDivider extends BasicSplitPaneDivider implements javax.swing.SwingConstants {

  /**
   * Description of the Field
   */
  protected Skin skin = SkinLookAndFeel.getSkin();
  final static Cursor defaultCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
  final static Insets NO_INSETS = new Insets(0, 0, 0, 0);

  /**
   * Constructor for the SkinSplitPaneDivider object
   *
   * @param ui  Description of Parameter
   */
  public SkinSplitPaneDivider(BasicSplitPaneUI ui) {
    super(ui);
    setLayout(new DividerLayout());
  }

  /**
   * Sets the size of the divider to <code>newSize</code>. That is the
   * width if the splitpane is <code>HORIZONTAL_SPLIT</code>, or the
   * height of <code>VERTICAL_SPLIT</code> .
   *
   * @param newSize  The new DividerSize value
   */
  public void setDividerSize(int newSize) {
    dividerSize = newSize;
    splitPane.setDividerSize(newSize);
  }

  /**
   * Paints the divider.
   *
   * @param g  Description of Parameter
   */
  public void paint(Graphics g) {

    //Paint the border.
    //Border   border = getBorder();
    //if (border != null) {
    Dimension size = getSize();
    //border.paintBorder(this, g, 0, 0, size.width, size.height);
    skin.getSplitPane().paintGutter(g, splitPane, size);

    if (leftButton != null) {
      Rectangle bounds = leftButton.getBounds();
      Graphics glb = g.create(bounds.x, bounds.y, bounds.width, bounds.height);
      leftButton.paint(glb);
    }

    if (rightButton != null) {
      Rectangle bounds = rightButton.getBounds();
      Graphics grb = g.create(bounds.x, bounds.y, bounds.width, bounds.height);
      rightButton.paint(grb);
    }

    skin.getSplitPane().paintThumb(g, splitPane, size);
  }

  /**
   * Creates and return an instance of JButton that can be used to collapse the
   * left component in the split pane.
   *
   * @return   Description of the Returned Value
   */
  protected JButton createLeftOneTouchButton() {
    int button_direction = SOUTH;
    if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
      button_direction = WEST;
    }
    JButton b = new SkinSplitArrowButton(button_direction);
    b.setCursor(defaultCursor);
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    updateDividerSize(b.getPreferredSize());
    return b;
  }


  /**
   * Creates and return an instance of JButton that can be used to collapse the
   * right component in the split pane.
   *
   * @return   Description of the Returned Value
   */
  protected JButton createRightOneTouchButton() {
    int button_direction = NORTH;
    if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
      button_direction = EAST;
    }
    JButton b = new SkinSplitArrowButton(button_direction);
    b.setCursor(defaultCursor);
    b.setFocusPainted(false);
    b.setBorderPainted(false);
    updateDividerSize(b.getPreferredSize());
    return b;
  }

  /**
   * Update the divider size to contain the appropriate dimension.
   *
   * @param d  Description of Parameter
   */
  protected void updateDividerSize(Dimension d) {
    int buttonSize;
    if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
      buttonSize = d.width;
    }
    else {
      buttonSize = d.height;
    }

    int sbSize = splitPane.getDividerSize();
    if (sbSize < buttonSize) {
      splitPane.setDividerSize(buttonSize);
    }
  }


  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  protected class DividerLayout implements LayoutManager {
    /**
     * Description of the Method
     *
     * @param c  Description of Parameter
     */
    public void layoutContainer(Container c) {
      if (leftButton != null && rightButton != null &&
          c == SkinSplitPaneDivider.this) {

        Dimension leftSize = leftButton.getPreferredSize();
        Dimension rightSize = rightButton.getPreferredSize();

        if (splitPane.isOneTouchExpandable()) {
          Insets insets = getInsets();
          if (insets == null) {
            insets = NO_INSETS;
          }
          if (orientation == JSplitPane.VERTICAL_SPLIT) {
            int blockSize = getDividerSize() - (insets.left + insets.right);
            int y = (c.getSize().height - blockSize) / 2;
            leftButton.setBounds(insets.left + leftSize.width, y, leftSize.width, leftSize.height);
            rightButton.setBounds((insets.left * 2) + leftSize.width +
                rightSize.width, y, rightSize.width, rightSize.height);
          }
          else {
            int blockSize = getDividerSize() - (insets.top + insets.bottom);
            int x = (c.getSize().width - blockSize) / 2;
            leftButton.setBounds(x, insets.top + leftSize.height, leftSize.width, leftSize.height);
            rightButton.setBounds(x, (insets.top * 2) + leftSize.height +
                rightSize.height, rightSize.width, rightSize.height);
          }
        }
        else {
          leftButton.setBounds(-5, -5, 1, 1);
          rightButton.setBounds(-5, -5, 1, 1);
        }
      }
    }

    public Dimension minimumLayoutSize(Container c) {
      return new Dimension(0,0);
    }
    
    public Dimension preferredLayoutSize(Container c) {
      return new Dimension(0, 0);
    }
    
    public void removeLayoutComponent(Component c) {}
    
    public void addLayoutComponent(String string, Component c) {}
  }

}
