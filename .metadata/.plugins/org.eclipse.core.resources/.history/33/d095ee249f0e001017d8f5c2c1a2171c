package StudyStack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TaskManager {

    static Map<String, DayOfWeek> weekDays = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    static {
        weekDays.put("Monday", new DayOfWeek("Monday"));
        weekDays.put("Tuesday", new DayOfWeek("Tuesday"));
        weekDays.put("Wednesday", new DayOfWeek("Wednesday"));
        weekDays.put("Thursday", new DayOfWeek("Thursday"));
        weekDays.put("Friday", new DayOfWeek("Friday"));
        weekDays.put("Saturday", new DayOfWeek("Saturday"));
        weekDays.put("Sunday", new DayOfWeek("Sunday"));
    }

    public static void addTask() {
        System.out.println("Enter the name of the task:");
        String taskName = scanner.nextLine();

        System.out.println("Which day of the week do you want to add it to?:");
        String day = scanner.nextLine();

        DayOfWeek d = weekDays.get(capitalize(day));
        if (d != null) {
            d.addTask(new Task(taskName));
            System.out.println("Task added to " + d.getName());
        } else {
            System.out.println("Invalid day entered.");
        }
        saveTask();
    }

    public static void editTask() {
        System.out.println("Which day do you want to edit a task on?:");
        String day = scanner.nextLine();

        DayOfWeek d = weekDays.get(capitalize(day));
        if (d != null) {
            List<Task> tasks = d.getTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks to edit on " + d.getName());
                return;
            }

            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": " + tasks.get(i).getName());
            }

            System.out.println("Enter the number of the task to edit:");
            int index = Integer.parseInt(scanner.nextLine());

            System.out.println("Enter the new task name:");
            String newName = scanner.nextLine();

            d.editTask(index, newName);
            System.out.println("Task updated.");
        } else {
            System.out.println("Invalid day entered.");
        }
    }

    public static void deleteTask() {
        System.out.println("Which day do you want to delete a task from?:");
        String day = scanner.nextLine();

        DayOfWeek d = weekDays.get(capitalize(day));
        if (d != null) {
            List<Task> tasks = d.getTasks();
            if (tasks.isEmpty()) {
                System.out.println("No tasks to delete on " + d.getName());
                return;
            }

            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(i + ": " + tasks.get(i).getName());
            }

            System.out.println("Enter the number of the task to delete:");
            int index = Integer.parseInt(scanner.nextLine());

            d.deleteTask(index);
            System.out.println("Task deleted.");
        } else {
            System.out.println("Invalid day entered.");
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

            System.out.println("\n*Courses saved to file*\n");
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

            System.out.println("\n*Courses loaded from file*\n");
        } catch (IOException e) {
            System.out.println("\n*Error loading file: " + e.getMessage() + "*\n");
        }
    }

    public static void displaySchedule() {
        int maxTasks = 0;
        for (DayOfWeek day : weekDays.values()) {
            maxTasks = Math.max(maxTasks, day.getTasks().size());
        }

        System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");
        for (String dayName : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}) {
            System.out.printf("|  %-20s", dayName);
        }
        System.out.println("|");
        System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");

        for (int i = 0; i < maxTasks; i++) {
            for (String dayName : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}) {
                DayOfWeek day = weekDays.get(dayName);
                String taskName = "";
                if (i < day.getTasks().size()) {
                    taskName = day.getTasks().get(i).getName();
                }
                System.out.printf("|   %-20s", taskName);
            }
            System.out.println("|");
        }

        System.out.println("+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+");
    }

    private static String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
