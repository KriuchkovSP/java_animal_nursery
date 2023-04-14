package Animals;
import java.time.LocalDate;

public class Donkey extends Pack implements BaseInterface{
    private String subspecies;
    public Donkey(String name, LocalDate dateOfBirth) throws AnimalsIsNotCreateException {
        super(name, dateOfBirth);
        this.subspecies = "Donkey";
    }
    public String getSubcpecies() {return subspecies;}

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, DateOfBirth: %d-%d-%d, Type: %s, Subspecies: %s, Commands: %s", 
         this.getId(), this.getName(), this.getDateOfBirth(), this.getDateOfBirth(), 
         this.getDateOfBirth().getYear(), this.getType(), this.getSubcpecies());
    }
    
}
