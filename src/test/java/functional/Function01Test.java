package functional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Function01Test {

    @Test
    @DisplayName("Function map generic test")
    public void test() {
        assertEquals(Arrays.asList(2, 4, 6, 8),
                Function01.map(Arrays.asList(1, 2, 3, 4), (Integer i) -> i * 2));

        assertEquals(Arrays.asList("ONE", "TWO", "THREE"),
                Function01.map(Arrays.asList("one", "two", "three"), (String s) -> s.toUpperCase()));
    }

}
