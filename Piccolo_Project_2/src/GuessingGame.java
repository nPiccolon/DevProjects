import java.util.Random;	// imports random number generation tool
import java.util.Scanner;   // imports scanner so user can guess
public class GuessingGame {

		public static void main (String[] args) {
			
		Random rand         = new Random();            // imports new random number generator assigned to rand
		int secretNumber    = rand.nextInt (1000);     // assigns secretNumber to a randomly generated number 1-1000
		int numberOfGuesses = 0;					   // assigns int: number of guesses to 0 before any guesses are used 
		int pointsEarned    = 100;                     // user begins with 100 points, losing 5 with every guess
		Scanner input       = new Scanner(System.in);  // allows user to input a guess into the system
		int userGuess;								   // declares userGuess as an int
		boolean win         = false;				   // assigns boolean variable with false, unless otherwise stated 
		
			System.out.println ("Welcome to the guessing game!");       // introduction to the guessing game
		
		while (win == false)  {											// starts a while loop, relies on boolean variable being false to continue loop
			
			System.out.print ("Enter a positive number up to 1000: ");  // prompts user to guess a number 1-1000
				userGuess       = input.nextInt();                      // allows user to input a guess after prompted
				numberOfGuesses = ++numberOfGuesses;                    // guess counter: keeps track of guesses, adds 1 per loop
				pointsEarned    = (pointsEarned - 5);                   // keeps track of points, deducts 5 points per guess
			
				if (userGuess == secretNumber) {                        // if guessed correctly, the boolean variable is changed from false to true, thus exiting the loop and skipping the "if else" tree
				win = true;
			} 
				else if (userGuess > secretNumber) {                    // if guessed incorrectly, the "else if" tree is followed and will give the user hints
				System.out.println ("Too high");	
			}
				else if (userGuess < secretNumber) {
				System.out.println ("Too low");
			}
		}
			System.out.println ("You Win!");                                                          // prints on the screen when the correct number is guessed
			System.out.println ("It took you " + numberOfGuesses + " guesses to figure it out.");     // tells the user how many guesses it took to get the correct number
			System.out.println ("You earned " + pointsEarned + " points.");							  // tells the user how many points they earned, losing 5 per guess
			System.out.println ("");                                                                  // break in text
			System.out.println ("Goodbye.");                                                          // Goodbye :)
			}
		
		
		}

