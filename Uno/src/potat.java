import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class potat {
    public static void main(String[] args) {
        int scoreP1 = 0;
        int scoreP2 = 0;
        int scoresamuel = 0;
        //creating deck
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
            for (String wild : wilds) {
                for (int j = 0; j < 4; j++) {
                    cards.add(wild);
                }
            }
        }
        //shuffle
        Collections.shuffle(cards);
        String topcard = cards.get(0);
        while (topcard.contains("Wild") || topcard.contains("Wild Draw 4") || topcard.contains("skip") || topcard.contains("Draw two")) {
            Collections.shuffle(cards);
            topcard = cards.get(0);
        }
        //choose game mode
        System.out.println(" Enter '1' to play vs a person or enter '2' to vs the bot");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                ArrayList<String> P1 = new ArrayList<>();
                ArrayList<String> P2 = new ArrayList<>();
                System.out.println("enter player 1's name");
                String player1 = sc.nextLine();
                System.out.println("enter player 2's name");
                String player2 = sc.nextLine();
                for (int i = 1; i <= 7; i++) {
                    P1.add(cards.remove(0));
                    P2.add(cards.remove(0));
                }
                //starts game
                int continueorend = 3;
                boolean player1turn = true;
                while (continueorend != 2) {
                    while (P1.size() != 0 && P2.size() != 0) {
                        ArrayList<String> playershand;
                        String currentplayer;
                        if (player1turn) {
                            playershand = P1;
                            currentplayer = player1;
                        } else {
                            playershand = P2;
                            currentplayer = player2;
                        }
                        System.out.println(currentplayer + "s hand is " + playershand);
                        System.out.println("the top card is " + topcard);
                        System.out.println("play a card by typing 'card' or draw a card by typing 'draw'");
                        String move = sc.nextLine();
                        if (move.equals("card")) {
                            System.out.println("Enter the card you want to play based on the order of the cards in your hand using numbers");
                            int decision = sc.nextInt() - 1;
                            sc.nextLine();


                            //what can be played
                            if (decision >= 0 && decision < playershand.size()) {
                                String playedcard = playershand.get(decision);
                                String playedcardcolor;
                                String playedcardnumber;
                                String topcardcolor;
                                String topcardnum;

                                // Determine playedcardcolor and playedcardnumber
                                if (playedcard.contains("Wild")) {

                                    playedcardcolor = "";

                                    playedcardnumber = "Wild";
                                } else {

                                    playedcardcolor = playedcard.substring(0, playedcard.indexOf(" "));

                                    playedcardnumber = playedcard.substring(playedcard.indexOf(" ") + 1);
                                }

                                // Determine topcardcolor and topcardnum
                                if (topcard.contains("Wild")) {
                                    topcardcolor = "";
                                        topcardnum = "Wild";
                                    } else {
                                            topcardcolor = topcard.substring(0, topcard.indexOf(" "));
                                                topcardnum = topcard.substring(topcard.indexOf(" ") + 1);
                                            }
                                if (playedcardcolor.equals(topcardcolor) || playedcardnumber.equals(topcardnum) || playedcard.contains("Wild")) {
                                    System.out.println(currentplayer + " played " + playedcard);
                                    topcard = playedcard;
                                    playershand.remove(decision);
                                    player1turn = !player1turn;

                                } else if (playedcardnumber.equals("skip")) {
                                    System.out.println("you skipped the other players turn");
                                    topcard = playedcard;
                                    topcard = playedcard;playershand.remove(decision);
                                    player1turn = !player1turn;


                                } else if (playedcard.contains("Wild") && !playedcard.contains("Draw 4")) {
                                    System.out.println("You played the Wild card. Pick the color you want to change to (B, R, G, Y):");
                                    String wildcolor = sc.nextLine().toUpperCase();
                                    topcard = wildcolor + " Wild"; 
                                    playershand.remove(decision); 
                                    player1turn = !player1turn;
                                } else if (playedcardnumber.equals("Draw two")) {
                                    ArrayList<String> opponentHand;
                                    if (player1turn) {
                                        opponentHand = P2;
                                    } else {
                                        opponentHand = P1;
                                    }
                                    
                                    for (int i = 0; i < 2; i++) {
                                        String newcard = cards.remove(0);
                                        opponentHand.add(newcard);
                                    }
                                    
                                    System.out.println("The opponent drew two cards!");
                                    topcard = playedcard;
                                    playershand.remove(decision);
                                    player1turn = !player1turn;

                                } else if (playedcardnumber.equals("Wild Draw 4")) {
                                    System.out.println("You played a Wild Draw 4 card! The opponent draws 4 cards.");
                                    ArrayList<String> opponentHand;
                                    if (player1turn) {
                                        opponentHand = P2;
                                    } else {
                                        opponentHand = P1;
                                    }
                                    for (int i = 0; i < 4; i++) {
                                        String newcard = cards.remove(0);
                                        opponentHand.add(newcard);
                                    }
                                    System.out.println("Pick the new color (B, R, G, Y):");
                                    String wildcolor = sc.nextLine().toUpperCase();
                                    topcard = wildcolor + " Wild draw 4";
                                    playershand.remove(decision);
                                    player1turn = !player1turn;
                                }
                                } else {
                                    System.out.println("Invalid move. The card does not match the top card. Drawing a new card.");
                                    String newcard = cards.remove(0);
                                    playershand.add(newcard);
                                    System.out.println("You drew " + newcard);
                                }

                            } else {
                                System.out.println("Invalid card number. Drawing a new card.");
                                String newcard = cards.remove(0);
                                playershand.add(newcard);
                                System.out.println("You drew " + newcard);
                            }
                        } else if (move.equals("draw")) {
                            String newcard = cards.remove(0);
                            playershand.add(newcard);
                            System.out.println("You drew " + newcard);
                            player1turn = !player1turn;
                        } else {
                            System.out.println("Invalid input. Try again.");
                        }
                    }
                    if (P1.size() == 0 || P2.size() == 0) {
                        System.out.println((P1.size() == 0 ? player1 : player2) + " wins!");
                        break;
                    }
                }
                break;
            case 2:
               
        }
    }
