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

/**
 * Thrown when a Theme Pack requires a SkinLF version greater than the current.
 * <br>
 *
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.5 $, $Date: 2009-05-01 13:24:44 $
 */
public final class IncorrectVersionException extends Exception {

  /**
   * Constructor for the IncorrectVersionException object
   *
   * @param required  Description of Parameter
   * @param current   Description of Parameter
   */
  public IncorrectVersionException(String required, String current) {
    super("Incorrect Skin Look And Feel version, " +
        "current version is " + current + ", required version is " + required);
  }

  /**
   * Check if the current version is bigger or equals to the required
   * version. If this is not the case an
   * <code>IncorrectVersionException</code> is thrown.
   * 
   * @param current Description of Parameter
   * @param required Description of Parameter
   * @exception IncorrectVersionException if the current version does not fit
   */
  public static void checkRequiredVersion(String current, String required)
    throws IncorrectVersionException {
    if ("@VERSION@".equals(current)) {
      return;
    }
    
    java.util.StringTokenizer currentToken =
      new java.util.StringTokenizer(current, ".");
    java.util.StringTokenizer requiredToken =
      new java.util.StringTokenizer(required, ".");

    int currentCount = currentToken.countTokens();
    int requiredCount = requiredToken.countTokens();

    int min = Math.min(currentCount, requiredCount);

    for (int i = 0; i < min; i++) {
      String cTok = currentToken.nextToken();
      String rTok = requiredToken.nextToken();
      
      int cValue = Integer.parseInt(cTok);
      int rValue = Integer.parseInt(rTok);
      
      // the current version is bigger than the required
      if (cValue > rValue) {
        break;
      }
      if (cValue < rValue) {
        throw new IncorrectVersionException(required, current);
      }
      if ((i == min - 1) && (currentCount < requiredCount)) {
        throw new IncorrectVersionException(required, current);
      }
    }

  }
  
}
