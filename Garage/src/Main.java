import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InputMismatchException {

        Garage.addParking(new Parking("ZZZ1", "John", "Alex",0));
        Garage.addParking(new Parking("FK231", "Jim", "Bob",1));
        Garage.addParking(new Parking("KOR0", "Michael", "Alex",0));

        System.out.println("Welcome! What would you like to do?");

        do {
            int choice;
            do {
                System.out.println("""
                    
                    [0] Check vacancy.
                    [1] Park a vehicle.
                    [2] Remove a vehicle.
                    [3] View valet per vehicle.
                    [4] Calculate earnings so far.
                    [5] Exit.
                    
                    Press [0-5]""");

                choice = readInt(5);
            } while (choice < 0 || choice > 5 );

            switch (choice) {
                case 0 -> Garage.displayVacancy();

                case 1 -> {
                    if (Garage.isFull()) {
                        Garage.displayVacancy();
                        break;
                    }
                    System.out.println("Please enter the vehicle's info.");
                    String plate = readEmpty("Plate: ");
                    String driver = noDigits("Driver: ");
                    System.out.print("Enter 0 if it is a Car, 1 if it is a Motorcycle: ");
                    int vehicle = readInt(1);
                    String valet = noDigits("Valet: ");

                    Garage.addParking(new Parking(plate, driver, valet, vehicle));
                }

                case 2 -> {
                    System.out.println("Which vehicle would you like to remove? Press the appropriate number.");
                    Garage.listParking();
                    choice = readInt(Garage.getOccupied() - 1);
                    Garage.removeParking(choice);
                }

                case 3 -> Garage.staff();

                case 4 -> System.out.println("Total money earned so far: " + Garage.earnings());

                case 5 -> {
                    System.out.println("Goodbye for now!");
                    return;
                }
            }
        } while (true);
    }

    public static int readInt(int range) {
        Scanner input = new Scanner( System.in );
        // -1 to be out of range in the do_while loop.
        int option = -1;

        do {
            try{
                option = Integer.parseInt(input.nextLine());
                if (option < 0 || option > range)
                    System.out.println("Number must be from 0 up to " + range + ".\n");
            }catch (NumberFormatException ex) {
                System.out.println("Please enter a number.\n");
            }
        } while (option < 0 || option > range);
        return option;
    }

    public static String readEmpty(String text) {
        Scanner input = new Scanner( System.in );
        String in;
        do {
            System.out.print(text);
            in = input.nextLine();
        } while (in.isBlank());
        // isBlank guarantees no empty string, including whitespaces ("  " is not accepted).

        return in;
    }

    public static String noDigits(String text) {
        String in = readEmpty(text);
        // matches: any char 0+ times (except newline), any digit, any char 0+ times (except newline).
        // in other words: checks if there is a digit in the string.
        while (in.matches(".*\\d.*")) {
            System.out.println("Input cannot contain digits. Try again.");
            in = readEmpty(text);
        }
        return in;
    }
}
