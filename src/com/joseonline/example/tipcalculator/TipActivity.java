
package com.joseonline.example.tipcalculator;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipActivity extends Activity {

    private static final double TIP_10_PCT = 0.10;
    private static final double TIP_15_PCT = 0.15;
    private static final double TIP_20_PCT = 0.20;

    private EditText etTotalAmount;
    private TextView tvTipAmount;

    private Button btn10pct;
    private Button btn15pct;
    private Button btn20pct;

    private final NumberFormat decimalFormatter = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        etTotalAmount = (EditText) findViewById(R.id.etTotalAmount);
        tvTipAmount = (TextView) findViewById(R.id.tvTipAmount);

        btn10pct = (Button) findViewById(R.id.btn10pct);
        btn15pct = (Button) findViewById(R.id.btn15pct);
        btn20pct = (Button) findViewById(R.id.btn20pct);

        btn10pct.setOnClickListener(calculateTipListener(TIP_10_PCT));
        btn15pct.setOnClickListener(calculateTipListener(TIP_15_PCT));
        btn20pct.setOnClickListener(calculateTipListener(TIP_20_PCT));
    }

    private OnClickListener calculateTipListener(final double tipPct) {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    double totalAmount = Double.parseDouble(etTotalAmount.getText().toString());
                    double tipAmount = calculateTip(totalAmount, tipPct);
                    tvTipAmount.setText("$" + decimalFormatter.format(tipAmount));
                } catch (NumberFormatException e) {
                    Log.i(INPUT_SERVICE, "Invalid Total Amount: ", e);
                }
            }
        };
    }

    /**
     * Returns tip amount base on total and tip percentage.
     * 
     * @param totalAmount
     * @param tipPct
     */
    private double calculateTip(double totalAmount, double tipPct) {
        return totalAmount * tipPct;
    }
}
