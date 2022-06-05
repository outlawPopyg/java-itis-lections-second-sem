import java.util.*;
import java.lang.*;
import java.io.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class Director {
    int id;
    String name;

    public Director(String line) {
        String[] split = line.split(",");
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Actor {
    int id;
    String name;
    int year;

    public Actor(String line) {
        String[] split = line.split(",");
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
        this.year = Integer.parseInt(split[2]);
    }

    @Override
    public String toString() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}

class Film {
    int id;
    String title;
    int year;
    int directorId;

    public Film(String line) {
        String[] split = line.split(",");
        this.id = Integer.parseInt(split[0]);
        this.title = split[1];
        this.year = Integer.parseInt(split[2]);
        this.directorId = Integer.parseInt(split[3]);
    }

    @Override
    public String toString() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getDirectorId() {
        return directorId;
    }
}

class Part {
    int filmId;
    int actorId;

    public Part(String line) {
        String[] split = line.split(",");
        this.filmId = Integer.parseInt(split[0]);
        this.actorId = Integer.parseInt(split[1]);
    }

    public int getFilmId() {
        return filmId;
    }

    public int getActorId() {
        return actorId;
    }
}


public class Variant3 {
    // todo getFilmById
    public static Film getFilmById(int filmId, LinkedList<Film> films) {
        return films.stream().filter(film -> film.getId() == filmId).findFirst().orElse(null);
    }

    // todo фильмы снятые конкертным режисером
    public static HashSet<Film> directorsFilms(int directorId, LinkedList<Director> directors, LinkedList<Film> films) {
        HashSet<Film> set = new HashSet<>();
        directors.forEach(director -> {
            if (director.getId() == directorId) {
                films.forEach(film -> {
                    if (film.getDirectorId() == directorId) {
                        set.add(film);
                    }
                });
            }
        });
        return set;
    }

    // todo фильмы в которых принимали участие конкретные актеры
    public static HashSet<Film> actorsFilms(int actorId, LinkedList<Actor> actors, LinkedList<Film> films, LinkedList<Part> parts) {
        HashSet<Film> set = new HashSet<>();
        actors.forEach(actor -> {
            if (actor.getId() == actorId) {
                parts.forEach(part -> {
                    if (part.getActorId() == actorId) {
                        set.add(getFilmById(part.getFilmId(), films));
                    }
                });
            }
        });
        return set;
    }

    // todo taskA
    public static void taskA(LinkedList<Actor> actors, LinkedList<Film> films, LinkedList<Director> directors, LinkedList<Part> parts) {
        directors.stream().forEach(director -> {
            actors.stream().forEach(actor -> {
                HashSet<Film> a = actorsFilms(actor.getId(),actors,films,parts);
                HashSet<Film> b = directorsFilms(director.getId(),directors,films);
                if (a.retainAll(b)) {
                    System.out.println(director + "-" + actor + " " + a);
                }
            });
        });
    }

    // todo посчитать кол-во актеров в фильме
    public static int actorsCount(int filmId, LinkedList<Actor> actors, LinkedList<Part> parts) {
        int c = 0;
        for (Actor actor : actors) {
            for (Part part : parts) {
                if (part.getActorId() == actor.getId() && filmId == part.getFilmId()) {
                    c++;
                }
            }
        }
        return c;
    }

    // todo taskB
    public static LinkedList<Director> taskB(LinkedList<Actor> actors, LinkedList<Director> directors, LinkedList<Part> parts, LinkedList<Film> films) {
        LinkedList<Director> res = new LinkedList<>();
        for (Director director : directors) {
            HashSet<Film> set = directorsFilms(director.getId(),directors,films);
            boolean flag = set.size() != 0;
            for (Film f : set) {
                if (!(actorsCount(f.getId(),actors,parts) > 2 && f.getYear() > 2000)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                res.add(director);
            }
        }
        return res;
    }

    // todo taskC
    public static boolean taskC(LinkedList<Actor> actors, LinkedList<Director> directors, LinkedList<Part> parts, LinkedList<Film> films) {
        for (Actor actor : actors) {
            HashSet<Film> set = actorsFilms(actor.getId(),actors,films,parts);
            boolean cond1 = set.stream().allMatch(film -> film.getYear() < 1990);
            boolean cond2 = set.stream().map(film -> film.getDirectorId()).count() >= 3;
            if (cond1 && cond2) {
                return true;
            }
        }
        return false;
    }

    static class ComparatorForTaskG implements Comparator<Director> {
        LinkedList<Director> directors;
        LinkedList<Film> films;
        public ComparatorForTaskG(LinkedList<Director> directors, LinkedList<Film> films) {
            this.directors = directors;
            this.films = films;
        }

        @Override
        public int compare(Director director1, Director director2) {
            long d1TotalCount = directorsFilms(director1.getId(),directors,films)
                    .stream().mapToInt(Film::getYear).distinct().count();
            long d2TotalCount = directorsFilms(director2.getId(),directors,films)
                    .stream().mapToInt(Film::getYear).distinct().count();
            if (d1TotalCount - d2TotalCount == 0) return 0;
            else if (d1TotalCount - d2TotalCount > 0) return 1;
            else return -1;
        }
    }

    static class TestClass {
        static LinkedList<Director> directors;
        static LinkedList<Actor> actors;
        static LinkedList<Film> films;
        static LinkedList<Part> parts;
        @BeforeAll
        static void beforeAll() {
            directors = new LinkedList<>();
            directors.add(new Director("0,D1"));
            directors.add(new Director("1,D2"));
            directors.add(new Director("2,D3"));
            directors.add(new Director("3,D4"));

            actors = new LinkedList<>();
            actors.add(new Actor("0,A1,0"));
            actors.add(new Actor("1,A2,0"));
            actors.add(new Actor("2,A3,0"));
            actors.add(new Actor("3,A4,0"));

            films = new LinkedList<>();
            films.add(new Film("0,F1,1980,0"));
            films.add(new Film("1,F2,1980,0"));
            films.add(new Film("2,F3,1980,0"));
            films.add(new Film("3,F4,1981,1"));
            films.add(new Film("4,F5,1982,1"));
            films.add(new Film("5,F6,1985,2"));
        }

        @Test
        void comparatorTest() {
            directors.sort(new ComparatorForTaskG(directors,films));
            assertEquals(1, directors.get(directors.size()-1).getId());
        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner actorsSc = new Scanner(new FileInputStream("v3/actors.txt"));
        Scanner directorsSc = new Scanner(new FileInputStream("v3/directors.txt"));
        Scanner filmsSc = new Scanner(new FileInputStream("v3/films.txt"));
        Scanner partSc = new Scanner(new FileInputStream("v3/part.txt"));

        LinkedList<Actor> actors = new LinkedList<>();
        LinkedList<Director> directors = new LinkedList<>();
        LinkedList<Film> films = new LinkedList<>();
        LinkedList<Part> parts = new LinkedList<>();

        actorsSc.useDelimiter("\t");
        directorsSc.useDelimiter("\t");
        filmsSc.useDelimiter("\t");
        partSc.useDelimiter("\t");

        while (actorsSc.hasNext()) {
            actors.add(new Actor(actorsSc.next()));
        }

        while (filmsSc.hasNext()) {
            films.add(new Film(filmsSc.next()));
        }

        while (directorsSc.hasNext()) {
            directors.add(new Director(directorsSc.next()));
        }

        while (partSc.hasNext()) {
            parts.add(new Part(partSc.next()));
        }

//        directorsFilms(0, directors, films);
//        actorsFilms(0, actors,films,parts);
//        taskA(actors,films,directors,parts);
//        System.out.println(actorsCount(0,actors,parts));
//        System.out.println(taskB(actors,directors,parts,films));
//        System.out.println(taskC(actors,directors,parts,films));
        directors.sort(new ComparatorForTaskG(directors,films));
        System.out.println(directors);
    }
}
