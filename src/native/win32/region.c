#include <jni.h>
#include <windows.h>
#include <assert.h>
#include "com_l2fprod_gui_nativeskin_win32_Win32NativeSkin.h"

/**
 * 
 * Created on 29/11/2000 by Frederic Lavigne, fred@L2FProd.com
 *
 * @author $Author: l2fprod $
 * @author Herve Lemaitre (setWindowImageRegion0)
 *
 * @version $Revision: 1.1 $, $Date: 2003-08-01 20:18:11 $
 */


JNIEXPORT void JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_setAlwaysOnTop0
(JNIEnv *env, jclass clas, jint window, jboolean enable) {
  HWND hWnd = (HWND)window;

  if (enable == JNI_TRUE) {
    SetWindowPos(hWnd, HWND_TOPMOST, NULL, NULL, NULL, NULL,
		 SWP_NOMOVE | SWP_NOSIZE);
  } else {
    SetWindowPos(hWnd, HWND_NOTOPMOST, NULL,NULL, NULL, NULL,
		 SWP_NOMOVE | SWP_NOSIZE);
  }
}

typedef BOOL (WINAPI* lpfnSetLayeredWindowAttributes)(HWND hWnd, COLORREF crKey, BYTE bAlpha, DWORD dwFlags);

JNIEXPORT void JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_setWindowTransparency0
(JNIEnv *env, jclass clas, jint window, jint transparency) {
  HWND hWnd = (HWND)window;
  HANDLE hDll;
  lpfnSetLayeredWindowAttributes pFn = NULL;

  // before:
  //  SetLayeredWindowAttributes(hWnd, RGB(0,0,0), transparency, LWA_ALPHA);
  //  but it could fail on non-Windows 2000 platform
  // now:
  //  we use dynamic invocation, this way it should work on Window 98

  hDll = LoadLibrary("USER32.dll");
  if (!hDll) {
    // No user32.dll
    return;
  }

  pFn = (lpfnSetLayeredWindowAttributes)GetProcAddress(hDll, "SetLayeredWindowAttributes");
  if (pFn) {
    SetWindowLong(hWnd, GWL_EXSTYLE,
		  GetWindowLong(hWnd, GWL_EXSTYLE) & ~WS_EX_LAYERED);
    // Ask the window and its children to repaint
    //  RedrawWindow(hWnd, NULL, NULL, RDW_INVALIDATE | RDW_FRAME |
    //	       RDW_ALLCHILDREN);
    
    SetWindowLong(hWnd, GWL_EXSTYLE,
		  GetWindowLong(hWnd, GWL_EXSTYLE) | WS_EX_LAYERED);
    
    pFn(hWnd, RGB(0,0,0), transparency, LWA_ALPHA);
  }

  if (hDll) FreeLibrary(hDll);
  hDll = NULL;
}

JNIEXPORT jlong JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_createEllipticRegion0
(JNIEnv *env, jclass clas, jint x1, jint y1, jint x2, jint y2) {
  return (long)CreateEllipticRgn(x1,y1,x2,y2);
}

JNIEXPORT jlong JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_createRectangleRegion0
(JNIEnv *env, jclass clas, jint x1, jint y1, jint x2, jint y2) {
  return (long)CreateRectRgn(x1,y1,x2,y2);
}

JNIEXPORT jlong JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_createRoundRectangleRegion0
(JNIEnv *env, jclass clas, jint x1, jint y1, jint x2, jint y2, jint x3, jint y3) {
  return (long)CreateRoundRectRgn(x1,y1,x2,y2,x3,y3);
}

JNIEXPORT jlong JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_createPolygonRegion0
(JNIEnv *env, jclass clas, jintArray xpoints, jintArray ypoints, jint fillMode) {
  int c = (*env)->GetArrayLength(env, xpoints);
  int i;
  POINT *lppt;
  POINT *tmp;
  jboolean isCopy;
  jint *arrayX = (jint*)(*env)->GetIntArrayElements(env, xpoints, &isCopy);
  jint *arrayY = (jint*)(*env)->GetIntArrayElements(env, ypoints, &isCopy);

  lppt = (POINT*)malloc(sizeof(POINT) * c);
    
  tmp = lppt;

  for (i = 0; i < c; i++) {
    tmp->x = *arrayX;
    tmp->y = *arrayY;
    tmp++;
    arrayX++;
    arrayY++;
  }

  return (long)CreatePolygonRgn(lppt, c, fillMode);
}

JNIEXPORT jlong JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_combineRegions0
(JNIEnv *env, jclass clas, jlong region1, jlong region2, jint mode) {
  (long)CombineRgn((HRGN)region1, (HRGN)region1, (HRGN)region2, mode);
  return region1;
}

JNIEXPORT void JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_setWindowRegion0
(JNIEnv *env, jclass clas, jint hwnd, jlong region, jboolean redraw) {
  SetWindowRgn((HWND)hwnd, (HRGN)region, redraw);
}

JNIEXPORT jlong JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_createRegion0
(JNIEnv *env, jclass clas, jintArray jpixels, jint width, jint height) {
    HRGN hRgnTemp = NULL;
    HRGN hRgnOut = NULL;

    int* pixels;
    int x, y, i;

    int firstx;
    int pixelCount;  
    
    pixelCount = (*env)->GetArrayLength(env, jpixels);
    
    pixels = (int*)(*env)->GetIntArrayElements(env, jpixels, 0);

    hRgnOut = CreateRectRgn(0, 0, 0, 0);

    // construct a region containing all non transparent pixels
    i = 0;

    // a temp region
    hRgnTemp = CreateRectRgn(0, 0, 0, 0);

    /* Old algo with a bug inside
    for (y = 0; y < height; y++) {
        for (x = 0; x < width; x++, i++) {
           
            // reset the temp region
            SetRectRgn(hRgnTemp, 0, 0, 0, 0);

            firstx = x;

            // go to the end of the line while we have transparent pixels                                
            while ((((pixels[i] >> 24) & 0xff) != 0) && (x < width) ) {
                x++ ; i++;
            }

            if (x > firstx) {
                SetRectRgn(hRgnTemp, firstx, y, x+1, y+1);
                CombineRgn(hRgnOut, hRgnOut, hRgnTemp, RGN_OR);
            }
        }
    }
    */
               
    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++, i++) {
        
        firstx = x;
        
        while ((x < width) && (((pixels[i] >> 24) & 0xff) != 0)) {
          x++;
          i++;
        }
        
        if (x > firstx) {
          SetRectRgn(hRgnTemp, firstx, y, x + 1, y + 1);
          CombineRgn(hRgnOut, hRgnOut, hRgnTemp, RGN_OR);
          if (x >= width) {
            // i may have been incremented too much
            // if we reached the end of the line
            // so decrement it
            i--;
          }
        }
      }
    }

    /* Here is the standard algorithm which is slow
    for (y = 0; y < height; y++) {
      for (x = 0; x < width; x++) {
        if (((pixels[i] >> 24) & 0xff) != 0) {
          // remove transparent color from region
          hRgnTemp = CreateRectRgn(x, y, x+1, y+1);
          CombineRgn(hRgnOut, hRgnOut, hRgnTemp, RGN_XOR);
          DeleteObject(hRgnTemp);
        }
        i++;
      }
    }
    */
    
    DeleteObject(hRgnTemp);

    (*env)->ReleaseIntArrayElements(env, jpixels, pixels, JNI_ABORT);

    return (long)hRgnOut;
}
