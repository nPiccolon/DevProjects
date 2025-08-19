

public class Album 
{
	String title;								// initializing my parameters 
	String artist;
	String genre;
	int length1;
	int length2;
		
	public Album (String title)					// constructor #1, containing only the title field 
	{
		this.title = title;
	}
	
	public Album (String title, String artist, String genre, int length1, int legnth2)			//constructor #2, contains all other parameters
	{																							// i had to use two different length parameters, one with a calculation for minutes and the other for seconds 
		this.title = title;																		 
		this.artist = artist;
		this.genre = genre;
		this.length1 = length1 / 60;															// when the user inputs the length in seconds, this will convert it to minutes 
		this.length2 = length1 % 60;															// when the user inputs the length in seconds, this will output the remainder in seconds
	}
	
	public void setTitle (String title)															//setters for my parameters
	{
		this.title = title;
	}
	
	public void setArtist (String artist)
	{
		this.artist = artist;
	}
	
	public void setGenre (String genre)
	{
		this.genre = genre;
	}
	
	public void setLength (int length1)
	{
		this.length1 = length1;
	}
	
	public void setLength2 (int length2)
	{
		this.length2 = length2;
	}
	
	public String getTitle()																	// my getters for my parameters 
	{
		return title;
	}
			
	public String getArtist()
	{	
		return artist;
	}
			
	public String getGenre()
	{
		return genre;
	}
		
	public int getLength1()
	{
		return length1;
	}
	
	public int getLength2()
	{
		return length2;
	}
	
	public String toString()															// my toString statement, formatting the console and intergrating calls to my parameters. User must input length in seconds twice in the test class.
	{
		return ("title = " + '"' + this.getTitle() + '"' + "," + "    " + "artist = " + '"' + this.getArtist() + '"' 
				+ "," + "    " + "genre = " + '"' + this.getGenre() + '"' + "," + "    " + "length = " + this.getLength1() + " mins, " + this.getLength2() + " secs" + "\n");
					
	}
		
	
}


