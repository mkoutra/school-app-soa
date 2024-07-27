package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements IStudentDAO {
    @Override
    public Student insert(Student student) throws StudentDAOException {
        String firstname = student.getFirstname();
        String lastname = student.getLastname();

        String sql = "INSERT INTO students (firstname, lastname) VALUES (?, ?);";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, firstname);
            ps.setString(2, lastname);

            ps.executeUpdate();

            return student;
        } catch (SQLException e) {
            throw new StudentDAOException("SQL error in insert student: " + student);
        }
    }

    @Override
    public Student update(Student student) throws StudentDAOException {
        Integer id = student.getId();
        String firstname = student.getFirstname();
        String lastname = student.getLastname();

        String sql = "UPDATE students SET firstname = ?, lastname = ? WHERE id = ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setInt(3, id);

            ps.executeUpdate();

            return student;
        } catch (SQLException e1) {
            throw new StudentDAOException("SQL error in update student: " + student);
        }
    }

    @Override
    public void delete(Integer id) throws StudentDAOException {
        String sql = "DELETE FROM students WHERE id = ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e1) {
            throw new StudentDAOException("SQL error in delete student with id: " + id);
        }
    }

    @Override
    public Student getById(Integer id) throws StudentDAOException {
        Student student = null;

        String sql = "SELECT id, firstname, lastname FROM students WHERE id = ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                student = new Student (
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname"));
            }

            return student;
        } catch (SQLException e1) {
            throw new StudentDAOException("SQL error in get student by id: " + id);
        }
    }

    @Override
    public List<Student> getByLastname(String lastname) throws StudentDAOException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, firstname, lastname FROM students WHERE lastname LIKE ?;";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lastname + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("id"),
                    rs.getString("firstname"),
                    rs.getString("lastname"))
                );
            }
            return students;
        } catch (SQLException e1) {
            throw new StudentDAOException("SQL error in get student by lastname: " + lastname);
        }
    }
}
