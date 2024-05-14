package Lab4ParseAndWrite;
import java.sql.*;
import java.util.List;
public class WriteReactorFromPris {
    public void write(List<List<String>> dataList) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources\\reactors.sqlite")) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS ReactorsPris (" +
                    "name TEXT PRIMARY KEY, " +
                    "country TEXT, " +
                    "status TEXT, " +
                    "type TEXT, " +
                    "owner TEXT, " +
                    "operator TEXT, " +
                    "thermalCapacity INTEGER, " +
                    "firstGridConnection INTEGER, " +
                    "loadFactor REAL, " +
                    "suspendedDate INTEGER, " +
                    "permanentShutdownDate INTEGER, " +
                    "FOREIGN KEY (type) REFERENCES ReactorsTypes(type))";

            try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
                statement.executeUpdate();
            }
            String insertSQL = "INSERT OR REPLACE INTO ReactorsPris (name, country, status, type, owner, operator, thermalCapacity, firstGridConnection, loadFactor, suspendedDate, permanentShutdownDate)    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

                // Вставка данных
                for (List<String> rowData : dataList) {
                    preparedStatement.setString(1, rowData.get(0));
                    preparedStatement.setString(2, rowData.get(1));
                    preparedStatement.setString(3, rowData.get(2));
                    preparedStatement.setString(4, rowData.get(3));
                    preparedStatement.setString(5, rowData.get(4));
                    preparedStatement.setString(6, rowData.get(5));
                    Integer thermalCapacity = parseToInt(rowData.get(6));
                    if (thermalCapacity != null) {
                        preparedStatement.setInt(7, thermalCapacity);
                    } else {
                        preparedStatement.setInt(7, 85);
                    }
                    Integer firstGridConnection = parseToInt(rowData.get(7));
                    if (firstGridConnection != null) {
                        preparedStatement.setInt(8, firstGridConnection);
                    } else {
                        preparedStatement.setNull(8, Types.INTEGER);
                    }
                    String loadFactor = rowData.get(8);
                    if (loadFactor != null && !rowData.get(8).equalsIgnoreCase("NC")) {
                        Double loadFactorD = parseToDouble(rowData.get(8));
                        preparedStatement.setDouble(9, loadFactorD);
                    } else {
                        preparedStatement.setDouble(9, 90.0);
                    }
                    Integer suspendedDate = parseToInt(rowData.get(9));
                    if (suspendedDate != null) {
                        preparedStatement.setInt(10, suspendedDate);
                    } else {
                        preparedStatement.setNull(10, Types.INTEGER);
                    }
                    Integer permanentShutdownDate = parseToInt(rowData.get(10));
                    if (permanentShutdownDate != null) {
                        preparedStatement.setInt(11, permanentShutdownDate);
                    } else {
                        preparedStatement.setNull(11, Types.INTEGER);
                    }
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Integer parseToInt(String value) {
        return value != null && !value.isEmpty() ? Integer.parseInt(value) : null;
    }

    private Double parseToDouble(String value) {
        return value != null && !value.isEmpty() ? Double.parseDouble(value) : null;
    }
}
