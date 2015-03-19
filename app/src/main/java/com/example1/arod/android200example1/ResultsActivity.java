package com.example1.arod.android200example1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ResultsActivity extends ActionBarActivity {

    String surveyResults;
    TextView output;
    ShareActionProvider shareSurveyResults;
    ListView resultsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Setup ShareActionProvider to use this class
        shareSurveyResults = new ShareActionProvider(this);

        setResultsLabel();
        setResultsList();
    }

    private void setResultsLabel() {

        // Receive the incoming intent
        Intent intent = getIntent();

        // Grab the message passed from the activity
        surveyResults = intent.getStringExtra(SurveyActivity.EXTRA_MESSAGE);

        // Put message into output TextView
        output = (TextView) findViewById(R.id.surveyResults);
        output.setText(surveyResults);

    }

    private void setResultsList() {

        resultsList = (ListView) findViewById(R.id.resultsList);

        User user = new User();

        try {
            FileInputStream fis = openFileInput("User.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (User)ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String[] results = { "Name: " + user.getName(),
                             "Spouse Name: " + user.getSpouseName(),
                             "Number of Children: " + user.getNumberOfKids(),
                             "Pets: " + user.getPetType() };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapter.addAll(results);

        resultsList.setAdapter(adapter);

        resultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Toast.makeText(resultsList.getContext(), String.format("\"%s\" was tapped", results[arg2]), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate our menu
        getMenuInflater().inflate(R.menu.menu_results, menu);

        // Create an instance of our share menu item
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        // Add compatibility support ShareActionProvider
        MenuItemCompat.setActionProvider(menuItem, shareSurveyResults);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // If share menu item is selected, perform share actions
        if(item.getItemId() == R.id.menu_item_share)
            onShareAction();

        return super.onOptionsItemSelected(item);
    }

    private void onShareAction(){

        if (shareSurveyResults != null) {

            // create an Intent with the contents of the TextView
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Did you know " + surveyResults + ", OMG!" );

            // Make sure the provider knows
            // it should work with that Intent
            shareSurveyResults.setShareIntent(shareIntent);
        }
    }

}
