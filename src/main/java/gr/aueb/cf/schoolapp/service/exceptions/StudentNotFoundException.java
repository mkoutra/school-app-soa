package gr.aueb.cf.schoolapp.service.exceptions;

import gr.aueb.cf.schoolapp.model.Student;

public class StudentNotFoundException extends Exception {
    private final static long serialVersionUID = 1L;

    public StudentNotFoundException(String s) {
        super(s);
    }

    public StudentNotFoundException(Student student) {
        super("Student with id: " + student.getId() + "was not found");

    }
}
