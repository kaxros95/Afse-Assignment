import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

public class Garage {

    private static ArrayList<Parking> parkingList = new ArrayList<>();
    private final static int spots = 50;
    private static double earnings;

    public static void listParking() {
        for (int i =0; i<parkingList.size(); i++)
            System.out.println("[" + i + "]\t" + parkingList.get(i));
        //parkingList.forEach(System.out::println);
    }

    public static double earnings() {
        // get current time
        Timestamp current = new Timestamp(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(30));
        // sort by driver
        parkingList.sort((Comparator.comparing(Parking::getDriver)));

        // calculate cost of each one
        for (int i = 0; i<parkingList.size(); i++)
        {
            double discount = 1; // no discount at first
            // list is ordered by driver's name, have to stay within bounds and compare to the next one
            if ( i < parkingList.size()-1){
                if (parkingList.get(i).getDriver().equals(parkingList.get(i+1).getDriver()))
                    // driver has a second vehicle parked, gets 30% off.
                    discount = 0.7;
            }
            long temp = current.compareTo(parkingList.get(i).getArrival()) > 0 ?
                    (current.getTime() - parkingList.get(i).getArrival().getTime())/ (60 * 1000) : 0;
            earnings += temp * parkingList.get(i).getCost() * discount;
        }
        return earnings;
    }

    public static void removeParking(int pos) {
        parkingList.remove(pos);
    }

    public static void addParking(Parking parking) {

        // sort by plate
        parkingList.sort((Comparator.comparing(Parking::getPlate)));

        for (int i = 0; i<parkingList.size(); i++)
        {
            // list is ordered by plate, have to stay within bounds and compare to the next one
            if ( i < parkingList.size()-1)
                if (parkingList.get(i).getPlate().equals(parkingList.get(i+1).getPlate())) {
                    // same plate is already parked
                    System.out.println("A vehicle with the exact same plate is already parked here. Please try again.");
                    return;

                }
        }
        // added if list is empty or plate is unique.
        parkingList.add(parking);
    }

    public static int getVacancy() { return spots - parkingList.size(); }
    public static int getOccupied() { return parkingList.size();}
    public static boolean isFull() { return getVacancy() == 0; }

    public static void staff() {
        // Prints each vehicle's plate number, along with who parked it.
        for (Parking i : parkingList)
            System.out.println("[Plate]: " + i.getPlate() + "\t[Valet]: " + i.getValet());
    }

    public static void displayVacancy() {
        if (isFull()) System.out.println("Garage is full. All " + spots + " are occupied.");
        else System.out.println("Garage is not full. There are " + getVacancy()
                + " parking spots remaining from a total of " + spots);
    }
}
	