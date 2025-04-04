package dk.sdu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardDAO {
    private final String url = "jdbc:sqlite:database.db";

    public LeaderboardDAO() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS leaderboard (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "player_name TEXT NOT NULL," +
                    "score INTEGER NOT NULL," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveScore(String playerName, int score) {
        String sql = "INSERT INTO leaderboard (player_name, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTopScores(int limit) {
        List<String> results = new ArrayList<>();
        String sql = "SELECT player_name, score FROM leaderboard ORDER BY score DESC LIMIT ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                results.add(rs.getString("player_name") + ": " + rs.getInt("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
