import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Customer extends User {
    private double balance;
    Invoice invoice;
    List<Order> orders;
    List<Invoice> invoices;
    private Scanner input = new Scanner(System.in);
    private XYZShop xyzshop = new XYZShop();

    Customer(String username, String password, String email) {
        super(username, password, email);
        this.balance = 0;
        this.orders = new ArrayList<>();
        this.invoices = new ArrayList<>();
        XYZShop.userCollection.addUser(this);
        UserCollections.eeee(this);
    }
    
    Customer(String username, String password, String email, double balance ) {
        this(username, password, email);
        this.balance = balance;
        this.orders = new ArrayList<>();
        this.invoices = new ArrayList<>();
        XYZShop.userCollection.addUser(this);
    }
    
    void createOrder(List<Product> products) {
        Order newOrder = new Order(orders.size() + 1, this, products);
        orders.add(newOrder);
        System.out.println("Order created successfully!\n");
        
        Invoice newInvoice = new Invoice(invoices.size() + 1, this, products);
        invoices.add(newInvoice);
    }
    
    void createOrder(ItemCollections itemCollection) {
        if (itemCollection.getItems().isEmpty()) {
            System.out.println("No products are available for ordering.\n");
            return;
        }

        System.out.println("Available Products:");
        itemCollection.displayAllProducts();

        System.out.print("Enter the Number of products you want to order: ");
        int numProducts = input.nextInt();
        input.nextLine();

        ItemCollections.selectedProducts = new ArrayList<>();
        for (int i = 0; i < numProducts; i++) {
            System.out.print("Enter the product ID for product " + (i + 1) + ": ");
            String productId = input.nextLine();
            Product product = itemCollection.findItemById(productId);

            if (product != null) {
                ItemCollections.selectedProducts.add(product);
            } else {
                System.out.println("Invalid product ID. Please enter a valid product ID.");
                i--; // Decrement to re-enter the product ID
            }
        }

        if (!ItemCollections.selectedProducts.isEmpty()) {
            createOrder(ItemCollections.selectedProducts);
        } else {
            System.out.println("No valid products selected. Order creation canceled.\n");
        }
    }
    
    void deleteOrder(int orderId) {
        
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                orders.remove(order);
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
        if (orders.isEmpty()) {
            System.out.println("There are no orders yet.\n");
        } else {
            for (Order order : orders) {
                order.displayOrderDetails();
            }
        }
    }
    
    public void displayBalance() {
        System.out.println("Current Balance: " + balance+"\n");
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
            System.out.println("5) Display Invoices");
            System.out.println("6) Rate seller");
            System.out.println("7) Logout");
            System.out.print("Enter your choice: ");

            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice >= 1 && choice <= 7) {
                    switch (choice) {
                        case 1:
                            displayAllOrders();
                            break;
                        case 2:
                            createOrder(XYZShop.itemCollection); 
                            break;
                        case 3:
                            if (orders.isEmpty()) {
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
                            displayAllInvoices();
                            break;
                        case 6:
                        	rateSeller();
                            break;
                        case 7:
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