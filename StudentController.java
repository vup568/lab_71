/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.util.ArrayList;
import java.util.Collections;
import Model.Student;
import util.Validation;

public class StudentController {
 // List of students
    private final ArrayList<Student> listOfStudent;

    // Validation utility for input validation
    private final Validation validation = new Validation();

    // ReportController for generating reports
    private final ReportController reportController = new ReportController();

    /**
     * Constructor to initialize the StudentController with a predefined set of
     * students
     */
    public StudentController() {
        this.listOfStudent = new ArrayList<>();

        // Adding some sample students to the list
        Student student = new Student("He18", "duong", "fa", "Java");
        listOfStudent.add(student);
        listOfStudent.add(new Student("He18", "duong", "fa", ".Net"));
        listOfStudent.add(new Student("He18", "duong", "fa1", ".Net"));
        listOfStudent.add(new Student("He17", "chien", "fa1", ".Net"));
    }

    /**
     * Constructor to initialize the StudentController with a given list of
     * students
     *
     * @param listOfStudent list of students
     */
    public StudentController(ArrayList<Student> listOfStudent) {
        this.listOfStudent = listOfStudent;
    }

    /**
     * Getter for the list of students
     *
     * @return list of students
     */
    public ArrayList<Student> getListOfStudent() {
        return listOfStudent;
    }

    /**
     * Function to add a new student by taking input from the user
     */
    public void addNewStudent() {
        int countNumberOfStudentEnter = 0;
        while (true) {
            System.out.println("===================");
            System.out.print("Enter ID: ");
            String id = validation.getString();

            // Validate and get student name
            String studentName = validation.validateStudentName();

            System.out.print("Enter Semester: ");
            String semester = validation.getString();

            // Validate and get course name
            String courseName = validation.validateCourseName();

            // Create and add new student to the list
            Student newStudent = new Student(id, studentName, semester, courseName);
            this.listOfStudent.add(newStudent);
            countNumberOfStudentEnter++;

            // Ask if the user wants to continue adding more students
            if (countNumberOfStudentEnter >= 1) {
                if (!validation.validateConfirm()) {
                    return;
                }
            }
        }
    }

    /**
     * Function to find students by name
     *
     * @param name name of the student to search for
     * @return list of students containing the name
     * @throws Exception if the student list is empty
     */
    public ArrayList<Student> findByName(String name) throws Exception {
        if (listOfStudent.isEmpty()) {
            throw new Exception("List of students is empty");
        }

        // List to store students that match the search name
        ArrayList<Student> listStudentFindByName = new ArrayList<>();
        for (Student student : listOfStudent) {
            if (student.getStudentName().toLowerCase().contains(name.toLowerCase())) {
                listStudentFindByName.add(student);
            }
        }
        return listStudentFindByName;
    }

    /**
     * Function to find students by name and sort them alphabetically by student
     * name
     */
    public void findAndSort() {
        if (listOfStudent.isEmpty()) {
            System.out.println("List of students is empty, can't find and sort");
            return;
        }

        // Get the name to search for
        String nameSearch = validation.validateStudentName();
        try {
            // Find students by name
            ArrayList<Student> listOfStudentFindByName = findByName(nameSearch);
            if (listOfStudentFindByName.isEmpty()) {
                System.out.println("No students found with the specified name");
                return;
            }

            // Sort the found students by name
            Collections.sort(listOfStudentFindByName, (Student student1, Student student2) -> {
                return student1.getStudentName().compareTo(student2.getStudentName());
            });

            // Print sorted students
            for (Student student : listOfStudentFindByName) {
                System.out.format("%-20s | %-15s | %-15s\n", student.getStudentName(), student.getSemester(), student.getCourseName());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Function to print the list of students
     */
    public void printList() {
        for (Student student : listOfStudent) {
            System.out.println(student);
        }
    }

    /**
     * Function to get a list of students by their ID
     *
     * @param ID student ID to search for
     * @return list of students matching the ID
     */
    public ArrayList<Student> getListStudentById(String ID) {
        ArrayList<Student> res = new ArrayList<>();
        for (Student student : this.listOfStudent) {
            if (student.getID().equalsIgnoreCase(ID)) {
                res.add(student);
            }
        }
        return res;
    }

    /**
     * Function to check if a student can be updated
     *
     * @param studentUpdate student object to update
     * @param indexStudentUpdate index of the student in the list
     * @return true if the update is allowed, false otherwise
     */
    private boolean checkCanUpdate(Student studentUpdate, int indexStudentUpdate) {
        for (int i = 0; i < listOfStudent.size(); i++) {
            Student studentCurrent = listOfStudent.get(i);
            boolean isSameIdAndCourseAndSameSemester
                    = studentCurrent.getID().equalsIgnoreCase(studentUpdate.getID())
                    && studentCurrent.getCourseName().equalsIgnoreCase(studentUpdate.getCourseName())
                    && studentCurrent.getSemester().equalsIgnoreCase(studentUpdate.getSemester());

            // Check if the same student is being updated at the same index
            if (isSameIdAndCourseAndSameSemester) {
                return (indexStudentUpdate == i);
            }
        }
        return true;
    }

    /**
     * Function to update a student's information
     *
     * @param studentToUpdate student object to update
     */
    public void updateStudent(Student studentToUpdate) {
        Student studentInList = null;
        int index = -1;

        // Find the student to update in the list
        for (int i = 0; i < listOfStudent.size(); i++) {
            Student student = listOfStudent.get(i);
            boolean isSameStudentInfo
                    = student.getID().equalsIgnoreCase(studentToUpdate.getID())
                    && student.getCourseName().equalsIgnoreCase(studentToUpdate.getCourseName())
                    && student.getStudentName().equalsIgnoreCase(studentToUpdate.getStudentName())
                    && student.getSemester().equalsIgnoreCase(studentToUpdate.getSemester());
            if (isSameStudentInfo) {
                studentInList = student;
                index = i;
                break;
            }
        }

        // Get new student information
        System.out.println("Enter New Name: ");
        String newName = validation.validateStudentName();
        System.out.println("Enter New Semester: ");
        String newSemester = validation.getString();
        System.out.println("Enter Course Name: ");
        String newCourse = validation.validateCourseName();

        Student newInfo = new Student(studentInList.getID(), newName, newSemester, newCourse);

        // Check if the student can be updated
        if (checkCanUpdate(newInfo, index)) {
            if (!newInfo.getStudentName().equalsIgnoreCase(studentToUpdate.getStudentName())) {
                for (Student student : listOfStudent) {
                    if (student.getID().equalsIgnoreCase(studentToUpdate.getID())) {
                        student.setStudentName(newName);
                    }
                }
            }
            Student studentToUpdateInList = listOfStudent.get(index);
            studentToUpdateInList.setStudentName(newName);
            studentToUpdateInList.setSemester(newSemester);
            studentToUpdateInList.setCourseName(newCourse);
        } else {
            System.out.println("Can't update");
        }
    }

    /**
     * Function to update or delete a student
     */
    public void updateOrDelete() {
        System.out.print("Enter ID: ");
        String idSearch = validation.getString();

        // Get the list of students matching the ID
        ArrayList<Student> listOfStudentFindById = this.getListStudentById(idSearch);
        if (listOfStudentFindById.isEmpty()) {
            System.out.println("No students found with ID: " + idSearch);
            return;
        }

        // Display found students and ask for selection
        for (int i = 0; i < listOfStudentFindById.size(); i++) {
            Student studentCurrent = listOfStudentFindById.get(i);
            System.out.format("%-15d%-20s%-15s%-15s%-15s\n", i + 1, 
                    studentCurrent.getID(),
                    studentCurrent.getStudentName(),
                    studentCurrent.getSemester(), studentCurrent.getCourseName());
        }

        System.out.print("Enter your choice: ");
        int id = validation.getIntBetween(0, listOfStudentFindById.size());

        System.out.println("Do you want to update or delete? (U/D): ");
        Student studentToUpdateOrDelete = listOfStudentFindById.get(id - 1);
        String inputConfirm = validation.getString();

        if (inputConfirm.equalsIgnoreCase("D")) {
            // Delete the student from the list
            listOfStudent.remove(studentToUpdateOrDelete);
        } else {
            // Update the student's information
            updateStudent(studentToUpdateOrDelete);
        }
    }

    /**
     * Function to generate and print a report based on the list of students
     */
    public void report() {
        if (listOfStudent.isEmpty()) {
            System.out.println("List of students is empty!");
            return;
        }

        // Clear the previous reports
        reportController.clearReport();

        // Generate reports based on student data
        for (Student student : listOfStudent) {
            int totalOfCourse = 0;
            String studentName = student.getStudentName();
            String course = student.getCourseName();
            String studentId = student.getID();

            for (Student studentCountTotal : listOfStudent) {
                boolean isSameStudent = studentId.equalsIgnoreCase(studentCountTotal.getID())
                        && studentName.equalsIgnoreCase(studentCountTotal.getStudentName())
                        && course.equalsIgnoreCase(studentCountTotal.getCourseName());
                if (isSameStudent) {
                    totalOfCourse++;
                }
            }

            // Add report if it doesn't already exist
            reportController.addIfReportNotExitReport(studentId, studentName, course, totalOfCourse);
        }

        // Print the report
        reportController.printReport();
    }
}
