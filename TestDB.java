import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/company", "root", "Archana@13"
            );
            System.out.println("✅ Connected to database successfully!");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
