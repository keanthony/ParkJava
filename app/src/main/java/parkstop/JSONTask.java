package parkstop;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JSONTask extends AsyncTask<Void, Integer, String>
{

    private String jsonURL;
    private Context context;
    private ArrayList<String> errors;
    private ArrayList<Park> parksList;
    private ProgressDialog dialog;
    private final String LOG_TAG = JSONTask.class.getSimpleName();

    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_ZIP = "zip";
    private static final String TAG_HOURS = "hours";
    private static final String TAG_PARKS = "parks";

    public JSONTask(Activity context, String query, ArrayList<String> attributes) {
        //this.jsonURL = "http://parkstop.herokuapp.com/api/parks?zip=" + query;
        this.jsonURL = "https://parkstopweb.herokuapp.com/api/parks?zip=" +
                query + "&restroom=" + attributes.get(0) + "&jogging=" +
                attributes.get(1) + "&playground=" + attributes.get(2) + "&dogpark=" +
                attributes.get(3);
        Log.d(LOG_TAG, "~~~~~~~THIS IS THE QUERY: " + jsonURL);
        this.context = context;
        this.errors = new ArrayList<>();
        this.parksList = new ArrayList<>();
        dialog = new ProgressDialog(context);
    }

    public ArrayList<Park> getParksList()
    {
        return parksList;
    }

    @Override
    protected void onPreExecute(){
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        URL url = null;

        JSONObject jsonObj = null;
        InputStream inStream = null;
        OutputStream outStream = null;
        String response = "";

            try {
                url = new URL(this.jsonURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inStream = urlConnection.getInputStream();
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
                String temp = "";
                while ((temp = bReader.readLine()) != null) {
                    response += temp;
            }

            /** JSON PARSING **/

            // GETTING JSON ARRAY
            jsonObj = new JSONObject(response);
            JSONArray parks = jsonObj.getJSONArray(TAG_PARKS);

            for(int i = 0; i < parks.length(); i++)
            {
                JSONObject obj = parks.getJSONObject(i);

                String id = obj.getString(TAG_ID);
                String name = obj.getString(TAG_NAME);
                String address = obj.getString(TAG_ADDRESS);
                int zip = Integer.parseInt(obj.getString(TAG_ZIP));
                String hours = obj.getString(TAG_HOURS);

                Park park = new Park(id, name, address, zip, hours);
                Log.d(LOG_TAG, park.toString());
                parksList.add(park);
            }

        } catch (Exception e)
            {
            this.errors.add(e.toString());
        } finally
            {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException ignored)
                    {
                    }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (!this.errors.isEmpty()) {
                Log.d(LOG_TAG + "ERRORS", this.errors.toString());
                return null;
            } else {
                return response;
            }
        }

    }

    @Override
    protected void onPostExecute(String response)
    {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }



}
