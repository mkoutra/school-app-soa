package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDAOImpl implements ITeacherDAO {
    @Override
    public Teacher insert(Teacher teacher) throws TeacherDAOException {
        String sql = "INSERT INTO teachers (firstname, lastname) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // Extract model info
            String firstname = teacher.getFirstname();
            String lastname = teacher.getLastname();

            ps.setString(1, firstname);
            ps.setString(2, lastname);

            int n = ps.executeUpdate();
            // Logging
            return teacher; // TBD We should return the created teacher (including the auto-increment id)
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new TeacherDAOException("SQL error in insert teacher: " + teacher);
        }
    }

    @Override
    public Teacher update(Teacher teacher) throws TeacherDAOException {
        String sql = "UPDATE teachers SET firstname = ?, lastname = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // Extract model info
            int id = teacher.getId();
            String firstname = teacher.getFirstname();
            String lastname = teacher.getLastname();

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setInt(3, id);

            int n = ps.executeUpdate();

            // Logging
            return teacher; // We should return the teacher before update or void
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new TeacherDAOException("SQL error in update teacher: " + teacher);
        }
    }

    @Override
    public void delete(Integer id) throws TeacherDAOException {
        String sql = "DELETE FROM teachers WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            // Logging
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new TeacherDAOException("SQL error in delete teacher with id = " + id);
        }

    }

    @Override
    public Teacher getById(Integer id) throws TeacherDAOException {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        Teacher teacher = null;
        ResultSet rs;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                teacher = new Teacher(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // logging
            throw new TeacherDAOException("SQL error in delete teacher with id = " + id);
        }
    }

    @Override
    public List<Teacher> getByLastname(String lastname) throws TeacherDAOException {
        return List.of();
    }
}
