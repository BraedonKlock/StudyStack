package StudyStack;

public class StudyStackException extends Exception {
	public  StudyStackException(String message) {
		System.err.println("Exception: " + message);
	}
}