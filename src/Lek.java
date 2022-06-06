import java.util.ArrayList;
import java.util.List;

class Fruit {}
class Citrus extends Fruit {
    int weight;
}
class Orange extends Citrus { }

public class Lek {

    public static int totalWeight(ArrayList<? extends Citrus> oranges) {
        int weight = 0;
        for (int i = 0; i < oranges.size(); i++) {
            weight += oranges.get(i).weight;
        }

//       oranges.add(new Citrus()) ошибка записывать нельзя, потому что вместо ? может быть допустим
//       Orange, и мы записываем в list потомков предка чего делать нельзя
//       следовательно из этого листа можео только читать
        return weight;
    }

    public static void addOranges(ArrayList<? super Orange> oranges) {
        for (int i = 0; i < 10; i++) {
            oranges.add(new Orange());
        }
//        Citrus c = oranges.get(0) - нельяз, потому что вместо ? мог бы быть Fruit
//        и мы бы записали в потомка родителя чего делать нельзя
//        следовательно этот лист только для записи
    }


    public static void main(String[] args) {

    }
}