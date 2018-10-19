import java.util.Scanner;

/**
 * A simple class to run our chatbot teams.
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies.
	 * Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		ChatBotYu chatbotYu = new ChatBotYu();
		ChatBotLi chatbotLi = new ChatBotLi();
		ChatBotMoore chatbotMoore = new ChatBotMoore();
		

		Scanner topic = new Scanner (System.in);
		System.out.println("Welcome to the chatbot, nice to meet you.");
		System.out.println("Would you like to talk about exercise, sleep, or hygiene?");
		String statement = topic.nextLine();

		// Want to add && where if statement is equal to one value, it will take the user to a different chatbot.
		while (!statement.equals("Bye"))
		{
			//Use Logic to control which chatbot is handling the conversation\
			//This example has only chatbot1
			if(statement.equals("exercise"))
				chatbotLi.chatLoop(statement);
			else if (statement.equals("hygiene"))
				chatbotMoore.chatLoop(statement);
			else if (statement.equals("sleep"))
				chatbotYu.chatLoop(statement);
			else
				System.out.println("Repeat topic again please!");

			statement = topic.nextLine();
		}
	}

}
