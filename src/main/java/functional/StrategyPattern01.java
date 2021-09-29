package functional;

public class StrategyPattern01 {

    public static void main(String[] args) {
        {
            // pattern made with classes
            Validator numericValidator = new Validator(new IsNumeric());
            boolean b1 = numericValidator.validate("aaaa");
//            System.out.println(b1); // false

            Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
            boolean b2 = lowerCaseValidator.validate("bbbb");
//            System.out.println(b2); // true
        }

        {
            // functional implementation
            Validator numericValidator = new Validator((String s) -> s.matches("\\d+"));
            boolean b1 = numericValidator.validate("bbbb");
            System.out.println(b1); // false

            Validator lowerCaseValidator = new Validator((String s) -> s.matches("[a-z]+"));
            boolean b2 = lowerCaseValidator.validate("aaaa");
            System.out.println(b2); // true
        }
    }

    private interface ValidationStrategy {
        boolean execute(String s);
    }

    private static class IsAllLowerCase implements ValidationStrategy {
        public boolean execute(String s){
            return s.matches("[a-z]+");
        }
    }

    private static class IsNumeric implements ValidationStrategy {
        public boolean execute(String s){
            return s.matches("\\d+");
        }
    }

    private static class Validator {
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy v) {
            this.strategy = v;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

}
