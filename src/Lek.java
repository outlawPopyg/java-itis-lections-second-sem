

class Thr extends Thread {
    int[] arr;
    int max = 0;
    int from;
    int to;

    public Thr(int[] arr, int from, int to) {
        this.arr = arr;
        this.from = from;
        this.to = to;
    }

    public int getMax() { return max; }

    public void run() {
        for (int i = from; i < to; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
    }
}

public class Lek {
    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[] {1, 5, 3, 19, 12, 3};
        Thr t1 = new Thr(arr, 0, arr.length / 2);
        Thr t2 = new Thr(arr, arr.length / 2, arr.length - 1);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(Math.max(t1.getMax(), t2.getMax()));
    }
}