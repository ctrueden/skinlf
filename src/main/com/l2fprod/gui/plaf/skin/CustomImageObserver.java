/*
 * Created on May 25, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.l2fprod.gui.plaf.skin;

import java.awt.Image;
import java.awt.image.ImageObserver;

/**
 * @author sebastien
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CustomImageObserver implements ImageObserver {

	/* (non-Javadoc)
	 * @see java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
	 */
	public boolean imageUpdate(
		Image img,
		int infoflags,
		int x,
		int y,
		int width,
		int height) {
		boolean toreturn = true;
		if (width > 0 && height > 0)
			toreturn = false;
		if (!toreturn) {
			synchronized (this) {
				this.notifyAll();
			}
		}
		return toreturn;

	}

	public CustomImageObserver() {
		super();
	}

	public Object getLock() {
		return this;
	}

}
