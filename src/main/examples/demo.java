/*
 * ====================================================================
 * 
 * @PROJECT.FULLNAME@ @VERSION@ License.
 * 
 * Copyright (c) @YEAR@ L2FProd.com. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The end-user documentation
 * included with the redistribution, if any, must include the following
 * acknowlegement: "This product includes software developed by L2FProd.com
 * (http://www.L2FProd.com/)." Alternately, this acknowlegement may appear in
 * the software itself, if and wherever such third-party acknowlegements
 * normally appear. 4. The names "@PROJECT.FULLNAME@", "SkinLF" and
 * "L2FProd.com" must not be used to endorse or promote products derived from
 * this software without prior written permission. For written permission,
 * please contact info@L2FProd.com. 5. Products derived from this software may
 * not be called "SkinLF" nor may "SkinLF" appear in their names without prior
 * written permission of L2FProd.com.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * L2FPROD.COM OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
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
    if (OS.isOneDotFour()) {
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
