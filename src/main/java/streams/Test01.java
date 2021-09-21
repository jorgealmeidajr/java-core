package streams;

import java.util.*;
import java.util.stream.Collectors;

public class Test01 {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950));

        // Finds all transactions in 2011 and sort by value (small to high)
        List<Transaction> t1 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        // What are all the unique cities where the traders work?
        List<String> t2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        Set<String> s2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(Collectors.toSet());

        // Finds all traders from Cambridge and sort them by name
        List<Trader> t3 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> "Cambridge".equalsIgnoreCase(trader.getCity()))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        // Returns a string of all traders’ names sorted alphabetically
        String str4 = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .reduce("", (a, b) -> a + "," + b);
//        System.out.println(str4);

        str4 = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .distinct()
                .sorted()
                .collect(Collectors.joining());
//        System.out.println(str4);

        // Are any traders based in Milan?
        boolean b5 = transactions.stream()
                .map(Transaction::getTrader)
                .anyMatch(trader -> "Milan".equalsIgnoreCase(trader.getCity()));

        // Finds the transaction with the smallest value
        Optional<Transaction> min6 = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
    }

    private static class Trader {
        private final String name;
        private final String city;

        public Trader(String n, String c) {
            this.name = n;
            this.city = c;
        }

        public String getName() {
            return this.name;
        }

        public String getCity() {
            return this.city;
        }

        public String toString() {
            return "Trader:" + this.name + " in " + this.city;
        }
    }

    private static class Transaction {
        private final Trader trader;
        private final int year;
        private final int value;

        public Transaction(Trader trader, int year, int value) {
            this.trader = trader;
            this.year = year;
            this.value = value;
        }

        public Trader getTrader() {
            return this.trader;
        }

        public int getYear() {
            return this.year;
        }

        public int getValue() {
            return this.value;
        }

        public String toString() {
            return "{" + this.trader + ", " +
                    "year: " + this.year + ", " +
                    "value:" + this.value +
                    "}";
        }
    }

}
