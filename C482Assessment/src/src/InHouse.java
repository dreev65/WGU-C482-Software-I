package src;
/**
 * InHouse class that extends the Part class. Determines the value order of the inHouse parts created by the user or predefined parts.
 * @author Daniel Reeve
 */

public class InHouse extends Part {
    private int machineID;
    
    /**
     * Constructs InHouse part.
     * @param partID part ID 
     * @param partName part name
     * @param partPrice part price
     * @param partStock part inventory
     * @param min minimum part quantity
     * @param max maximum part quantity
     * @param machineID machine ID for part
     */
    public InHouse(int partID, String partName, double partPrice, int partStock, int min, int max, int machineID) {
        super(partID, partName, partPrice, partStock, min, max);
        this.machineID = machineID;
    }
    
    /**
     * getMachineID class retrieves the machine ID from the setMachineID class
     * @return returns the machine ID
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * Sets the machineID for the inHouse parts
     * @param machineID machine id for the inHouse part
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
