package StudyStack;

import java.util.ArrayList;
import java.util.List;

public class DayOfWeek {
    private String name;
    private List<Task> tasks;

    public DayOfWeek(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void editTask(int index, String newName) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setName(newName);
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }
}