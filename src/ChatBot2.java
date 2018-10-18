import jdk.internal.util.xml.impl.Input;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

//Kent Li - Exercise ChatBot

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot2
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;


	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while (!statement.equals("Bye"))
		{

			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));

		}

	}

	/**
	 * Greets the user and asks for their BMI.
	 * @return a greeting
	 */
	public String getGreeting()
	{
		String bmiresponse = "";
		System.out.println("Would you like to find out your bmi?");
		Scanner response = new Scanner (System.in);
		String responseStr = response.nextLine();
		if(responseStr.contains("yes") || responseStr.contains("sure") || responseStr.contains("ok") || responseStr.contains("cool"))
		{
			bmiresponse = transformBMIStatement(responseStr);
		}
		else if(responseStr.contains("no") || responseStr.contains("no thank you") || responseStr.contains("non"))
		{
			System.out.println("Cmon, you know you want to, be honest!");
			bmiresponse = transformBMIStatement(responseStr);
		}
		else
		{
			System.out.println("I don't know what you just said, but it seems like you wanna know your BMI!");
			bmiresponse = transformBMIStatement(responseStr);
		}
		System.out.println(bmiresponse);
		double bmiNum;
		while(true)
		{
			System.out.println("Tell me your BMI result that you got from earlier! Feel free to round to 2 decimal places!");
			try{
				bmiNum = Double.parseDouble(response.next());
				break;
			}
			catch(NumberFormatException e)
			{
				System.out.println("Invalid input!");
			}
		}
		return askIfBMIIsAverage(bmiNum);
	}

	/**
	 * After getting the BMI, this method asks the user for their general age group and compares their BMI to the average BMI.
	 * @return
	 */
	public String askIfBMIIsAverage(double bmi)
	{
		String questionanswer = "";
		System.out.println("Are you a kid, adolescent, or an adult?");
		Scanner questionresponse = new Scanner(System.in);
		String questionStr = questionresponse.nextLine();
		if(questionStr.contains("kid") || questionStr.contains("child") || questionStr.contains("children)"))
		{
			if(bmi >= 14 && bmi <= 18)
			{
				questionanswer = "Hey your BMI is pretty normal.";
			}
			else if (bmi <= 14)
			{
				questionanswer = "Hey you're underweight.";
			}
			else if (bmi >= 18)
			{
				questionanswer = "Hey you're overweight!";
			}
			else
			{
				questionanswer = "I don't even know what you are.";
			}
		}
		else if(questionStr.contains("adolescent") || questionStr.contains("teen"))
		{
			if(bmi >= 17 && bmi <= 23)
			{
				questionanswer = "Hey your BMI is pretty normal.";
			}
			else if (bmi <= 17)
			{
				questionanswer = "Hey you're underweight.";
			}
			else if (bmi >= 23)
			{
				questionanswer = "Hey you're overweight!";
			}
			else
			{
				questionanswer = "I don't even know what you are.";
			}
		}
		else if(questionStr.contains("adult") || questionStr.contains("grown up"))
		{
			if(bmi >= 19 && bmi <= 27)
			{
				questionanswer = "Hey your BMI is pretty normal.";
			}
			else if (bmi <= 19)
			{
				questionanswer = "Hey you're underweight.";
			}
			else if (bmi >= 27)
			{
				questionanswer = "Hey you're overweight!";
			}
			else
			{
				questionanswer = "I don't even know what you are.";
			}
		}
		else
		{
			System.out.println("Tell me more about you! Are you a child, teenager, or adult?");
		}
		return questionanswer;
	}

	/**
	 * Gives a response to a user statement
	 *
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response;

		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
			emotion--;
		}

		else if (findKeyword(statement, "levin") >= 0)
		{
			response = "More like LevinTheDream amiright?";
			emotion++;
		}
		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to play", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}
		else if (findKeyword(statement,"My favorite sport is",0) >= 0)
		{
			response = transformSportsStatement(statement);
		}
		//else if (findKeyword(statement,"you",0) >= 0)
		//{
		//	response = transformIYouStatement(statement);
		//}
		else if (findKeyword(statement,"What is my BMI?",0) >= 0)
		{
			response = transformBMIStatement(statement);
		}
		else
		{
			response = getRandomResponse();
		}

		return response;
	}



	/**
	 * Take a statement with "I want to <something>." and transform it into
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
	}

	/**
	 * Transform favorite sport and gives a response.
	 * @param statement
	 * @return
	 */
	private String transformSportsStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "My favorite sport is", 0);
		String restOfStatement = statement.substring(psn + 21).trim();
		return "Cool! I like to play " + restOfStatement + " too! Would you like to find out what your BMI is? Ask 'What is my BMI?' to find out!";
	}

	/**
	 * Gets user's height and weights and returns BMI.
	 * @param statement
	 * @return
	 */
	private String transformBMIStatement(String statement)
	{
		System.out.println("Let's find your BMI!");
		Scanner bmiscan = new Scanner (System.in);
		double weight= 0;
		double height = 0;
		double bmi;
		String bmistr = "";
		boolean condition = false;
		while(!condition) {
			System.out.println("What is your weight in kg?");
			boolean condition2 = false;
			while (!condition2) {
				try {
					weight = Double.parseDouble(bmiscan.nextLine());
					condition2 = true;
				} catch (NumberFormatException e) {
					System.out.println("Try inputting a number!");
					System.out.println("What is your weight in kg?");
				}
			}
			System.out.println("What is your height in meters?");
			boolean condition3 = false;
			while (!condition3) {
				try {
					height = Double.parseDouble(bmiscan.nextLine());
					condition3 = true;
				} catch (NumberFormatException e) {
					System.out.println("Try inputting a number!");
					System.out.println("What is your height in meters?");
				}
			}
			bmi = weight / (height * height);
			bmistr = Double.toString(bmi);
		condition = true;
		}
		return "Your BMI is: " + bmistr + ".";
	}


	/**
	 * Take a statement with "I want <something>." and transform it into
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would you really be happy if you had " + restOfStatement + "?";
	}


	/**
	 * Take a statement with "I <something> you" and transform it into
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	//private String transformIYouStatement(String statement)
	//{
	//	//  Remove the final period, if there is one
	//	statement = statement.trim();
	//	String lastChar = statement.substring(statement
	//			.length() - 1);
	//	if (lastChar.equals("."))
	//	{
	//		statement = statement.substring(0, statement
	//				.length() - 1);
	//	}
	//
	//		int psnOfI = findKeyword (statement, "I", 0);
	//		int psnOfYou = findKeyword (statement, "you", psnOfI);
	//
	//		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
	//		return "Why do you " + restOfStatement + " me?";
	//	}

	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
							int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
					// letter
					&& ((after.compareTo("a") < 0) || (after
					.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}

		return -1;
	}

	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}



	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion == 0)
		{
			return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
		}
		if (emotion < 0)
		{
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}
		return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
	}

	private String [] randomNeutralResponses = {"Interesting, tell me more",
			"Hmmm.",
			"Do you really think so?",
			"You don't say.",
			"It's all boolean to me.",
			"So, would you like to go for a walk?",
			"Could you say that again?",
			"Use a full sentence!"
	};
	private String [] randomAngryResponses = {"Bahumbug.", "Harumph", "The rage consumes me!", "Speak a proper sentence!", "I don't have all day to talk to you!"};
	private String [] randomHappyResponses = {"H A P P Y, what's that spell?", "Today is a good day", "You make me feel like a brand new pair of shoes."};

}


