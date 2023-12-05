import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

interface Payable {
    void calculatePayment();
}

class Seller extends User implements Payable {
	
    static List<Product> sellerProducts;
    private Scanner input = new Scanner(System.in);
    private XYZShop xyzshop = new XYZShop();
    private double totalRevenue ;
    
    Seller(String username, String password, String email) {
        super(username, password, email);
        sellerProducts = new ArrayList<>();
        XYZShop.userCollection.addUser(this);
        UserCollections.eeee(this);
    }
    
    void addProduct() {
        String productID;
        while (true) {
            System.out.print("Enter the ID of product: ");
            productID = input.nextLine();

            if (!productID.trim().isEmpty()) {
                boolean idExists = false;

                for (Product product : ItemCollections.products) {
                    if (product.getProductID().equals(productID)) {
                        idExists = true;
                        break;
                    }
                }

                if (idExists) {
                    System.out.println("Error: Product ID already exists. Please choose a different one.\n");
                } else {
                    break; // Exit the loop if a non-empty product ID is provided
                }
            } else {
                System.out.println("Error: Product ID cannot be empty.\n");
            }
        }

        String name;
        while (true) {
            System.out.print("Enter the name of product: ");
            name = input.nextLine();
            if (!name.trim().isEmpty()) {
                break; // Exit the loop if a non-empty product name is provided
            }
            System.out.println("Error: Product name cannot be empty.\n");
        }

        double price;
        while (true) {
            System.out.print("Enter the price of product: ");
            try {
                price = input.nextDouble();
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid price. Please enter a valid number.\n");
                input.nextLine(); // Consume invalid input
            }
        }

        String description;
        while (true) {
            System.out.print("Enter the description for product: ");
            description = input.nextLine();
            if (!description.trim().isEmpty()) {
                break;
            }
            System.out.println("Error: Product description cannot be empty.\n");
        }
        
        sellerProducts.add(new Product(productID, name, price, description));
        ItemCollections.products.add(new Product(productID, name, price, description));
        System.out.println("Product added successfully!\n");
    }

    void removeProduct() {
    	
        if (sellerProducts.isEmpty()) {
            System.out.println("No products available to remove.\n");
            return;
        }
    	
    	System.out.print("Enter the ID of product you need remove: ");
    	String productID = input.nextLine();
    	
    	for (Product product : sellerProducts) {
    		if (product.getProductID().equals(productID)) {
                sellerProducts.remove(product);
                System.out.println("Product deleted successfully!\n");
                break;
            }
    		else 
    			System.out.println("Product not found. Enter correct Product ID.\n");
        }
    	
    	for (Product product : ItemCollections.products) {
    		if (product.getProductID().equals(productID)) {
    			ItemCollections.products.remove(product);
                break;
    	}
    }
}
    
    void displaySellingStatistics() {
    	
        System.out.println("Selling Statistics:");        
        System.out.println("Total Products sold: " + ItemCollections.selectedProducts.size());

        if (!ItemCollections.selectedProducts.isEmpty()) {
            System.out.println("Products sold:");
            totalRevenue = 0.0 ;
            for (Product product : ItemCollections.selectedProducts) {
                System.out.println("  - " + product.getName() + ": $" + product.getPrice());
                totalRevenue += product.getPrice();
            }
            System.out.println("Total Revenue: $" + totalRevenue+"\n");
        } else {
            System.out.println("No products was sold yet.\n");
        }
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Seller: " + getUsername() + ", Products: " + sellerProducts.size());
    }
    
    void displayAllProducts() {
        if (sellerProducts.isEmpty()) {
            System.out.println("There are no products.\n");
        } else {
            for (Product product : sellerProducts) {
                product.displayProductDetails();
            }
            System.out.println();
        }
    }
    
    void displaySellerMenu() {
        int choice;
        do {
            System.out.println("Seller Menu:");
            System.out.println("1) Add product");
            System.out.println("2) Remove Product");
            System.out.println("3) Display All products");
            System.out.println("4) Display selling statistics");
            System.out.println("5) Logout");
            System.out.print("Enter your choice: ");

            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice >= 1 && choice <= 5) {
                    switch (choice) {
                        case 1:
                            addProduct();
                            break;
                        case 2:
                            removeProduct();
                            break;
                        case 3:
                            displayAllProducts();
                            break;
                        case 4:
                        	displaySellingStatistics();
                            break;
                        case 5:
                        	System.out.println();
                            xyzshop.displayMainMenu(XYZShop.itemCollection, XYZShop.userCollection);
                            break;
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.\n");
                }
            } else {
                System.out.println("Invalid choice. You can't enter a string. Please enter a valid number.\n");
                input.nextLine(); // Consume invalid input
            }
        } while (true);
    }
    public List<Product> getSellerProducts(){
    	return sellerProducts;
    }
    @Override
    public void calculatePayment() {
        // Implement payment calculation for Seller
    }
}