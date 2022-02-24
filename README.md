# BlackJack Game
Final Group Project for Data Structures

By: Ceyhun, Aliya and Lien 


Rules:<br />

-Player plays against the dealer<br />
-Player is given 2 cards initially, they can “hit” to ask for more cards or “stand” <br />
-While “hitting” if player passes 21, they lose<br />
-When player “stands”, the dealer plays<br />
-Dealer draws a new card until they reach at least 16, and stops drawing if they have a hand <br />
between 17 and 21 (dealer always plays with the same logic in BlackJack)<br />
-Once dealer is done, player’s hand is compared to the dealer’s, whoever is closer to 21 wins<br />
-The K, Q and J are worth 10,  value of the Ace can be 1 or 11<br />
-If you get a 21 (For example Ace + J), it’s BlackJack!<br />

Game Instructions<br />

-You can run the game using an IDE, by running the RealGame() function located in the game class (src/BlackJackgame/Game.java) <br />
-Press start button to start the game. <br />
-Press the hit button to request another card (if the new card makes you pass 21,  you lose)<br />
-Press the stand button when you are happy with your hand and the dealer will play<br />
-Press replay button to start a new round after you lose or win. <br />
