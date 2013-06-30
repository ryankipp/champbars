package com.example.champbars;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Class: AndroidListClient - This is the main activity for this application.
 * This example shows how we can implement a simple client/server model using
 * an Android app as the client and a PHP script as the server. This example
 * should be used in conjunction with the AndroidListServer example.
 * 
 * Based on the tutorial from http://www.hassanpur.com/blog/2010/09/android-development-implementing-a-simple-client-server-model/
 * 
 */
public class CheckInActivity extends ListActivity {

        // Instance Variables
        private CheckInActivity mainActivity = null;

        /** Called when the activity is first created. */
        @SuppressWarnings("unchecked")
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                
                // we're going to need this later by the other threads
                mainActivity = this;

                // let's initialize the list, the real data will be filled in later
                String[] initialList = {""};

                // now we'll supply the data structure needed by this ListActivity
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_android_list_client, initialList);
                this.setListAdapter(adapter);

               
                ListView lv = this.getListView();
                lv.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                /*
                                 * Spawn a thread. This thread will get the
                                 * data from the server in the background, without blocking the
                                 * main UI thread.
                                 */
                        	String test = (String) ((TextView)view).getText();
                                (new BarCheckInTask()).execute((Object)((TextView)view).getText());
                        	Log.i( "OnItemClick", "Name: " + test );
                        }

                });

                /*
                 * Spawn a GetListTask thread. This thread will get the data from the
                 * server in the background, so as not to block our main (UI) thread.
                 */
                (new GetListTask()).execute((Object)null);
        }

        /**
         * Used to implement an asynchronous retrieval of the list from the web.
         * This uses the AsyncTask class as a starting point. For more info, see
         * http://android-developers.blogspot.com/2009/05/painless-threading.html.
         */
        @SuppressWarnings("unchecked")
        private class GetListTask extends AsyncTask {

                /**
                 * Let's make the http request and return the result as a String.
                 */
                protected String doInBackground(Object... args) {                       
                        return ServerInterface.getBarsList();
                }

                /**
                 * Parse the String result, and create a new array adapter for the list
                 * view.
                 */
                protected void onPostExecute(Object objResult) {
                        // check to make sure we're dealing with a string
                        if(objResult != null && objResult instanceof String) {                          
                                String result = (String) objResult;
                                // this is used to hold the string array, after tokenizing
                                String[] responseList;

                                // we'll use a string tokenizer, with "," (comma) as the delimiter
                                StringTokenizer tk = new StringTokenizer(result, ",");

                                // now we know how long the string array is
                                responseList = new String[tk.countTokens()];

                                // let's build the string array
                                int i = 0;
                                while(tk.hasMoreTokens()) {
                                        responseList[i++] = tk.nextToken();
                                }

                                // now we'll supply the data structure needed by this ListActivity
                                ArrayAdapter<String> newAdapter = new ArrayAdapter<String>(mainActivity, R.layout.activity_android_list_client, responseList);
                                mainActivity.setListAdapter(newAdapter);
                        }
                }

        }

        
        /**
         * Used to spawn a thread to retrieve the animal sound.
         */
        @SuppressWarnings("unchecked")
        private class BarCheckInTask extends AsyncTask {

                /**
                 * Let's make the http request and return the result as a String.
                 */
                protected String doInBackground(Object... args) {
                        if(args != null && args[0] instanceof String) {
                                String bar = (String) args[0];
                                Log.i( "BarCheckInTask", "Name: " + bar );
                                return ServerInterface.barCheckIn(bar);
                        } else {
                                return null;
                        }
                }

                /**
                 * Display the result as a Toast.
                 */
                protected void onPostExecute(Object objResult) {
                        // check to make sure we're dealing with a string
                        if(objResult != null && objResult instanceof String) {                          
                                String result = (String) objResult;
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                        }
                }

        }
}