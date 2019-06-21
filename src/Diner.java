import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Diner {
    public static void main(String [] args) {

        try { // sets the default I/O to the text file
            System.setIn(new FileInputStream(new File("/home/xogaiht/Code/DiningPhilosophersProblem/input.txt")));
//            System.setOut(new PrintStream(new FileOutputStream("/home/xogaiht/Code//DiningPhilosophersProblem/output.txt", false)));
        } catch (Exception e) {
            System.out.println(e);
        }

        // input attributes
        Scanner scanner = new Scanner(System.in);
        int qtPhilosopher;
        // execution attributes
        Philosopher[] philosopher;
        boolean[] fork;
        Semaphore semaphore;

        // read number of philosophers
        System.out.print("Number of philosophers at diner: \n");
        qtPhilosopher = scanner.nextInt();
        // set the philosophers array and the fork array at the same value
        philosopher = new Philosopher[qtPhilosopher];
        fork = new boolean[qtPhilosopher];
        // set semaphore permits with number of philosophers / 2 being rounded down
        semaphore = new Semaphore(qtPhilosopher/2, true);

        // set all fork available
        for(int i = 0; i < qtPhilosopher; i++) {
            fork[i] = true;
        }

        System.out.println("---------------DINING---------------");
        for(int i = 0; i < qtPhilosopher; i++) { // instancing all philosophers && starting their threads
            philosopher[i] = new Philosopher(i, semaphore, fork);
            philosopher[i].start();
        }
    }
}