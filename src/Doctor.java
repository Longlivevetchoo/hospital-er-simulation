public class Doctor extends Thread {

    private int doctor_no;
    private WaitingRoom waitingRoom;
    private TreatmentRoom treatmentRoom;

    public Doctor(int doctor_no,
                  WaitingRoom waitingRoom,
                  TreatmentRoom treatmentRoom) {

        this.doctor_no = doctor_no;
        this.waitingRoom = waitingRoom;
        this.treatmentRoom = treatmentRoom;
    }

    @Override
    public void run() {

        System.out.println("Doctor " + doctor_no + " is now available.");

        while (!isInterrupted()) {

            try {

                //Wait until a patient is available
                Patient patient = waitingRoom.take();

                //Assign the patient to this doctor
                System.out.println("Doctor " + doctor_no + " is assigned to " + patient.getName() + ".");

                //Try to acquire a treatment room
                System.out.println("Doctor " + doctor_no + " is trying to acquire a treatment room." );

                boolean acquired =
                    treatmentRoom.tryAcquireRoom(3000);

                if (!acquired) {

                    System.out.println("Doctor " + doctor_no + " could not acquire a treatment room.");

                    //Send the patient back
                    System.out.println("Doctor " + doctor_no + " is sending " + patient.getName() + " back to the waiting room.");

                    waitingRoom.sendBack(patient);

                    System.out.println(
                        "Doctor " + doctor_no + " is now available."
                    );

                    continue;
                }

                //Begin treatment
                System.out.println("Doctor " + doctor_no + " acquired a treatment room.");

                boolean treatmentCompleted = false;

                try {

                    System.out.println("Doctor " + doctor_no + " begins treating " + patient.getName() + ".");

                    //Treatment simulation
                    int treatmentTime =
                        (int)(Math.random() * 4000) + 1000;

                    Thread.sleep(treatmentTime);

                    treatmentCompleted = true;

                } finally{
                    treatmentRoom.freeRoom();

                    System.out.println("Doctor " + doctor_no + " released the treatment room." );
                }

                //Finish treatment
                if (treatmentCompleted) {

                    System.out.println("Doctor " + doctor_no + " finished treating " + patient.getName() + ".");
                    patient.markTreated();
                }

                System.out.println("Doctor " + doctor_no + " is now available.");

            } catch (InterruptedException e) {

                System.out.println("Doctor " + doctor_no + " is stopping.");
                interrupt();
                break;
            }
        }
    }
}
