import java.util.concurrent.ConcurrentHashMap;

public class PokemonFactory {
    /* brings out a Pokemon from its pokeball (stored in trainer's bag)
    actually, if the wanted pokemon is not created, then it will be created,
    else just return the pokemon */

    /* using Flyweight pattern */

    /* using ConcurrentHashMap for thread safety */
    private static final ConcurrentHashMap<String, Pokemon> pokemons =
            new ConcurrentHashMap<>();

    public static Pokemon getPokemon(String name) {
        Pokemon ans = pokemons.get(name);
        if (ans == null) {
            Parser parser = new Parser();
            ans = parser.parsePokemon(name);
            pokemons.put(name, ans);
        }

        return ans;
    }
}
