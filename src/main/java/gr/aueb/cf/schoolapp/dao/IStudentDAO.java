package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.Student;

import java.util.List;

public interface IStudentDAO {
    Student insert(Student student) throws StudentDAOException;
    Student update(Student student) throws StudentDAOException;
    void delete(Integer id) throws StudentDAOException;
    Student getById(Integer id) throws StudentDAOException;
    List<Student> getByLastname(String lastname) throws StudentDAOException;
}
