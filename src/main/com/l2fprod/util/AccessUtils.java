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

/**
 * Created on 11/04/2001 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.3 $, $Date: 2009-05-01 13:23:50 $
 */
public class AccessUtils {

  /**
   * Gets the Property attribute of the AccessUtils class
   *
   * @param name  Description of Parameter
   * @return      The Property value
   */
  public static String getProperty(String name) {
    return getProperty(name, null);
  }

  /**
   * Gets the Property attribute of the AccessUtils class
   *
   * @param name          Description of Parameter
   * @param defaultValue  Description of Parameter
   * @return              The Property value
   */
  public static String getProperty(String name, String defaultValue) {
    String result = null;
    try {
      result = System.getProperty(name);
    } catch (Throwable th) {
    }
    if (result == null) {
      result = defaultValue;
    }
    return result;
  }

  public static int getIntValue(Class p_Class, String p_Field) {
    try {
      return p_Class.getField(p_Field).getInt(null);
    } catch (Throwable throwable) {
      throw new Error(throwable.getMessage());
    }
  }

  public static int getIntValue(Object p_Object, String p_Field) {
    try {
      return p_Object.getClass().getField(p_Field).getInt(p_Object);
    } catch (Throwable throwable) {
      throw new Error(throwable.getMessage());
    }
  }

  public static Object invoke(Object p_Object, String p_Method,
                              Class[] p_ParameterClasses,
                              Object[] p_Parameters) {
    try {
      return p_Object.getClass().getMethod(p_Method, p_ParameterClasses).invoke(p_Object, p_Parameters);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      throw new Error(throwable.getMessage());
    }
  }

  public static int getAsInt(Object p_Object, String p_Method) {
    return ((Integer)invoke(p_Object, p_Method, null, null)).intValue();
  }

  public static void setAsInt(Object p_Object, String p_Method, int p_Int) {
    invoke(p_Object, p_Method, new Class[]{int.class}, new Object[]{new Integer(p_Int)});
  }

}
