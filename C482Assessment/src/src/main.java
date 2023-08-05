package src;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *  The main class for the program.<br>
 *  FUTURE ENHANCEMENTS:The user should be able to export the parts and products lists to a PDF so that they can make an inventory list. This would make it easier to manage what
 *  they currently have in stock rather than try to run threw the tables for orders. Likewise, an option to filter the parts and products that are close to their minimum inventory 
 *  values could be implemented to just see the items they need to order. This would make purchasing an easier and less time-consuming task.<br><br>
 *  All javadoc comments are right above each method or class. Javadoc index.html can be found by going to "C482Assessment\dist\javadoc\index.html".
 * 
 *  @author Daniel Reeve
 */

public class main extends Application{

    /**
     * Initializes the stage for the application and defines predetermine parts and products
     * @param mainStage the main stage of the application
     * @throws java.io.IOException ignore
     */
    @Override
    public void start(Stage mainStage) throws IOException {
        
    //Add Parts to InHouse//
        Inventory.addPart(new InHouse(1, "Brakes", 15.00, 10, 1, 25, 0));
        Inventory.addPart(new InHouse(2, "Wheel", 11.00, 16, 1, 25, 0));

    //Add Parts to OutSourced//
        Inventory.addPart(new Outsourced(3, "Lights", 20.00, 22, 1, 25, "null"));
        Inventory.addPart(new Outsourced(4, "Hoods", 300.00, 5, 1, 25, "null"));
       
    //Add to Products//
        Inventory.addProduct(new Product(1000, "Giant Bike", 299.99, 5, 1, 25));
        Inventory.addProduct(new Product(1001, "Tricycle", 99.99, 3, 1, 25));
        
    //Start Stage//
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();
    }
    
    /**
     * Launches the main stage for the application
     * @param args executes the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}
