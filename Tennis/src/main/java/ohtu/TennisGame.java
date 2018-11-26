package ohtu;

public class TennisGame {
    private Player player1;
    private Player player2;

    private static final String[] SCORE_MAP = {"Love", "Fifteen", "Thirty", "Forty"};
    private static final int SCORE_OVERTIME = 4;

    public TennisGame(String player1Name, String player2Name) {
        this.player1 = new Player(player1Name);
        this.player2 = new Player(player2Name);
    }

    public void wonPoint(String playerName) {
        if (playerName == player1.getName()) {
            player1.incrementScore();
        } else if (playerName == player2.getName()) {
            player2.incrementScore();
        }
    }

    public String getScore() {
        int player1Score = player1.getScore();
        int player2Score = player2.getScore();
        String score;
        if (player1Score == player2Score) {
            score = getScoreEqual();
        }
        else if (player1Score >= SCORE_OVERTIME || player2Score >= SCORE_OVERTIME) {
            score = getScoreAdvantageOrWin();
        }
        else {
            score = getScoreMixed();
        }
        return score;
    }

    private String getScoreMixed() {
        return String.format("%s-%s",
                SCORE_MAP[player1.getScore()],
                SCORE_MAP[player2.getScore()]
        );
    }

    private String getScoreEqual() {
        String score;
        int player1Score = player1.getScore();
        if (player1Score >= SCORE_OVERTIME) {
            score = "Deuce";
        } else {
            score = String.format("%s-All", SCORE_MAP[player1Score]);
        }
        return score;
    }

    private String getScoreAdvantageOrWin() {
        String score;
        Player leadingPlayer = getLeadingPlayer();
        assert(leadingPlayer != null);
        int scoreDifference = Math.abs(player1.getScore() - player2.getScore());
        if (scoreDifference == 1) {
            score = String.format("Advantage %s", leadingPlayer.getName());
        } else {
            score = String.format("Win for %s", leadingPlayer.getName());
        }
        return score;
    }

    private Player getLeadingPlayer() {
        Player leadingPlayer = null;
        if (player1.getScore() > player2.getScore()) {
            leadingPlayer = player1;
        } else if (player2.getScore() > player1.getScore()) {
            leadingPlayer = player2;
        }
        return leadingPlayer;
    }
}