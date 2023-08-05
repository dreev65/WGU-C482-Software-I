package src;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class that identifies the components of each product. Contains the accessors and mutators for each Product.
 * @author Daniel Reeve
 */
public class Product {
    private ObservableList<Part> associatedPart;
    private int productID;
    private String name;
    private double productPrice;
    private int inventory;
    private int min;
    private int max;
    
    /**
     * Sets the passed parameters to the values of the product. 
     * @param productID the product id
     * @param name the product name
     * @param productPrice the product price
     * @param inventory the product inventory
     * @param min the product minimum quantity
     * @param max the product maximum quantity
     */
    public Product(int productID, String name, double productPrice, int inventory, int min, int max) {
        this.associatedPart = FXCollections.observableArrayList();
        this.productID = productID;
        this.name = name;
        this.productPrice = productPrice;
        this.inventory = inventory;
        this.min = min;
        this.max = max;
    }
    
    /**
     * Constructor for the default product
     */
    public Product() {
        this(0, null, 0.00, 0, 0, 0);
        this.associatedPart = FXCollections.observableArrayList();
    }
    
//Product Mutators//

    /**
     * Sets product ID
     * @param productID sets the product id
     */
    public void setProductID(int productID){
        this.productID = productID;
    }

    /**
     * Sets the product name
     * @param name sets the product name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the product price
     * @param productPrice sets the product price
     */
    public void setProductPrice(double productPrice){
        this.productPrice = productPrice;
    }

    /**
     * Sets the product inventory
     * @param inventory sets the product inventory
     */
    public void setInventory(int inventory){
        this.inventory = inventory;
    }

    /**
     * Sets the product minimum quantity
     * @param min sets the product minimum quantity
     */
    public void setMin(int min){
        this.min = min;
    }

    /**
     * Sets the product maximum quantity
     * @param max sets the product maximum quantity
     */
    public void setMax(int max){
        this.max = max;
    }
    
//Product Accessors// 

    /**
     * Returns the product ID
     * @return returns the product ID
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Returns the product name
     * @return returns the product name
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the product price
     * @return returns the product price
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     * Returns the product inventory
     * @return returns the product inventory
     */
    public int getInventory() {
        return inventory;
    }

    /**
     * Returns the product minimum quantity
     * @return returns the product minimum quantity
     */
    public int getMin() {
        return min;
    }

    /**
     * Returns the product maximum quantity
     * @return returns the product maximum quantity
     */
    public int getMax() {
        return max;
    }
    
//ObservableList//

    /**
     * Sets the selected part as an associated part of the selected product.
     * @param part the selected part being added to a product
     */
    public void addAssociatedPart(ObservableList<Part> part) {
        this.associatedPart.addAll(part);
    }

    /**
     * Gets the associate part.
     * @return returns the associated part
     */
    public ObservableList<Part> getAssociatedPart(){
        return associatedPart;
    }
    
//Delete Part//

    /**
     * Removes the associated part from the product
     * @param part the selected part being removed
     * @return removes the selected part
     */
    public boolean deleteAssocaitedPart(ObservableList<Part> part) {
        return associatedPart.remove(part);
    }

}


