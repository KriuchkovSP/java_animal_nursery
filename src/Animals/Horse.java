package Animals;
import java.time.LocalDate;

public class Horse extends Pack implements BaseInterface{
    private String subspecies;
    public Horse(String name, LocalDate dateOfBirth) throws AnimalsIsNotCreateException {
        super(name, dateOfBirth);
        this.subspecies = "Horse";
    }
    public String getSubcpecies() {return subspecies;}
    
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, DateOfBirth: %d-%d-%d, Type: %s, Subspecies: %s\r\n", 
         this.getId(), this.getName(), this.getDateOfBirth().getDayOfMonth(), this.getDateOfBirth().getMonthValue(), 
         this.getDateOfBirth().getYear(), this.getType(), this.getSubcpecies());
    }
}
