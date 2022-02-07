import java.nio.file.Path;
import java.util.ArrayList;

import static java.lang.System.exit;

public class Adventure {
    private final String inPath;
    private final String outPath;
    private final AdventureHelper helper;

    public Adventure(String inPath, String outPath) {
        this.inPath = inPath;
        this.outPath = outPath;
        this.helper = new AdventureHelper(outPath);
    }

    public void startAdventure() {
        Parser parser = new Parser();
        ArrayList<Trainer> trainerList = parser.parseTrainers(Path.of(inPath));
        Trainer firstTrainer = trainerList.get(0);
        Trainer secondTrainer = trainerList.get(1);
        Logger.printToStream("Our adventure starts now!", outPath);
        Logger.printToStream("----------------------------------------",
                outPath);
        startStage(firstTrainer, secondTrainer,0);
        Logger.printToStream("Stages 2 starts!", outPath);
        Logger.printToStream("----------------------------------------",
                outPath);
        startStage(firstTrainer, secondTrainer, 1);

        Logger.printToStream("Stages 3 starts!", outPath);
        Logger.printToStream("----------------------------------------",
                outPath);
        startStage(firstTrainer, secondTrainer, 2);

        Logger.printToStream("Stages 4 starts!", outPath);
        Logger.printToStream("----------------------------------------",
                outPath);
        finalStage(firstTrainer, secondTrainer);
    }

    private void startStage(Trainer firstTrainer, Trainer secondTrainer, int index) {
        String firstPokeball = firstTrainer.getBag().get(index);
        String secondPokeball = secondTrainer.getBag().get(index);
        String opponent = helper.getOpponent(secondPokeball);

        if (!opponent.equals(secondPokeball)) {
            trainerVsNPC(firstTrainer, secondTrainer, index, firstPokeball,
                    secondPokeball, opponent);

            /* repeat until trainer vs trainer */
            startStage(firstTrainer, secondTrainer, index);
        }

        else {
           trainerVsTrainer(firstTrainer, secondTrainer, firstPokeball,
                    secondPokeball, index);
        }
    }

    private void finalStage(Trainer firstTrainer, Trainer secondTrainer) {
        String firstBall = helper.getBest(firstTrainer);
        String secondBall = helper.getBest(secondTrainer);

        /* put them on the same index */
        int index = firstTrainer.getBag().indexOf(firstBall);
        int tmp = secondTrainer.getBag().indexOf(secondBall);

        secondTrainer.getBag().set(index, secondTrainer.getBag().get(tmp));
        secondTrainer.getItems().set(index, secondTrainer.getItems().get(tmp));

        String winner = trainerVsTrainer(firstTrainer, secondTrainer,
                firstBall, secondBall, index);

        String champion;
        if (winner.equals(firstBall)) {
            champion = firstTrainer.getName() + "(" + firstTrainer.getAge() +
                    ") with " + firstBall;
        }

        else {
            champion = secondTrainer.getName() + "(" + secondTrainer.getAge() +
                    ") with " + secondBall;
        }
            Logger.printToStream(champion + " WON THE ADVENTURE!",
                    outPath);
    }

    private void trainerVsNPC(Trainer firstTrainer, Trainer secondTrainer,
                              int index, String firstPokeball,
                              String secondPokeball, String opponent) {
        String winner = helper.npcFight(firstTrainer, firstPokeball, opponent,
                index);
        Logger.printToStream("The winner is " + winner, outPath);
        Logger.printToStream("----------------------------------------",
                outPath);

        if (winner.equals("Neutrel1") || winner.equals("Neutrel2")) {
            Logger.printToStream("Trainer" + secondTrainer +
                    " won the adventure!", outPath);
            exit(0);
        }

        Pokemon pokemon = PokemonFactory.getPokemon(firstPokeball);
        pokemon.giveInForm();

        /* the other trainer should fight the other neutrel */
        if (opponent.equals("Neutrel1"))
            opponent = "Neutrel2";
        else
            opponent = "Neutrel1";

        winner = helper.npcFight(secondTrainer, secondPokeball, opponent,
                index);

        Logger.printToStream("The winner is " + winner, outPath);
        Logger.printToStream("----------------------------------------",
                outPath);

        if (winner.equals("Neutrel1") || winner.equals("Neutrel2")) {
            Logger.printToStream("Trainer" + firstTrainer +
                    " won the adventure!", outPath);

            exit(0);
        }

        pokemon = PokemonFactory.getPokemon(winner);
        pokemon.giveInForm();
    }

    private String trainerVsTrainer(Trainer firstTrainer, Trainer secondTrainer,
                                  String firstBall, String secondBall,
                                  int index) {
        String winner = helper.trainerFight(firstTrainer, secondTrainer,
                firstBall, secondBall, index);

        Logger.printToStream("The winner is " + winner, outPath);
        Logger.printToStream("----------------------------------------",
                outPath);

        Pokemon toBeUpgraded;
        if (winner.equals(firstBall)) {
            toBeUpgraded = PokemonFactory.getPokemon(firstBall);
        }
        else {
            toBeUpgraded = PokemonFactory.getPokemon(secondBall);
        }

        toBeUpgraded.giveInForm();

        return winner;
    }
}
