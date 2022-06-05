import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

class User {
    private int id;
    private String name;
    private String email;
    private String city;
    private int birthYear;

    public User(String line) {
        String[] split = line.split(",");
        this.id = Integer.parseInt(split[0]);
        this.name = split[1];
        this.email = split[2];
        this.city = split[3];
        this.birthYear = Integer.parseInt(split[4]);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public int getBirthYear() {
        return birthYear;
    }

    @Override
    public String toString() {
        return name;
    }
}

class Message {
    private int id;
    private String text;
    private int authorId;

    public Message(String line) {
        String[] split = line.split(",");
        this.id = Integer.parseInt(split[0]);
        this.text = split[1];
        this.authorId = Integer.parseInt(split[2]);
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getAuthorId() {
        return authorId;
    }

    @Override
    public String toString() {
        return text;
    }
}

class Like {
    private int userId;
    private int messageId;

    public int getUserId() {
        return userId;
    }

    public int getMessageId() {
        return messageId;
    }

    public Like(String line) {
        String[] split = line.split(",");
        this.userId = Integer.parseInt(split[0]);
        this.messageId = Integer.parseInt(split[1]);
    }
}


public class Variant1 {

    public static int authorsLikesSum(int authorId, LinkedList<Like> likes, LinkedList<Message> messages) {
        return messages.stream().filter(m -> m.getAuthorId() == authorId).map(m -> {
            return likes.stream().filter(l -> l.getMessageId() == m.getId()).count();
        }).mapToInt(Math::toIntExact).sum();

    }

    public static void taskA(LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
        users.forEach(user -> {
            System.out.println(user.getName() + ": " + authorsLikesSum(user.getId(), likes, messages));
        });
    }

    public static User getUserById(int id, LinkedList<User> users) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public static void taskB(LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
        // TODO вывод пользователей которые ставили лайки только пользователям из своего города
        for (User user : users) {
            boolean flag = false;
            String city = user.getCity();
            for (Like like : likes) {
                for (Message message : messages) {
                    if (user.getId() == like.getUserId()) {
                        if (like.getMessageId() == message.getId()) {
                            flag = !Objects.equals(getUserById(message.getAuthorId(), users).getCity(), city) || flag;
                        }
                    }
                }
            }
            if (!flag) {
                System.out.println(user);
            }
        }
    }

    // todo вывести пользователей кому заданный пользователь ставил лайки
    public static HashSet<User> userLikes(int userId, LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
        User user = getUserById(userId, users);
        HashSet<User> set = new HashSet<>();
        for (Like like : likes) {
            for (Message message : messages) {
                if (like.getUserId() == user.getId() && like.getMessageId() == message.getId()) {
                    set.add(getUserById(message.getAuthorId(), users));
                }
            }
        }
        return set;
    }

    // todo вывести пользователей которые ставили лайки только своим (из своего города)
    public static void taskB1(LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
        for (User user : users) {
            HashSet<User> set = userLikes(user.getId(), users, messages, likes);
            boolean flag = set.size() != 0;
            for (User x : set) {
                if (!Objects.equals(x.getCity(), user.getCity())) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                System.out.println(user);
            }
        }
    }


    // todo проверить что существуют пользователи, которые поставили все свои лайки пользователям, которые младше них
    public static boolean taskC(LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
        for (User user : users) {
            HashSet<User> set = userLikes(user.getId(), users,messages,likes);
            boolean flag = set.size() != 0;
            for (User x : set) {
                if (user.getBirthYear() > x.getBirthYear()) {
                    flag = false;
                    break;
                }
            }
            if (flag) return true;
        }
        return false;
    }

    public static LinkedList<User> util(int userId, LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
        User user = getUserById(userId, users);
        LinkedList<User> list = new LinkedList<>();
        for (Like like : likes) {
            for (Message message : messages) {
                if (like.getUserId() == user.getId() && like.getMessageId() == message.getId()) {
                    list.add(getUserById(message.getAuthorId(), users));
                }
            }
        }
        return list;
    }


    static class ComparatorForTaskD implements Comparator<User> {
        LinkedList<User> users;
        LinkedList<Message> messages;
        LinkedList<Like> likes;

        public ComparatorForTaskD(LinkedList<User> users, LinkedList<Message> messages, LinkedList<Like> likes) {
            this.users = users;
            this.likes = likes;
            this.messages = messages;
        }

        @Override
        public int compare(User user1, User user2) {
            int user1TotalLikes = util(user1.getId(), users,messages,likes).size();
            int user2TotalLikes = util(user2.getId(), users,messages,likes).size();
            return user1TotalLikes - user2TotalLikes;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

        LinkedList<User> users = new LinkedList<>();
        LinkedList<Message> messages = new LinkedList<>();
        LinkedList<Like> likes = new LinkedList<>();

        Scanner usersScanner = new Scanner(new FileInputStream("v2/users.txt"));
        Scanner messagesScanner = new Scanner(new FileInputStream("v2/messages.txt"));
        Scanner likesScanner = new Scanner(new FileInputStream("v2/likes.txt"));
        usersScanner.useDelimiter("\t");
        messagesScanner.useDelimiter("\t");
        likesScanner.useDelimiter("\t");

        while(usersScanner.hasNext()) {
            users.add(new User(usersScanner.next()));
        }

        while(messagesScanner.hasNext()) {
            messages.add(new Message(messagesScanner.next()));
        }

        while(likesScanner.hasNext()) {
            likes.add(new Like(likesScanner.next()));
        }

//        authorsLikesSum(0,likes,messages);
//        taskA(users,messages,likes);
//        taskB(users,messages,likes);

//        taskB1(users, messages, likes);
//        System.out.println(taskC(users,messages,likes));
        users.sort(new ComparatorForTaskD(users,messages,likes));
        System.out.println(users);
    }
}