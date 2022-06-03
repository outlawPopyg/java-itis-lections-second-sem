import java.lang.*;
import java.util.*;


class Thread1 extends Thread {
    LinkedList<Integer> list;
    public Thread1(LinkedList<Integer> list) {
        this.list = list;
    }

    public void run() {
        synchronized (list) {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
        }
    }
}





public class Lek {
    public static void main(String[] args) throws InterruptedException {
        LinkedList<Integer> list = new LinkedList<>();
        Thread1 thread1 = new Thread1(list);
        Thread1 thread2 = new Thread1(list);
        thread1.start();
        thread2.start();
        Thread.sleep(2000);
        System.out.println(list.size());
    }
}