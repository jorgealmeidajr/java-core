package functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DSL01Test {

    @Nested
    @DisplayName("Implementation with no method chaining")
    class ImplNoMethodChain {
        @Test
        @DisplayName("No method chaining")
        public void test() {
            DSL01.Order order = new DSL01.Order();
            order.setCustomer("BigBank");

            DSL01.Trade trade1 = new DSL01.Trade();
            trade1.setType(DSL01.Trade.Type.BUY);

            DSL01.Stock stock1 = new DSL01.Stock();
            stock1.setSymbol("IBM");
            stock1.setMarket("NYSE");

            trade1.setStock(stock1);
            trade1.setPrice(150.0);
            trade1.setQuantity(10);
            assertEquals(1500.0, trade1.getValue(), 0.0);

            order.addTrade(trade1);

            DSL01.Trade trade2 = new DSL01.Trade();
            trade2.setType(DSL01.Trade.Type.BUY);

            DSL01.Stock stock2 = new DSL01.Stock();
            stock2.setSymbol("GOOGLE");
            stock2.setMarket("NASDAQ");

            trade2.setStock(stock2);
            trade2.setPrice(100.0);
            trade2.setQuantity(10);
            assertEquals(1000.0, trade2.getValue(), 0.0);

            order.addTrade(trade2);

            assertEquals(2, order.getTrades().size());
            assertEquals(2500.0, order.getValue(), 0.0);
        }
    }

    @Nested
    @DisplayName("Implementation with method chaining")
    class WithMethodChain {
        @Test
        @DisplayName("DSL with method chaining")
        public void testOrderBuilder() {
            DSL01.Order order1 = DSL01.MethodChainingOrderBuilder.forCustomer("BigBank")
                    .buy(10).stock("IBM").on("NYSE").at(150.0)
                    .buy(10).stock("GOOGLE").on("NASDAQ").at(100.0)
                    .end();
            assertEquals(2, order1.getTrades().size());
            assertEquals(2500.0, order1.getValue(), 0.0);

            DSL01.Order order2 = DSL01.MethodChainingOrderBuilder.forCustomer("BigBank")
                    .buy(10).stock("IBM").on("NYSE").at(150.0)
                    .sell(10).stock("GOOGLE").on("NASDAQ").at(100.0)
                    .end();
            assertEquals(2, order2.getTrades().size());
            assertEquals(2500.0, order2.getValue(), 0.0);
        }

        @Test
        @DisplayName("Tax calculator DSL with builder")
        public void testTaxCalculatorWithBuilder() {
            DSL01.Order order = DSL01.MethodChainingOrderBuilder.forCustomer("jorge")
                    .buy(10).stock("IBM").on("NYSE").at(150.0)
                    .end();
            assertEquals(1500.0, order.getValue());

            // dsl with builder
            double value = new DSL01.TaxCalculator()
                    .withTaxRegional()
                    .withTaxSurcharge()
                    .calculate(order);

            assertEquals(1732.5, value, 0.0);
        }

        @Test
        @DisplayName("Tax calculator DSL functional")
        public void testTaxCalculatorFunctional() {
            DSL01.Order order = DSL01.MethodChainingOrderBuilder.forCustomer("jorge")
                    .buy(10).stock("IBM").on("NYSE").at(150.0)
                    .end();
            assertEquals(1500.0, order.getValue());

            // dsl functional
            double value = new DSL01.TaxCalculator2()
                    .with(DSL01.Tax::regional)
                    .with(DSL01.Tax::surcharge)
                    .calculate(order);

            assertEquals(1732.5, value, 0.0);
        }
    }

}
