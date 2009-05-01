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

import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import com.l2fprod.util.OS;
import com.l2fprod.util.WindowUtils;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

/**
 * Skin Look And Feel Demo. <BR>Provides information on how to use Skin Look
 * And Feel
 * 
 * @author fred @created 27 avril 2002
 */
public class demo extends JApplet {

  /**
   * Constructor for the demo object
   */
  public demo() {
  }

  /**
   * Description of the Method
   * 
   * @param themes Description of Parameter
   */
  public void createUI(String[] themes) {
    getContentPane().setLayout(new BorderLayout(3, 3));
    getContentPane().add("Center", new demoPanel(themes));
  }

  /**
   * main method.
   * 
   * @param args the list of theme packs available for this demo
   * @exception Exception Description of Exception
   */
  public static void main(String[] args) throws Exception {
    if (args.length > 0) {
      String themepack = args[0];
      if (themepack.endsWith(".xml")) {
        SkinLookAndFeel.setSkin(
          SkinLookAndFeel.loadThemePackDefinition(new File(args[0]).toURL()));
        UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
      } else if (themepack.startsWith("class:")) {
        String classname = themepack.substring("class:".length());
        SkinLookAndFeel.setSkin((Skin)Class.forName(classname).newInstance());
        UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
      } else if (themepack.startsWith("theme:")) {
        String classname = themepack.substring("theme:".length());
        MetalTheme theme = (MetalTheme)Class.forName(classname).newInstance();
        MetalLookAndFeel metal = new MetalLookAndFeel();
        MetalLookAndFeel.setCurrentTheme(theme);
        UIManager.setLookAndFeel(metal);
      } else {
        SkinLookAndFeel.setSkin(SkinLookAndFeel.loadThemePack(args[0]));
        UIManager.setLookAndFeel("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
      }
    }

    demo d = new demo();
    d.createUI(args);

    // if we are running with JDK1.4 decorates the frames and dialogs
    if (OS.isOneDotFourOrMore()) {
      java.lang.reflect.Method method =
        JFrame.class.getMethod(
          "setDefaultLookAndFeelDecorated",
          new Class[] { boolean.class });
      method.invoke(null, new Object[] { Boolean.TRUE });

      method =
        JDialog.class.getMethod(
          "setDefaultLookAndFeelDecorated",
          new Class[] { boolean.class });
      method.invoke(null, new Object[] { Boolean.TRUE });
    }

    Image frameIcon =
      new ImageIcon(demo.class.getResource("windowicon.gif")).getImage();
    // so option pane as same icon as us
    JOptionPane.getRootFrame().setIconImage(frameIcon);

    JFrame f = new JFrame("Skin Look And Feel " + SkinLookAndFeel.version());
    f.setIconImage(frameIcon);
    f.getContentPane().setLayout(new BorderLayout());
    f.getContentPane().add("Center", d);

    f.pack();

    WindowUtils.centerOnScreen(f);

    f.setVisible(true);

    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent event) {
        System.exit(0);
      }
    });
  }

}
