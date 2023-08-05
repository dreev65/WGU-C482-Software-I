package src;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * Classifies the inventory lists for both parts and products. Also controls adding, modifying and removing parts and products.
 * @author Daniel Reeve
 */

public class Inventory {
    private static ObservableList<Part> allPart = FXCollections.observableArrayList();
    private static ObservableList<Product> allProduct = FXCollections.observableArrayList(); 
    
//Add Section//

    /**
     * Adds a new part to the Part list
     * @param newPart the new part being added
     */
    public static void addPart(Part newPart){
        allPart.add(newPart);
    }

    /**
     * Adds a new product to the Product list
     * @param newProduct the new product being added
     */
    public static void addProduct(Product newProduct){
        allProduct.add(newProduct);
    }

//Update Section//

    /**
     * Identifies the selected part in the list to be modified.
     * @param index the position in the list of the selected part
     * @param selectedPart the selected part to be modified
     */
    public static void modifyPart(int index, Part selectedPart) {
        allPart.set(index, selectedPart);
    }

    /**
     * Identifies the selected product in the list to be modified.
     * @param index the position in the list of the selected product
     * @param selectedProduct the selected product to be modified
     */
    public static void modifyProduct(int index, Product selectedProduct){
        allProduct.set(index, selectedProduct);
    }
    
//Delete Section//

    /**
     * Removes the selected part from the Part list.
     * @param selectedPart the selected part from the list
     * @return removes the selected part
     */
    public static boolean deletePart(Part selectedPart){
        return allPart.remove(selectedPart);
    }

    /**
     * Removes the selected product from the Product list.
     * @param selectedProduct the selected product from the list
     * @return removes the selected part
     */
    public static boolean deleteProduct(Product selectedProduct){
        return allProduct.remove(selectedProduct);
    }
    
//Accessor Section//

    /**
     * Returns all the parts currently being held in the Part list.
     * @return returns all parts 
     */
    public static ObservableList<Part> getAllPart() {
        return allPart;
    }

    /**
     * Returns all the products currently being held in the Product list.
     * @return returns all products
     */
    public static ObservableList<Product> getAllProduct() {
        return allProduct;
    }
}
