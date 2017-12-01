package com.pocketmarket.mined.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.pocketmarket.mined.R;

import java.util.ArrayList;

/**
 * Created by mark on 12/1/17.
 */

public class ExpenseAnalyticsActivity extends AppCompatActivity {
    private final static String TAG = "ExpenseAnalyticsActivity";
    private XYPlot mPlot;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_expense_analytics);


        ArrayList<String> sPesoFixes = getIntent().getStringArrayListExtra("pesofixed");
        ArrayList<String> sDollars = getIntent().getStringArrayListExtra("dollar");
        ArrayList<String> sEquities = getIntent().getStringArrayListExtra("equity");


        ArrayList<Double> pesoFixes = new ArrayList<Double>();
        for (String pesoFixed:sPesoFixes){
            Log.i(TAG, "pesoFixed: " + pesoFixed);
            pesoFixes.add(Double.parseDouble(pesoFixed));
        }

        ArrayList<Double> dollars = new ArrayList<Double>();
        for (String dollar:sDollars){
            Log.i(TAG, "dollar: " + dollar);
            dollars.add(Double.parseDouble(dollar));
        }

        ArrayList<Double> equities = new ArrayList<Double>();
        for (String equity:sEquities){
            Log.i(TAG, "equity: " + equity);
            equities.add(Double.parseDouble(equity));
        }


        mPlot = (XYPlot) findViewById(R.id.expense_plot);

        XYSeries pesoFixed = new SimpleXYSeries(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "PFI", pesoFixes.toArray(new Double[pesoFixes.size()]));

        XYSeries dollar = new SimpleXYSeries(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "DBF", dollars.toArray(new Double[dollars.size()]));

        XYSeries equity = new SimpleXYSeries(SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "DPE", equities.toArray(new Double[equities.size()]));

        mPlot.addSeries(pesoFixed, new LineAndPointFormatter(Color.RED, Color.RED, null, null));
        mPlot.addSeries(dollar, new LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null));
        mPlot.addSeries(equity, new LineAndPointFormatter(Color.YELLOW, Color.YELLOW, null, null));

    }

}
