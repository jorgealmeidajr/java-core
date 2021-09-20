package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// example with deadlock solution with global ordering
public class Deadlock02 {

    Lock lock1 = new ReentrantLock(true);
    Lock lock2 = new ReentrantLock(true);

    void firstOperation() {
        lock1.lock();

        try {
            print("lock1 acquired, waiting to acquire lock2");
            sleep(50);

            lock2.lock();
            print("lock2 acquired");

            print("executing first operation");
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }

    void secondOperation() {
        lock1.lock();

        try {
            print("lock1 acquired, waiting to acquire lock2");
            sleep(50);

            lock2.lock();
            print("lock2 acquired");

            print("executing second operation");
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }

    void execute() {
        new Thread(this::firstOperation, "T1-first_operation").start();
        new Thread(this::secondOperation, "T2-second_operation").start();
    }

    public void print(String message) {
        System.out.println("Thread " + Thread.currentThread().getName() + ": " + message);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Deadlock02().execute();
    }

}
