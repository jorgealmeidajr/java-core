package functional;

// cat story oop vs functional
public class FunctionalFusco01 {

    static class Bird1 {
    }

    static class Cat1 {
        private Bird1 birdCatch;
        private boolean full;

        public void capture(Bird1 bird) {
            birdCatch = bird;
        }

        public void eat() {
            full = true;
            birdCatch = null;
        }

        public Bird1 getBirdCatch() {
            return birdCatch;
        }

        public boolean isFull() {
            return full;
        }
    }

    static class Bird2 {
    }

    static class Cat2 {
        public CatWithCatch capture(Bird2 bird) {
            return new CatWithCatch(bird);
        }
    }

    static class CatWithCatch {
        private final Bird2 birdCatch;

        public CatWithCatch(Bird2 birdCatch) {
            this.birdCatch = birdCatch;
        }

        public FullCat eat() {
            return new FullCat();
        }
    }

    static class FullCat {
    }

}
