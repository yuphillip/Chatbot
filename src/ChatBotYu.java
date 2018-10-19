/*Phillip Yu */
import java.util.Random;
import java.util.Scanner;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBotYu
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
	 * Get a default greeting
	 * @return a greeting
	 */
	public String getGreeting()
	{
		return "Hey! How many hours do you sleep ?";
	}

	/**
	 * Gives a response to a user statement
	 *
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	/**
	 *
	 * @param statement is the answer/response the user is giving to us.
	 * @return This method returns a response based on the statement given to use by the user.
	 */
	public String getResponse(String statement)
	{
		String response = "";

		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
		}
		else if (findKeyword(statement, "yes") >= 0)
		{
			response = "Great! Is there any other activity you could cut time off of?";
		}
		// Response transforming I want to statement
		else if (findKeyword(statement, "I sleep", 0) >= 0)
		{
			response = transformISleepStatement(statement);
		}
		else if (findKeyword(statement, "hours", 0) >= 0)
		{
			response = transformISleepStatement(statement);
		}
		else if (findKeyword(statement, "Before sleeping I",0) >= 0)
		{
			response = transformBeforeSleepStatement(statement);
		}
		else if (findKeyword(statement, "phone", 0) >= 0) {
			return (getPhoneResponse() + "\n" + getRandomResponse());
		}
		else
		{
			response = getRandomResponse();
		}

		return response;
	}

	/**
	 *
	 * @param statement is the response given to us by the user.
	 * @return This returns a unique response based on the response/keywords found in the user. In this one it substrings out
	 * the statement either before "hours" or after "I sleep". Then it puts that statement into the question unique to the user.
	 */
	public String transformISleepStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String restOfStatement = "";
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn1 = findKeyword (statement, "I sleep", 0);
		int psn = findKeyword (statement,"hours", 0);
		String SleepTime = statement.substring(psn-2,psn-1);
		int Sleep = Integer.parseInt(SleepTime);
		if(statement.length() < 8)
		{
			restOfStatement = statement.substring(psn - 2).trim();
		}
		else {
			restOfStatement = statement.substring(psn1 + 8).trim();
		}
		return SleepHoursResponse(Sleep) + "\n" + "What do you do before sleeping that you only sleep" + " " + restOfStatement + "?";
	}

	/**
	 *
	 * @param Sleep This represents the amount of the time the user inputted that they sleep a day.
	 * @return This will return a different statement depending on how many hours the user sleeps.
	 */
	public String SleepHoursResponse(int Sleep)
	{
		if(Sleep >= 0 && Sleep <= 3)
		{
			return "I feel" + " " + Sleep + " " + "hours is no where near enough sleep you should get! You need to improve your sleep schedule ASAP!";
		}
		else if (Sleep > 3 && Sleep <= 5)
		{
			return "I feel" + " " + Sleep + " " + "hours is coming close to the amount of sleep you should get if you save some time on some activities maybe you could sleep the recommended amount.";
		}
		else if (Sleep > 5 && Sleep <= 8)
		{
			return "Your" + " " + Sleep + " " + "hours a day is a good amount, you are getting around the recommended hours of sleep a day.";
		}
		else if (Sleep > 8 && Sleep <= 12)
		{
			return "Your" + " " + Sleep + " " + "hours a day is above the recommended for most but some people are different and this is still in the healthy range!";
		}
		else if (Sleep > 12)
		{
			return "How do you get" + " " + Sleep + "hours of sleep a day? You are starting to sleep an unhealthy amount. Be careful!";
		}
		else
		{
			return "What do you mean you get" + " " + "hours of Sleep a day?";
		}

	}

	/**
	 *
	 * @param statement is the statement which the use inputs in response to the chat bot's questions.
	 * @return This will return a unique response depending on statement. It will transform any statement having the keyword
	 * "Before sleeping I do" which will then add a unique part into the next question.
	 *
	 */
	private String transformBeforeSleepStatement(String statement)
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
		int psn = findKeyword (statement, "Before sleeping I do", 0);
		String restOfStatement = statement.substring(psn + 20).trim();
		return "Do you think you could spend less time on" + " " + restOfStatement + "?";
	}

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
	 * @return This gets a random number and goes through and array of neutral Response to give one to the user.
	 */
	private String getRandomResponse () {
		Random r = new Random();
		return randomNeutralResponses[r.nextInt(randomNeutralResponses.length)];
	}

	/**
	 * @return This gets a random number and then goes through an array of phone facts to give to the user.
	 */
	private String getPhoneResponse()
	{
		Random r = new Random ();
		return phoneResponses [r.nextInt(phoneResponses.length)];
	}
	private String [] phoneResponses = {
			"Did you know it is generally recommended to not use electronics 30 minutes before you sleep?",
			"Researchers have found that using electronics prior to sleep leads to sleep disturbances and low energy.",
			"Smartphones and tablets disrupt sleep, in part, because they emit what's known as 'blue' light.",
			"Screen time at night keeps adults from falling asleep and sleeping well due to cognitive stimulation and sleep deprivation."
	};
	private String [] randomNeutralResponses =
			{"Why though?",
			"I do too! What else do you do ?",
			"Sleep is vital to having a healthy lifestyle, Do you think you're healthy?",
			"I feel that sleeping more could always help!",
			"Spending less time online is always a good start!",
                    "I feel that getting the recommended hours of sleep everyday should be prioritized."
	};
	
}
