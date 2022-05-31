import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.*;
import java.util.Arrays;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyOverride {}

@interface SomeAnnotation {}

class A {
    public void f(int a, int b) {}
}

class B extends A {
    @MyOverride
    public void f(int a) {}

    @SomeAnnotation
    public void g() {}

}

class C {
    public void f() {
        System.out.println("invoke");
    }
}

public class Lek {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class cv = B.class;
        Method[] methods = cv.getMethods();
        for (Method method : methods) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation instanceof MyOverride) {
                    String methodName = method.getName();
                    Class[] parameters = method.getParameterTypes();
                    Class superClass = cv.getSuperclass();
                    try {
                        superClass.getMethod(methodName, parameters);
                    } catch (NoSuchMethodException exception) {
                        System.out.println("Сигнатура не сходится");
                    }

                }
            }
        }

        Class c = C.class;
        Object o1 = c.newInstance();
        c.getMethod("f").invoke(o1);

    }

}