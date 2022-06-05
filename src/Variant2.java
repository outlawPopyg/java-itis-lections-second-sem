//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileReader;
//import java.io.IOException;
//import java.lang.*;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class User {
//    private int ID;
//    private String name;
//    private int birthYear;
//    private String nativeCity;
//
//    public User(String line) {
//        String[] split= line.split(",");
//        this.ID = Integer.parseInt(split[0]);
//        this.name = split[1];
//        this.birthYear = Integer.parseInt(split[2]);
//        this.nativeCity = split[3];
//    }
//
//    public int getID() {
//        return ID;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getBirthYear() {
//        return birthYear;
//    }
//
//    public String getNativeCity() {
//        return nativeCity;
//    }
//
//    @Override
//    public String toString() {
//        return name;
//    }
//}
//
//class Good {
//    private int ID;
//    private String title;
//    private String manufacturer;
//    private String manufacturerCity;
//
//    public Good(String line) {
//        String[] split = line.split(",");
//        this.ID = Integer.parseInt(split[0]);
//        this.title = split[1];
//        this.manufacturer = split[2];
//        this.manufacturerCity = split[3];
//    }
//
//    public int getID() {
//        return ID;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getManufacturer() {
//        return manufacturer;
//    }
//
//    public String getMfCity() {
//        return manufacturerCity;
//    }
//
//    @Override
//    public String toString() {
//        return title;
//    }
//}
//
//class Favorite {
//    private int userID;
//    private int productID;
//
//    public Favorite(String line) {
//        String[] split = line.split(",");
//        this.userID = Integer.parseInt(split[0]);
//        this.productID = Integer.parseInt(split[1]);
//    }
//
//    public int getUserID() {
//        return userID;
//    }
//
//    public int getProductID() {
//        return productID;
//    }
//
//    @Override
//    public String toString() {
//        return "Favorite{" +
//                "userID=" + userID +
//                ", productID=" + productID +
//                '}';
//    }
//}
//
//
//class TestClassForTaskE {
//    static private LinkedList<User> list;
//    static private LinkedList<Favorite> favorites;
//    static private LinkedList<Good> goods;
//    @BeforeAll
//    static void beforeAll() {
//        User user1 = new User("0,Kalim,2003,Kazan");
//        User user2 = new User("1,Anton,2005,Moscow");
//        User user3 = new User("2,Ivan,2001,Ulyanovsk");
//        User user4 = new User("3,John,1992,Dallas");
//
//        Good good1 = new Good("0,Bread,BreadFactory,Kazan");
//        Good good2 = new Good("1,Bread,MilkFactory,Kazan");
//        Good good3 = new Good("2,Laptop,Lenovo,California");
//        Good good4 = new Good("3,Macbook,Apple,California");
//
//        Favorite favorite1 = new Favorite("0,1");
//        Favorite favorite2 = new Favorite("1,1");
//        Favorite favorite3 = new Favorite("2,3");
//        Favorite favorite4 = new Favorite("0,2");
//
//        list = new LinkedList<>(List.of(user1,user2,user3,user4));
//        favorites = new LinkedList<>(List.of(favorite1, favorite2, favorite3, favorite4));
//        goods = new LinkedList<>(List.of(good1, good2, good3, good4));
//    }
//
//    @Test
//    void comparatorTest() {
//        list.sort(new Variant2.ComparatorForTaskD(favorites));
//        assertEquals("Kalim", list.get(3).getName());
//    }
//
//    @Test
//    void taskCTest() {
//        assertTrue(Variant2.taskC(list, goods, favorites));
//    }
//
//}
//
//public class Variant2 {
//
//    static class ComparatorForTaskD implements Comparator<User> {
//        private LinkedList<Favorite> fvs;
//
//        public ComparatorForTaskD(LinkedList<Favorite> fvs) {
//            this.fvs = fvs;
//        }
//
//        @Override
//        public int compare(User user1, User user2) {
//            long r = Variant2.favoriteGoodsCount(user1, fvs) - Variant2.favoriteGoodsCount(user2, fvs);
//            if (r == 0) {
//                return 0;
//            }
//            if (r > 0) {
//                return 1;
//            }
//            return -1;
//        }
//    }
//
//    public static long favoriteGoodsCount(User user, LinkedList<Favorite> fvs) {
//        return fvs.stream().filter(f -> f.getUserID() == user.getID()).count();
//    }
//
//    public static void taskA(LinkedList<User> users, LinkedList<Favorite> fvs, String city) {
//        System.out.println(
//                users.stream().filter(x -> Objects.equals(x.getNativeCity(), city))
//                        .collect(Collectors.groupingBy(x -> {
//                            return fvs.stream()
//                                    .filter(f -> f.getUserID() == x.getID())
//                                    .count();
//                        }))
//        );
//    }
//
//    public static void taskA1(LinkedList<User> users, LinkedList<Favorite> fvs, String city) {
//        System.out.println(
//                fvs.stream().filter(x -> {
//                    return users.stream()
//                            .filter(u -> Objects.equals(u.getNativeCity(), city))
//                            .anyMatch(u -> u.getID() == x.getUserID());
//                }).collect(Collectors.groupingBy(x -> {
//                    return users.stream().filter(u -> u.getID() == x.getUserID()).collect(Collectors.toList());
//                }, Collectors.counting()))
//        );
//    }
//
//    public static void taskB(LinkedList<User> users, LinkedList<Good> goods, LinkedList<Favorite> fvs) {
//        for (User user : users) {
//            boolean flag = false;
//            for (Favorite fv : fvs) {
//                for (Good good : goods) {
//                    if (user.getID() == fv.getUserID()) {
//                        if (fv.getProductID() == good.getID()) {
//                            flag = Objects.equals(user.getNativeCity(), good.getMfCity()) || flag;
//                        }
//                    }
//                }
//            }
//            if (!flag) System.out.println(user);
//        }
//    }
//
//    public static boolean taskC(LinkedList<User> users, LinkedList<Good> goods, LinkedList<Favorite> fvs) {
//        return users.stream().filter(u -> u.getBirthYear() > 2003)
//                .anyMatch(u -> {
//                    return fvs.stream().filter(x -> x.getUserID() == u.getID())
//                            .map(x -> getManufacturerByProductId(goods, x.getProductID()))
//                            .collect(Collectors.toSet())
//                            .size() == 1;
//                });
//    }
//
//    public static String getManufacturerByProductId(LinkedList<Good> goods, int id) {
//        for (Good good : goods) {
//            if (good.getID() == id) {
//                return good.getManufacturer();
//            }
//        }
//        return "";
//    }
//
//
//    public static void main(String[] args) throws IOException {
//        LinkedList<User> users = new LinkedList<>();
//        LinkedList<Good> goods = new LinkedList<>();
//        LinkedList<Favorite> favorites = new LinkedList<>();
//
//        Scanner usersScanner = new Scanner(new FileInputStream("users.txt"));
//        Scanner goodsScanner = new Scanner(new FileInputStream("goods.txt"));
//        Scanner favoritesScanner = new Scanner(new FileInputStream("favorites.txt"));
//
//        while (usersScanner.hasNext()) {
//            users.add(new User(usersScanner.next()));
//        }
//
//        while (goodsScanner.hasNext()) {
//            goods.add(new Good(goodsScanner.next()));
//        }
//
//        while (favoritesScanner.hasNext()) {
//            favorites.add(new Favorite(favoritesScanner.next()));
//        }
//
////        taskA1(users, favorites, "Kazan");
////        taskB(users, goods, favorites);
////        taskC(users, goods, favorites);
//        users.sort(new ComparatorForTaskD(favorites));
//        System.out.println(users);
//    }
//}