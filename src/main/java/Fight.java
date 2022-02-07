public class Fight implements Runnable {
    private Trainer attackerTrainer;
    private Trainer defenderTrainer;
    private Pokemon attacker;
    private Pokemon defender;
    private final FightHelper helper;

    public Fight() {
        helper = new FightHelper();
    }

    public void setAttackingPair(Trainer trainer, Pokemon pokemon) {
        this.attackerTrainer = trainer;
        this.attacker = pokemon;
    }

    public void setDefendingPair(Trainer trainer, Pokemon pokemon) {
        this.defenderTrainer = trainer;
        this.defender = pokemon;
    }

    @Override
    public void run() {
            /* safeguard */
            if (attacker.getHp() <= 0 || defender.getHp() <= 0)
                return;

            FightHelper helper = new FightHelper();
            /* the pokemons and their trainers are preparing for the new attack :D */

            /* generate possible attacking action */
            String attackerAction = attackerTrainer.giveAction
                    (helper.generateAttackingActions(attacker));

            /* generate possible counter-attack action */
            String defenderAction = defenderTrainer.giveAction
                    (helper.generateDefensiveActions(defender));

            /* apply the consequences */
            switch (defenderAction) {
                case "TakeDMG" ->
                        defenderTakeDamage(attackerAction);
                case "Ability1" ->
                    helper.useAbility(defender, attacker, 0);

                case "Ability2" ->
                    helper.useAbility(defender, attacker, 1);
            }

            helper.recoverAbilities(attacker);
            helper.recoverAbilities(defender);
    }

    private void defenderTakeDamage(String attackType) {
        switch(attackType) {
            case "attack" ->
                helper.basicAttack(attacker, defender);
            case "specialAttack" ->
                helper.specialAttack(attacker, defender);
            case "Ability1" ->
                helper.useAbility(attacker, defender, 0);
            case "Ability2" ->
                helper.useAbility(attacker, defender, 1);
        }
    }
}
