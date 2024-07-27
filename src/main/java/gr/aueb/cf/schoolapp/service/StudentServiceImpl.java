package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IStudentDAO;
import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dto.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.exceptions.StudentNotFoundException;

import java.util.List;

public class StudentServiceImpl implements IStudentService {

    private final IStudentDAO studentDAO;

    public StudentServiceImpl(IStudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student insertStudent(StudentInsertDTO dto) throws StudentDAOException {
        try {
            Student student = mapToStudent(dto);
            return studentDAO.insert(student);
        } catch (StudentDAOException e1) {
            e1.printStackTrace();
            throw e1;
        }
    }

    @Override
    public Student updateStudent(StudentUpdateDTO dto) throws StudentNotFoundException, StudentDAOException {
        try {
            Student student = mapToStudent(dto);
            if (studentDAO.getById(student.getId()) == null) {
                throw new StudentNotFoundException("Student with id: " + dto.getId() + "was not found");
            }
            return studentDAO.update(student);
        } catch (StudentDAOException | StudentNotFoundException e1) {
            e1.printStackTrace();
            throw e1;
        }
    }

    @Override
    public void deleteStudent(Integer id) throws StudentNotFoundException, StudentDAOException {
        try {
            if (studentDAO.getById(id) == null) {
                throw new StudentNotFoundException("Student was not found");
            }
            studentDAO.delete(id);
        } catch (StudentDAOException | StudentNotFoundException e1) {
            e1.printStackTrace();
            throw e1;
        }
    }

    @Override
    public Student getStudentById(Integer id) throws StudentNotFoundException, StudentDAOException {
        try {
            Student student = studentDAO.getById(id);
            if (student == null) {
                throw new StudentNotFoundException("Student was not found");
            }
            return student;
        } catch (StudentDAOException | StudentNotFoundException e1) {
            e1.printStackTrace();
            throw e1;
        }
    }

    @Override
    public List<Student> getStudentByLastname(String lastname) throws StudentDAOException {
        try {
            return studentDAO.getByLastname(lastname);
        } catch (StudentDAOException e1) {
            e1.printStackTrace();
            throw e1;
        }
    }

    private Student mapToStudent(StudentInsertDTO dto) {
        return new Student(null, dto.getFirstname(), dto.getLastname());
    }

    private Student mapToStudent(StudentUpdateDTO dto) {
        return new Student(dto.getId(), dto.getFirstname(), dto.getLastname());
    }
}
