import java.util.Scanner;

class Admin extends User {
	
	private XYZShop xyzshop = new XYZShop();
	private Scanner input = new Scanner(System.in);
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "0000";
    private static final String DEFAULT_EMAIL = "0000";
    
    Admin() {
    	super(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_EMAIL);
    	XYZShop.userCollection.addUser(this);
    }
    
    // 2) Display Active Users
    void displayActiveUsers(UserCollections userCollection) {
    	
        System.out.println("Active Users:");
        boolean foundActiveUser = false;

        for (User user : UserCollections.users) {
            if (user.isActive()) {
                user.displayInfo();
                foundActiveUser = true;
            }
        }

        if (!foundActiveUser) {
            System.out.println("No active users found.");
        }
    }
    
    // 2) Display UnActive Users
    void displayInactiveUsers(UserCollections userCollection) {
    	
        System.out.println("Inactive Users:");
        boolean foundInactiveUser = false;

        for (User user : UserCollections.users) {
            if (!user.isActive()) {
                user.displayInfo();
                foundInactiveUser = true;
            }
        }

        if (!foundInactiveUser) {
            System.out.println("No Inactive users found.");
        }
    }
    
    // 3) Display All the products
    void displayAllProducts() {

        if (ItemCollections.products.isEmpty()) {
            System.out.println("There are no products.\n");
        } else {
            for (Product product : ItemCollections.products) {
            	//System.out.println(ItemCollections.products.size());
                product.displayProductDetails();
            }
        }
    }
    
    // 4) Display All users
    void displayAllUsers(UserCollections userCollection) {

        if (UserCollections.users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            System.out.println("All Users:");
            userCollection.displayAllUsers();
        }
    }
    
    // 5) Display Customers
    void displayCustomers(UserCollections userCollection) {

        boolean hasCustomers = false;
        for (User user : UserCollections.users) {
            if (user instanceof Customer) {
                user.displayInfo();
                hasCustomers = true;
            }
        }

        if (!hasCustomers) {
            System.out.println("No customers available.");
        }
    }
    
    // 6) Display Sellers
    void displaySellers(UserCollections userCollection) {

        boolean hasSellers = false;
        for (User user : UserCollections.users) {
            if (user instanceof Seller) {
                user.displayInfo();
                hasSellers = true;
            }
        }

        if (!hasSellers) {
            System.out.println("No sellers available.");
        }
    }
    
    // 7) Approve products
    void approveProducts(ItemCollections itemCollection) {
        for (Product product : ItemCollections.products) {
            product.setApproved(true);
        }
        System.out.println("All products approved successfully.");
    }
    
    // 8) Remove Users
    void removeUser() {
    	System.out.print("Enter the UserName you need remove: ");
    	String userNameRemove = input.nextLine();
    	for (User user : UserCollections.users) {
    		if (user.getUsername().equals(userNameRemove)) {
    			UserCollections.users.remove(user);
                System.out.println("User deleted successfully!");
                return;
            }
        }
        System.out.println("User not found.");
    }
    
    // 9) Remove Products
    void removeProduct() {
    	System.out.print("Enter the ID of product you need remove: ");
    	String productID = input.nextLine();
    	
    	for (Product product : ItemCollections.products) {
    		if (product.getProductID().equals(productID)) {
                ItemCollections.products.remove(product);
                System.out.println("Product deleted successfully!");
                break;
            }
    		else 
    			System.out.println("Product not found. Enter correct Product ID.\n");
        }
    	
    	for (Product product : Seller.sellerProducts) {
    		if (product.getProductID().equals(productID)) {
    			Seller.sellerProducts.remove(product);
                break;
    	}
    }
    }
    
    // 10) Display Statistics
    void displayStatistics() {
        System.out.println("All Customer: ");
        displayCustomers(XYZShop.userCollection);
        System.out.println("==================================");
        System.out.println("All Seller: ");
        displaySellers(XYZShop.userCollection);
        System.out.println("==================================");
        System.out.println("All Product: ");
        displayAllProducts();
    }

    @Override
    public void displayInfo() {
        System.out.println("Admin: " + getUsername());
    }
    
    void displayAdminMenu() {
        int choice;
        do {
            System.out.println("\nAdmin Menu:");
            System.out.println("1) Display Activate User");
            System.out.println("2) Display Unactive Users");
            System.out.println("3) Display All the products");
            System.out.println("4) Display All users");
            System.out.println("5) Display Customers");
            System.out.println("6) Display Sellers");
            System.out.println("7) Approve products");
            System.out.println("8) Remove Users");
            System.out.println("9) Remove Products");
            System.out.println("10) Display Statistics");
            System.out.println("11) Logout");
            System.out.print("Enter your choice: ");

            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice >= 1 && choice <= 11) {
                    switch (choice) {
                        case 1:
                        	displayActiveUsers(XYZShop.userCollection);
                            break;
                        case 2:
                        	displayInactiveUsers(XYZShop.userCollection);
                            break;
                        case 3:
                        	displayAllProducts();
                            break;
                        case 4:
                        	displayAllUsers(XYZShop.userCollection);
                            break;
                        case 5:
                        	displayCustomers(XYZShop.userCollection);
                            break;
                        case 6:
                        	displaySellers(XYZShop.userCollection);
                            break;
                        case 7:
                        	approveProducts(XYZShop.itemCollection);
                        	break;
                        case 8:
                        	removeUser();
                            break;
                        case 9:
                        	removeProduct();
                            break;
                        case 10:
                        	displayStatistics();
                            break;
                        case 11:
                        	System.out.println();
                            xyzshop.displayMainMenu(XYZShop.itemCollection, XYZShop.userCollection);
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 11.\n");
                }
            } else {
            	System.out.println("Invalid choice. You can't enter string. Please enter a valid number.\n");
                input.nextLine(); // Consume invalid input
            }
        } while (true);
    }
}
