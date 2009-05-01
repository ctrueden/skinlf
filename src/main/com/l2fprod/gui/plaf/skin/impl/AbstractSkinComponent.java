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
package com.l2fprod.gui.plaf.skin.impl;

import com.l2fprod.gui.plaf.skin.*;

/**
 * Default Skin Support. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:21:42 $
 */
public class AbstractSkinComponent implements SkinComponent {

  private Skin m_Skin;

  public AbstractSkinComponent() {
  }

  public AbstractSkinComponent(Skin p_ParentSkin) {
    m_Skin = p_ParentSkin;
  }

  public Skin getSkin() {
    return m_Skin;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public boolean status() {
    return false;
  }

  /**
   * Description of the Method
   *
   * @param c  Description of Parameter
   * @return   Description of the Returned Value
   */
  public boolean installSkin(javax.swing.JComponent c) {
    return false;
  }

  public void uninstallSkin(javax.swing.JComponent c) {
  }
  
}
