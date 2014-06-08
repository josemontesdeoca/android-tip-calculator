
package com.joseonline.example.tipcalculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class TipActivity extends Activity {

    private static final int DEFAUTL_INITIAL_TIP = 15;

    private EditText etBillAmount;
    private TextView tvTipPct;
    private TextView tvTipAmount;
    private TextView tvTotalAmount;
    private SeekBar sbTipPct;

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

        // Set up SeekBar Listener
        sbTipPct.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar bar)
            {
                double bill = Double.parseDouble(etBillAmount.getText().toString());
                int tipPct = bar.getProgress();

                calculateTip(bill, tipPct);
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

        // Set OnEditorActionListener for the EditText (etTotalAmount) widget
        etBillAmount.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    try {
                        double bill = Double.parseDouble(v.getText().toString());
                        int tipPct = sbTipPct.getProgress();

                        calculateTip(bill, tipPct);
                    } catch (NumberFormatException e) {
                        Log.i(INPUT_SERVICE, "Invalid bill amount: ", e);
                    }
                }
                return false;
            }
        });
    }

    /**
     * Calculate the tip amount base on bill and tip percentage and update the UI
     * 
     * @param bill
     * @param tipPct
     */
    private void calculateTip(double bill, int tipPct) {
        double tip = bill * tipPct / 100;
        double totalAmount = bill + tip;

        tvTipAmount.setText("$" + decimalFormatter.format(tip));
        tvTotalAmount.setText("$" + decimalFormatter.format(totalAmount + tip));
    }
}
