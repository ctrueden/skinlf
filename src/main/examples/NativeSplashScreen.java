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

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

import com.l2fprod.gui.region.*;
import com.l2fprod.gui.nativeskin.*;

/**
 * NativeSplashScreen.
 * A SkinRegion demo
 */
public class NativeSplashScreen extends Window
  implements KeyListener, MouseListener, ActionListener {
  
  public NativeSplashScreen(Frame parent, ImageIcon icon, int timeout) {
    super(parent);

    setLayout(new BorderLayout());
    JLayeredPane layer = new JLayeredPane();
    JLabel picture = new JLabel(icon);
    layer.add(picture, JLayeredPane.DEFAULT_LAYER);
    layer.setPreferredSize(picture.getPreferredSize());
    picture.setSize(picture.getPreferredSize());
    add("Center", layer);

    // only use the region if it is supported by the platform
    if (NativeSkin.isSupported()) {
      // get the Region builder
      final NativeSkin builder = NativeSkin.getInstance();
      // create a region object from the icon image
      Region region = builder.createRegion(icon.getImage());
      // set the Region to the window
      builder.setWindowRegion(this, region, true);

      builder.setAlwaysOnTop(this, true);

      final JSlider slider = new JSlider(10, 255);
      layer.add(slider, JLayeredPane.PALETTE_LAYER);
      slider.setMajorTickSpacing(20);
      slider.setSnapToTicks(true);
      slider.setBounds(327, 147, 150, 20);
      slider.setOpaque(false);
      slider.setDoubleBuffered(false);
      builder.setWindowTransparency(NativeSplashScreen.this,
                                    slider.getValue());
      slider.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
            builder.setWindowTransparency(NativeSplashScreen.this,
                                          slider.getValue());
          }
        });
    } else {
      System.err.println("NativeSkin not supported on this platform");
    }
    

    pack();

    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((screen.width - getWidth())/2,
                (screen.height - getHeight())/2);

    // Listen for key strokes
    addKeyListener(this);

    // Listen for mouse events from here and parent
    addMouseListener(this);
    if (parent != null) {
    	parent.addMouseListener(this);
    }

    // Timeout after a while
    Timer timer = new Timer(0, this);
    timer.setRepeats(false);
    timer.setInitialDelay(timeout);
    timer.start();
  }

  public synchronized void block() {
    while(isVisible()) {
      try {         
        wait(5);
      } catch (Exception e) {        
      }      
    }
  }

  public void setVisible(boolean b) {
    if (!b) {
      NativeSkin.getInstance().setWindowTransparency(this, 255);
    }
    super.setVisible(b);
  }

  // Dismiss the window on a key press
  public void keyTyped(KeyEvent event) {}
  public void keyReleased(KeyEvent event) {}
  public void keyPressed(KeyEvent event) {
    setVisible(false);
    dispose();
  }

  // Dismiss the window on a mouse click
  public void mousePressed(MouseEvent event) {}
  public void mouseReleased(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {
    setVisible(false);
    dispose();
  }

  // Dismiss the window on a timeout
  public void actionPerformed(ActionEvent event) {
    setVisible(false);
    dispose();
  }

}
