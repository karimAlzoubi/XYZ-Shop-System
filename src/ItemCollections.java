import java.util.ArrayList;
import java.util.List;

class ItemCollections {
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
    
    public int getSize () {
    	return products.size();
    }
    
    void displayAllProducts() {
        for (Product product : products) {
        	product.displayProductDetails();
        }
    }
}
