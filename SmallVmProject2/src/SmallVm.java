import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SmallVm 
{    
    
    public static void main(String[] args) throws FileNotFoundException 
    {
    	// Path to input file
        String inputProg = ("src/mySmallVm_Prog.txt");
 
        // Print output header
        printHeader();

        // Load program into memory
        String[] simulatedMemory = loadProgram(inputProg);
            
        // Print memory contents
        printMemory(simulatedMemory);
        
        // Print output footer
        printFooter(); 
        
        // Execute program
        executeProgram(simulatedMemory);
        
    } //End main

   
    //print header with course info & 46 asterisks
    private static void printHeader() 
    {
        System.out.println("Nick Piccolo, CSCI 4200, Spring 2023");
        System.out.println("**********************************************");
    }

   
    //print footer of 46 asterisks
    private static void printFooter() 
    {
        System.out.println("**********************************************");
    }
    
  
    //print memory contents
    private static void printMemory(String[] memory) 
    {
        for (int i = 0; i < memory.length; i++) 
        {
            if (memory[i] != null) 
            {
                System.out.println(memory[i]);
            }
        }
        System.out.println();
    }
    
    
    //Load program from file into simulated memory cells
    private static String[] loadProgram(String inputFile) 
    {
    	// simulatedMemory array of size 500
        String[] simulatedMemory = new String[500];
        try (BufferedReader ProgReader = new BufferedReader(new FileReader(inputFile))) 
        {
            String currentLine;
            int i = 0;
           
            while ((currentLine = ProgReader.readLine()) != null && i < simulatedMemory.length) 
            {
                if (!currentLine.startsWith(";")) //ignore comments
                { 
                    simulatedMemory[i++] = currentLine;
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return simulatedMemory;
    }
    
     
    
    //Execute program instructions in simulated memory           
    private static void executeProgram(String[] simulatedMemory) 
    {
        int programCounter = 0; //Initialize counter to 0
        String instruction = simulatedMemory[programCounter]; 
        Map<String, Integer> variableMap = new HashMap<String, Integer>(); // Hashmap used to map user defined variables to values in memory

        while (instruction != null && !instruction.equals("HALT")) // Breaks loop if HALT commanad is reached
        {
            programCounter++;
            String[] tokens = instruction.split(" "); //use space as a delimiter & break delimited lines into tokens to be processed in switch cases below
            String opcode = tokens[0]; 

           //begin switch cases for tokens[0] (command)
            switch (opcode) 
            {
            
            // begin OUT
            case "OUT":
                String output = "";
                for (int i = 1; i < tokens.length; i++) 
                {
                	//String builder to handle input in quotes, and remove the quotes for output
                	if (tokens[i].startsWith("\"")) 
                    {	
                    	String literal = tokens[i].substring(1); // Start at index 1 to exclude the opening quote
                        if (literal.endsWith("\"")) 
                        {
                            literal = literal.substring(0, literal.length() - 1); // Remove the closing quote if it's there
                        }
                        int j = i + 1;
                        while (j < tokens.length && !tokens[j].endsWith("\"")) 
                        {
                            literal += " " + tokens[j]; // Add a space and the current token to the string literal
                            j++;
                        }
                        if (j < tokens.length)  // If we haven't reached the end of the tokens array
                        {
                            literal += " " + tokens[j].substring(0, tokens[j].length() - 1); // Add the final token without the closing quote
                        }
                        output += literal; // Append the string literal to the output
                        i = j; // Skip over the tokens that were part of the string literal
                    } 
                    // maps and assigns the value of user defined variables to output
                	else if (variableMap.containsKey(tokens[i])) 
                    {
                        int index = variableMap.get(tokens[i]);
                        output += simulatedMemory[index];
                    } 
                    //output literals - not in quotes, not a user defined variable 
                	else
                    {
                        output += tokens[i];
                    }
                }
                System.out.println(output);
                break; // end OUT


               // IN case 
                case "IN":
                    String variableName = tokens[1]; //begin at second token
                    if (!variableName.matches("^[a-zA-Z]\\w*$")) 
                    {
                        System.out.println("Invalid variable name");
                        break;
                    }
                    if (!variableMap.containsKey(variableName)) 
                    {
                        // If the variable hasn't been mapped yet, map it to the next available memory location
                        int index = getNextAvailableMemoryLocation(simulatedMemory); //helper method 
                        variableMap.put(variableName, index); //assign variable name and value to hashmap
                    }
                    Scanner scanner = new Scanner(System.in);
                    //make sure user input is an integer. It is a calculator after all. 
                    while (!scanner.hasNextInt()) 
                    {
                        System.out.println("Invalid input. Please enter an integer.");
                        scanner.nextLine(); // delete the invalid input
                    }
                    //accept int values
                    int input = scanner.nextInt();
                    simulatedMemory[variableMap.get(variableName)] = Integer.toString(input); //convert integer to string for storage in memory
                    break; // end IN


                 // ADD case
                case "ADD":
                    String variableToAddName = tokens[1]; //user defined variable name
                    //check if variable already exists, if no, create variable 
                    if (!variableMap.containsKey(variableToAddName)) 
                    {
                        int index = getNextAvailableMemoryLocation(simulatedMemory);
                        variableMap.put(variableToAddName, index); //mapping variable to value
                    }
                    int sum = 0;
                    //begin at index 2 (value position)
                    for (int i = 2; i < tokens.length; i++) 
                    {
                        String variableValue = tokens[i];
                        if (variableMap.containsKey(variableValue)) 
                        {
                            int index = variableMap.get(variableValue);
                            String value = simulatedMemory[index];
                            try
                            {
                            	sum += Integer.parseInt(value); // convert hard int string value to int for processing, and add to sum
                            } catch (NumberFormatException e)
                            {
                            	System.out.println("Invalid operation, please only use integers for command ADD. "
                            					 + "Executing ADD on valid integers:");
                            }
                        } 
                        else
                        {
                           try
                           {
                        	   sum += Integer.parseInt(variableValue); // convert variable to int for processing, and add to sum
                           } catch (NumberFormatException e)
                           {
                        	   System.out.println("Invalid operation, please only use integers for command ADD. "
                        	   					+ "Executing ADD on valid integers:");
                           }
                        }
                    }
                    //convert processed integer back into string for output
                    simulatedMemory[variableMap.get(variableToAddName)] = Integer.toString(sum); //store result in memory
                    break; // end ADD




                
                // begin SUB
                case "SUB":
                    String variableToSubtractName = tokens[1];
                    if (!variableMap.containsKey(variableToSubtractName)) 
                    {
                        int index = getNextAvailableMemoryLocation(simulatedMemory);
                        variableMap.put(variableToSubtractName, index);
                    }
                    int difference = 0;
                   //if 2 values are entered, same as ADD case
                    if (tokens.length > 2) 
                    {
                        if (variableMap.containsKey(tokens[2])) 
                        {
                            int index = variableMap.get(tokens[2]);
                            String value = simulatedMemory[index];
                            try
                            {
                            	difference = Integer.parseInt(value);
                            } catch (NumberFormatException e)
                            {
                            	System.out.println("Invalid operation, please only use integers for command SUB. "
                            					 + "Executing SUB on valid integers:");
                            }
                        } 
                        else 
                        {
                            try
                            {
                            	difference = Integer.parseInt(tokens[2]);
                            } catch (NumberFormatException e)
                            {
                            	System.out.println("Invalid operation, please only use integers for command SUB. "
                            					 + "Executing SUB on valid integers:");
                            }
                        }
                    }
                    //if more than 2 values are being subtracted, loop through additional values and subtract from the first value
                    for (int i = 3; i < tokens.length; i++) 
                    {
                        String variableValue = tokens[i];
                        if (variableMap.containsKey(variableValue)) 
                        {
                            int index = variableMap.get(variableValue);
                            String value = simulatedMemory[index];
                           try
                           {
                        	   difference -= Integer.parseInt(value);
                           } catch (NumberFormatException e)
                           {
                        	   System.out.println("Invalid operation, please only use integers for command SUB. "
                        	   					+ "Executing SUB on valid integers:");
                           }
                        } 
                        else 
                        {
                            try
                            {
                            	difference -= Integer.parseInt(variableValue);
                            } catch (NumberFormatException e)
                            {
                            	System.out.println("Invalid operation, please only use integers for command SUB. "
                            					+ "Executing SUB on valid integers:");
                            }
                        }
                    }
                    simulatedMemory[variableMap.get(variableToSubtractName)] = Integer.toString(difference); //store result in memory
                    break; // end SUB



                
                
                // begin MUL
                // same logic as ADD case
                case "MUL":
                    String variableToMultiplyName = tokens[1];
                    if (!variableMap.containsKey(variableToMultiplyName)) 
                    {
                        int index = getNextAvailableMemoryLocation(simulatedMemory);
                        variableMap.put(variableToMultiplyName, index);
                    }
                    int product = 1;
                    for (int i = 2; i < tokens.length; i++) 
                    {
                        String variableValue = tokens[i];
                        if (variableMap.containsKey(variableValue)) 
                        {
                            int index = variableMap.get(variableValue);
                            String value = simulatedMemory[index];
                            try
                            {
                              product *= Integer.parseInt(value); //for hard ints
                            } catch (NumberFormatException e)
                            {
                            	System.out.println("Invalid operation, please only use integers for command MUL. "
                            					 + "Executing MUL on valid integers:");
                            }
                        } 
                        else 
                        {
                            try
                            {
                            	product *= Integer.parseInt(variableValue); //for variables
                            } catch (NumberFormatException e)
                            {
                            	System.out.println("Invalid operation, please only use integers for command MUL. "
                            					 + "Executing MUL on valid integers:");
                            }
                        }
                    }
                    simulatedMemory[variableMap.get(variableToMultiplyName)] = Integer.toString(product); //store result in memory
                    break; // end MUL

                
                
                
                
                
                //begin DIV 
                //same logic as MUL, with error handling for division by 0    
                case "DIV":
                    String variableToDivideName = tokens[1];
                    if (!variableMap.containsKey(variableToDivideName)) 
                    {
                        int index = getNextAvailableMemoryLocation(simulatedMemory);
                        variableMap.put(variableToDivideName, index);
                    }
                    int quotient = 0;
                    for (int i = 2; i < tokens.length; i++) 
                    {
                        String variableValue = tokens[i];
                        if (variableMap.containsKey(variableValue)) 
                        {
                            int index = variableMap.get(variableValue);
                            String value = simulatedMemory[index];
                            
                            if (Integer.parseInt(value) == 0) //error handling for division by 0 as hard int
                            {
                                System.out.println("Error: Division by zero");
                                return;
                            }
                            if (i == 2) //assigns first user value as dividend 
                            {
                                try
                                {
                                	quotient = Integer.parseInt(value);
                                } catch (NumberFormatException e)
                                {
                                	System.out.println("Invalid operation, please only use integers for command DIV. "
                                					 + "Executing DIV on valid integers:");
                                }
                            }
                            else //next value as divisor
                            {
                                try
                                {
                                	quotient /= Integer.parseInt(value);
                                } catch (NumberFormatException e)
                                {
                                	System.out.println("Invalid operation, please only use integers for command DIV. "
                                					 + "Executing DIV on valid integers:");
                                }
                            }
                        } 
                        else
                        {
                            if (Integer.parseInt(variableValue) == 0) //error handling for division by 0 as variable
                            {
                                System.out.println("Error: Division by zero");
                                return;
                            }
                            if (i == 2) {
                               try
                               {
                            	   quotient = Integer.parseInt(variableValue);
                               } catch (NumberFormatException e)
                               {
                            	   System.out.println("Invalid operation, please only use integers for command DIV. "
                            	   					+ "Executing DIV on valid integers:");
                               }
                            } else {
                                try
                                {
                                	quotient /= Integer.parseInt(variableValue);
                                } catch (NumberFormatException e)
                                {
                                	System.out.println("Invalid operation, please only use integers for command DIV. "
                                					 + "Executing DIV on valid integers:");
                                }
                            }
                        }
                    }
                    simulatedMemory[variableMap.get(variableToDivideName)] = Integer.toString(quotient); // store result in memory
                    break; //end DIV

                
                
                // begin STO
                case "STO":
                    String variableToStoreName = tokens[1];
                    Integer address = variableMap.get(variableToStoreName);
                    if (address == null)  //check if map address exists, if no, find available slot and create it
                    {
                        address = getNextAvailableMemoryLocation(simulatedMemory);
                        variableMap.put(variableToStoreName, address);
                    }
                    String valueToken = tokens[2];
                    Object value;
                   
                    //the below will check to see if the user input is an int or string value, and store the value as a string
                    //experimenting with polymorphism
                    try 
                    {
                        value = Integer.parseInt(valueToken);
                    } 
                    catch (NumberFormatException e) 
                    {
                        value = valueToken;
                    }
                    if (value instanceof Integer)
                    {
                        simulatedMemory[address] = ((Integer) value).toString();
                    } 
                    else 
                    {
                        simulatedMemory[address] = (String) value;
                    }
                    break;// end STO
                    
                    // default
                default:
                	break;
            } // end switch cases
            
            instruction = simulatedMemory[programCounter]; //use program counter to fetch next instruction
        }
    }

    //Helper method to find next available memory location
    private static int getNextAvailableMemoryLocation(String[] simulatedMemory) 
    {
        for (int i = 0; i < simulatedMemory.length; i++) 
        {
            if (simulatedMemory[i] == null) 
            {
                return i;
            }
        }
        return -1; // No available memory location found
    }   
} // end class