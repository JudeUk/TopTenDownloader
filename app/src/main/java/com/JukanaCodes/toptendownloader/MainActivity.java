package com.JukanaCodes.toptendownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousByteChannel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.feed_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        String feedUrl = "";

        switch(itemID){
            case R.id.menuFree:
                feedUrl="http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";
                break;
            case R.id.menuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=10/xml";
                break;
            case R.id.menuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml";
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        newDownloader(feedUrl);
        return super.onOptionsItemSelected(item);
    }

    public void newDownloader(String feedUrlString){

        Log.d(TAG, "onCreate: starting");
        Downloader downloader = new Downloader();
        downloader.execute(feedUrlString);
        Log.d(TAG, "onCreate: completed");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.xmlListView);
    }

    private String downloadXML(String urlString) {

        BufferedReader bufferedReader;
        StringBuilder xmlstringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int response = httpURLConnection.getResponseCode();
            Log.d(TAG, "downloadXML: The response code" + response);
//            InputStream inputStream = httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
//            bufferedReader = new BufferedReader(inputStreamReader);

            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            int charRead;
            char[] bufferedCharArray = new char[500];


            while (true) {
                charRead = bufferedReader.read(bufferedCharArray);
                if (charRead < 0) {
                    break;
                }
                if (charRead > 0) {

                    xmlstringBuilder.append(String.copyValueOf(bufferedCharArray, 0, charRead));
                }
            }
                bufferedReader.close();

                return xmlstringBuilder.toString();



        } catch (MalformedURLException e) {

            Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "downloadXML: IOException reading Data " + e.getMessage());
        } catch (SecurityException e) {

            Log.e(TAG, "downloadXML: Security Exception. Needs Permission? " + e.getMessage());
        }

        return null;
//        return xmlstringBuilder.toString();


    }

    private class Downloader extends AsyncTask<String, Void, String> {

        private static final String TAG = "Downloader";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: the parameter for postExecute is " + s);
            XmlParser pp = new XmlParser();
            pp.parser(s);
//            ArrayAdapter<FeedResponseEntry> arrayAdapter = new ArrayAdapter<FeedResponseEntry>(MainActivity.this,R.layout.list_record,pp.getApplications());
//            listView.setAdapter(arrayAdapter);
               FeedAdapter feedAdapter = new FeedAdapter(MainActivity.this,R.layout.list_record,pp.getApplications());
               listView.setAdapter(feedAdapter);

        }


        @Override
        protected String doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);

            if (rssFeed == null) {

                Log.e(TAG, "doInBackground: Error downloading ");
            }

            Log.d(TAG, "doInBackground: rssFeed is" + rssFeed);
            return rssFeed;
        }


    }



}

