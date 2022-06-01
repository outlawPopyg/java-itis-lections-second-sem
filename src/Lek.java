import java.lang.annotation.*;
import java.util.Arrays;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface RuntimeAnnotation {
    String name() default "ABC";
    String city() default "CDE";
}

@RuntimeAnnotation
class RuntimeAnnotationTestClass {}

public class Lek {
    public static void main(String[] args) {
        Annotation annotation = RuntimeAnnotationTestClass.class.getAnnotation(RuntimeAnnotation.class);
        RuntimeAnnotation annotation1 = (RuntimeAnnotation) annotation;

        System.out.println(annotation1.city() + " " + annotation1.name());
    }
}