package com.l2fprod.gui.plaf.skin;

import java.awt.*;
import java.awt.AWTEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.beans.*;
import javax.swing.*;
import javax.swing.plaf.UIResource;

/*
 * Created on May 24, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */


/**
 * @author sebastien
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
class FrameWindow implements com.l2fprod.gui.plaf.skin.Window{

	private JFrame frame = null;
	private JDialog dialog = null;
	private Rectangle restoreBounds = null;

   /**
	* Constructor for the SkinWindowWindow object
	*
	* @param frame  Description of Parameter
	*/
   
   
   public FrameWindow()  {
   	frame = null;
   }
   
   public void setFrame(java.awt.Window argWin)  {
   	if (argWin instanceof JDialog)  {
   		dialog = (JDialog)argWin;
   	}
   	else if (argWin instanceof JFrame)  {
   		
		frame = (JFrame)argWin;
		
		
   	} else
   		frame = null;
   	 	
   }

   /**
	* Sets the Selected attribute of the SkinWindowWindow object
	*
	* @param b                          The new Selected value
	* @exception PropertyVetoException  Description of Exception
	*/
   public void setSelected(boolean b) throws PropertyVetoException {
	 if (frame != null)  {
		frame.show();
			frame.toFront();
	 }
	else if (dialog != null)  {
		frame.show();
		frame.toFront();
	}
	
   }
   
   public java.awt.Window getMainFrame()  {
   	java.awt.Window toreturn =  null;
   	if (frame != null)
   		toreturn = frame;
   	else if (dialog != null)
   		toreturn = dialog;
   	return toreturn;
   }

   /**
	* Sets the Icon attribute of the SkinWindowWindow object
	*
	* @param b                          The new Icon value
	* @exception PropertyVetoException  Description of Exception
	*/
   public void setIcon(boolean b) throws PropertyVetoException {
	 if (frame != null)  {
	 	frame.setState(Frame.ICONIFIED);
	 	//frame.show();
	 }
	 	
	 
	
   }

   /**
	* Sets the Maximum attribute of the SkinWindowWindow object
	*
	* @param b                          The new Maximum value
	* @exception PropertyVetoException  Description of Exception
	*/
   public void setMaximum(boolean b) throws PropertyVetoException {
	 if (frame != null)  {
	 	if (SkinRootPaneUI.getExtendedState(frame) != SkinRootPaneUI.Frame_MAXIMIZED_BOTH)  {
			restoreBounds = frame.getBounds();
			SkinRootPaneUI.setExtendedState(frame, SkinRootPaneUI.Frame_MAXIMIZED_BOTH);
			
	 	}
	 		
	 	else if (restoreBounds != null)
	 		frame.setBounds(restoreBounds);
	 	frame.show();
	 	dispatchEvent(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));
	 }
   }

   /**
	* Sets the Shaded attribute of the SkinWindowWindow object
	*
	* @param b  The new Shaded value
	*/
   public void setShaded(boolean b) {
	// frame.setShaded(b);
   }

   /**
	* Sets the Closed attribute of the SkinWindowWindow object
	*
	* @param b                          The new Closed value
	* @exception PropertyVetoException  Description of Exception
	*/
   public void setClosed(boolean b) throws PropertyVetoException {
	 if (frame != null)
	 	frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
	else if (dialog != null)
		dialog.dispatchEvent(new WindowEvent(dialog,WindowEvent.WINDOW_CLOSING));
   }

   /**
	* Gets the Container attribute of the SkinWindowWindow object
	*
	* @return   The Container value
	*/
   public Container getContainer() {
   	if (frame != null)
	 return frame.getContentPane();
	else if (dialog != null)
	 return dialog.getContentPane();
	return null;
   }

   /**
	* Gets the Selected attribute of the SkinWindowWindow object
	*
	* @return   The Selected value
	*/
   public boolean isSelected() {
	boolean toreturn = true;
	if (frame != null)
	 toreturn = frame.isShowing();
	else if (dialog != null)
	toreturn = dialog.isShowing();
	return toreturn;
   }

   /**
	* Gets the Icon attribute of the SkinWindowWindow object
	*
	* @return   The Icon value
	*/
   public boolean isIcon() {
	boolean toreturn = false;
	if (frame != null)
		toreturn = frame.getState() == Frame.ICONIFIED;
	return toreturn;
   }

   /**
	* Gets the Maximum attribute of the SkinWindowWindow object
	*
	* @return   The Maximum value
	*/
   public boolean isMaximum() {
	boolean toreturn = false;
	if (frame != null)
		toreturn = SkinRootPaneUI.getExtendedState(frame) == SkinRootPaneUI.Frame_MAXIMIZED_BOTH;
	return toreturn;
   }

   /**
	* Gets the Maximizable attribute of the SkinWindowWindow object
	*
	* @return   The Maximizable value
	*/
   public boolean isMaximizable() {
	 boolean toreturn = false;
	if (frame != null)
		toreturn = frame.isResizable();
	 return toreturn;
   }

   /**
	* Gets the Shaded attribute of the SkinWindowWindow object
	*
	* @return   The Shaded value
	*/
   public boolean isShaded() {
	 return false;
   }

   /**
	* Gets the Iconifiable attribute of the SkinWindowWindow object
	*
	* @return   The Iconifiable value
	*/
   public boolean isIconifiable() {
	 boolean toreturn = true;
	if (dialog != null)
		toreturn = false;
	else if (frame != null)
		toreturn = frame.isResizable();
	return toreturn;
   }

   /**
	* Gets the Closable attribute of the SkinWindowWindow object
	*
	* @return   The Closable value
	*/
   public boolean isClosable() {
	 return true;
   }

   /**
	* Gets the Resizable attribute of the SkinWindowWindow object
	*
	* @return   The Resizable value
	*/
   public boolean isResizable() {
	boolean toreturn = false;
	if (frame != null)
		toreturn = frame.isResizable();
	return toreturn;
   }

   /**
	* Gets the Title attribute of the SkinWindowWindow object
	*
	* @return   The Title value
	*/
   public String getTitle() {
	String title = "";
	if (frame != null)
		title = frame.getTitle();
	else if (dialog != null)
		title = dialog.getTitle();
	return title;
   }

   /**
	* Gets the FrameIcon attribute of the SkinWindowWindow object
	*
	* @return   The FrameIcon value
	*/
   public Icon getFrameIcon() {
	Icon toreturn = null;
	
	if (frame != null)  {
		Image frameImage = frame.getIconImage();
		if (frameImage != null)
			toreturn = new ImageIcon(frame.getIconImage());
	}
		
	//cannot set the JDialog corner it already takes it from
	//the parent frame
	 return toreturn;
   }

   /**
	* Adds a feature to the PropertyChangeListener attribute of the
	* SkinWindowWindow object
	*
	* @param listener  The feature to be added to the PropertyChangeListener
	*      attribute
	*/
   public void addPropertyChangeListener(PropertyChangeListener listener) {
	 if (frame != null)
	 frame.addPropertyChangeListener(listener);
	else if (dialog != null)
	 	dialog.addPropertyChangeListener(listener);
   }

   /**
	* Removes a feature to the PropertyChangeListener attribute of the
	* SkinWindowWindow object
	*
	* @param listener  The feature to be added to the PropertyChangeListener
	*      attribute
	*/
   public void removePropertyChangeListener(PropertyChangeListener listener) {
	 if (frame != null)
	 frame.removePropertyChangeListener(listener);
	 else if (dialog != null)
	 	dialog.removePropertyChangeListener(listener);
   }

   /**
	* Description of the Method
	*
	* @param event  Description of Parameter
	*/
   public void dispatchEvent(AWTEvent event) {
	if (frame != null)
	 frame.dispatchEvent(event);
   }
 }

