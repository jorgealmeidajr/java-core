package functional;

import java.util.function.Consumer;

public class Template01 {

    static abstract class OnlineBanking {
        private Database database;

        public OnlineBanking(Database database) {
            this.database = database;
        }

        public void processCustomer(int id){
            Customer c = database.getCustomerWithId(id);
            makeCustomerHappy(c);
        }
        abstract void makeCustomerHappy(Customer c);
    }

    static class OnlineBankingHappy extends OnlineBanking {
        public OnlineBankingHappy(Database database) {
            super(database);
        }

        @Override
        void makeCustomerHappy(Customer c) {
            System.out.println("Hello " + c.getName());
            if (!c.isHappy()) {
                c.setHappy(true);
            }
        }
    }

    static class OnlineBankingLambda {
        private Database database;

        public OnlineBankingLambda(Database database) {
            this.database = database;
        }

        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
            Customer c = database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }
    }

    static class Customer {
        private int id;
        private String name;
        private boolean happy;

        public Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean isHappy() {
            return happy;
        }

        void setHappy(boolean happy) {
            this.happy = happy;
        }
    }

    interface Database {
        Customer getCustomerWithId(int id);
    }

}
