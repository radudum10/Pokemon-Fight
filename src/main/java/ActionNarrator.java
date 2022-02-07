public class ActionNarrator implements FightObserver {
    /* displaying who attacked who */
    private String victim;
    private final String path;

    public ActionNarrator(String path) {
        this.path = path;
    }

    public void setVictim(String victim) {
        this.victim = victim;
    }

    @Override
    public void update(String attacker, String action, int dmg, boolean stun) {
        if (attacker.equals(victim))
            return;

        String message;
        if (action.equals("rejected"))
            message = attacker + " failed to break the defense!";

        else if (dmg == 0)
            message = attacker + " dodged " + victim + " attack";

        else if (stun) {
            message = attacker + " hit " + victim + " for " + dmg + " with " +
                    action + " and stuns him!";
        }
        else {
            message = attacker + " hit " + victim + " for " + dmg +
                    " with " + action;
        }

        Logger.printToStream(message, path);
    }
}
