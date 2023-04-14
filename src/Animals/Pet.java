package Animals;
import java.time.LocalDate;

public abstract class Pet extends Animals{
    private String type;
    public Pet(String name, LocalDate dateOfBirth) throws AnimalsIsNotCreateException {
        super(name, dateOfBirth);
        this.type = "Pet";
    }
    public String getType() {return type;}
}
