package edu.eci.arsw.primefinder;

public class Control extends Thread {

    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 100;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];
    private final Object pauseLock;

    private Control() {
        super();
        this.pft = new PrimeFinderThread[NTHREADS];
        this.pauseLock = new Object();

        int i;
        for (i = 0; i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i * NDATA, (i + 1) * NDATA, pauseLock);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i * NDATA, MAXVALUE + 1, pauseLock);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for (int i = 0; i < NTHREADS; i++) {
            pft[i].start();
        }

        while (true) {
            try {
                Thread.sleep(TMILISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            synchronized (pauseLock) {
                System.out.println("Pausando los hilos...");
                for (PrimeFinderThread thread : pft) {
                    thread.pause();
                }
            }

            int totalPrimes = 0;
            for (PrimeFinderThread thread : pft) {
                totalPrimes += thread.getPrimes().size();
            }
            System.out.println("El total de números primos es de: " + totalPrimes);


            System.out.println("Presione ENTER para continuar...");
            new java.util.Scanner(System.in).nextLine();


            synchronized (pauseLock) {
                System.out.println("Continuando el proceso...");
                for (PrimeFinderThread thread : pft) {
                    thread.resumeThread();
                }
                pauseLock.notifyAll();
            }
        }
    }
}