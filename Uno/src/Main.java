        
        import java.util.ArrayList;
        import java.util.Scanner;
        import java.util.Collections;
        
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        ArrayList<String> cards = new ArrayList<>();
         String[] colors = {"B", "R", "G", "Y"};
         String[] wilds = {"Wild", "Draw 4"};
         String[] specials = {"Draw two", "skip"};
         for (String color : colors){
            cards.add(color + " " + 0);
            for (String special : specials) {
                cards.add(color + " " + special);
                cards.add(color + " " + special);
            }
        
    
            for (int i = 1; i<=9; i++){
                cards.add(color + " " + i);
                cards.add(color + " " + i);
            }
            for (String wild : wilds){
            for (int j = 0; j<4; j++){
                cards.add(wild);
            }
            }
            

            
        Collections.shuffle(cards);
        String topcard = cards.get(0);
        while (topcard.contains("Wild")||topcard.contains("Draw 4")||topcard.contains("skip")||topcard.contains("Draw two")){
            Collections.shuffle(cards);
            topcard = cards.get(0);
        }
        
        
           System.out.println("enter 1 to play vs a person or enter 2 to vs our bot");
          int choice = sc.nextInt();
       switch (choice) {
           case 1:
           ArrayList<String> P1 = new ArrayList<>();
           ArrayList<String> P2 = new ArrayList<>();
               System.out.println("enter player 1's name");
               String player1 = sc.nextLine();
               sc.nextLine();
               System.out.println("enter player 2's name");
               String player2 = sc.next();
              for (int i = 1; i <=7; i++){
                P1.add(cards.remove(0));
                P2.add(cards.remove(0));
              }
              System.out.println("Players 1's hand is" + P1);
              System.out.println("enter 1 to show player 2's hand");
              int continues = sc.nextInt();
              while (continues != 1){
                System.out.println("enter 1 to show player 2's hand");
                continues = sc.nextInt();
              } 
              System.out.println("Players 2's hand is" + P2);
              System.out.println("the first card is " + topcard);
               break;

               case 2;
        
                    }
                  
                }
           
            }
        }
            
        
    

    
    