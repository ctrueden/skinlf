/* ====================================================================
 *
 * @PROJECT.FULLNAME@ @VERSION@ License.
 *
 * Copyright (c) @YEAR@ L2FProd.com.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by L2FProd.com
 *        (http://www.L2FProd.com/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "@PROJECT.FULLNAME@", "SkinLF" and "L2FProd.com" must not
 *    be used to endorse or promote products derived from this software
 *    without prior written permission. For written permission, please
 *    contact info@L2FProd.com.
 *
 * 5. Products derived from this software may not be called "SkinLF"
 *    nor may "SkinLF" appear in their names without prior written
 *    permission of L2FProd.com.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL L2FPROD.COM OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package com.l2fprod.util;

/**
 * Created on 11/04/2001 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.2 $, $Date: 2003-08-25 20:00:47 $
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
