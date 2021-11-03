package functional;

import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

// using method chaining
public class DSL01 {

    @Getter
    @Setter
    static class Stock {
        private String symbol;
        private String market;
    }

    @Getter
    @Setter
    static class Trade {
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
    static class Order {
        private String customer;
        private List<Trade> trades = new ArrayList<>();

        public void addTrade(Trade trade) {
            trades.add(trade);
        }

        public double getValue() {
            return trades.stream().mapToDouble(Trade::getValue).sum();
        }
    }

    static class MethodChainingOrderBuilder {
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

    static class TradeBuilder {
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

    static class StockBuilder {
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

    static class TradeBuilderWithStock {
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

    static class Tax {
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

    static class TaxCalculator {
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

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String result = decimalFormat.format(value);
            return Double.parseDouble(result.replace(',', '.'));
        }
    }

    static class TaxCalculator2 {
        public DoubleUnaryOperator taxFunction = d -> d;

        public TaxCalculator2 with(DoubleUnaryOperator f) {
            taxFunction = taxFunction.andThen(f);
            return this;
        }

        public double calculate(Order order) {
            double value = taxFunction.applyAsDouble(order.getValue());
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String result = decimalFormat.format(value);
            return Double.parseDouble(result.replace(',', '.'));
        }
    }

}
