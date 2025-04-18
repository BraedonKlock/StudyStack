package StudyStack;

import java.util.ArrayList;
import java.util.List;

/*
 * This class represents a course object that can store associated homework.
 * Each course contains an ArrayList of homework objects, allowing
 * you to define all the homework due for a specific course.
 */

public class Course {
	private String name;
    private String courseCode;
    private List<Homework> homework; // homework ArrayList

    public Course(String name, String courseCode) {
        this.name = name;
        this.courseCode = courseCode;
        this.homework = new ArrayList<>();
    }

    public void addHomework(Homework hw) {
        homework.add(hw);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public List<Homework> getHomework() {
        return homework;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(Interface.ConsoleColors.YELLOW + "%-50s\n", name + Interface.ConsoleColors.RESET));
        sb.append(String.format("%s\n", courseCode + "\n"));
        sb.append(String.format("%-35s %-20s\n", "Homework", "Due Date"));
        sb.append("-----------------------------------------------\n");

        for (Homework hw : homework) {
            sb.append(String.format("%-35s %-20s\n", hw.getName(), hw.getDueDate()));
        }
        return sb.toString();
    }
}