
package BlackJackgame;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import edu.macalester.graphics.*;

/**
 * Creates a card object that is drawn randomly from a pool. Card object has a string name (ex. Seven),
 * a type (suit), a numeric value (ex. 7) , a code (ex. 7s.gif) and an image.
 * @author: Ceyhun, Lien, Aliya 
 */
public class Card extends GraphicsObject {
    int value;
    String name;
    String type;
    String code;
    private Image image = new Image(0, 0);
    public static final String[] classes = new String[] { "Spades", "Hearts", "Diamonds", "Clubs" };
    public static final String[] cards = new String[] { "a", "2", "3", "4", "5",
        "6", "7", "8", "9", "t", "j", "q", "k" };
    Random rand = new Random();
    Boolean flipped = true;


    Card() {
        setname(drawRandomName());
        settype(drawRandomType());
        setcode(constructCardCode(name, type));
        setvalue(returnValue(code));
        setimage(code);
    }

    /**
     * Sets the name of the card.
     *
     * @param name name of the card is the value as string (ex. seven)
     */
    public void setname(String name) {
        this.name = name;
    }

    /**
     * Sets the value of the card.
     *
     * @param value numeric value of the card
     */
    public void setvalue(Integer value) {
        this.value = value;
    }

    /**
     * Sets the type (suit) of the card.
     *
     * @param type suit of the card as string (ex. spades)
     */
    public void settype(String type) {
        this.type = type;
    }

    /**
     * Sets the code of the card. Used for finding the right image path.
     *
     * @param code code of the card (ex. seven of spades = 7s)
     */
    public void setcode(String code) {
        this.code = code;
    }


    /**
     * Sets the image path of the card. Using the code.
     *
     * @param code code of the card (ex. seven of spades = 7s)
     */
    public void setimage(String code) {
        String currentcode = "cards/" + code;
        image.setImagePath(currentcode);
    }

    /**
     * Sets the card image so the back side is showing visually.
     *
     */
    public void setcardback() {
        String cardback = "cards/cardback.gif";
        this.image.setImagePath(cardback);
        flipped = false;
    }

    /**
     * Return image of card
     *
     * @return image of card
     */
    public Image getImage() {
        return this.image;
    }

    /**
     * Return random card name
     *
     * @return card name
     */
    public String drawRandomName() {
        return cards[rand.nextInt(13)];
    }

    /**
     * Return random card tyoe
     *
     * @return card type
     */
    public String drawRandomType() {
        return classes[rand.nextInt(4)];
    }

    /**
     * Return string represnt the card
     *
     * @return string represent the card
     */
    public String toString() {
        String rep = name + " of " + type;
        return rep;
    }

    /**
     * Constructs and returns a card code that matches the image path when given the name and type of
     * the card.
     * 
     * @param name name of the card (ex. seven)
     * @param type suit of card (ex. spades)
     * @return card code
     */
    public String constructCardCode(String name, String type) {
        if (type == "Diamonds") {
            return name + "d" + ".gif";
        }
        if (type == "Spades") {
            return name + "s" + ".gif";
        }
        if (type == "Hearts") {
            return name + "h" + ".gif";
        }
        if (type == "Clubs") {
            return name + "c" + ".gif";
        } else {
            return null;
        }
    };

    /**
     * Given the code of the card, returns the numeric value.
     *
     * @param code code of the card
     * @return value of the card
     */
    public Integer returnValue(String code) {
        String letter = code.substring(0, 1);
        if (letter.equals("k") || letter.equals("q") || letter.equals("j") || letter.equals("t")) {
            return 10;
        }
        if (letter.equals("a")) {
            return 11;
        } else {
            Integer parsedInt = Integer.parseInt(letter);
            return parsedInt;
        }
    }
    @Override
    protected void drawInLocalCoordinates(Graphics2D gc) {

    }

    @Override
    public boolean testHitInLocalCoordinates(double x, double y) {
        if (image.getBounds().contains(x, y)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return this.image.getBounds();
    }

    @Override
    protected Object getEqualityAttributes() {
        return null;
    };


}