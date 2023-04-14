package Animals;

import java.time.LocalDate;

public class Dog extends Pet implements BaseInterface{
    private String subspecies;
    public Dog(String name, LocalDate dateOfBirth) throws AnimalsIsNotCreateException {
        super(name, dateOfBirth);
        this.subspecies = "Dog";
    }
    public String getSubcpecies() {return subspecies;}

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, DateOfBirth: %d-%d-%d, Type: %s, Subspecies: %s\r\n", 
         this.getId(), this.getName(), this.getDateOfBirth().getDayOfMonth(), this.getDateOfBirth().getMonthValue(), 
         this.getDateOfBirth().getYear(), this.getType(), this.getSubcpecies());
    }
    
}

