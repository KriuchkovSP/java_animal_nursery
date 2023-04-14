package Animals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Animals {
    private int id;
    private String name;
    private LocalDate dateOfBirth;
    private PetCommands commands;
    private static List<Animals> animals = new ArrayList<>();
    
    public int getId() {return id;}
    public String getName() {return name;}
    public LocalDate getDateOfBirth(){return dateOfBirth;}
    public PetCommands getCommands(){return commands;}
    
    public void setId() {}
    public int setName(String name) {
        if (name.equals("")) {
            return 1;
        }
        this.name = name;
        return 0;
    }
    public void setDateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
    }

    public void setCommands(Command comm) {
        commands.addCommand(comm);
    }
    
    public Animals(String name, LocalDate dateOfBirth)  throws AnimalsIsNotCreateException {
        int error = 0;
        animals.add(this);
        error += setName(name);
        setDateOfBirth(dateOfBirth);
        if (error != 0) {throw new AnimalsIsNotCreateException(error, "Ошибка создания экземпляра класса");}
    }

}
