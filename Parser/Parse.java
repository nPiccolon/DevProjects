/*
 * Nick Piccolo
 * Dr. Salimi
 * CSCI 4200
 * Spring 2023
 * Parser 
 */

//package lexPackage;
 
import java.util.*;
import java.io.*;

public class Parse {
        
    
    static final int MAX_LEXEME_LEN = 100;
	static Token charClass;                            // Compare to enum to identify the character's class
	static int lexLen;                                 // Current lexeme's length
	static char lexeme[] = new char[MAX_LEXEME_LEN];   // Current lexeme's character array
	static char nextChar;
	static Token nextToken;
	static int charIndex;
	static char ch = '0';
	static FileWriter myOutput;
    static Scanner scan;
    
	static boolean isLast = true; //bugfix

    // Tokens for parsing and lexical analysis
    enum Token {
        DIGIT,
        LETTER,
        UNKNOWN,
		ADD_OP,
		DIV_OP,
		END_KEYWORD,
		END_OF_FILE,
		IDENT,
		INT_LIT,
		LEFT_PAREN,
		MULT_OP,
		PRINT_KEYWORD,
		PROGRAM_KEYWORD,
		RIGHT_PAREN,
		SEMICOLON,
		SUB_OP,
		ASSIGN_OP,
	
		//new tokens
		IF_KEYWORD,
		THEN_KEYWORD,
		READ_KEYWORD
    }



    public static void main(String[] args) throws IOException {
        
        //File writer for creating the output text file and string for scanner to place lines from input file
        String line;
	    myOutput = new FileWriter("parseOut.txt");

        System.out.print("Nick Piccolo, CSCI4200, Spring 2023, Parser\n"); 
		myOutput.write("Nick Piccolo, CSCI4200, Spring 2023, Parser\n");
		System.out.print("********************************************************************************\n");
		myOutput.write("********************************************************************************\n");
        

		try {
		    scan = new Scanner(new File("sourceProgram.txt"));
		    while (scan.hasNextLine()) {
		        String inputLine = scan.nextLine(); // Store the value returned by scan.nextLine() in a variable
		        myOutput.write(inputLine + "\n");
		        System.out.println(inputLine + "\n");
		    }
		    myOutput.write("********************\n");
		    System.out.println("********************");
		    scan = new Scanner(new File("sourceProgram.txt"));
		    // For each line, grab each character 
		    while (scan.hasNextLine()) {
		        line = scan.nextLine().trim();
		        charIndex = 0;

		        if (getChar(line)) {
		            // Perform lexical analysis within array bounds 
		            lex(line);
		            program();
		        }
		    }


			// If there are no more lines, it must be the end of file 
			if (!scan.hasNext()) {
				System.out.print("Exit <program>\n");
				myOutput.write("Exit <program>\n");
				System.out.print("Parsing of the program is complete!\n");
				myOutput.write("Parsing of the program is complete!\n");
                System.out.print("********************************************************************************\n");
		        myOutput.write("********************************************************************************\n");

			}
			
			scan.close();
		}
        catch (FileNotFoundException e) {
			System.out.println(e.toString());
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		myOutput.close();
	}
   
    // Program is the start of the parsing process and calls each subsequent method until parsing is complete.
    private static void program() throws IOException {
        if (nextToken == Token.PROGRAM_KEYWORD) {
            System.out.print("Enter <program>\n\n");
            myOutput.write("Enter <program>\n\n");
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                charIndex = 0;
                if (nextToken != Token.END_KEYWORD && getChar(line)) {
                    while (charIndex < line.length()) {
                        lex(line);
                        if (nextToken == Token.PRINT_KEYWORD || nextToken == Token.IDENT || nextToken == Token.READ_KEYWORD ||  nextToken == Token.IF_KEYWORD) {
                            statement(line);
                            
                            // Check for semicolon after a statement
                            if (nextToken != Token.SEMICOLON) {
                                System.out.print("**ERROR** - expected SEMICOLON after a statement\n\n");
                                myOutput.write("**ERROR** - expected SEMICOLON after a statement\n\n");
                            }
                        } else if (nextToken != Token.END_KEYWORD) {
                            System.out.print("**ERROR** - expected identifier or PRINT_KEYWORD\n");
                            myOutput.write("**ERROR** - expected identifier or PRINT_KEYWORD\n");
                        }
                    }
                }
            }
        }
    }


    // expr is called by assign, output or factor and leads to terms
    private static void expr(String ln) throws IOException{
        System.out.print("Enter <expr>\n");
        myOutput.write("Enter <expr>\n");
         term(ln);
        // Checks for addition or subtraction operators and calls term and lex for next token to parse
         while(nextToken == Token.ADD_OP || nextToken == Token.SUB_OP) {
            lex(ln);
            term(ln);
         }
         System.out.print("Exit <expr>\n");
         myOutput.write("Exit <expr>\n");
    }
    //terms produce factors and are called by expr
 
    private static void term(String ln) throws IOException{
        System.out.print("Enter <term>\n");
        myOutput.write("Enter <term>\n");
        // beginning call for factor
        factor(ln);
        // checks for multiplication or division operators and makes a recursive method call for the next factor to multiply or divide by
        while (nextToken == Token.MULT_OP || nextToken == Token.DIV_OP) {
            lex(ln);
            factor(ln);
        }
        System.out.print("Exit <term>\n");
        myOutput.write("Exit <term>\n");
    }
   
    // factor is called by term and is a key part of the parser as this is where the actually values are defined and where expr is looped back through to lengthen equations
    private static void factor(String ln) throws IOException {
        System.out.print("Enter <factor>\n");
        myOutput.write("Enter <factor>\n");
        // if statements run through cases to ascertain what methods will be called. ident/int_lit/expr
        if (nextToken == Token.IDENT || nextToken == Token.INT_LIT) {
            lex(ln);
        }
        else { 
            // Used to check for calling of expression and lex is called twice for left and right parentheses
            if (nextToken == Token.LEFT_PAREN) {
                lex(ln);
                expr(ln);
                lex(ln);        
            }      
            else {
                System.out.print("**ERROR** - expected identifier, integer or left-parenthesis\n");
                myOutput.write("**ERROR** - expected identifier, integer or left-parenthesis\n");
            }
        }   
        System.out.print("Exit <factor>\n");
        myOutput.write("Exit <factor>\n");
    }
 
    // First method called by program
  private static void statement(String ln) throws IOException {
	    // If statements for checking statement type: ASSIGN/OUTPUT/INPUT/SELECTION
	    if (nextToken == Token.IDENT) {
	        assign(ln);
	    } else if (nextToken == Token.PRINT_KEYWORD) {
	        output(ln);
	    } else if (nextToken == Token.READ_KEYWORD) {
	        input(ln);
	    } else if (nextToken == Token.IF_KEYWORD) {
	    	selection(ln);
	    } else if (nextToken != Token.END_KEYWORD) {
	        System.out.print("**ERROR** - expected identifier, PRINT_KEYWORD, READ_KEYWORD or IF_KEYWORD\n");
	        myOutput.write("**ERROR** - expected identifier, PRINT_KEYWORD, READ_KEYWORD, or IF_KEYWORD\n");
	    }
	}

    // used for printing
  private static void output(String line) throws IOException {
	    System.out.print("Enter <output>\n");
	    myOutput.write("Enter <output>\n");

	    if (nextToken == Token.PRINT_KEYWORD) {
	        lex(line);
	        if (nextToken == Token.LEFT_PAREN) {
	            lex(line);
	            expr(line);
	            if (nextToken == Token.RIGHT_PAREN) {
	                // Call lex(line) to get the next token (semicolon)
	                lex(line);
	            } else {
	                System.out.print("**ERROR** - expected RIGHT_PAREN\n");
	                myOutput.write("**ERROR** - expected RIGHT_PAREN\n");
	            }
	        } else {
	            System.out.print("**ERROR** - expected LEFT_PAREN\n");
	            myOutput.write("**ERROR** - expected LEFT_PAREN\n");
	        }
	    }
	    System.out.print("Exit <output>\n\n");
	    myOutput.write("Exit <output>\n\n");
	}

    // Statement calls assign based on if next token is an ident
    private static void assign(String ln) throws IOException {
        System.out.print("Enter <assign>\n");
        myOutput.write("Enter <assign>\n");
        //checks to make sure next token is ident and calls lex
        if (nextToken == Token.IDENT) {
            lex(ln);
            // checks to make sure equals sign is there and then calls lex and expr method
            if (nextToken == Token.ASSIGN_OP) {
                    lex(ln);
                    expr(ln);
            }
            else {
                System.out.print("**ERROR** - expected ASSIGN_OP\n");
                myOutput.write("**ERROR** - expected ASSIGN_OP\n");
            }
        }
        else {
            System.out.print("**ERROR** - expected IDENT\n");
            myOutput.write("**ERROR** - expected IDENT\n");
            }
        if(isLast) {
        	System.out.print("Exit <assign>\n\n");
            myOutput.write("Exit <assign>\n\n");
        } else {
        	System.out.print("Exit <assign>\n");
            myOutput.write("Exit <assign>\n");
            isLast=true;
        }
    }
    
    //new grammar methods 
    private static void input(String ln) throws IOException {
        // Check to make sure the next token is the READ_KEYWORD
        if (nextToken == Token.READ_KEYWORD) {
            System.out.print("Enter <input>\n");
            myOutput.write("Enter <input>\n");
            lex(ln); // Get the next token after the READ_KEYWORD
            
            // Check if the next token is the LEFT_PAREN
            if (nextToken == Token.LEFT_PAREN) {
                lex(ln); // Get the next token after the LEFT_PAREN
                
                // Check if the next token is an IDENT
                if (nextToken == Token.IDENT) {
                    lex(ln); // Get the next token after the IDENT
                  
                    // Check if the next token is the RIGHT_PAREN
                    if (nextToken == Token.RIGHT_PAREN) {
                        lex(ln); // Get the next token after the RIGHT_PAREN
                    } else {
                        System.out.print("**ERROR** - expected RIGHT_PAREN\n");
                        myOutput.write("**ERROR** - expected RIGHT_PAREN\n");
                    }
                } else {
                    System.out.print("**ERROR** - expected IDENT\n");
                    myOutput.write("**ERROR** - expected IDENT\n");
                }
            } else {
                System.out.print("**ERROR** - expected LEFT_PAREN\n");
                myOutput.write("**ERROR** - expected LEFT_PAREN\n");
            }
            System.out.print("Exit <input>\n\n");
            myOutput.write("Exit <input>\n\n");
        }
    }
    
    private static void selection(String ln) throws IOException {
    	if (nextToken == Token.IF_KEYWORD) {
        	isLast = false; //bugfix
    		System.out.print("Enter <selection>\n");
            myOutput.write("Enter <selection>\n");
            lex(ln); // Get the next token after IF_KEYWORD
           
            // Check if the next token is LEFT_PAREN
            if (nextToken == Token.LEFT_PAREN) {
                lex(ln); // Get the next token
                expr(ln);
                
                // Check if the next token is RIGHT_PAREN
                if (nextToken == Token.RIGHT_PAREN) {
                    lex(ln); // Get the next token
                  
                    // Check if the next token is THEN_KEYWORD
                    if (nextToken == Token.THEN_KEYWORD) {
                        lex(ln); // Get the next token
                        statement(ln);
                    } else {
                        System.out.print("**ERROR** - expected THEN_KEYWORD\n");
                        myOutput.write("**ERROR** - expected THEN_KEYWORD\n");
                    }
                } else {
                    System.out.print("**ERROR** - expected right-parenthesis\n");
                    myOutput.write("**ERROR** - expected right-parenthesis\n");
                }
            } else {
                System.out.print("**ERROR** - expected left-parenthesis\n");
                myOutput.write("**ERROR** - expected left-parenthesis\n");
            }
            System.out.print("Exit <selection>\n\n");
            myOutput.write("Exit <selection>\n\n");
        }
    }   
    
    static boolean isKeyWord(String ln) {
		ln = ln.toUpperCase();
		if (ln.contains("PROGRAM")) {
			return true;
		}
		else if (ln.contains("END")) {
			return true;
		}
		else if (ln.contains("PRINT")) {
			return true;
		}
		
		//new keywords	
		else if (ln.contains("IF")) {
			return true;
		}
		else if (ln.contains("THEN")) {
			return true;
		}
		else if (ln.contains("READ")){
			return true;
		}
		else {
			return false;
		}
	}

	 static Token keyWordLookUp(String ln) {
		ln = ln.toUpperCase();
		Token tk = null;
		switch (ln) {

		case "PROGRAM":
			tk = Token.PROGRAM_KEYWORD;
			break;
		case "END":
			tk = Token.END_KEYWORD;
			break;

		case "PRINT":
			tk = Token.PRINT_KEYWORD;
			break;
			
		//new keywords		
		case "IF":
			tk = Token.IF_KEYWORD;
			break;
			
		case "THEN":
			tk = Token.THEN_KEYWORD;
			break;
			
		case "READ":
			tk = Token.READ_KEYWORD;
			break;

		}
		return tk;
	}

	
	/************************************************
	 *  Assign each lexeme with its respective token.
	 * This allows the lexical analyzer to determine
	 * what the Token names connect to.
	 ************************************************/
	 static Token lookup(char ch) {

		switch (ch) {
		
		case '(':
			addChar();
			nextToken = Token.LEFT_PAREN;
			break;

		case ')':
			addChar();
			nextToken = Token.RIGHT_PAREN;
			break;

		case '+':
			addChar();
			nextToken = Token.ADD_OP;
			break;

		case '-':
			addChar();
			nextToken = Token.SUB_OP;
			break;

		case '*':
			addChar();
			nextToken = Token.MULT_OP;
			break;

		case '/':
			addChar();
			nextToken = Token.DIV_OP;
			break;

		case '=':
			addChar();
			nextToken = Token.ASSIGN_OP;
			break;
		case ';':
			addChar();
			nextToken = Token.SEMICOLON;
			break;

	/*
	 * No default case - each lexeme should fall
	 * within one of the categories set above.
	 */
		}

		return nextToken;
	}

	
	/************* addChar - a function to add nextChar to lexeme *************/
	 static boolean addChar() {
		
		if (lexLen <= 98) {
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
			return true;
		}
		
		else {
			System.out.print("Error - lexeme is too long \n");
			return false;
		}
	}

	
	/************* getChar - a function to get the next character in the line *************/
	 static boolean getChar(String ln) {

		if (charIndex >= ln.length()) {
			return false;
		}

		nextChar = ln.charAt(charIndex++);

		if (Character.isDigit(nextChar)) {
			charClass = Token.DIGIT;
		} 
		
		else if (Character.isAlphabetic(nextChar)) {
			charClass = Token.LETTER;
		} 
		
		else {
			charClass = Token.UNKNOWN;
		}

		return true;
	}

	/************* getNonBlank - a method to skip whitespace *************/
	public static boolean getNonBlank(String ln) {
		while (Character.isSpaceChar(nextChar) || nextChar == '	') {
			if (!getChar(ln)){
				return false;
			}
		}
		return true;
	}
	
	/************* lex - a simple lexical analyzer for arithmetic expressions *************/
	public static Token lex(String ln) throws IOException {
		
		lexLen = 0;
		getNonBlank(ln);	
		switch (charClass) {

		// Parse identifiers 
		case LETTER:
			nextToken = Token.IDENT;
			addChar();
			
			if (getChar(ln)) {
				while (charClass == Token.LETTER || charClass == Token.DIGIT) {
					addChar();
					if (!getChar(ln)) {
						break;
					}
				}
				if (charClass == Token.UNKNOWN && charIndex == ln.length()) {
					charIndex--;
				}
			}
			break;
			
		// Parse integer literals 
		case DIGIT:
			nextToken = Token.INT_LIT;
			addChar();
			
			if (getChar(ln)) {
				while (charClass == Token.DIGIT) {
					addChar();
					
					if(!getChar(ln)) {
						break;
					}
				}	
				if (charClass == Token.UNKNOWN && charIndex == ln.length()) {
					charIndex--;
				}
			}
			break;
			
		 // Parentheses and operators 
		case UNKNOWN:
			lookup(nextChar);
			getChar(ln);
			break;
			
		default:
			nextToken = Token.UNKNOWN;
			break;
		} // End of switch 
		if (isKeyWord(String.valueOf(lexeme,0,lexLen))) {
			nextToken = keyWordLookUp(String.valueOf(lexeme,0,lexLen));
		}
		
		// Print each token and its respective lexeme 
		System.out.printf("Next token is: %-12s \n", String.valueOf(nextToken));
		myOutput.write(String.format("Next token is: %-12s\n", String.valueOf(nextToken)));

		return nextToken;
	} // End of function lex 
}




