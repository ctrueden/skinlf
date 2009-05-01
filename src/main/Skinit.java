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

/**
 * Skinit. <br> Skinit is Skin Look And Feel wrapper. It allows you to
 * start any application with SkinLF. Skinit will prevent the
 * application to set its own Look And Feel. Once Skin Look And Feel
 * is set, it can't be removed. <br>
 * 
 * @author fred
 */
public class Skinit extends com.l2fprod.gui.plaf.skin.Skinit {

  /**
   * The main program for the Skinit class
   * 
   * @param args The command line arguments
   * @throws Exception Description of Exception
   */
  public static void main(String[] args) throws Exception {
    com.l2fprod.gui.plaf.skin.Skinit.main(args);
  }

}
