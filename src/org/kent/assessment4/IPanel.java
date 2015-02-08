package org.kent.assessment4;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;
/**
 * IPanel is an extension of JPanel, it allows the Frame to
 * wrap an image over the background. 
 * 
 * @author Dan Andrews
 * @version 1.02
 */
@SuppressWarnings("serial")
public class IPanel extends JPanel {

	// The image that is being displayed in the background
	private Image image;

	@Deprecated
	/**
	 * Load a file directly from a source location. Not used as this
	 * in inefficient for different users computers, unless stored via
	 * URL.
	 * @param fileName
	 */
	public IPanel(String fileName) {
		this(Toolkit.getDefaultToolkit().createImage(fileName));
	}

	/**
	 * Creates the panel based on the image supplied
	 * @param image Background image
	 */
	public IPanel(Image image) {
		this.image = image;
	}

	/**
	 *  Defaultly creates the panel, but will have no background.
	 */
	public IPanel() {
		this.image = null;		
	}

	/**
	 * Change the image directly from a source location. Not used as this
	 * in inefficient for different users computers, unless stored via
	 * URL.
	 * @param fileName
	 */
	@Deprecated
	public void changeImageFileName(String fileName) {
		this.image = Toolkit.getDefaultToolkit().createImage(fileName);
	}

	/**
	 * Change the image from the resource manager, using the .jar to handle
	 * images.
	 * @param resource The name of the image to load
	 */
	public void changeImageResource(String resource){		
		this.image = Main.resourceManager.getImage(resource);
	}

	/**
	 * Inherited method, this is the act which updates the Panel to show
	 * a background image.
	 */
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		graphic.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}

	/**
	 * Clears the image by making it null
	 */
	public void clearImage() {
		this.image = null;

	}
	

}
