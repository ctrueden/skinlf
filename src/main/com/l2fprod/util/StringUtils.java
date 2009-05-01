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
package com.l2fprod.util;

import java.util.StringTokenizer;

/**
 * Description of the Class
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class StringUtils {

  /**
   * Description of the Method
   *
   * @param txt   Description of Parameter
   * @param from  Description of Parameter
   * @param to    Description of Parameter
   * @return      Description of the Returned Value
   */
  public static String replace(String txt, String from, String to) {
    int index = txt.indexOf(from);

    if (index != -1) {
      txt = txt.substring(0, index) + to + replace(txt.substring(index + from.length()), from, to);
    }

    return txt;
  }

  /**
   * Description of the Method
   *
   * @param s      Description of Parameter
   * @param delim  Description of Parameter
   * @return       Description of the Returned Value
   */
  public static String[] splitString(String s, String delim) {
    StringTokenizer token = new StringTokenizer(s, delim);
    String[] split = new String[token.countTokens()];
    int i = 0;
    while (token.hasMoreTokens()) {
      split[i] = token.nextToken();
      i++;
    }
    return split;
  }

  /**
   * Description of the Method
   *
   * @param s       Description of Parameter
   * @param length  Description of Parameter
   * @param c       Description of Parameter
   * @return        Description of the Returned Value
   */
  public static String format(String s, int length, char c) {
    int len = s.length();
    if (len >= length) {
      return s;
    }
    while (len < length) {
      s += c;
      len++;
    }
    return s;
  }

}
