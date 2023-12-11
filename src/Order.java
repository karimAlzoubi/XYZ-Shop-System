import java.util.ArrayList;
import java.util.List;

class Order {
	
	static List<Order> orders;
	private int orderId;
    private Customer customer;
    private List<Product> pro2;
    private double totalAmount;
    private List<Integer> quantities = new ArrayList<>();
    
    public Order() {
	}
    
    public Order(int orderId, Customer customer, List<Product> products, int quantity) {
        this.orderId = orderId;
        this.customer = customer;
        this.pro2 = new ArrayList<>(products);
        this.quantities.add(quantity);
        }
    
    public int getOrderId() {
		return orderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<Product> getPro2() {
		return pro2;
	}
	
	public List<Integer> getQuantities() {
		return quantities;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

    double calculateTotalAmount() {
    	
        double totalAmount = 0.0;
        int minSize = Math.min(ItemCollections.products.size(), quantities.size());
        for (int i = 0; i < minSize; i++) {
            Product product = ItemCollections.products.get(i);
            int quantity = quantities.get(i);
            totalAmount += product.getPrice() * quantity;
        }
        return totalAmount;
    }
    
    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Products:");

        int minSize = Math.min(ItemCollections.products.size(), quantities.size());
        for (int i = 0; i < minSize; i++) {
            Product product = ItemCollections.products.get(i);
            int quantity = quantities.get(i);

            System.out.println("- ID: " + product.getName() + " - " + product.getPrice() + "$");
            System.out.println("- Quantity: " + quantity);
        }
        System.out.println("Total Amount: " + calculateTotalAmount() + "$\n");
    }
}





class Invoice {
	
	Order order = new Order();
    private int invoiceId;
    private Customer customer;
    private List<Product> pro2;
    List<Invoice> invoices;
    private double totalAmount;
    
    public Invoice(int invoiceId, Customer customer, List<Product> products) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.pro2 = products;
        calculateTotalAmount();
    }

	public int getInvoiceId() {
		return invoiceId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public List<Product> getProducts() {
		return pro2;
	}

	public double getTotalAmount() {
		return totalAmount;
	}
    
    private void calculateTotalAmount() {
        totalAmount = 0;
        for (Product product : pro2) {
            totalAmount +=  product.getPrice() * customer.getSoldQuantity();
        }
    }
   
    public void displayInvoiceDetails() {
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Customer: " + customer.getUsername());
        System.out.println("Products:");

        int minSize = Math.min(ItemCollections.products.size(), order.getQuantities().size());
        for (int i = 0; i < minSize; i++) {
            Product product = ItemCollections.products.get(i);
            int quantity = order.getQuantities().get(i);

            System.out.println("- ID: " + product.getName() + " - " + product.getPrice() + "$");
            System.out.println("- Quantity: " + quantity);
        }
        
//        System.out.println("Total Amount: " + totalAmount + "$\n");
        System.out.println("Total Amount: " + order.calculateTotalAmount() + "$\n");
    }
}