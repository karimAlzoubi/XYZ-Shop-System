import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class XYZShop {
	private Customer customer;
	private Seller seller ;
	private Admin admin;
    private Scanner input = new Scanner(System.in);
    private static User currentUser = null;
    public static UserCollections userCollection = new UserCollections();
    public static ItemCollections itemCollection = new ItemCollections();

    public void displayMainMenu(ItemCollections itemCollection, UserCollections userCollection) {
    	
        int choice;
        do {
            System.out.println("XYZ Shop Menu:");
            System.out.println("1) Log In");
            System.out.println("2) Register");
            System.out.println("3) Exit And Save");
            System.out.print("Enter your choice: ");
            
            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice >= 1 && choice <= 4) {
                    switch (choice) {
                        case 1:
                            login(userCollection, itemCollection);
                            break;
                        case 3:
                            exitAndSaveInFile();
                            break;
                        case 2:
                            register(userCollection);
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.\n");
                }
            } else {
                System.out.println("Invalid choice. You can't enter string. Please enter a valid number.\n");
                input.nextLine(); // Consume invalid input
            }
        } while (true);
    }
    
    private void login(UserCollections userCollection, ItemCollections itemCollection) {
        System.out.print("Enter username: ");
        String username = input.nextLine().trim(); // Trim leading and trailing spaces
        
        if (username.isEmpty()) {
            System.out.println("Error: Username cannot be empty.\n");
            return;
        }

        System.out.print("Enter password: ");
        String password = input.nextLine();
        
        if (username.contains(" ") || password.contains(" ")) {
            System.out.println("Error: Username and password cannot contain spaces.\n");
            return;
        }

        for (User user : UserCollections.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful!\n");

                if (currentUser instanceof Seller) {
                    seller = (Seller) currentUser;
                } 
                else if (currentUser instanceof Customer) {
                    customer = (Customer) currentUser;
                } 
                else if (currentUser instanceof Admin) {
                    admin = (Admin) currentUser;
                }

                displayUserMenu(itemCollection, userCollection);
                return;
            }
        }

        System.out.println("Login failed. Invalid username or password.\n");
    }

    private void register(UserCollections userCollection) {
        int userTypeChoice;
        do {
            System.out.println("\nRegistration Menu:");
            System.out.println("1) Customer");
            System.out.println("2) Seller");
            System.out.println("3) Back to the main menu");
            System.out.print("Enter your choice: ");

            if (input.hasNextInt()) {
                userTypeChoice = input.nextInt();
                input.nextLine(); // Consume newline

                switch (userTypeChoice) {
                    case 1:
                    case 2:
                        System.out.print("Enter username: ");
                        String username = input.nextLine().trim();

                        if (username.isEmpty()) {
                            System.out.println("Error: Username cannot be empty.");
                            continue; // Continue to the next iteration of the loop
                        }

                        if (userCollection.userNameExists(username)) {
                            System.out.println("Error: Username already exists. Please choose a different username.");
                            continue; // Continue to the next iteration of the loop
                        }

                        System.out.print("Enter password: ");
                        String password = input.nextLine();
                        
                        if (password.isEmpty()) {
                            System.out.println("Error: Password cannot be empty.\n");
                            return;
                        }

                        System.out.print("Enter email: ");
                        String email = input.nextLine();

                        if (userTypeChoice == 1) {
                            customer = new Customer(username, password, email);
                            UserCollections.users.add(customer);
                            System.out.println("Customer registration successful!\n");
                            return;
                        } else if (userTypeChoice == 2) {
                            seller = new Seller(username, password, email);
                            UserCollections.users.add(seller);
                            System.out.println("Seller registration successful!\n");
                            return;
                        }
                        break;
                    case 3:
                    	System.out.println();
                    	displayMainMenu(itemCollection, userCollection);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.\n");
                }
            } else {
                System.out.println("Invalid choice. You can't enter a string. Please enter a valid number.\n");
                input.nextLine();
            }
        } while (true);
    }

    private void displayUserMenu(ItemCollections itemCollection, UserCollections userCollection) {

        do {
            if (currentUser instanceof Admin) {
                admin.displayAdminMenu();
            } 
            else if (currentUser instanceof Customer) {
                customer.displayCustomerMenu();
            }
            else if (currentUser instanceof Seller) {
                seller.displaySellerMenu();
            }
        } while (true);
    }
    
    public void exitAndSaveInFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("UsersDetails.txt", false))) {
            for (User user : UserCollections.users) {
                if (user instanceof Admin) {
                    writer.write("Admin: \n");
                } else if (user instanceof Seller) {
                    writer.write("Seller: \n");
                } else if (user instanceof Customer) {
                    writer.write("Customer: \n");
                }
                writer.write("UserName: " + user.getUsername() + ", Password: " + user.getPassword() + ", Email: " + user.getEmail() + "\n");
            }
            System.out.println("User details saved successfully.");
        } catch (IOException e) {
            // Handle the exception more gracefully (e.g., log it or display a user-friendly message)
            System.err.println("Error saving user details to file.");
        }
        System.out.println("Exiting XYZ Shop. Goodbye!");
        System.exit(0);
    }
}