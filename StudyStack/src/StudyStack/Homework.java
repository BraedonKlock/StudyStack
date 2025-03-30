package StudyStack;

public class Homework {
	private String name;
	private String dueDate;
	
	public Homework(String name, String dueDate) {
		this.name = name;
		this.dueDate = dueDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String quantity) {
		this.dueDate = quantity;
	}
	
	@Override
	public String toString() {
		return name + "\nDue date: " + dueDate;
	}
}