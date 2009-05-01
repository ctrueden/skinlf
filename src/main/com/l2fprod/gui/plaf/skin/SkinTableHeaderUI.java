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

import java.util.Enumeration;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:45 $
 */
public final class SkinTableHeaderUI extends BasicTableHeaderUI {

  TableCellRenderer renderer;
  Skin skin = SkinLookAndFeel.getSkin();

  /**
   * Constructor for the SkinTableHeaderUI object
   */
  public SkinTableHeaderUI() {
    super();
    renderer = skin.getPersonality().getTableHeaderRenderer();
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   */
  public void installUI(JComponent c) {
    super.installUI(c);

    try {
      // if JDK1.3 or later
      header.getClass().getMethod("setDefaultRenderer", new Class[]{TableCellRenderer.class}).invoke(header, new Object[]{renderer});
    } catch (Exception e) {
      // else do it the old way
      Enumeration enumeration = header.getColumnModel().getColumns();
      while (enumeration.hasMoreElements()) {
        TableColumn aColumn = (TableColumn) enumeration.nextElement();
        aColumn.setHeaderRenderer(renderer);
      }
    }
    // end of else
  }

  /**
   * Description of the Method
   *
   * @param h  Description of Parameter
   * @return   Description of the Returned Value
   */
  public static ComponentUI createUI(JComponent h) {
    return new SkinTableHeaderUI();
  }

}

