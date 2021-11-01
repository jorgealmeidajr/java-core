package functional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Factory01Test {

    @Nested
    class WithClasses {
        @Test
        @DisplayName("Test pattern made with classes")
        public void testPatternWithClasses() {
            Factory01.Product p = Factory01.ProductFactory.createProduct("loan");
            assertTrue(p instanceof Factory01.Loan);

            p = Factory01.ProductFactory.createProduct("stock");
            assertTrue(p instanceof Factory01.Stock);
        }

        @Test
        @DisplayName("Test pattern with classes exception")
        public void testPatternWithClassesFail() {
            Throwable throwable = assertThrows(RuntimeException.class,
                    () -> Factory01.ProductFactory.createProduct("unknown"));
            assertEquals("No such product unknown", throwable.getMessage());
        }
    }

    @Nested
    class Functional {
        @Test
        @DisplayName("Test same pattern made in functional way")
        public void testPatternFunctional() {
            Factory01.Product p = Factory01.createProduct("loan");
            assertTrue(p instanceof Factory01.Loan);
        }

        @Test
        @DisplayName("Test functional pattern exception")
        public void testPatternFunctionalException() {
            Throwable throwable = assertThrows(RuntimeException.class,
                    () -> Factory01.createProduct("unknown"));
            assertEquals("No such product unknown", throwable.getMessage());
        }
    }

}
