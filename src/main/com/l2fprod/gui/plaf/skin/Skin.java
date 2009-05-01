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

import javax.swing.UIDefaults;

/**
 * Skin Entry Class. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:24:43 $
 */
public interface Skin {

  /**
   * Gets the Personality attribute of the Skin object
   *
   * @return   The Personality value
   */
  public SkinPersonality getPersonality();

  /**
   * Gets the Button attribute of the Skin object
   *
   * @return   The Button value
   */
  public SkinButton getButton();

  /**
   * Gets the Frame attribute of the Skin object
   *
   * @return   The Frame value
   */
  public SkinFrame getFrame();

  /**
   * Gets the Tab attribute of the Skin object
   *
   * @return   The Tab value
   */
  public SkinTab getTab();

  /**
   * Gets the Progress attribute of the Skin object
   *
   * @return   The Progress value
   */
  public SkinProgress getProgress();

  /**
   * Gets the Colors attribute of the Skin object
   *
   * @return   The Colors value
   */
  public String[] getColors();

  /**
   * Gets the Scrollbar attribute of the Skin object
   *
   * @return   The Scrollbar value
   */
  public SkinScrollbar getScrollbar();

  /**
   * Gets the SplitPane attribute of the Skin object
   *
   * @return   The SplitPane value
   */
  public SkinSplitPane getSplitPane();

  /**
   * Gets the Slider attribute of the Skin object
   *
   * @return   The Slider value
   */
  public SkinSlider getSlider();

  /**
   * Gets the Separator attribute of the Skin object
   *
   * @return   The Separator value
   */
  public SkinSeparator getSeparator();

  /**
   * Description of the Method
   */
  public void unload();

  /**
   * Gets the Resource attribute of the Skin object
   *
   * @param key  Description of Parameter
   * @return     The Resource value
   */
  public Object getResource(Object key);

  public void initComponentDefaults(UIDefaults table);
}
