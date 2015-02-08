package org.kent.assessment4;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Dan Andrews
 *
 * @version 1
 */
public class Score implements Serializable{
	
	// Serializable ID
	private static final long serialVersionUID = 1641338408250119679L;
	
	// The name of the person with this score
	private String name;
	
	// The score itself
	private int score;
	
	// The time taken
	@Deprecated
	private int time;
	
	// The date the score was achieved on
	private String dateStamp;
	
	/**
	 * Creates a new score
	 * @param name The user with this score
	 * @param score The score itself
	 * @param time Unused variable
	 */
	public Score(String name, int score, int time){		
		this.name = name;
		this.score = score;
		DateFormat date = new SimpleDateFormat("HH:mm:ss - dd_MM_yyyy");
		dateStamp = date.format(new Date());
		
	}
	
	/**
	 * Returns the name of the user with this score
	 * @return username
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns the score itself
	 * @return score
	 */
	public int getScore(){
		return this.score;
	}
	
	/**
	 * Unused method for retrieving the time.
	 * @return
	 */
	@Deprecated
	public int getTime(){
		return this.time;
	}
	
	/**
	 * Returns the date this score was achieved on.
	 * @return date
	 */
	public String getDateStamp(){
		return this.dateStamp;
	}

}
