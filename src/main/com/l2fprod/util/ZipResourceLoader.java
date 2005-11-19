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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Loads files from a local or network zip archive.
 *
 * @author    fred
 * @created   27 avril 2002
 */
public class ZipResourceLoader {

  /**
    * A cache of the files from the archive.
    */
  Hashtable resources = new Hashtable();

  /**
    * An instance of an anonymous subclass of URLStreamHandler that can handle files in zip archives.
    */
  URLStreamHandler handler =
    new URLStreamHandler() {
      protected URLConnection openConnection(URL u) {
        return new ZipURLConnection(u);
      }

      protected void parseURL(URL u, String spec, int start, int limit) {
        String file = u.getFile();
        int index = file.indexOf("/");
        if (index != -1) {
          String path = file.substring(0, index);
          setURL(u, u.getProtocol(), u.getHost(), u.getPort(),
              path + "/" + spec, u.getRef());
        } else {
          setURL(u, u.getProtocol(), u.getHost(), u.getPort(),
              spec, u.getRef());
        }
      }

      protected String toExternalForm(URL u) {
        return "ziploader://" + u.getFile();
      }
    };

  /**
   * Creates a ZipResourceLoader that loads data from an URL.
   *
   * @param p_JarUrl       URL to load files from.
   * @exception IOException  if there is an exception while loading the archive.
   */
  public ZipResourceLoader(URL p_JarUrl) throws IOException {
    this(p_JarUrl.openStream());
  }

  /**
    * Creates a ZipResourceLoader that loads data from an InputStream.
    */
  public ZipResourceLoader(InputStream p_JarStream) throws IOException {
    BufferedInputStream bis = new BufferedInputStream(p_JarStream);
    ZipInputStream input = new ZipInputStream(bis);

    ZipEntry entry = null;
    while ((entry = input.getNextEntry()) != null) {
      if (entry.isDirectory()) {
        continue;
      }
      else {
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        int read;
        while ((read = input.read()) != -1) {
          outBuffer.write(read);
        }
        resources.put(entry.getName(), outBuffer.toByteArray());
      }
    }
  }

  /**
   * Opens an InputStream from a file contained in the archive.
   *
   * @param name                       Name of the file to retrieve.
   * @return                           An input stream from the file.
   */
  public InputStream getResourceAsStream(String name) {
    return new ByteArrayInputStream(getURLContent(name));
  }

  /**
   * Gets the URL of a file in the archive.
   *
   * @param name                       Name of the file to retrieve.
   * @return                           The URL of the file.
   * @exception MalformedURLException  If the URL is malformed.
   */
  public URL getResource(final String name) throws MalformedURLException {
    if (resources.get(name) == null) {
      return null;
    }
    return new URL("ziploader", null, -1, name, handler);
  }

  /**
   * Gets a ZipResource representing the file.
   *
   * @param name  The name of the file to retrieve.
   * @return      The ZipResource representing the file
   */
  public ZipResource getZipResource(String name) {
    return new ZipResource(name);
  }

  /**
   * Gets a ZipResource representing the file.
   *
   * @param name  The URL of the resource to retrieve.
   * @return      The ZipResource representing the file.
   */
  public ZipResource getZipResource(URL name) {
    return getZipResource(name.getFile());
  }

  /**
   * Gets the files contained in this zip archive.
   *
   * @return   An Enumeration of the files in this archive.
   */
  public Enumeration entries() {
    return resources.keys();
  }

  /**
   * A debugging method.
   */
  public void dump() {
    for (Enumeration e = resources.keys(); e.hasMoreElements(); ) {
      String name = (String) e.nextElement();
      System.out.println(name + " size = " + ((byte[]) resources.get(name)).length);
    }
  }

  /**
   * Releases the resources claimed by this object.
   */
  public void release() {
    resources.clear();
    resources = null;
  }

  /**
   * Gets the raw data of this file as a byte array.
   *
   * @param name  The entry to load.
   * @return      The data contained in the entry.
   */
  private byte[] getURLContent(String name) {
    byte[] data = (byte[]) resources.get(name);
    if (data == null && name.startsWith("/")) {
      data = (byte[]) resources.get(name.substring(1));
    }
    return data;
  }

  /**
   * Diagnostic for the ZipResource class.
   *
   * @param args           The command line arguments
   * @exception Exception  If anything whatsoever goes wrong. :-)
   */
  public static void main(String[] args) throws Exception {
    ZipResourceLoader loader = new ZipResourceLoader(new File(args[0]).toURL());
    for (Enumeration e = loader.entries(); e.hasMoreElements(); ) {
      String name = (String) e.nextElement();
      System.out.println(name);
      URL url = loader.getResource(name);
      System.out.println(url);
      System.out.println(url.openStream());
      System.out.println(loader.getZipResource(name).getInputStream());
    }
  }

  /**
   * A class that represents a file contained in a zip archive.
   *
   * @author    fred
   * @created   27 avril 2002
   */
  public class ZipResource {
    String m_Name;

    /**
     * Constructs a ZipResource object representing the file whose name is passed in.
     *
     * @param p_Name  Name of the file.
     */
    public ZipResource(String p_Name) {
      m_Name = p_Name;
    }

    /**
     * Gets the URL of the file represented by this object.
     *
     * @return                                    The URL.
     * @exception java.net.MalformedURLException  If the URL if malformed.
     */
    public URL getURL() throws java.net.MalformedURLException {
      return new URL("http", null, -1, m_Name);
    }

    /**
     * Opens an input stream from the file represented by this object.
     *
     * @return   The InputStream.
     */
    public InputStream getInputStream() {
      ByteArrayInputStream input = new ByteArrayInputStream(ZipResourceLoader.this.getURLContent(m_Name));
      return input;
    }

    /**
     * Returns the raw data contained in the file represented by this object.
     *
     * @return   The data contained in the file.
     */
    public byte[] getURLContent() {
      return ZipResourceLoader.this.getURLContent(m_Name);
    }
  }

  /**
   * An URLConnection that knows how to load files from a zip archive.
   *
   * @author    fred
   * @created   27 avril 2002
   */
  class ZipURLConnection extends URLConnection {
    /**
     * Constructs a ZipURLConnection from an URL.
     *
     * @param p_Url  The URL connected to.
     */
    public ZipURLConnection(URL p_Url) {
      super(p_Url);
    }

    /**
     * Opens an input stream from the URL host.
     *
     * @return                 The InputStream.
     */
    public InputStream getInputStream() {
      ByteArrayInputStream input = new ByteArrayInputStream((byte[]) resources.get(getURL().getFile()));
      return input;
    }

    /**
     * Opens a connection with the URL host.
     *
     * @exception IOException  If there is an exception while communicating the the URL host.
     */
    public void connect() throws IOException {
      if (resources == null || resources.get(getURL().getFile()) == null) {
        throw new IOException("No data for " + getURL());
      }
    }
  }
}
