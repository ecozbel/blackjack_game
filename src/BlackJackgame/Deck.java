
package BlackJackgame;


import java.util.ArrayList;


/**
 * 
 * Creates a data structure that represents cards being held. It uses an outer array list th holds
 * inner arraylists in each element, that hold cards of the same value in them. When the deck is
 * first constructed it is filled with empty inner arraylists. The index of the outer arraylist is
 * the same as the numeric value of the type of cards stored in that index.
 * 
 * 
 * Author: Ceyhun, Lien, Aliya
 */
public class Deck {

    ArrayList<ArrayList<Card>> hand = new ArrayList<ArrayList<Card>>(12);

    Deck() {
        ArrayList<Card> tempList = new ArrayList<Card>();
        for (int i = 0; i < 12; i++) {
            hand.add(i, tempList);
        }

    }

    /**
     * Returns the outer array list.
     *
     * @return hand
     */
    public ArrayList<ArrayList<Card>> getHand() {
        return this.hand;
    }

    /**
     * Clears out all the innerlists within the outer list, and fills it with empty lists.
     *
     */
    public void clearDeck() {
        hand.clear();
        ArrayList<Card> tempList = new ArrayList<Card>();
        for (int i = 0; i < 12; i++) {
            hand.add(i, tempList);
        }

    }

    /**
     * Adds a given card to the inner list at the correct index of the outer list.
     * 
     * @param card
     */
    public void addCardtoHand(Card card) {
        ArrayList<Card> cardList = hand.get(card.value);
        if (cardList.isEmpty()) {
            cardList.add(card);
            hand.set(card.value, new ArrayList<>(cardList));
        } else {
            ArrayList<Card> existingList = hand.get(card.value);
            existingList.add(card);
            hand.set(card.value, new ArrayList<>(existingList));
        }
        cardList.clear();
    }


    /**
     * Calculates the total value of the cards in the hand.
     * 
     * @param count total cards value
     */
    public Integer calculateTotal() {
        Integer count = 0;
        for (ArrayList<Card> lists : hand) {
            if (!lists.isEmpty()) {
                for (Card card : lists) {
                    count += card.value;
                }
            }
        }
        return count;
    }

    /**
     * Makes all cards in the deck show their front side (Used to "flip over" cards that 
     * have the backside showing)
     *
     */
    public void flipCards() {
        for (ArrayList<Card> lists : hand) {
            if (!lists.isEmpty()) {
                for (Card card : lists) {
                    if (!card.flipped)
                        card.setimage(card.code);
                }
            }
        }
    }

    /**
     * Check if the  hand contains an Ace
     * 
     * @param true
     * @param false
     */
    public boolean AceCheck() {
        ArrayList<Card> aceList = hand.get(11);
        if (aceList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
