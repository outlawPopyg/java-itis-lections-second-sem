import java.util.*;


public class Lek {

    static class Friend {
        private final String name;
        public Friend(String name) {
            this.name = name;
        }
        public String getName() { return this.name; }

        public synchronized void bow(Friend bower, int threadNumber) {
            System.out.println("Поток номер " + threadNumber + " удерживает объект " + this.getName());
            bower.bowBack(this, threadNumber);
        }

        public synchronized void bowBack(Friend bower, int threadNumber) {
            System.out.println("Поток номер " + threadNumber + " удерживает объект " + bower.getName());
        }
    }

    public static void main(String[] args) {
        Friend alphonse = new Friend("alphonse");
        Friend gaston = new Friend("gaston");

        new Thread(new Runnable() {
            @Override
            public void run() {
                alphonse.bow(gaston, 1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                gaston.bow(alphonse, 2);
            }
        }).start();
    }
}