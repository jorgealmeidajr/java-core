package functional;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

// composable api
public class FunctionalFusco02 {

    public static void main(String[] args) {
        Function<List<Item>, Cart> buy = API::buy;
        Function<List<Item>, Delivery> oneClickBuy = buy
                .andThen(API::order)
                .andThen(API::order);

        Item book = new Item();
        Item watch = new Item();
        Item phone = new Item();
        Delivery delivery = oneClickBuy.apply(asList(book, watch, phone));
    }

    private static class API {
        public static Cart buy(List<Item> itens) {
            return new Cart();
        }

        public static Order order(Cart cart) {
            return new Order();
        }

        public static Delivery order(Order order) {
            return new Delivery();
        }
    }

    private static class Item {
    }

    private static class Cart {
    }

    private static class Order {
    }

    private static class Delivery {
    }

}
