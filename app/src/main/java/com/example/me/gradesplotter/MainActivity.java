package com.example.me.gradesplotter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText total, A, B, C, D, F;
    Button compute, clear;
    Dialog myDialog;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total = (EditText)findViewById(R.id.totalTxt);
        A = (EditText)findViewById(R.id.ATxt);
        B = (EditText)findViewById(R.id.BTxt);
        C = (EditText)findViewById(R.id.CTxt);
        D = (EditText)findViewById(R.id.DTxt);
        F = (EditText)findViewById(R.id.FTxt);

        compute = (Button)findViewById(R.id.computeBtn);
        clear = (Button)findViewById(R.id.clearBtn);

        myDialog = new Dialog(this);

    }


    public boolean validate(EditText ed1,EditText ed2,EditText ed3,EditText ed4,EditText ed5,EditText edTotal)
    {
        boolean check = true;

        if(ed1.getText().toString().equals("") || (Integer.parseInt(ed1.getText().toString())>Integer.parseInt(edTotal.getText().toString()))){

            ed1.setError("Invalid data!");
            check = false;
        }

        if(ed2.getText().toString().equals("") || (Integer.parseInt(ed2.getText().toString())>Integer.parseInt(edTotal.getText().toString()))){

        ed2.setError("Invalid data!");
        check = false;
    }

        if(ed3.getText().toString().equals("") || (Integer.parseInt(ed3.getText().toString())>Integer.parseInt(edTotal.getText().toString()))){

        ed3.setError("Invalid data!");
        check = false;
    }

        if(ed4.getText().toString().equals("") || (Integer.parseInt(ed4.getText().toString())>Integer.parseInt(edTotal.getText().toString()))){

        ed4.setError("Invalid data!");
        check = false;
    }

        if(ed5.getText().toString().equals("") || (Integer.parseInt(ed5.getText().toString())>Integer.parseInt(edTotal.getText().toString()))){

        ed5.setError("Invalid data!");
        check = false;
    }

        if(edTotal.getText().toString().equals("")){

        ed1.setError("Invalid data!");
        check = false;
    }

        int temp1 = Integer.parseInt(ed1.getText().toString());
        int temp2 = Integer.parseInt(ed2.getText().toString());
        int temp3 = Integer.parseInt(ed3.getText().toString());
        int temp4 = Integer.parseInt(ed4.getText().toString());
        int temp5 = Integer.parseInt(ed5.getText().toString());

        if(Integer.parseInt(edTotal.getText().toString()) < (temp1+temp2+temp3+temp4+temp5))
        {
            edTotal.setError("The value provided here is smaller than total students!");
            check = false;
        }

    return check;
    }

    public void onCompute(View view) {

        boolean check = validate(A,B,C,D,F,total);

        if(check==true)
        {
            int Astudent, Bstudent, Cstudent, Dstudent, Fstudent, TotalStudent;
            float Apercent, Bpercent, Cpercent, Dpercent, Fpercent;

            Astudent = Integer.parseInt(A.getText().toString());
            Bstudent = Integer.parseInt(B.getText().toString());
            Cstudent = Integer.parseInt(C.getText().toString());
            Dstudent = Integer.parseInt(D.getText().toString());
            Fstudent = Integer.parseInt(F.getText().toString());
            TotalStudent = Integer.parseInt(total.getText().toString());

            Apercent =  ((float)Astudent/TotalStudent) * 100;
            Bpercent = ((float)Bstudent/TotalStudent) * 100;
            Cpercent = ((float)Cstudent/TotalStudent) * 100;
            Dpercent = ((float)Dstudent/TotalStudent) * 100;
            Fpercent = ((float)Fstudent/TotalStudent) * 100;

            myDialog.setContentView(R.layout.bar_layout);
            barChart = (BarChart)myDialog.findViewById(R.id.bargraph);
            TextView cancel = (TextView)myDialog.findViewById(R.id.cancelBtn);

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                }
            });

            ArrayList<BarEntry> Yentries = new ArrayList<>();

            Yentries.add(new BarEntry(0,Apercent));
            Yentries.add(new BarEntry(1,Bpercent));
            Yentries.add(new BarEntry(2,Cpercent));
            Yentries.add(new BarEntry(3,Dpercent));
            Yentries.add(new BarEntry(4,Fpercent));
            BarDataSet barDataSet = new BarDataSet(Yentries, "Percentile");
            barDataSet.setDrawIcons(false);
            barDataSet.setColors(new int[] {Color.RED, Color.GREEN, Color.GRAY, Color.YELLOW, Color.BLUE});



            BarData mData = new BarData( barDataSet);
            barChart.setData(mData);

            final String[] quarters = new String[] { "A's", "B's", "C's", "D's", "F's" };

            IAxisValueFormatter formatter = new IAxisValueFormatter() {

                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return quarters[(int) value];
                }


            };

            XAxis xAxis = barChart.getXAxis();
            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setValueFormatter(formatter);
            barChart.getAxisLeft().setTextColor(Color.WHITE);
            barChart.getAxisRight().setTextColor(Color.WHITE);// left y-axis
            barChart.getXAxis().setTextColor(Color.WHITE);
            barChart.getLegend().setTextColor(Color.WHITE);
            barChart.getXAxis().setDrawGridLines(false);
            barChart.getAxisLeft().setDrawGridLines(false);
            barChart.getAxisRight().setDrawGridLines(false);

            Legend l = barChart.getLegend();
            l.setEnabled(true);
            l.setExtra(new int[] {Color.RED, Color.GREEN, Color.GRAY, Color.YELLOW, Color.BLUE},new String[]{"A's", "B's", "C's", "D's", "F's"} );

            barChart.setScaleEnabled(true);
            barChart.setTouchEnabled(true);
            barChart.setDragEnabled(true);

            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();

        }
    }

    public void onClear(View view) {

        total.setText("");
        A.setText("");
        B.setText("");
        C.setText("");
        D.setText("");
        F.setText("");
    }
}
