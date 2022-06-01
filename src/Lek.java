import java.lang.annotation.*;
import java.util.Arrays;



class ArrayIterator {
    private int[] arr;
    private int capacity;
    private int currentElement;

    public ArrayIterator(int[] arr) {
        this.arr = arr;
        this.capacity = arr.length;
        this.currentElement = 0;
    }

    public int next() { return arr[currentElement++]; }
    public boolean hasNext() { return currentElement < capacity; }
}

public class Lek {
    public static void main(String[] args) {
        ArrayIterator iterator = new ArrayIterator(new int[] {1,2,3,4,5});
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
     }
}