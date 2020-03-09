
package com.example.earthquake_monitor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.crypto.AEADBadTagException;

public class DownloadEqsAsyncTask extends AsyncTask<URL, Void, ArrayList<EarthQuake>> {

    public DownloadEqsInterface delegate;
    private Context context;

    DownloadEqsAsyncTask(Context context) {
        this.context = context;
    }

    public interface DownloadEqsInterface {

        void onEqsDownloaded(ArrayList<EarthQuake> eqList);
    }


    @Override
    protected ArrayList<EarthQuake> doInBackground(URL... urls) {
        String eqData;
        ArrayList<EarthQuake> eqList = null;

        try {
            eqData = downloadData(urls[0]);
            eqList = parseDataJson(eqData);
            saveEarthquakesOnDB(eqList);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return eqList;
    } // end doInBackground()


    @Override
    protected void onPostExecute(ArrayList<EarthQuake> eqList) {
        super.onPostExecute(eqList);

        delegate.onEqsDownloaded(eqList);
    }

    private void saveEarthquakesOnDB(ArrayList<EarthQuake> eqList) {
        EarthquakeDbHelper dbHelper = new EarthquakeDbHelper(this.context);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        for(EarthQuake earthQuake : eqList) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(EarthquakeContract.EarthquakeColumns.MAGNITUDE, earthQuake.getMagnitude());
            contentValues.put(EarthquakeContract.EarthquakeColumns.PLACE, earthQuake.getPlace());
            contentValues.put(EarthquakeContract.EarthquakeColumns.LONGITUDE, earthQuake.getLatitude());
            contentValues.put(EarthquakeContract.EarthquakeColumns.LATITUDE, earthQuake.getLatitude());
            contentValues.put(EarthquakeContract.EarthquakeColumns.TIMESTAMP, earthQuake.getDateTime());

            database.insert(EarthquakeContract.EarthquakeColumns.TABLE_NAME, null, contentValues);
        }

    }

    private ArrayList<EarthQuake> parseDataJson(String eqsData) {
        ArrayList<EarthQuake> eqList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(eqsData);
            JSONArray featuresJsonArray = jsonObject.getJSONArray("features");

            for(int i = 0; i < featuresJsonArray.length(); i++) {
                JSONObject featuresJsonObject = featuresJsonArray.getJSONObject(i);
                JSONObject propertiesJsonObject = featuresJsonObject.getJSONObject("properties");

                double magnitude = propertiesJsonObject.getDouble("mag");
                String place = propertiesJsonObject.getString("place");
                Long timestamp = propertiesJsonObject.getLong("time");

                JSONObject geometryJsonObject = featuresJsonObject.getJSONObject("geometry");
                JSONArray coordinates = geometryJsonObject.getJSONArray("coordinates");
                String longitude = coordinates.getString(0);
                String latitude = coordinates.getString(1);

                eqList.add(new EarthQuake(timestamp, magnitude, place, longitude,latitude));
                Log.d("MANZANA", magnitude + " " + place);
            } // end loop for


        }catch (JSONException e) {
            e.printStackTrace();
        }

        return eqList;
    } // end parseDataJson(...)

    // Api Method
    private String downloadData (URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000/* milliseconds */);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();

            if (inputStream != null)
                inputStream.close();
        } // end finally

        return jsonResponse;
    } // End downloadData(...)


    private String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while(line != null){
                output.append(line);
                line = reader.readLine();
            } // end loop while()

        } // end if(inputStream != null)

        return output.toString();
    } // end readFromStream(...)

}
