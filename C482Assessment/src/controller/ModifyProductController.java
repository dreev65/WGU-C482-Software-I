
package controller;

import src.Inventory;
import src.Part;
import src.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class<br>
 * Initializes the ModifyProductController. A selected product can be have all fields except the ID modified. Associated parts can also be added or removed.
 * @author Daniel Reeve
 */
public class ModifyProductController implements Initializable {

    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    
    @FXML private TableView<Part> associatedPartTable;  
    @FXML private TableColumn<Product, Integer> associatedPartIDCol;
    @FXML private TableColumn<Product, String> associatedPartNameCol;
    @FXML private TableColumn<Product, Integer> associatedPartInventoryCol;
    @FXML private TableColumn<Product, Double> associatedPartPriceCol;
    
    @FXML private TextField idBox;
    @FXML private TextField nameBox;
    @FXML private TextField inventoryBox;
    @FXML private TextField priceBox;
    @FXML private TextField maxBox;
    @FXML private TextField minBox;
    
    @FXML private TextField partSearch;
    private ObservableList<Part> associatedPart = FXCollections.observableArrayList();
    private Product selectedProduct;
    private Product modifiedProduct;
    private int productID;

    
//Part Search//    

    /**
     * Searches for parts by either partial name or part ID. If no part is found an error will be displayed stating so. If the search box is empty and the user hits the enter key, the
     * search will return the full list of parts. Otherwise, the returned values are those that have the searched ID or contain the partial name.
     * @param actionEvent searches for the parts by partial name or ID on enter
     */
    public void partSearch(ActionEvent actionEvent) {
        String q = partSearch.getText();
        ObservableList<Part> part = searchByPartName(q);
        
        try{
            if(part.isEmpty()){
                int x = Integer.parseInt(q);
                Part p = searchByPartID(x);
                if(p != null){
                    part.add(p);
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"PRODUCT NOT FOUND.");
                    alert.showAndWait();
                }
            }
        }
        
        catch(NumberFormatException e){
            //Ignore//      
        }
        
        partTable.setItems(part);
        partSearch.setText("");
    }
    //Name Search//
    private ObservableList<Part> searchByPartName(String partialName){
        ObservableList<Part> partNameResult = FXCollections.observableArrayList();
        ObservableList<Part> allPart = Inventory.getAllPart();
        for(Part p : allPart){
            if(p.getName().contains(partialName)){
                partNameResult.add(p);
            }
        }
        return partNameResult;
    }
    //ID Search//
    private Part searchByPartID(int ID){
        ObservableList<Part> allPart = Inventory.getAllPart();
        for(Part p : allPart) {
            if(p.getId() == ID){
                return p;
            }
        }
        return null;
    }   
    
    /**
     * Sets the selectedProduct data to their respective text-boxes so that the use can modify them, except for the ID. The Id can not be modified.
     * @param selectedProduct the product that was selected for modification
     */
    public void transferProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        productID = Inventory.getAllProduct().indexOf(selectedProduct);
        idBox.setText(Integer.toString(selectedProduct.getProductID()));
        nameBox.setText(selectedProduct.getName());
        inventoryBox.setText(Integer.toString(selectedProduct.getInventory()));
        priceBox.setText(Double.toString(selectedProduct.getProductPrice()));
        maxBox.setText(Integer.toString(selectedProduct.getMax()));
        minBox.setText(Integer.toString(selectedProduct.getMin()));
        associatedPart.addAll(selectedProduct.getAssociatedPart());
    }
    
//Save Button//

    /**
     * Saves the product being modified and returns the user to the main stage (Inventory). Before the product can be saved, conditional statements check to see if the product has improper entries
     * and asks the user if they do want to save the product. If improper entries are entered, then errors are displayed to inform the user of what needs corrected. If the product does not have
     * any associated parts an error will be displayed.<br>
     * LOGICAL ERROR: The user could try saving a product with a min that is higher than the max or the inventory does not fall between the min and max values. This was corrected by adding 
     * alerts that prevent the user from saving without correcting the issue.<br>
     * RUNTIME ERROR: NumberFormatException can be produced, causing the application to crash, if the user enters a value into the int text fields that is non-numerical.
     * This has been fixed by implementing a try and catch statements. In the catch statement,when a NumberFormatException is produced, an alert is thrown instead of crashing the application.
     * @param actionEvent saves the modified product
     * @throws IOException throws an error when the product can not be saved
     */
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {
            int productInventory = Integer.parseInt(inventoryBox.getText());
            int productMin = Integer.parseInt(minBox.getText());
            int productMax = Integer.parseInt(maxBox.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save the product?");
            Optional <ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                if (productMax < productMin){
                    Alert minAlert = new Alert(Alert.AlertType.ERROR, "ERROR!\nMax value higher than min value.\nPlease check entries.");
                    minAlert.showAndWait();
                }
                else if(productInventory < productMin || productInventory > productMax) {
                    Alert inventoryAlert = new Alert(Alert.AlertType.ERROR, "ERROR!\nInventory is not between min and max values.\nPlease check entry.");
                    inventoryAlert.showAndWait();
                }
                else if(associatedPart.size() == 0) {
                    Alert inventoryAlert = new Alert(Alert.AlertType.ERROR, "ERROR!\nProducts must have associated parts.\nPlease add one or more parts.");
                    inventoryAlert.showAndWait();
                }
                else {
                    this.modifiedProduct = selectedProduct;
                    selectedProduct.setProductID(Integer.parseInt(idBox.getText()));
                    selectedProduct.setName(nameBox.getText());
                    selectedProduct.setInventory(Integer.parseInt(inventoryBox.getText()));
                    selectedProduct.setProductPrice(Double.parseDouble(priceBox.getText()));
                    selectedProduct.setMax(Integer.parseInt(maxBox.getText()));
                    selectedProduct.setMin(Integer.parseInt(minBox.getText()));
                    selectedProduct.getAssociatedPart().clear();
                    selectedProduct.addAssociatedPart(associatedPart);
                    

                    Inventory.modifyProduct(productID, selectedProduct);
                    Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                    Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "NUMBER FORMAT ERROR.");
            alert.showAndWait();
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Inventory, Max, and Min must be integers.\nPrice/Cost must be a double value.");
            alert2.showAndWait();
            Alert alert3 = new Alert(Alert.AlertType.ERROR, "Inventory, Max, Min Format:\nMust be X (CAN NOT BE A DECIMAL)\nPrice Format:\nMust be X.XX or X (DO NOT INCLUDE $)");
            alert3.showAndWait();
        }  
    } 
    
//Add Button//

    /**
     * Adds a part to AssocaitedPartTable from the part table. The user selects a part, then clicks add part. The part is then copied to AsociatedPartTable. if no part is selected
     * before clicking add an error will be displayed.
     * @param actionEvent adds selected part to AssocaitePartTable 
     */
    public void addButton(ActionEvent actionEvent) {
        Part selectedPart = partTable.getSelectionModel().getSelectedItem();
        if (partTable.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"NO PART SELECTED.\nPlease select a part from the list above.");
            alert.showAndWait();
        }
        else {
            associatedPart.add(selectedPart);
            modifyPartTable();
            modifyAssociatedPartTable();
        }
    }
    
//Remove Button//

    /**
     * Removes associated part from the AssociatedPartTable. The user selects a part, then clicks the remove part button. If the confirmation to remove is accepted,
     * then the part is removed from AsociatedPartTable. If no part is selected before clicking add an error will be displayed.
     * @param actionEvent removes associated part from the AssociatedPartTable
     */
    public void removeButton(ActionEvent actionEvent) {
        if (associatedPartTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR,"NO PART SELECTED.\nPlease select a part from the list above.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this part?");
            Optional <ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                int selectedPart = associatedPartTable.getSelectionModel().getSelectedIndex();
                associatedPartTable.getItems().remove(selectedPart);
            }
        }
    }    
    
//Cancel Button//    

    /**
     * Cancels the new product modification and returns the user to the main stage (Inventory).
     * @param actionEvent cancels the new product creation
     * @throws IOException ignore
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
//Modify Tables// 

    /**
     * Sets the all parts to the partTable
     */
    public void modifyPartTable() {
        partTable.setItems(Inventory.getAllPart());
    }

    /**
     * Sets all associated parts
     */
    private void modifyAssociatedPartTable() {
        associatedPartTable.setItems(associatedPart);
    }
    
    /**
     * Initializes the controller class.
     * Sets the TableView cells to their respective values from each part and associated part.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partTable.setItems(Inventory.getAllPart());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        associatedPartTable.setItems(associatedPart);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        modifyPartTable();
        modifyAssociatedPartTable();
    }
    
}
