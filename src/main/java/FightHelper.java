import java.util.ArrayList;

public class FightHelper {
    public ArrayList<String> generateAttackingActions(Pokemon pokemon) {
        ArrayList<String> actions = new ArrayList<>();
        if (pokemon.isStunned()) {
            actions.add("None");
            pokemon.setStunned(false);
            return actions;
        }
        if (pokemon.getAttack() != 0)
            actions.add("attack"); // the pokemon either have basic or special attack

        else
            actions.add("specialAttack");

        Ability ability1 = pokemon.getAbility(0);
        if (ability1 == null) /* npc pokemons don't have abilities */
            return actions;

        /* there are some abilities using for dodging only, so why to attack with them*/
        if (ability1.getCurrentCooldown() == 0 &&
                (ability1.getDmg() != 0 || ability1.isStun()))
            actions.add("Ability1");

        Ability ability2 = pokemon.getAbility(1);
        if (ability2.getCurrentCooldown() == 0 &&
                (ability2.getDmg() != 0 || ability2.isStun()))
            actions.add("Ability2");


        return actions;
    }

    public ArrayList<String> generateDefensiveActions(Pokemon pokemon) {
        ArrayList<String> actions = new ArrayList<>();
        actions.add("TakeDMG");

        Ability ability1 = pokemon.getAbility(0);
        if (ability1 == null || pokemon.isStunned()) { // if u are stunned u can't dodge
            pokemon.setStunned(false);
            return actions;
        }

        /* in this case they can use ability only to dodge */
        if (ability1.getCurrentCooldown() == 0 && ability1.isDodge())
            actions.add("Ability1");

        Ability ability2 = pokemon.getAbility(1);
        if (ability2.getCurrentCooldown() == 0 && ability2.isDodge())
            actions.add("Ability2");

        return actions;
    }
    public void basicAttack(Pokemon attacker, Pokemon defender) {
        int dmg = attacker.getAttack() - defender.getDef();
        Logger logger = Logger.getInstance();

        /* if the defender is too strong */
        if (dmg <= 0) {
            logger.notify(attacker.getName(), "rejected", dmg, false);
            return;
        }
        defender.takeDamage(dmg);

        logger.notify(attacker.getName(), "attack", dmg, false);
    }

    public void specialAttack(Pokemon attacker, Pokemon defender) {
        int dmg = attacker.getSpecialAttack() - defender.getSpecialDef();

        Logger logger = Logger.getInstance();
        /* if the defender is too strong */
        if (dmg <= 0) {
            logger.notify(attacker.getName(), "rejected", dmg, false);
            return;
        }
        defender.takeDamage(dmg);

        logger.notify(attacker.getName(), "specialAttack", dmg, false);
    }

    public void useAbility(Pokemon attacker, Pokemon defender, int index) {
        int dmg = attacker.getAbilityDmg(index);
        boolean stun = attacker.getAbilityStun(index);
        defender.takeDamage(dmg);
        defender.setStunned(stun);
        attacker.coolAbility(index);
        index += 1; // my indexing starts from 0, but abilities are indexed from 1
        Logger logger = Logger.getInstance();
        logger.notify(attacker.getName(), "Ability" + index, dmg, stun);
    }

    public void recoverAbilities(Pokemon pokemon) {
        Ability ability1 = pokemon.getAbility(0);
        if (ability1 == null)
            return;

        if (ability1.getCurrentCooldown() != 0)
            ability1.decrementCooldown();
        Ability ability2 = pokemon.getAbility(1);
        if (ability2.getCurrentCooldown() != 0)
            ability2.decrementCooldown();
    }
}
