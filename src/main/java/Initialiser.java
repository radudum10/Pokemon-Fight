
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Initialiser {
    public static void main(String[] args) {
        try {
            Files.deleteIfExists(Path.of(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Adventure adventure = new Adventure(args[0], args[1]);
        adventure.startAdventure();
    }
}


