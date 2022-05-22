import java.io.*;
import java.util.*;

class BonusCard {
    private int bonuses;
    public int getBonuses() { return bonuses; }

    public BonusCard(int bonuses) {
        this.bonuses = bonuses;
    }

    public boolean use(int n) {
        if (bonuses >= n) {
            bonuses -= n;
            System.out.println(bonuses + " left");
            return true;
        } else {
            System.out.println("OOOOPS");
            return false;
        }
    }
}

class Human extends Thread {
    private final BonusCard bonusCard;
    private String who;

    public Human(BonusCard bonusCard, String who) {
        this.bonusCard = bonusCard;
        this.who = who;
    }

    public void shoppingWithBonuses(int bonuses) {
        synchronized (bonusCard) {
            if (bonusCard.getBonuses() >= bonuses) {
                System.out.println(who + " is gonna buy something");
                if (bonusCard.use(bonuses))
                    System.out.println(who + " bought something");
            } else {
                System.out.println("Sorry, no money");
            }
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            shoppingWithBonuses(7);
        }
    }
}

public class Lek {
    public static void main(String[] args) {
        BonusCard bc = new BonusCard(150);
        Human husband = new Human(bc, "husband");
        Human wife = new Human(bc, "wife");
        husband.start();
        wife.start();
    }
}





