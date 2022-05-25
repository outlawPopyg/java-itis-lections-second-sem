import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

class MyClass {}

public class Lek {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class str = String.class;
        Method[] methods = str.getMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " " + method.getReturnType()); // все методы класса String + возвр знчаения
        }
    }

}