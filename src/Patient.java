public class Patient extends Thread {

    private int patient_no;
    private WaitingRoom waitingRoom;
    private boolean treated = false;

    public Patient(int patient_no, WaitingRoom waitingRoom) {
        this.patient_no = patient_no;
        this.waitingRoom = waitingRoom;

        setName("P" + patient_no);
    }

    @Override
    public void run() {

        System.out.println(getName() + " arrived.");

        try {

            //Enter the waiting room or wait outside if it is full
            waitingRoom.enter(this);

            //Wait until the doctor finishes treatment
            synchronized (this) {

                while (!treated) {
                    wait();
                }
            }

            System.out.println(getName() + " left.");

        } catch (InterruptedException e) {

            System.out.println(getName() + " was interrupted.");

            Thread.currentThread().interrupt();
        }
    }

    public synchronized void markTreated() {

        treated = true;
        notifyAll();
    }

    public int getPatient_no() {
        return patient_no;
    }

    public synchronized boolean isTreated() {
        return treated;
    }
}
