package Animals;
import java.util.ArrayList;
import java.util.List;

public class PetCommands {
    private List<Command> commands = new ArrayList<>();
    public void addCommand(Command comm) {
        commands.add(comm);
        System.out.println("Животное обучено новой команде");
    }
    @Override
    public String toString() {
        String res = "";
        for (int i = 0; i < commands.size(); i++) {
            res += String.format("%s ", commands.get(i));
        }
        return res;
    }
}
