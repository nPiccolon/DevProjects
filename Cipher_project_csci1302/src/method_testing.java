import java.util.Arrays;
import java.util.Scanner;

public class method_testing 
{
	
	public static void main (String args[]) 
	{
		Scanner scan = new Scanner (System.in);
		String message = "";                            // initializes the message to be used in the messagePrompt method
		int shift_int = 0;								// initializes the shifit_int to be used in the shiftChecker and shiftCipher method 
		String shifted_string = "";						// initializes the shifted_string to be used in the substCipher method
		
		message = messagePrompt(message, scan);			// reassigns the message to the return value of messagePrompt
		shift_int = shiftChecker(shift_int, scan);		// reassigns the shift_int to the return value of shiftChecker
		
		char[][] storage_array = new char[message.length()][3];		//initializes the char[][] storage_array
	  	
		shifted_string = shiftCipher(storage_array, message, shift_int, shifted_string);	//reassigns the shift_string to the return value of shiftCipher
		
		arrayFiller(message, storage_array);										//begin execution order for main
		shiftCipher(storage_array, message, shift_int, shifted_string);
		substCipher(storage_array, shifted_string);
		printResults(storage_array);												// end execution order for main
		
	} //end main
	
	
	/*
	 * This method takes the message string and replaces the initialized message string ("") with the user message.
	 */
	public static String messagePrompt(String message, Scanner scan)    
	{
		System.out.println ("please enter a message");
		message = scan.nextLine();
		return (message);
		
	} // end messagePrompt
	
	
	/*
	 * This method checks to make sure the user inputs a number between 1 and 4 for the shiftCipher. 
	 * It will re-prompt if anything other than an int between 1 and 4 is entered. 
	 * This becomes my shift_int value to be passed to my shiftCipher method.
	 */ 
	public static int shiftChecker(int shift_int, Scanner scan)
	{
		boolean valid_shift = false;
		while (valid_shift == false)
    	{
			System.out.println ("please enter a number 1-4. This is for encrypting your message.");
			String shift_str = scan.nextLine();
			char char_shift = shift_str.charAt(0);
   
    		if (Character.isDigit(char_shift)) {
    			shift_int = Integer.parseInt(String.valueOf(char_shift));
    			if (shift_int >= 1 && shift_int <= 4)
    				valid_shift = true;
    				}							
    	} // end while loop
	return (shift_int);
	} //end shift checker
	
	
	/*
	 * This method will loop through the string message and fill the storage_array with each character in the message, in the [X][0] slot. 
	 * This also includes my allowed punctuation filter. 
	 * If anything other than a letter, digit, or allowed punctuation is entered, it will not be added to the array and will be ignored.
	 */
	public static void arrayFiller (String message, char[][]storage_array)                        
	{
		for (int array_filler = 0; array_filler <= (message.length() - 1); array_filler ++)     
		{
			char curr_char = message.charAt(array_filler);
		
			boolean allowedPunctuation = false;                                                  
	        allowedPunctuation =
	            (curr_char == (' ') ||
	             curr_char == (',') ||
	             curr_char == ('!') ||
	             curr_char == ('?') ||
	             curr_char == ('.'));

	        boolean is_letter = Character.isLetter(curr_char);
	        boolean is_digit = Character.isDigit(curr_char);
	       
				if (allowedPunctuation || is_letter || is_digit)
				{
					storage_array[array_filler][0] = curr_char;
				} // end checked characters and stores in storage_array
			} //end for loop, checks for valid characters and puts them into an array
	} //end arrayFiller method
	
	
	
	/*
	 * This is the shiftCipher method. It will traverse through the char array and convert each char into the ascii int value, 
	 * add the shift_int value to the ascii value for each character, then converts the shifted ascii_value back into a char.
	 * It will then store those characters into slot [X][1] in the storage_array.
	 * It will also store those shifted characters in a string to be passed to the substCipher method.
	 */
	
	public static String shiftCipher (char[][]storage_array, String message, int shift_int, String shifted_string)
	{
		for (int array_filler = 0; array_filler <= (message.length() - 1); array_filler ++)     
		{
			char curr_char = message.charAt(array_filler);			//targets the character to be altered 
			int ascii_value = (int) curr_char;						//assigns the targeted character to the ascii value
			int shifted_value = (ascii_value + shift_int);			//adds the shift_int value from shiftChecker method to the ascii value
			char shifted_char = (char) shifted_value;				//converts the shifted ascii value back to a character
			
			String shifted_char_string = Character.toString(shifted_char);		//builds a string of shifted characters to be used in substCipher
			shifted_string = shifted_string + shifted_char_string;				//assigns shifted_char_string to the next position in shifted_string
			
			storage_array[array_filler][1] = shifted_char;						//stores shifted_char in the array
			} //end for loop
		return shifted_string;
	} //end shiftCipher method
	

	
	/*
	 * This is the substCipher method. It will take the shifted_string (assigned in shiftCipher method) and traverse through the characters.
	 * It will then check each character against a series of switch cases until it finds one that matches the curr_char, and assign the 
	 * substituted character to slot [X][2] in the storage_array. It will loop until all characters in the shifted_string have been assigned.
	 * 
	 * KEY: "all work and no play makes Johnny a dull boy.Heres Johnny!-Wendy Im home!THE TWINS.
	 */
	
	public static void substCipher (char[][]storage_array, String shifted_string)
	{
		for (int array_filler = 0; array_filler <= (shifted_string.length() - 1); array_filler ++)  //for loop to traverse through shifted_string
		{
			char curr_char = shifted_string.charAt(array_filler);
			
			switch (curr_char)
			{
			case 'B': curr_char = 'a';
				break;
			case 'C' : curr_char = 'l';
				break;
			case 'D' : curr_char = 'l';
				break;
			case 'E' : curr_char = ' ';
				break;
			case 'F' : curr_char = 'w';
				break;
			case 'G' : curr_char = 'o';
				break;
			case 'H' : curr_char = 'r';
				break;
			case 'I' : curr_char = 'k';
				break;
			case 'J' : curr_char = ' ';
				break;
			case 'K' : curr_char = 'a';
				break;
			case 'L' : curr_char = 'n';
				break;
			case 'M' : curr_char = 'd';
				break;
			case 'N' : curr_char = ' ';
				break;
			case 'O' : curr_char = 'n';
				break;
			case 'P' : curr_char = 'o';
				break;
			case 'Q' : curr_char = ' ';
				break;
			case 'R' : curr_char = 'p';
				break;
			case 'S' : curr_char = 'l';
				break;
			case 'T' : curr_char = 'a';
				break;
			case 'U' : curr_char = 'y';
				break;
			case 'V' : curr_char = ' ';
				break;
			case 'W' : curr_char = 'm';
				break;
			case 'X' : curr_char = 'a';
				break;
			case 'Y' : curr_char = 'k';
				break;
			case 'Z' : curr_char = 'e';
				break;
			case '[' : curr_char = 's';
				break;
			case '\\' : curr_char = 'J';
				break;
			case ']' : curr_char = 'o';
				break;
			case '^' : curr_char = 'h';
				break;
			case 'b': curr_char = 'n';
				break;
			case 'c' : curr_char = 'n';
				break;
			case 'd' : curr_char = 'y';
				break;
			case 'e' : curr_char = ' ';
				break;
			case 'f' : curr_char = 'a';
				break;
			case 'g' : curr_char = ' ';
				break;
			case 'h' : curr_char = 'd';
				break;
			case 'i' : curr_char = 'u';
				break;
			case 'j' : curr_char = 'l';
				break;
			case 'k' : curr_char = 'l';
				break;
			case 'l' : curr_char = ' ';
				break;
			case 'm' : curr_char = 'b';
				break;
			case 'n' : curr_char = 'o';
				break;
			case 'o' : curr_char = 'y';
				break;
			case 'p' : curr_char = '.';
				break;
			case 'q' : curr_char = 'H';
				break;
			case 'r' : curr_char = 'e';
				break;
			case 's' : curr_char = 'r';
				break;
			case 't' : curr_char = 'e';
				break;
			case 'u' : curr_char = 's';
				break;
			case 'v' : curr_char = ' ';
				break;
			case 'w' : curr_char = 'J';
				break;
			case 'x' : curr_char = 'o';
				break;
			case 'y' : curr_char = 'h';
				break;
			case 'z' : curr_char = 'n';
				break;
			case '{' : curr_char = 'n';
				break;
			case '|' : curr_char = 'y';
				break;
			case '}' : curr_char = '!';
				break;
			case '!' : curr_char = '-';
				break;
			case '~' : curr_char = 'W';
				break;
			case '1' : curr_char = 'e';
				break;
			case '2' : curr_char = 'n';
				break;
			case '3' : curr_char = 'd';
				break;
			case '4' : curr_char = 'y';
				break;
			case '5' : curr_char = ' ';
				break;
			case '6' : curr_char = 'I';
				break;
			case '7' : curr_char = 'm';
				break;
			case '8' : curr_char = ' ';
				break;
			case '9' : curr_char = 'h';
				break;
			case ':' : curr_char = 'o';
				break;
			case ';' : curr_char = 'm';
				break;
			case '<' : curr_char = 'e';
				break;
			case '=' : curr_char = '!';
				break;
			case '"' : curr_char = 'T';
				break;
			case '#' : curr_char = 'H';
				break;
			case '$' : curr_char = 'E';
				break;
			case '%' : curr_char = ' ';
				break;
			case '-' : curr_char = 'T';
				break;
			case ',' : curr_char = 'W';
				break;
			case '/' : curr_char = 'I';
				break;
			case '0' : curr_char = 'N';
				break;
			case '@' : curr_char = 'S';
				break;
			case 'A' : curr_char = '.';
				break;
			} // end switch
		storage_array[array_filler][2] = curr_char;   // fills the array in slot [X][2] with substituted characters.
		} // end for loop
	} // end substCipher
	
	
	/*
	 * This method traverses through the storage_array with two for loops and prints the results on their own line.
	 */
	
	public static void printResults (char[][] storage_array)    
	{
		
		for (int row = 0; row < storage_array[0].length; row ++)
		{
			for (int column = 0; column < storage_array.length; column ++)
			{
				System.out.print (storage_array[column][row]);
			}
			System.out.println();
		}
	}
	
	
	
} //end class 


