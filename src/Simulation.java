public class Simulation {

    private static final int TREATMENT_ROOMS = 2;      
    private static final long ARRIVAL_GAP_MS = 300;    // small gap between patient arrivals to make the output readable 

    private final int waitingRoomCapacity;
    private final int numberOfDoctors;
    private final int numberOfPatients;

    private WaitingRoom waitingRoom;
    private TreatmentRoom treatmentRoom;
    private Doctor[] doctors;
    private Patient[] patients;

    public Simulation(int waitingRoomCapacity, int numberOfDoctors, int numberOfPatients) {
        this.waitingRoomCapacity = waitingRoomCapacity;
        this.numberOfDoctors = numberOfDoctors;
        this.numberOfPatients = numberOfPatients;
    }

    private void initialize() {
        waitingRoom = new WaitingRoom(waitingRoomCapacity);
        treatmentRoom = new TreatmentRoom(TREATMENT_ROOMS);

        doctors = new Doctor[numberOfDoctors];
        for (int i = 0; i < numberOfDoctors; i++) {
            doctors[i] = new Doctor(i + 1, waitingRoom, treatmentRoom);
        }

        patients = new Patient[numberOfPatients];
        for (int i = 0; i < numberOfPatients; i++) {
            patients[i] = new Patient(i + 1, waitingRoom);
        }
    }

    public void run() {
        initialize();

        System.out.println();
        System.out.println("Patients arriving:");
        StringBuilder list = new StringBuilder();
        for (Patient p : patients) {
            list.append(p.getName()).append(" ");
        }
        System.out.println(list.toString().trim());
        System.out.println();

        try {
            for (Doctor d : doctors) {
                d.start();
            }

            for (Patient p : patients) {
                p.start();
                Thread.sleep(ARRIVAL_GAP_MS);
            }

            for (Patient p : patients) {
                p.join();
            }

            for (Doctor d : doctors) {
                d.interrupt();
            }
            for (Doctor d : doctors) {
                d.join();
            }

            System.out.println();
            System.out.println("All patients have been treated.");
            System.out.println("Simulation completed.");

        } catch (InterruptedException e) {
            System.out.println("Simulation was interrupted.");
            for (Doctor d : doctors) {
                d.interrupt();
            }
            Thread.currentThread().interrupt();
        }
    }
}
