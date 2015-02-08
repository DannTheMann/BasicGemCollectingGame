package org.kent.assessment4;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * The screenshot handler, handles taking and saving screenshots.
 * This class was going to have a lot more functionality such as viewing your 
 * screenshots, but I never got round to implementing this.
 * 
 * @author Dan Andrews
 * 
 * @version 1
 */
public class ScreenshotHandler {
	
	// The list of screenshots taken
	private ArrayList<File> screenshots;
	// The directory to which screenshots are saved
	private String saveDirectory;
	
	/**
	 * The inital screenshot handler container
	 */
	public ScreenshotHandler(){
		saveDirectory = System.getProperty("user.dir")
				+ File.separator + "IWCTG_cap_";
		screenshots = new ArrayList<File>();
		
		
	}
	
	/**
	 * Takes a screenshot of the current frame.
	 * @param frame The JFrame to which is being screenshot
	 * @return A string message detailing the events
	 */
	public String takeScreenshot(JFrame frame) {

		Rectangle rec = frame.getBounds();
		BufferedImage image = new BufferedImage(rec.width, rec.height,
				BufferedImage.TYPE_INT_ARGB);
		frame.paint(image.getGraphics());

		File screenshot = new File(saveDirectory + date() + ".png");

		try {

			if (screenshot.canWrite()) {
				JOptionPane.showMessageDialog(null,
						"Error: No permission to save screenshot. :( \n"
								+ "Directory: " + screenshot.getAbsolutePath());
				return "Error: Unable to save file due to security exception. :( \n"
						+ "Directory: " + screenshot.getAbsolutePath();
			}

			ImageIO.write(image, "png", screenshot);

			screenshots.add(screenshot);

			return "Screenshot '" + screenshot.getName() + "' saved to "
					+ screenshot.getAbsolutePath() + ".";

		} catch (IOException ioe) {
			ioe.printStackTrace();
			return "Failed to screenshot: " + ioe.toString();
		} catch (SecurityException se) {
			JOptionPane.showMessageDialog(null,
					"Error: Unable to save file due to security exception. :( \n"
							+ "Directory: " + screenshot.getAbsolutePath());
			se.printStackTrace();
			return "Error: Unable to save file due to security exception. :( \n"
					+ "Directory: " + screenshot.getAbsolutePath();
		}

	}
	
	/**
	 * Retrieves the date for this screenshot
	 * @return
	 */
	private String date() {
		   DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		   return dateFormat.format(new Date());
	}
	
	@Deprecated
	/**
	 * Loads a screenshot from the index position,
	 * method was never used.
	 * @param position index
	 * @return Image of the screenshot
	 */
	public Image loadScreenshot(int position){
		
		try{
			return ImageIO.read(screenshots.get(position));
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException aiobe){
			aiobe.printStackTrace();
		}
		
		return Main.resourceManager.getImage("background_not_found");
		
	}

}
