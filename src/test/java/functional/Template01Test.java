package functional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Template01Test {

    @Mock
    Template01.Database database;
    Template01.Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Template01.Customer(1, "John");

        when(database.getCustomerWithId(1))
                .thenReturn(customer);
    }

    @Nested
    @DisplayName("Implementation with classes")
    class WithClasses {
        @Test
        @DisplayName("Make a customer happy")
        public void test() {
            assertFalse(customer.isHappy());
            Template01.OnlineBankingHappy onlineBankingHappy = new Template01.OnlineBankingHappy(database);
            onlineBankingHappy.processCustomer(1);

            verify(database, times(1)).getCustomerWithId(1);
            assertTrue(customer.isHappy());
        }
    }

    @Nested
    @DisplayName("Functional implementation")
    class Functional {
        @Test
        @DisplayName("Make a customer happy")
        public void test() {
            assertFalse(customer.isHappy());
            Template01.OnlineBankingLambda onlineBankingLambda = new Template01.OnlineBankingLambda(database);

            Consumer<Template01.Customer> makeCustomerHappy = customer -> {
                System.out.println("Hello " + customer.getName());

                if (!customer.isHappy()) {
                    customer.setHappy(true);
                }
            };
            onlineBankingLambda.processCustomer(1, makeCustomerHappy);

            verify(database, times(1)).getCustomerWithId(1);
            assertTrue(customer.isHappy());
        }
    }

}
