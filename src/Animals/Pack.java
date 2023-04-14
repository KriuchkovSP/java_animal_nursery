package Animals;
import java.time.LocalDate;

public abstract class Pack extends Animals{
    private String type;
    public Pack(String name, LocalDate dateOfBirth) throws AnimalsIsNotCreateException {
        super(name, dateOfBirth);
        this.type = "Pack";
    }
    public String getType() {return type;}
}
