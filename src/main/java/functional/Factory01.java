package functional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Factory01 {

    public static void main(String[] args) {
        // pattern made with classes
        Product p = ProductFactory.createProduct("loan");
        System.out.println(p);

        // functional implementation
        p = createProduct("loan");
        System.out.println(p);

        p = createProduct("unknown");
        System.out.println(p);
    }

    private static class ProductFactory {
        public static Product createProduct(String name) {
            switch (name) {
                case "loan": return new Loan();
                case "stock": return new Stock();
                case "bond": return new Bond();
                default: throw new RuntimeException("No such product " + name);
            }
        }
    }

    final static Map<String, Supplier<Product>> map = new HashMap<>();
    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

    private static Product createProduct(String name){
        Supplier<Product> p = map.get(name);
        if(p != null) return p.get();
        throw new IllegalArgumentException("No such product " + name);
    }

    private static class Product {
    }

    private static class Loan extends Product {
    }

    private static class Stock extends Product {
    }

    private static class Bond extends Product {
    }

}
