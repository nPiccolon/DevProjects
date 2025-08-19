import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Csci1302_hw2 
{
	static String[] tempArray = new String[0]; //temp array that resets with each soldier added
	static Soldier[] soldiers = new Soldier[31]; //array that holds soldier objects
	static int arraySlot = 0;
	
	public static void main (String args[]) throws IOException
	{	
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("C:/Users/Owner/Desktop/FtBenning.txt"));
			String line = reader.readLine();
			while (line != null) 								// while loop splits and assigns soldier ID variables to correct position in Soldier object
			{ 														
				tempArray = line.split(",");
					
				Soldier soldier = new Soldier(tempArray[0], tempArray[1], tempArray[2], tempArray[3]);
					
						soldiers[arraySlot] = soldier;           //assigns solder Object to soldiers array
						arraySlot = arraySlot + 1;				 //increment soldiers array slot
						
				line = reader.readLine();
		    } //close while loop
		} catch (IOException notFound) 
		{
			
			System.out.println("This file can not be found at the given location.");//print statement if file is not in designated location
			System.exit(0);
		
		}
		
			
		System.out.println("-----------------Soldier List----------------\n"); // list description
		
		for (int loopCounter = 0; loopCounter < soldiers.length; loopCounter++)	//prints soldiers array	
			{
				System.out.println(soldiers[loopCounter]);
			} //close for loop
	
				
	
	System.out.println ("\n-------------Sorted by Rank-------------\n");    // break line
	
		
		SortSearch<Soldier> sortRank = new SortSearch<Soldier>();     //sorts and re-assigns soldiers array
		sortRank.selectionSort(soldiers);
			for(Soldier soldier : soldiers)
				{
					while (soldiers.length > arraySlot)
					{
						soldiers[arraySlot] = soldier;
					}
				
				}
			
			
			
		for (int loopCounter = 0; loopCounter < soldiers.length; loopCounter++)  //prints sorted soldier array
			{
				System.out.println(soldiers[loopCounter]);
			}
		
				
		System.out.println ("\n-------------Searching for Soldier-------------\n");   // break line
			
	
	
		Soldier target, found;
			SortSearch<Soldier> searches = new SortSearch<Soldier>();				// searches by rank and last name - prints last name, rank, and years of service if soldier is found.
		
				target = new Soldier("Sergeant", "", "Jenkins", "");
				found = (Soldier)searches.binarySearch(soldiers, target);
					if (found != null)
						System.out.println("Soldier: " + found.getLastName() + " - Rank: " + found.getRank() + " - Years in Service: " +found.getYears());
					else
						System.out.println("That soldier cannot be found at the base.");
		
	} // end main
	
} // end class
