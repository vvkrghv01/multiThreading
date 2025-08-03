import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantExample {
    private  final Lock lock = new ReentrantLock(true);
    //if a thread has acquired a lock it can again acquire lock means reentrant to avoid deadlock
    //during reentrancy count has maintained to check how many times lock has acquired by a thread in order to figure out how many times lock has to unlock

    public void outerMethod(){
        lock.lock();//other threads can not enter into this method because thread 1 has acquired lock. Other Threads are in waiting or blocked state.
        try{
            System.out.println("Inside outerMethod");
            innerMethod();
        }finally {
            lock.unlock();
        }
    }
    public void innerMethod(){
        lock.lock();// here the same thread try to acquire the lock which is already had taken java allow this to avoid deadlock
        try {
            System.out.println("Inside innerMethod");

        }finally {
            lock.unlock();
        }
    }
    public void accessMethod(){
        lock.lock();
        try{
            System.out.println("Lock is acquired by "+ Thread.currentThread().getName());
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }finally {
            System.out.println("Lock is released by "+Thread.currentThread().getName());
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantExample obj = new ReentrantExample();
        //obj.outerMethod();
        //reentrancy also used by synchronized keyword

        //Now  time to look at the fairness of locks
        Runnable task = new Runnable() {
            @Override
            public void run() {
                obj.accessMethod();
            }
        };
        Thread t1 = new Thread(task,"Thread 1");
        Thread t2 = new Thread(task,"Thread 2");
        Thread t3 = new Thread(task,"Thread 3");

        t1.start();
        t2.start();
        t3.start();

        //Although these threads get executed randomly no fix ordering is there and this might be possible if there are multiple threads few threads could
        //not get the cpu time for execution , to avoid this we pass the true flag in reentrant lock constructor.
        //and hence starvation is avoided

        //Advantage of manual locking over synchronized
        /*
        1.Provide fairness to all the threads to execute without starvation
        2.Thread never goes to blocked state as we use try lock  by passing time as a parameter for waiting.
        3.Provides interrupt ability.
        4.read/write blocking.
         */


    }
}
