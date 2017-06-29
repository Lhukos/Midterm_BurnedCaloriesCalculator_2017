package com.toney.burnedcaloriescalculator;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.NumberFormat;

public class BurnedCaloriesCalculatorActivity extends AppCompatActivity
        implements OnEditorActionListener, OnSeekBarChangeListener {

    // define widget variables
    private EditText weightET;
    private TextView caloriesTV;
    private TextView bmiTV;
    private TextView milesTV;
    private SeekBar milesSeekBar;
    private Spinner feetSpinner;
    private Spinner inchesSpinner;

    // define instance variables that should be saved
    private String weightString = "";

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burned_calories_calculator);

        // get reference to the widgets
        weightET = (EditText) findViewById(R.id.weightET);
        caloriesTV = (TextView) findViewById(R.id.caloriesTV);
        bmiTV = (TextView) findViewById(R.id.bmiTV);
        milesTV = (TextView) findViewById(R.id.milesTV);
        milesSeekBar = (SeekBar) findViewById(R.id.milesSeekBar);
        feetSpinner = (Spinner) findViewById(R.id.feetSpinner);
        inchesSpinner = (Spinner) findViewById(R.id.inchesSpinner);

        // set the listener
        // current class as listener
        weightET.setOnEditorActionListener(this);

        //anonymous class as the listener
        milesSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        //anonymous inner class as the listener
        feetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int pos = feetSpinner.getSelectedItemPosition() + 1;
                String selectedText = (String) feetSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        inchesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int pos = inchesSpinner.getSelectedItemPosition() + 1;
                String selectedText = (String) inchesSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }
        return false;
    }

    // anonymous class
    private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            milesTV.setText(progress + "mi");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            calculateAndDisplay();
        }
    };

    private void calculateAndDisplay() {
        // get weight from user
        weightString = weightET.getText().toString();
        float weight;
        if(weightString.equals("")) {
            weight = 0;
        } else {
            weight = Float.parseFloat(weightString);
        }

//        // get miles ran
//        int progress = milesSeekBar.getProgress();
//        milesRan = (float) progress + 1;
//
//        // get calories burned and bmi
//        (float) calories = 0.75 * weight * milesRan;
//        (float) bmi = (weight * 703) / ((12 * feet + inches) * (12 * feet + inches));
//
//        // format and display
//        NumberFormat miles = NumberFormat.getNumberInstance();
//        milesTV.setText(miles.format(milesRan));
//
//        NumberFormat cal = NumberFormat.getNumberInstance();
//        caloriesTV.setText(cal.format(calories));
//        bmiTV.setText(cal.format(bmi));

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        milesTV.setText(progress + 1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        calculateAndDisplay();
    };

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("weightString", weightString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        weightString = savedValues.getString("weightString", "");
        weightET.setText(weightString);
        calculateAndDisplay();
    }
}
