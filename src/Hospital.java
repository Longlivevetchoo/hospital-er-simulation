import java.util.Scanner;

public class Hospital {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int capacity = readPositiveInt(in, "Enter room capacity: ");
        int doctors = readPositiveInt(in, "Enter the number of doctors: ");
        int patients = readPositiveInt(in, "Enter the number of patients: ");

        Simulation simulation = new Simulation(capacity, doctors, patients);
        simulation.run();

        in.close();
    }

    private static int readPositiveInt(Scanner in, String TXT) {
        int value = 0;

        while (value < 1) {
            System.out.print(TXT);

            if (!in.hasNextLine()) {
                System.out.println("\nNo input available. Exiting.");
                System.exit(1);
            }

            try {
                value = Integer.parseInt(in.nextLine().trim());

                if (value < 1) {
                    System.out.println("Please enter a number greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }

        return value;
    }
}
