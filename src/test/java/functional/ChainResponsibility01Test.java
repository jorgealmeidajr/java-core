package functional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainResponsibility01Test {

    @Nested
    @DisplayName("Implementation made with classes OOP")
    class ImplWithClasses {
        @Test
        @DisplayName("Process text pipeline")
        public void processTextPipeline() {
            ChainResponsibility01.ProcessingObject<String> headerStep = new ChainResponsibility01.HeaderTextProcessing();
            ChainResponsibility01.ProcessingObject<String> spellStep = new ChainResponsibility01.SpellCheckerProcessing();
            headerStep.setSuccessor(spellStep);

            assertEquals("From Raoul, Mario and Alan: Aren't lambdas really sexy?!!", headerStep.handle("Aren't labdas really sexy?!!"));
        }
    }

    @Nested
    @DisplayName("Functional implementation")
    class Functional {
        @Test
        @DisplayName("Process text pipeline")
        public void processTextPipeline() {
            UnaryOperator<String> headerProcessing =
                    (String text) -> "From Raoul, Mario and Alan: " + text;
            UnaryOperator<String> spellCheckerProcessing =
                    (String text) -> text.replaceAll("labda", "lambda");

            Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
            var result = pipeline.apply("Aren't labdas really sexy?!!");

            assertEquals("From Raoul, Mario and Alan: Aren't lambdas really sexy?!!", result);
        }
    }

}
