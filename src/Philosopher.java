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
            System.out.println("Philosopher #" + id + " THINKING \uD83E\uDD14");
            Thread.sleep(2000+ (int)(Math.random() * ((5000 - 2000) + 1))); // wait between 2 and 5 seconds
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hungry() {
        try {
            this.state = HUNGRY;
            System.out.println("Philosopher #" + id + " HUNGRY \uD83D\uDE0B");
            Thread.sleep(2000+ (int)(Math.random() * ((5000 - 2000) + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean takesFork() {
        try {
            // right
            if (fork[(id + 1) % fork.length]) {
                fork[(id + 1) % fork.length] = false;
                System.out.println("Philosopher #" + id + " takes the right fork");
                Thread.sleep(1000);
                // left
                if (fork[id]) {
                    fork[id] = false;
                    System.out.println("Philosopher #" + id + " takes the left fork");
                    Thread.sleep(1000);
                    return true;
                } else {
                    System.out.println("Philosopher #" + id + " release the right fork, can't reach it the left one \uD83D\uDE20");
                    fork[(id + 1) % fork.length] = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void eat() {
        try {
            this.state = EATING;
            System.out.println("Philosopher #" + id + " EATING \uD83C\uDF74");
            Thread.sleep(2000+ (int)(Math.random() * ((5000 - 2000) + 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void releaseFork() {
        try {
            // right
            fork[(id + 1) % fork.length] = true;
            System.out.println("Philosopher #" + id + " release the right fork");
            // left
            fork[id] = true;
            System.out.println("Philosopher #" + id + " release the left fork");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                think();
                hungry();
                semaphore.acquire();
                while ( !takesFork() ) { Thread.sleep(2000);  }
                eat();
                releaseFork();
                semaphore.release();
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }
    }
}