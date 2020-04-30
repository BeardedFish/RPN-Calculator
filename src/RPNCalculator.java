/*
 *	Program Description: This program is an RPN (also know as postfix) calculator that has many functions such as adding, subtracting,
 						 dividing, squareroot, sin, cos, tan, etc. This calculator program requires ansicon to properly work.
 *	By: Darian Benam (GitHub: https://github.com/BeardedFish/)
 *	Date Created: Wednesday, April 5, 2017
 *  Last Updated: Wednesday, April 29, 2020
 */

import java.util.Scanner;

public class RPNCalculator
{
	private static Scanner sc = new Scanner(System.in);
	
	private static double t, x, y, z; // The 4 registers of the calculator. The variable 't' is the top register and 'x' is the total register.
	
	private static final String RED = "\033[1;31m"; // Red forecolor.
	private static final String YELLOW = "\033[1;33m"; // Yellow forecolor.
	private static final String GREEN = "\033[1;32m"; // Green forecolor.
	private static final String DEFAULT_COLOUR = "\033[0m"; // Default forecolour (light gray).
	
	private static boolean useDegreesMode = false; // NOTE: False means radians mode.

	/*
	 *	Main entry point of the application.
	 */
	public static void main(final String[] args)
	{
		clearConsole();
		
		selectMode(); // Get the user to enter a mode (degrees or radians) they want the calculator to be in before they can start calulating (they can change the mode later if they choose too).

		boolean breakOutOfLoop = false; // States whether the nested while loop below should stop or not
		while (!breakOutOfLoop)
		{
			clearConsole();
			
			printTitle("RPN Calculator - By: Darian Benam");
			System.out.println(YELLOW + "\nHaving trouble? To get a list of commands, type in 'help' or 'commands' (without the quotes) into the console and press enter to go to the help screen." + DEFAULT_COLOUR);
			System.out.println("\nCurrent calculator mode: " + calculatorModeName()); // Print the current mode the calulator is in on the console.
			
			printRegisters();
			
			while (!breakOutOfLoop)
			{
				System.out.print("Enter either a number or a command: ");
				final String input = sc.next();
			
				if (input.equals("exit") || input.equals("quit"))
				{
					// Stop the nested while loop
					breakOutOfLoop = true;
					break;
				}
				else
				{
					final boolean validCommand = handleInput(input);
					
					if (validCommand)
					{
						break;
					}
					else
					{
						printErrorMessage("Uh-oh! You entered an invalid command! Please try again.");
					}
				}
			}
		}

		// Close the scanner
		sc.close();
	}
	
	/*
	 *	Prints the values of the t, x, y, z registers onto the console in color. If the x, y, z registers are equal to 0.0 then their forecolor 
	 *	will be red, else white. The x registers forecolor will always be green.
	 */
	static void printRegisters()
	{	
		// NOTE: Any register (excluding x) that has a value of 0.0 will be printed in a red forecolor, else they will be printed in a light gray forecolor.
		
		System.out.println("\nRegister values:\n");
		
		if (t == 0.0)
		{
			System.out.printf("%s%8s %1.9f %s%n", RED, "T:", t, DEFAULT_COLOUR);
		}
		else
		{
			System.out.printf("%8s %1.9f %n", "T:", t);
		}
		
		if (z == 0.0)
		{
			System.out.printf("%s%8s %1.9f %s%n", RED, "Z:", z, DEFAULT_COLOUR);
		}
		else
		{
			System.out.printf("%8s %1.9f %n", "Z:", z);
		}
		
		if (y == 0.0)
		{
			System.out.printf("%s%8s %1.9f %s%n", RED, "Y:", y, DEFAULT_COLOUR);
		}
		else
		{
			System.out.printf("%8s %1.9f %n", "Y:", y);
		}
		
		System.out.printf(GREEN + "Total/X: %1.9f %s %n%n", x, DEFAULT_COLOUR);
	}
	
	/*
	 *	Returns the name of the mode (degrees or radians) the calculator is currently in.
	 */
	static String calculatorModeName()
	{
		if (useDegreesMode)
		{
			return "degrees";
		}
		
		return "radians";
	}
	
	/*
	 *	A method that asks the user what mode (degrees or radians) they want the calculator to be in, setting the mode to their choice.
	 */
	static void selectMode()
	{
		clearConsole();
		
		printTitle("RPN Calculator - By: Darian Benam | Select a Mode");
		
		System.out.println("\nPlease enter the mode you would like the calculator to be in:");
		
		while (true)
		{
			System.out.print("\nAvailable Modes:\n1) Degrees\n2) Radians\n\n");
			System.out.print("Mode: ");
			final String choice = sc.next();
			
			if (choice.equals("1") || choice.equals("degrees")) // Degrees mode.
			{
				useDegreesMode = true;
				break;
			}
			else if (choice.equals("2") || choice.equals("radians")) // Radians mode.
			{
				useDegreesMode = false;
				break;
			}
			else // User entered a invalid mode.
			{
				printErrorMessage("That's an invalid mode! Please try again.");
			}
		}
	}
	
	/*
	 *	This method prints out a string that is surrounded with special box characters.
	 */
	static void printTitle(final String title)
	{
		final int titleLength = title.length() + 1;
		System.out.print("\u2554");
		for (int i = 0; i <= titleLength; i++)
		{
			System.out.print("\u2550");
		}
		System.out.print("\u2557\n");
		System.out.println("\u2551 " + title + " \u2551");
		System.out.print("\u255A");	
		for (int i = 0; i <= titleLength; i++)
		{
			System.out.print("\u2550");
		}
		System.out.println("\u255D");	
	}
	
	/*
	 *	Prints a help screen containing all the application and calulation commands for the calculator.
	 */
	static void printHelpScreen()
	{
		clearConsole();
		
		printTitle("RPN Calculator - By: Darian Benam | Help");
		
		System.out.println("\n- Application commands:\n");
		System.out.println("\texit or quit - Terminates the program.");	
		
		System.out.println("\n- Calculation commands:\n");
		System.out.println("\t'insert any # here' - Sets the x register to the specified number.");
		System.out.println(" \tc or clear - Clears all register values and sets them to 0.0.");
		System.out.println("\tcleart or clt - Sets the t register value to 0.");	
		System.out.println("\tclearz or clz - Sets the z register value to 0.");
		System.out.println("\tcleary or cly - Sets the y register value to 0.");
		System.out.println("\tclearx or clx - Sets the x register value to 0.");
		System.out.println("\tmode or changemode - Redirects user to a screen where they can change the calculator mode (degrees or radians).");
		System.out.println("\tx<>y - Switches the value of the x register with the value of the y register and vice versa.");
		System.out.println("\t= or equals - Sets the y register value with the x register value while still keeping the x register value the same.");
		System.out.println("\t+ or add - Adds the x and y register values together.");
		System.out.println("\t- or subtract - Subtracts the x register value from the y register value.");
		System.out.println("\t/ or divide - Divides the y register value with the x register value.");
		System.out.println("\t* or multiply - Multiplies the x register value with the y register value.");
		System.out.println("\t% or percent - Gets the percentage of the x register value from the y register value.");
		System.out.println("\tx^2 or power2 - Powers the x register value to 2.");
		System.out.println("\t1/x - Divides the number 1 with the value of the x register.");	
		System.out.println("\ty^x - Powers the y register value to the value of the x register.");
		System.out.println("\tsqrt or squareroot - Gets the square root of the x register value.");
		System.out.println("\tpi - Assigns the x register value with PI (3.14...)");
		System.out.println("\tsin or sine - Gets the sine of the x register.");
		System.out.println("\tcos or cosine - Gets the cosine of the x register..");
		System.out.println("\ttan or tangent - Gets the tangent of the x register.");
		System.out.println("\trup or shuffleup - Shuffles all the register values up.");
		System.out.println("\trdn or shuffledown - Shuffles all the register values down.");
		
		System.out.println("\nType 'back', 'exit', or 'return' (without quotes) and press enter to exit out the help screen.");	
		while (true)
		{
			System.out.print("Input: ");
			final String input = sc.next();
			
			if (input.equals("exit") || input.equals("return") || input.equals("back"))
			{
				return;
			}
		}
	}
	
	/*
	 *	Handles the users input they entered, returning a boolean if the input was handled succesfully or not. If the input is a number, then it will be 
	 *	added to the x register, returning true. If the input is a calculation command then it will be perform the specific calculation command the user 
	 *	entered, returning true. If the input is not a number or an invalid command, then it will return false.
	 */
	static boolean handleInput(final String input)
	{
		final boolean isNum = isNumber(input);
		
		final double tempT = t;
		final double tempZ = z;
		final double tempY = y;
		final double tempX = x;
		
		if (isNum) // The input is a number.
		{
			t = z;
			z = y;
			y = x;
			x = Double.parseDouble(input);
		}
		else
		{
			switch (input)
			{
				case "commands": // Prints the help/commands screen onto the console.
				case "help":
				{
					printHelpScreen();
				}
				break;
				case "c": // Clears all the register values, setting them all to 0.0.
				case "clear":
					t = x = y = z = 0.0;
					break;
				case "cleart": // Clears/sets the t register to 0.0.
				case "clt": 
					t = 0.0;
					break;
				case "clearz": // Clears/sets the z register to 0.0.
				case "clz":
					z = 0.0;
					break;
				case "cleary": // Clears/sets the y register to 0.0.
				case "cly":
					y = 0.0;
					break;
				case "clearx": // Clears/sets the x register to 0.0.
				case "clx":
					x = 0.0;
					break;
				case "mode":
				case "changemode":
					selectMode();
					break;
				case "x<>y": // This switches the x register with the y register and the y register with the x register.
					x = y;
					y = tempX;
					break;
				case "equals": // Assigns the y register value with the x register value (the x register still stays the same).
				case "=":
					t = z;
					z = y;
					y = x;
					break;
				case "add": // Adds the 'x' register with the 'y' register.
				case "+":
					x = x + y;
					y = z;
					z = t;
					break;
				case "subtract": // Subtracts the 'x' register from the 'y' register.
				case "-":
					x = y - x;
					y = z;
					z = t;
					break;
				case "divide": // Divides the 'x' register from the 'y' register.
				case "/":
					x = y / x;
					y = z;
					z = t;
					break;
				case "multiply": // Multiplies the 'x' and 'y' registers together.
				case "*":
					x = x * y;
					y = z;
					z = t;
					break;
				case "percent": // Gets the value of percentage of the 'x' register from the 'y' register.
				case "%":
					x = y / 100.0 * x;
					break;
				case "power2": // Powers the 'x' register to 2.
				case "x^2":
					x = x * x;
					break;
				case "1/x": // Assigns the 'x' register with 1 divided by the 'x' register.
					x = 1.0 / x;
					break;
				case "y^x": // Powers the y register value to the value of the x register.
					x = Math.pow(y, x);
					break;
				case "squareroot": // Gets the square root of the 'x' register.
				case "sqrt":
					x = Math.sqrt(x);
					break;
				case "pi": // Assigns the full PI value to the 'x' register.
					t = z;
					z = y;
					y = x;
					x = Math.PI;
					break;
				case "sine": // Gets the sine of the 'x' register.
				case "sin":
					if (useDegreesMode)
					{
						x = Math.sin(Math.toRadians(x));
					}
					else
					{
						x = Math.sin(x);
					}
					break;
				case "cosine": // Gets the cosine of the 'x' register.
				case "cos":
					if (useDegreesMode)
					{
						x = Math.cos(Math.toRadians(x));
					}
					else
					{
						x = Math.cos(x);
					}
					break;
				case "tangent": // Gets the tangent of the 'x' register.
				case "tan":
					if (useDegreesMode)
					{
						x = Math.tan(Math.toRadians(x));
					}
					else
					{
						x = Math.tan(x);
					}
					break;
				case "shuffleup": // Shuffles registers up.
				case "rup":
					t = tempZ;
					z = tempY;
					y = tempX;
					x = tempT;
					break;
				case "shuffledown": // Shuffle registers down.
				case "rdn":
					t = tempX;
					z = tempT;
					y = tempZ;
					x = tempY;
					break;		
				default: // The input is a invalid command.
					return false; // Input wasnt handled correctly.
			}	
		}
		return true; // Input was handled correctly (it was either a valid number or a valid commnand).
	}
	
	/*
	 *	This method will clear all the text that is currently on the console screen.
	 */
	static void clearConsole()
	{
		System.out.print("\033[2J");
	}
	
	/*
	 *	Prints an error message on the console with red font.
	 */
	static void printErrorMessage(final String errorMessage)
	{	
		System.out.println(RED + "ERROR: " + errorMessage + DEFAULT_COLOUR);
	}
	
	/*
	 *	Checks if a string value is a number, returning true if it is, and false if it is not.
	 */
	static boolean isNumber(final String text)
	{
		try 
		{
			Double.valueOf(text);
		} 
		catch (final NumberFormatException e) 
		{
			return false;
		}
		
		return true;
	}
}