/**
* The scorebook holds and returns the scores of the dealer and the player. The dealer's score 
* is kept in idex 0, whereas the player's score is kept in idex 1.
* 
* Author: Ceyhun, Lien, Aliya
*/
package BlackJackgame;

import java.util.ArrayList;
import java.util.List;

public class Scorebook {
    List<Integer> scorebook = new ArrayList<Integer>();
    Integer dealerScore;
    Integer playerScore;

    Scorebook() {
        initializeScorebook(scorebook);
        dealerScore = scorebook.get(0);
        playerScore = scorebook.get(1);
    }

    /**
    * Initialises the scoreboard (fills it so the set() method doesn't fail)
    *
    *  @param scorebook a list of scores
    */
    public void initializeScorebook(List<Integer> scorebook) {
        scorebook.add(0, 0);
        scorebook.add(1, 0);
    }

    /**
    * Sets the score of the dealer
    *
    *  @param score score of the dealer
    */
    public void setDealerScore(Integer score) {
        scorebook.set(0, score);
        this.dealerScore = score;
    }
    /**
    * Returns the score of the dealer
    *
    *  @return  score of the dealer
    */
    public Integer getDealerScore() {
        return this.dealerScore;
    }
    /**
    * Sets the score of the player
    *
    *  @param score score of the player
    */
    public void setPlayerScore(Integer score) {
        scorebook.set(1, score);
        this.playerScore = score;
    }
    /**
    * Returns the score of the player
    *
    *  @return  score of the player
    */
    public Integer getPlayerScore() {
        return this.playerScore;
    }

}

