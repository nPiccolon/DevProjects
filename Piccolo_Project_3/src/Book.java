
public class Book                         // Name of the class
{
	String title;						  // initializing parameters 
	String author;
	String genre;
	int pageCount;
	
	public Book (String title)			  // constructor #1, only contains title field
	{
		this.title = title;
	}
	
	public Book (String title, String author, String genre, int pageCount)       // constructor #2, contains all four fields as parameters
	{
		this.title = title;				 
		this.author = author;
		this.genre = genre;
		this.pageCount = pageCount;
	}
		
	public void setTitle (String title)		//setters for my parameters
	{
		this.title = title;
	}
	
	public void setAuthor (String author)
	{
		this.author = author;
	}
	
	public void setGenre (String genre)
	{
		this.genre = genre;
	}
	
	public void setPageCount (int pageCount)
	{
		this.pageCount = pageCount;
	}
	
	public String getTitle()				// getters for all my parameters 
	{										
		return title;
	}
		
	public String getAuthor()
	{	
		return author;
	}
		
	public String getGenre()
	{
		return genre;
	}
		
	public int getPageCount()
	{
		return pageCount;
	}
		
	public String toString()                                      // my toString method, calls my parameters and formatting what will print to the console
	{
		return ("title = " + '"' + this.getTitle() + '"' + "," + "    " + "author = " + '"' + this.getAuthor() + '"' 
				+ "," + "    " + "genre = " + '"' + this.getGenre() + '"' + "," + "    " + "pagecount = " + this.getPageCount() + "\n");
					
	}
		
	
}

