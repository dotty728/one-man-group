import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Main {
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
                    System.out.println("Play a card by typing 'card' or draw a card by typing 'draw'");
                    String move = sc.nextLine();

                    if (move.equals("card")) {
                        System.out.println("Enter the card number you want to play");
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
                                    System.out.println("You played a Draw Two card your opponent draws 2 cards");
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
            case 2: {
                ArrayList<String> playerHand = new ArrayList<>();
                ArrayList<String> botHand = new ArrayList<>();
                System.out.println("Enter your name: ");
                String player = sc.nextLine();

                for (int i = 0; i < 7; i++) {
                    playerHand.add(cards.remove(0));
                    botHand.add(cards.remove(0));
                }
                boolean playerTurn = true;
                boolean skipNextTurn = false;

                while (!playerHand.isEmpty() && !botHand.isEmpty()) {
                    if (cards.size() == 0) {
                        System.out.println("The deck is empty, the game ends");
                        System.out.println("Restart the game by typing 'yes' or end the whole game by typing 'no'");
                        String restartselection = sc.nextLine().toLowerCase();
                        if (restartselection.equals("yes")) {
                            main(args);
                        } else {
                            System.out.println("Thanks for playing");
                            return;
                        }
                    }

                    //checks skip before each players turn to ensure it happens
                    if (skipNextTurn) {
                        skipNextTurn = false;
                        playerTurn = !playerTurn;
                        System.out.println("Skipping turn");
                        continue;
                    }

                    ArrayList<String> currentPlayerHand = playerTurn ? playerHand : botHand;
                    String currentPlayer = playerTurn ? player : "Bot";

                    System.out.println(currentPlayer + "'s hand: " + currentPlayerHand);
                    System.out.println("Top card: " + topcard);

                    if (playerTurn) {
                        // Player's turn
                        System.out.println("Play a card 'card' or draw 'draw'?");
                        String move = sc.nextLine();

                        if (move.equalsIgnoreCase("card")) {
                            System.out.println("Enter card number to play (1-based index):");
                            int decision2 = sc.nextInt() - 1;
                            sc.nextLine();

                            if (decision2 >= 0 && decision2 < currentPlayerHand.size()) {
                                String playedcard = currentPlayerHand.get(decision2);
                                String playedcardcolor;
                                String playedcardnumber;

                                if (playedcard.contains("Wild")) {
                                    playedcardcolor = "";
                                    playedcardnumber = "Wild";
                                } else {
                                    playedcardcolor = playedcard.substring(0, playedcard.indexOf(" "));
                                    playedcardnumber = playedcard.substring(playedcard.indexOf(" ") + 1);
                                }

                                String topcardcolor;
                                String topcardnumber;

                                if (topcard.contains("Wild")) {
                                    topcardcolor = topcard.split(" ")[0];
                                    topcardnumber = "Wild";
                                } else {
                                    topcardcolor = topcard.substring(0, topcard.indexOf(" "));
                                    topcardnumber = topcard.substring(topcard.indexOf(" ") + 1);
                                }

                                if (playedcardcolor.equals(topcardcolor) || playedcardnumber.equals(topcardnumber) || playedcard.contains("Wild")) {
                                    System.out.println(currentPlayer + " played " + playedcard);
                                    currentPlayerHand.remove(decision2);

                                    if (playedcard.contains("Wild Draw 4")) {
                                        System.out.println("You played a Wild Draw 4 card! The opponent draws 4 cards.");
                                        for (int i = 0; i < 4; i++) {
                                            botHand.add(cards.remove(0));
                                        }
                                        System.out.println("Pick the new color (R, B, G, Y)");
                                        String wildcolor = sc.nextLine().toUpperCase();
                                        System.out.println("The new color is: " + wildcolor);
                                        topcard = wildcolor + " Wild Draw 4";
                                    } else if (playedcard.contains("Wild")) {
                                        System.out.println("You played a Wild card. Pick the new color (R, B, G, Y)");
                                        String wildcolor = sc.nextLine().toUpperCase();
                                        System.out.println("The new color is: " + wildcolor);
                                        topcard = wildcolor + " Wild";
                                    } else if (playedcardnumber.equals("skip")) {
                                        System.out.println("You played a Skip card! The next player loses their turn.");
                                        topcard = playedcard;
                                        skipNextTurn = true; // Set flag to skip next turn
                                    } else if (playedcardnumber.equals("Draw two")) {
                                        System.out.println("You played a Draw Two card! The opponent draws 2 cards.");
                                        for (int i = 0; i < 2; i++) {
                                            botHand.add(cards.remove(0));
                                        }
                                        topcard = playedcard;
                                    } else {
                                        topcard = playedcard;
                                    }

                                    playerTurn = !playerTurn;
                                } else {
                                    System.out.println("Invalid card. Try again.");
                                }
                            } else {
                                System.out.println("Invalid card number.");
                            }
                        } else if (move.equalsIgnoreCase("draw")) {
                            String newcard = cards.remove(0);
                            playerHand.add(newcard);
                            System.out.println("You drew: " + newcard);
                            playerTurn = !playerTurn;
                        } else {
                            System.out.println("Invalid input. Try again.");
                        }
                    } else {
                        // Bot's turn
                        boolean botPlayed = false;
                        for (int i = 0; i < botHand.size(); i++) {
                            String botCard = botHand.get(i);
                            if (botCard.startsWith(topcard.split(" ")[0]) || botCard.endsWith(topcard.split(" ")[1]) || botCard.contains("Wild")) {
                                botHand.remove(i);

                                if (botCard.contains("Wild Draw 4")) {
                                    System.out.println("Bot played a Wild Draw 4 card! You draw 4 cards.");
                                    for (int j = 0; j < 4; j++) {
                                        playerHand.add(cards.remove(0));
                                    }

                                    String chosenColor = colors[(int) (Math.random() * colors.length)];
                                    System.out.println("The new color is: " + chosenColor);
                                    topcard = chosenColor + " Wild Draw 4";
                                } else if (botCard.contains("Wild")) {
                                    System.out.println("Bot played a Wild card. The new color is chosen randomly.");

                                    String chosenColor = colors[(int) (Math.random() * colors.length)];
                                    System.out.println("The new color is: " + chosenColor);
                                    topcard = chosenColor + " Wild";
                                } else if (botCard.endsWith("skip")) {
                                    System.out.println("Bot played a Skip card! You lose your turn.");
                                    skipNextTurn = true;
                                    topcard = botCard;
                                } else {
                                    topcard = botCard;
                                    System.out.println("Bot played " + botCard);
                                }
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
                        playerTurn = !playerTurn;
                    }
                }
                if (playerHand.size() == 0) {
                    System.out.println(player + " wins!");
                    scoreP1 += botHand.size();
                    System.out.println("Player 1 has " + scoreP1 + " points and bot has " + scoreP2 + " points");
                } else if (botHand.size() == 0) {
                    System.out.println(botHand + " wins!");
                    scoreP2 += playerHand.size();
                    System.out.println("Player 1 has " + scoreP1 + " points and bot has " + scoreP2 + " points");
                }
                System.out.println("Do you want to play again? Type 'yes' to restart or 'no' to end the game.");
                String restartselection = sc.nextLine().toLowerCase();
                if (!restartselection.equals("yes")) {
                    System.out.println("Thanks for playing the final scores are");
                    System.out.println(player +  + scoreP1 + "points");
                    System.out.println("Bot " + scoreP2 + " points");
                    return;
                }
            }
            }

        }
    }
