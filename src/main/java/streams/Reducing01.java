package streams;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class Reducing01 {

    public static void main(String[] args) {
        Integer sum1 = Stream.of(1, 2, 3, 4)
                .reduce(0, (a, b) -> a + b);
        System.out.println(sum1);

        sum1 = Arrays.asList(1, 2, 3, 4)
                .stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println(sum1);

        sum1 = Arrays.asList(1, 2, 3, 4)
                .stream()
                .reduce(0, Integer::sum);
        System.out.println(sum1);

        Optional<Integer> maxValue = Arrays.asList(1, 2, 3, 4)
                .stream()
                .reduce(Integer::max);
    }

}
