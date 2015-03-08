package com.example1.arod.android200example1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SurveyActivity extends Activity {

    // Declare class variables and objects
    EditText name;
    ToggleButton isMarried;
    CheckBox hasKids;
    TextView spouseLabel;
    EditText spouseName;
    TextView numberOfKidsLabel;
    RadioGroup numberOfKids;
    TextView output;
    String numberOfChildren;
    String summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        // Once the view in rendered, create instances of all the view objects that will we interacted with
        name = (EditText) findViewById(R.id.editName);

        isMarried = (ToggleButton) findViewById(R.id.marriageStatusToggle);
        spouseLabel = (TextView) findViewById(R.id.spouseName);
        spouseName = (EditText) findViewById(R.id.editSpouseName);

        hasKids = (CheckBox) findViewById(R.id.childrenCheckBox);
        numberOfKidsLabel = (TextView) findViewById(R.id.howManyChildren);
        numberOfKids = (RadioGroup) findViewById(R.id.numberOfChildren);

        output = (TextView) findViewById(R.id.output);

    }

    public void toggleMarriageStatus(View view) {

        // Dynamically hide and show spouse UI elements if marriage status is checked
        if (isMarried.isChecked()) {
            spouseLabel.setVisibility(View.VISIBLE);
            spouseName.setVisibility(View.VISIBLE);
        } else {
            spouseLabel.setVisibility(View.GONE);
            spouseName.setVisibility(View.GONE);
        }

    }

    public void checkedChildren(View view) {

        // Dynamically hide and show children UI elements if "Have Children" is checked
        if (hasKids.isChecked()) {
            numberOfKidsLabel.setVisibility(View.VISIBLE);
            numberOfKids.setVisibility(View.VISIBLE);
        } else {
            numberOfKidsLabel.setVisibility(View.GONE);
            numberOfKids.setVisibility(View.GONE);
        }

    }

    public void radioClicked(View view) {

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_one:
                if (checked)
                    numberOfChildren = "one";
                break;
            case R.id.radio_two:
                if (checked)
                    numberOfChildren = "two";
                break;
            case R.id.radio_three:
                if (checked)
                    numberOfChildren = "three";
                break;
            case R.id.radio_four:
                if (checked)
                    numberOfChildren = "three or more";
                break;
        }
    }

    public void summarizeInfo(View view) {

        Log.i("Output", "Name: " + name.getText());
        Log.i("Output", "Marriage Status: " + isMarried.isChecked());
        Log.i("Output", "Spouse Name: " + spouseName.getText());
        Log.i("Output", "Has Children: " + hasKids.isChecked());
        Log.i("Output", "Number of Children:: " + numberOfChildren);

        // Validate user input and display error messages to use via Toast
        if (name.getText().toString().matches("")) {

            output.setText("Name can not be empty");

        } else if (isMarried.isChecked() && spouseName.getText().toString().matches("")) {

            output.setText("Spouse name can not be empty");

        } else if (hasKids.isChecked() && numberOfChildren == null) {

            output.setText("Please specify how many children you have");

        } else {

            // Input is valid - build readable output string to show on next screen
            if (!isMarried.isChecked()) {

                // Single w/ no kids
                if(!hasKids.isChecked()) {

                    summary = name.getText() + " is single with no children ";

                } else  {

                    if (numberOfChildren.equals("one")) {

                        // Single w/ 1 kid
                        summary = name.getText() + " is single with " + numberOfChildren + " child";

                    } else {

                        // Single w/ 2 or more kids
                        summary = name.getText() + " is single with " + numberOfChildren + " children";
                    }

                }

            } else {

                if (!hasKids.isChecked()) {

                    // Married w/ no kids
                    summary = name.getText() + " is married to " + spouseName.getText() + " with no children";

                } else {

                    if (numberOfChildren.equals("one")) {

                        // Married w/ 1 child
                        summary = name.getText() + " is married to " + spouseName.getText() + " with " + numberOfChildren + " child";

                    } else {

                        // Married w/ 2 or more children
                        summary = name.getText() + " is married to " + spouseName.getText() + " with " + numberOfChildren + " children";

                    }

                }

            }

            output.setText(summary);

        }

    }

}