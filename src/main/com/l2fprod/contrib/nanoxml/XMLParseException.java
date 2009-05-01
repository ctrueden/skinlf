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
package com.l2fprod.contrib.nanoxml;

/**
 * An XMLParseException is thrown when an error occures while parsing an XML
 * string. <P>
 *
 * $Revision: 1.3 $<BR>
 * $Date: 2009-05-01 13:24:46 $<P>
 *
 *
 *
 * @author    Marc De Scheemaecker &lt;<A
 *      HREF="mailto:Marc.DeScheemaecker@advalvas.be" >
 *      Marc.DeScheemaecker@advalvas.be</A> &gt;
 * @created   27 avril 2002
 * @see       com.l2fprod.contrib.nanoxml.XMLElement
 * @version   1.6
 */
public class XMLParseException
     extends RuntimeException {

  /**
   * Where the error occurred, or -1 if the line number is unknown.
   */
  private int lineNr;


  /**
   * Creates an exception.
   *
   * @param tag      The name of the tag where the error is located.
   * @param message  A message describing what went wrong.
   */
  public XMLParseException(String tag,
      String message) {
    super("XML Parse Exception during parsing of "
        + ((tag == null) ? "the XML definition" : ("a " + tag + "-tag"))
        + ": " + message);
    this.lineNr = -1;
  }


  /**
   * Creates an exception.
   *
   * @param tag      The name of the tag where the error is located.
   * @param lineNr   The number of the line in the input.
   * @param message  A message describing what went wrong.
   */
  public XMLParseException(String tag,
      int lineNr,
      String message) {
    super("XML Parse Exception during parsing of "
        + ((tag == null) ? "the XML definition" : ("a " + tag + "-tag"))
        + " at line " + lineNr + ": " + message);
    this.lineNr = lineNr;
  }


  /**
   * Where the error occurred, or -1 if the line number is unknown.
   *
   * @return   The LineNr value
   */
  public int getLineNr() {
    return this.lineNr;
  }

}
