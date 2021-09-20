package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock01 {

    static Lock lock = new ReentrantLock();
    static int count = 0;

    static void accessSharedResource() {
        lock.lock();
        print("lock acquired");
        sleep(100);
        try {
            count++;
        } finally {
            lock.unlock();
            print("lock unlocked");
        }
    }

    public static void main(String[] args) {
        System.out.println("initial value = " + count);

        Thread t1 = new Thread(() -> accessSharedResource(), "T1");
        Thread t2 = new Thread(() -> accessSharedResource(), "T2");
        Thread t3 = new Thread(() -> accessSharedResource(), "T3");
        Thread t4 = new Thread(() -> accessSharedResource(), "T4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("final value = " + count);
    }

    static void print(String message) {
        System.out.println("Thread " + Thread.currentThread().getName() + ": " + message);
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
