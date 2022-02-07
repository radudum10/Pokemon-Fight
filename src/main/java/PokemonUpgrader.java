public class PokemonUpgrader {
    public Pokemon upgrade(Pokemon pokemon, String type) {
        switch(type) {
            case "shield" -> {
                return giveShield(pokemon);
            }
            case "vest" -> {
                return giveVest(pokemon);
            }
            case "sword" -> {
                return giveSword(pokemon);
            }
            case "ward" -> {
                return giveWard(pokemon);
            }
            case "vitamins" -> {
                return giveVitamins(pokemon);
            }
            case "christmasTree" -> {
                return giveTree(pokemon);
            }
            case "cloak" -> {
                return giveCloak(pokemon);
            }

            default -> {
                return pokemon;
            }
        }
    }

    private Pokemon giveShield(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .def(2)
                .specialDef(2)
                .build();
    }

    private Pokemon giveVest(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .hp(10)
                .build();
    }

    private Pokemon giveSword(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .attack(3)
                .build();
    }

    private Pokemon giveWard(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .specialAttack(3)
                .build();
    }

    /* sounds like doping for me but ok */
    private Pokemon giveVitamins(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .hp(2)
                .attack(2)
                .specialAttack(2)
                .build();
    }

    private Pokemon giveTree(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .attack(3)
                .def(1)
                .build();
    }

    private Pokemon giveCloak(Pokemon pokemon) {
        return Pokemon.modifier(pokemon)
                .specialDef(3)
                .build();
    }
}
