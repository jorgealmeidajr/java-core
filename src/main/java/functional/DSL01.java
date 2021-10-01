package functional;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

// using method chaining
public class DSL01 {

    public static void main(String[] args) {
        {
            // no method chaining
            Order order = new Order();
            order.setCustomer("BigBank");

            Trade trade1 = new Trade();
            trade1.setType(Trade.Type.BUY);

            Stock stock1 = new Stock();
            stock1.setSymbol("IBM");
            stock1.setMarket("NYSE");
            trade1.setStock(stock1);
            trade1.setPrice(125.00);
            trade1.setQuantity(80);
            order.addTrade(trade1);

            Trade trade2 = new Trade();
            trade2.setType(Trade.Type.BUY);

            Stock stock2 = new Stock();
            stock2.setSymbol("GOOGLE");
            stock2.setMarket("NASDAQ");
            trade2.setStock(stock2);
            trade2.setPrice(375.00);
            trade2.setQuantity(50);
            order.addTrade(trade2);
        }

        {
            // with method chaining
            Order order1 = MethodChainingOrderBuilder.forCustomer("jorge")
                    .buy(80).stock("IBM").on("NYSE").at(125.0)
                    .sell(20).stock("GOOGLE").on("NASDAQ").at(200.0)
                    .buy(100).stock("TESLA").on("NASDAQ").at(225.0)
                    .end();
            order1.getTrades().stream().forEach(System.out::println);
        }

        {
            Order order = MethodChainingOrderBuilder.forCustomer("jorge")
                    .buy(80).stock("IBM").on("NYSE").at(125.0)
                    .end();

            // dsl with builder
            double value = new TaxCalculator()
                    .withTaxRegional()
                    .withTaxSurcharge()
                    .calculate(order);
            System.out.println(value);
        }

        {
            Order order = MethodChainingOrderBuilder.forCustomer("jorge")
                    .buy(80).stock("IBM").on("NYSE").at(125.0)
                    .end();

            // dsl functional
            double value = new TaxCalculator2()
                    .with(Tax::regional)
                    .with(Tax::surcharge)
                    .calculate(order);
            System.out.println(value);
        }
    }

    @Getter
    @Setter
    private static class Stock {
        private String symbol;
        private String market;
    }

    @Getter
    @Setter
    private static class Trade {
        public enum Type { BUY, SELL }

        private Type type;
        private Stock stock;
        private int quantity;
        private double price;

        public double getValue() {
            return quantity * price;
        }

        @Override
        public String toString() {
            return "Trade{" +
                    "type=" + type +
                    ", quantity=" + quantity +
                    ", price=" + price +
                    '}';
        }
    }

    @Getter
    @Setter
    private static class Order {
        private String customer;
        private List<Trade> trades = new ArrayList<>();

        public void addTrade(Trade trade) {
            trades.add(trade);
        }

        public double getValue() {
            return trades.stream().mapToDouble(Trade::getValue).sum();
        }
    }

    private static class MethodChainingOrderBuilder {
        public final Order order = new Order();

        private MethodChainingOrderBuilder(String customer) {
            order.setCustomer(customer);
        }

        public static MethodChainingOrderBuilder forCustomer(String customer) {
            return new MethodChainingOrderBuilder(customer);
        }

        public TradeBuilder buy(int quantity) {
            return new TradeBuilder(this, Trade.Type.BUY, quantity);
        }

        public TradeBuilder sell(int quantity) {
            return new TradeBuilder(this, Trade.Type.SELL, quantity);
        }

        public MethodChainingOrderBuilder addTrade(Trade trade) {
            order.addTrade(trade);
            return this;
        }

        public Order end() {
            return order;
        }
    }

    private static class TradeBuilder {
        private final MethodChainingOrderBuilder builder;
        public final Trade trade = new Trade();

        private TradeBuilder(MethodChainingOrderBuilder builder,
                             Trade.Type type, int quantity) {
            this.builder = builder;
            trade.setType(type);
            trade.setQuantity(quantity);
        }

        public StockBuilder stock(String symbol) {
            return new StockBuilder(builder, trade, symbol);
        }
    }

    private static class StockBuilder {
        private final MethodChainingOrderBuilder builder;
        private final Trade trade;
        private final Stock stock = new Stock();

        private StockBuilder(MethodChainingOrderBuilder builder,
                             Trade trade, String symbol) {
            this.builder = builder;
            this.trade = trade;
            stock.setSymbol(symbol);
        }

        public TradeBuilderWithStock on(String market) {
            stock.setMarket(market);
            trade.setStock(stock);
            return new TradeBuilderWithStock(builder, trade);
        }
    }

    private static class TradeBuilderWithStock {
        private final MethodChainingOrderBuilder builder;
        private final Trade trade;

        public TradeBuilderWithStock(MethodChainingOrderBuilder builder,
                                     Trade trade) {
            this.builder = builder;
            this.trade = trade;
        }

        public MethodChainingOrderBuilder at(double price) {
            trade.setPrice(price);
            return builder.addTrade(trade);
        }
    }

    private static class Tax {
        public static double regional(double value) {
            return value * 1.1;
        }

        public static double general(double value) {
            return value * 1.3;
        }

        public static double surcharge(double value) {
            return value * 1.05;
        }
    }

    private static class TaxCalculator {
        private boolean useRegional;
        private boolean useGeneral;
        private boolean useSurcharge;

        public TaxCalculator withTaxRegional() {
            useRegional = true;
            return this;
        }

        public TaxCalculator withTaxGeneral() {
            useGeneral = true;
            return this;
        }

        public TaxCalculator withTaxSurcharge() {
            useSurcharge = true;
            return this;
        }

        public double calculate(Order order) {
            return calculate(order, useRegional, useGeneral, useSurcharge);
        }

        public static double calculate(Order order, boolean useRegional,
                                       boolean useGeneral, boolean useSurcharge) {
            double value = order.getValue();
            if (useRegional) value = Tax.regional(value);
            if (useGeneral) value = Tax.general(value);
            if (useSurcharge) value = Tax.surcharge(value);
            return value;
        }
    }

    private static class TaxCalculator2 {
        public DoubleUnaryOperator taxFunction = d -> d;

        public TaxCalculator2 with(DoubleUnaryOperator f) {
            taxFunction = taxFunction.andThen(f);
            return this;
        }

        public double calculate(Order order) {
            return taxFunction.applyAsDouble(order.getValue());
        }
    }

}
