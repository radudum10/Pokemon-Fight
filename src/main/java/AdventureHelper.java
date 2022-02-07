import java.util.ArrayList;
import java.util.Random;

public class AdventureHelper {
    private final String outPath;

    public AdventureHelper(String outPath) {
        this.outPath = outPath;
    }

    /* generates random an opponent */
    public String getOpponent(String pokeball) {
        ArrayList<String> candidates = new ArrayList<>();
        candidates.add(pokeball);
        candidates.add("Neutrel1");
        candidates.add("Neutrel2");

        Random generator = new Random();
        int index = generator.nextInt(candidates.size());

        return candidates.get(index);
    }

    /* trainer vs npc fight */
    public String npcFight(Trainer trainer, String pokeBall, String npc,
                           int index) {
        Pokemon pokemon = PokemonFactory.getPokemon(pokeBall);
        Pokemon npcPok = PokemonFactory.getPokemon(npc);

        Pokemon pokemonClone = clonePokemon(pokemon);
        pokemonClone = equipPokemon(trainer, pokemonClone, index);
        Pokemon npcClone = clonePokemon(npcPok);
        Trainer cpuTrainer = new Trainer(); // neutrels also deserve a trainer <3
        Duel duel = new Duel(outPath);
        duel.setFirstPair(trainer, pokemonClone);
        duel.setSecondPair(cpuTrainer, npcClone);

        return duel.letThemFight();
    }

    /* trainer vs trainer fight */
    public String trainerFight(Trainer firstTrainer, Trainer secondTrainer,
                               String firstBall, String secondBall,
                               int index) {
        Pokemon firstPok = PokemonFactory.getPokemon(firstBall);
        Pokemon secondPok = PokemonFactory.getPokemon(secondBall);

        Pokemon firstClone = clonePokemon(firstPok);
        Pokemon secondClone = clonePokemon(secondPok);

        firstClone = equipPokemon(firstTrainer, firstClone, index);
        secondClone = equipPokemon(secondTrainer, secondClone, index);

        Duel duel = new Duel(outPath);
        duel.setFirstPair(firstTrainer, firstClone);
        duel.setSecondPair(secondTrainer, secondClone);

        return duel.letThemFight();
    }

    /* adding attributes given by items */
    public Pokemon equipPokemon(Trainer trainer, Pokemon pokemon, int index) {
        ArrayList<String> itemList = trainer.getItems().get(index);
        PokemonUpgrader upgrader = new PokemonUpgrader();
        for (String item : itemList) {
            pokemon = upgrader.upgrade(pokemon, item);
        }

        return pokemon;
    }

    /* this pokemons goes to war! */
    public Pokemon clonePokemon(Pokemon pokemon) {
        Pokemon clone = new Pokemon();
        Pokemon.modifier(clone)
                .name(pokemon.getName())
                .hp(pokemon.getHp())
                .attack(pokemon.getAttack())
                .specialAttack(pokemon.getSpecialAttack())
                .def(pokemon.getDef())
                .specialDef(pokemon.getSpecialDef())
                .abilities(pokemon.getAbilities())
                .build();

        return clone;
    }

    /* gets the best pokemon of a trainer */
    public String getBest(Trainer trainer) {
        String firstPok = trainer.getBag().get(0);
        Pokemon best = PokemonFactory.getPokemon(firstPok);
        int overall = best.overall();

        for (int i = 0; i < 2; i++) {
            String nextPok = trainer.getBag().get(i);
            Pokemon candidate = PokemonFactory.getPokemon(nextPok);
            int candidateOverall = candidate.overall();
            if (candidateOverall > overall) {
                best = candidate;
                overall = candidateOverall;
            }
        }

        return best.getName();
    }
}
