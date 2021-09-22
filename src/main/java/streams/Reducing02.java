package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

public class Reducing02 {

    public static void main(String[] args) {
        List<Dish> menu = Arrays.asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));

        long howManyDishes1 = menu.stream().collect(counting());
        long howManyDishes2 = menu.stream().count();

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        Optional<Dish> mostCalorieDish = menu.stream().collect(maxBy(dishCaloriesComparator));

        int totalCalories1 = menu.stream().collect(summingInt(Dish::getCalories));

        double averageCalories = menu.stream().collect(averagingInt(Dish::getCalories));

        String shortMenu1 = menu.stream().map(Dish::getName).collect(joining());
        String shortMenu2 = menu.stream().map(Dish::getName).collect(joining(", "));

        int totalCalories2 = menu.stream().collect(
                reducing(0, Dish::getCalories, (i, j) -> i + j));
        int totalCalories3 = menu.stream().collect(
                reducing(0, Dish::getCalories, Integer::sum));
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
