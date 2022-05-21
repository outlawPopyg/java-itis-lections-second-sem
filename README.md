# Лекции по Java 2 семестр
## Пакеты
  
import не рекурсивная операция  
import static импорт статических методов. Например import static java.lang.Math.cos  
Единица компиляции - понятие означающее базовую конструкцию, которая может быть скомпилирована. Включает в себя: Пакет, Импорты, Объявление сущности  
Допустим есть такая иерархия файлов:
`example` -> `src` -> `game` -> `Game.java`, `Player.java`
`Player.java`:
```java
package game;

public class Player {
	public void battleCry() {
		System.out.println("I'm player");
	}
}
```

`Game.java`:
```java
package game;

public class Game {

	public void go() {
		Player p = new Player();
		p.battleCry();
	}

	public static void main(String[] args) {
		(new Game()).go();
	}
}
```
Поскольку эти два файла лежат в одном пакетк импортировать Player в Game не надо.
Попробуем запустить Game.java, находясь при этом по следующему пути: `example/src/game`: `javac Game.java`:
Получим ошибку: `error: cannot find symbol Player p = new Player()`. Game под носом у себя не видит Player.
Чтобы заработало нужно:
 - Создать иерархию папок, соответсвующую иерархии пакетов
 - Поместить исходные файлы в нужные места
 - Поместить все полученное в спец. папку для исходников(src)
 - компилировать из корня проекта(т.е та папка, которая содержит src)

Надо при компилировании Game сказать, где лежит `Player.java`.
Параметр/ключ для команды в команд. строке под названием `sourcepath`.

`javac -sourcepath src src/game/Game.java` <b>

параметр компиляции `-d`:
 - указывает куда поместить соответствующие скомпилированные файлы
 - указывается как значение папка рядом с src(т.к будет аналогичная иерархия) 

При запуске необходимо указывать откуда брать .class-файлы, необходимые для запуска.
Этот параметр называется `classpath`(при компиляции можно писать cp)

 - переменная, содерж путь(или пути через ; ) к необходимым бинарникам:
 - путь к корню иерархии пакетов проекта 
 - пути к библиотекам

 В нашем случае `classpath` - `bin`

 запуск Game: `java -classpath bin game.Game`

## Generics
### Стирание типов
Происходит после компиляции, потому что нужно поддерживать обратную совместимость.
До пятой версии тип коллекций всегда должен был быть Object. 

### Ограничения:
 - new T(), new T[] - нельзя
 - нельзя делать inctanceof для параметра
 - нельзя делать static поле типа T
 - нельзя перегрузить методы двумя классами с разными параметрами
 	void print(List<String> c) и void print(List<Integer> c) - нельзя
 - В коллекциях должны быть объекты - нельзя примитивы

### Неизвестный тип (Wildcard):
Нам нужно объявить метод, принимающий параметризованный класс, но параметр нам неизвестен

```java
public class Printer {
 	public void print(List<...> elements) {
 		//..
 	}
}
``` 

Что же написать вместо <...>:
 - `List<T>` нельзя, т.к неизвестно что такое T
 - `List` - явная заточенность под Object - а если есть специфика?
 - ?

```java
public class Printer {
 	public void print(List<?> elements) {
 		//..
 	}
}
```
### Ковариантность и контрвариантность
### Ковариантность  
Если студент - частный случай человека, то список из студентов - 
частный случай списка из людей.
### Контрвариантность
Животное - родитель  
Рептилия - наследник (расширяет родителя - значит имеет больший
функционал)  
НО: когда мы говорим про зоопарк, очевидно, что больший функуионал имеет зоопарк из животных.  
=> в этом примере имеет место обратная связь:  
если тип1 - наследник тип2, но как будто бы список из тип2 наследник списка из тип1 
- Зоопарк<Животное>
- Зоопарк<Рептилия>
```java
public static void copy(List<? super Number> dest, List<? extends Number> src) {
    for (int i = 0; i < src.size(); i++) {
        dest.set(i, src.get(i));
    }
}
```
- src - должен содержать числа, поэтому extends Number - `ковариантность`
- dest - принимает числа ( но может быть и более общего типа) - поэтому `контрвариантность`
Если захотим написать вместо super - extends `void copy(List<? extends Number> dest, List<? extends Number> src)`,
то столкнемся с ошибкой. Ведь может быть такое что, в лист Integer присваивается значения листа, содержащие Double.
___
Если переводить на язык метафор, то, допустим, у нас есть мешок
яблок. В нем могут лежать Антоновка, зеленые, красные и тд. Если мы
будем перекладывать в дргуой мешок который тоже extends Яблоки
то в нем могут лежать только Антоновки или только красные. Поэтому
нужно второй мешок сделать мешком с большей вместительной характеристикой
например мешок с фруктами. В таком случае гарантированно любой мешок с яблоками мы
переложим в другой мешок.
___

`Ковариантность` (extends)- перенос наследования исходных типов на производные от них типы в прямом порядке. (мы можем добавить какие-то методы, которых нет = наследование).
```java
List<Integer> ints = new ArrayList<>();
List<? extends Number> nums = list;
```
`List<Integer> подтип List<? extends Number>`  
`Контравариантность` (super)- перенос наследования исходных типов на производные от них типы в обратном порядке.
```java
List<Number> nums = new ArrayList<>();
List<? super Integer> ints = new ArrayList<>();
ints = nums; // можно, он не наоборот
```
`List<Number> подтип List<? super Integer>`

## Функциональное программирование
В лямбда-исчислении 2 операции:
- Аппликация - применение ф-ии к аргументу
- Абстракция - построение новых ф-ий
### Аппликация
`ƒa` - ф-ия ƒ применяется к значению а (алгоритм ƒ, вычисляющий
результат по значению а).
### Абстракция
`λx.t[x]` - новая ф-ия с параметром `x` и телом `t[x]`.
___
Выражжение λx.λy.t v w воспринимается как (λx.λy.t v) w
### Каррирование
Суть: т.к ф-ии могут возвращать другие ф-ии как результат,
можно применить многоместную ф-ию к одному аргументу, считая,
что в итоге получается новая ф-ия, применяемая к слд-му аргументу
и так далее.

`(λx.λy.t v) w` => `λy.[x -> v]t`   
Программа - набор шагов, явно описывающих то, что нужно сделать
 и, главное, - как(императивность )  
Лямбда-выражение - программная конструкция для объявления 
функциональных объектов (ф-ий как объектов)  
```text
sumlist(list a b c) = sum a (sumlist (list a b))
sumlist(list a b) = sum a b
```
Пример кода LISP  
```text
(* (+ 2 2) (- 11 1))
(list 1 2 3)
```
## Collections
**Java Collections** - набор интерфейсов и классов-коллекций,
а так же ряда других полезных служебных классов
### Set(extends Collection)
 - Интерфейс
 - Требуется, чтобы те кто реализуют Set, добивались уникальности эл-ов
#### SortedSet
 - comparator()
 - first(), last()
 - subset(a,b), headSet(a) - smaller than a, tailSet(a) - bigger than a
 - NavigableSet(extends SortedSet)
### List(extends Collection)
 - set(i,x),get(i),add(i,x),indexOf(x),lastIndexOf(x),sublist(from,to)...
### Queue (extends Collection)
 Очередь
#### Deque(extends Queue)
 - Очередь с двумя концами
### Map<K,V>
 
### Abstract Collection
 - Чтобы не  все нужно было реализовывать при написании собственных коллекций 
 - Расписаны методы которые используют другие методы, суть которых зависит от конкретной реализации класса

Пример:
```java
abstract class MyCollection<T> implements Collection<T> {
    public abstract boolean add(T x);
    
    public boolean addAll(Collection<? extends T> c) {
        boolean result = false;
        for (T x : c) {
            result = this.add(x) || result;
            // если написать наоборот то поскольку это ленивый оператор после 
            // первого добавленного элемента result станет true и добавления не будет
        }
        return result;
    }
} 
```
### ArrayList (class)
 - Size
 - Capacity
 - loadFactor
### LinkedList (class)
  - Реализует List
  - Реализует Queue, Deque

## Iterator
Цикл for each - синтаксический сахар для использования итератора.
Цикл for each внтури своей реализации использует методы итератора.  
Коллекции имплементят Iterable<T>.
```text
ArrayList<Student> students = new ArrayList<>();
Iterator<Student> i = students.iterator();
while (i.hasNext()) {
    System.out.println(i.next());
}
```
Любой вызов next() - переход к след элементу.  
## Comparable<T>
Интерфейс из java.util, который позволяет сравнивать объекты класса которые реализуют
его с другими объектами.
```java
class A implements Comparable<T> {
    @Override
    public int compareTo(T anotherObject) {
        return 0;
    }
}
```
## Comparator<T>
Сторонний объект умеющий сравнивать два объекта. Аналог: весы.
```java
class StudentComparator implements Comparator<Student> {
    public int compareTo(Student s1, Student s2) {
        return (s1.getAge() - s2.getAge());
    }
}

// Collections.sort(students, new StudentComparator());
```
Очередь с приоритетами
```text
PriorityQueue<Student> strings = new PriorityQueue<>(new Comparator<String>() {
        @Override
        public int compare(Student s1, student s2) {
            return s1.getAge() - s2.getAge();
        }
    });
```
## Stream API
### Конвейерные операции
 - map, filter
 - distinct
 - sorted
 - mapToInt,mapToDouble...  
### Терминальные
 - findFirst
 - collect
 - forEach
 - reduce  
Терминальные возвращают Optional<T>
## Unit-тестирование(модульное)
 Процесс проверки работоспособности отдельных частей исходного кода
 (чаще всего методов) программы путем запуска тестов в исскуственной
 среде. **Осуществляется разработчиком!**
 ### Test case
Артефакт, описывающий список конкретных шагов, условий и параметров, необходимых
для проверки реализации тестируемой функции или ее части.  
Под кейсом понимается структура вида: Action > Expected Result > Test Result  
Модульное тестирвание нужно для:
 - Ошибки выявляются в процессе проектирование метода или класса(TDD)
 - Разработчик создает методы и классы для конкретных целей
 - Снижается количесво новых ошибок при добавлении новой функц-ти
 - Тест отражает элементы технического задания (некорректное завершение
теста сообщает о нарушении технических требований заказчика)
### Как называть тесты
 - суффикс Test к названии класса(если тесты сгруппированы в классе)
 - суффикс test к ф-ям/методам, если того требует библиотека
 - тестовые методы желательно должны содержать should (sumShouldBePositive)
### Что такое assert
 Проверка ожидание/реальность
 - assertTrue
 - assertFalse
 - assertEquals
 - assertArrayEquals
 - assertNotEquals
 - assertSame
 - assertNotSame
 - fail - гарантированное падение теста
### Покрытие
 - процент кода(строк, методов, классов), покрытого тестами.
 - а так же сами эти тесты
### TDD
 1. Пишем простейший тест, ломающий программу
 2. Пишем простейшую реализацию, достаточную для прохождения теста
 3. Улучшаем написанный код, не ломая тесты. Возвращаемся к пункту 1.
### Expected, Timeout
 ```java
class Test {
    @Test(expected = ArithmeticException.class)
    public void checkZeroDivide() {
        // проверяем исключение делением на ноль
        Math.divide(1, 0);
    }

    // останавливаем когда проходит 100мс
    @Test(timeout = 100)
    public void waitMe() {
        while (true) ;
    }
    
    // если исключений не одно
    @Test
    public void checkZero() {
        // проверяем искл при делении на ноль
        try {
            Math.divide(1,0);
            Assert.fail();
        } catch (ArithmeticException e) {}
        catch (NullPointerException e) {}
        catch (Exception e) {
            throw new AssertionError();
        }
        
    }
}
```
### Ignore, Assume
 - Тест, помеченный @Ignore не выполняется 
 - Вместо Assert можно использовать Assume(assumeEquals, ...)
   + Если проверка верна - тест пройден
   + Если не верна - тест игнорируется
 
### Fixture
Окружение, необходимое для корректной работы теста.(Объекты, БД, файлы)
 * setUpClass / setUpBeforeClass / @beforeClass - запускается только один раз при запуске теста(static).
 * setUp / setUpClass / @Before - запускается перед каждым тестовым методом
 * tearDown / tearDownClass / @After - запускается после каждого метода.
 * tearDownAfterClass / tearDownClass / @AfterClass - запускается после того, как отработали все тестовые методы
### Жизненный цикл тестирующего класса
 * setUp / beforeClass
 * для каждого @Test-метода:
   + создание экземпляра тестового класса
   + выполняется  setUp / Before
   + выполняется test
   + выполняется tearDown / After
 * Выполняется TearDownAfterClass
```java
// JUnit 3
public class TestPlayer extends TestCase {
    Player p1;
    
    public static void setUpBeforeClass() {
        p1 = new Player("Kalim");
    }
    
    public void testHpShouldBe100() {
        Assert.assertEquals(100, p1.getHP());
    }
    
}
```
### Mock
 - Создание: Класс объект = mock(Класс.class)
 - Задание поведения: when(объект.метод(параметры)).thenResult(значение)

```java
import java.io.InputStream;

import static org.mockito.Mocktio.mock;
import static org.mockito.Mocktio.when;

class MockClass {
    public static void main(String[] args) {
        InputStream is = mock(InputStream.class);
        when(is.read()).thenResult(5);
        
        // имитация того как будто мы считываем файл, в котором одни пятерки
        // всегда будет возвращаться 5
        System.out.println(is.read()); // 5
        System.out.println(is.read()); // 5
        // что-то не определено -> возвращать 0 или null
        System.out.println(is.available()); // 0
    }
}
```

```java
import java.io.InputStream;

public class Add {
    public static int add(InputStream is, int n) {
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += is.read();
        }
        return s;
    }
}

class MockTest {
    @Test
    public void testAdd() {
        // Чтобы протестировать функцию сложения всех чисел из файла, нужно было
        // этот файл создать и заполнить. Но это возня.
        InputStream is = mock(InputStream.class);
        when(is.read()).thenReturn(5).thenReturn(6);
        // создаем типо файл в котором при первом счтывании возвр 5 а далее 6
        // соответсвенно если счситать три раза то будет 5-6-6
        Assert.assertEquals(Add.add(is, 3), 17);
    }
}
```

### Проверка вызова

```java
import java.util.Iterator;

class MockTest {
    public static void main(String[] args) {
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("ITIS").thenReturn("KFU");
        i.next();
        // Проверка, что был вызван один раз - тест пройден
        verify(i).next();
        // Проверка, что два раза - тест завалился
        virify(i, times(2)).next();
    }
}
```
Лог ошибки:
```text
Wanted 2 times
But was 1 time
```
## IO
**Файл** - всегда набор битов (байтов, килобайтов, мегабайтов). У бита 2 возможных значенния,
поэтому он называется бинарным.  
**Текстовый файл** - бинарный файл, который при считывании байтов возвращает символы.
Может быть прочитан человеком.
### Почему не все файлы текстовые
Для экономии памяти. 65 и 66 - байты. Получается 2 байта. Но если записать в текстовом
виде `65 66` - уже 5 байтов.  
Еще можно считывать с:
 - консоль
 - сеть
 - устройство
 - структура данных

Современный подход к вводу/выводу: есть **объекты** которые работая с разными источниками
данных(файл, сеть, структура) имеют общий интерфейс(источник данных может поменяться
но это никак не повлияет на код). Эти объекты называются Streams(input/output).
### Streams(input/output)
Как работать:
 - Открыть поток
 - Пока есть информация, читать с него или записать
 - Закрыть поток
 
### java.io
 - InputStream - входной поток
    + read()
 - OutputSteam - выходной поток
    + write()
    
### Тонкости read()
 + Метод read() считывает один байт и превращает его в int
    * Делается это потому что, байты джавовские да и не только первым битом 
   хранят фактор что это отрицательное или положительное число. 
   И поэтому при считывании нам было бы необходимо конвертировать отрицательный байт
   в положительный инт, который потом уже сконвертирвать, например в символ. И чтобы этим не заниматься возвр инт.
 
```java
class IOLek {
    public static void main(String[] args) {
        echo(System.in);
    }

    public static void echo(InputStream in) {
        try {
            int x = in.read();
            while (x != -1) {
                System.out.print((char) x);
                x = in.read();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println();
    }
}
```
Чтение нескольких байтов сразу
```text
public int read(byte[] b)
public int read(byte[] b, int offset, int length)
b[offset] - начиная с какого элемента идет запись в массив
length - сколько байтов считать
```
### Откуда можно прочитать данные
Так и называется соответствующий InputStream
 - ByteArrayInputStream
 - FileInputStream
 - StringBufferInputStream
 - PipedInputStream(в связке с OutputInputStream)
    + Что записали в PipedOutput, то будет доступно в PipedInput
    + PipedOutputStream po = new PipedOutputSteam();
    + PipedInputStream pi = new PipedInputStream(po);
 
```java
import java.io.*;
import java.util.*;

class IOLek {
    public static void main(String[] args) {
        fisDemo();
    }
    
    public static void fisDemo() {
        try {
            FileInputStream fis = new FileInputStream("test.txt");
            int i;
            while ( (i = fis.read()) != -1) {
                System.out.println((char)i);
            }
            fis.close();
        } catch (IOException e) {
            System.out.println("Exception");
        }
    }
}
```