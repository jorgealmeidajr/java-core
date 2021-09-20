package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function01 {

    @FunctionalInterface
    private static interface Function<T, R> {
        R apply(T t);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T t: list) {
            result.add(f.apply(t));
        }
        return result;
    }

    public static void main(String[] args) {
        var result1 = map(Arrays.asList(1, 2, 3, 4), (Integer i) -> i * 2);
        var result2 = map(Arrays.asList("one", "two", "three"), (String s) -> s.toUpperCase());
    }

}
