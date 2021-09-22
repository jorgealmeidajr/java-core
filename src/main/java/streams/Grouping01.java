package streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

public class Grouping01 {

    public static void main(String[] args) {
        List<Dish> menu = asList(
                new Dish("seasonal fruit", true, 120, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER));

        Map<Dish.Type, List<Dish>> group1 = menu.stream().collect(groupingBy(Dish::getType));

        Function<Dish, CaloricLevel> dishCaloricLevelClassifier = dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        };
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menu.stream().collect(
                groupingBy(dishCaloricLevelClassifier));

        // group by type, show types with empty dishes
        Map<Dish.Type, List<Dish>> caloricDishesByType = menu.stream().collect(
                groupingBy(Dish::getType,
                        filtering(dish -> dish.getCalories() > 500, toList())));

        Map<Dish.Type, List<String>> dishes1 = menu.stream().collect(
                groupingBy(Dish::getType,
                        mapping(Dish::getName, toList())));

        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));

        Function<Dish, Stream<String>> mapper1 = dish -> {
            if (dishTags.get(dish.getName()) != null) return dishTags.get(dish.getName()).stream();
            return Stream.empty();
        };
        Map<Dish.Type, Set<String>> group2 = menu.stream().collect(
                groupingBy(Dish::getType,
                        flatMapping(mapper1, toSet())));

        // multilevel grouping
        Function<Dish, CaloricLevel> classifier1 = dish -> {
            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        };
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> group3 = menu.stream().collect(
                groupingBy(Dish::getType,
                        groupingBy(classifier1)));

        Map<Dish.Type, Long> typesCount = menu.stream().collect(
                groupingBy(Dish::getType, counting()));

        Map<Dish.Type, Optional<Dish>> group4 = menu.stream().collect(
                groupingBy(Dish::getType,
                        maxBy(comparingInt(Dish::getCalories))));

        Map<Dish.Type, Integer> totalCaloriesByType = menu.stream().collect(
                groupingBy(Dish::getType,
                        summingInt(Dish::getCalories)));
    }

    private enum CaloricLevel { DIET, NORMAL, FAT }

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
