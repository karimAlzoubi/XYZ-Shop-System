
class Product{
	
	private String productID;
    private String name;
    private double price;
    int stockQuantity;
    private String description;
    private boolean approved;
    private int orderQuantity;
    
    public Product() {
	}
    
    Product(String productID, String name, double price, int stockQuantity, String description) {
    	this.productID = productID;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.approved = false;
    }
   
	public String getProductID() {
		return productID;
	}

	public String getName() {
		return name;
	}
	
	public int getStockQuantity() {
		return stockQuantity;
	}
	
	public void setStockQuantity(int newQuantity) {
		this.stockQuantity = newQuantity;
	}
    
    public int getOrderedQuantity() {
        return orderQuantity;
    }
    
    public void setOrderedQuantity(int orderedQuantity) {
        this.orderQuantity = orderedQuantity;
    }
	
	public double getPrice() {
		return price;
	}
	
    public boolean isApproved() {
        return approved;
    }
    
	public String getDescription() {
		return description;
	}
	
    void setApproved(boolean approved) {
        this.approved = approved;
    }
	
    void displayProductDetails() {
        System.out.println("Product ID: " + productID+","+" Name: " + name+","+" Price: " + price+","+" Quantity: "+stockQuantity+","+ " Description: " + description+","+" Approved: " + approved);
    }
}
    
