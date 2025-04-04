package ScoringAndProgression.ScoreSystem;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public static void connect() {
        var url = "jdbc:sqlite:database.db";
        try (var _ = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}