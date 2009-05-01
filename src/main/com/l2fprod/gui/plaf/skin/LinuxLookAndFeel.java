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

import com.l2fprod.gui.plaf.skin.impl.gtk.GtkSkin;
import com.l2fprod.gui.plaf.skin.impl.gtk.GtkSkinNotFoundException;

/**
 * Linux Look And Feel. <br>
 * Use this class to set the look and feel to the current user GTK theme. <br>
 * This code was submitted by Nicholas Allen <nicholas.allen@ireland.sun.com>
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:43 $
 */
public class LinuxLookAndFeel extends SkinLookAndFeel {

  /**
   * Constructor for the LinuxLookAndFeel object
   *
   * @exception GtkSkinNotFoundException  Description of Exception
   * @exception Exception                 Description of Exception
   */
  public LinuxLookAndFeel() throws GtkSkinNotFoundException, Exception {
    setSkin(new GtkSkin(GtkSkin.getDefaultSkinLocation()));
  }

}
