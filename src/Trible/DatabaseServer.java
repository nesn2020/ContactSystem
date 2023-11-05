package Trible;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServer {
    Connection connection;

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/contact?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "cyhjy";

    //注册驱动程序放在代码块中，每次只能注册一次
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //创建getConnection对象，用来获得connection对象
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return connection;
    }

    public List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM contacts";
            Connection conn = DatabaseServer.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone_number = resultSet.getString("phone_number");
                contacts.add(new Contact(id, name, address, phone_number));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public boolean addContact(Contact contact) {
        try {
            String sql = "INSERT INTO contacts (name, address, phone_number) VALUES (?, ?, ?)";
            Connection conn = DatabaseServer.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getAddress());
            statement.setString(3, contact.getPhone());
            int rowsAffected = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()) {
                int generatedId = rs.getInt(1);
                contact.setId(generatedId);
            }
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateContact(Contact contact) {
        try {
            String sql = "UPDATE contacts SET name=?, address=?, phone_number=? WHERE id=?";
            Connection conn = DatabaseServer.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, contact.getName());
            statement.setString(2, contact.getAddress());
            statement.setString(3, contact.getPhone());
            statement.setInt(4, contact.getId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteContact(int contactId) {
        try {
            String sql = "DELETE FROM contacts WHERE id=?";
            Connection conn = DatabaseServer.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, contactId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

