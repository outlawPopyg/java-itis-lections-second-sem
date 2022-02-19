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
