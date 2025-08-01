public class Synchronization extends Thread{

    private final Counter counter;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++){
            counter.increment();
        }
        counter.withdraw(50);
    }
    public Synchronization(Counter counter,String name){
        super(name);
        this.counter = counter;
    }

    public static class Counter{

        private int count = 0;
        private int balance = 200;

        public synchronized void withdraw(int amount){
            System.out.println(Thread.currentThread().getName() + "--Attempting to withdraw--" + amount);
            if(balance >= amount){
                System.out.println(Thread.currentThread().getName()+ "--processing withdrawal--" + amount);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                balance -= amount;
                System.out.println(Thread.currentThread().getName()+"--Given amount has withdrew -- current balance is--"+ balance);

            }else{
                System.out.println(Thread.currentThread().getName() + "--Insufficient balance");
            }
        }

        public synchronized void increment(){// here we use keyword synchronized which means at a time only one thread can enter into
            //critical section and which ever the thread enter it acquires lock and no other thread can enter into critical section.
            count++;
        }

        public int getCount(){
            return count;
        }
    }

    public static void main(String[] args) {

        Counter counter = new Counter();
        Synchronization t = new Synchronization(counter,"USER 1");//here single instance is being shared between two threads it leads to race condition
        Synchronization t2 = new Synchronization(counter, "USER 2");// both threads access the same instance and increment the value of count,
        //it may be possible at a same time both thread access the same shared method increment and value incremented by both threads and value remains same
        t.start();
        t2.start();

        try {
            t.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        Synchronization user1 = new Synchronization(counter, "USER 3");//here single instance is being shared between two threads it leads to race condition
        Synchronization user2 = new Synchronization(counter, "USER 4");
        user1.start();
        user2.start();

        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(counter.getCount());// count should be 4000 coz we are running a loop 1000 times for four threads, but it might not
        // be int the way like we're thinking if we do not use synchronized keyword


    }

}
