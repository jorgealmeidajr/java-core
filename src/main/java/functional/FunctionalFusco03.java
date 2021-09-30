package functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

// strategy pattern
public class FunctionalFusco03 {

    public static void main(String[] args) {
        {
            // oop impl
            List<Double> values = asList(10d, 20d, 50d);
            List<Double> convertedValues = convertValues(values, new Mi2KmConverter());
            convertedValues.forEach(System.out::println);
        }

        {
            // functional impl
            Converter2 converter = new Converter2();
            double tenMilesInKm = converter.apply(1.609, 10.0);
//            System.out.println(tenMilesInKm);

            Function<Double, Double> mi2KmConverter = converter.curry1(1.609);
            tenMilesInKm = mi2KmConverter.apply(10.0);
//            System.out.println(tenMilesInKm);

            Stream.of(10d, 20d, 50d)
                    .map(mi2KmConverter)
                    .collect(toList())
                    .forEach(System.out::println);
        }

        {
            // function composition
            Function<Double, Double> celsius2FahrenheitConverter = new Converter2()
                    .curry1(9.0 / 5)
                    .andThen(n -> n + 32);

            Function<Double, Double> fahrenheit2CelsiusConverter = new Converter2()
                    .compose2((Double n) -> n - 32)
                    .curry1(5.0 / 9);
        }
    }

    private interface Converter {
        double convert(double value);
    }

    private static abstract class AbstractConverter implements Converter {
        @Override
        public double convert(double value) {
            return value * getConversionRate();
        }

        protected abstract double getConversionRate();
    }

    private static class Mi2KmConverter extends AbstractConverter {
        @Override
        protected double getConversionRate() {
            return 1.609;
        }
    }

    public static List<Double> convertValues(List<Double> values,
                                             Converter converter) {
        List<Double> convertedValues = new ArrayList<>();
        for (double value : values) {
            convertedValues.add(converter.convert(value));
        }
        return convertedValues;
    }

    private static class Converter2 implements ExtendedBiFunction<Double, Double, Double> {
        @Override
        public Double apply(Double conversionRate, Double value) {
            return conversionRate * value;
        }
    }

    @FunctionalInterface
    private interface ExtendedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        default Function<U, R> curry1(T t) {
            return u -> apply(t, u);
        }

        default Function<T, R> curry2(U u) {
            return t -> apply(t, u);
        }

        default <V> ExtendedBiFunction<V, U, R> compose1(Function<? super V, ? extends T> before) {
            return (v, u) -> apply(before.apply(v), u);
        }

        default <V> ExtendedBiFunction<T, V, R> compose2(Function<? super V, ? extends U> before) {
            return (t, v) -> apply(t, before.apply(v));
        }
    }

}
