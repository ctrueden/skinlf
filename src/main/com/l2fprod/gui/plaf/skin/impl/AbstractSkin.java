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

import javax.swing.UIDefaults;

/**
 * Default Skin Support. <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:21:42 $
 */
public class AbstractSkin implements Skin {

  /**
   * Description of the Field
   */
  protected SkinPersonality personality;
  /**
   * Description of the Field
   */
  protected SkinButton button;
  /**
   * Description of the Field
   */
  protected SkinFrame frame;
  /**
   * Description of the Field
   */
  protected SkinTab tab;
  /**
   * Description of the Field
   */
  protected SkinProgress progress;
  /**
   * Description of the Field
   */
  protected SkinScrollbar scrollbar;
  /**
   * Description of the Field
   */
  protected SkinSplitPane splitpane;
  /**
   * Description of the Field
   */
  protected SkinSeparator separator;
  /**
   * Description of the Field
   */
  protected SkinSlider slider;
  /**
   * Description of the Field
   */
  protected java.util.Hashtable resources = new java.util.Hashtable();

  /**
   * Gets the Personality attribute of the AbstractSkin object
   *
   * @return   The Personality value
   */
  public SkinPersonality getPersonality() {
    return personality;
  }

  /**
   * Gets the Button attribute of the AbstractSkin object
   *
   * @return   The Button value
   */
  public SkinButton getButton() {
    return button;
  }

  /**
   * Gets the Frame attribute of the AbstractSkin object
   *
   * @return   The Frame value
   */
  public SkinFrame getFrame() {
    return frame;
  }

  /**
   * Gets the Tab attribute of the AbstractSkin object
   *
   * @return   The Tab value
   */
  public SkinTab getTab() {
    return tab;
  }

  /**
   * Gets the Progress attribute of the AbstractSkin object
   *
   * @return   The Progress value
   */
  public SkinProgress getProgress() {
    return progress;
  }

  /**
   * Gets the Colors attribute of the AbstractSkin object
   *
   * @return   The Colors value
   */
  public String[] getColors() {
    return null;
  }

  /**
   * Gets the Scrollbar attribute of the AbstractSkin object
   *
   * @return   The Scrollbar value
   */
  public SkinScrollbar getScrollbar() {
    return scrollbar;
  }

  /**
   * Gets the Separator attribute of the AbstractSkin object
   *
   * @return   The Separator value
   */
  public SkinSeparator getSeparator() {
    return separator;
  }

  /**
   * Gets the SplitPane attribute of the AbstractSkin object
   *
   * @return   The SplitPane value
   */
  public SkinSplitPane getSplitPane() {
    return splitpane;
  }

  /**
   * Gets the Slider attribute of the AbstractSkin object
   *
   * @return   The Slider value
   */
  public SkinSlider getSlider() {
    return slider;
  }

  /**
   * Gets the Resource attribute of the AbstractSkin object
   *
   * @param key  Description of Parameter
   * @return     The Resource value
   */
  public Object getResource(Object key) {
    return resources.get(key);
  }

  /**
   * Description of the Method
   */
  public void unload() {
  }

  public void initComponentDefaults(UIDefaults table) {
  }

}
