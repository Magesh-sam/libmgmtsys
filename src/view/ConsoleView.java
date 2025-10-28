package src.view;
import src.view.memberView;
import java.util.Scanner;


public class ConsoleView{
            private final Scanner sc = new Scanner(System.in);

    public void display(){
        int choice =-1;
        while(true){
            System.out.println("\n---Library Management System ---");
            System.out.println("1. Login");
            System.out.println("2. Register(members only)");
            System.out.println("3. Exit");
             System.out.println("Enter your choice");
            String input = sc.nextLine().trim();

             if(!input.matches("\\d+"))
            {
              System.out.println("Invalid input Enter num between 1-2");
              continue;
            }
       
            choice = Integer.parseInt(input);


            switch (input) {
                case 1:
                    handleLogin();
                    break;
               
                case 2:
                    handleLogin();
                    break;
                default:
                    break;
            }
        }
    }

   
}