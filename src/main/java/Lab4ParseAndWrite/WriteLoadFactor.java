package Lab4ParseAndWrite;
import java.sql.*;
import java.util.HashMap;

public class WriteLoadFactor {
    public void createTable(){
        String url = "jdbc:sqlite:D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources\\reactors.sqlite";

        String sql = "CREATE TABLE IF NOT EXISTS LoadFactor (\n"
                + " reactor TEXT,\n"
                + " year INTEGER,\n"
                + " loadfactor REAL,\n"
                + "FOREIGN KEY (reactor) REFERENCES ReactorsPris(name)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addNewRow(String name, HashMap<Integer, Double> loadMap){
        String url = "jdbc:sqlite:D:\\2_курс_4_семестр\\Программирование\\Reactors\\src\\main\\resources\\reactors.sqlite";
        for (int i = 2014; i <= 2024; i++){
            String sql = "INSERT OR REPLACE INTO LoadFactor(reactor, year, loadfactor)"
                + "VALUES(?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setInt(2, i);
                pstmt.setDouble(3, loadMap.get(i));
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}

    }}