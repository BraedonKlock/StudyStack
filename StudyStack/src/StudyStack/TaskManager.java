package StudyStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskManager {

    static Map<String, DayOfWeek> weekDays = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    
    private static void initializeWeekDays() {
        weekDays.put("Monday", new DayOfWeek("Monday"));
        weekDays.put("Tuesday", new DayOfWeek("Tuesday"));
        weekDays.put("Wednesday", new DayOfWeek("Wednesday"));
        weekDays.put("Thursday", new DayOfWeek("Thursday"));
        weekDays.put("Friday", new DayOfWeek("Friday"));
        weekDays.put("Saturday", new DayOfWeek("Saturday"));
        weekDays.put("Sunday", new DayOfWeek("Sunday"));
    }

    public static void addTask() {
	    System.out.print("\nEnter the name of the task or type \"2\" to exit: ");
	    String taskName = scanner.nextLine().trim();
	    if (taskName.equals("2")) return;

	    System.out.print("\nWhich day of the week do you want to add it to? or type \"2\" to exit: ");
	    String dayInput = scanner.nextLine().trim();
	    if (dayInput.equals("2")) return;

	    DayOfWeek day = weekDays.get(capitalize(dayInput));
	    if (day == null) {
	        System.out.println("\n*Invalid day entered*\n");
	        return;
	    }

	    day.addTask(new Task(taskName));
	    System.out.println("\n*Task added to " + day.getName() +"*\n");
	    saveTask();
	}


	public static void editTask() {
	    System.out.print("\nWhich day do you want to edit a task on? (or type \"2\" to exit): ");
	    String dayInput = scanner.nextLine().trim();
	    if (dayInput.equals("2")) return;
	    
	    System.out.print("\n");
	    DayOfWeek day = weekDays.get(capitalize(dayInput));
	    if (day == null) {
	        System.out.println("\n*Invalid day entered*\n");
	        return;
	    }

	    List<Task> tasks = day.getTasks();
	    if (tasks.isEmpty()) {
	        System.out.println("\n*No tasks to edit on " + day.getName() + "*\n");
	        return;
	    }

	    for (int i = 0; i < tasks.size(); i++) {
	        System.out.println(i + ": " + tasks.get(i).getName());
	    }
	    System.out.print("Enter the number of the task to edit or type \"Exit\": ");
	    String input = scanner.nextLine().trim();

	    if (input.equalsIgnoreCase("Exit")) return;

	    int index;
	    try {
	        index = Integer.parseInt(input);
	    } catch (NumberFormatException e) {
	        System.out.println("\n*Invalid number. Please enter a valid task number*\n");
	        return;
	    }
	    System.out.print("\nEnter the new task name or type \"2\" to exit: ");
	    String newName = scanner.nextLine();
	    if (newName.equals("2")) return;

	    day.editTask(index, newName);
	    saveTask();
	}


    public static void deleteTask() {
        System.out.print("\nWhich day do you want to delete a task from? Or type \"2\" to exit: ");
        String day = scanner.nextLine();
	    if (day.equals("2")) return;

        DayOfWeek d = weekDays.get(capitalize(day));
        if (d != null) {
            List<Task> tasks = d.getTasks();
            if (tasks.isEmpty()) {
                System.out.println("\n*No tasks to delete on " + d.getName() + "*\n");
                return;
            }
            
            System.out.print("\n");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": " + tasks.get(i).getName());
            }

            System.out.print("Enter the number of the task to delete or type \"exit\": ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Exit")) return;

            int index;
            try {
                index = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\n*Invalid number. Please enter a valid task number*\n");
                return;
            }
            d.deleteTask(index);
            System.out.println("\n*Task deleted*\n");
            saveTask();
        } else {
            System.out.println("\n*Invalid day entered*\n");
        }
    }
    
    public static void clearSchedule() throws StudyStackException {
        try {
            while (true) {
                System.out.print("\nDo you want to clear your schedule? (1 = YES | 2 = NO): ");
                int clear = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character

                if (clear == 1) {
                    weekDays.clear();
                    initializeWeekDays(); // <-- Add this
                    saveTask();
                    System.out.println("\n*Schedule cleared*\n");
                    break;
                } else if (clear == 2) {
                    break;
                } else {
                    System.out.println("\n*Enter a valid number (1 = YES | 2 = NO)*\n");
                }
            }
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the bad input
            throw new StudyStackException("\n*Enter a valid number (1 = YES | 2 = NO)*\n");
        }
    }


    public static void saveTask() {
        try (FileWriter writer = new FileWriter("C:\\Users\\braed\\AcademicProjects\\StudyStack\\StudyStack\\schedule.csv")) {
            for (DayOfWeek day : weekDays.values()) {
                StringBuilder line = new StringBuilder();
                line.append(day.getName()).append(",");

                List<Task> task = day.getTasks();
                for (int i = 0; i < task.size(); i++) {
                    Task t = task.get(i);
                    line.append(t.getName());
                    if (i < task.size() - 1) {
                        line.append("|");
                    }
                }
                writer.write(line.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("\n*Error saving file: " + e.getMessage() + "*\n");
        }
    }

    public static void loadTaskFromFile() {
        weekDays.clear();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\braed\\AcademicProjects\\StudyStack\\StudyStack\\schedule.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length < 2) continue;

                String dayName = parts[0].trim();
                DayOfWeek weekdays = new DayOfWeek(dayName);

                String[] taskEntries = parts[1].split("\\|");
                for (String taskName : taskEntries) {
                    if (!taskName.isEmpty()) {
                        weekdays.addTask(new Task(taskName.trim()));
                    }
                }
                weekDays.put(dayName, weekdays);
            }
        } catch (IOException e) {
            System.out.println("\n*Error loading file: " + e.getMessage() + "*\n");
        }
    }

    public static void displaySchedule() {
        int maxTasks = 0;
        for (DayOfWeek day : weekDays.values()) {
            maxTasks = Math.max(maxTasks, day.getTasks().size());
        }

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int columnWidth = 17;

        String divider = Interface.ConsoleColors.YELLOW + "+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+" + Interface.ConsoleColors.RESET;

        System.out.println(divider);

        // Print centered day names
        for (String dayName : days) {
            System.out.print(Interface.ConsoleColors.YELLOW + "| " + centerText(dayName, columnWidth - 2) + " ");
        }
        System.out.println(Interface.ConsoleColors.YELLOW + "|" + Interface.ConsoleColors.RESET);
        System.out.println(divider);

        // Print centered task names
        for (int i = 0; i < maxTasks; i++) {
            for (String dayName : days) {
                DayOfWeek day = weekDays.get(dayName);
                String taskName = "";
                if (i < day.getTasks().size()) {
                    taskName = day.getTasks().get(i).getName();
                }
                System.out.print(Interface.ConsoleColors.YELLOW +"| " + centerText(taskName, columnWidth - 2) + " ");
            }
            System.out.println("|");
        }

        System.out.println(divider);
    }

    private static String centerText(String text, int width) {
        int padding = width - text.length();
        if (padding <= 0) return text.substring(0, width); // truncate if too long
        int paddingLeft = padding / 2;
        int paddingRight = padding - paddingLeft;

        return " ".repeat(paddingLeft) + text + " ".repeat(paddingRight);
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}