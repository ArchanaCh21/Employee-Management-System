import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

// Employee class
class Employee {
    int id;
    String name;
    String department;
    double salary;

    Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Dept: " + department + " | Salary: " + salary);
    }
}

// Main management system
public class EmployeeManagementSystem {
    static ArrayList<Employee> employees = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Employee Management System ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> displayAll();
                case 3 -> searchEmployee();
                case 4 -> updateEmployee();

                case 5 -> deleteEmployee();
                case 6 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice. Try again!");
            }
        } while (choice != 6);
    }

    // Add new employee
    static void addEmployee() {
    try {
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/company", "root", "Archana@13"
        );

        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        String query = "INSERT INTO employees (id, name, department, salary) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setString(3, dept);
        pst.setDouble(4, salary);

        pst.executeUpdate();
        System.out.println("✅ Employee added successfully!");
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Display all employees
    static void displayAll() {
    try {
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/company", "root", "Archana@13"
        );

        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM employees");

        while (rs.next()) {
            System.out.println(
                "ID: " + rs.getInt("id") +
                " | Name: " + rs.getString("name") +
                " | Dept: " + rs.getString("department") +
                " | Salary: " + rs.getDouble("salary")
            );
        }
        con.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    // Search by ID
    static void searchEmployee() {
        System.out.print("Enter Employee ID to search: ");
        int id = sc.nextInt();
        for (Employee e : employees) {
            if (e.id == id) {
                e.display();
                return;
            }
        }
        System.out.println("❌ Employee not found.");
    }

    // Update details
    static void updateEmployee() {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        for (Employee e : employees) {
            if (e.id == id) {
                System.out.print("Enter new Name: ");
                e.name = sc.nextLine();
                System.out.print("Enter new Department: ");
                e.department = sc.nextLine();
                System.out.print("Enter new Salary: ");
                e.salary = sc.nextDouble();
                System.out.println("✅ Employee updated successfully!");
                return;
            }
        }
        System.out.println("❌ Employee not found.");
    }

    // Delete by ID
    static void deleteEmployee() {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        for (Employee e : employees) {
            if (e.id == id) {
                employees.remove(e);
                System.out.println("✅ Employee deleted successfully!");
                return;
            }
        }
        System.out.println("❌ Employee not found.");
    }
}
