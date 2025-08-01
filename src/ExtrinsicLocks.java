import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExtrinsicLocks {

    private final Lock lock = new ReentrantLock();

    private  int balance = 200;

    public void withdraw(int amount){
        System.out.println(Thread.currentThread().getName() + "--Attempting to withdraw--"+ amount);

        try {
            if(lock.tryLock(4000, TimeUnit.MILLISECONDS)){// here we provide fairness to each thread so that starvation get reduced
                if(balance >= amount){

                    try{
                        System.out.println(Thread.currentThread().getName()+ "--processing withdrawal--");
                        Thread.sleep(3000);
                        balance -= amount;
                        System.out.println(Thread.currentThread().getName()+"--Withdrawal has completed--Current balance is--"+balance);

                    }catch (Exception e){

                    }finally {
                        lock.unlock();// always free the resources in finally block, keep in mind
                    }

                }else{
                    System.out.println(Thread.currentThread().getName()+"--Insufficient balance");
                }
            }else{
                System.out.println(Thread.currentThread().getName()+"--could not acquire lock will try later");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExtrinsicLocks sbi = new ExtrinsicLocks();
        Runnable task = new Runnable() { // we simply create a anonymous class so that no need to implement or extend thread class or runnable interface
            @Override
            public void run() {
                sbi.withdraw(50);
            }
        };

        Thread t1 = new Thread(task,"USER 1");
        Thread t2 = new Thread(task, "USER 2");
        t1.start();
        t2.start();

    }

}

