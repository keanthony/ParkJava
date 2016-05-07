package parkstop;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Park implements Serializable, Comparable<Park>
{
    // Instance variables
    private String name;
    private String parkID;
    private String address;
    private int zip;
    private ArrayList<String> attributes;

    /**Constructor
     * Creates park objects
     */
    public Park(String id, String name, String address, int zip, String hours)
    {

        this.name = name;
        this.address = address;
        this.zip = zip;
        attributes = new ArrayList<>();
        parkID = id;

    }

    public Park()
    {

    }

    public String getParkID() {
        return parkID;
    }

    public String getName()
    {
        return this.name;
    }

    public String getAddress()
    {
        return this.address;
    }

    public int getZip(){return this.zip;}

    @Override
    public int compareTo(Park another)
    {
        if(this.getZip() < another.getZip())
            return -1;

        if(this.getZip() > another.getZip())
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        return "Park{" +
                "name='" + name + '\'' +
                ", parkID=" + parkID +
                ", address='" + address + '\'' +
                ", zip=" + zip +
                ", attributes=" + attributes +
                '}';
    }
}
