package functional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class FunctionalFusco02Test {

    @Test
    @DisplayName("Composable API")
    public void test() {
        Function<List<FunctionalFusco02.Item>, FunctionalFusco02.Cart> buy = FunctionalFusco02.API::buy;

        Function<List<FunctionalFusco02.Item>, FunctionalFusco02.Delivery> oneClickBuy = buy
                .andThen(FunctionalFusco02.API::order)
                .andThen(FunctionalFusco02.API::provide);

        FunctionalFusco02.Item book = new FunctionalFusco02.Item();
        FunctionalFusco02.Item watch = new FunctionalFusco02.Item();
        FunctionalFusco02.Item phone = new FunctionalFusco02.Item();

        assertTrue(oneClickBuy.apply(asList(book, watch, phone)) instanceof FunctionalFusco02.Delivery);
    }

}
