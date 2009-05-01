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

import javax.swing.SwingConstants;
import com.l2fprod.gui.plaf.skin.impl.*;
import com.l2fprod.gui.plaf.skin.impl.gtk.parser.*;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:21:08 $
 */
final class GtkProgress extends AbstractSkinProgress implements SwingConstants {

	/**
	 * Constructor for the GtkProgress object
	 *
	 * @param parser         Description of Parameter
	 * @exception Exception  Description of Exception
	 */
	public GtkProgress(GtkParser parser) throws Exception {
		//PENDING(fred): progress needs to be improved with vertical and horizontal progress
		progressBarHorizontal =
      GtkUtils.newButton(parser, "GtkProgressBar",
                         new String[]{"function", "detail"},
                         new String[]{"BOX", "bar"},
                         false, true, true, false);

		progressBarBackHorizontal =
      GtkUtils.newButton(parser, "GtkProgressBar",
                         new String[]{"function", "detail"},
                         new String[]{"BOX", "trough"},
                         false, true, true, false);

    progressBarVertical = progressBarHorizontal.
      rotateCounterClockWise();
    progressBarBackVertical = progressBarBackHorizontal.
      rotateCounterClockWise();
	}

}
