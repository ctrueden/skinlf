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

import java.io.File;
import java.lang.reflect.Method;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.UIManager;
import javax.swing.LookAndFeel;
import javax.swing.plaf.metal.*;

import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinUtils;
import com.l2fprod.gui.plaf.skin.CompoundSkin;

/**
 * Skinit. <br>
 * Skinit is Skin Look And Feel wrapper. It allows you to start any application
 * with SkinLF. Skinit will prevent the application to set its own Look And
 * Feel. Once Skin Look And Feel is set, it can't be removed. <br>
 *
 * @author    $Author: l2fprod $
 * @version   $Revision: 1.5 $, $Date: 2009-05-01 13:24:43 $
 */
public class Skinit {

  /**
   * The main program for the Skinit class
   *
   * @param args           The command line arguments
   * @exception Exception  Description of Exception
   */
  public static void main(String[] args) throws Exception {

    if (args.length == 0) {
      printUsage();
    }

    int mainClassNameIndex = -1;
    String gtktheme = null;
    String kdetheme = null;
    String packtheme = null;
    String lnfclassname = null;
    String metaltheme = null;

    for (int i = 0, c = args.length; i < c; i++) {
      if (args[i].equals("-gtk")) {
        gtktheme = args[++i];
      }
      else if (args[i].equals("-kde")) {
        kdetheme = args[++i];
      }
      else if (args[i].equals("-pack")) {
        packtheme = args[++i];
      }
      else if (args[i].equals("-lnf")) {
        lnfclassname = args[++i];
      }      
      else if (args[i].equals("-metaltheme")) {
        metaltheme = args[++i];
      }      
      else {
        mainClassNameIndex = i;
        break;
      }
    }

    String[] realArgs = new String[args.length - mainClassNameIndex - 1];
    for (int i = 0, c = realArgs.length; i < c; i++) {
      realArgs[i] = args[mainClassNameIndex + i + 1];
    }

    // First try to find the class
    Class clazz = null;
    try {
      clazz = Class.forName(args[mainClassNameIndex]);       
    } catch (ClassNotFoundException e) {
      System.err.println("The class " + args[mainClassNameIndex] + " was not found in the classpath.");
      System.exit(1);
    } catch (Throwable e) {
      e.printStackTrace();
      System.exit(1);
    }
    
    // if the class exists, get the main method
    Method mainMethod = null;
    try {
      mainMethod = clazz.getMethod("main", new Class[]{String[].class});
    } catch (NoSuchMethodException e) {
      System.err.println("No method public static void main(String[] args) in " + clazz.getName());
      System.exit(1);
    } catch (Throwable e) {
      e.printStackTrace();
      System.exit(1);
    }
    
    // try to make sure the main method is accessible
    try {
      mainMethod.setAccessible(true);
    } catch (Throwable e) {      
    }

    // main class and main method found, time to load the skin
    if (lnfclassname != null) {
      LookAndFeel lnf = (LookAndFeel)Class.forName(lnfclassname).newInstance();
      UIManager.setLookAndFeel(lnf);
    } else if (metaltheme != null) {
      MetalTheme theme = (MetalTheme)Class.forName(metaltheme).newInstance();
      MetalLookAndFeel metal = new MetalLookAndFeel();
      MetalLookAndFeel.setCurrentTheme(theme);
      UIManager.setLookAndFeel(metal);
    } else {
      Skin skin = null;
      
      if (packtheme != null) {
        if (SkinUtils.DEBUG) {
          System.out.println("Loading themepack " + packtheme);
        }
        if (packtheme.toLowerCase().endsWith(".xml")) {
          skin = SkinLookAndFeel.loadThemePackDefinition(new File(packtheme)
            .toURL());
        } else {
          skin = SkinLookAndFeel.loadThemePack(packtheme);
        }
      } else if (gtktheme != null) {
        if (kdetheme != null) {
          skin = new CompoundSkin(SkinLookAndFeel.loadSkin(gtktheme),
                                  SkinLookAndFeel.loadSkin(kdetheme));
        }
        else {
          skin = SkinLookAndFeel.loadSkin(gtktheme);
        }
      }
      
      /*
       *  try to use the user default skin
       */
      if (skin == null) {
        if (SkinUtils.DEBUG) {
          System.out.println("Trying user skin");
        }
        skin = SkinLookAndFeel.getSkin();
      }
      
      if (skin != null) {
        SkinLookAndFeel.setSkin(skin);
        SkinLookAndFeel lnf = new SkinLookAndFeel();
        UIManager.setLookAndFeel(lnf);
        
        UIManager.
          addPropertyChangeListener(new PropertyChangeListener() {
              public void propertyChange(PropertyChangeEvent event) {
                Object newLF = event.getNewValue();
                
                if ((newLF instanceof SkinLookAndFeel) == false) {
                  try {
                    UIManager.setLookAndFeel(new SkinLookAndFeel());
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                }
                
              }
            });
        
      }
      else {
        System.out.println("No GTK theme provided, defaulting to application Look And Feel");
      }
    }

    try {
      mainMethod.invoke(null, new Object[]{realArgs});       
    } catch (IllegalAccessException e) {
      System.err.println("Please make sure the class " + clazz.getName() +
                         " and the method main(String[] args) are public.");
      System.exit(1);
    } catch (Throwable e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Description of the Method
   */
  static void printUsage() {
    String usage = "Skinit - Skin Look And Feel " +
        SkinLookAndFeel.version() + " wrapper\n" +
        "Usage: skinit [options] class [args...]\n" +
        "\n" +
        "where options include:\n" +
        "\t-gtk <name>              GTK Theme Filename\n" +
        "\t-kde <name>              KDE Theme Filename\n" +
        "\t-pack <name>             Theme Pack Filename\n" +
        "\t-lnf <classname>         Look and feel class name\n" +
        "\t-metaltheme <classname>  Metal Theme to use\n";
    System.out.println(usage);
    System.exit(1);
  }

}
