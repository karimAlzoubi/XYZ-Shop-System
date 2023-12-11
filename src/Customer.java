import java.util.*;

class Customer extends User {
	
	Order order = new Order();
	Product product = new Product();
    private double balance;
    Invoice invoice;
    List<Invoice> invoices;
    int soldQuantity;
    private Scanner input = new Scanner(System.in);
    private XYZShop xyzshop = new XYZShop();
    
    Customer(String username, String password, String email, double balance ) {
        super(username, password, email);
        this.balance = balance;
        Order.orders = new ArrayList<>();
        this.invoices = new ArrayList<>();
        XYZShop.userCollection.addUser(this);
    }
    
    int getSoldQuantity() {
    	return soldQuantity;
    }

    public void createOrder(ItemCollections itemCollection, UserCollections userCollection) {
        if (itemCollection.getItems().isEmpty()) {
            System.out.println("No products are available for ordering.\n");
            return;
        }

        System.out.println("Available Products:");
        if (!itemCollection.displayAllProducts()) {
            System.out.println("No approved products are available for ordering.\n");
            return;
        }

        System.out.print("Enter the ID of the product you want to order: ");
        String productID = input.nextLine();

        Product selectedProduct = itemCollection.findItemById(productID);

        if (selectedProduct == null) {
            System.out.println("Invalid product ID. Please try again.\n");
            return;
        }

        List<Product> sss = new ArrayList<Product>() ;
        sss.add(selectedProduct);
        ItemCollections.selectedProducts.add(selectedProduct);

        
        System.out.print("Enter the quantity you want to order OR 0 to cancel: ");

        while (true) {
            try {
                soldQuantity = Integer.parseInt(input.nextLine());
                if (soldQuantity < 0) {
                    System.out.println("Invalid quantity. Please enter a quantity greater than 0.\n");
                    return;
                }
                if (soldQuantity == 0) {
                    System.out.println("Good-bye, see you soon.\n");
                    return;
                }
                if (selectedProduct.getStockQuantity() < soldQuantity) {
                    System.out.println("Insufficient stock. Please try create order again.\n");
                    return;
                }

                if (XYZShop.currentUser instanceof Customer) {
                    double totalOrderCost = selectedProduct.getPrice() * soldQuantity;

                    if (balance < totalOrderCost) {
                        System.out.println("Insufficient balance. Please add funds to your account and try again.\n");
                        return;
                    }
                }

                createOrder(sss, soldQuantity);
                balance -= selectedProduct.getPrice() * soldQuantity;
                System.out.println("Your current balance: " + balance + "$\n");
                
                // Update stock based on the individual item quantity
                XYZShop.itemCollection.updateStock(selectedProduct, soldQuantity);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                System.out.print("Enter the quantity you want to order OR 0 to cancel: ");
            }
        }
    }
    
    void createOrder(List<Product> products, int soldQuantity) {
        if (products.isEmpty()) {
            System.out.println("No products selected for ordering.\n");
            return;
        }

        Order newOrder = new Order(Order.orders.size() + 1, this, products, soldQuantity);
        Order.orders.add(newOrder);

        Invoice newInvoice = new Invoice(invoices.size() + 1, this, products);
        invoices.add(newInvoice);

        System.out.println("Order created successfully!");
    }
    
    void deleteOrder(int orderId) {
        
        for (Order order : Order.orders) {
            if (order.getOrderId() == orderId) {
                Order.orders.remove(order);
                invoices.removeIf(invoice -> invoice.getInvoiceId() == orderId);
                
                System.out.println("Order deleted successfully!\n");
                return;
            }
        }
        System.out.println("Order not found. Try agian\n");
    }
    
    void displayAllInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("There are no invoices yet.\n");
        } else {
            for (Invoice invoice : invoices) {
                invoice.displayInvoiceDetails();
            }
        }
    }
    
    void displayAllOrders() {
        if (Order.orders.isEmpty()) {
            System.out.println("There are no orders yet.\n");
        } else {
            for (Order order : Order.orders) {
                order.displayOrderDetails();
            }
        }
    }
    
    public void displayBalance() {
        System.out.println("Current Balance: " + balance+"$\n");
    }
    
    public void addBalance() {
        while (true) {
            try {
                System.out.print("How much money do you want to add?");
                double newBalance = input.nextDouble();
                
                // Validate that the entered amount is non-negative
                if (newBalance < 0) {
                    System.out.println("Please enter a non-negative amount.");
                    continue;
                }

                this.balance += newBalance;
                System.out.println("The balance has been added successfully.");
                System.out.println("Your current balance: " + this.balance + "$\n");
                break; // Exit the loop if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                input.nextLine(); // Consume the invalid input
            }
        }
    }
    
    void rateSeller() {
        Scanner input = new Scanner(System.in);

        System.out.println("\nRate the seller " + ":");
        System.out.println("1) Excellent");
        System.out.println("2) Good");
        System.out.println("3) Average");
        System.out.println("4) Poor");
        System.out.println("5) Terrible");
        System.out.print("Enter your rating (1-5): ");

        int rating;
        while (true) {
            if (input.hasNextInt()) {
                rating = input.nextInt();
                if (rating >= 1 && rating <= 5) {
                    break; // Valid rating, exit the loop
                } else {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                }
            } else {
                System.out.println("Invalid choice. You can't enter a string.\nPlease enter a valid number:");
                input.nextLine(); 
            }
        }
        if (rating == 1)
        System.out.println("Thank you for your rating! The seller has been rated Excellent.\n");
        if (rating == 2)
        System.out.println("Thank you for your rating! The seller has been rated Good.\n");
        if (rating == 3)
        System.out.println("Thank you for your rating! The seller has been rated Average.\n");
        if (rating == 4)
        System.out.println("Thank you for your rating! The seller has been rated Poor.\n");
        if (rating == 5)
        System.out.println("Thank you for your rating! The seller has been rated Terrible.\n");
        
    }

    @Override
    public void displayInfo() {
        System.out.println("Customer: " + getUsername() + ", Balance: " + balance);
    }
  
    void displayCustomerMenu() {
        int choice;
        do {
            System.out.println("Customer Menu:");
            System.out.println("1) Display orders");
            System.out.println("2) Create order");
            System.out.println("3) Delete order");
            System.out.println("4) Display Balance");
            System.out.println("5) Add Balance");
            System.out.println("6) Display Invoices");
            System.out.println("7) Rate seller");
            System.out.println("8) Logout");
            System.out.print("Enter your choice: ");

            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice >= 1 && choice <= 8) {
                    switch (choice) {
                        case 1:
                            displayAllOrders();
                            break;
                        case 2:
                            createOrder(XYZShop.itemCollection, XYZShop.userCollection); 
                            break;
                        case 3:
                            if (Order.orders.isEmpty()) {
                                System.out.println("No products orders to remove.\n");
                                return;
                            }
                            else do {
                        	    System.out.print("Enter an Order ID you need to delete:");

                        	    if (input.hasNextInt()) {
                        	        int delId = input.nextInt();
                        	        input.nextLine();

                        	        deleteOrder(delId);
                        	        break; // Break out of the loop after successfully deleting the order
                        	    } else {
                        	        System.out.println("Invalid input. Please enter a valid number for Order ID.\n");
                        	        input.nextLine();
                        	    }
                        	} while (true);
                        	break;
                        case 4:
                            displayBalance();
                            break;
                        case 5:
                        	addBalance();
                            break;
                        case 6:
                            displayAllInvoices();
                            break;
                        case 7:
                        	rateSeller();
                            break;
                        case 8:
                        	System.out.println();
                            xyzshop.displayMainMenu(XYZShop.itemCollection, XYZShop.userCollection);
                            return; 
                    }
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.\n");
                }
            } else {
                System.out.println("Invalid choice. You can't enter a string. Please enter a valid number.\n");
                input.nextLine();
            }
        } while (true);
    }
}