package functional;

import java.util.function.Consumer;

public class Template01 {

    public static void main(String[] args) {
        // pattern made with classes
        new OnlineBankingHappy().processCustomer(1);

        // functional implementation
        new OnlineBankingLambda().processCustomer(1,
                (customer -> System.out.println("Hello " + customer.getName())));
    }

    private static abstract class OnlineBanking {
        public void processCustomer(int id){
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }
        abstract void makeCustomerHappy(Customer c);
    }

    private static class OnlineBankingHappy extends OnlineBanking {
        @Override
        void makeCustomerHappy(Customer c) {
            System.out.println("Hello " + c.getName());
        }
    }

    private static class OnlineBankingLambda {
        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }
    }

    private static class Customer {
        private int id;
        private String name;

        public Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static class Database {
        public static Customer getCustomerWithId(int id) {
            return new Customer(id, "test");
        }
    }

}
