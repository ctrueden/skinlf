/* ====================================================================
 *
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright (c) @YEAR@ L2FProd.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by L2FProd.com
 *        (http://www.L2FProd.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not
 *    be used to endorse or promote products derived from this software
 *    without prior written permission. For written permission, please
 *    contact info@L2FProd.com.
 *
 * 5. Products derived from this software may not be called "SkinLF"
 *    nor may "SkinLF" appear in their names without prior written
 *    permission of L2FProd.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL L2FPROD.COM OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
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
 * @version   $Revision: 1.3 $, $Date: 2003-12-06 21:47:37 $
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
	    g.drawString(keyText, 
                   metrics.stringWidth(tipText) + padSpaceBetweenStrings, 
                   2 + metrics.getAscent());
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
