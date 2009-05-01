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

import java.awt.*;
import java.awt.event.*;

import com.l2fprod.gui.nativeskin.NativeSkin;

/**
 * Always On top demo.<BR>
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class alwaysontop {

  public static void main(String[] args) {
    final Frame frame = new Frame("Always On Top test");
    frame.setLayout(new BorderLayout());

    final Checkbox check = new Checkbox("Check to make this window always on top");
    check.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          NativeSkin.getInstance().setAlwaysOnTop(frame, check.getState());
        }
      });

    frame.add("Center", check);

    frame.pack();

    frame.setLocation(100, 100);
    frame.show();
  }
  
}
