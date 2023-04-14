package Animals;
import java.util.ArrayList;
import java.util.List;

public class PetCommands {
    private List<Command> commands = new ArrayList<>();
    public PetCommands() {
        
    }
    public void addCommand(Command comm) {
        commands.add(comm);
    }
}
