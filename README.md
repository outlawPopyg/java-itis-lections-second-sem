# Лекции по Java 2 семестр
## Пакеты
### import  
import - не рекурсивная операция  
import static - импорт статических методов. Например import static java.lang.Math.cos  
---
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
Конвейерные операции
 - map, filter
 - distinct
 - sorted
 - mapToInt,mapToDouble...
Терминальные
 - findFirst
 - collect
 - forEach
 - reduce
Терминальные возвращают Optional<T>