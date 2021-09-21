package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Mapping01 {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));

        menu.stream()
                .map(Dish::getName)
                .collect(Collectors.toList())
//                .forEach(System.out::println)
        ;

        List<String> words = Arrays.asList("Modern", "Java", "In", "Action");
        words.stream()
                .map(String::length)
                .collect(Collectors.toList())
//                .forEach(System.out::println)
        ;

        menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList())
//                .forEach(System.out::println)
        ;

        words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList())
//                .forEach(System.out::println)
        ;
    }

    private static class Dish {
        String name;
        boolean vegetarian;
        int calories;
        Type type;

        public Dish(String name, boolean vegetarian, int calories, Type type) {
            this.name = name;
            this.vegetarian = vegetarian;
            this.calories = calories;
            this.type = type;
        }

        enum Type {
            FISH, MEAT, OTHER
        }

        public String getName() {
            return name;
        }

        public boolean isVegetarian() {
            return vegetarian;
        }

        public int getCalories() {
            return calories;
        }

        public Type getType() {
            return type;
        }
    }

}
