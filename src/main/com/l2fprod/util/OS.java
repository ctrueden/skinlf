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
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.4 $, $Date: 2009-05-01 13:23:50 $
 */
public final class OS {

  /**
   * Description of the Field
   */
  public final static int JDK1_1 = 11;
  /**
   * Description of the Field
   */
  public final static int JDK1_2 = 12;
  /**
   * Description of the Field
   */
  public final static int JDK1_3 = 13;
  /**
   * Description of the Field
   */
  public final static int JDK1_4 = 14;

  private static boolean isWindows95;
  private static boolean isWindowsNT;
  private static boolean isWindows2000;
  private static boolean isMacintosh;
  private static boolean isSolaris;
  private static boolean isLinux;
  private static boolean isCaseSensitive;

  private static int jdkVersion = JDK1_1;

  /**
   * Constructor for the OS object
   */
  private OS() {
  }

  /**
   * Gets the Windows attribute of the OS class
   *
   * @return   The Windows value
   */
  public static boolean isWindows() {
    return isWindows95() || isWindowsNT() || isWindows2000();
  }

  /**
   * Gets the Windows95 attribute of the OS class
   *
   * @return   The Windows95 value
   */
  public static boolean isWindows95() {
    return isWindows95;
  }

  /**
   * Gets the WindowsNT attribute of the OS class
   *
   * @return   The WindowsNT value
   */
  public static boolean isWindowsNT() {
    return isWindowsNT;
  }

  /**
   * Gets the Windows2000 attribute of the OS class
   *
   * @return   The Windows2000 value
   */
  public static boolean isWindows2000() {
    return isWindows2000;
  }

  /**
   * Gets the Macintosh attribute of the OS class
   *
   * @return   The Macintosh value
   */
  public static boolean isMacintosh() {
    return isMacintosh;
  }

  /**
   * Gets the Solaris attribute of the OS class
   *
   * @return   The Solaris value
   */
  public static boolean isSolaris() {
    return isSolaris;
  }

  /**
   * Gets the Linux attribute of the OS class
   *
   * @return   The Linux value
   */
  public static boolean isLinux() {
    return isLinux;
  }

  /**
   * Gets the Unix attribute of the OS class
   *
   * @return   The Unix value
   */
  public static boolean isUnix() {
    return isSolaris() || isLinux();
  }

  /**
   * Gets the CaseSensitive attribute of the OS class
   *
   * @return   The CaseSensitive value
   */
  public static boolean isCaseSensitive() {
    return isCaseSensitive;
  }

  /**
   * Gets the JDKVersion attribute of the OS class
   *
   * @return   The JDKVersion value
   */
  public static int getJDKVersion() {
    return jdkVersion;
  }

  /**
   * Gets the OneDotOne attribute of the OS class
   *
   * @return   The OneDotOne value
   */
  public static boolean isOneDotOne() {
    return jdkVersion == JDK1_1;
  }

  /**
   * Gets the OneDotTwo attribute of the OS class
   *
   * @return   The OneDotTwo value
   */
  public static boolean isOneDotTwo() {
    return jdkVersion == JDK1_2;
  }

  /**
   * Gets the OneDotThree attribute of the OS class
   *
   * @return   The OneDotThree value
   */
  public static boolean isOneDotThree() {
    return jdkVersion == JDK1_3;
  }

  /**
   * Gets the OneDotFour attribute of the OS class
   *
   * @return   The OneDotFour value
   */
  public static boolean isOneDotFour() {
    return jdkVersion == JDK1_4;
  }

  public static boolean isOneDotFourOrMore() {
    return jdkVersion >= JDK1_4;
  }
  
  /**
   * Gets the OneDotThreeOrMore attribute of the OS class
   *
   * @return   The OneDotThreeOrMore value
   */
  public static boolean isOneDotThreeOrMore() {
    return jdkVersion >= JDK1_3;
  }

  /**
   * Description of the Method
   *
   * @param path           Description of Parameter
   * @exception Exception  Description of Exception
   */
  public static void openDocument(String path) throws Exception {
    if (isWindows2000()) {
      Runtime.getRuntime().exec(new String[]{"cmd /c start", path});
    }
    else if (isWindows()) {
      Runtime.getRuntime().exec(new String[]{"start", path});
    }
    else {
      System.err.println("OS.openDocument() not supported on this platform (" + System.getProperty("os.name"));
    }
  }

  static {
    String s = System.getProperty("os.name").toLowerCase();
    String version = System.getProperty("os.version").toLowerCase();

    if ("windows nt".equals(s) && "5.0".equals(version)) {
      isWindows2000 = true;
    }
    else if (s.equals("windows nt")) {
      isWindowsNT = true;
    }
    else if (s.startsWith("windows")) {
      // win95 or win98
      isWindows95 = true;
    }
    else if (s.equals("macintosh") || s.equals("macos") ||
        s.equals("mac os") || s.equals("mac os x")) {
      isMacintosh = true;
    }
    else if (s.equals("sunos") || s.equals("solaris")) {
      isSolaris = true;
      isCaseSensitive = true;
    }
    else if (s.equals("linux")) {
      isLinux = true;
      isCaseSensitive = true;
    }
  }

  // JDK version
  static {
    try {
      Class.forName("java.lang.ref.WeakReference");
      jdkVersion = JDK1_2;
    } catch (Exception e) {
    }
    try {
      Class.forName("javax.swing.UIDefaults$LazyInputMap");
      jdkVersion = JDK1_3;
    } catch (Exception e) {
    }
    try {
      Class.forName("java.lang.CharSequence");
      jdkVersion = JDK1_4;
    } catch (Exception e) {
    }
  }

}
