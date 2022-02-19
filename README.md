# Лекции по Java 2 семестр

## Пакеты
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

