package StudyStack;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class NotesManager {

    private static final String NOTES_DIR = "C:\\Users\\braed\\AcademicProjects\\StudyStack\\notes";
    private static Scanner scanner = new Scanner(System.in);

    public static void CreateNote() {
        String courseName = pickCourseFromCSV();
        if (courseName == null) return;

        Path coursePath = Paths.get(NOTES_DIR, courseName);

        try {
            Files.createDirectories(coursePath);

            File courseInfo = coursePath.resolve("CourseInfo.txt").toFile();
            if (!courseInfo.exists()) {
                System.out.print("\nCourseInfo.txt not found. Open it in Vim? (1 = Yes / 2 = No: ");
                String courseInfoChoice = scanner.nextLine().trim();
                if (courseInfoChoice.equals("1")) {
                	System.out.println("Opening CourseInfo.txt in Vim...");
                	new ProcessBuilder("vim", courseInfo.getAbsolutePath()).inheritIO().start().waitFor();
                	scanner = new Scanner(System.in);
                }

                System.out.print("\nDo you want to create a new note as well? (1 = Yes / 2 = No: ");
                String choice = scanner.nextLine().trim();
                if (!choice.equals("1")) {
                    return;
                }
            }

            String fileName = "";
            while (fileName.isEmpty()) {
                System.out.print("\nEnter note filename (without extension, or type 'exit' to cancel): ");
                fileName = scanner.nextLine().trim();
                if (fileName.equalsIgnoreCase("exit")) {
                    return;
                }
                if (fileName.isEmpty()) {
                    System.out.println("\n*Invalid filename*\n");
                    continue;
                }

                System.out.print("\nYou entered: \"" + fileName + "\". Continue? (1 = Yes / 2 = No: ");
                String confirmFile = scanner.nextLine().trim();
                if (!confirmFile.equals("1")) {
                    fileName = "";
                }
            }

            File noteFile = coursePath.resolve(fileName + ".txt").toFile();

            new ProcessBuilder("vim", noteFile.getAbsolutePath()).inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\n*Error creating note: " + e.getMessage() + "*\n");
        }
    }

    public static void editNote() {
        File course = selectCourse();
        if (course == null) return;

        File note = selectNote(course);
        if (note == null) return;

        try {
            new ProcessBuilder("vim", note.getAbsolutePath()).inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("\n*Error editing note: " + e.getMessage() + "*\n");
        }
    }

    public static void deleteNote() {
        File course = selectCourse();
        if (course == null) return;

        File note = selectNote(course);
        if (note == null) return;

        System.out.print("Are you sure you want to delete this note? (1 = Yes / 2 = No: ");
        String confirm = scanner.nextLine().trim();
        if (!confirm.equals("1")) {
            System.out.println("\n*Deletion cancelled*\n");
            return;
        }

        if (note.delete()) {
            System.out.println("\n*Note deleted successfully*\n");
        } else {
            System.out.println("\n*Failed to delete note*\n");
        }
    }

    public static void viewNote() {
        File course = selectCourse();
        if (course == null) return;

        File note = selectNote(course);
        if (note == null || !note.exists()) {
            return;
        }

        System.out.println("\n" + Interface.ConsoleColors.GREEN + "\n\nViewing: " + note.getName() + Interface.ConsoleColors.RESET);
        try (BufferedReader reader = new BufferedReader(new FileReader(note))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(Interface.ConsoleColors.YELLOW + line + Interface.ConsoleColors.RESET);
            }
            System.out.println("\n\n");
        } catch (IOException e) {
            System.out.println("\n*Error reading note: " + e.getMessage() + "*\n");
        }
    }

    private static File selectCourse() {
        File notesDir = new File(NOTES_DIR);
        File[] courses = notesDir.listFiles(File::isDirectory);

        if (courses == null || courses.length == 0) {
            System.out.println("\n*No course notes found*\n");
            return null;
        }

        System.out.println("\nCourses:");
        for (int i = 0; i < courses.length; i++) {
            System.out.println(i + ". " + courses[i].getName());
        }
        System.out.print("Select a course number (or type 'exit' to cancel): ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
            return null;
        }
        try {
            int choice = Integer.parseInt(input);
            if (choice < 0 || choice >= courses.length) return null;
            return courses[choice];
        } catch (NumberFormatException e) {
            System.out.println("\n*Invalid input*\n");
            return null;
        }
    }

    private static File selectNote(File course) {
        File[] notes = course.listFiles((dir, name) -> name.endsWith(".txt"));
        if (notes == null || notes.length == 0) {
            System.out.println("\n*No notes found for this course*\n");
            return null;
        }

        System.out.println("\nNotes:");
        for (int i = 0; i < notes.length; i++) {
            System.out.println(i + ". " + notes[i].getName());
        }
        System.out.print("Select a note number (or type 'exit' to cancel): ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
            return null;
        }
        try {
            int choice = Integer.parseInt(input);
            if (choice < 0 || choice >= notes.length) return null;
            return notes[choice];
        } catch (NumberFormatException e) {
            System.out.println("\n*Invalid input*\n");
            return null;
        }
    }

    private static String pickCourseFromCSV() {
        List<String> courseNames = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\braed\\AcademicProjects\\StudyStack\\StudyStack\\courses.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    courseNames.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("\n*Error reading courses.csv: " + e.getMessage() + "*\n");
            return null;
        }

        if (courseNames.isEmpty()) {
            System.out.println("\n*No courses found in CSV*\n");
            return null;
        }

        System.out.println("\nCourses:");
        for (int i = 0; i < courseNames.size(); i++) {
            System.out.println(i + ". " + courseNames.get(i));
        }
        System.out.print("Select a course number (or type 'exit' to cancel): ");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("exit")) {
            return null;
        }
        try {
            int choice = Integer.parseInt(input);
            if (choice < 0 || choice >= courseNames.size()) return null;
            return courseNames.get(choice);
        } catch (NumberFormatException e) {
            System.out.println("\n*Invalid input*\n");
            return null;
        }
    }
}
