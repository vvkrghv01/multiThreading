public class Synchronization extends Thread{

    private final Counter counter;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++){
            counter.increment();
        }
    }
    public Synchronization(Counter counter){
        this.counter = counter;
    }

    public static class Counter{

        private int count = 0;

        public synchronized void increment(){// here we use keyword synchronized which means at a time only one thread can enter into
            //critical section and which ever the thread enter it acquires lock and no other thread can enter into critical section.
            count++;
        }

        public int getCount(){
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();
        Synchronization t = new Synchronization(counter);//here single instance is being shared between two threads it leads to race condition
        Synchronization t2 = new Synchronization(counter);// both threads access the same instance and increment the value of count,
        //it may be possible at a same time both thread access the same shared method increment and value incremented by both threads and value remains same
        t.start();
        t2.start();

        t.join();
        t2.join();
        System.out.println(counter.getCount());// count should be 2000 coz we are running a loop 1000 times for both threads, but it might not
        // be int the way like we're thinking


    }

}
