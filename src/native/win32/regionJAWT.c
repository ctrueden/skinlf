#include <jawt.h>
#include <jawt_md.h>
#include <assert.h>
#include "region.c"

JNIEXPORT jint JNICALL Java_com_l2fprod_gui_nativeskin_win32_Win32NativeSkin_getHWND0
(JNIEnv *env, jclass clas, jobject window) {
  JAWT awt;
  JAWT_DrawingSurface* ds;
  JAWT_DrawingSurfaceInfo* dsi;
  JAWT_Win32DrawingSurfaceInfo* dsi_win;
  jboolean result;
  jint lock;
  HWND hwnd;

  // Get the AWT
  awt.version = JAWT_VERSION_1_3;
  result = JAWT_GetAWT(env, &awt);
  assert(result != JNI_FALSE);
 
  // Get the drawing surface
  ds = awt.GetDrawingSurface(env, window);
  assert(ds != NULL);
  
  // Lock the drawing surface
  lock = ds->Lock(ds);
  assert((lock & JAWT_LOCK_ERROR) == 0);
 
  // Get the drawing surface info
  dsi = ds->GetDrawingSurfaceInfo(ds);
 
  // Get the platform-specific drawing info
  dsi_win = (JAWT_Win32DrawingSurfaceInfo*)dsi->platformInfo;
 
  hwnd = dsi_win->hwnd;

  // Free the drawing surface info
  ds->FreeDrawingSurfaceInfo(dsi);
  
  // Unlock the drawing surface
  ds->Unlock(ds);
  
  // Free the drawing surface
  awt.FreeDrawingSurface(ds);

  return hwnd;
}
