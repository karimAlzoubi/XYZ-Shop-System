import java.util.Scanner;

public class XYZShop {
	
	private Customer customer;
	private Seller seller ;
	private Admin admin;
    private Scanner input = new Scanner(System.in);
    static User currentUser = null;
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
                if (!user.isActive()) {
                    System.out.println("Error: User must be active first. Please contact an admin.\n");
                    return;
                }

                currentUser = user;
                System.out.println("Login successful!\n");

                if (currentUser instanceof Seller) {
                    seller = (Seller) currentUser;
                } else if (currentUser instanceof Customer) {
                    customer = (Customer) currentUser;
                } else if (currentUser instanceof Admin) {
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
                        System.out.print("Enter username: ");
                        String userNameCustomer = input.nextLine().trim();

                        if (userNameCustomer.isEmpty()) {
                            System.out.println("Error: Username cannot be empty.");
                            continue; // Continue to the next iteration of the loop
                        }

                        if (userCollection.userNameExists(userNameCustomer)) {
                            System.out.println("Error: Username already exists. Please choose a different username.");
                            continue; // Continue to the next iteration of the loop
                        }

                        System.out.print("Enter password: ");
                        String passwordCustomer = input.nextLine();
                        
                        if (passwordCustomer.isEmpty()) {
                            System.out.println("Error: Password cannot be empty.\n");
                            return;
                        }
                        
                        Double balance = 0.0 ;
                        System.out.print("Enter your balance: ");
                        
                        if (input.hasNextDouble()) {
                            balance = input.nextDouble();
                            input.nextLine();  // Consume newline
                        } else {
                            System.out.println("Error: Invalid input. Please enter a valid number for balance.\n");
                            input.nextLine();  // Consume invalid input
                        }

                        System.out.print("Enter email: ");
                        String emailCustomer = input.nextLine();
                        
                        if (emailCustomer.isEmpty()) {
                            System.out.println("Error: Password cannot be empty.\n");
                            return;
                        }
                        
                        customer = new Customer(userNameCustomer, passwordCustomer, emailCustomer, balance);
                        System.out.println("Customer registration successful!\n");
                        	return;
                    case 2:
                        System.out.print("Enter username: ");
                        String userNameSeller = input.nextLine().trim();

                        if (userNameSeller.isEmpty()) {
                            System.out.println("Error: Username cannot be empty.");
                            continue; // Continue to the next iteration of the loop
                        }

                        if (userCollection.userNameExists(userNameSeller)) {
                            System.out.println("Error: Username already exists. Please choose a different username.");
                            continue; // Continue to the next iteration of the loop
                        }

                        System.out.print("Enter password: ");
                        String passwordSeller = input.nextLine();
                        
                        if (passwordSeller.isEmpty()) {
                            System.out.println("Error: Password cannot be empty.\n");
                            return;
                        }

                        System.out.print("Enter email: ");
                        String emailSeller = input.nextLine();
                        
                        if (emailSeller.isEmpty()) {
                            System.out.println("Error: Password cannot be empty.\n");
                            return;
                        }
                        
                        
                        seller = new Seller(userNameSeller, passwordSeller, emailSeller);
                        System.out.println("Seller registration successful!\n");
                        return;
                    case 3:
                    	System.out.println();
                    	displayMainMenu(itemCollection, userCollection);
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.\n");
                }
            } else {
                System.out.println("Invalid choice. You can't enter a string. Please enter a valid number.\n");
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
    
    public User getCurrentUser() {
    	return currentUser;
    }

    public void exitAndSaveInFile() {
//        saveUsersToFile();
//        saveProductsToFile();
//        saveOrdersToFile();
        System.out.println("Exiting XYZ Shop. Goodbye!");
        System.exit(0);
    }

    
    
    
    							// =========================== If you want to read and write from the file =================================
    
    
// you must put this in Main class.
    	//	xyzShop.loadUsersFromFile();
    	//	xyzShop.loadProductsFromFile();
    	//	xyzShop.loadOrdersFromFile();
    
    
// And put this in exitAndSaveInFile() Method in XYZShop class.
    	//	xyzShop.loadUsersFromFile();
    	//	xyzShop.loadProductsFromFile();
    	//	xyzShop.loadOrdersFromFile();
    
// Add 'implements Serializable' in some classes.
    
    
    
//    public void loadUsersFromFile() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("UsersDetails.txt"))) {
//            UserCollections.users = (List<User>) ois.readObject();
//            System.out.println("User details loaded successfully.");
//        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Error loading user details from file.");
//        }
//    }
//
//    public void saveUsersToFile() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UsersDetails.txt"))) {
//            oos.writeObject(UserCollections.users);
//            System.out.println("User details saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error saving user details to file.");
//            e.printStackTrace();
//        }
//    }
//
//    public void loadProductsFromFile() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ProductsDetails.txt"))) {
//            List<Product> readObject = (List<Product>) ois.readObject();
//			ItemCollections.products = readObject;
//            System.out.println("Product details loaded successfully.");
//        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Error loading product details from file.");
//        }
//    }
//
//    public void saveProductsToFile() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ProductsDetails.txt"))) {
//            oos.writeObject(ItemCollections.products);
//            System.out.println("Product details saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error saving product details to file.");
//            e.printStackTrace();
//        }
//    }
//    
//    public void loadOrdersFromFile() {
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("OrdersDetails.txt"))) {
//            List<Order> readObject = (List<Order>) ois.readObject();
//			Order.orders = readObject;
//            System.out.println("Order details loaded successfully.");
//        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Error loading Order details from file.");
//            e.printStackTrace();
//        }
//    }
//
//    public void saveOrdersToFile() {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("OrdersDetails.txt"))) {
//            oos.writeObject(Order.orders);
//            System.out.println("Order details saved successfully.");
//        } catch (IOException e) {
//            System.err.println("Error saving order details to file.");
//            e.printStackTrace();
//        }
//    }
    
    
}
    
