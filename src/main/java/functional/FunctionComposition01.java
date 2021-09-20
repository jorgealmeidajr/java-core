package functional;

import java.util.function.Function;

public class FunctionComposition01 {

    public static void main(String[] args) {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(1);
        System.out.println(result);// 4

        h = f.compose(g);
        result = h.apply(1);
        System.out.println(result);// 3
    }

}
