package src.view;
import src.utils.InputUtil;
public class AdminUserView {
    
    public void display(){
        while(true){
            System.out.println("==== Admin View ====");
            System.out.println("1.Manage Roles ");
            System.out.println("2.Manage Librarians");
            System.out.println("3.Logout");
            System.out.println("Enter your choice : ");

        int choice = InputUtil.getIntInput();

        switch(choice){
            case 1 -> new UserRoleView().display();
            case 2 -> new LibrarianView().display();
            case 3 -> {
                System.out.println("Logging out");
                return;
            }
            default -> System.out.println("Invalid Choice. Try again");
        }
        }
    } 
}