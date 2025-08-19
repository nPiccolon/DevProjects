
public class Movie {													
	String title;														//initializing parameters 
	String director;
	String genre;
	int length;
		
	public Movie (String title)											// constructor #1, contains only title 
	{
		this.title = title;
	}
	
	public Movie (String title, String director, String genre, int length)    	// constructor #2, contains all 4 parameters 
	{
		this.title = title;												 
		this.director = director;
		this.genre = genre;
		this.length = length;
	}
		
	public void setTitle (String title)												//setters for my parameters
	{
		this.title = title;
	}
	
	public void setDirector (String director)
	{
		this.director = director;
	}
	
	public void setGenre (String genre)
	{
		this.genre = genre;
	}
	
	public void setLength (int length)
	{
		this.length = length;
	}
	
	public String getTitle()													// getters for the parameters 
	{
		return title;
	}
		
	public String getDirector()
	{	
		return director;
	}
		
	public String getGenre()
	{
		return genre;
	}
		
	public int getLength()
	{
		return length;
	}
	
	public String toString()													// toString method, formatting the console and calling my parameters
	{
		return ("title = " + '"' + this.getTitle() + '"' + "," + "    " + "director = " + '"' + this.getDirector() + '"' 
				+ "," + "    " + "genre = " + '"' + this.getGenre() + '"' + "," + "    " + "length = " + this.getLength() + " mins" +"\n");
	}
}
