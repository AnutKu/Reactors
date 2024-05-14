package Lab4ParseAndWrite;

import Lab3.ReactorType;
import Lab3.ReactorTypeHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


public class WriteRactorsTypesFromFile {
    public static void write(ReactorTypeHolder reactorTypeHolder) {
        Map<String, ReactorType> fileReactors = reactorTypeHolder.getReactorMap();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources\\reactors.sqlite")) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS ReactorsTypes (type TEXT PRIMARY KEY, burnup INT)";
            try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
                statement.executeUpdate();
            }
            String insertSQL = "INSERT OR REPLACE INTO ReactorsTypes (type, burnup) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                for (Map.Entry<String, ReactorType> entry : fileReactors.entrySet()) {
                    ReactorType reactorType = entry.getValue();
                    String type = reactorType.getReactorClass();
                    Double burnupDouble = reactorType.getBurnup();
                    System.out.println(type);
                    int burnup = burnupDouble.intValue();
                    statement.setString(1, type);
                    statement.setDouble(2, burnup);
                    statement.executeUpdate();
                }
                statement.setString(1, "LWGR");
                statement.setDouble(2, 37);
                statement.executeUpdate();
                statement.setString(1, "GCR");
                statement.setDouble(2, 22);
                statement.executeUpdate();
                statement.setString(1, "FBR");
                statement.setDouble(2, 125);
                statement.setString(1, "HTGR");
                statement.setDouble(2, 200);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

