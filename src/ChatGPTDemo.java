import java.io.IOException;
import java.util.Scanner;

import org.json.JSONException;

public class ChatGPTDemo {

	public static void main(String[] args) {
		ChatGPT chatGPT = new ChatGPT("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");
		Scanner keyboard = new Scanner(System.in);
		String completion = "";
		String choice = "";
		
		do{
			System.out.println("Hello, How may I assist you?");
			String prompt = keyboard.nextLine();
			try {
				completion = chatGPT.generateCompletion(prompt);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(completion);	
			System.out.print("Continue (yes/no)? ");
			choice= keyboard.next();
			keyboard.nextLine();
		}while(choice.equalsIgnoreCase("yes"));
		System.out.println("Program has shut down.");
		System.out.println("Program by Si Wang");
	}

}
