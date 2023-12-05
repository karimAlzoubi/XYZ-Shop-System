import java.util.ArrayList;
import java.util.List;

class Order {
    private int orderId;
    private Customer customer;
    private List<Product> pro2;
    private double totalAmount;
    
    public Order(int orderId, Customer customer, List<Product> products) {
        this.orderId = orderId;
        this.customer = customer;
        this.pro2 = new ArrayList<>(products);
        calculateTotalAmount();
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

	public double getTotalAmount() {
		return totalAmount;
	}

	private void calculateTotalAmount() {
        totalAmount = 0;
        for (Product product : pro2) {
            totalAmount += product.getPrice();
        }
    }
    
    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Products:");

        for (Product product : pro2) {
            System.out.println("- " + product.getName() + " - " + product.getPrice());
        }

        System.out.println("Total Amount: " + totalAmount+"\n");
    }
}



class Invoice {
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
            totalAmount += product.getPrice();
        }
    }
    
    public void displayInvoiceDetails() {
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Customer: " + customer.getUsername());
        System.out.println("Products:");

        for (Product product : pro2) {
            System.out.println("- " + product.getName() + " - " + product.getPrice());
        }

        System.out.println("Total Amount: " + totalAmount+"\n");
    }
}