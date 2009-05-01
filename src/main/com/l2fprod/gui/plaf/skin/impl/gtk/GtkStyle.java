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

import javax.swing.tree.TreeNode;
import java.util.Enumeration;
import javax.swing.tree.MutableTreeNode;

import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
public final class GtkStyle extends GtkProps implements MutableTreeNode {

  /**
   * Description of the Field
   */
  public String name;

  /**
   * Description of the Field
   */
  public GtkEngine engine;

  /**
   * Description of the Field
   */
  public MutableTreeNode parent;

  /**
   * Description of the Field
   */
  public GtkParser parser;

  /**
   * Sets the Parent attribute of the GtkStyle object
   *
   * @param newParent  The new Parent value
   */
  public void setParent(MutableTreeNode newParent) {
    this.parent = newParent;
  }

  /**
   * Sets the UserObject attribute of the GtkStyle object
   *
   * @param object  The new UserObject value
   */
  public void setUserObject(Object object) {
  }

  /**
   * Gets the Engine attribute of the GtkStyle object
   *
   * @return   The Engine value
   */
  public GtkEngine getEngine() {
    return engine;
  }

  /**
   * Gets the Parser attribute of the GtkStyle object
   *
   * @return   The Parser value
   */
  public GtkParser getParser() {
    return parser;
  }

  /**
   * Gets the AllowsChildren attribute of the GtkStyle object
   *
   * @return   The AllowsChildren value
   */
  public boolean getAllowsChildren() {
    return true;
  }

  /**
   * Gets the ChildAt attribute of the GtkStyle object
   *
   * @param childIndex  Description of Parameter
   * @return            The ChildAt value
   */
  public TreeNode getChildAt(int childIndex) {
    return (TreeNode) engine.getImages().elementAt(childIndex);
  }

  /**
   * Gets the ChildCount attribute of the GtkStyle object
   *
   * @return   The ChildCount value
   */
  public int getChildCount() {
    if (engine == null) {
      return 0;
    }
    else {
      return engine.getImages().size();
    }
  }

  /**
   * Gets the Index attribute of the GtkStyle object
   *
   * @param node  Description of Parameter
   * @return      The Index value
   */
  public int getIndex(TreeNode node) {
    return engine.getImages().indexOf(node);
  }

  /**
   * Gets the Parent attribute of the GtkStyle object
   *
   * @return   The Parent value
   */
  public TreeNode getParent() {
    return parent;
  }

  /**
   * Gets the Leaf attribute of the GtkStyle object
   *
   * @return   The Leaf value
   */
  public boolean isLeaf() {
    return false;
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public Enumeration children() {
    throw new Error("Not implemented");
  }

  /**
   * Description of the Method
   *
   * @return   Description of the Returned Value
   */
  public String toString() {
    return name;
  }

  /**
   * Description of the Method
   *
   * @param child  Description of Parameter
   * @param index  Description of Parameter
   */
  public void insert(MutableTreeNode child, int index) {
  }

  /**
   * Description of the Method
   *
   * @param index  Description of Parameter
   */
  public void remove(int index) {
  }

  /**
   * Description of the Method
   *
   * @param node  Description of Parameter
   */
  public void remove(MutableTreeNode node) {
  }

  /**
   * Description of the Method
   */
  public void removeFromParent() {
  }

}
