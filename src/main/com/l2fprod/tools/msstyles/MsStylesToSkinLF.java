package com.l2fprod.tools.msstyles;

import com.l2fprod.tools.ThemeConverter;
import com.l2fprod.tools.ImageUtils;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class MsStylesToSkinLF extends ThemeConverter {
  
  private Document msstyle;
  private String   msstyleDirectory;
  private String   msstyleFile;
  private Element  lastElement;
  
  public MsStylesToSkinLF() {
    super("MS{", "}");
  }
  
  public void setMsStyleDirectory(String directory) {
    msstyleDirectory = directory;
  }

  public String getMsStyleDirectory() {
    return msstyleDirectory;
  }

  public void setMsStyleXML(String file) throws Exception {
    msstyleFile = file;

    // load the xml
    DocumentBuilderFactory factory =
      DocumentBuilderFactory.newInstance();
    DocumentBuilder builder =
      factory.newDocumentBuilder();
    msstyle = builder.parse(new FileInputStream(file));
  }

  public void process() throws Exception {
    super.process();

    System.out.println("Fixing Window borders");
    postProcessWindowDecorations();
  }

  /**
   * Locate the element. if xpath is absolute, update the lastElement
   * property else find a node relative to lastElement but do not
   * update it.
   *
   * @param xpath a <code>String</code> value
   * @return an <code>Element</code> value
   * @exception Exception if an error occurs
   */
  private Element locateElement(String xpath) throws Exception {
    if (xpath.startsWith("//")) {
      lastElement =
        (Element)XPathAPI.selectSingleNode(msstyle.getDocumentElement(),
                                           xpath);
      return lastElement;
    } else {
      return (Element)XPathAPI.selectSingleNode(lastElement, xpath);
    }
  }

  private String getPropertyValue(Element part, String property)
    throws Exception {

    Element element =
      (Element)XPathAPI.
      selectSingleNode(part, "Property[@Name='" + property + "']");
    if (element == null) {
      return null;
    } else {
      return element.getAttribute("Value");
    }
  }

  protected String handleProperty(String path) throws Exception {
    Element element = locateElement(path);
    return element.getAttribute("Value");
  }

  protected String handleImage(String path,
                               int index, int count,
                               String outputname) throws Exception {
    Element element = locateElement(path);
    if (element == null) {
      throw new Exception("Node " + path + " not found");
    }

    // how are the chunk organized? vertical or horizontal
    boolean vertical = true;

    String imageFile = null;
    if ("Property".equalsIgnoreCase(element.getTagName())) {
      imageFile = element.getAttribute("Value");
    } else if ("Part".equalsIgnoreCase(element.getTagName()) ||
               "Class".equalsIgnoreCase(element.getTagName())) {

      // may happen if image is available is several DPI
      imageFile = getPropertyValue(element, "ImageFile1");
      if (imageFile == null) {
        imageFile = getPropertyValue(element, "ImageFile");
      }

      String layout = getPropertyValue(element, "ImageLayout");
      if (layout == null) {
        layout = getPropertyValue(element, "Imagelayout");
      }
      
      vertical = "vertical".equalsIgnoreCase(layout);

      String countProperty = getPropertyValue(element, "ImageCount");
      if (countProperty != null) {
        count = Integer.parseInt(countProperty);
      }
    }

    if (imageFile == null || "".equals(imageFile)) {
      throw new Exception("No value found for " + path + " (" + imageFile + ")");
    }

    //    System.out.println("element name = " + element.getTagName());
    //    System.out.println("imageFile = " + imageFile);
    File file = new File(getMsStyleDirectory(), imageFile);
    // if the .BMP is not found, try the .png
    if (file.exists() == false && imageFile.toLowerCase().endsWith(".bmp")) {
      imageFile = imageFile.substring(0, imageFile.length() - 4) + ".png";
      file = new File(getMsStyleDirectory(), imageFile);
      if (!file.exists()) {
        imageFile = imageFile.substring(0, imageFile.length() - 4) + ".gif";        
      }
    }

    ImageUtils.createPicture(getMsStyleDirectory() + File.separator + imageFile,
                             index, count,
                             new File(getCurrentOutputDirectory(), outputname).getAbsolutePath(),
                             vertical);
                             
    return outputname;
  }

  private void postProcessWindowDecorations() throws Exception {
    // get the top selected and move the left and right top corners as
    // single images, then remove the borders from the selected and
    // unselected.
    Image top = ImageUtils.loadPng(new File(getSkinDirectory(), "kde/titlebar_selected.png").getAbsolutePath());
    Image topUnselected = ImageUtils.loadPng(new File(getSkinDirectory(), "kde/titlebar_unselected.png").getAbsolutePath());

    Image left = ImageUtils.loadPng(new File(getSkinDirectory(), "kde/window_left.png").getAbsolutePath());
    Image right = ImageUtils.loadPng(new File(getSkinDirectory(), "kde/window_right.png").getAbsolutePath());

    Image topleft = ImageUtils.grab(top, 0, 0, left.getWidth(null), top.getHeight(null));
    ImageUtils.savePng(topleft, new File(getSkinDirectory(), "kde/window_topleft.png").getAbsolutePath());
    
    Image topright = ImageUtils.grab(top, top.getWidth(null) - right.getWidth(null), 0,
                                     right.getWidth(null), top.getHeight(null));
    ImageUtils.savePng(topright, new File(getSkinDirectory(), "kde/window_topright.png").getAbsolutePath());

    // rewrite the top selected
    top = ImageUtils.grab(top, left.getWidth(null), 0,
                          top.getWidth(null) - left.getWidth(null) - right.getWidth(null),
                          top.getHeight(null));
    ImageUtils.savePng(top, new File(getSkinDirectory(), "kde/titlebar_selected.png").getAbsolutePath());

    // and unselected
    topUnselected = ImageUtils.grab(topUnselected, left.getWidth(null), 0,
                                    top.getWidth(null) - left.getWidth(null) - right.getWidth(null),
                                    top.getHeight(null));
    ImageUtils.savePng(topUnselected, new File(getSkinDirectory(), "kde/titlebar_unselected.png").getAbsolutePath());

    // same process with the bottom image
    Image bottom = ImageUtils.loadPng(new File(getSkinDirectory(), "kde/window_bottom.png").getAbsolutePath());

    Image bottomLeft = ImageUtils.grab(bottom, 0, 0, left.getWidth(null), bottom.getHeight(null));
    ImageUtils.savePng(bottomLeft, new File(getSkinDirectory(), "kde/window_bottomleft.png").getAbsolutePath());

    Image bottomRight = ImageUtils.grab(bottom, bottom.getWidth(null) - right.getWidth(null),
                                        0, right.getWidth(null), bottom.getHeight(null));
    ImageUtils.savePng(bottomRight, new File(getSkinDirectory(), "kde/window_bottomright.png").getAbsolutePath());

    // rewrite the bottom
    // rewrite the top selected
    bottom = ImageUtils.grab(bottom, left.getWidth(null), 0,
                             bottom.getWidth(null) - left.getWidth(null) - right.getWidth(null),
                             bottom.getHeight(null));
    ImageUtils.savePng(bottom, new File(getSkinDirectory(), "kde/window_bottom.png").getAbsolutePath());
  }

  protected String paramString() {
    return super.paramString() +
      ",msstyleDirectory=" + msstyleDirectory +
      ",msstyleFile=" + msstyleFile;
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 3) {
      System.err.println("usage: msstylestoskinlf <styledirectory> <style.xml> <skinfolder>");
      System.exit(1);
    }

    String msstyleDir = args[0];
    String msstyles   = args[1];
    String out        = args[2];

    File outDir = new File(out);
    outDir.mkdirs();

    MsStylesToSkinLF converter = new MsStylesToSkinLF();
    converter.setMsStyleDirectory(msstyleDir);
    converter.setMsStyleXML(msstyles);
    converter.setSkinDirectory(outDir);

    System.out.println(converter.toString());

    converter.process();

    // awt thread may prevent to exit
    System.exit(0);
  }

}
