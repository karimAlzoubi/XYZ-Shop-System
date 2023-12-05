class Product {
	private String productID;
    private String name;
    private double price;
    private String description;
    private boolean approved;

    Product(String productID, String name, double price, String description) {
    	this.productID = productID;
        this.name = name;
        this.price = price;
        this.description = description;
        this.approved = false;
    }
   
	public String getProductID() {
		return productID;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}
	
    void setApproved(boolean approved) {
        this.approved = approved;
    }
	
    void displayProductDetails() {
        System.out.println("Product ID: " + productID+","+" Name: " + name+","+" Price: " + price+","+" Description: " + description+","+" Approved: " + approved);
    }
}
    
