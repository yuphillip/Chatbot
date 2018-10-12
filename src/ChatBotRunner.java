import java.util.Scanner;

/**
 * A simple class to run our chatbot teams.
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class hiChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		ChatBot1 chatbot1 = new ChatBot1();
		ChatBot2 chatbot2 = new ChatBot2();
		ChatBot3 chatbot3 = new ChatBot3();
		

		Scanner topic = new Scanner (System.in);
		System.out.println("Welcome to the chatbot, nice to meet you.");
		System.out.println("Would you like to talk about exercise, sleep, or hygiene?");
		String statement = topic.nextLine();


		while (!statement.equals("Bye"))
		{
			//Use Logic to control which chatbot is handling the conversation\
			//This example has only chatbot1
			if(statement.equals("exercise"))
				chatbot2.chatLoop(statement);
			else if (statement.equals("hygiene"))
				chatbot3.chatLoop(statement);
			else if (statement.equals("sleep"))
				chatbot1.chatLoop(statement);
			else
				System.out.println("Repeat topic again please!");

			statement = topic.nextLine();
		}
	}

}
