
public class MyTest {


	public static void main(String[] args)								//main method
	{
		Book It = new Book("It","Stephen King","horror", 1184);					//examples of books with title, author, genre, and page count
		System.out.println(It.toString());
		
		Book nineteeneightyfour = new Book("1984","George Orwell","dystopian", 328);
		System.out.println(nineteeneightyfour.toString());
		
		Book CatchTwentyTwo = new Book("Catch-22","Joseph Heller","satire", 453);
		System.out.println(CatchTwentyTwo.toString());
	
	/////////////////////////////////////////////////////////
	
		Album Help = new Album("Help!","The Beatles","rock", 2024, 2024);		//examples of albums with title, artist, genre, and length
		System.out.println(Help.toString());
		
		Album TheWall = new Album("The Wall", "Pink Floyd", "prog rock", 4802, 4802);
		System.out.println(TheWall.toString());
		
		Album Pastorale = new Album("Pastorale", "Beethoven", "classical", 2400, 0);
		System.out.println(Pastorale.toString());
		
	/////////////////////////////////////////////////////////////
		
		Movie Brazil = new Movie("Brazil", "Terry Gilliam", "dystopian", 143);			// examples of movies with title, director, genre, and length
		System.out.println (Brazil.toString());
		
		Movie StarWars = new Movie("Star Wars", "George Lucas", "sci-fi", 121);
		System.out.println (StarWars.toString());
		
		Movie Clue = new Movie("Clue", "Jonathan Lynn", "comedy", 96);
		System.out.println (Clue.toString());
	
	
	}

}
