import java.util.ArrayList;
import java.util.Random;

public class Trainer {
    private String name;
    private int age;
    private ArrayList<String> bag;
    private ArrayList<ArrayList<String>> items;

    public Trainer(String name, int age, ArrayList<String> bag,
                   ArrayList<ArrayList<String>> items) {
        this.name = name;
        this.age = age;
        this.bag = bag;
        this.items = items;
    }

    public Trainer() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getBag() {
        return bag;
    }

    public ArrayList<ArrayList<String>> getItems() {
        return items;
    }

    public String giveAction(ArrayList<String> possibleActions) {
        Random generator = new Random();
        int index = generator.nextInt(possibleActions.size());

        return possibleActions.get(index);
    }

}
