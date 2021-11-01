package functional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Factory01 {

    static class ProductFactory {
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

    static Product createProduct(String name){
        Supplier<Product> p = map.get(name);
        if(p != null) return p.get();
        throw new IllegalArgumentException("No such product " + name);
    }

    static class Product {
    }

    static class Loan extends Product {
    }

    static class Stock extends Product {
    }

    static class Bond extends Product {
    }

}
