import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
       System.out.println("enter 1 to play vs a person or enter 2 to vs our bot");
       Scanner sc = new Scanner(System.in);
       int choice = sc.nextInt();
       while (choice != 1 && choice != 2) {
           System.out.println("invalid input");
           System.out.println("enter 1 to play vs a person or enter 2 to vs our bot");
           choice = sc.nextInt();
       }
       int max = 10;
       int min = 1;
       switch (choice) {
           case 1:
               System.out.println("enter player 1's name");
               String player1 = sc.next();
               System.out.println("enter player 2's name");
               String player2 = sc.next();
               for (int i = 0; i < 7; i++){
                   int random = (int)(Math.random()*(max-min+1)+min);
                   if ()
                   System.out.println("red");
               }
               break;
           case 2:
               System.out.println("enter player 1's name");
               String player = sc.next();


               break;

       }
        }
    }

