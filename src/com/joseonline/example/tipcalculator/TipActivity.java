
package com.joseonline.example.tipcalculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TipActivity extends Activity {

    private static final int DEFAUTL_INITIAL_TIP = 15;
    private static final int MAX_NUMBER_PICKER_VALUE = 20;
    private static final int MIN_NUMBER_PICKER_VALUE = 1;

    private EditText etBillAmount;
    private TextView tvTipPct;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;
    private SeekBar sbTipPct;
    private NumberPicker npNumPeople;
    private TextView tvPerPerson1;
    private TextView tvPerPerson2;

    private final NumberFormat decimalFormatter = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        // Bind UI elements
        etBillAmount = (EditText) findViewById(R.id.etBillAmount);
        tvTipPct = (TextView) findViewById(R.id.tvTipPct);
        tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);
        tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        sbTipPct = (SeekBar) findViewById(R.id.sbTipPct);
        npNumPeople = (NumberPicker) findViewById(R.id.npNumPeople);
        tvPerPerson1 = (TextView) findViewById(R.id.tvPerPerson1);
        tvPerPerson2 = (TextView) findViewById(R.id.tvPerPerson2);

        setupSeekBarListener();

        setupEditorActionListener();

        setupNumberPickerListener();
    }
    
    /**
     * Setup Tip Percentage (SeekBar) listener
     */
    private void setupSeekBarListener() {
        sbTipPct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar bar)
            {
                double bill = Double.parseDouble(etBillAmount.getText().toString());
                int tipPct = bar.getProgress();
                int splitNum = npNumPeople.getValue();

                calculateTip(bill, tipPct, splitNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar bar)
            {

            }

            @Override
            public void onProgressChanged(SeekBar bar, int paramInt, boolean paramBoolean)
            {
                tvTipPct.setText("" + paramInt + "%");
            }
        });
        sbTipPct.setProgress(DEFAUTL_INITIAL_TIP);
    }
    
    /**
     * Setup Total Amount (EditText) OnEditorActionListener
     */
    private void setupEditorActionListener() {
        etBillAmount.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        double bill = Double.parseDouble(v.getText().toString());
                        int tipPct = sbTipPct.getProgress();
                        int splitNum = npNumPeople.getValue();

                        calculateTip(bill, tipPct, splitNum);
                    } catch (NumberFormatException e) {
                        Log.i(INPUT_SERVICE, "Invalid bill amount: ", e);
                    }
                }
                return false;
            }
        });
    }
    
    /**
     * Setup Number of People (NumberPicker) Listener
     */
    private void setupNumberPickerListener() {
        // Initialize NumberPicker input control
        npNumPeople.setMaxValue(MAX_NUMBER_PICKER_VALUE);
        npNumPeople.setMinValue(MIN_NUMBER_PICKER_VALUE);

        npNumPeople.setOnValueChangedListener(new OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > 1) {
                    tvPerPerson1.setVisibility(View.VISIBLE);
                    tvPerPerson2.setVisibility(View.VISIBLE);
                } else {
                    tvPerPerson1.setVisibility(View.INVISIBLE);
                    tvPerPerson2.setVisibility(View.INVISIBLE);
                }

                double bill = Double.parseDouble(etBillAmount.getText().toString());
                int tipPct = sbTipPct.getProgress();

                calculateTip(bill, tipPct, newVal);
            }
        });
    }

    /**
     * Calculate the tip amount base on bill and tip percentage and update the UI
     * 
     * @param bill
     * @param tipPct
     */
    private void calculateTip(double bill, int tipPct, int splitNum) {
        double tip = (bill * tipPct / 100) / splitNum;
        double totalAmount = (bill / splitNum) + tip;

        tvTipAmount.setText("$" + decimalFormatter.format(tip));
        tvTotalAmount.setText("$" + decimalFormatter.format(totalAmount + tip));
    }
}
