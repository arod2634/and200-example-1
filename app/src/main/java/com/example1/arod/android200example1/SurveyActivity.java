package com.example1.arod.android200example1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class SurveyActivity extends Activity {

    // Declare class variables and objects
    EditText name;
    ToggleButton isMarried;
    CheckBox hasKids;
    CheckBox hasPets;
    TextView spouseLabel;
    TextView petType;
    TextView petLabel;
    EditText spouseName;
    TextView numberOfKidsLabel;
    RadioGroup numberOfKids;
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

        hasPets = (CheckBox) findViewById(R.id.pets);
        petType = (TextView) findViewById(R.id.petType);
        petLabel = (TextView) findViewById(R.id.petLabel);

        showWelcomeDialog();

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

        // Validate user input and display error messages to use via Toast
        if (name.getText().toString().matches(""))
            Toast.makeText(getApplicationContext(), "Name can not be empty", Toast.LENGTH_SHORT).show();
        else if (isMarried.isChecked() && spouseName.getText().toString().matches(""))
            Toast.makeText(getApplicationContext(), "Spouse name can not be empty", Toast.LENGTH_SHORT).show();
        else if (hasKids.isChecked() && numberOfChildren == null)
            Toast.makeText(getApplicationContext(), "Please specify how many children you have", Toast.LENGTH_SHORT).show();
        else {

            summary =  name.getText() + " is single with ";

            // Input is valid - build readable output string to show on next screen
            if (!isMarried.isChecked()) {

                // Single w/ no kids
                if(!hasKids.isChecked())
                    summary += "no children";
                else  {
                    // Single w/ 1 kid
                    if (numberOfChildren.equals("one"))
                        summary += numberOfChildren + " child";
                    // Single w/ 2 or more kids
                    else
                        summary += numberOfChildren + " children";
                }

            } else {

                summary = name.getText() + " is married to " + spouseName.getText();

                // Married w/ no kids
                if (!hasKids.isChecked())
                    summary += " with no children";
                else {

                    // Married w/ 1 child
                    if (numberOfChildren.equals("one"))
                        summary += " with " + numberOfChildren + " child";
                    // Married w/ 2 or more children
                    else
                        summary += " with " + numberOfChildren + " children";

                }

            }

            if (hasPets.isChecked())
                summary += " and has a " + petType.getText();
            else
                summary += " and no pets";

            showAreYouSureConfirmation();

        }

    }

    private void showAreYouSureConfirmation() {

        // Create an alert and set properties
        AlertDialog.Builder areYouSureConfirmation = new AlertDialog.Builder(this);
        areYouSureConfirmation.setTitle("Are you sure?");
        areYouSureConfirmation.setMessage("Do you really want to see your survey results?");

        // Setup a custom event listener to handle when a user selects an alert option
        DialogInterface.OnClickListener chooseOption = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButtonClicked) {

                // If the user selects "Yes" or "Maybe", show a custom toast
                if ((whichButtonClicked == AlertDialog.BUTTON_POSITIVE) || (whichButtonClicked == AlertDialog.BUTTON_NEUTRAL))
                    showCustomToast();

            }
        };

        // Attach custom listener to alert dialog clicks
        areYouSureConfirmation.setPositiveButton("Yes", chooseOption);
        areYouSureConfirmation.setNegativeButton("No", chooseOption);
        areYouSureConfirmation.setNeutralButton("Maybe", chooseOption);

        // Create and show custom dialog
        AlertDialog customDialog = areYouSureConfirmation.create();
        customDialog.show();

    }

    private void showCustomToast() {

        // Create and display custom toast
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(summary);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    private void showWelcomeDialog() {

        AlertDialog.Builder welcomeMessage  = new AlertDialog.Builder(this);
        welcomeMessage.setTitle("Welcome!");
        welcomeMessage.setMessage("Please tell me about yourself");

        AlertDialog dlg = welcomeMessage.create();
        dlg.show();

    }

    public void checkedHasPets(View view) {

        // Show custom pets dialog
        if (hasPets.isChecked()) {

            final String[] pets = { "Cat", "Dog", "Rabbit", "Turtle", "Fish", "Other..." };
            final ArrayList<String> selectedPets = new ArrayList<>(6);

            AlertDialog.Builder choosePet = new AlertDialog.Builder(this);
            choosePet.setTitle("What kind of pets do you have?");

            DialogInterface.OnMultiChoiceClickListener clickedMultiChoiceOption = new DialogInterface.OnMultiChoiceClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {


                    if (isChecked)
                        selectedPets.add(pets[which]);
                    else
                        selectedPets.remove(pets[which]);

                    Log.i("Output", String.valueOf(selectedPets));
                }
            };

            DialogInterface.OnClickListener clickedDialogButton = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == AlertDialog.BUTTON_POSITIVE) {
                        petType.setText(String.valueOf(selectedPets).subSequence(1, String.valueOf(selectedPets).length()-1));
                        petLabel.setVisibility(View.VISIBLE);
                        petType.setVisibility(View.VISIBLE);
                    } else
                        hasPets.setChecked(false);

                }
            };

            choosePet.setMultiChoiceItems(pets, null, clickedMultiChoiceOption);

            choosePet.setPositiveButton("OK", clickedDialogButton);
            choosePet.setNegativeButton("Cancel", clickedDialogButton);

            AlertDialog dlg = choosePet.create();
            dlg.show();

        } else {
            petLabel.setVisibility(View.GONE);
            petType.setVisibility(View.GONE);
        }

    }
}