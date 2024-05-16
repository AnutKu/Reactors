package Lab4;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
public class ReadFromDB {
    public static void read(String path, ReactorHolder reactorHolder) throws IOException, SQLException {
        path = "jdbc:sqlite:" + path.replaceAll("\\\\", "\\\\\\\\");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(path);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ReactorsPris");
            while (resultSet.next()) {
                String reactorName = resultSet.getString("name");
                String type = resultSet.getString("type");
                String country = resultSet.getString("country");
                String region = getRegion(connection, country);
                String operator = resultSet.getString("operator");
                int burnup = getBurnup(connection, type);
                int thermalCapacity = resultSet.getInt("thermalCapacity");
                int firstGridConnection = resultSet.getInt("firstGridConnection");
                HashMap<Integer, Double> loadFactors = getLoadFactors(connection, reactorName);
                Reactor newreactor = new Reactor(reactorName, type, country, region, operator, burnup, thermalCapacity, firstGridConnection, loadFactors);
                reactorHolder.addReactor(newreactor);
            }
        } catch (SQLException e) {
            throw  e;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
    }

    private static String getRegion(Connection connection, String country) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT region FROM Countries WHERE country = ?");
        preparedStatement.setString(1, country);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getString("region");
    }


    private static int getBurnup(Connection connection, String type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT burnup FROM ReactorsTypes WHERE type = ?");
        preparedStatement.setString(1, type);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.getInt("burnup");
    }

    private static HashMap<Integer, Double> getLoadFactors(Connection connection, String reactorName) throws SQLException {
        HashMap<Integer, Double> loadFactors = new HashMap<>();
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