import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyOverride {}

class A {
    public void f() {}
}

class B extends A {
    @MyOverride
    public void f() {}
}

public class Lek {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class cv = B.class;
        Method[] methods = cv.getMethods();
        for (Method method : methods) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof MyOverride) {

                }
            }
        }

    }

}