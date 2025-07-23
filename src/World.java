import static java.lang.Thread.currentThread;

public class World implements Runnable{ //extends Thread{

    @Override
    public void run() {

        for(int i = 1; i < 10; i++) System.out.println(currentThread().getName());
    }
}
