package Lab4;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
public class ReadFromDB {
    public static void read(String path){
        path = "jdbc:sqlite:" + path.replaceAll("\\\\", "\\\\\\\\");
        Connection connection = null;
        try {
            // Установка соединения с базой данных
            connection = DriverManager.getConnection(path);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ReactorsPris");

            // Цикл по результатам запроса
            while (resultSet.next()) {
                String reactorName = resultSet.getString("name");
                String country = resultSet.getString("country");
                String type = resultSet.getString("type");

                // Получаем данные о регионе
                String region = getRegion(connection, country);

                // Получаем данные об операторе
                String operator = getOperator(connection, reactorName);

                // Получаем данные о burnup и thermalCapacity
                int burnup = getBurnup(connection, type);
                int thermalCapacity = resultSet.getInt("thermalCapacity");

                // Выводим данные о реакторе
                System.out.println("Reactor: " + reactorName);
                System.out.println("Country: " + country);
                System.out.println("Region: " + region);
                System.out.println("Operator: " + operator);
                System.out.println("Burnup: " + burnup);
                System.out.println("Thermal Capacity: " + thermalCapacity);

                // Получаем loadfactor для данного реактора
                Map<Integer, Double> loadFactors = getLoadFactors(connection, reactorName);
                System.out.println("Load Factors:");
                for (Map.Entry<Integer, Double> entry : loadFactors.entrySet()) {
                    System.out.println("Year: " + entry.getKey() + ", Load Factor: " + entry.getValue());
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод для получения региона по стране
    private static String getRegion(Connection connection, String country) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT region FROM Countries WHERE country = ?");
        preparedStatement.setString(1, country);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getString("region");
    }

    // Метод для получения оператора по имени реактора
    private static String getOperator(Connection connection, String reactorName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT operator FROM ReactorsPris WHERE name = ?");
        preparedStatement.setString(1, reactorName);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getString("operator");
    }

    // Метод для получения burnup по типу реактора
    private static int getBurnup(Connection connection, String type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT burnup FROM ReactorsTypes WHERE type = ?");
        preparedStatement.setString(1, type);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getInt("burnup");
    }

    // Метод для получения loadfactor для данного реактора
    private static Map<Integer, Double> getLoadFactors(Connection connection, String reactorName) throws SQLException {
        Map<Integer, Double> loadFactors = new HashMap<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT year, loadfactor FROM LoadFactor WHERE reactor = ?");
        preparedStatement.setString(1, reactorName);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int year = resultSet.getInt("year");
            double loadFactor = resultSet.getDouble("loadfactor");
            loadFactors.put(year, loadFactor);
        }
        return loadFactors;
    }
}