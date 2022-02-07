import java.util.ArrayList;

public class Pokemon {
    private String name;
    private int hp;
    private int attack;
    private int specialAttack;
    private int def;
    private int specialDef;
    private ArrayList<Ability> abilities;
    private boolean stunned = false;

    public Pokemon() {
    }

    public static Builder modifier(Pokemon pokemon) {
        return new Builder(pokemon);
    }

    public static class Builder {
        private final Pokemon pokemon;

        public Builder(Pokemon pokemon) {
            this.pokemon = pokemon;
        }

        public Builder name(String name) {
            this.pokemon.name = name;
            return this;
        }

        public Builder hp(int bonus) {
            this.pokemon.hp += bonus;
            return this;
        }

        public Builder attack(int bonus) {
            this.pokemon.attack += bonus;
            return this;
        }

        public Builder specialAttack(int bonus) {
            this.pokemon.specialAttack += bonus;
            return this;
        }

        public Builder def(int bonus) {
            this.pokemon.def += bonus;
            return this;
        }

        public Builder specialDef(int bonus) {
            this.pokemon.specialDef += bonus;
            return this;
        }

        public Builder abilities(ArrayList<Ability> abilities) {
            if (abilities == null)
                return this;

            this.pokemon.abilities = abilities;
            for (Ability ability : abilities) {
                ability.setCurrentCooldown(0);
            }
            return this;
        }

        public Pokemon build() {
            return this.pokemon;
        }
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public int getDef() {
        return def;
    }

    public int getSpecialDef() {
        return specialDef;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public boolean isStunned() {
        return stunned;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
    }

    public Ability getAbility(int index) {
        if (abilities == null)
            return null;

        return abilities.get(index);
    }

    public int getAbilityDmg(int index) {
        return getAbility(index).getDmg();
    }

    public boolean getAbilityStun(int index) {
        return getAbility(index).isStun();
    }

    public void coolAbility(int index) {
        Ability ability = getAbility(index);
        ability.cooldown();
    }

    public void giveInForm() {
        this.hp++;
        this.attack++;
        this.specialAttack++;
        this.def++;
        this.specialDef++;
    }

    public int overall() {
        return this.hp + this.attack + this.specialAttack + this.def +
                this.specialDef;
    }
}
