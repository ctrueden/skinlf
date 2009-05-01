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
package examples;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class VerticalLayout implements LayoutManager {

  int gap = 0;

  /**
   * Constructor for the VerticalLayout object
   */
  public VerticalLayout() {
  }

  /**
   * Constructor for the VerticalLayout object
   *
   * @param gap  Description of Parameter
   */
  public VerticalLayout(int gap) {
    this.gap = gap;
  }

  /**
   * Adds a feature to the LayoutComponent attribute of the VerticalLayout
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
   * @param parent  Description of Parameter
   */
  public void layoutContainer(Container parent) {
    Insets insets = parent.getInsets();
    Dimension size = parent.getSize();
    int width = size.width - insets.left - insets.right;
    int height = insets.top;

    for (int i = 0, c = parent.getComponentCount(); i < c; i++) {
      Component m = parent.getComponent(i);
      if (m.isVisible()) {
        m.setBounds(insets.left, height, width, m.getPreferredSize().height);
        height += m.getSize().height + gap;
      }
    }
  }

  /**
   * Description of the Method
   *
   * @param parent  Description of Parameter
   * @return        Description of the Returned Value
   */
  public Dimension minimumLayoutSize(Container parent) {
    return preferredLayoutSize(parent);
  }

  /**
   * Description of the Method
   *
   * @param parent  Description of Parameter
   * @return        Description of the Returned Value
   */
  public Dimension preferredLayoutSize(Container parent) {
    Insets insets = parent.getInsets();
    Dimension pref = new Dimension(0, 0);

    for (int i = 0, c = parent.getComponentCount(); i < c; i++) {
      Component m = parent.getComponent(i);
      if (m.isVisible()) {
        pref.height += parent.getComponent(i).getPreferredSize().height + gap;
        pref.width = Math.max(pref.width, parent.getComponent(i).getPreferredSize().width);
      }
    }

    pref.height += insets.top + insets.bottom;

    return pref;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   */
  public void removeLayoutComponent(Component c) {
  }

}
