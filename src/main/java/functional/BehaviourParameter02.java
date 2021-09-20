package functional;

import java.util.List;

public class BehaviourParameter02 {

    static void prettyPrintApple(List<Apple> inventory, AppleFormatter formatter) {
        for(Apple apple: inventory) {
            String output = formatter.accept(apple);
            System.out.println(output);
        }
    }

    static private class Apple {
        int weight;
        AppleColor color;

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
    static private interface AppleFormatter {
        String accept(Apple apple);
    }

    static class AppleFancyFormatter implements AppleFormatter {
        public String accept(Apple apple) {
            String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
            return "A " + characteristic + " " + apple.getColor() +" apple";
        }
    }

    static class AppleSimpleFormatter implements AppleFormatter {
        public String accept(Apple apple) {
            return "An apple of " + apple.getWeight() + "g";
        }
    }

}
