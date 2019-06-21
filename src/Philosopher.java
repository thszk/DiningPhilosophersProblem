import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    // flag of philosophers state
    private static final int THINKING = 0;
    private static final int HUNGRY = 1;
    private static final int EATING = 2;

    private int id;
    private int state; // not effectively used
    private Semaphore semaphore;
    boolean fork[]; // fork array, shared with all philosophers
                     // false -> busy | true -> available

    public Philosopher(int id, Semaphore semaphore, boolean[] fork) {
        this.id = id;
        this.semaphore = semaphore;
        this.fork = fork;
    }

    private void think() {
        try {
            this.state = THINKING;
            System.out.println("Philosopher #" + id + " THINKING");
            Thread.sleep((int)(2000+Math.random() * 5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hungry() {
        try {
            this.state = HUNGRY;
            System.out.println("Philosopher #" + id + " HUNGRY");
            Thread.sleep((int)(2000+Math.random() * 5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean takesFork() {
        // right
        if ( fork[(id+1)%fork.length] ) {
            fork[(id+1)%fork.length] = false;
            // left
            if ( fork[id] ) {
                fork[id] = false;
                System.out.println("Philosopher #" + id + " takes the fork");
                return true;
            }
        }
        fork[(id+1)%fork.length] = true;
        return false;
    }

    private void eat() {
        try {
            this.state = EATING;
            System.out.println("Philosopher #" + id + " eating");
            Thread.sleep((int)(2000+Math.random() * 5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseFork() {
        // right
        fork[(id+1)%fork.length] = true;
        // left
        fork[id] = true;
        System.out.println("Philosopher #" + id + " release the fork");
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