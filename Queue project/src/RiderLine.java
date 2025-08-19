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

import java.util.LinkedList;

public class RiderLine 
{
	LinkedList<Riders> queue; //linked list of Riders called "queue"
	
	/*
	 * RiderLine object that holds the linked list
	 */
	public RiderLine()
	{
		queue = new LinkedList<Riders>();
	}
	
	/*
	 * method to remove the first rider from the queue when they get on the ride
	 */
	public Riders nextRider()
	{
		return queue.removeFirst();
	}
	
	/*
	 * method to add a new rider when someone enters the queue
	 */
	public void addRider (Riders person)
	{
		queue.addLast(person);
	}
	
	/*
	 * boolean to determine whether the line is empty or not
	 */
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	/*
	 * boolean to determine whether the line is full or not; also sets the maximum queue size to 40(unspecified in description), 
	 * so that the remainder of the queue can be cleared within a maximum of 2 cycles(6 minutes) once the park has closed.
	 */
	public boolean isFull()
	{
	    return queue != null && queue.size() >= 40;
	}
	
	/*
	 * returns the size of the line. Used when the park has closed to determine how many riders are left in line. 
	 */
	public int size()
	{
		return queue.size();
	}
	
}
