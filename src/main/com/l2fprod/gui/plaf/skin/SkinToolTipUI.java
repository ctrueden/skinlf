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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 * @author    $Author: l2fprod $
 * @version   $Revision: 1.5 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinToolTipUI extends BasicToolTipUI {

  static SkinToolTipUI sharedInstance = new SkinToolTipUI();

  private Font smallFont;			    	     
  private JToolTip tip;
  public static final int padSpaceBetweenStrings = 12;
  private String acceleratorDelimiter;
  
  public SkinToolTipUI() {
    super();
  }

  public static ComponentUI createUI(JComponent c) {
    return sharedInstance;
  }

  public void installUI(JComponent c) {
    super.installUI(c);
    tip = (JToolTip)c;
    Font f = c.getFont();
    smallFont = new Font( f.getName(), f.getStyle(), f.getSize() - 2 );
    acceleratorDelimiter = UIManager.getString( "MenuItem.acceleratorDelimiter" );
    if ( acceleratorDelimiter == null ) { acceleratorDelimiter = "-"; }
  }

  public void paint(Graphics g, JComponent c) {
    super.paint(g, c);

    Font font = c.getFont();
    FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(font);
    String keyText = getAcceleratorString();
    String tipText = ((JToolTip)c).getTipText();
    if (tipText == null) {
	    tipText = "";
    }
    if (! (keyText.equals(""))) {  // only draw control key if there is one
	    g.setFont(smallFont);
	    g.setColor( c.getForeground() );

	    // from http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4449310
        metrics = c.getFontMetrics(smallFont);
	    g.drawString(keyText, c.getWidth() - 2 - metrics.stringWidth(keyText),
	      c.getHeight() - 2 - metrics.getDescent());
    }
  }

  public Dimension getPreferredSize(JComponent c) {
    Dimension d = super.getPreferredSize(c);

    String key = getAcceleratorString();
    if (! (key.equals(""))) {
      FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(smallFont);	
	    d.width += fm.stringWidth(key) + padSpaceBetweenStrings;
    }
    return d;
  }
  
  public String getAcceleratorString() {
    JComponent comp = tip.getComponent();
    if (comp == null) {
	    return "";
    }
    KeyStroke[] keys = comp.getRegisteredKeyStrokes();
    String controlKeyStr = "";
    
    for (int i = 0; i < keys.length; i++) {
      int mod = keys[i].getModifiers();
      int condition =  comp.getConditionForKeyStroke(keys[i]);
      
      if ( condition == JComponent.WHEN_IN_FOCUSED_WINDOW &&
           ( (mod & InputEvent.ALT_MASK) != 0 || (mod & InputEvent.CTRL_MASK) != 0 ||
             (mod & InputEvent.SHIFT_MASK) != 0 || (mod & InputEvent.META_MASK) != 0 ) )
        {
          controlKeyStr = KeyEvent.getKeyModifiersText(mod) +
            acceleratorDelimiter + (char)keys[i].getKeyCode();
          break;
        }
    }
    
    /* Special case for menu item since they do not register a
       keyboard action for their mnemonics and they always use Alt */
    if ( controlKeyStr.equals("") && comp instanceof JMenuItem )
      {
        int mnemonic = ((JMenuItem) comp).getMnemonic();
        if ( mnemonic != 0 )
          {
            controlKeyStr = "Alt" + acceleratorDelimiter + (char) mnemonic;
          }
      }
    
    return controlKeyStr;
  }

}
