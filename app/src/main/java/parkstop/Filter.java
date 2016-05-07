package parkstop;
import java.util.ArrayList;

/**
 * Purpose: This class reads all the parks from the JSON Task and does filter
 * It's instance variable contains all the parks in the database
 */
public class Filter
{
    private ArrayList<Park> parkArrayList;

    public Filter(ArrayList<Park> parks)
    {
        this.parkArrayList = parks;
    }

    public ArrayList<Park> getParkArrayList()
    {
        return parkArrayList;
    }

    public ArrayList<Park> filterParksByZipcode(int zipcode)
    {
        ArrayList<Park> result = new ArrayList<>();

        for(int i = 0; i < parkArrayList.size(); i++)
        {
            if(parkArrayList.get(i).getZip() == zipcode)
            {
                result.add(parkArrayList.get(i));
            }
        }
        return result;
    }

    public ArrayList<Park> filterParksByAttributes(ArrayList<String> attributes)
    {
        ArrayList<Park> result = new ArrayList<>();


        return result;
    }

 }

