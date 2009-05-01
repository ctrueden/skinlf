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

import com.l2fprod.util.WindowUtils;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.Timer;
import javax.swing.UIManager;

/**
 * SkinPreview Window. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:24:43 $
 */
public class SkinPreviewWindow extends JFrame {

  /**
   * Constructor for the SkinPreviewWindow object
   */
  public SkinPreviewWindow() {
    super();

    JTabbedPane tabs = new JTabbedPane();
    getContentPane().add("Center", tabs);
    tabs.addTab("Buttons", new ButtonTest());
    tabs.addTab("CheckBox", new CheckBoxTest());
    tabs.addTab("Radio", new RadioTest());
    tabs.addTab("Progress", new ProgressTest());
    tabs.addTab("Desktop", new InternalTest());
    tabs.addTab("Scrollbars", new ScrollTest());
    tabs.addTab("List", new ListTest());
    tabs.addTab("Table", new TableTest());

    JMenuBar menubar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menu.add(new JCheckBoxMenuItem("Check Box ?"));
    JMenu submenu = new JMenu("Sub menu");
    submenu.add(new JMenuItem("Hi!"));
    menu.add(submenu);
    menu.add(new JMenuItem("Exit"));
    menubar.add(menu);

    setJMenuBar(menubar);

    WindowUtils.sizeTo(this, 0.5d, 0.5d);
    WindowUtils.centerOnScreen(this);

    addWindowListener(
      new WindowAdapter() {
        public void windowClosing(WindowEvent event) {
          SkinPreviewWindow.this.dispose();
        }
      });
  }

  /**
   * Runs the skin preview demo.
   *
   * @param args           The command line arguments
   * @exception Exception  Description of Exception
   */
  public static void main(String[] args) throws Exception {

    Skin skin = null;
    if (args.length > 1) {
      skin = new CompoundSkin(SkinLookAndFeel.loadSkin(args[0]),
          SkinLookAndFeel.loadSkin(args[1]));
    }
    else {
      skin = SkinLookAndFeel.loadSkin(args[0]);
    }

    SkinLookAndFeel.setSkin(skin);

    UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");

  }

  /**
   * Demonstrates buttons.
   */
  static class ButtonTest extends JPanel {
    /**
     * Constructor for the ButtonTest object
     */
    ButtonTest() {
      setLayout(new BorderLayout());
      add("North", new JButton("North"));
      add("East", new JButton("East"));
      add("South", new JButton("South"));
      add("West", new JButton("West"));
      JButton b = new JButton("Center");
      b.setEnabled(false);
      add("Center", b);
    }
  }

  /**
   * Demonstrates check boxes.
   */
  static class CheckBoxTest extends JPanel {
    /**
     * Constructor for the CheckBoxTest object
     */
    CheckBoxTest() {
      setLayout(new BorderLayout());
      Box p = new Box(BoxLayout.Y_AXIS);
      p.add(new JCheckBox("Hello ???"));
      p.add(Box.createVerticalStrut(3));
      p.add(new JComboBox(new String[]{"1", "2", "4", "8"}));
      p.add(Box.createVerticalStrut(3));
      JComboBox editable = new JComboBox(new String[]{"1", "2", "4", "8"});
      editable.setEditable(true);
      p.add(editable);

      p.add(Box.createVerticalStrut(3));
      JPanel toggles = new JPanel();
      toggles.setLayout(new FlowLayout());
      ButtonGroup group = new ButtonGroup();
      JToggleButton button;
      button = new JToggleButton("Hello 1");
      toggles.add(button);
      group.add(button);
      button = new JToggleButton("Hello 2");
      toggles.add(button);
      group.add(button);
      button = new JToggleButton("Hello 3");
      toggles.add(button);
      group.add(button);
      p.add(toggles);

      add("North", p);
    }
  }

  /**
   * Demonstrates radio buttons.
   */
  static class RadioTest extends JPanel {
    /**
     * Constructor for the RadioTest object
     */
    RadioTest() {
      ButtonGroup group = new ButtonGroup();
      setLayout(new GridLayout(3, 1));
      JRadioButton button;
      button = new JRadioButton("Hello 1");
      add(button);
      group.add(button);
      button = new JRadioButton("Hello 2");
      add(button);
      group.add(button);
      button = new JRadioButton("Hello 3");
      add(button);
      group.add(button);
    }
  }

  /**
   * Demonstrates slider controlls and progress bars.
   */
  static class ProgressTest extends JPanel {
    /**
     * Constructor for the ProgressTest object
     */
    ProgressTest() {
      setLayout(new BorderLayout());
      final BoundedRangeModel model = new DefaultBoundedRangeModel(0, 0, 0, 100);

      JProgressBar progress = new JProgressBar(model);
      add("South", progress);

      JSlider slider = new JSlider(model);
      add("North", slider);

      slider = new JSlider(JSlider.VERTICAL);
      slider.setModel(model);
      add("West", slider);

      progress = new JProgressBar(JProgressBar.VERTICAL);
      progress.setModel(model);
      add("East", progress);

      new Timer(50, new ActionListener() {
        int counter = 0;

        public void actionPerformed(ActionEvent e) {
          if (model.getValue() >= 100) {
            counter += 20;
            if (counter >= 500) model.setValue(0);
          } else {
            model.setValue(model.getValue() + 2);
            counter = 0;
          }
        }
      }).start();
    }
  }

  /**
   * Tests internal frames.
   */
  static class InternalTest extends JPanel {
    /**
     * Constructor for the InternalTest object
     */
    InternalTest() {
      setLayout(new BorderLayout());
      JDesktopPane desk = new JDesktopPane();
      add("Center", new JScrollPane(desk));
      desk.putClientProperty("JDesktopPane.backgroundEnabled", Boolean.TRUE);

      JInternalFrame frame = new JInternalFrame("A Frame", true, true, true, true);
      frame.getContentPane().add(new JButton("Ola"));
      frame.setVisible(true);
      frame.setSize(200, 100);
      desk.add(frame);

      frame = new JInternalFrame("An other Frame", true, true, true, true);
      frame.getContentPane().add(new JButton("Hello"));
      frame.setMaximizable(false);
      frame.setVisible(true);
      frame.setSize(200, 200);
      frame.setLocation(50, 50);
      desk.add(frame);
    }
  }

  /**
   * Tests scrollbars.
   */
  static class ScrollTest extends JPanel {
    /**
     * Constructor for the ScrollTest object
     */
    ScrollTest() {
      setLayout(new BorderLayout());
      add("Center", new JScrollPane(new JTree()));
    }
  }

  /**
   * Tests lists.
   */
  static class ListTest extends JPanel {
    /**
     * Constructor for the ListTest object
     */
    ListTest() {
      setLayout(new BorderLayout());
      String[] values = new String[50];
      for (int i = 0, c = values.length; i < c; i++) {
        values[i] = "Item " + i;
      }
      add("Center", new JScrollPane(new JList(values)));
    }
  }

  /**
   * Tests tables.
   */
  static class TableTest extends JPanel {
    /**
     * Constructor for the TableTest object
     */
    TableTest() {
      setLayout(new BorderLayout());
      String[] columns = new String[4];
      String[][] rowData = new String[10][4];
      for (int i = 0, c = rowData[0].length; i < c; i++) {
        columns[i] = "Column" + i;
        for (int j = 0, d = rowData.length; j < d; j++) {
          rowData[j][i] = "Cell(" + i + ", " + j + ")";
        }
      }

      add("Center", new JScrollPane(new JTable(rowData, columns)));
    }
  }

}

