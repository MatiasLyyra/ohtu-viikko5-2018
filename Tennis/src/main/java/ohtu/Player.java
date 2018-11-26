package ohtu;

public class Player {
    private String playerName;
    private int score;

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;
    }

    public String getName() {
        return playerName;
    }

    public int getScore() {
        return this.score;
    }

    public void incrementScore() {
        this.score++;
    }
}
