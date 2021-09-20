package concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// example with deadlock
public class Deadlock01 {

    Lock lock1 = new ReentrantLock(true);
    Lock lock2 = new ReentrantLock(true);

    void firstOperation() {
        lock1.lock();
        print("lock1 acquired, waiting to acquire lock2");
        sleep(50);

        lock2.lock();
        print("lock2 acquired");

        print("executing first operation");

        lock2.unlock();
        lock1.unlock();
    }

    void secondOperation() {
        lock2.lock();
        print("lock2 acquired, waiting to acquire lock1");
        sleep(50);

        lock1.lock();
        print("lock1 acquired");

        print("executing second operation");

        lock1.unlock();
        lock2.unlock();
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
        new Deadlock01().execute();
    }

}
