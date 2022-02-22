
package BlackJackgame;

import java.awt.Color;

import java.util.ArrayList;


import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.Button;

/**
 * 
 * Creates the game that holds the logic for the player and dealer, as well as UI elements.
 * 
 * Author: Ceyhun, Lien, Aliya
 */
public class Game {


    Deck deckDealer = new Deck();
    Deck deckPlayer = new Deck();

    boolean ongoing = false;
    Boolean stand = false;

    Rectangle dealerScoreboard = new Rectangle(400, 100, 300, 40);
    GraphicsText dealerField = new GraphicsText("Dealer is holding:", dealerScoreboard.getX(), dealerScoreboard.getY());

    Rectangle playerScoreboard = new Rectangle(400, 500, 300, 40);
    GraphicsText playerField = new GraphicsText("You are holding:", playerScoreboard.getX(), playerScoreboard.getY());
    public Scorebook scorebook = new Scorebook();


    /**
     * Gives two cards to the dealer and updates the scoreboard.
     * 
     */
    public void dealerInitialPlay() {
        for (int i = 0; i < 2; i++) {
            Card card = new Card();
            deckDealer.addCardtoHand(card);
            if (i == 0) {
                card.setcardback();
            }
            if (i == 1) {
                scorebook.setDealerScore(card.value);
            }
        }
        updateDealerSB();
    }

    /**
     * Gives two cards to the player and updates the scoreboard. Returns total hand value of the player
     * after initial play.
     * 
     * @param cv CanvasWindow
     * @return score
     */
    public Integer playerInitialPlay(CanvasWindow cv) {
        Integer score = 0;
        for (int i = 0; i < 2; i++) {
            Card card = new Card();
            deckPlayer.addCardtoHand(card);
            score += card.value;
            scorebook.setPlayerScore(score);
        }
        updatePlayerSB();
        if (score == 21) {
            blackjackText(cv);
        }

        if (score == 22) {
            score -= 10;
            scorebook.setPlayerScore(score);
        }
        return score;
    }

    /**
     * Starts the main logic for the dealer.
     * 
     * @param cv CanvasWindow
     * 
     */

    public void dealerPlay(CanvasWindow cv) {
        deckDealer.flipCards();
        Integer score = deckDealer.calculateTotal();
        boolean aceCheck = false;
        do {
            while (score <= 16) {
                Card card = new Card();
                deckDealer.addCardtoHand(card);

                score += card.value;
            }
            aceCheck = deckDealer.AceCheck();
            if (!aceCheck && score > 16 && score <= 21) {
                break;
            }
            if (aceCheck && score >= 17 && score <= 21) {
                break;
            }
            if (aceCheck && score > 21) {
                score -= 10;
                break;
            } else {
                break;
            }
        } while (true);
        scorebook.setDealerScore(score);
        addCardstoCanvas(cv, 100, deckDealer);
        updateDealerSB();
        winCheck(cv);

    }

    /**
     * Check if the dealer win the game or player win the game
     * 
     * @param cv CanvasWindow
     */
    public void winCheck(CanvasWindow cv) {
        Integer player = scorebook.getPlayerScore();
        Integer dealer = scorebook.getDealerScore();
        if (player < 21 && dealer < 21) {

            if (21 - player < 21 - dealer) {
                winText(cv);
            }

            if (dealer == player) {
                pushText(cv);

            }

            if (21 - player > 21 - dealer) {
                loseText(cv);
            }
        } else {
            if (player == 21 && dealer != 21) {
                blackjackText(cv);
            }
            if (player != 21 && dealer == 21) {
                loseText(cv);
            }
            if (player == 21 && dealer == 21) {
                pushText(cv);
            }
            if (player > 21 && dealer <= 21) {
                loseText(cv);
            }
            if (player < 21 && dealer > 21) {
                winText(cv);
            }
        }
    }

    /**
     * Gives the player a card, adds it to their deck and updates the score
     * 
     * @param cv   CanvasWindow
     * @param deck player's deck
     * 
     */
    public void playerPlay(Deck deck, CanvasWindow cv) {
        Integer score = scorebook.getPlayerScore();
        Integer scoreDealer = scorebook.getDealerScore();
        boolean newAce = false;
        if (score < 21 && scoreDealer < 21) {
            Card card = new Card();
            deck.addCardtoHand(card);
            score += card.value;
            if (card.value == 11) {
                newAce = true;
            }
            scorebook.setPlayerScore(score);
            addCardstoCanvas(cv, 400, deckPlayer);
        }
        if (score > 21 && newAce == true) {
            newAce = false;
            score -= 10;
            Card card = new Card();
            deck.addCardtoHand(card);
            if (card.value == 11) {
                newAce = true;
            }
            score += card.value;
            scorebook.setPlayerScore(score);
            addCardstoCanvas(cv, 400, deckPlayer);
        }
        if (score > 21 && !newAce) {
            loseText(cv);
            stand = true;
        }
        updatePlayerSB();
    }

    /**
     * Adds a replay button to the canvas.
     * 
     * @param cv CanvasWindow
     * 
     */
    public void replayButton(CanvasWindow canvas) {
        Button button = new Button("Replay");
        button.setCenter(100, 300);
        button.setScale(10);
        canvas.add(button);
        button.onClick(() -> clearCurrent(canvas));
    }

    /**
     * Adds a hit button to the canvas. The hit button is used to draw a card.
     * 
     * @param cv CanvasWindow
     * 
     */
    public void hitButton(CanvasWindow cv) {
        Button button = new Button("Hit");
        button.setPosition(cv.getWidth() * 0.8, 200);
        cv.add(button);
        if (stand == false) {
            button.onClick(() -> playerPlay(deckPlayer, cv));
        }
    }

    /**
     * Adds a stand button to the canvas. The stand button is used when player is done hitting.
     * 
     * @param cv CanvasWindow
     * 
     */
    public void standButton(CanvasWindow cv) {
        stand = true;
        Button stand = new Button("Stand");
        stand.setPosition(cv.getWidth() * 0.8, 400);
        cv.add(stand);
        stand.onClick(() -> dealerPlay(cv));
    }


    /**
     * Adds the cards in a given deck to a given canvas, starting at the given position.
     * 
     * @param deck     deck of cards to add to canvas
     * @param cv       CanvasWindow object
     * @param position the Y position to line up the cards on
     */
    public void addCardstoCanvas(CanvasWindow cv, double position, Deck input) {
        double x = 50;
        double y = position;
        for (ArrayList<Card> lists : input.getHand()) {
            if (!lists.isEmpty()) {
                for (Card card : lists) {
                    cv.add(card.getImage(), x, y);
                    x += card.getWidth() + 10;
                }
            }
        }
    }

    /**
     * Adds a scoreboard for the dealer to the canvas.
     * 
     * @param cv CanvasWindow
     * 
     */
    public void dealerScoreboard(CanvasWindow cv) {
        dealerScoreboard.setFillColor(Color.WHITE);
        dealerField.setCenter(dealerScoreboard.getCenter());
        dealerField.setFillColor(Color.BLACK);
        cv.add(dealerScoreboard);
        cv.add(dealerField);
    }

    /**
     * Adds a scoboard for the player to the canvas.
     * 
     * @param cv CanvasWindow
     * 
     */
    public void playerScoreBoard(CanvasWindow cv) {
        playerScoreboard.setFillColor(Color.WHITE);
        playerField.setCenter(playerScoreboard.getCenter());
        playerField.setFillColor(Color.BLACK);
        cv.add(playerScoreboard);
        cv.add(playerField);

    }

    /**
     * Updates the scoreboard of the dealer.
     * 
     */
    public void updateDealerSB() {
        Integer dealerScore = scorebook.getDealerScore();

        dealerField.setText("Dealer is holding: " + dealerScore.toString());
    }

    /**
     * Updates the scoreboard of the player.
     * 
     */
    public void updatePlayerSB() {
        Integer playerScore = scorebook.getPlayerScore();
        playerField.setText("You are holding: " + playerScore.toString());
    }

    /**
     * Adds a text that shows the player has won to the canvas.
     * 
     * @param cv CanvasWindow
     */
    public void winText(CanvasWindow cv) {
        Rectangle textboard = new Rectangle(300, 300, 400, 50);
        textboard.setFillColor(Color.WHITE);
        GraphicsText dealerField = new GraphicsText("0", textboard.getX(), textboard.getY());
        dealerField.setText("You Win! Press replay to play again.");
        dealerField.setFillColor(Color.BLACK);
        dealerField.setFontSize(20.0);
        dealerField.setCenter(textboard.getCenter());
        cv.add(textboard);
        cv.add(dealerField);
    }

    /**
     * Adds a text that shows the player has lost to the canvas.
     * 
     * @param cv CanvasWindow
     */
    public void loseText(CanvasWindow cv) {
        stand = true;
        Rectangle textboard = new Rectangle(300, 300, 400, 50);
        textboard.setFillColor(Color.BLACK);
        GraphicsText dealerField = new GraphicsText("0", textboard.getX(), textboard.getY());
        dealerField.setText("You Lost! Press replay to play again.");
        dealerField.setFillColor(Color.WHITE);
        dealerField.setFontSize(20.0);
        dealerField.setCenter(textboard.getCenter());
        cv.add(textboard);
        cv.add(dealerField);
    }

    /**
     * Adds a text that shows the player is holding 21 in their hands.
     * 
     * @param cv CanvasWindow
     */
    public void blackjackText(CanvasWindow cv) {
        stand = true;
        Rectangle textboard = new Rectangle(300, 300, 400, 50);
        textboard.setFillColor(Color.BLACK);
        GraphicsText dealerField = new GraphicsText("0", textboard.getX(), textboard.getY());
        dealerField.setText("Blackjack! You win!");
        dealerField.setFillColor(Color.WHITE);
        dealerField.setFontSize(20.0);
        dealerField.setCenter(textboard.getCenter());
        cv.add(textboard);
        cv.add(dealerField);
    }

    /**
     * Adds a text to a given canavs that shows the player and the dealer have drawn.
     * 
     * @param cv CanvasWindow
     */
    public void pushText(CanvasWindow cv) {
        stand = true;
        Rectangle textboard = new Rectangle(300, 300, 400, 50);
        textboard.setFillColor(Color.BLACK);
        GraphicsText dealerField = new GraphicsText("0", textboard.getX(), textboard.getY());
        dealerField.setText("Push! It's a Draw!");
        dealerField.setFillColor(Color.WHITE);
        dealerField.setFontSize(20.0);
        dealerField.setCenter(textboard.getCenter());
        cv.add(textboard);
        cv.add(dealerField);
    }

    /**
    * Play the game using this function.
    * 
    */
    public static void main(String[] args) {
        realGame();
    }

    /**
     * Resets the game and starts a new round.
     * 
     * @param cv CanvasWindow
     */
    public void clearCurrent(CanvasWindow cv) {
        stand = false;
        ongoing = true;
        deckDealer = new Deck();
        deckPlayer = new Deck();
        cv.removeAll();
        Image background = new Image("cards/background.png");
        cv.add(background);
        background.setCenter(cv.getCenter());
        startGame(cv);
    }

    /**
     * Initialises every function neccesary for the game.
     * 
     * @param cv CanvasWindow
     */
    public void startGame(CanvasWindow cv) {
        dealerScoreboard(cv);
        playerScoreBoard(cv);
        dealerInitialPlay();
        playerInitialPlay(cv);
        addCardstoCanvas(cv, 100, deckDealer);
        addCardstoCanvas(cv, 400, deckPlayer);
        hitButton(cv);
        standButton(cv);
        replayButton(cv);

    }


   
    /**
     * Sets up the canvas and the start button. Start button starts the game.
     * 
     */   
    
     public static void realGame() {
        CanvasWindow cv = new CanvasWindow("Blackjack Game", 800, 800);
        Image background = new Image("cards/background.png");
        cv.add(background);
        background.setCenter(cv.getCenter());

        Button start = new Button("Start Game");
        start.setPosition(cv.getCenter().getX() - start.getWidth() / 2, cv.getCenter().getY());

        Game game = new Game();
        cv.add(start);
        start.onClick(() -> cv.remove(start));
        start.onClick(() -> game.startGame(cv));

    }
}
