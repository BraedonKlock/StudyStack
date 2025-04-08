package StudyStack;

import java.util.Scanner;

public class ScheduleInterface {
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void ShowMenu() {
		System.out.println("|=========================|\n" + 
						   "|------- Task Menu -------|\n" +
						   "|=========================|\n" +
		  		   "1. Add task\n" +
		  		   "2. Edit schedule\n" +
		  		   "3. Delete task\n" +
		  		   "4. Clear schedule\n" +
		  		   "5. Exit\n" +
		  		   "Choose an option (enter #): ");
	}

	public static void Menu() throws Exception {
		int option = 0;
		
		do {
			try {
			TaskManager.loadTaskFromFile();
			Interface.printLogo();
			TaskManager.displaySchedule();
			ShowMenu();
			option = Integer.parseInt(scanner.nextLine().trim());
			switch (option) {
			case 1: TaskManager.addTask();
					break;
					
			case 2: TaskManager.editTask();
					break;
					
			case 3: TaskManager.deleteTask();
					break;
					
			case 4: TaskManager.clearSchedule();
					break;
					
			case 5: Interface.mainMenu();
					break;
					
			default: System.out.println("\n*Not a valid option*\n");
			}
			} catch(NumberFormatException e) {
				System.out.println("\n*Invalid option! try again*\n");
			}
			option = 0;
		} while (option != 5);
	}
}