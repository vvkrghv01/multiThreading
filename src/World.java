import static java.lang.Thread.currentThread;

public class World extends Thread{ //implements Runnable {

    public World(String name){
        super(name);
    }
    @Override
    public void run() {

        for(int i = 1; i <= 5 ; i++) {
            System.out.println(Thread.currentThread().getName() + "- Priority :" + Thread.currentThread().getPriority() + "- Count :" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
                //System.out.println("Exception in thread :" + e);
            }
            Thread.yield();//hint to a scheduler current thread willing to yield its current use of processor and wants to give cpu time to other threads


           // System.out.println(i);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        World t1 = new World("vvkrghv");
        World t2 = new World("ash");
        World t3 = new World("priy");
        t1.setDaemon(true);
        t1.start();// used to initialize the user thread;
        t1.setPriority(Thread.MIN_PRIORITY);
        t1.interrupt();
        t2.start();
        t2.setPriority(Thread.MAX_PRIORITY);
        t3.start();
        t3.setPriority(Thread.NORM_PRIORITY);
        t1.join();// by using join method the execution of main thread has stopped, and it waits until t1 thread does not terminate
                 // after that main thread will start its execution
        System.out.println("hello");
    }
}
