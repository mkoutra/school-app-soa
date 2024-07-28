package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.dao.util.DBHelper;
import gr.aueb.cf.schoolapp.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentDAOTest {
    private static IStudentDAO studentDAO;

    @BeforeAll
    public static void beforeAll() throws SQLException{
        studentDAO = new StudentDAOImpl();
        DBHelper.eraseData();
    }

    @BeforeEach
    public void setup() throws StudentDAOException {
        fillWithDummyData();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseData();
    }

    @Test
    void getById() throws StudentDAOException {
        Student student = studentDAO.getById(1);
        assertEquals("Lionel", student.getFirstname());
        assertEquals("Messi", student.getLastname());
    }

    @Test
    void insert() throws StudentDAOException {
        Student student = new Student(4, "Zinedine", "Zidane");
        Student insertedStudent = studentDAO.insert(student);

        assertEquals(4, insertedStudent.getId());
        assertEquals("Zinedine", insertedStudent.getFirstname());
        assertEquals("Zidane", insertedStudent.getLastname());
    }

    @Test
    void update() throws StudentDAOException {
        Student student = studentDAO.getById(3);
        student.setFirstname("Federico");
        student.setLastname("Valverde");
        Student updatedStudent = studentDAO.update(student);

        assertEquals(3, updatedStudent.getId());
        assertEquals("Federico", updatedStudent.getFirstname());
        assertEquals("Valverde", updatedStudent.getLastname());
    }

    @Test
    void delete() throws StudentDAOException {
        studentDAO.delete(3);
        Student student = studentDAO.getById(3);

        assertNull(student);
    }

    @Test
    void getByLastName() throws StudentDAOException {
        List<Student> students = studentDAO.getByLastname("Mod");

        assertEquals(1, students.size());
        assertEquals("Modric", students.get(0).getLastname());

        students = studentDAO.getByLastname("abc");
        assertEquals(0, students.size());

        students = studentDAO.getByLastname("M");
        assertEquals(3, students.size());
    }

    private static void fillWithDummyData() throws StudentDAOException {
        studentDAO.insert(new Student(null, "Lionel", "Messi"));
        studentDAO.insert(new Student(null, "Luka", "Modric"));
        studentDAO.insert(new Student(null, "Kylian", "Mbappe"));
    }
}