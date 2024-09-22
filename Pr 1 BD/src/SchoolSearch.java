import java.io.*;
import java.util.*;

public class SchoolSearch {

    // Клас для представлення студента
    static class Student {
        String lastName;
        String firstName;
        int grade;
        int classroom;
        int bus;
        String teacherLastName;
        String teacherFirstName;

        public Student(String lastName, String firstName, int grade, int classroom, int bus, String teacherLastName, String teacherFirstName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.grade = grade;
            this.classroom = classroom;
            this.bus = bus;
            this.teacherLastName = teacherLastName;
            this.teacherFirstName = teacherFirstName;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName + ", Grade: " + grade + ", Classroom: " + classroom +
                    ", Bus: " + bus + ", Teacher: " + teacherFirstName + " " + teacherLastName;
        }
    }

    // Метод для завантаження даних з файлу
    public static List<Student> loadStudents(String fileName) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    students.add(new Student(
                            parts[0], // StLastName
                            parts[1], // StFirstName
                            Integer.parseInt(parts[2]), // Grade
                            Integer.parseInt(parts[3]), // Classroom
                            Integer.parseInt(parts[4]), // Bus
                            parts[5], // TLastName
                            parts[6]  // TFirstName
                    ));
                }
            }
        }
        return students;
    }

    // Пошук студентів по прізвищу і вивід їх класу та вчителя
    public static void searchByLastName(List<Student> students, String lastName) {
        Set<String> uniqueStudents = new HashSet<>();
        boolean found = false;

        for (Student student : students) {
            if (student.lastName.equalsIgnoreCase(lastName)) {
                String studentInfo = student.firstName + " " + student.lastName +
                        " is in classroom " + student.classroom +
                        " with teacher " + student.teacherFirstName + " " + student.teacherLastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("There is no student with this lastname in the database.");
        }
    }

    // Пошук студентів по прізвищу і вивід їх автобусного маршруту
    public static void searchBusByLastName(List<Student> students, String lastName) {
        Set<String> uniqueBusRoutes = new HashSet<>();
        boolean found = false;

        for (Student student : students) {
            if (student.lastName.equalsIgnoreCase(lastName)) {
                String busInfo = student.firstName + " " + student.lastName +
                        " rides bus number " + student.bus;

                if (uniqueBusRoutes.add(busInfo)) {
                    System.out.println(busInfo);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("There is no student with this lastname in the database.");
        }
    }

    // Пошук студентів по прізвищу вчителя
    public static void searchByTeacherLastName(List<Student> students, String teacherLastName) {
        Set<String> uniqueStudents = new HashSet<>();
        boolean found = false;

        for (Student student : students) {
            if (student.teacherLastName.equalsIgnoreCase(teacherLastName)) {
                String studentInfo = student.firstName + " " + student.lastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("There is no teacher with this lastname in the database.");
        }
    }

    // Пошук студентів, які їдуть певним автобусом
    public static void searchByBusRoute(List<Student> students, int busRoute) {
        Set<String> uniqueStudents = new HashSet<>();
        boolean found = false;

        for (Student student : students) {
            if (student.bus == busRoute) {
                String studentInfo = student.firstName + " " + student.lastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No student uses this bus.");
        }
    }

    // Пошук всіх студентів певної класної кімнати
    public static void searchByClassroom(List<Student> students, int classroom) {
        Set<String> uniqueStudents = new HashSet<>();
        boolean found = false;

        for (Student student : students) {
            if (student.classroom == classroom) {
                String studentInfo = student.firstName + " " + student.lastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("There is no student in this classroom in the database.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students;

        // Завантаження даних
        try {
            students = loadStudents("students.txt");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        String command;
        do {
            System.out.println("\nSchool Search Menu:");
            System.out.println("S (Student) <lastname> - Search by student's last name (classroom and teacher)");
            System.out.println("S (Student) <lastname> B (Bus) - Search by student's last name (bus route)");
            System.out.println("T (Teacher) <lastname> - Search by teacher's last name (list of students)");
            System.out.println("C (Classroom) <number> - Search by classroom number (list of students)");
            System.out.println("B (Bus) <number> - Search by bus route (list of students)");
            System.out.println("Q - Quit");

            System.out.print("Enter your choice: ");
            command = scanner.nextLine().trim().toUpperCase();
            String[] parts = command.split(" ");
            System.out.println();

            long startTime = System.nanoTime();

            switch (parts[0]) {
                case "S":
                    if (parts.length == 2) {
                        searchByLastName(students, parts[1]);
                    } else if (parts.length == 3 && parts[2].equals("B")) {
                        searchBusByLastName(students, parts[1]);
                    } else {
                        System.out.println("Incorrect command for student search.");
                    }
                    break;
                case "T":
                    if (parts.length == 2) {
                        searchByTeacherLastName(students, parts[1]);
                    } else {
                        System.out.println("Incorrect command for teacher search.");
                    }
                    break;
                case "C":
                    if (parts.length == 2) {
                        try {
                            int classroom = Integer.parseInt(parts[1]);
                            searchByClassroom(students, classroom);
                        } catch (NumberFormatException e) {
                            System.out.println("Classroom number is incorrect.");
                        }
                    } else {
                        System.out.println("Incorrect command for classroom search.");
                    }
                    break;
                case "B":
                    if (parts.length == 2) {
                        try {
                            int busRoute = Integer.parseInt(parts[1]);
                            searchByBusRoute(students, busRoute);
                        } catch (NumberFormatException e) {
                            System.out.println("Bus route number is incorrect.");
                        }
                    } else {
                        System.out.println("Incorrect command for bus route search.");
                    }
                    break;
                case "Q":
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

            long endTime = System.nanoTime();
            long durationInMilliseconds = (endTime - startTime) / 1_000_000;

            System.out.println("\nSearch completed in: " + durationInMilliseconds + " ms");

        } while (!command.equals("Q"));

        scanner.close();
    }
}