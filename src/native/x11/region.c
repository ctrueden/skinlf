#include <stdio.h>
#include "com_l2fprod_gui_nativeskin_x11_X11NativeSkin.h"

#include <X11/Xlib.h>
#include <X11/extensions/shape.h>

/*
 * Takes an array of pixels, build a pixmap of depth 1 using transparent
 * value, and set this pixmap as clip mask on the drawable.
 *
 * Class:     com_l2fprod_gui_nativeskin_x11_X11NativeSkin
 * Method:    setWindowRegion0
 * Signature: (II[III)V
 */
JNIEXPORT void JNICALL Java_com_l2fprod_gui_nativeskin_x11_X11NativeSkin_setWindowRegion0
    (JNIEnv *env, jclass clas, jint display, jint drawable,
     jintArray jpixels, jint width, jint height) {

    Pixmap pixmap_mask = 0;
    char* xpm;
    jint* pixels;
    int x, y, i;
    
    /* in : int array, of size (width * height) */
    /* out: char array, of size ((width/8) +1) * height */
    xpm = (char*)calloc(((width/8) +1) * height, sizeof(char));
    pixels = (jint*)(*env)->GetIntArrayElements(env, jpixels, NULL);

    /* linux is LSBFirst, but use the MSDB algo */

    i = 0;
    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++, i++) {
        if (((pixels[i] >> 24) & 0xff) != 0) {
            xpm[y*(width/8+1) + ((x) >> 3)] |= 1 << (x & 7);
        }
      }
    }
    
    /* create the pixmap mask with a depth of 1 */
    pixmap_mask = XCreatePixmapFromBitmapData
      ((Display *)display,
       drawable,
       xpm,
       width,
       height,
       1, /* fg */
       0, /* bg */
       /* BlackPixel((Display*) display, DefaultScreen((Display*) display)), */
       /* WhitePixel((Display*) display, DefaultScreen((Display*) display)), */
       1); /* depth */

  /* apply the mask to the window */
  XShapeCombineMask ((Display *)display,
                     drawable-2, /* well... */
                     ShapeBounding,
                     0,
                     0,
                     pixmap_mask,
                     ShapeSet);
  
  XFreePixmap((Display *)display, pixmap_mask);
  free(xpm);
  (*env)->ReleaseIntArrayElements(env, jpixels, (jint*)pixels, JNI_ABORT);
}
