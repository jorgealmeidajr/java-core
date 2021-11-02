package functional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionalFusco01Test {

    @Nested
    @DisplayName("OOP implementation")
    class ImplWithClasses {
        @Test
        @DisplayName("Cat eats bird")
        public void catEatsBird() {
            FunctionalFusco01.Cat1 cat = new FunctionalFusco01.Cat1();
            FunctionalFusco01.Bird1 bird = new FunctionalFusco01.Bird1();

            cat.capture(bird);
            cat.eat();

            assertNull(cat.getBirdCatch());
            assertTrue(cat.isFull());
        }
    }

    @Nested
    @DisplayName("Functional implementation")
    class Functional {
        @Test
        @DisplayName("Cat eats bird functional 1")
        public void catEatsBird1() {
            BiFunction<FunctionalFusco01.Cat2, FunctionalFusco01.Bird2, FunctionalFusco01.CatWithCatch> catCaptureBird =
                    FunctionalFusco01.Cat2::capture;

            BiFunction<FunctionalFusco01.Cat2, FunctionalFusco01.Bird2, FunctionalFusco01.FullCat> story =
                    catCaptureBird
                            .andThen(FunctionalFusco01.CatWithCatch::eat);

            assertTrue(story.apply(new FunctionalFusco01.Cat2(), new FunctionalFusco01.Bird2()) instanceof FunctionalFusco01.FullCat);
        }

        @Test
        @DisplayName("Cat eats bird functional 2")
        public void catEatsBird2() {
            BiFunction<FunctionalFusco01.Cat2, FunctionalFusco01.Bird2, FunctionalFusco01.CatWithCatch> capture =
                    FunctionalFusco01.Cat2::capture;

            Function<FunctionalFusco01.CatWithCatch, FunctionalFusco01.FullCat> eat =
                    FunctionalFusco01.CatWithCatch::eat;

            BiFunction<FunctionalFusco01.Cat2, FunctionalFusco01.Bird2, FunctionalFusco01.FullCat> story =
                    capture.andThen(eat);

            assertTrue(story.apply(new FunctionalFusco01.Cat2(), new FunctionalFusco01.Bird2()) instanceof FunctionalFusco01.FullCat);
        }
    }

}
