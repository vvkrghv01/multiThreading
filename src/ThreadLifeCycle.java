public class ThreadLifeCycle extends Thread {

    @Override
    public void run() {
        //System.out.println("Running");//running state is not mentioned in java manually printing it only runnable is there
        try {
            int count = 0;
            while(count < 5) {
                System.out.println("t1 am going to sleep");
                Thread.sleep(500);// if we set the same timer for the main thread and the thread t in that case we do not able to see Timed_waiting state
                                      // because when the flow goes from main thread sleep to next line during that time thread t is somewhere inn live mode
                                     // and hence runnable gets display
                System.out.println("t1 am going to work");

                count += 1;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLifeCycle t = new ThreadLifeCycle();
        System.out.println(t.getState());// thread just created but not used
        t.start();
        System.out.println(t.getState());// in java runnable state considered as whether thread is in running state
                                        // or ready to run
        // System.out.println(Thread.currentThread().getState());// currentThread method returns the current method thread state
                                                             // that is main method.
        Thread.sleep(100);// explicitly stop the thread execution so that run() can execute.

        System.out.println(t.getState());
        t.join();
        System.out.println(t.getState());
    }

}
