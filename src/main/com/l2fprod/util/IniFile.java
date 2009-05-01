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

import java.io.*;
import java.util.*;
import java.net.URL;

/**
 * @author    $Author: l2fprod $
 * @created   27 avril 2002
 * @version   $Revision: 1.6 $, $Date: 2009-05-01 13:23:50 $
 */
public class IniFile {

  Hashtable sections;

  /**
   * Constructor for the IniFile object
   */
  public IniFile() {
    sections = new Hashtable();
  }

  /**
   * Constructor for the IniFile object
   *
   * @param filename                   Description of Parameter
   * @exception FileNotFoundException  Description of Exception
   */
  public IniFile(String filename) throws FileNotFoundException {
    this();
    load(filename);
  }

  /**
   * Constructor for the IniFile object
   *
   * @param url              Description of Parameter
   * @exception IOException  Description of Exception
   */
  public IniFile(URL url) throws IOException {
    this();
    load(url.openStream());
  }

  /**
   * Constructor for the IniFile object
   *
   * @param input            Description of Parameter
   */
  public IniFile(InputStream input) {
    this();
    load(input);
  }

  /**
   * Sets the KeyValue attribute of the IniFile object
   *
   * @param section  The new KeyValue value
   * @param key      The new KeyValue value
   * @param value    The new KeyValue value
   */
  public void setKeyValue(String section, String key, String value) {
    try {
      getSection(section).put(key.toLowerCase(), value);
    } catch (NullPointerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the Sections attribute of the IniFile object
   *
   * @return   The Sections value
   */
  public Hashtable getSections() {
    return sections;
  }

  /**
   * Gets the Section attribute of the IniFile object
   *
   * @param section  Description of Parameter
   * @return         The Section value
   */
  public Hashtable getSection(String section) {
    return (Hashtable) (sections.get(section.toLowerCase()));
  }

  /**
   * Gets the NullOrEmpty attribute of the IniFile object
   *
   * @param section  Description of Parameter
   * @param key      Description of Parameter
   * @return         The NullOrEmpty value
   */
  public boolean isNullOrEmpty(String section, String key) {
    String value = getKeyValue(section, key);
    return (value == null || value.length() == 0);
  }

  /**
   * Gets the KeyValue attribute of the IniFile object
   *
   * @param section  Description of Parameter
   * @param key      Description of Parameter
   * @return         The KeyValue value
   */
  public String getKeyValue(String section, String key) {
    try {
      return (String) getSection(section).get(key.toLowerCase());
    } catch (NullPointerException e) {
      return null;
    }
  }

  /**
   * Gets the KeyIntValue attribute of the IniFile object
   *
   * @param section  Description of Parameter
   * @param key      Description of Parameter
   * @return         The KeyIntValue value
   */
  public int getKeyIntValue(String section, String key) {
    return getKeyIntValue(section, key, 0);
  }

  /**
   * Gets the KeyIntValue attribute of the IniFile object
   *
   * @param section       Description of Parameter
   * @param key           Description of Parameter
   * @param defaultValue  Description of Parameter
   * @return              The KeyIntValue value
   */
  public int getKeyIntValue(String section, String key, int defaultValue) {
    String value = getKeyValue(section, key.toLowerCase());
    if (value == null) {
      return defaultValue;
    }
    else {
      try {
        return Integer.parseInt(value);
      } catch (NumberFormatException e) {
        return 0;
      }
    }
  }

  /**
   * return true if the value of the key is yes/true, false if no/false, defaultValue in all other cases.
   * @param section
   * @param key
   * @param defaultValue
   */
  public boolean getKeyBooleanValue(String section, String key, boolean defaultValue) {
      String value = getKeyValue(section, key.toLowerCase());
      if (value == null) {
        return defaultValue;
      }
      if ("yes".equalsIgnoreCase(value) || "true".equalsIgnoreCase(value))
          return true;
      if ("no".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value))
          return false;
      return defaultValue;
  }

  /**
   * Gets the KeysAndValues attribute of the IniFile object
   *
   * @param aSection  Description of Parameter
   * @return          The KeysAndValues value
   */
  public String[][] getKeysAndValues(String aSection) {
    Hashtable section = getSection(aSection);
    if (section == null) {
      return null;
    }
    String[][] results = new String[section.size()][2];
    int i = 0;
    for (Enumeration f = section.keys(), g = section.elements(); f.hasMoreElements(); i++) {
      results[i][0] = (String) f.nextElement();
      results[i][1] = (String) g.nextElement();
    }
    return results;
  }

  public String getSectionWhere(String[][] query) {
    String result = null;
    
    for (Enumeration e = getSections().keys(); e.hasMoreElements(); ) {
	    String current = (String)e.nextElement();
	    boolean match = true;
	    for (int i = 0, c = query.length; i < c; i++) {
        if (getKeyValue(current, query[i][0]) == null ||
            getKeyValue(current, query[i][0]).equals(query[i][1]) == false) {
          match = false;
          break;
        }
	    }
	    if (match) {
        result = current;
        break;
	    }
    }
    return result;
  }

  /**
   * Description of the Method
   *
   * @param filename                   Description of Parameter
   * @exception FileNotFoundException  Description of Exception
   */
  public void load(String filename) throws FileNotFoundException {
    load(new FileInputStream(filename));
  }

  /**
   * Description of the Method
   *
   * @param filename         Description of Parameter
   * @exception IOException  Description of Exception
   */
  public void save(String filename) throws IOException {
    save(new FileOutputStream(filename));
  }

  /**
   * Description of the Method
   *
   * @param in  Description of Parameter
   */
  public void load(InputStream in) {
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(in));
      String read;
      Hashtable section = null;
      String section_name;
      while ((read = input.readLine()) != null) {
        if (read.startsWith(";") || read.startsWith("#")) {
          continue;
        }
        else if (read.startsWith("[")) {
          // new section
          section_name = read.substring(1, read.indexOf("]")).toLowerCase();
          section = (Hashtable) sections.get(section_name);
          if (section == null) {
            section = new Hashtable();
            sections.put(section_name, section);
          }
        }
        else if (read.indexOf("=") != -1 && section != null) {
          // new key
          String key = read.substring(0, read.indexOf("=")).trim().toLowerCase();
          String value = read.substring(read.indexOf("=") + 1).trim();
          section.put(key, value);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Description of the Method
   *
   * @param out  Description of Parameter
   */
  public void save(OutputStream out) {
    try {
      PrintWriter output = new PrintWriter(out);
      String section;
      for (Enumeration e = sections.keys(); e.hasMoreElements(); ) {
        section = (String) e.nextElement();
        output.println("[" + section + "]");
        for (Enumeration f = getSection(section).keys(), g = getSection(section).elements();
            f.hasMoreElements(); ) {
          output.println(f.nextElement() + "=" + g.nextElement());
        }
      }
      output.flush();
      output.close();
      out.flush();
      out.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds a feature to the Section attribute of the IniFile object
   *
   * @param section  The feature to be added to the Section attribute
   */
  public void addSection(String section) {
    sections.put(section.toLowerCase(), new Hashtable());
  }

  /**
   * Description of the Method
   *
   * @param section  Description of Parameter
   */
  public void removeSection(String section) {
  }

  /**
   * Simple test function
   *
   * @param args           The command line arguments
   * @exception Exception  Description of Exception
   */
  public static void main(String[] args) throws Exception {
    (new IniFile()).load(new FileInputStream(args[0]));
  }
}
