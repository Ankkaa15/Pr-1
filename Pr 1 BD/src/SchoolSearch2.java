import java.io.*;
import java.util.*;

public class SchoolSearch2 {

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

    public static void searchByLastName(List<Student> students, String lastName) {
        Set<String> uniqueStudents = new HashSet<>();

        for (Student student : students) {
            if (student.lastName.equalsIgnoreCase(lastName)) {
                String studentInfo = student.firstName + " " + student.lastName +
                        " is in classroom " + student.classroom +
                        " with teacher " + student.teacherFirstName + " " + student.teacherLastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                }
            }
        }
    }

    public static void searchBusByLastName(List<Student> students, String lastName) {
        Set<String> uniqueBusRoutes = new HashSet<>();

        for (Student student : students) {
            if (student.lastName.equalsIgnoreCase(lastName)) {
                String busInfo = student.firstName + " " + student.lastName +
                        " rides bus number " + student.bus;

                if (uniqueBusRoutes.add(busInfo)) {
                    System.out.println(busInfo);
                }
            }
        }
    }

    public static void searchByTeacherLastName(List<Student> students, String teacherLastName) {
        Set<String> uniqueStudents = new HashSet<>();

        for (Student student : students) {
            if (student.teacherLastName.equalsIgnoreCase(teacherLastName)) {
                String studentInfo = student.firstName + " " + student.lastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                }
            }
        }
    }

    public static void searchByBusRoute(List<Student> students, int busRoute) {
        Set<String> uniqueStudents = new HashSet<>();

        for (Student student : students) {
            if (student.bus == busRoute) {
                String studentInfo = student.firstName + " " + student.lastName;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                }
            }
        }
    }

    public static void searchByGrade(List<Student> students, int grade) {
        if (grade < 1 || grade > 6) {
            System.out.println("Invalid grade level. Please enter a grade between 1 and 6.");
            return;
        }

        Set<String> uniqueStudents = new HashSet<>();

        for (Student student : students) {
            if (student.grade == grade) {
                String studentInfo = student.firstName + " " + student.lastName +
                        " is in grade " + student.grade;

                if (uniqueStudents.add(studentInfo)) {
                    System.out.println(studentInfo);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = null;

        try {
            students = loadStudents("students.txt");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.print("\nEnter a command (S[tudent], T[eacher], C[lassroom], B[us], Q[uit]): ");
            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("Q")) {
                break;
            }

            long startTime = System.nanoTime();

            if (command.startsWith("S")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    String lastName = parts[1];
                    searchByLastName(students, lastName);
                } else if (parts.length == 3 && parts[2].equalsIgnoreCase("B")) {
                    String lastName = parts[1];
                    searchBusByLastName(students, lastName);
                } else {
                    System.out.println("Invalid S command format.");
                }
            } else if (command.startsWith("T")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    String teacherLastName = parts[1];
                    searchByTeacherLastName(students, teacherLastName);
                } else {
                    System.out.println("Invalid T command format.");
                }
            } else if (command.startsWith("C")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        int classroom = Integer.parseInt(parts[1]);
                        searchByBusRoute(students, classroom);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid classroom number.");
                    }
                } else {
                    System.out.println("Invalid C command format.");
                }
            } else if (command.startsWith("B")) {
                String[] parts = command.split(" ");
                if (parts.length == 2) {
                    try {
                        int busRoute = Integer.parseInt(parts[1]);
                        searchByBusRoute(students, busRoute);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid bus route number.");
                    }
                } else {
                    System.out.println("Invalid B command format.");
                }
            } else {
                System.out.println("Unknown command. Please try again.");
            }

            long endTime = System.nanoTime();
            long durationInMilliseconds = (endTime - startTime) / 1_000_000;
            System.out.println("\nSearch completed in: " + durationInMilliseconds + " ms");
        }

        scanner.close();
    }
}
