package functional;

import java.util.List;

// composable api
public class FunctionalFusco02 {

    static class API {
        public static Cart buy(List<Item> itens) {
            return new Cart();
        }

        public static Order order(Cart cart) {
            return new Order();
        }

        public static Delivery provide(Order order) {
            return new Delivery();
        }
    }

    static class Item {
    }

    static class Cart {
    }

    static class Order {
    }

    static class Delivery {
    }

}
