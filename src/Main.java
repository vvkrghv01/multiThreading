import static java.lang.Thread.currentThread;

public class Main {
    public static void main(String[] args) {
        System.out.println(currentThread().getName());
        World world = new World();//NEW STATE
        //world.start();
        Thread t1 = new Thread(world);//for initiate the thread of world class ,if we implements a Runnable interface
        t1.start();//RUNNABLE STATE

    }
}