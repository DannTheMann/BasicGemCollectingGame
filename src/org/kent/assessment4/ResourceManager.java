package org.kent.assessment4;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * 
 * The resource manager handles all files stored in the
 * .jar with this project - essentially used so it is not
 * required to load an image more than once and not throwing
 * data into the GC.
 * 
 * @author Dan Andrews
 *
 * @version 1
 */
public class ResourceManager {
	
	// Hashmap storing a nick_name for an image as key then the Image object as value
	private HashMap<String, Image> images = new HashMap<String, Image>();
	
	/**
	 * Loading an image from the base resource location.
	 * @param resource The name of resource to find.
	 * @return
	 * @throws ResourceLoadException
	 */
	private Image loadImageFromResource(String resource) throws ResourceLoadException {
		try {

			if (getClass().getClassLoader().getResource(resource) != null) {
				return ImageIO.read(getClass().getClassLoader()
						.getResource(resource));
			} else {
				System.out.println("Failed to load " + resource);
				return ImageIO
						.read(getClass()
								.getClassLoader()
								.getResource(
										"org/kent/assessment4/resources/icon_not_found.png"));
			}
		} catch (IOException ioe) {
			throw new ResourceLoadException(
					"Failed to load base resources, suspending application.");
		}
	}
	
	/**
	 * Loads all default resources into the HashMap.
	 */
	public void loadDefaultResources(){
		
		try {
			images.put("checkbox_unselected", loadImageFromResource("org/kent/assessment4/resources/checkbox_unselected_icon.png"));
			images.put("checkbox_selected", loadImageFromResource("org/kent/assessment4/resources/checkbox_selected_icon.png"));
			images.put("mine_cart_sprite", loadImageFromResource("org/kent/assessment4/resources/mine_cart_sprite.png"));
			images.put("icon_not_found", loadImageFromResource("org/kent/assessment4/resources/icon_not_found.png"));
			images.put("background_not_found", loadImageFromResource("org/kent/assessment4/resources/background_not_found.png"));
			images.put("navy_diamond", loadImageFromResource("org/kent/assessment4/resources/navy_diamond.png"));
			images.put("white_diamond", loadImageFromResource("org/kent/assessment4/resources/white_diamond.png"));
			images.put("bomb", loadImageFromResource("org/kent/assessment4/resources/bomb.png"));
			images.put("plane", loadImageFromResource("org/kent/assessment4/resources/plane.jpg"));
			images.put("plant", loadImageFromResource("org/kent/assessment4/resources/plant.jpg"));
			images.put("game_over", loadImageFromResource("org/kent/assessment4/resources/game_over.png"));
			images.put("highscore_background", loadImageFromResource("org/kent/assessment4/resources/highscore_background.jpg"));
		} catch (ResourceLoadException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns an image from the HashMap based on it's nick_name.
	 * @param imageName The nick_name for this image.
	 * @return The image assigned in the hashmap
	 */
	public Image getImage(String imageName){
		
		Image image = images.get(imageName);
		
		if(image == null){
			if(imageName.split(".")[1].equalsIgnoreCase("png")){
				return images.get("icon_not_found");
			}else{
				return images.get("background_not_found");
			}
		}
		
		return image;
		
	}
	
	

}
