package com.l2fprod.tools;

import java.awt.*;
import java.awt.image.*;

import com.ibm.imageconversion.*;
import com.sun.jimi.core.*;

public class ImageUtils {

  public static Component bitmapCreator = new javax.swing.JLabel();
  
  static BMPDecoder decoder = new BMPDecoder();
  static BMPEncoder encoder = new BMPEncoder();
  
  public static void createPicture(String pathToImage, int index, int maxParts,
                                   String filename, boolean horizontal) {
    try {
      System.out.println("working with " + pathToImage);
      decoder.setInputFilename(pathToImage);
	    decoder.triggerAction();
	    Image image = decoder.getResult();
	    //	    encoder.setInputFilename(filename);

      // if only one image, dump it as it
	    if (index == 0 && maxParts == 1) {
        Jimi.putImage(image, filename);
      } else {
        if (horizontal) {
          int partHeight = image.getHeight(bitmapCreator) / maxParts;
          image = grab(image, 0, partHeight*index, 
                       image.getWidth(bitmapCreator), partHeight);
        } else {
          int partWidth = image.getWidth(bitmapCreator) / maxParts;
          image = grab(image, partWidth * index, 0,
                       partWidth, image.getHeight(bitmapCreator));
        }
        //	    encoder.setInputImage(image);
        //	    encoder.triggerAction();
        Jimi.putImage(image, filename);
      }
    } catch (Exception e) {
      System.out.println("error while working with " + pathToImage);
	    e.printStackTrace();
    }
  }
  
  public static Image grab(Image image, int x, int y, int width, int height) {
    if (width * height < 0)
	    return null;
    
    int[] pixels = new int[width * height];
    PixelGrabber grabber = new PixelGrabber(image, x, y, width, height, pixels, 0, width);
    try {
	    grabber.grabPixels();
    } catch (Exception e) {
	    e.printStackTrace();
    }
    int pixel, alpha, red, green, blue;
    for (int j = 0; j < height; j++) {
	    for (int i = 0; i < width; i++) {
        pixel = pixels[j * width + i];
	    }
    } 
    return bitmapCreator.createImage(new MemoryImageSource(width, height, pixels, 0, width));
  }

}
