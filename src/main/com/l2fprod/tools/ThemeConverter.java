package com.l2fprod.tools;

import java.io.LineNumberReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.StringTokenizer;

public abstract class ThemeConverter {

  private final String prefix;
  private final String suffix;
  private File  skinDirectory;
  private File  currentOutputDir;

  protected ThemeConverter(String prefix, String suffix) {
    this.prefix = prefix;
    this.suffix = suffix;
  }

  public void setSkinDirectory(File directory) {
    skinDirectory = directory;
  }

  public void setCurrentOutputDirectory(File directory) {
    currentOutputDir = directory;
  }

  public File getCurrentOutputDirectory() {
    return currentOutputDir;
  }

  public void process() throws Exception {
    // process the gtk template
    System.out.println("Processing GTK template");
    File gtkFolder = new File(skinDirectory, "gtk");
    gtkFolder.mkdirs();

    setCurrentOutputDirectory(gtkFolder);
    processTemplate(getClass().getResourceAsStream("gtkrc.template"),
                    new FileOutputStream(new File(gtkFolder, "gtkrc")));

    // process the kde template
    System.out.println("Processing KDE template");
    File kdeFolder = new File(skinDirectory, "kde");
    kdeFolder.mkdirs();

    setCurrentOutputDirectory(kdeFolder);
    processTemplate(getClass().getResourceAsStream("kde.template"),
                    new FileOutputStream(new File(kdeFolder, "kde.themerc")));

    System.out.println("Writing skinlf-themepack.xml");
    File skin = new File(skinDirectory, "skinlf-themepack.xml");
    PrintWriter out = new PrintWriter(new FileWriter(skin));
    out.println("<?xml version=\"1.0\"?>");
    out.println("<skinlf-themepack require=\"1.2.4\">");
    out.println("<property name=\"JDesktopPane.backgroundEnabled\" value=\"false\" />");
    out.println("<property name=\"PopupMenu.animation\" value=\"false\" />");
    out.println("<skin>");
    out.println("<skin url=\"gtk/gtkrc\"></skin>");
    out.println("<skin url=\"kde/kde.themerc\"></skin>");
    out.println("</skin>");
    out.println("</skinlf-themepack>");
    out.flush();
    out.close();
  }

  protected void processTemplate(InputStream template,
                                 OutputStream output) throws Exception {
    PrintWriter out = new PrintWriter(output);
    LineNumberReader reader = new LineNumberReader(new InputStreamReader(template));
    try {
      String read;
      while ((read = reader.readLine()) != null) {
        out.println(parseLine(read));
      }
    } catch (Exception e) {
      System.err.println("Error at line:" + reader.getLineNumber());
      e.printStackTrace();
    } finally {
      out.flush();
    }
  }

  protected String parseLine(String line) throws Exception {
    int index = line.indexOf(prefix);
    if (index == -1)
	    return line;
    else {
	    int endIndex = line.indexOf(suffix, index + prefix.length());
	    if (endIndex == -1)
        throw new Exception("Tag not closed");
	    String tag = line.substring(index + prefix.length(), endIndex);
	    return line.substring(0, index) +
        parseTag(tag) +
        parseLine(line.substring(endIndex + 1));
    }
  }

  protected String parseTag(String tag) throws Exception {
    // handle Image:pathtoimagefile#index,count|filenametocreate.png|trueorfalse
    if (tag.startsWith("Image:")) {
      StringTokenizer token = new StringTokenizer(tag, ":#,|");
      // skip image
      token.nextToken();
      // path
      String path = token.hasMoreTokens()?token.nextToken():null;
      // index
      int index = token.hasMoreTokens()?Integer.parseInt(token.nextToken()):-1;
      // count
      int count = token.hasMoreTokens()?Integer.parseInt(token.nextToken()):-1;
      // filenametocreate
      String output = token.hasMoreTokens()?token.nextToken():null;
      // how are the chunks organized
      boolean vertical = token.hasMoreTokens()?"true".equals(token.nextToken()):true;

      // we have all parameters
      return handleImage(path, index, count, vertical, output);
    } else if (tag.startsWith("Property:")) {
      StringTokenizer token = new StringTokenizer(tag, ":");
      // skip prop
      token.nextToken();
      // value
      String prop = token.hasMoreTokens()?token.nextToken():null;
      return handleProperty(prop);
    } else {
      System.err.println("Found unknown tag: " + tag);
      return tag;
    }
  }

  protected abstract String handleImage(String path,
                                        int index, int count,
                                        boolean vertical,
                                        String outputname) throws Exception;

  protected abstract String handleProperty(String path) throws Exception;

  public String toString() {
    return getClass().getName() +
      "[" + paramString() + "]";
  }

  protected String paramString() {
    return "prefix=" + prefix +
      ",suffix=" + suffix +
      ",skinDirectory=" + skinDirectory;
  }

}
