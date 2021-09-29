package collections;

import java.util.*;

import static java.util.Map.entry;

public class Improvements01 {

    public static void main(String[] args) {
        List<String> friends1 = Arrays.asList("Raphael", "Olivia", "Thibaut");
        Set<String> friends2 = new HashSet<>(Arrays.asList("Raphael", "Olivia", "Thibaut"));
        List<String> friends3 = List.of("Raphael", "Olivia", "Thibaut");
        Set<String> friends4 = Set.of("Raphael", "Olivia", "Thibaut");

        Map<String, Integer> ageOfFriends = Map.ofEntries(
                entry("Raphael", 30),
                entry("Olivia", 25),
                entry("Thibaut", 26));

        List<String> friends = new ArrayList<>();
        friends.add("Raphael");
        friends.add("Olivia");
        friends.add("Thibaut");
        friends.removeIf(friend -> "Olivia".equalsIgnoreCase(friend));

        ageOfFriends.forEach((friend, age) ->
                System.out.println(friend + " is " + age + " years old"));

        System.out.println();

        Map<String, String> favouriteMovies = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix"),
                entry("Olivia", "James Bond"));
        favouriteMovies.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(System.out::println);
    }

}
