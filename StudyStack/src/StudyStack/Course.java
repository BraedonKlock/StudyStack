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
        sb.append(name).append("\n");
        sb.append("--Course code--\n").append(courseCode).append("\n");

        sb.append("\n---HOMEWORK---\n");
        for (Homework homework : homework) {
            sb.append(homework.getName()).append("|");
            sb.append("Due date: ").append(homework.getDueDate()).append("\n");
        }
        return sb.toString();
    }
}