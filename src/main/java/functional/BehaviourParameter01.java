package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BehaviourParameter01 {

    // ONE PARAMETER for MULTIPLE BEHAVIORS
    public static List<Apple> filterApples1(List<Apple> inventory, ApplePredicate p) {
        List<Apple> result = new ArrayList<>();
        for(Apple apple: inventory) {
            if(p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String... args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, AppleColor.GREEN),
                new Apple(155, AppleColor.GREEN),
                new Apple(120, AppleColor.RED));

        List<Apple> heavyApples = filterApples1(inventory, new AppleHeavyWeightPredicate());
        List<Apple> greenApples = filterApples1(inventory, new AppleGreenColorPredicate());

        // old usage of class with no name
        List<Apple> redApples = filterApples1(inventory, new ApplePredicate() {
            public boolean test(Apple apple){
                return AppleColor.RED.equals(apple.getColor());
            }
        });

        // same thing with lambda expression
        redApples = filterApples1(inventory,
                (Apple apple) -> AppleColor.RED.equals(apple.getColor()));
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
    static private interface ApplePredicate {
        boolean test(Apple apple);
    }

    // MULTIPLE BEHAVIORS
    static class AppleHeavyWeightPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    static class AppleGreenColorPredicate implements ApplePredicate {
        public boolean test(Apple apple) {
            return AppleColor.GREEN.equals(apple.getColor());
        }
    }

    public class AppleRedAndHeavyPredicate implements ApplePredicate {
        public boolean test(Apple apple){
            return AppleColor.RED.equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

}
