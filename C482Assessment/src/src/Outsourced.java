package src;
/**
 * Outsourced class that extends the Part class. Determines the value order of the Outsourced parts created by the user or predefined parts.
 * @author Daniel Reeve
 */
public class Outsourced extends Part{
    private String companyName;
    
    /**
     *
     * Constructs Outsourced part.
     * @param partID part ID 
     * @param partName part name
     * @param partPrice part price
     * @param partStock part inventory
     * @param min minimum part quantity
     * @param max maximum part quantity
     * @param companyName company name for part
     */
    public Outsourced(int partID, String partName, double partPrice, int partStock, int min, int max, String companyName) {
        super(partID, partName, partPrice, partStock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * getCompanyName class retrieves the company name from the setCompanyName class
     * @return returns the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets the companyName for the Outsourced parts
     * @param companyName name for the company providing the outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
}
