public interface FightObserver {
    void update(String attacker, String action, int dmg, boolean stun);
}
