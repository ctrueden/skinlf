package com.l2fprod.tools.msstyles;

import com.l2fprod.tools.ThemeConverter;
import com.l2fprod.tools.ImageUtils;

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
                               boolean vertical,
                               String outputname) throws Exception {
    Element element = locateElement(path);
    if (element == null) {
      throw new Exception("Node " + path + " not found");
    }

    String imageFile = null;
    if ("Property".equalsIgnoreCase(element.getTagName())) {
      imageFile = element.getAttribute("Value");
    } else if ("Part".equalsIgnoreCase(element.getTagName()) ||
               "Class".equalsIgnoreCase(element.getTagName())) {
      imageFile = getPropertyValue(element, "ImageFile");
      // may happen if image is available is several DPI
      if (null == imageFile) {
         imageFile = getPropertyValue(element, "ImageFile1");
      }
    }

    if (imageFile == null || "".equals(imageFile)) {
      throw new Exception("No value found for " + path + " (" + imageFile + ")");
    }

    //    System.out.println("element name = " + element.getTagName());
    //    System.out.println("imageFile = " + imageFile);

    ImageUtils.createPicture(getMsStyleDirectory() + File.separator + imageFile,
                             index, count,
                             new File(getCurrentOutputDirectory(), outputname).getAbsolutePath(),
                             vertical);
                             
    return outputname;
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
