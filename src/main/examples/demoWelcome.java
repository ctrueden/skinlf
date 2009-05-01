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

import java.io.File;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import com.l2fprod.gui.plaf.skin.*;

/**
 * demoWelcome.<BR>
 * Shows the Welcome screen and the combobox to select a skin.
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class demoWelcome extends JPanel {

  JComboBox m_Themes;

  /**
   * Constructor for the demoWelcome object
   *
   * @param themes  Description of Parameter
   */
  public demoWelcome(String[] themes) {
    setBorder(new EmptyBorder(10, 10, 10, 10));

    setLayout(new BorderLayout(10, 10));

    JLabel label = new JLabel(new ImageIcon(demoWelcome.class.getResource("skinlfdemo.gif")));
    label.setOpaque(true);
    label.setBackground(Color.black);
    label.setBorder(new LineBorder(Color.black));
    add("West", label);

    JPanel right = new JPanel(new BorderLayout(3, 3));
    add("Center", right);

    JPanel toolbar = new JPanel(new FlowLayout());
    JButton hello = new JButton("Hello");
    hello.setToolTipText("<html><b>Hello!</b><br><i>Bonjour !</i><br><u>Ciao !</u>");
    hello.setMnemonic('i');
    toolbar.add(hello);
    toolbar.add(new JSeparator(JSeparator.VERTICAL));
    toolbar.add(new JButton("World!"));
    add("North", toolbar);

    JPanel p = new JPanel();
    p.setLayout(new VerticalLayout(3));

    JTextArea area = new JTextArea();
    area.setWrapStyleWord(true);
    area.setLineWrap(true);
    area.setBorder(null);
    area.setEditable(false);
    area.setOpaque(false);
    area.setText("Welcome to Skin Look And Feel " + SkinLookAndFeel.version() + "\n" +
        "Use the combobox above to test the various themepacks available.\n");
    area.setBackground(getBackground());
    p.add(area);
    p.add(new JSeparator());
    p.add(new JLabel("Available Themepacks:"));
    p.add(m_Themes = new JComboBox(themes));

    m_Themes.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          setTheme((String) m_Themes.getSelectedItem());
        }
      });

    //    setTheme(themes[0]);
    right.add("North", p);
    right.add("East", new JSeparator(JSeparator.VERTICAL));
    //    right.add("Center", new VuMeter(15));
  }

  /**
   * Sets the Theme attribute of the demoWelcome object
   *
   * @param selectedItem  The new Theme value
   */
  void setTheme(String selectedItem) {
    try {
      if (selectedItem.endsWith(".xml")) {
        SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePackDefinition(SkinUtils.toURL(new File(selectedItem))));
      }
      else {
        SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePack(selectedItem));
      }
      UIManager.setLookAndFeel(new SkinLookAndFeel());

      Component c = SwingUtilities.getAncestorOfClass(java.awt.Window.class, this);
      if (c == null) {
        c = SwingUtilities.getAncestorOfClass(demo.class, this);
      }

      if (c != null) {
        SwingUtilities.updateComponentTreeUI(c);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // end of try-catch
  }

  /**
   * Description of the Class
   *
   * @author    fred
   * @created   27 avril 2002
   */
  static class VuMeter extends JPanel {
    double[] bars;
    int[] barDirections;

    //    CellRendererPane cellRenderer = new CellRendererPane();

    /**
     * Constructor for the VuMeter object
     *
     * @param barCount  Description of Parameter
     */
    public VuMeter(int barCount) {
      setPreferredSize(new Dimension(100, 100));
      setOpaque(true);
      setBackground(Color.white);
      setDoubleBuffered(false);
      bars = new double[barCount];
      barDirections = new int[barCount];
      Arrays.fill(barDirections, 1);
      Thread runner =
        new Thread() {
          public void run() {
            while (true) {
              try {
                if (VuMeter.this.isVisible()) {
                  VuMeter.this.repaint();
                }
                Thread.sleep(500);
              } catch (Exception e) {
              }
            }
          }
        };
      runner.start();
      runner.setPriority(Thread.MIN_PRIORITY);

      //      add(cellRenderer);
    }

    /**
     * Description of the Method
     *
     * @param g  Description of Parameter
     */
    protected void paintComponent(Graphics g) {
      g.setColor(getParent().getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());

      int w = getWidth() / bars.length;
      int barHeight;

      for (int i = 0, c = bars.length; i < c; i++) {
        double speed = Math.random() * 0.3;
        bars[i] += (speed * barDirections[i]);
        if (bars[i] >= 1) {
          bars[i] = 1;
          barDirections[i] = -1;
        }
        else if (bars[i] <= 0) {
          bars[i] = 0;
          barDirections[i] = 1;
        }
        barHeight = (int) (getHeight() * bars[i]);
        paintBar(g,
            new Rectangle(i * w, getHeight() - barHeight, w - 2, barHeight),
            new Rectangle(i * w, 0, w - 2, getHeight()),
            bars[i],
            barDirections[i]);
      }
    }

    /**
     * Description of the Method
     *
     * @param g             Description of Parameter
     * @param barRect       Description of Parameter
     * @param fullRect      Description of Parameter
     * @param barValue      Description of Parameter
     * @param barDirection  Description of Parameter
     */
    protected void paintBar(Graphics g, Rectangle barRect, Rectangle fullRect, double barValue, int barDirection) {
      //renderer.setValue((int)(barValue * 100));
      //cellRenderer.paintComponent(g, renderer, this, fullRect.x, fullRect.y, fullRect.width, fullRect.height);
      g.setColor(getParent().getBackground().brighter());
      g.fillRect(fullRect.x, fullRect.y, fullRect.width, fullRect.height);
      g.setColor(getParent().getBackground().darker());
      g.drawRect(fullRect.x, fullRect.y, fullRect.width, fullRect.height);
      g.fillRect(barRect.x, barRect.y, barRect.width, barRect.height);
    }

    //    JProgressBar renderer = new JProgressBar(JProgressBar.VERTICAL);
  }

}
