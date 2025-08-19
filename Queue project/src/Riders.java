/*
 * Csci1302_hw3		
 * Nick Piccolo
 * Created 11/25/20
 * 
 * This program creates a simulated queue for a roller coaster in an amusement park. It will complete 20 cycles per hour, or 160 cycles 
 * over the course of the whole day, simulating a realistic ride-time of about 3 minutes. This assumes the ride can hold a realistic maximum of 
 * 20 riders per cycle. Since the queue maximum was not specified, nor was the number of riders entering the queue, I assigned the number of riders 
 * entering the queue to be slightly larger than the 400 riders per hour that the ride can handle. This is to simulate a new roller being more popular. 
 * The queue maximum is set to 50 riders, since the line would be longer than a single ride could handle. This insures that the line is rarely empty, 
 * and should only fill up on fairly rare occasions. 
 */

import java.util.Random;

public class Riders 
{
	 Random rand = new Random();
	 
	 //this is the randomly generated riderNames array, to be used by the rider object. 
	 String[] riderNames = {"Mary", "Beth", "Alexa", "Daniella", "Connie", "Hannah",
			 				"Maddie", "Jasmine", "Emily", "Courtney", "Nadia", "Bella",
							"Lee", "Alica", "Larry", "Annie", "Robin", "Lawrence", 
							"Rodney", "Jody", "Lucas", "Candice", "Cornelius", "Pam",
							"Regina", "Eddie", "Elizabeth", "Lindsay", "Craig", "Kyle",
							"Perry", "Katie", "Travis", "Elena", "Alyssa", "Jenny", 
							"Ricky", "Hugo", "Phillip", "Paul", "Corey", "Kurt", 
						    "Ramona", "Roxanne", "Simon", "Rachael", "Garry", "Jay",
					   	    "Taylor", "Robert"};
	
	int index = rand.nextInt(riderNames.length); //randomly selects an array position to be used for a rider
	private String riderName = (riderNames[index]); //sets the name of the rider based on the randomly selected array position
	
	private int timeInLine = rand.nextInt(20)+1; //randomly generated wait time 
	
	private int id;
	
	//Rider object
	public Riders (int number)
	{
		id = number;
	}
	//getter method for wait time
	public int getWait()
	{
		return timeInLine;
	}
	//setter method for wait time
	public void setWait(int time)
	{
		this.timeInLine = time;
	}
	//getter method for rider name 
	public String getName()
	{
		return riderName;
	}
	//setter method for rider name
	public void setName(String newName)
	{
		this.riderName = newName;
	}
	
	
	
	//toString method that assigns the rider a random name and sequenced ID number
	public String toString()
	{
		return "Rider #" + id + ": " + this.riderName + "-";
	}
}
