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

import com.l2fprod.util.OS;

import java.io.File;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalFileChooserUI;

/**
 * Created on 05/04/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:24:44 $
 */
public final class SkinFileChooserUI extends MetalFileChooserUI {

  private BasicFileView fileView = new SystemFileView();

  public static ComponentUI createUI(JComponent c) {
    return new SkinFileChooserUI((JFileChooser) c);
  }

  /**
   * Constructor for the SkinFileChooserUI object
   *
   * @param filechooser  Description of Parameter
   */
  public SkinFileChooserUI(JFileChooser filechooser) {
    super(filechooser);
  }

  public FileView getFileView(JFileChooser fc) {    
  	return fileView;
  }

  /**
   * return "native" icons for Files
   */
  protected class SystemFileView extends BasicFileView {
    public Icon getIcon(File f) {
      Icon icon = getCachedIcon(f);
      if (icon != null) { return icon; }
      if (OS.isOneDotFourOrMore() && f != null) {
        icon = getFileChooser().getFileSystemView().getSystemIcon(f);
      }
      if (icon == null) {
        icon = super.getIcon(f);
      }
      cacheIcon(f, icon);
      return icon;
    }
  }

}
