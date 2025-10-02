package dao;
import java.sql.*;
import java.util.*;
import model.User;
import util.DBConnection;
public class UserDAOImpl implements UserDAO {
    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO USERS(name, email, age) VALUES(?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getAge());
            ps.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE USERS SET name=?, email=?, age=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setInt(3, user.getAge());
            ps.setInt(4, user.getId());
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("User updated successfully!");
            else
                System.out.println("User not found!");
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) {
        String sql = "DELETE FROM USERS WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("User deleted successfully!");
            else
                System.out.println("User not found!");
        } catch (SQLException e) {
            System.out.println("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public User getUserById(int id) {
        String sql = "SELECT * FROM USERS WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age"));
        } catch (SQLException e) {
            System.out.println("Error fetching user: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<User> searchByName(String name) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM USERS WHERE LOWER(name) LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age")));
            }
        } catch (SQLException e) {
            System.out.println("Error searching user: " + e.getMessage());
        }
        return list;
    }

    @Override
    public User searchByEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE email=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), rs.getInt("age"));
        } catch (SQLException e) {
            System.out.println("Error searching user: " + e.getMessage());
        }
        return null;
    }
}