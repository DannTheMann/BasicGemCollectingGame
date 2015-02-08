package org.kent.assessment4;

@SuppressWarnings("serial")
/**
 * 
 * ResourceLoadException is thrown when the Resource
 * Manager fails to load resources correctly.
 * 
 * @author Dan Andrews
 * 
 * @version 1
 *
 */
public class ResourceLoadException extends Exception {
	
	public ResourceLoadException(String log){
		super(log);
	}

	
	
}
