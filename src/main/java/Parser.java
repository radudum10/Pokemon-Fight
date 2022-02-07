import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    /* returns a list with all the trainers from json */
    public ArrayList<Trainer> parseTrainers(Path path) {
        String jsonBody = null;
        /* put all the content of the json file in a String */
        try {
            jsonBody = Files.readString(path);
        } catch (IOException e) {
            System.out.println("IOException: " + path);
            e.printStackTrace();
        }

        Gson gson = new Gson();
        /* make a TypeToken and return the list */
        Type type = new TypeToken<List<Trainer>>(){}.getType();

        return gson.fromJson(jsonBody, type);
    }

    /* returns a Pokemon from json */
    public Pokemon parsePokemon(String name) {
        String jsonBody = null;
        String path = "Pokemons/" + name + ".json";

        try {
            jsonBody = Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.printf("Hey! This pokemon %s doesn't exist!\n", name);
            e.printStackTrace();
        }

        Gson gson = new Gson();

        /* make a TypeToken and return the Pokemon */
        Type type = new TypeToken<Pokemon>(){}.getType();

        return gson.fromJson(jsonBody, type);
    }
}
