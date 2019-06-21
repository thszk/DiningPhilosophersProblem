import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {

    private static final int THINKING = 0;
    private static final int HUNGRY = 1;
    private static final int EATING = 2;

    private int id;
    private int state;
    private Semaphore semaphore;
    boolean forks[]; // false -> busy | true -> available

    public Philosopher(int id, Semaphore semaphore, boolean[] forks) {
        this.id = id;
        this.semaphore = semaphore;
        this.forks = forks;
        this.state = -1;
    }

    private void think() {
        try {
            this.state = THINKING;
            System.out.println("Philosopher #" + id + " THINKING");
            Thread.sleep( 1000 );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void hungry() {
        try {
            this.state = HUNGRY;
            System.out.println("Philosopher #" + id + " HUNGRY");
            Thread.sleep( 1000 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean takesFork() {
        // right
        if ( forks[(id+1)%forks.length] ) {
            forks[(id+1)%forks.length] = false;
            // left
            if ( forks[id] ) {
                forks[id] = false;
                System.out.println("Philosopher #" + id + " takes the forks");
                return true;
            }
        }
        forks[(id+1)%forks.length] = true;
        return false;
    }

    private void eat() {
        try {
            this.state = EATING;
            System.out.println("Philosopher #" + id + " eating");
            Thread.sleep( 1000 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseFork() {
        // right
        forks[(id+1)%forks.length] = true;
        // left
        forks[id] = true;
        System.out.println("Philosopher #" + id + " release the forks");
    }

    @Override
    public void run() {
        while(true) {
            try {
                think();
                hungry();
                semaphore.acquire();
                while (!takesFork()) {  }
                eat();
                releaseFork();
                System.out.println("Philosopher #" + id + " exiting");
                semaphore.release();
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
    }
}