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

public class Csci1302_hw3 
{
	private static final int MAX_NUM_RIDERS = 22;  //maximum number of people that can get in line per ride cycle. 22 people every 3 minutes seems reasonable.
	private static final int MAX_RIDERS_EXITED = 20; //maximum number of riders that can go on the ride every cycle. 20*160 = 3200 maximum for the day.
	private static final int CYCLES = 160; //number of cycles for the day. 160 cycles / 8 hours = 20 cycles per hour, or a cycle every 3 minutes. 
	
	public static void main(String args[])
	{
		int numRider, riderExited;   //variables to be used later in the program.
		Riders rider;				 //.
		int riderCounter = 0;		 //.
		double waitTimeAvg = 0;      //.
		int waitTime;			 //.
		int riderCount = 1;			 //.
		
		Random rand = new Random();
		
		RiderLine line = new RiderLine();
		
		/*
		 * for loops that add riders to the line, remove them from the line, and keep track of wait times 
		 * to be used in the average wait time calculation. 
		 */
		for (int cycleCounter = 0; cycleCounter < CYCLES; cycleCounter++)
		{
			numRider = rand.nextInt(MAX_NUM_RIDERS); //randomly decides how many riders will be added to the queue
			for (int innerCounter1 = 0; innerCounter1 < numRider; innerCounter1++)
				{
					if (!line.isFull())  // if the line is not full, a new rider will be added to the queue
					{
						rider = new Riders(riderCount++);
						line.addRider(rider);
						riderCounter++;
						System.out.println(rider + " joins the line");
					}
					else
						System.out.println("line is full.");  //outputs if the line is full, will not add any more riders until some are removed
				}
			
			
			riderExited = rand.nextInt(MAX_RIDERS_EXITED); //randomly decides how many riders will be removed from the queue
			
			
			for (int innerCounter2 = 0; innerCounter2 < riderExited; innerCounter2++)
			{
				if (line.isEmpty())
					System.out.println("Line is empty.");   //outputs if riderExited is attempted and there are no riders currently in line
				else
				{
					waitTime = rand.nextInt(20) + 1;
					waitTimeAvg += waitTime; //keeps track of total wait time
					System.out.println(line.nextRider() + " has exited the queue after " + waitTime + " minutes and gotten on the ride."); //outputs when a rider exits the queue
				}
				}
			
		}
		
		System.out.println("Park is closing . . . "+ line.size() + " Riders left in line."); // outputs when the park is closing and how many riders are left in line
		while(!line.isEmpty()) //while loop to remove riders from the line while queue is not empty
		{
			waitTime = rand.nextInt(20) + 1;
			System.out.println(line.nextRider() + " has exited the queue after " + waitTime + " minutes and gotten on the ride.");
			waitTimeAvg += waitTime; 
		}
		System.out.println ("\n\nTotal number of riders: " + riderCounter);  //outputs the total number of riders 
		System.out.println("Average wait time: " + waitTimeAvg/riderCounter + " minutes."); //outputs the total wait time divided by number of riders for the average wait time
		
	}
}
