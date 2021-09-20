package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BehaviourParameter03 {

    // more abstract
    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for(T e: list) {
            if(p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, AppleColor.GREEN),
                new Apple(155, AppleColor.GREEN),
                new Apple(120, AppleColor.RED));

        List<Apple> redApples = filter(inventory, (Apple apple) -> AppleColor.RED.equals(apple.getColor()));
        List<Integer> evenNumbers = filter(Arrays.asList(1, 2, 3, 4), (Integer i) -> i % 2 == 0);
    }

    static private class Apple {
        int weight;
        AppleColor color;

        public Apple(int weight, AppleColor color) {
            this.weight = weight;
            this.color = color;
        }

        public int getWeight() {
            return weight;
        }

        public AppleColor getColor() {
            return color;
        }
    }

    static private enum AppleColor {
        RED, GREEN
    }

    @FunctionalInterface
    static private interface Predicate<T> {
        boolean test(T t);
    }

}
