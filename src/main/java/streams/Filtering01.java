package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering01 {

    public static void main(String[] args) {
        List<Dish> specialMenu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));

        // filter
        List<Dish> vegetarianDishes = specialMenu.stream()
                .filter(dish -> dish.isVegetarian())
                .collect(Collectors.toList());

        // filter with method reference
        vegetarianDishes = specialMenu.stream()
                .filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        // unique elements
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        List<Integer> evenNumbers = numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .collect(Collectors.toList());

        // if you have a collection sorted, you dont need to traverse all elements
        specialMenu.sort(Comparator.comparing(Dish::getCalories));
        List<Dish> filteredMenu = specialMenu.stream()
                .filter(dish -> {
//                    System.out.println(dish.getCalories());
                    return dish.getCalories() < 320;
                })
                .collect(Collectors.toList());

        filteredMenu = specialMenu.stream()
                .takeWhile(dish -> {
//                    System.out.println(dish.getCalories());
                    return dish.getCalories() < 320;
                })
                .collect(Collectors.toList());

        filteredMenu = specialMenu.stream()
                .dropWhile(dish -> {
//                    System.out.println(dish.getCalories());
                    return dish.getCalories() < 320;
                })
                .collect(Collectors.toList());

        // truncate
        List<Dish> dishes = specialMenu
                .stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(Collectors.toList());

        // skip
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
