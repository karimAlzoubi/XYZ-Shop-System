import java.util.ArrayList;
import java.util.List;

class ItemCollections {
	Product product = new Product();
    static List<Product> products;
    static List<Product> selectedProducts = new ArrayList<Product>() ;

    ItemCollections() {
        products = new ArrayList<>();
    }
    
    public List<Product> getItems() {
        return products;
    }
    
    public Product findItemById(String productId) {
        for (Product product : products) {
            if (product.getProductID().equals(productId)) {
                return product; // Found the item with the given ID
            }
        }
        return null; // Item not found
    }
    
    public List<Product> getUnapprovedProducts() {
        List<Product> unapprovedProducts = new ArrayList<>();
        for (Product product : products) {
            if (!product.isApproved()) {
                unapprovedProducts.add(product);
            }
        }
        return unapprovedProducts;
    }
    
    public void updateStock(Product selectedProduct, int soldQuantity) {
    	
    	for(Product product : products) {
    		if (product.getProductID().equals(selectedProduct.getProductID()))
    				product.setStockQuantity(product.getStockQuantity()-soldQuantity);
    	}
        for (Product product : Seller.sellerProducts) {
        	if(product.getProductID().equals(selectedProduct.getProductID()))
        		product.setStockQuantity(product.getStockQuantity()-soldQuantity);
        }
    }
    
    public int getSize () {
    	return products.size();
    }
    
    public boolean displayAllProducts() {
        boolean anyApprovedProducts = false;
        for (Product product : products) {
            if (product.isApproved()) {
            	product.displayProductDetails();
                anyApprovedProducts = true;
            }
        }
        return anyApprovedProducts;
    }
    
    public List<Product> getProductList() {
        return products;
    }

    public void setItemList(List<Product> userArrayList) {
        products = userArrayList;
    }
    
    
    
}
