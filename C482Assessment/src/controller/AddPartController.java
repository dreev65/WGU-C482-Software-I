
package controller;

import src.InHouse;
import src.Outsourced;
import src.Inventory;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static src.Inventory.getAllPart;




/**
 * FXML Controller <br>
 * Initializes the AddPartController. Used when the user clicks the add button in the Parts pane of the main (Inventory) stage. Parts created are identified as inHouse or Outsourced
 * parts. Each are given their own unique id that the user can not select on their own. It is system generated.
 * @author Daniel Reeve
 */
public class AddPartController implements Initializable {
     
    @FXML private RadioButton outsourcedRadio;
    @FXML private Label variableLabel;
    @FXML private TextField nameBox;
    @FXML private TextField inventoryBox;
    @FXML private TextField priceBox;
    @FXML private TextField maxBox;
    @FXML private TextField minBox;
    @FXML private TextField variableBox;
    
    
    
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
    
//ID Generation//

    /**
     * Generates the unique part ID for the new part.
     * @return returns the new part ID
     */
    public static int getNewID(){
        int newID = 1;
        for (Part allPart : getAllPart()) {
            newID++;
        }
        return newID;
    }
    
    /**
     * Saves the new part being created and returns the user to the main stage (Inventory). Before the part can be saved, conditional statements check to see if the part has improper entries
     * and asks the user if they do want to save the part. If improper entries are entered, then errors are displayed to inform the user of what needs corrected.<br>
     * LOGICAL ERROR: The user could try saving a part with a min that is higher than the max or the inventory does not fall between the min and max values. This was corrected by adding 
     * alerts that prevent the user from saving without correcting the issue.<br>
     * RUNTIME ERROR: NumberFormatException can be produced, causing the application to crash, if the user enters a value into the int text fields that is non-numerical.
     * This has been fixed by implementing a try and catch statements. In the catch statement,when a NumberFormatException is produced, an alert is thrown instead of crashing the application.
     * @param actionEvent saves the new part
     * @throws IOException throws and error when the part can not be saved
     */
    public void saveButton(ActionEvent actionEvent) throws IOException {
        try {
            int partInventory = Integer.parseInt(inventoryBox.getText());
            int partMin = Integer.parseInt(minBox.getText());
            int partMax = Integer.parseInt(maxBox.getText());
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to save the part?");
            Optional <ButtonType> option = alert.showAndWait();
            
            if (option.get() == ButtonType.OK){
                if (partMax < partMin){
                    Alert minAlert = new Alert(Alert.AlertType.ERROR, "ERROR!\nMax value higher than min value.\nPlease check entries.");
                    minAlert.showAndWait();
                }
                else if (partInventory < partMin || partInventory > partMax) {
                    Alert inventoryAlert = new Alert(Alert.AlertType.ERROR, "ERROR!\nInventory is not between min and max values.\nPlease check entry.");
                    inventoryAlert.showAndWait();
                }
                else {
                    int newID = getNewID();
                    String name = nameBox.getText();
                    int inventory = partInventory;
                    double price = Double.parseDouble(priceBox.getText());
                    int max = partMax;
                    int min = partMin;

                    if (outsourcedRadio.isSelected()) {
                        String company = variableBox.getText();
                        Outsourced temp = new Outsourced(newID, name, price, inventory, min, max, company);
                        Inventory.addPart(temp);
                    }
                    else {
                        int machineID = Integer.parseInt(variableBox.getText());
                        InHouse temp = new InHouse(newID, name, price, inventory, min, max, machineID);
                        Inventory.addPart(temp);
                    }
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

//Cancel Button//
    /**
     * Cancels the new part creation and returns the user to the main stage (Inventory).
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
    }    

}
