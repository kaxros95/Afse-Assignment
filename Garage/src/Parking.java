import java.sql.Timestamp;

public class Parking {
    private final String valet, plate, driver;
    private final Timestamp arrival;
    private int cost;
    public enum vehicleType {Car, Motorcycle}
    vehicleType type;

    public Parking(String plate, String driver, String valet, int vehicle) {
        this.plate = plate;
        this.driver = driver;
        this.valet = valet;
        this.arrival = new Timestamp(System.currentTimeMillis());
        this.setType(vehicle);
    }

    public Timestamp getArrival() { return arrival; }
    public int getCost() {return cost;}
    public String getValet() {return valet;}
    public String getDriver() {return driver;}
    public String getPlate() {return plate;}

    public void setType(int type) {
        if (type == 0) this.type = vehicleType.Car;
        else this.type = vehicleType.Motorcycle;

        // If it is a Car, cost is 4.
        // If it is a Motorcycle, cost is 2.
        this.cost = 4 - type * 2;
    }

    @Override
    public String toString()
    {
        return "[Plate]: " + this.plate + "\t[Type]: "
                + this.type + "\t[Driver]: " + this.driver +
                "\t[Arrival]: " + this.arrival + "\n";
    }
}
