package parkstop;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GoogleMapJSON extends AsyncTask<Void, Integer, String>
{

    private String jsonURL;
    private static final String TAG_RESULTS = "results";
    private static final String TAG_FORMAT = "formatted_address";
    private static final String TAG_LOCATION = "location";
    private Context context;
    private String lat;
    private String lng;
    private final String LOG_TAG = GoogleMapJSON.class.getSimpleName();
    private ArrayList<String> errors;
    private ProgressDialog dialog;


    public GoogleMapJSON(Activity context, String address)
    {
        this.context = context;
        this.errors = new ArrayList<>();
        dialog = new ProgressDialog(context);
        String s = address.replace(" ", "+");
        this.jsonURL = "https://maps.googleapis.com/maps/api/geocode/json?" +
                "address=" + s + "&key=AIzaSyAAfZ4Xegck630zO5D_QxDZG069L-FHTJM";
        Log.d(LOG_TAG, jsonURL);
    }

    @Override
    protected void onPreExecute()
    {
        dialog.setMessage("Doing something, please wait.");
        dialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(Void... params)
    {
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
            JSONArray locations = jsonObj.getJSONArray(TAG_RESULTS);
            Log.d(LOG_TAG, ""+locations.length());
            JSONObject obj = locations.getJSONObject(0);

            JSONObject geometry = obj.getJSONObject("geometry");
            JSONObject location = geometry.getJSONObject("location");
            String lng = location.getString("lng");
            String lat = location.getString("lat");

            Log.d(LOG_TAG, lat + " : " + lng);
            setLat(lat);
            setLng(lng);



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

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
