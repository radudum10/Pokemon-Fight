import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.*;

public class Duel {
    private Trainer combatantOneTrainer;
    private Trainer combatantTwoTrainer;
    private Pokemon combatantOne;
    private Pokemon combatantTwo;
    private final String path;

    public Duel(String path) {
        this.path = path;
    }

    public void setFirstPair(Trainer trainer, Pokemon combatant) {
        this.combatantOneTrainer = trainer;
        this.combatantOne = combatant;
    }

    public void setSecondPair(Trainer trainer, Pokemon combatant) {
        this.combatantTwoTrainer = trainer;
        this.combatantTwo = combatant;
    }

    private void initLoggers() {
        Logger logger = Logger.getInstance();
        logger.cleanSubscribers();

        ActionNarrator firstNarator = new ActionNarrator(this.path);
        firstNarator.setVictim(combatantOne.getName());
        logger.addSubscriber(firstNarator);

        ActionNarrator secondNarrator = new ActionNarrator(this.path);
        secondNarrator.setVictim(combatantTwo.getName());
        logger.addSubscriber(secondNarrator);

        HealthMonitor firstMonitor = new HealthMonitor(this.path);
        firstMonitor.setPokemonName(combatantOne.getName());
        firstMonitor.setInitialHp(combatantOne.getHp());
        logger.addSubscriber(firstMonitor);

        HealthMonitor secondMonitor = new HealthMonitor(this.path);
        secondMonitor.setPokemonName(combatantTwo.getName());
        secondMonitor.setInitialHp(combatantTwo.getHp());
        logger.addSubscriber(secondMonitor);
    }

    public String letThemFight() {
        startMessage();
        Fight turnOne = new Fight();
        turnOne.setAttackingPair(combatantOneTrainer, combatantOne);
        turnOne.setDefendingPair(combatantTwoTrainer, combatantTwo);

        Fight turnTwo = new Fight();
        turnTwo.setAttackingPair(combatantTwoTrainer, combatantTwo);
        turnTwo.setDefendingPair(combatantOneTrainer, combatantOne);

        /* deciding who strikes first */
        Random generator = new Random();
        int coinFlip = generator.nextInt(2);
        if (coinFlip == 1) {
            Fight tmp = turnOne;
            turnOne = turnTwo;
            turnTwo = tmp;
        }

        initLoggers();
        ExecutorService executorService = Executors.newFixedThreadPool(1); // the number 1 is very important here
        while (fightStatus()) {
            /* adding to the collection the future tasks (first attack and second attack basically) */
            Collection<Future<?>> tasks = new LinkedList<>();
            Future<?> future = executorService.submit(turnOne);
            tasks.add(future);
            future = executorService.submit(turnTwo);
            tasks.add(future);

            /* the next task starts when the previous one finished */
            for (Future<?> currTask : tasks) {
                try {
                    currTask.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        executorService.shutdown();
        return winner();
    }

    private boolean fightStatus() {
        return combatantOne.getHp() > 0 && combatantTwo.getHp() > 0;
    }

    private void startMessage() {
        String message = "The fight between " + combatantOne.getName() +
                "(" + combatantOne.getHp() + ")" + " and " +
                combatantTwo.getName() + "(" + combatantTwo.getHp()  +
                ") starts!";

        Logger.printToStream(message, path);
    }

    private String winner() {
        if (combatantOne.getHp() <= 0 && combatantTwo.getHp() <= 0)
            return "Draw";

        else if (combatantOne.getHp() <= 0)
            return combatantTwo.getName();

        else
            return combatantOne.getName();
    }
}
