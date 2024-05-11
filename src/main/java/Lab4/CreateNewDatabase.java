package Lab4;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateNewDatabase {
    public static void createNewDatabase(String fileName) {
        String url = "jdbc:sqlite:D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources\\" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println("Error creating database: " + e.getMessage());
        }
    }
}
