package functional;

import java.util.function.BiFunction;
import java.util.function.Function;

// cat story oop vs functional
public class FunctionalFusco01 {

    public static void main(String[] args) {
        {
            // OOP implementation
            Cat1 cat = new Cat1();
            Bird1 bird = new Bird1();
            cat.capture(bird);
            cat.eat();
        }

        {
            // functional impl 1
            BiFunction<Cat2, Bird2, FullCat> story =
                    ((BiFunction<Cat2, Bird2, CatWithCatch>) Cat2::capture)
                            .andThen(CatWithCatch::eat);
            FullCat fullCat = story.apply(new Cat2(), new Bird2());
        }

        {
            // functional impl 2
            BiFunction<Cat2, Bird2, CatWithCatch> capture = Cat2::capture;
            Function<CatWithCatch, FullCat> eat = CatWithCatch::eat;
            BiFunction<Cat2, Bird2, FullCat> story = capture.andThen(eat);
            FullCat fullCat = story.apply(new Cat2(), new Bird2());
        }
    }

    private static class Bird1 {
    }

    private static class Cat1 {
        private Bird1 birdCatch;
        private boolean full;

        public void capture(Bird1 bird) {
            birdCatch = bird;
        }

        public void eat() {
            full = true;
            birdCatch = null;
        }
    }

    private static class Bird2 {
    }

    private static class Cat2 {
        public CatWithCatch capture(Bird2 bird) {
            return new CatWithCatch(bird);
        }
    }

    private static class CatWithCatch {
        private final Bird2 birdCatch;

        public CatWithCatch(Bird2 birdCatch) {
            this.birdCatch = birdCatch;
        }

        public FullCat eat() {
            return new FullCat();
        }
    }

    private static class FullCat {
    }

}
