import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class StudentManagementSystem {
    private static final String FILE_NAME = "new.txt";
    private Map<String, Student> students;

    public StudentManagementSystem() {
        students = new HashMap<>();
        loadFromFile();
    }

    public void addStudent(String id, String name) {
        if (!students.containsKey(id)) {
            students.put(id, new Student(id, name));
            saveToFile();
        } else {
            System.out.println("Student ID already exists.");
        }
    }

    public void deleteStudent(String id) {
        if (students.containsKey(id)) {
            students.remove(id);
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void updateStudent(String id, String name) {
        if (students.containsKey(id)) {
            students.get(id).setName(name);
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void addCourse(String studentId, String course, double grade) {
        if (students.containsKey(studentId)) {
            students.get(studentId).addCourse(course, grade);
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void removeCourse(String studentId, String course) {
        if (students.containsKey(studentId)) {
            students.get(studentId).removeCourse(course);
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayStudent(String id) {
        if (students.containsKey(id)) {
            System.out.println(students.get(id));
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayAllStudents() {
        for (Student student : students.values()) {
            System.out.println(student);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (Map<String, Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing student data found. Starting fresh.");
            students = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.addStudent("1", "John Doe");
        sms.addStudent("2", "Jane Smith");
        sms.addCourse("1", "Math", 95);
        sms.addCourse("1", "Science", 90);
        sms.addCourse("2", "Math", 85);
        sms.displayAllStudents();
    }
}
