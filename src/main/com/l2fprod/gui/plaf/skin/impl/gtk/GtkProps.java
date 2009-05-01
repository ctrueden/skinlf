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
package com.l2fprod.gui.plaf.skin.impl.gtk;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2009-05-01 13:21:08 $
 */
public class GtkProps {

  java.util.Hashtable properties;
  java.util.Vector keys;

  /**
   * Constructor for the GtkProps object
   */
  public GtkProps() {
    properties = new java.util.Hashtable();
    keys = new java.util.Vector();
  }

  /**
   * Sets the Property attribute of the GtkProps object
   *
   * @param key    The new Property value
   * @param value  The new Property value
   */
  public void setProperty(String key, Object value) {
    properties.put(key, value);
    if (!keys.contains(key)) {
      keys.addElement(key);
    }
  }

  /**
   * Gets the Property attribute of the GtkProps object
   *
   * @param key  Description of Parameter
   * @return     The Property value
   */
  public Object getProperty(Object key) {
    return properties.get(key);
  }

  /**
   * Gets the Properties attribute of the GtkProps object
   *
   * @return   The Properties value
   */
  public java.util.Hashtable getProperties() {
    return properties;
  }

  /**
   * Gets the Keys attribute of the GtkProps object
   *
   * @return   The Keys value
   */
  public java.util.Vector getKeys() {
    return keys;
  }

}
