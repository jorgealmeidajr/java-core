package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLock03 {

    static final int NUM_INCREMENTS = 10000;
    static ReentrantLock lock = new ReentrantLock();
    static int count = 0;

    static void increment1() {
        count = count + 1;
    }

    static void increment2() {
        synchronized (ReentrantLock03.class) {
            count = count + 1;
        }
    }

    static void increment3() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        count = 0;
        {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            IntStream.range(0, NUM_INCREMENTS)
                .forEach(i -> executor.submit(ReentrantLock03::increment3));

            ConcurrentUtils.stop(executor);

            System.out.println(count);
        }

        count = 0;
        {
            ExecutorService executor = Executors.newFixedThreadPool(2);

            executor.submit(() -> {
                lock.lock();
                try {
                    ConcurrentUtils.sleep(1);
                } finally {
                    lock.unlock();
                }
            });

            executor.submit(() -> {
                System.out.println("Locked: " + lock.isLocked());
                System.out.println("Held by me: " + lock.isHeldByCurrentThread());
                boolean locked = lock.tryLock();
                System.out.println("Lock acquired: " + locked);
            });

            ConcurrentUtils.stop(executor);
        }
    }

}
