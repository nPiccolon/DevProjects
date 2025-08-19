
public class Soldier implements Comparable <Soldier>

{
	private String rank;
	private String firstName;
	private String lastName;
	private String yearsInService;
	
	public Soldier(String ranking, String fName, String lName, String years)
	{
		this.rank = ranking;
		this.firstName = fName;
		this.lastName = lName;
		this.yearsInService = years;	
	}
	
	/*
	 * toString method for Soldier object.
	 */
	
	public String toString()             
	{
		return ("Name: " + firstName + " " + lastName + " --- Rank: " + rank);
	}
	
	
	/*
	 * "equals" method, used in the binarySearch method in SortSearch class
	 */	
	public boolean equals (Object other)
	{
		return(lastName.equals(((Soldier)other).getLastName()) && rank.equals(((Soldier)other).getRank()));
	}
	
	
	/*
	 * compareTo method, used in both the selectionSort method 
	 * and binarySearch method in SortSearch class
	 */
	public int compareTo(Soldier other)
	{
		int result;
		
		if(rank.equals(other.getRank()))                         
			result = lastName.compareTo(other.getLastName());	//compares rank and last name. If ranks are equal, last names are also used for sort instead. 
		  else 
			result = rank.compareTo(other.getRank());
		  
	    return result;
	}
		
	/*
	 * getters and setters for Soldier object
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public String getRank()
	{
		return this.rank;
	}
	
	public String getYears()
	{
		return this.yearsInService;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName; 
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public void setRank(String rank)
	{
		this.rank = rank;
	}
	
	public void setYears(String years)
	{
		this.yearsInService = years;
	}
	
} //end class
