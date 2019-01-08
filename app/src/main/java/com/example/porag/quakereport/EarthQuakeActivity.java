package com.example.porag.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
//  App for learning networking
//  1 . Basics of JSON Parsing [ JSON = JavaScript object Notation ]
//     Json is not a programming language , its just a measure to communicate on the web
//           
//  2 . HTTP Networking



//  3.  Threads and parallelism .

public class EarthQuakeActivity extends AppCompatActivity {


        /**
         * Adapter for the list of earthquakes
         */
        private EarthQuakeAdapter mAdapter;

        /**
         * Constant value for the earthquake loader ID. We can choose any integer.
         * This really only comes into play if you're using multiple loaders.
         */


       public static final String LOG_TAG = EarthQuakeActivity.class.getName();

        //  Url for earthquake data from the USGS  dataset.
        public static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_earth_quake);


            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);


            mAdapter = new EarthQuakeAdapter(this, new ArrayList<Earthquake>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(mAdapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    Earthquake currentEarthquake = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });
            // Start the AsyncTask to fetch the earthquake data
            EarthquakeAsyncTask task = new EarthquakeAsyncTask();
            task.execute(USGS_REQUEST_URL);


        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



        private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

            @Override
            protected List<Earthquake> doInBackground(String... urls) {
                // Don't perform the request if there are no URLs, or the first URL is null.
                if (urls.length < 1 || urls[0] == null) {
                    return null;
                }
                List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
                return result;
            }

            // Theres no code to execute the progress ..so we omit it .

            /**
             * This method runs on the main UI thread after the background work has been
             * completed. This method receives as input, the return value from the doInBackground()
             * method. First we clear out the adapter, to get rid of earthquake data from a previous
             * query to USGS. Then we update the adapter with the new list of earthquakes,
             * which will trigger the ListView to re-populate its list items.
             */
            @Override
            protected void onPostExecute(List<Earthquake> data) {
                // Clear the adapter of previous earthquake data
                mAdapter.clear();

                // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
                // data set. This will trigger the ListView to update.
                if (data != null && !data.isEmpty()) {
                    mAdapter.addAll(data);

                }
            }

        }

    }

