public class HealthMonitor implements FightObserver {
    private String pokemonName;
    private int initialHp;
    private final String path;

    public HealthMonitor(String path) {
        this.path = path;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public void setInitialHp(int initialHp) {
        this.initialHp = initialHp;
    }

    @Override
    public void update(String attacker, String action, int dmg, boolean stun) {
        if (!attacker.equals(pokemonName) && !action.equals("rejected"))
            initialHp -= dmg;

        String message = "The pokemon " + pokemonName + " is now at "
                + initialHp + "!";

        Logger.printToStream(message, path);
    }
}