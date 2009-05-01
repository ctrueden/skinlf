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

import com.l2fprod.gui.plaf.skin.SkinUtils;

import java.awt.Image;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:21:08 $
 */
public class GtkImage extends GtkProps implements TreeNode {

  GtkStyle style;
  Image preloaded;

  /**
   * Sets the Preview attribute of the GtkImage object
   *
   * @param image  The new Preview value
   */
  public void setPreview(Image image) {
    preloaded = image;
  }

  /**
   * Gets the Preview attribute of the GtkImage object
   *
   * @param w              Description of Parameter
   * @param h              Description of Parameter
   * @return               The Preview value
   * @exception Exception  Description of Exception
   */
  public Image getPreview(int w, int h) throws Exception {
    if (preloaded == null) {
      preloaded = getImage(style.getParser().getDirectory());
      if (preloaded != null) {
        preloaded = preloaded.getScaledInstance(w, h, 0);
      }
    }
    return preloaded;
  }

  /**
   * Gets the Filename attribute of the GtkImage object
   *
   * @return   The Filename value
   */
  public String getFilename() {
    String filename = (String) getProperty("file");
    if (filename != null) {
      return filename;
    }
    filename = (String) getProperty("overlay_file");
    return filename;
  }

  /**
   * Gets the Image attribute of the GtkImage object
   *
   * @param skinDirectory  Description of Parameter
   * @return               The Image value
   * @exception Exception  Description of Exception
   */
  public Image getImage(URL skinDirectory) throws Exception {
    Image im = null;
    im = getImage(skinDirectory, "file");
    if (im == null) {
      im = getImage(skinDirectory, "overlay_file");
    }
    return im;
  }

  /**
   * Gets the OverlayImage attribute of the GtkImage object
   *
   * @param skinDirectory  Description of Parameter
   * @return               The OverlayImage value
   * @exception Exception  Description of Exception
   */
  public Image getOverlayImage(URL skinDirectory) throws Exception {
    return getImage(skinDirectory, "overlay_file");
  }

  /**
   * Gets the Image attribute of the GtkImage object
   *
   * @param skinDirectory  Description of Parameter
   * @param prop           Description of Parameter
   * @return               The Image value
   * @exception Exception  Description of Exception
   */
  public Image getImage(URL skinDirectory, String prop) throws Exception {
    String filename = (String) getProperty(prop);
    if (filename == null) {
      return null;
    }

    return SkinUtils.loadImage(new URL(skinDirectory, filename));
  }

  /**
   * Gets the AllowsChildren attribute of the GtkImage object
   *
   * @return   The AllowsChildren value
   */
  public boolean getAllowsChildren() {
    return false;
  }

  /**
   * Gets the ChildAt attribute of the GtkImage object
   *
   * @param childIndex  Description of Parameter
   * @return            The ChildAt value
   */
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /**
   * Gets the ChildCount attribute of the GtkImage object
   *
   * @return   The ChildCount value
   */
  public int getChildCount() {
    return 0;
  }

  /**
   * Gets the Index attribute of the GtkImage object
   *
   * @param node  Description of Parameter
   * @return      The Index value
   */
  public int getIndex(TreeNode node) {
    return -1;
  }

  /**
   * Gets the Parent attribute of the GtkImage object
   *
   * @return   The Parent value
   */
  public TreeNode getParent() {
    return style;
  }

  /**
   * Gets the Leaf attribute of the GtkImage object
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
    return getProperties().toString();
  }

}
