import java.io.*;
import java.util.ArrayList;

public class Logger {
    private static Logger instance = null;
    ArrayList<FightObserver> observers = new ArrayList<>();

    private Logger() {}

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();

        return instance;
    }

    public void addSubscriber(FightObserver observer) {
        observers.add(observer);
    }

    public void cleanSubscribers() {
        observers.clear();
    }

    public void notify(String attacker, String action, int dmg, boolean stun) {
        for (FightObserver observer : observers) {
            observer.update(attacker, action, dmg, stun);
        }
    }

    public static void printToStream(String message, String path) {
        if (path.equals("stdout"))
            System.out.println(message);

        else {
            PrintWriter out = null;
            try {
                out = new PrintWriter(new BufferedWriter(new FileWriter(
                        path, true)));
                out.println(message);
            } catch (IOException e) {
                System.out.println("Output error");
                e.printStackTrace();
            } finally {
                assert out != null;
                out.close();
            }
        }
    }
}
