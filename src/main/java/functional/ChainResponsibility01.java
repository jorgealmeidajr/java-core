package functional;

public class ChainResponsibility01 {

    static abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor){
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if(successor != null){
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    static class HeaderTextProcessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }
    }

    static class SpellCheckerProcessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }

}
