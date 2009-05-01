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
package com.l2fprod.gui.nativeskin.win32;

import com.l2fprod.gui.region.Region;

/**
 * Created on 21/12/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:24:46 $
 */
final class Win32Region extends Region {

  long nativeHandle;

  /**
   * Constructor for the Win32Region object
   */
  Win32Region() {
    super();
  }

  /**
   * Constructor for the Win32Region object
   *
   * @param nativeHandle  Description of Parameter
   */
  Win32Region(long nativeHandle) {
    this.nativeHandle = nativeHandle;
  }

}
