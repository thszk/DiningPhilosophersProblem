import java.io.File;
import java.io.FileInputStream;
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

        Scanner scanner = new Scanner(System.in);
        int qtPhilosopher;
        Philosopher[] philosopher;
        Semaphore semaphore;
        boolean[] forks;

        qtPhilosopher = scanner.nextInt();
        philosopher = new Philosopher[qtPhilosopher];
        forks = new boolean[qtPhilosopher];
        semaphore = new Semaphore(qtPhilosopher/2); // permits -> number of eating philosophers

        for(int i = 0; i < qtPhilosopher; i++) {
            forks[i] = true;
        }

        for(int i = 0; i < qtPhilosopher; i++) {
            philosopher[i] = new Philosopher(i, semaphore, forks);
            philosopher[i].start();
        }


    }
}