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

import com.l2fprod.gui.SkinApplet;

import java.awt.BorderLayout;
import javax.swing.*;

/**
 * SimpleApplet.<br>
 * An applet showing how to set the Skin Look And Feel using a themepack
 * provided as an applet parameter or using the themepack bundled in the applet
 * jar file.
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class SimpleApplet extends SkinApplet {

  /**
   * Constructor for the SimpleApplet object
   */
  public SimpleApplet() {
  }

  /**
   * The init() method is called by the Applet container (appletviewer,java
   * plugin,...). In this method, we will set up the look and feel and tries to
   * load a theme pack
   */
  public void init() {
    try {
      initSkin(THEMEPACK_TAG);
      createUI();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Description of the Method
   */
  public void createUI() {
    getContentPane().setLayout(new BorderLayout(3, 3));
    getContentPane().add("Center", new JButton("Congratulations !"));
  }

}

