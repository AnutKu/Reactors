package Lab4;

import Lab3.Reactor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class WriteCountriesRegions{
    static HashMap<String, String> countriesAndRegions = new HashMap<>();

    public static void write() {
        fillMap();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources\\reactors.sqlite");

            // Создание таблицы Regions
            String createRegionsTableSQL = "CREATE TABLE IF NOT EXISTS Regions (\n"
                    + "    name TEXT PRIMARY KEY\n"
                    + ");";
            try (PreparedStatement statement = connection.prepareStatement(createRegionsTableSQL)) {
                statement.executeUpdate();
            }

            // Вставка значений в таблицу Regions
            String insertRegionSQL = "INSERT OR IGNORE INTO Regions (name) VALUES (?)";
            try (PreparedStatement insertRegionStmt = connection.prepareStatement(insertRegionSQL)) {
                for (String region : countriesAndRegions.values()) {
                    insertRegionStmt.setString(1, region);
                    insertRegionStmt.executeUpdate();
                }
            }

            // Создание таблицы Countries
            String createCountriesTableSQL = "CREATE TABLE IF NOT EXISTS Countries (\n"
                    + "    country TEXT PRIMARY KEY,\n"
                    + "    region TEXT,\n"
                    + "    FOREIGN KEY (region) REFERENCES Regions(name)\n"
                    + ");";
            try (PreparedStatement statement = connection.prepareStatement(createCountriesTableSQL)) {
                statement.executeUpdate();
            }

            // Вставка значений в таблицу Countries
            String insertCountrySQL = "INSERT INTO Countries (country, region) VALUES (?, ?)";
            try (PreparedStatement insertCountryStmt = connection.prepareStatement(insertCountrySQL)) {
                for (Map.Entry<String, String> entry : countriesAndRegions.entrySet()) {
                    insertCountryStmt.setString(1, entry.getKey());
                    insertCountryStmt.setString(2, entry.getValue());
                    insertCountryStmt.executeUpdate();
                }
            }

            connection.close();
            System.out.println("Tables created and populated successfully.");
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private static void fillMap() {
        countriesAndRegions.put("ARGENTINA", "South America");
        countriesAndRegions.put("ARMENIA", "Asia");
        countriesAndRegions.put("BELARUS", "Europe");
        countriesAndRegions.put("BELGIUM", "Europe");
        countriesAndRegions.put("BRAZIL", "South America");
        countriesAndRegions.put("BULGARIA", "Europe");
        countriesAndRegions.put("CANADA", "North America");
        countriesAndRegions.put("CHINA", "Asia");
        countriesAndRegions.put("CZECH REPUBLIC", "Europe");
        countriesAndRegions.put("FINLAND", "Europe");
        countriesAndRegions.put("FRANCE", "Europe");
        countriesAndRegions.put("GERMANY", "Europe"); // Регион для Германии
        countriesAndRegions.put("HUNGARY", "Europe");
        countriesAndRegions.put("INDIA", "Asia");
        countriesAndRegions.put("IRAN, ISLAMIC REPUBLIC OF", "Asia");
        countriesAndRegions.put("JAPAN", "Asia");
        countriesAndRegions.put("KOREA, REPUBLIC OF", "Asia");
        countriesAndRegions.put("MEXICO", "North America");
        countriesAndRegions.put("NETHERLANDS, KINGDOM OF THE", "Europe");
        countriesAndRegions.put("PAKISTAN", "Asia");
        countriesAndRegions.put("ROMANIA", "Europe");
        countriesAndRegions.put("RUSSIA", "Europe");
        countriesAndRegions.put("SLOVAKIA", "Europe");
        countriesAndRegions.put("SLOVENIA", "Europe");
        countriesAndRegions.put("SOUTH AFRICA", "Africa");
        countriesAndRegions.put("SPAIN", "Europe");
        countriesAndRegions.put("SWEDEN", "Europe");
        countriesAndRegions.put("SWITZERLAND", "Europe");
        countriesAndRegions.put("TAIWAN", "Asia"); // Регион для Тайваня
        countriesAndRegions.put("UKRAINE", "Europe");
        countriesAndRegions.put("UNITED ARAB EMIRATES", "Asia");
        countriesAndRegions.put("UNITED KINGDOM", "Europe");
        countriesAndRegions.put("UNITED STATES OF AMERICA", "North America");
    }
}
