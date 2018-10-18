import java.util.Random;
import java.util.Scanner;

//Matthew Moore- Hygiene ChatBot**/

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot3
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;
	//tracker makes sure that a response has to be correct to the context it's said in.
	int track=0;
	//error, if you gave the wrong answer before, the error counter goes up, and will give you a new response, if your answer is wrong again."
	int error=0;



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
	 * Def and init track here. Init as 0.
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		int track=0;
		int error=0;
		return "Hey, would you like to be less filthy? \n Answer yes or no";
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
		String response = "";
		
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}


		
		else if (findKeyword(statement, "levin") >= 0)
		{
			response = "More like LevinTheDream amiright?";
			emotion++;
		}

		// Response transforming I want to statement

		//First set of questions
		else if (track==0 && findKeyword(statement,"yes")>=0)
		{
			response="Sweet dawg. \n Lemme start by asking: \n How long do you wash your hands for?";
			track++;
		}
		// If they say no.
		else if (track==0 && findKeyword(statement,"no")>=0)
		{
			response="oh \n Well then let me redirect you to my good buddy Chatbot sleep, \n type in sleep";
			track++;
			statement="Bye";


		}
		else if (track==1 && findKeyword(statement, "I wash my hands for", 0)>=0)
		{
			response= transformIWashStatement(statement);
			track++;
			error=0;

		}
		else if (track==1 && findKeyword(statement, "I wash my hands for", 0)==-1)
		{

			if (error==0)
			{
				response= "I'm sorry dude, I forgot to tell you: \n You have to type your answer as 'I wash my hands for...'";

			}
			if (error==1)
			{
				response= "Either my human error is showing, or your faulty programming is XD \n Remember, type your answer as 'I wash my hands for...' ";

			}
			if (error>1)
			{
				for (int i=0; i<=error; i++)
				{
					String stor= errorList[(int)(Math.random()*(5-0))];
					response+=stor;
				}
				response+="\n";
				response+="I wash my hands for";
			}
			error++;
		}
		else if (track==2 && findKeyword(statement, "I started because",0)>=0)
		{
			response= transformIStarted(statement);
			track++;
			error=0;
		}
		else if (track==2 && findKeyword(statement, "I started because",0)==-1)
		{
			if (error==0)
			{
				response= "I know it's a bit much to type, but I'll need you to type your answer as \n 'I started because'";
			}
			if (error>0)
			{
				response="";
				for (int i=0; i<=error; i++)
				{
					String stor= errorList[(int)(Math.random()*(5-0))];
					response+=stor;
				}
				response+="\n";
				response+="I Started to wash my hands for...";
			}

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
	 * Take a statement with "I want <something>." and transform it into 
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWashStatement(String statement)
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
		int psn = findKeyword (statement, "I wash my hands for", 0);
		String restOfStatement = statement.substring(psn + 19).trim();
		return "What made you start to wash your hands for " + restOfStatement + "?";
	}

	/** Takes the persons explanation for why, and gives them a response. Snide remark about what was said, Then asks how
	 * frequently you does it. Change this shit
	 * @param statement
	 * @return
	 */
	private String transformIStarted(String statement)
	{
		// Tkaes in astatemen, checks if it was done correctly. Handles erroe where somebody might type I or my
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		if (findKeyword(statement,"I started because my")>=0)
		{
			int psn = findKeyword (statement, "I started because my", 0);
			String restOfStatement = statement.substring(psn + 20).trim();
			return sassList[(int)(Math.random()*(3-0))] + "\n So you started this way because your " +restOfStatement + "? \n Despite how you might've read my response to that, "+ jokeList[(int)(Math.random()*(3-0))] + " \n I would advise you to wash your hands for "+ ((int)(Math.random()*(1000-1)));
		}
		if (findKeyword(statement,"I started because I")>=0)
		{
			int psn = findKeyword (statement, "I started because I", 0);
			String restOfStatement = statement.substring(psn + 19).trim();
			return sassList[(int)(Math.random()*(3-0))] + "\n So you started because you " +restOfStatement + "? \n Despite how you might've read my response to that, "+ jokeList[(int)(Math.random()*(3-0))] + " \n I would advise you to wash your hands for "+ ((int)(Math.random()*(1000-1))) ;
		}
		int psn = findKeyword (statement, "I started because", 0);
		String restOfStatement = statement.substring(psn + 17).trim();
		return sassList[(int)(Math.random()*(3-0))] + "\n So you started because " + restOfStatement + "? \n Despite how you might've read my response to that, "+ jokeList[(int)(Math.random()*(3-0))] + " \n I would advise you to wash your hands for "+ ((int)(Math.random()*(1000-1))) ;

	}
	private String resaonForLength()
	{
		int stor = (int)(Math.random()*(-1));
		if ( stor <= 50)
		{
			return stor + " seconds, \n This is because, your response indicated that you are a thorough and hygenic human being.";
		}
		else
		{
			return stor+" seconds, \n This is because, of the humans I have surveyed, most people that have a personality like yours "+stor+" seconds is enough tome for you to be as clean as possible.";
		}
	}
	/**private String transformIBathEvery(String statement)
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
		int psn = findKeyword (statement, "I take a shower", 0);
		String restOfStatement = statement.substring(psn + 19).trim();
		return "What made you start to wash your hands for " + restOfStatement + "?";
	*/


	
	/**
	 * Take a statement with "I <something> you" and transform it into 
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
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
		
		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);
		
		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "Why do you " + restOfStatement + " me?";
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
			"Could you say that again?"
	};
	private String [] randomAngryResponses = {"Bahumbug.", "Harumph", "The rage consumes me!"};
	private String [] randomHappyResponses = {"H A P P Y, what's that spell?", "Today is a good day", "You make me feel like a brand new pair of shoes."};
	private String [] errorList={"(-_-) ","('.') ", "(-(-_(-_-)_-)-) ", "ヽ(`Д´)ﾉ ", "(＃ﾟДﾟ) " };
	private String [] sassList={"(¬_¬) Ok..."," (☉_☉) ARE YOU SOME SORT OF MANIAC!?!?!", "( ◞･౪･) Cool dude" };
	private String[] jokeList={"almost 3% percent of cave people have that exact same reason.", "I have heard this reason several thousand times.", "Sleep bot and Exercise bot" +
			" would have been even bigger jerks","it's is nothing to be ashamed of."};

	
}
