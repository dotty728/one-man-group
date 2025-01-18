import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class potat {
    public static void main(String[] args) {

        int scoreP1 = 0;
        int scoreP2 = 0;

        // Creating deck
        Scanner sc = new Scanner(System.in);
        ArrayList<String> cards = new ArrayList<>();
        String[] colors = {"B", "R", "G", "Y"};
        String[] wilds = {"Wild", "Wild Draw 4"};
        String[] specials = {"Draw two", "skip"};

        for (String color : colors) {
            cards.add(color + " " + 0);
            for (String special : specials) {
                cards.add(color + " " + special);
                cards.add(color + " " + special);
            }
            for (int i = 1; i <= 9; i++) {
                cards.add(color + " " + i);
                cards.add(color + " " + i);
            }
        }
        for (String wild : wilds) {
            for (int j = 0; j < 4; j++) {
                cards.add(wild);
            }
        }

        // Shuffle the deck
        Collections.shuffle(cards);
        String topcard = cards.get(0);
        while (topcard.contains("Wild") || topcard.contains("Wild Draw 4") || topcard.contains("skip") || topcard.contains("Draw two")) {
            Collections.shuffle(cards);
            topcard = cards.get(0);
        }

        // Choose game mode
        System.out.println("Enter '1' to play vs a person or enter '2' to vs the bot");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1: {
                ArrayList<String> P1 = new ArrayList<>();
                ArrayList<String> P2 = new ArrayList<>();

                System.out.println("Enter player 1's name:");
                String player1 = sc.nextLine();
                System.out.println("Enter player 2's name:");
                String player2 = sc.nextLine();

                for (int i = 1; i <= 7; i++) {
                    P1.add(cards.remove(0));
                    P2.add(cards.remove(0));
                }

                // Start the game
                boolean player1turn = true;
                while (P1.size() > 0 && P2.size() > 0) {
                    if (cards.size() == 0) {
                        System.out.println("The deck is empty, the game ends");
                        System.out.println("Restart the game by typing yes or end the whole game by pressing no");
                        String restartselection = sc.nextLine().toLowerCase();
                        if (restartselection.equals("yes")) {
                            main(args);
                        } else {
                            System.out.println("Thanks for playing!");
                            return;
                        }
                    }
                    ArrayList<String> playershand;
                    String currentplayer;

                    if (player1turn) {
                        playershand = P1;
                        currentplayer = player1;
                    } else {
                        playershand = P2;
                        currentplayer = player2;
                    }

                    System.out.println(currentplayer + "'s hand: " + playershand);
                    System.out.println("The top card is: " + topcard);
                    System.out.println("Play a card by typing 'card' or draw a card by typing 'draw':");
                    String move = sc.nextLine();

                    if (move.equals("card")) {
                        System.out.println("Enter the card number you want to play (1-based index):");
                        int decision = sc.nextInt() - 1;
                        sc.nextLine();

                        if (decision >= 0 && decision < playershand.size()) {
                            String playedcard = playershand.get(decision);
                            String playedcardcolor;
                            if (playedcard.contains("Wild")) {
                                playedcardcolor = "";
                            } else {
                                playedcardcolor = playedcard.substring(0, playedcard.indexOf(" "));
                            }

                            String playedcardnumber;
                            if (playedcard.contains("Wild")) {
                                playedcardnumber = "Wild";
                            } else {
                                playedcardnumber = playedcard.substring(playedcard.indexOf(" ") + 1);
                            }

                            String topcardcolor;
                            if (topcard.contains("Wild")) {
                                topcardcolor = topcard.split(" ")[0];
                            } else {
                                topcardcolor = topcard.substring(0, topcard.indexOf(" "));
                            }

                            String topcardnumber;
                            if (topcard.contains("Wild")) {
                                topcardnumber = "Wild";
                            } else {
                                topcardnumber = topcard.substring(topcard.indexOf(" ") + 1);
                            }

                            if (playedcardcolor.equals(topcardcolor) || playedcardnumber.equals(topcardnumber) || playedcard.contains("Wild")) {
                                System.out.println(currentplayer + " played " + playedcard);
                                playershand.remove(decision);

                                if (playedcard.contains("Wild Draw 4")) {
                                    System.out.println("You played a Wild Draw 4 card! The opponent draws 4 cards.");
                                    ArrayList<String> opponentHand;
                                    if (player1turn) {
                                        opponentHand = P2;
                                    } else {
                                        opponentHand = P1;
                                    }
                                    for (int i = 0; i < 4; i++) {
                                        opponentHand.add(cards.remove(0));
                                    }
                                    System.out.println("Pick the new color (B, R, G, Y):");
                                    String wildcolor = sc.nextLine().toUpperCase();
                                    topcard = wildcolor + " Wild";

                                } else if (playedcard.contains("Wild")) {
                                    System.out.println("You played a Wild card. Pick the new color (B, R, G, Y):");
                                    String wildcolor = sc.nextLine().toUpperCase();
                                    topcard = wildcolor + " Wild";

                                } else if (playedcardnumber.equals("skip")) {
                                    System.out.println("You played a Skip card! The next player loses their turn.");
                                    topcard = playedcard;
                                    player1turn = !player1turn;

                                } else if (playedcardnumber.equals("Draw two")) {
                                    System.out.println("You played a Draw Two card! The opponent draws 2 cards.");
                                    ArrayList<String> opponentHand;
                                    if (player1turn) {
                                        opponentHand = P2;
                                    } else {
                                        opponentHand = P1;
                                    }
                                    for (int i = 0; i < 2; i++) {
                                        opponentHand.add(cards.remove(0));
                                    }
                                    topcard = playedcard;

                                } else {
                                    topcard = playedcard;
                                }

                                player1turn = !player1turn;

                            }
                        } else {
                            System.out.println("Invalid card number.");
                        }
                    } else if (move.equals("draw")) {
                        String newcard = cards.remove(0);
                        playershand.add(newcard);
                        System.out.println("You drew: " + newcard);
                        player1turn = !player1turn; // Switch turn
                    } else {
                        System.out.println("Invalid input. Try again.");
                    }
                }

                // Determines the winner by checking who has zero cards
                if (P1.size() == 0) {
                    System.out.println(player1 + " wins!");
                    scoreP1 += P2.size();
                    System.out.println("Player 1 has " + scoreP1 + " points and player 2 has " + scoreP2 + " points");
                } else if (P2.size() == 0) {
                    System.out.println(player2 + " wins!");
                    scoreP2 += P1.size();
                    System.out.println("Player 1 has " + scoreP1 + " points and player 2 has " + scoreP2 + " points");
                }

                // Ask if the player wants to play again
                System.out.println("Do you want to play again? Type 'yes' to restart or 'no' to end the game.");
                String restartselection = sc.nextLine().toLowerCase();
                if (!restartselection.equals("yes")) {
                    System.out.println("Thanks for playing! Final scores:");
                    System.out.println("Player 1: " + scoreP1 + " points");
                    System.out.println("Player 2: " + scoreP2 + " points");
                    break;
                }
                break;

            }
            case 2: { // Fixed the error by placing this case inside the switch block
                ArrayList<String> playerHand = new ArrayList<>();
                ArrayList<String> botHand = new ArrayList<>();
                System.out.println("Enter your name ");
                String player = sc.nextLine();

                // Deal cards
                for (int i = 0; i < 7; i++) {
                    playerHand.add(cards.remove(0));
                    botHand.add(cards.remove(0));
                }

                boolean playerTurn = true;
                while (!playerHand.isEmpty() && !botHand.isEmpty()) {
                    ArrayList<String> currentPlayerHand;
                    String currentPlayer;

                    if (playerTurn) {
                        currentPlayerHand = playerHand;
                        currentPlayer = player;
                    } else {
                        currentPlayerHand = botHand;
                        currentPlayer = "Bot";
                    }

                    System.out.println(currentPlayer + "'s hand: " + currentPlayerHand);
                    System.out.println("Top card: " + topcard);

                    if (playerTurn) {
                        System.out.println("Play a card 'card' or draw 'draw'?");
                        String move = sc.nextLine();

                        if (move.equalsIgnoreCase("card")) {
                            System.out.println("Enter card number to play (1-based index):");
                            int cardIndex = sc.nextInt() - 1;
                            sc.nextLine();

                            if (cardIndex >= 0 && cardIndex < currentPlayerHand.size()) {
                                String selectedCard = currentPlayerHand.get(cardIndex);
                                if (selectedCard.startsWith(topcard.split(" ")[0]) || selectedCard.endsWith(topcard.split(" ")[1]) || selectedCard.contains("Wild")) {
                                    topcard = selectedCard;
                                    currentPlayerHand.remove(cardIndex);
                                    System.out.println(currentPlayer + " just played " + selectedCard);
                                } else {
                                    System.out.println("Invalid move.");
                                }
                            } else {
                                System.out.println("Invalid card index.");
                            }
                        } else if (move.equalsIgnoreCase("draw")) {
                            if (!cards.isEmpty()) {
                                String drawnCard = cards.remove(0);
                                currentPlayerHand.add(drawnCard);
                                System.out.println(currentPlayer + " drew " + drawnCard);
                            } else {
                                System.out.println("No cards left to draw.");
                            }
                        } else {
                            System.out.println("Invalid move. Try again.");
                        }
                    } else {
                        boolean botPlayed = false;
                        for (int i = 0; i < botHand.size(); i++) {
                            String botCard = botHand.get(i);
                            if (botCard.startsWith(topcard.split(" ")[0]) || botCard.endsWith(topcard.split(" ")[1]) || botCard.contains("Wild")) {
                                topcard = botCard;
                                botHand.remove(i);
                                System.out.println("Bot played " + botCard);
                                botPlayed = true;
                                break;
                            }
                        }
                        if (!botPlayed) {
                            if (!cards.isEmpty()) {
                                String drawnCard = cards.remove(0);
                                botHand.add(drawnCard);
                                System.out.println("Bot drew a card.");
                            } else {
                                System.out.println("No cards left to draw for the bot.");
                            }
                        }
                    }

                    playerTurn = !playerTurn;
                }

                System.out.println(playerHand.isEmpty() ? player + " wins!" : "Bot wins!");
                break;
            }
        }

    }
}
