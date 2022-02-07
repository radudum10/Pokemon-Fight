public class Ability {
    private final int dmg;
    private final boolean stun;
    private final boolean dodge;
    private final int cooldown;
    private int currentCooldown = 0;

    public Ability(int damage, boolean stun, boolean dodge, int cooldown) {
        this.dmg = damage;
        this.stun = stun;
        this.dodge = dodge;
        this.cooldown = cooldown;
    }

    public int getDmg() {
        return dmg;
    }

    public boolean isStun() {
        return stun;
    }

    public boolean isDodge() {
        return dodge;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void cooldown() {
        this.currentCooldown = this.cooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }

    /* in order to use the ability again, a number of rounds should pass */
    public void decrementCooldown() {
        this.currentCooldown--;
    }
}
