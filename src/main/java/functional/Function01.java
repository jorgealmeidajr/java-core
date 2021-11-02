package functional;

import java.util.ArrayList;
import java.util.List;

public class Function01 {

    @FunctionalInterface
    interface Function<T, R> {
        R apply(T t);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T t: list) {
            result.add(f.apply(t));
        }
        return result;
    }

}
