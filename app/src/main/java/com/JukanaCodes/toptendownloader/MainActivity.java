package com.JukanaCodes.toptendownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting");
        Downloader downloader = new Downloader();
        downloader.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        Log.d(TAG, "onCreate: completed");
    }

    private class Downloader extends AsyncTask<String, Void, String> {

        private static final String TAG = "Downloader";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: the parameter for postExecute is " + s);

        }


        @Override
        protected String doInBackground(String... strings) {

            Log.d(TAG, "doInBackground: starts with " + strings[0]);

            String rssFeed = downloadXML(strings[0]);

            if(rssFeed==null){

                Log.e(TAG, "doInBackground: Error downloading ");
            }

            return rssFeed;
        }
    }

    private String downloadXML(String urlString){

        BufferedReader bufferedReader ;
        StringBuilder xmlstringBuilder = new StringBuilder();
        try {
            URL url = new URL(urlString);
            final HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            int response = httpURLConnection.getResponseCode();
            Log.d(TAG, "downloadXML: The response code"+ response );
//            InputStream inputStream = httpURLConnection.getInputStream();
//            InputStreamReader inputStreamReader= new InputStreamReader(inputStream);
//            bufferedReader = new BufferedReader(inputStreamReader);

            bufferedReader= new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            int charRead;
            char[] bufferedCharArray = new char[500];

            charRead = bufferedReader.read(bufferedCharArray);
            while(true){

                if(charRead<0){
                    break;
                }
                if(charRead>0)
            {

                    xmlstringBuilder.append(String.copyValueOf(bufferedCharArray,0,charRead));
                }

                bufferedReader.close();

                return xmlstringBuilder.toString();

            }

        }
        catch (MalformedURLException e) {

            Log.e(TAG, "downloadXML: Invalid URL "+ e.getMessage() );
        }
        catch (IOException e) {
            Log.e(TAG, "downloadXML: IOException reading Data " + e.getMessage());
        }
        catch (SecurityException e){

            Log.e(TAG, "downloadXML: Security Exception. Needs Permission? " + e.getMessage());
        }

        return null;


    }


}