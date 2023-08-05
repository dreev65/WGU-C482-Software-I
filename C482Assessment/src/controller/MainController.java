package controller;

import src.Part;
import src.Product;
import src.Inventory;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 * FXML Controller class<br>
 * Main controller that is used to start the program, input starting data, and house the primary functions.
 * @author Daniel Reeve
 */
public class MainController implements Initializable {

    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partInventoryCol;
    @FXML private TableColumn<Part, Double> partPriceCol;
    @FXML private TextField partSearch;
    
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productIDCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productInventoryCol;
    @FXML private TableColumn<Product, Double> productPriceCol;
    @FXML private TextField productSearch;
    
    private Product selectedProduct;
    
//Buttons for window transition//
    /**
     * Switches from the main scene to the addPart scene.
     * @param actionEvent performs the action of switching the main scene to the addPart scene through a button
     * 
     * @throws IOException ignore
     */
    public void switchToAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     *  Switches from the main scene to the modifyPart scene.
     * @param actionEvent performs the action of switching the main scene to the addPart scene through a button
     * @throws IOException ignore
     */
    
    public void switchToModifyPart(ActionEvent actionEvent) throws IOException {
       try {
            Part selectedPart = partTable.getSelectionModel().getSelectedItem();
            if (selectedPart == null) {
                return;
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPart.fxml"));
            Parent scene = loader.load();
            ModifyPartController controller = loader.getController();
            controller.transferPart(selectedPart);
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            //Ignore
        }
    }
    
    /**
     * Switches from the main scene to the addProduct scene.
     * @param actionEvent performs the action of switching the main scene to the addProduct scene through a button
     * @throws IOException ignore
     */
    public void switchToAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Switches from the main scene to the modifyProduct scene.
     * @param actionEvent performs the action of switching the main scene to the modify Product scene through a button
     * @throws IOException ignore
     */
    public void switchToModifyProduct(ActionEvent actionEvent) throws IOException {
        try {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct == null) {
                return;
            }
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent scene = loader.load();
            ModifyProductController controller = loader.getController();
            controller.transferProduct(selectedProduct);
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (IOException e) {
            //Ignore
        }
    }
    
//Exit Button//

    /**
     * Exits the application.
     * @param actionEvent exits the application through the use of a button
     */
    public void exitMain(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
    
//Delete Part Button//

    /**
     * Deletes the selected part from the partTable. If there is no part selected then an error is thrown for the user to select one, otherwise the part will be deleted after a confirmation.
     * @param actionEvent deletes the selected part from the partTable through the use of a button
     */
    public void deletePart(ActionEvent actionEvent) {
        if (partTable.getSelectionModel().isEmpty()){
            Alert alert = new Alert(AlertType.ERROR,"NO PART SELECTED.\nPlease select a part from the list above.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.CONFIRMATION,"Are you sure you want to delete this part?");
            Optional <ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                int selectedPart = partTable.getSelectionModel().getSelectedIndex();
                partTable.getItems().remove(selectedPart);
            }
        }
    }


//Delete Product Button//

    /**
     * Deletes the selected product from the productTable. If there is no product selected then an error is thrown for the user to select one before deleting. If the selected product has
     * associated parts, then it can not be deleted. If there are no associated parts and a product is selected then it will be deleted after a confirmation.
     * @param actionEvent deletes the selected product from the productTable through the use of a button
     * @throws IOException ignore
     */
    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        if (productTable.getSelectionModel().isEmpty()){
            Alert errorAlert = new Alert(AlertType.ERROR,"NO PRODUCT SELECTED.\nPlease select a product from the list above.");
            errorAlert.showAndWait();
        }
        else { 
            Product selectedProd = productTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(AlertType.CONFIRMATION,"Are you sure you want to delete this product?");
            Optional <ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK && selectedProd.getAssociatedPart().size() != 0){
                Alert associatedPartAlert = new Alert(AlertType.ERROR,"PRODUCT HAS ASSOCIATED PARTS.\nYou should not delete a product with associated parts.");
                associatedPartAlert.showAndWait();
                
                Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            if (option.get() == ButtonType.OK && selectedProd.getAssociatedPart().isEmpty()){
                int selectedProduct = productTable.getSelectionModel().getSelectedIndex();
                productTable.getItems().remove(selectedProduct);
            }
        }
    }
    
    
//Part Search Field//

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
                    Alert alert = new Alert(AlertType.ERROR,"PRODUCT NOT FOUND.");
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
    
//Product Search Field//
    /**
     * Searches for products by either partial name or part ID. If no product is found an error will be displayed stating so. If the search box is empty and the user hits the enter key, the
     * search will return the full list of products. Otherwise, the returned values are those that have the searched ID or contain the partial name.
     * @param actionEvent searches for the products by partial name or ID on enter
     */
    public void productSearch(ActionEvent actionEvent) {
        String q = productSearch.getText();
        ObservableList<Product> product = searchByProductName(q);
        
        try{
            if(product.isEmpty()){
                int x = Integer.parseInt(q);
                Product p = searchByProductID(x);
                if(p != null){
                    product.add(p);
                }
                else {
                    Alert alert = new Alert(AlertType.ERROR,"PRODUCT NOT FOUND.");
                    alert.showAndWait();
                }
            }
        }
        catch(NumberFormatException e){
            //Ignore//
        }
        
        productTable.setItems(product);
        productSearch.setText("");
    }
    //Name Search//
    private ObservableList<Product> searchByProductName(String partialName){
        ObservableList<Product> productNameResult = FXCollections.observableArrayList();
        ObservableList<Product> allProduct = Inventory.getAllProduct();
        for(Product p : allProduct){
            if(p.getName().contains(partialName)){
                productNameResult.add(p);
            }
        }
        return productNameResult;
    }
    //ID Search//
    private Product searchByProductID(int ID){
        ObservableList<Product> allProduct = Inventory.getAllProduct();
        for(Product p : allProduct) {
            if(p.getProductID()== ID){
                return p;
            }
        }
        return null;
    }  
    
    
    /**
     * Initializes the controller class.
     * Initializes the partTabl and productTable with the predefined items in the main.java file
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTable.setItems(Inventory.getAllPart());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTable.setItems(Inventory.getAllProduct());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inventory"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }
}

