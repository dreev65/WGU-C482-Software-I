
package controller;

import src.InHouse;
import src.Inventory;
import src.Outsourced;
import src.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class<br>
 * Initializes the ModifyPartController. All fields except the ID can be modified. 
 * @author Daniel Reeve
 */
public class ModifyPartController implements Initializable {

    @FXML private RadioButton outsourcedRadio;
    @FXML private RadioButton inHouseRadio;
    @FXML private Label variableLabel;
    @FXML private TextField idBox;
    @FXML private TextField nameBox;
    @FXML private TextField inventoryBox;
    @FXML private TextField priceBox;
    @FXML private TextField maxBox;
    @FXML private TextField minBox;
    @FXML private TextField variableBox;
   
    private int partID;

    /**
     * Declares the selectedPart as being part of the Part class.
     */
    public Part selectedPart;
    
    
//Determine Inhouse or Outsourced Part//

    /**
     * The radioSelect determines if the part is inHouse or Outsourced. Also sets the variableLabel to "Company Name" or "Machine ID" depending on what radio button is selected.
     */
    public void radioSelect() {
        if (outsourcedRadio.isSelected()){
            this.variableLabel.setText("Company Name");
        }
        else {
            this.variableLabel.setText("Machine ID");
        }
    }    
    
//Set Selected Part//

    /**
     * Sets the selectedPart data to their respective text-boxes so that the use can modify them, except for the ID. The Id can not be modified.
     * @param selectedPart the part that was selected for modification
     */
    public void transferPart(Part selectedPart) {
        this.selectedPart = selectedPart;
        partID = Inventory.getAllPart().indexOf(selectedPart);
        idBox.setText(Integer.toString(selectedPart.getId()));
        nameBox.setText(selectedPart.getName());
        inventoryBox.setText(Integer.toString(selectedPart.getStock()));
        priceBox.setText(Double.toString(selectedPart.getPrice()));
        maxBox.setText(Integer.toString(selectedPart.getMax()));
        minBox.setText(Integer.toString(selectedPart.getMin()));
        
        if(selectedPart instanceof InHouse ih){
            inHouseRadio.setSelected(true);
            this.variableLabel.setText("Machine ID");
            variableBox.setText(Integer.toString(ih.getMachineID()));
        }
        else{
            Outsourced os = (Outsourced) selectedPart;
            outsourcedRadio.setSelected(true);
            this.variableLabel.setText("Company Name");
            variableBox.setText(os.getCompanyName());
        }
    }
    
//Save Button//

    /**
     * Saves the part being modified and returns the user to the main stage (Inventory). Before the part can be saved, conditional statements check to see if the part has improper entries
     * and asks the user if they do want to save the part. If improper entries are entered, then errors are displayed to inform the user of what needs corrected.<br>
     * LOGICAL ERROR: The user could try saving a part with a min that is higher than the max or the inventory does not fall between the min and max values. This was corrected by adding 
     * alerts that prevent the user from saving without correcting the issue.<br>
     * RUNTIME ERROR: NumberFormatException can be produced, causing the application to crash, if the user enters a value into the int text fields that is non-numerical.
     * This has been fixed by implementing a try and catch statements. In the catch statement,when a NumberFormatException is produced, an alert is thrown instead of crashing the application.
     * @param actionEvent saves the modified part
     
     */
    public void saveButton(ActionEvent actionEvent) throws IOException{
        
        try {
            int partInventory = Integer.parseInt(inventoryBox.getText());
            int partMax = Integer.parseInt(maxBox.getText());
            int partMin = Integer.parseInt(minBox.getText());

            Alert alert = new Alert(AlertType.CONFIRMATION, "Would you like to save the part?");
            Optional <ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK){
                if (partMax < partMin){
                    Alert minAlert = new Alert(AlertType.ERROR, "ERROR!\nMax value higher than min value.\nPlease check entries.");
                    minAlert.showAndWait();
                }
                else if(partInventory < partMin || partInventory > partMax) {
                    Alert inventoryAlert = new Alert(AlertType.ERROR, "ERROR!\nInventory is not between min and max values.\nPlease check entry.");
                    inventoryAlert.showAndWait();
                }
                else {
                    int id = Integer.parseInt(idBox.getText());
                    String name = nameBox.getText();
                    double price = Double.parseDouble(priceBox.getText());
                    int inventory = Integer.parseInt(inventoryBox.getText());
                    int max = Integer.parseInt(maxBox.getText());
                    int min = Integer.parseInt(minBox.getText());

                    if (inHouseRadio.isSelected()) {
                        int machineID = Integer.parseInt(variableBox.getText());
                        InHouse temp = new InHouse(id, name, price, inventory, min, max, machineID);
                        Inventory.modifyPart(partID, temp);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        String companyName = variableBox.getText();
                        Outsourced temp = new Outsourced(id, name, price, inventory, min, max, companyName);
                        Inventory.modifyPart(partID, temp);
                        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
                        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
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
    
//Cancel Button//
    /**
     * Cancels the part modification and returns the user to the main stage (Inventory).
     * @param actionEvent cancels the new part creation
     * @throws IOException ignore
     */
    public void cancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }    
    
    
    /**
     * Initializes the controller class.
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }   
}
