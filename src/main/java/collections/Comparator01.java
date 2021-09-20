package collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Comparator01 {

    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, AppleColor.GREEN, "china"),
                new Apple(155, AppleColor.GREEN, "china"),
                new Apple(155, AppleColor.GREEN, "australia"),
                new Apple(120, AppleColor.RED, "australia"));

        inventory.sort(new Comparator<Apple>() {
            public int compare(Apple a1, Apple a2){
                return a1.getWeight().compareTo(a2.getWeight());
            }
        });

        Comparator<Apple> c1 = (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
        Comparator<Apple> c2 = (a1, a2) -> a1.getWeight().compareTo(a2.getWeight());

        inventory.sort(Comparator.comparing(Apple::getWeight));

        inventory.sort(Comparator.comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getCountry));
    }

    static private class Apple {
        Integer weight;
        AppleColor color;
        String country;

        public Apple(Integer weight, AppleColor color, String country) {
            this.weight = weight;
            this.color = color;
            this.country = country;
        }

        public Integer getWeight() {
            return weight;
        }

        public AppleColor getColor() {
            return color;
        }

        public String getCountry() {
            return country;
        }
    }

    static private enum AppleColor {
        RED, GREEN
    }

}
