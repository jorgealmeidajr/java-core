package functional;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class StrategyPattern01Test {

    @Nested
    class WithClasses {
        @Test
        @DisplayName("Numeric validator test")
        public void testNumericValidator() {
            StrategyPattern01.Validator numericValidator = new StrategyPattern01.Validator(
                    new StrategyPattern01.IsNumeric());
            assertFalse(numericValidator.validate("aaaa"));
            assertTrue(numericValidator.validate("0123"));
        }

        @Test
        @DisplayName("LowerCase validator test")
        public void testLowerCaseValidator() {
            StrategyPattern01.Validator lowerCaseValidator = new StrategyPattern01.Validator(
                    new StrategyPattern01.IsAllLowerCase());
            assertTrue(lowerCaseValidator.validate("bbbb"));
            assertFalse(lowerCaseValidator.validate("BBBB"));
        }
    }

    @Nested
    class Functional {
        @Test
        @DisplayName("Numeric validator test")
        public void testNumericValidator() {
            StrategyPattern01.Validator numericValidator = new StrategyPattern01.Validator(
                    (String s) -> s.matches("\\d+"));
            assertFalse(numericValidator.validate("aaaa"));
            assertTrue(numericValidator.validate("0123"));
        }

        @Test
        @DisplayName("LowerCase validator test")
        public void testLowerCaseValidator() {
            StrategyPattern01.Validator lowerCaseValidator = new StrategyPattern01.Validator(
                    (String s) -> s.matches("[a-z]+"));
            assertTrue(lowerCaseValidator.validate("bbbb"));
            assertFalse(lowerCaseValidator.validate("BBBB"));
        }
    }

}
