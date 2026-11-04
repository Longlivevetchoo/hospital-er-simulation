import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final Queue<Patient> waitingPatients = new LinkedList<>();
    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore mutex;

    public WaitingRoom(int capacity) {
        this.empty = new Semaphore(capacity); // count free seats and starts with capacity
        this.full = new Semaphore(0); // counts waiting patients and starts at 0
        this.mutex = new Semaphore(1); // guards the queue and starts at 1
    }

    public void enter(Patient p) throws InterruptedException {
        if (empty.availablePermits() == 0) {
            System.out.println("The waiting room is full.");
            System.out.println(p.getName() + " " + "is waiting outside for an available seat.");
        }

        empty.acquire();
        mutex.acquire();
        waitingPatients.add(p);
        mutex.release();
        full.release();
        System.out.println(p.getName() + " " + "entered the waiting room.");

    }

    public Patient take() throws InterruptedException {
        full.acquire();
        mutex.acquire();
        Patient p = waitingPatients.poll(); // stores the first patient in a variable
        mutex.release();
        empty.release();

        return p;
    }

    public void sendBack(Patient p) throws InterruptedException {
        enter(p);
    }

}
