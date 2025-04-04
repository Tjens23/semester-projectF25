package dk.sdu;
public class ScoreSystem {
    private int score;

    public ScoreSystem() {
        this.score = 0;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getScore() {
        return this.score;
    }

    public int setScore(int score) {
        return this.score = score;
    }

    public void reset() {
        this.score = 0;
    }
}
